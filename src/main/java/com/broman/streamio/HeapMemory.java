package com.broman.streamio;

/**
 * @author Brayan Roman
 * @since 1.0.0
 */
public class HeapMemory implements Memory {

    private byte[] bytes;

    public HeapMemory(int size) {
        if (size < 1) {
            size = 0;
        }
        this.bytes = new byte[size];
    }

    public HeapMemory(byte[] bytes) {
        this.bytes = bytes;
    }

    @Override
    public int size() {
        return bytes.length;
    }

    @Override
    public byte get(int index) {
        return bytes[index];
    }

    @Override
    public void put(int index, byte value) {
        bytes[index] = value;
    }

    @Override
    public void close() {
        this.bytes = new byte[0];
    }

}
