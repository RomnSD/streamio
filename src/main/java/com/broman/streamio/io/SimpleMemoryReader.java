package com.broman.streamio.io;

import com.broman.streamio.IStreamio;
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
        this(streamio, new MemoryIndex(0), MemoryEncoding.BigEndian);
    }

    public SimpleMemoryReader(IStreamio streamio, MemoryEncoding endianess) {
        this(streamio, new MemoryIndex(0), endianess);
    }

    public SimpleMemoryReader(IStreamio streamio, MemoryIndex index, MemoryEncoding endianess) {
        super(index, endianess);
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
        return readShort(index);
    }

    @Override
    public short readShort(MemoryIndex index) {
        if (encoding == MemoryEncoding.BigEndian) {
            return (short) (
                (streamio.get(index.inc()) & 0xFF) << 8 |
                (streamio.get(index.inc()) & 0xFF)
            );
        }
        if (encoding == MemoryEncoding.LittleEndian) {
            return (short) (
                (streamio.get(index.inc()) & 0xFF) |
                (streamio.get(index.inc()) & 0xFF) << 8
            );
        }
        return (short) readUnsignedVarInt(index);
    }

    @Override
    public int readMedium() {
        return readMedium(index);
    }

    @Override
    public int readMedium(MemoryIndex index) {
        if (encoding == MemoryEncoding.BigEndian) {
            return (streamio.get(index.inc()) & 0xFF) << 16 | 
                   (streamio.get(index.inc()) & 0xFF) << 8  |
                   (streamio.get(index.inc()) & 0xFF);
        }
        if (encoding == MemoryEncoding.LittleEndian) {
            return (streamio.get(index.inc()) & 0xFF)      |
                   (streamio.get(index.inc()) & 0xFF) << 8 |
                   (streamio.get(index.inc()) & 0xFF) << 16;
        }
        return readUnsignedVarInt(index);
    }

    @Override
    public int readInt() {
        return readInt(index);
    }

    @Override
    public int readInt(MemoryIndex index) {
        if (encoding == MemoryEncoding.BigEndian) {
            return (streamio.get(index.inc()) & 0xFF) << 24 | 
                   (streamio.get(index.inc()) & 0xFF) << 16 | 
                   (streamio.get(index.inc()) & 0xFF) << 8  | 
                   (streamio.get(index.inc()) & 0xFF)       ;
        }
        if (encoding == MemoryEncoding.LittleEndian) {
            return (streamio.get(index.inc()) & 0xFF)       | 
                   (streamio.get(index.inc()) & 0xFF) << 8  |
                   (streamio.get(index.inc()) & 0xFF) << 16 |
                   (streamio.get(index.inc()) & 0xFF) << 24 ;
        }
        return readUnsignedVarInt(index);
    }

    @Override
    public long readLong() {
        return readLong(index);
    }

    @Override
    public long readLong(MemoryIndex index) {
        if (encoding == MemoryEncoding.BigEndian) {
            return ((long) (streamio.get(index.inc()) & 0xFF) << 56) |
                   ((long) (streamio.get(index.inc()) & 0xFF) << 48) | 
                   ((long) (streamio.get(index.inc()) & 0xFF) << 40) | 
                   ((long) (streamio.get(index.inc()) & 0xFF) << 32) | 
                   ((long) (streamio.get(index.inc()) & 0xFF) << 24) | 
                   ((long) (streamio.get(index.inc()) & 0xFF) << 16) | 
                   ((long) (streamio.get(index.inc()) & 0xFF) << 8 ) | 
                   ((long) (streamio.get(index.inc()) & 0xFF)      );
        }
        if (encoding == MemoryEncoding.LittleEndian) {
            return ((long) (streamio.get(index.inc()) & 0xFF)      ) |
                   ((long) (streamio.get(index.inc()) & 0xFF) << 8 ) |
                   ((long) (streamio.get(index.inc()) & 0xFF) << 16) |
                   ((long) (streamio.get(index.inc()) & 0xFF) << 24) |
                   ((long) (streamio.get(index.inc()) & 0xFF) << 32) |
                   ((long) (streamio.get(index.inc()) & 0xFF) << 40) |
                   ((long) (streamio.get(index.inc()) & 0xFF) << 48) |
                   ((long) (streamio.get(index.inc()) & 0xFF) << 56);
        }
        return readUnsignedVarLong(index);
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
    public double readDouble() {
        return readDouble(index);
    }

    @Override
    public double readDouble(MemoryIndex index) {
        return Double.longBitsToDouble(readLong(index));
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

    public int readSignedVarInt() {
        return readSignedVarInt(index);
    }

    public int readSignedVarInt(MemoryIndex index) {
        int r = readUnsignedVarInt(index);
        return (r >>> 1) ^ -(r & 1);
    }

    @Override
    public int readUnsignedVarInt() {
        return readUnsignedVarInt(index);
    }

    @Override
    public int readUnsignedVarInt(MemoryIndex index) {
        int r = 0;
        int s = 0;
        int b;
        
        do {
            b = streamio.get(index.inc());
            r = r | ((b & 0x7F) << (s++ * 7));
            
            checkVarLength(s, 5);
        } while ((b & 0x80) == 0x80);

        return r;
    }

    @Override
    public long readSignedVarLong() {
        return readSignedVarLong(index);
    }

    @Override
    public long readSignedVarLong(MemoryIndex index) {
        long r = readUnsignedVarLong(index);
        return (r >>> 1) ^ -(r & 1);
    }

    @Override
    public long readUnsignedVarLong() {
        return readUnsignedVarLong(index);
    }

    @Override
    public long readUnsignedVarLong(MemoryIndex index) {
        long r = 0;
        long s = 0;
        long b;

        do {
            b = streamio.get(index.inc());
            r = r | ((b & 0x7F) << (s++ * 7));
            
            checkVarLength(s, 10);
        } while ((b & 0x80) == 0x80);

        return r;
    }

    private void checkVarLength(long value, int limit) {
        if (value > limit) {
            throw new RuntimeException("VarInt too big. Expected " + limit + " but got " + value);
        }
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

    public SimpleMemoryReader reader(MemoryEncoding endianess) {
        return new SimpleMemoryReader(streamio, index, endianess);
    }

    public SimpleMemoryReader reader(MemoryIndex index) {
        return new SimpleMemoryReader(streamio, index, encoding);
    }

}
