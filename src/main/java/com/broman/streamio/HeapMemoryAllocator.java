package com.broman.streamio;

import java.nio.ByteBuffer;

/**
 * @author Brayan Roman
 * @since 1.0.0
 */
public class HeapMemoryAllocator implements MemoryAllocator {

    @Override
    public Memory allocate() {
        return new HeapMemory(512);
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
