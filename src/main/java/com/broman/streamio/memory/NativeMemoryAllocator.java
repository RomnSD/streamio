package com.broman.streamio.memory;

import java.nio.ByteBuffer;

import com.broman.streamio.Memory;
import com.broman.streamio.MemoryAllocator;

/**
 * @author Brayan Roman
 * @since  1.0.0
 */
public class NativeMemoryAllocator implements MemoryAllocator {

    @Override
    public MemoryType getType() {
        return MemoryType.NATIVE;
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
