package com.broman.streamio.memory;

import java.lang.reflect.Field;
import java.nio.Buffer;
import java.nio.ByteBuffer;

import com.broman.streamio.Memory;

import sun.misc.Unsafe;

/**
 * @author Brayan Roman
 * @since  1.0.0
 */
public class NativeMemory extends Memory {

    private final static Unsafe UNSAFE;
    private final static long ADDRESS_FIELD;

    static {
        try {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            UNSAFE = (Unsafe) field.get(null);
            ADDRESS_FIELD = UNSAFE.objectFieldOffset(Buffer.class.getDeclaredField("address"));
        } catch (IllegalAccessException | NoSuchFieldException | SecurityException exception) {
            throw new RuntimeException(exception);
        }
    }

    private static long getAddress(Buffer buffer) {
        return UNSAFE.getLong(buffer, ADDRESS_FIELD);
    }

    private int size;
    private long address;
    private Object attachment;

    public NativeMemory(ByteBuffer buffer) {
        if (buffer == null) {
            throw new IllegalArgumentException("Buffer cannot be null");
        }
        if (buffer.isDirect()) {
            size = buffer.capacity();
            address = getAddress(buffer);
            attachment = buffer; // Keep a reference to the buffer so it doesn't get garbage collected
        }
        else {
            throw new IllegalArgumentException("buffer is not direct");
        }
    }

    public NativeMemory(int size) {
        if (size < 1) {
            throw new IllegalArgumentException("Size must be greater than 0");
        }
        this.size = size;
        this.address = UNSAFE.allocateMemory(size);
        this.attachment = null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public MemoryType getType() {
        return MemoryType.NATIVE;
    }

    @Override
    public byte get(int index) {
        if (closed()) {
            throw new IllegalStateException("Memory is closed");
        }
        return UNSAFE.getByte(address + index);
    }

    @Override
    public void put(int index, byte value) {
        if (closed()) {
            throw new IllegalStateException("Memory is closed");
        }
        UNSAFE.putByte(address + index, value);
    }

    @Override
    public void close() {
        super.close();

        if (attachment == null) {
            if (address != 0) {
                UNSAFE.freeMemory(address);
            }
        }
        
        size = 0;
        address = 0;
        attachment = null;
    }

}
