package com.broman.streamio;

import java.nio.ByteBuffer;

/**
 * @author Brayan Roman
 * @since 1.0.0
 */
public class NativeMemoryAllocator implements MemoryAllocator {

    @Override
    public Memory allocate() {
        return new NativeMemory(512);
    }

    @Override
    public Memory allocate(int size) {
        return new NativeMemory(size);
    }

    @Override
    public Memory allocate(byte[] array) {
        throw new RuntimeException("Byte arrays cannot be allocated as native memory.");
    }

    @Override
    public Memory allocate(ByteBuffer buffer) {
        return new NativeMemory(buffer);
    }

}
