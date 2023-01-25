package com.broman.streamio.memory;

import java.nio.ByteBuffer;

import com.broman.streamio.Memory;
import com.broman.streamio.MemoryAllocator;

/**
 * @author Brayan Roman
 * @since  1.0.0
 */
public class HeapMemoryAllocator implements MemoryAllocator {

    @Override
    public MemoryType getType() {
        return MemoryType.HEAP;
    }

    @Override
    public Memory allocate(int size) {
        return new HeapMemory(size);
    }

    @Override
    public Memory allocate(byte[] array) {
        return new HeapMemory(array);
    }

    @Override
    public Memory allocate(ByteBuffer buffer) {
        return new HeapMemory(buffer.array());
    }

}
