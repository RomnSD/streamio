package com.broman.streamio.memory;

import com.broman.streamio.Memory;

/**
 * @author Brayan Roman
 * @since  1.0.0
 */
public class HeapMemory extends Memory {

    private byte[] bytes;

    public HeapMemory(int size) {
        if (size < 1) {
            throw new IllegalArgumentException("Size must be greater than 0");
        }
        this.bytes = new byte[size];
    }

    public HeapMemory(byte[] bytes) {
        if (bytes == null) {
            throw new IllegalArgumentException("Bytes cannot be null");
        }
        this.bytes = bytes;
    }

    @Override
    public MemoryType getType() {
        return MemoryType.HEAP;
    }

    @Override
    public int size() {
        if (bytes == null) {
            return 0;
        }
        return bytes.length;
    }

    @Override
    public byte get(int index) {
        if (closed()) {
            throw new IllegalStateException("Memory is closed");
        }
        return bytes[index];
    }

    @Override
    public void put(int index, byte value) {
        if (closed()) {
            throw new IllegalStateException("Memory is closed");
        }
        bytes[index] = value;
    }

    @Override
    public void close() {
        super.close();
        bytes = null;
    }

}
