package com.broman.streamio;

import java.lang.reflect.Field;
import java.nio.Buffer;
import java.nio.ByteBuffer;

import sun.misc.Unsafe;

/**
 * @author Brayan Roman
 * @since  1.0.0
 */
public class NativeMemory implements Memory {

    private final static Unsafe UNSAFE;
    private final static long ADDRESS_FIELD;

    static {
        try {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            UNSAFE = (Unsafe) field.get(null);
            ADDRESS_FIELD = UNSAFE.objectFieldOffset(Buffer.class.getDeclaredField("address"));
        }
        catch (IllegalAccessException | NoSuchFieldException | SecurityException exception) {
            throw new RuntimeException(exception);
        }
    }

    private int size;
    private long address;
    private Object attachment;

    public NativeMemory(ByteBuffer buffer) {
        if (buffer.isDirect()) {
            size = buffer.capacity();
            address = UNSAFE.getLong(buffer, ADDRESS_FIELD);
            attachment = buffer;
        }
        else {
            throw new IllegalArgumentException("buffer is not direct");
        }
    }

    public NativeMemory(int size) {
        this.size = size;
        this.address = UNSAFE.allocateMemory(size);
        this.attachment = null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public byte get(int index) {
        return UNSAFE.getByte(address + index);
    }

    @Override
    public void put(int index, byte value) {
        UNSAFE.putByte(address + index, value);
    }

    @Override
    public void close() {
        if (attachment == null && address != 0) {
            UNSAFE.freeMemory(address);
        }
        size = 0;
        address = 0;
        attachment = null;
    }

}