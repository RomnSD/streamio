package com.broman.streamio;

import java.nio.ByteBuffer;

/**
 * @author Brayan Roman
 * @since  1.0.0
 */
public class Streamio implements IStreamio, AutoCloseable {

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
        return new Streamio(bytes.length, bytes.length, null, new StaticMemoryLookupTable(new Memory[]{
            new HeapMemory(bytes)
        }));
    }

    public static Streamio wrap(ByteBuffer buffer) {
        return new Streamio(buffer.capacity(), buffer.capacity(), null, new StaticMemoryLookupTable(new Memory[]{
            buffer.isDirect() ? new NativeMemory(buffer) : new HeapMemory(buffer.array())
        }));
    }

    private final int size;
    private final int blockSize;
    private final MemoryAllocator allocator;
    private final MemoryLookupTable table;

    private byte defaultByte = 0;
    private CachedMemory cache = CachedMemory.NULL;

    Streamio(int size, int blockSize, MemoryAllocator allocator, MemoryLookupTable table) {
        this.size = size;
        this.blockSize = blockSize;
        this.allocator = allocator;
        this.table = table;
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

    @Override
    public byte get(int index) {
        checkIndex(index);
        Memory memory = findMemoryAt(blockIndex(index), false);

        if (memory == null) {
            return defaultByte;
        } else {
            return memory.get(valueIndex(index));
        }
    }

    @Override
    public void put(int index, byte value) {
        checkIndex(index);
        Memory memory = findMemoryAt(blockIndex(index), true);
        memory.put(valueIndex(index), value);
    }

    @Override
    public byte[] array(int offset, int length) {
        checkIndex(offset);
        checkIndex(length);
        try {
            byte[] content = new byte[length - offset];
            for (int i = 0; i < content.length; i++) {
                content[i] = get(offset + i);
            }
            return content;
        } catch (NegativeArraySizeException exception) {
            throw new NegativeArraySizeException("Array length should be greater than offset"); 
        }
    }

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
            while (offset != length) {
                buffer.put(get(offset++));
            }
        } else {
            throw new IllegalArgumentException("There is not enough space for write in the provided buffer.");
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
            memory = table.getAt(index);

            if (memory == null) {
                if (create && allocator != null) {
                    memory = table.setAt(index, allocator.allocate(blockSize));
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

}
