package com.broman.streamio.io.encoding;

import com.broman.streamio.IStreamio;
import com.broman.streamio.io.MemoryIndex;

/**
 * @author Brayan Roman
 * @since  1.0.0
 */
public class LittleEndian implements IntEncoding {

    @Override
    public short getSInt16(IStreamio streamio, MemoryIndex index) {
        return (short) (
            (streamio.get(index.inc()) & 0xFF)    |
            (streamio.get(index.inc()) & 0xFF) << 8
        );
    }

    @Override
    public void putSInt16(short value, IStreamio streamio, MemoryIndex index) {
        streamio.put(index.inc(), (byte) value);
        streamio.put(index.inc(), (byte) (value >> 8));
    }

    @Override
    public short getUInt16(IStreamio streamio, MemoryIndex index) {
        return getSInt16(streamio, index);
    }

    @Override
    public void putUInt16(short value, IStreamio streamio, MemoryIndex index) {
        putSInt16(value, streamio, index);
    }

    @Override
    public int getSInt24(IStreamio streamio, MemoryIndex index) {
        return (streamio.get(index.inc()) & 0xFF)      | 
               (streamio.get(index.inc()) & 0xFF) << 8 | 
               (streamio.get(index.inc()) & 0xFF) << 16;
    }

    @Override
    public void putSInt24(int value, IStreamio streamio, MemoryIndex index) {
        streamio.put(index.inc(), (byte) value);
        streamio.put(index.inc(), (byte) (value >> 8));
        streamio.put(index.inc(), (byte) (value >> 16));
    }

    @Override
    public int getUInt24(IStreamio streamio, MemoryIndex index) {
        return getSInt24(streamio, index);
    }

    @Override
    public void putUInt24(int value, IStreamio streamio, MemoryIndex index) {
        putSInt24(value, streamio, index);
    }

    @Override
    public int getSInt32(IStreamio streamio, MemoryIndex index) {
        return (streamio.get(index.inc()) & 0xFF)       | 
               (streamio.get(index.inc()) & 0xFF) << 8  | 
               (streamio.get(index.inc()) & 0xFF) << 16 | 
               (streamio.get(index.inc()) & 0xFF) << 24 ;
    }

    @Override
    public void putSInt32(int value, IStreamio streamio, MemoryIndex index) {
        streamio.put(index.inc(), (byte) value);
        streamio.put(index.inc(), (byte) (value >> 8));
        streamio.put(index.inc(), (byte) (value >> 16));
        streamio.put(index.inc(), (byte) (value >> 24));
    }

    @Override
    public int getUInt32(IStreamio streamio, MemoryIndex index) {
        return getSInt32(streamio, index);
    }

    @Override
    public void putUInt32(int value, IStreamio streamio, MemoryIndex index) {
        putSInt32(value, streamio, index);
    }

    @Override
    public long getSInt64(IStreamio streamio, MemoryIndex index) {
        return (long) (streamio.get(index.inc()) & 0xFF)       | 
               (long) (streamio.get(index.inc()) & 0xFF) << 8  | 
               (long) (streamio.get(index.inc()) & 0xFF) << 16 | 
               (long) (streamio.get(index.inc()) & 0xFF) << 24 |
               (long) (streamio.get(index.inc()) & 0xFF) << 32 | 
               (long) (streamio.get(index.inc()) & 0xFF) << 40 |
               (long) (streamio.get(index.inc()) & 0xFF) << 48 | 
               (long) (streamio.get(index.inc()) & 0xFF) << 56;
    }

    @Override
    public void putSInt64(long value, IStreamio streamio, MemoryIndex index) {
        streamio.put(index.inc(), (byte) value);
        streamio.put(index.inc(), (byte) (value >> 8));
        streamio.put(index.inc(), (byte) (value >> 16));
        streamio.put(index.inc(), (byte) (value >> 24));
        streamio.put(index.inc(), (byte) (value >> 32));
        streamio.put(index.inc(), (byte) (value >> 40));
        streamio.put(index.inc(), (byte) (value >> 48));
        streamio.put(index.inc(), (byte) (value >> 56));
    }

    @Override
    public long getUInt64(IStreamio streamio, MemoryIndex index) {
        return getSInt64(streamio, index);
    }

    @Override
    public void putUInt64(long value, IStreamio streamio, MemoryIndex index) {
        putSInt64(value, streamio, index);
    }
    
}
