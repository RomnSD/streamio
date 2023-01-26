package com.broman.streamio.io.encoding;

import com.broman.streamio.IStreamio;
import com.broman.streamio.io.MemoryIndex;

/**
 * @author Brayan Roman
 * @since  1.0.0
 */
public class VarInt implements IntEncoding {

    @Override
    public void putSInt16(short value, IStreamio streamio, MemoryIndex index) {
        writeVarInt(zigzagEncode((int) value), streamio, index);
    }

    @Override
    public short getSInt16(IStreamio streamio, MemoryIndex index) {
        return (short) zigzagDecode(readVarInt(streamio, index));
    }

    @Override
    public void putUInt16(short value, IStreamio streamio, MemoryIndex index) {
        writeVarInt(value, streamio, index);
    }

    @Override
    public short getUInt16(IStreamio streamio, MemoryIndex index) {
        return (short) readVarInt(streamio, index);
    }

    @Override
    public void putSInt24(int value, IStreamio streamio, MemoryIndex index) {
        writeVarInt(zigzagEncode(value), streamio, index);
    }

    @Override
    public int getSInt24(IStreamio streamio, MemoryIndex index) {
        return zigzagDecode(readVarInt(streamio, index));
    }

    @Override
    public void putUInt24(int value, IStreamio streamio, MemoryIndex index) {
        writeVarInt(value, streamio, index);
    }

    @Override
    public int getUInt24(IStreamio streamio, MemoryIndex index) {
        return readVarInt(streamio, index);
    }

    @Override
    public void putSInt32(int value, IStreamio streamio, MemoryIndex index) {
        writeVarInt(zigzagEncode(value), streamio, index);
    }

    @Override
    public int getSInt32(IStreamio streamio, MemoryIndex index) {
        return zigzagDecode(readVarInt(streamio, index));
    }

    @Override
    public void putUInt32(int value, IStreamio streamio, MemoryIndex index) {
        writeVarInt(value, streamio, index);
    }

    @Override
    public int getUInt32(IStreamio streamio, MemoryIndex index) {
        return readVarInt(streamio, index);
    }

    @Override
    public void putSInt64(long value, IStreamio streamio, MemoryIndex index) {
        writeVarLong(zigzagEncode(value), streamio, index);
    }

    @Override
    public long getSInt64(IStreamio streamio, MemoryIndex index) {
        return zigzagDecode(readVarLong(streamio, index));
    }

    @Override
    public void putUInt64(long value, IStreamio streamio, MemoryIndex index) {
        writeVarLong(value, streamio, index);
    }

    @Override
    public long getUInt64(IStreamio streamio, MemoryIndex index) {
        return readVarLong(streamio, index);
    }

    public int readVarInt(IStreamio streamio, MemoryIndex index) {
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

    public long readVarLong(IStreamio streamio, MemoryIndex index) {
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

    public void writeVarInt(int value, IStreamio streamio, MemoryIndex index) {
        int temp;
        do {
            temp = value & 0x7F;
            value >>>= 7;
            streamio.put(index.inc(), (byte) (temp + (value == 0 ? 0 : 0x80)));
        } while (value != 0);
    }

    public void writeVarLong(long value, IStreamio streamio, MemoryIndex index) {
        long temp;
        do {
            temp = value & 0x7F;
            value >>>= 7;
            streamio.put(index.inc(), (byte) (temp + (value == 0 ? 0 : 0x80)));
        } while (value != 0);
    }

    private void checkVarLength(long value, int limit) {
        if (value > limit) {
            throw new RuntimeException("VarInt too big. Expected " + limit + " but got " + value);
        }
    }

    public int zigzagEncode(int value) {
        return (value << 1) ^ (value >> 31);
    }

    public int zigzagDecode(int value) {
        return (value >>> 1) ^ -(value & 1);
    }

    public long zigzagEncode(long value) {
        return (value << 1) ^ (value >> 63);
    }

    public long zigzagDecode(long value) {
        return (value >>> 1) ^ -(value & 1);
    }

}
