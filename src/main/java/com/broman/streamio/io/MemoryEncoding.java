package com.broman.streamio.io;

/**
 * <p>
 * The encoding of the memory.</p>
 * 
 * @author Brayan Roman
 * @since  1.0.0
 */
public enum MemoryEncoding {
    BigEndian   , // BigEndian is encoded with the most significant byte first.
    LittleEndian, // LittleEndian is encoded with the least significant byte first.
    VarInt        // Varint is a special case for reading variable length integers.
}
