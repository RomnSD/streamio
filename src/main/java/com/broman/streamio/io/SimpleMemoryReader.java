package com.broman.streamio.io;

import com.broman.streamio.IStreamio;
import com.broman.streamio.io.encoding.IntEncoding;
import com.broman.streamio.serialization.ObjectSerializer;

/**
 * <p>
 * A memory reader that reads from IStreamio.</p>
 * 
 * @author Brayan Roman
 * @since  1.0
 */
public class SimpleMemoryReader extends AbstractMemoryManagement implements MemoryReader {
    private IStreamio streamio;

    public SimpleMemoryReader(IStreamio streamio) {
        this(streamio, new MemoryIndex(0), MemoryEncoding.BIG_ENDIAN);
    }

    public SimpleMemoryReader(IStreamio streamio, IntEncoding encoding) {
        this(streamio, new MemoryIndex(0), encoding);
    }

    public SimpleMemoryReader(IStreamio streamio, MemoryIndex index, IntEncoding encoding) {
        super(index, encoding);
        if (streamio == null) {
            throw new NullPointerException("Streamio cannot be null");
        }
        this.streamio = streamio;
    }

    @Override
    public byte readByte() {
        return streamio.get(index.inc());
    }

    @Override
    public byte readByte(MemoryIndex index) {
        return streamio.get(index.inc());
    }

    @Override
    public short readShort() {
        return encoding.getSInt16(streamio, index);
    }

    @Override
    public short readShort(MemoryIndex index) {
        return encoding.getSInt16(streamio, index);
    }

    public short readUShort() {
        return encoding.getUInt16(streamio, index);
    }

    public short readUShort(MemoryIndex index) {
        return encoding.getUInt16(streamio, index);
    }

    @Override
    public int readMedium() {
        return encoding.getSInt24(streamio, index);
    }

    @Override
    public int readMedium(MemoryIndex index) {
        return encoding.getSInt24(streamio, index);
    }

    public int readUMedium() {
        return encoding.getUInt24(streamio, index);
    }

    public int readUMedium(MemoryIndex index) {
        return encoding.getUInt24(streamio, index);
    }

    @Override
    public int readInt() {
        return encoding.getSInt32(streamio, index);
    }

    @Override
    public int readInt(MemoryIndex index) {
        return encoding.getSInt32(streamio, index);
    }

    public int readUInt() {
        return encoding.getUInt32(streamio, index);
    }

    public int readUInt(MemoryIndex index) {
        return encoding.getUInt32(streamio, index);
    }

    @Override
    public long readLong() {
        return encoding.getSInt64(streamio, index);
    }

    @Override
    public long readLong(MemoryIndex index) {
        return encoding.getSInt64(streamio, index);
    }

    @Override
    public long readULong() {
        return encoding.getUInt64(streamio, index);
    }

    @Override
    public long readULong(MemoryIndex index) {
        return encoding.getUInt64(streamio, index);
    }

    @Override
    public float readFloat() {
        return readFloat(index);
    }

    @Override
    public float readFloat(MemoryIndex index) {
        return Float.intBitsToFloat(readInt(index));
    }

    @Override
    public float readUFloat() {
        return readUFloat(index);
    }

    @Override
    public float readUFloat(MemoryIndex index) {
        return Float.intBitsToFloat(readUInt(index));
    }

    @Override
    public double readDouble() {
        return readDouble(index);
    }

    @Override
    public double readDouble(MemoryIndex index) {
        return Double.longBitsToDouble(readLong(index));
    }

    @Override
    public double readUDouble() {
        return readUDouble(index);
    }

    @Override
    public double readUDouble(MemoryIndex index) {
        return Double.longBitsToDouble(readULong(index));
    }

    @Override
    public char readChar() {
        return readChar(index);
    }

    @Override
    public char readChar(MemoryIndex index) {
        return (char) readShort(index);
    }

    @Override
    public boolean readBoolean() {
        return readBoolean(index);
    }

    @Override
    public boolean readBoolean(MemoryIndex index) {
        return streamio.get(index.inc()) == 1;
    }

    @Override
    public String readCharString() {
        return readCharString(index);
    }

    @Override
    public String readCharString(MemoryIndex index) {
        char[] array = new char[readInt(index)];
        for (int i = 0; i < array.length; i++) {
            array[i] = readChar(index);
        }
        return new String(array);
    }

    @Override
    public String readByteString() {
        return readByteString(index);
    }

    @Override
    public String readByteString(MemoryIndex index) {
        byte[] array = new byte[readInt(index)];
        for (int i = 0; i < array.length; i++) {
            array[i] = readByte(index);
        }
        return new String(array);
    }

    @Override
    public byte[] readBytes(int length) {
        return readBytes(index, length);
    }

    @Override
    public byte[] readBytes(MemoryIndex index, int length) {
        if (length < 0) {
            throw new IllegalArgumentException("length must be >= 0");
        }
        byte[] bytes = new byte[length];
        for (int i = 0; i < length; i++) {
            bytes[i] = streamio.get(index.inc());
        }
        return bytes;
    }

    @Override
    public<T> T readObject(Class<T> type) {
        java.util.Objects.requireNonNull(type);
        java.util.Objects.requireNonNull(serializers);
        return readObject(serializers.find(type));
    }

    @Override
    public<T> T readObject(ObjectSerializer<T> serializer) {
        java.util.Objects.requireNonNull(serializer);
        return serializer.unserialize(this);
    }

    public SimpleMemoryReader reader(IntEncoding encoding) {
        return new SimpleMemoryReader(streamio, index, encoding);
    }

    public SimpleMemoryReader reader(MemoryIndex index) {
        return new SimpleMemoryReader(streamio, index, encoding);
    }

}
