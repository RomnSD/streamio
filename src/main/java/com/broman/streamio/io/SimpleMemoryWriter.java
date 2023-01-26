package com.broman.streamio.io;

import com.broman.streamio.IStreamio;
import com.broman.streamio.io.encoding.IntEncoding;
import com.broman.streamio.serialization.ObjectSerializer;

public class SimpleMemoryWriter extends AbstractMemoryManagement implements MemoryWriter {

    private IStreamio streamio;

    public SimpleMemoryWriter(IStreamio streamio) {
        this(streamio, new MemoryIndex(0), MemoryEncoding.BIG_ENDIAN);
    }

    public SimpleMemoryWriter(IStreamio streamio, IntEncoding encoding) {
        this(streamio, new MemoryIndex(0), encoding);
    }

    public SimpleMemoryWriter(IStreamio streamio, MemoryIndex index, IntEncoding encoding) {
        super(index, encoding);
        if (streamio == null) {
            throw new NullPointerException("Streamio cannot be null");
        }
        this.streamio = streamio;
    }

    @Override
    public void writeByte(byte value) {
        streamio.put(index.inc(), value);
    }

    @Override
    public void writeByte(MemoryIndex index, byte value) {
        streamio.put(index.inc(), value);
    }

    @Override
    public void writeShort(short value) {
        encoding.putSInt16(value, streamio, index);
    }

    @Override
    public void writeShort(MemoryIndex index, short value) {
        encoding.putSInt16(value, streamio, index);
    }

    @Override
    public void writeUShort(short value) {
        encoding.putUInt16(value, streamio, index);
    }

    @Override
    public void writeUShort(MemoryIndex index, short value) {
        encoding.putUInt16(value, streamio, index);
    }

    @Override
    public void writeMedium(int value) {
        encoding.putSInt24(value, streamio, index);
    }

    @Override
    public void writeMedium(MemoryIndex index, int value) {
        encoding.putSInt24(value, streamio, index); 
    }

    @Override
    public void writeUMedium(int value) {
        encoding.putUInt24(value, streamio, index);
    }

    @Override
    public void writeUMedium(MemoryIndex index, int value) {
        encoding.putUInt24(value, streamio, index);
    }

    @Override
    public void writeInt(int value) {
        encoding.putSInt32(value, streamio, index);
    }

    @Override
    public void writeInt(MemoryIndex index, int value) {
        encoding.putSInt32(value, streamio, index);
    }

    public void writeUInt(int value) {
        encoding.putUInt32(value, streamio, index);
    }

    public void writeUInt(MemoryIndex index, int value) {
        encoding.putUInt32(value, streamio, index);
    }

    @Override
    public void writeLong(long value) {
        encoding.putSInt64(value, streamio, index);
    }

    @Override
    public void writeLong(MemoryIndex index, long value) {
        encoding.putSInt64(value, streamio, index);
    }

    @Override
    public void writeULong(long value) {
        encoding.putUInt64(value, streamio, index);
    }

    @Override
    public void writeULong(MemoryIndex index, long value) {
        encoding.putUInt64(value, streamio, index);
    }

    @Override
    public void writeFloat(float value) {
        writeFloat(index, value);
    }

    @Override
    public void writeFloat(MemoryIndex index, float value) {
        writeInt(index, Float.floatToIntBits(value));
    }

    @Override
    public void writeUFloat(float value) {
        writeUFloat(index, value);
    }

    @Override
    public void writeUFloat(MemoryIndex index, float value) {
        writeUInt(index, Float.floatToIntBits(value));
    }

    @Override
    public void writeDouble(double value) {
        writeDouble(index, value);
    }

    @Override
    public void writeDouble(MemoryIndex index, double value) {
        writeLong(index, Double.doubleToLongBits(value));
    }

    @Override
    public void writeUDouble(double value) {
        writeUDouble(index, value);
    }

    @Override
    public void writeUDouble(MemoryIndex index, double value) {
        writeULong(index, Double.doubleToLongBits(value));
    }

    @Override
    public void writeChar(char value) {
        writeChar(index, value);
    }

    @Override
    public void writeChar(MemoryIndex index, char value) {
        writeShort(index, (short) value);
    }

    @Override
    public void writeBoolean(boolean value) {
        writeBoolean(index, value);
    }

    @Override
    public void writeBoolean(MemoryIndex index, boolean value) {
        streamio.put(index.inc(), (byte) (value ? 1 : 0));
    }

    @Override
    public void writeCharString(String value) {
        writeCharString(index, value);
    }

    @Override
    public void writeCharString(MemoryIndex index, String value) {
        int l = value.length();
        writeInt(index, l);
        for (int i = 0; i < l; i++) {
            writeChar(index, value.charAt(i));
        }
    }

    @Override
    public void writeByteString(String value) {
        writeByteString(index, value);
    }

    @Override
    public void writeByteString(MemoryIndex index, String value) {
        int l = value.length();
        writeInt(index, l);
        for (int i = 0; i < l; i++) {
            writeByte(index, (byte) value.charAt(i));
        }
    }

    @Override
    public void writeBytes(byte[] value) {
        writeBytes(index, value);
    }

    @Override
    public void writeBytes(MemoryIndex index, byte[] value) {
        for (byte b : value) {
            writeByte(index, b);
        }
    }

    @Override
    public<T> void writeObject(T object) {
        writeObject(object, object == null ? null : java.util.Objects.requireNonNull(serializers).find(object.getClass()));
    }

    @Override
    public<T> void writeObject(T object, ObjectSerializer<T> serializer) {
        java.util.Objects.requireNonNull(object);
        java.util.Objects.requireNonNull(serializer);
        serializer.serialize(object, this);
    } 

    public MemoryWriter writer(IntEncoding encoding) {
        return new SimpleMemoryWriter(streamio, index, encoding);
    }

    public SimpleMemoryWriter writer(MemoryIndex index) {
        return new SimpleMemoryWriter(streamio, index, encoding);
    }
    
}
