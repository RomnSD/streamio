package com.broman.streamio.io;

import com.broman.streamio.io.encoding.BigEndian;
import com.broman.streamio.io.encoding.IntEncoding;
import com.broman.streamio.io.encoding.LittleEndian;
import com.broman.streamio.io.encoding.VarInt;

/**
 * @author Brayan Roman
 * @since  1.0.0
 */
public final class MemoryEncoding {
    public static final IntEncoding BIG_ENDIAN = new BigEndian();
    public static final IntEncoding LITTLE_ENDIAN = new LittleEndian();
    public static final IntEncoding VARINT = new VarInt();
}
