package com.broman.streamio;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;

import com.broman.streamio.memory.NativeMemory;
import com.broman.streamio.memory.NativeMemoryAllocator;
import com.broman.streamio.memory.HeapMemory;
import com.broman.streamio.memory.HeapMemoryAllocator;
import com.broman.streamio.table.DynamicMemoryLookupTable;
import com.broman.streamio.table.MemoryLookupTable;
import com.broman.streamio.table.SingleMemoryLookupTable;

/**
 * @author Brayan Roman
 * @since  1.0.0
 */
public class Streamio implements IStreamio {

    private final static int MAX_SIZE = 1024 * 1024;
    private final static int MAX_BLOCK_SIZE = 1024;

    public static int blockIndexes(int size, int blockSize) {
        if (size == 0) {
            return 1;
        } else {
            return (int) Math.ceil((float) size / (float) blockSize);
        }
    }

    public static Streamio heap() {
        return new Streamio(
                MAX_SIZE,
                MAX_BLOCK_SIZE,
                new HeapMemoryAllocator(),
                new DynamicMemoryLookupTable(MAX_BLOCK_SIZE, MAX_BLOCK_SIZE)
        );
    }

    public static Streamio heap(int size) {
        return new Streamio(
                size,
                MAX_BLOCK_SIZE,
                new HeapMemoryAllocator(),
                new DynamicMemoryLookupTable(size, MAX_BLOCK_SIZE)
        );
    }

    public static Streamio heap(int size, int blockSize) {
        return new Streamio(
                size,
                blockSize,
                new HeapMemoryAllocator(),
                new DynamicMemoryLookupTable(size, blockSize)
        );
    }

    public static Streamio direct() {
        return new Streamio(
                MAX_SIZE,
                MAX_BLOCK_SIZE,
                new NativeMemoryAllocator(),
                new DynamicMemoryLookupTable(MAX_BLOCK_SIZE, MAX_BLOCK_SIZE)
        );
    }

    public static Streamio direct(int size) {
        return new Streamio(
                size,
                MAX_BLOCK_SIZE,
                new NativeMemoryAllocator(),
                new DynamicMemoryLookupTable(size, MAX_BLOCK_SIZE)
        );
    }

    public static Streamio direct(int size, int blockSize) {
        return new Streamio(
                size,
                blockSize,
                new NativeMemoryAllocator(),
                new DynamicMemoryLookupTable(size, blockSize)
        );
    }

    public static Streamio wrap(byte[] bytes) {
        return new Streamio(bytes.length, bytes.length, null, new SingleMemoryLookupTable(new HeapMemory(bytes)));
    }

    public static Streamio wrap(ByteBuffer buffer) {
        return new Streamio(buffer.capacity(), buffer.capacity(), null, new SingleMemoryLookupTable(
            buffer.isDirect() ? new NativeMemory(buffer) : new HeapMemory(buffer.array())
        ));
    }

    private final int size;
    private final int blockSize;
    private final MemoryAllocator allocator;
    private final MemoryLookupTable table;

    private byte defaultByte = 0;
    private CachedMemory cache = CachedMemory.NULL;
    private MemoryPool pool;

    Streamio(int size, int blockSize, MemoryAllocator allocator, MemoryLookupTable table) {
        if (blockSize > size) {
            blockSize = size;
        }
        this.size = size;
        this.blockSize = blockSize;
        this.allocator = allocator;
        this.table = table;
    }

    @Override
    public MemoryAllocator allocator() {
        return allocator;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public int blockSize() {
        return blockSize;
    }

    @Override
    public void defaultByte(byte value) {
        this.defaultByte = value;
    }

    public void useMemoryPool(MemoryPool pool) {
        this.pool = pool;
    }

    @Override
    public byte get(int index) {
        checkIndex(index);
        Memory memory = findMemoryAt(index, false);

        if (memory == null) {
            return defaultByte;
        } else {
            return memory.get(valueIndex(index));
        }
    }

    @Override
    public void put(int index, int value) {
        put(index, (byte) value);
    }

    @Override
    public void put(int index, byte[] value) {
        for (int i = 0; i < value.length; i++) {
            put(index + i, value[i]);
        }
    }

    @Override
    public void put(int index, byte value) {
        checkIndex(index);
        Memory memory = findMemoryAt(index, true);
        memory.put(valueIndex(index), value);
    }

    @Override
    public byte[] array(int offset, int length) {
        checkIndex(offset);
        checkIndex(length);
        try {
            byte[] content = new byte[(length + 1) - offset];
            for (int i = 0; i < content.length; i++) {
                content[i] = get(offset + i);
            }
            return content;
        } catch (NegativeArraySizeException exception) {
            throw new NegativeArraySizeException("Array length should be greater than offset"); 
        }
    }

    @Override
    public void writeTo(ByteBuffer buffer, int offset, int length) {
        checkIndex(offset);
        checkIndex(offset + length);
        if (offset == length) {
            throw new IllegalArgumentException("offset == length");
        }
        if (offset > length) {
            throw new IllegalArgumentException("offset > length");
        }
        if (buffer.remaining() >= length) {
            while (offset <= length) {
                buffer.put(get(offset++));
            }
        } else {
            throw new IllegalArgumentException("There is not enough space for write in the provided buffer.");
        }
    }

    @Override
    public void writeTo(OutputStream stream, int offset, int length) {
        checkIndex(offset);
        checkIndex(offset + length);
        if (offset == length) {
            throw new IllegalArgumentException("offset == length");
        }
        if (offset > length) {
            throw new IllegalArgumentException("offset > length");
        }
        try {
            while (offset <= length) {
                stream.write(get(offset++));
            }
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("index (" + index + ") is out of bounds. (0 - " + size + ")");
        }
    }

    private int blockIndex(int index) {
        if (index < blockSize) {
            return 0;
        } else {
            return index / blockSize;
        }
    }

    private int valueIndex(int index) {
        if (index < blockSize) {
            return index;
        } else {
            return index % blockSize;
        }
    }

    private Memory findMemoryAt(int index, boolean create) {
        Memory memory = cache.get(index);

        if (memory == null) {
            int blockIndex = blockIndex(index);

            memory = table.getAt(blockIndex);
            if (memory == null) {
                if (create && allocator != null) {
                    if (pool == null) {
                        memory = table.setAt(blockIndex, allocator.allocate(blockSize));
                    } else {
                        memory = table.setAt(blockIndex, pool.get(allocator));
                    }
                }
            }

            if (memory != null) {
                cache = CachedMemory.of(memory, index, blockSize);
            }
        }

        return memory;
    }

    @Override
    public void close() {
        try {
            table.offer(pool);
            table.close();
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        } finally {
            cache = CachedMemory.NULL;
        }
    }

    public record CachedMemory(Memory memory, int min, int max) {
        static CachedMemory NULL = of(null, -1, -1);

        static CachedMemory of(Memory memory, int ind, int bsz) {
            int min = ind * bsz;
            int max = min + bsz;
            return new CachedMemory(memory, min, max);
        }

        public Memory get(int ind) {
            if (ind >= min && ind < max) {
                return memory;
            }
            return null;
        }
    }

    @Override
    public String toString() {
        return "Streamio(pool=" + pool + ")";
    }

}
