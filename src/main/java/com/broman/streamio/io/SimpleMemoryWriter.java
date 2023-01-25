package com.broman.streamio.io;

import com.broman.streamio.IStreamio;
import com.broman.streamio.serialization.ObjectSerializer;

public class SimpleMemoryWriter extends AbstractMemoryManagement implements MemoryWriter {

    private IStreamio streamio;

    public SimpleMemoryWriter(IStreamio streamio) {
        this(streamio, new MemoryIndex(0), MemoryEncoding.BigEndian);
    }

    public SimpleMemoryWriter(IStreamio streamio, MemoryEncoding endianess) {
        this(streamio, new MemoryIndex(0), endianess);
    }

    public SimpleMemoryWriter(IStreamio streamio, MemoryIndex index, MemoryEncoding endianess) {
        super(index, endianess);
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
        writeShort(index, value);
    }

    @Override
    public void writeShort(MemoryIndex index, short value) {
        if (encoding == MemoryEncoding.BigEndian) {
            streamio.put(index.inc(), (byte) (value >> 8));
            streamio.put(index.inc(), (byte) value);
            return;
        }
        if (encoding == MemoryEncoding.LittleEndian) {
            streamio.put(index.inc(), (byte) value);
            streamio.put(index.inc(), (byte) (value >> 8));
            return;
        }
        writeUnsignedVarInt(index, value); 
    }

    @Override
    public void writeMedium(int value) {
        writeMedium(index, value);
    }

    @Override
    public void writeMedium(MemoryIndex index, int value) {
        if (encoding == MemoryEncoding.BigEndian) {
            streamio.put(index.inc(), (byte) (value >> 16));
            streamio.put(index.inc(), (byte) (value >> 8));
            streamio.put(index.inc(), (byte) value);
            return;
        }
        if (encoding == MemoryEncoding.LittleEndian) {
            streamio.put(index.inc(), (byte) value);
            streamio.put(index.inc(), (byte) (value >> 8));
            streamio.put(index.inc(), (byte) (value >> 16));
            return;
        }
        writeUnsignedVarInt(index, value);   
    }

    @Override
    public void writeInt(int value) {
        writeInt(index, value);
    }

    @Override
    public void writeInt(MemoryIndex index, int value) {
        if (encoding == MemoryEncoding.BigEndian) {
            streamio.put(index.inc(), (byte) (value >> 24));
            streamio.put(index.inc(), (byte) (value >> 16));
            streamio.put(index.inc(), (byte) (value >> 8 ));
            streamio.put(index.inc(), (byte) (value      ));
            return;
        }
        if (encoding == MemoryEncoding.LittleEndian) {
            streamio.put(index.inc(), (byte) (value      ));
            streamio.put(index.inc(), (byte) (value >> 8 ));
            streamio.put(index.inc(), (byte) (value >> 16));
            streamio.put(index.inc(), (byte) (value >> 24));
            return;
        }
        writeUnsignedVarInt(value);
    }

    @Override
    public void writeLong(long value) {
        writeLong(index, value);
    }

    @Override
    public void writeLong(MemoryIndex index, long value) {
        if (encoding == MemoryEncoding.BigEndian) {
            streamio.put(index.inc(), (byte) (value >> 56));
            streamio.put(index.inc(), (byte) (value >> 48));
            streamio.put(index.inc(), (byte) (value >> 40));
            streamio.put(index.inc(), (byte) (value >> 32));
            streamio.put(index.inc(), (byte) (value >> 24));
            streamio.put(index.inc(), (byte) (value >> 16));
            streamio.put(index.inc(), (byte) (value >> 8 ));
            streamio.put(index.inc(), (byte) (value      ));
            return;
        }
        if (encoding == MemoryEncoding.LittleEndian) {
            streamio.put(index.inc(), (byte) (value      ));
            streamio.put(index.inc(), (byte) (value >> 8 ));
            streamio.put(index.inc(), (byte) (value >> 16));
            streamio.put(index.inc(), (byte) (value >> 24));
            streamio.put(index.inc(), (byte) (value >> 32));
            streamio.put(index.inc(), (byte) (value >> 40));
            streamio.put(index.inc(), (byte) (value >> 48));
            streamio.put(index.inc(), (byte) (value >> 56));
            return;
        }
        writeUnsignedVarLong(value);
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
    public void writeDouble(double value) {
        writeDouble(index, value);
    }

    @Override
    public void writeDouble(MemoryIndex index, double value) {
        writeLong(index, Double.doubleToLongBits(value));
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
    public void writeSignedVarInt(int value) {
        writeSignedVarInt(index, value);
    }

    @Override
    public void writeSignedVarInt(MemoryIndex index, int value) {
        writeUnsignedVarInt(index, (value << 1) ^ (value >> 31));
    }

    @Override
    public void writeUnsignedVarInt(int value) {
        writeUnsignedVarInt(index, value);
    }

    @Override
    public void writeUnsignedVarInt(MemoryIndex index, int value) {
        int temp;
        do {
            temp = value & 0x7F;
            value >>>= 7;
            streamio.put(index.inc(), (byte) (temp + (value == 0 ? 0 : 0x80)));
        } while (value != 0);
    }

    @Override
    public void writeSignedVarLong(long value) {
        writeSignedVarLong(index, value);
    }

    @Override
    public void writeSignedVarLong(MemoryIndex index, long value) {
        writeUnsignedVarLong(index, (value << 1) ^ (value >> 63));
    }

    @Override
    public void writeUnsignedVarLong(long value) {
        long temp;
        do {
            temp = value & 0x7F;
            value >>>= 7;
            streamio.put(index.inc(), (byte) (temp + (value == 0 ? 0 : 0x80)));
        } while (value != 0);
    }

    @Override
    public void writeUnsignedVarLong(MemoryIndex index, long value) {
        long temp;
        do {
            temp = value & 0x7F;
            value >>>= 7;
            streamio.put(index.inc(), (byte) (temp + (value == 0 ? 0 : 0x80)));
        } while (value != 0);
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

    public MemoryWriter writer(MemoryEncoding endianess) {
        return new SimpleMemoryWriter(streamio, index, endianess);
    }

    public SimpleMemoryWriter writer(MemoryIndex index) {
        return new SimpleMemoryWriter(streamio, index, encoding);
    }
    
}
