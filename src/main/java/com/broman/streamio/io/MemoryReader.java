package com.broman.streamio.io;

import com.broman.streamio.serialization.ObjectSerializer;

/**
 * <p>
 * The interface for reading from memory.</p>
 * 
 * @author Brayan Roman
 * @since  1.0
 */
public interface MemoryReader extends MemoryManagement {

    /**
     * <p>
     * Reads a byte from the memory.</p>
     * 
     * @return The byte read from the memory.
     */
    byte readByte();

    /**
     * <p>
     * Reads a byte from the memory at the specified index.</p>
     * 
     * @param index The index to read the byte from.
     * @return      The byte read from the memory.
     */
    byte readByte(MemoryIndex index);

    /**
     * <p>
     * Reads a short from the memory.</p>
     * 
     * @return The short read from the memory.
     */
    short readShort();

    /**
     * <p>
     * Reads a short from the memory at the specified index.</p>
     * 
     * @param index The index to read the short from.
     * @return      The short read from the memory.
     */
    short readShort(MemoryIndex index);

    short readUShort();
    short readUShort(MemoryIndex index);

    /**
     * <p>
     * Reads a medium from the memory.</p>
     * 
     * @return The medium read from the memory.
     */
    int readMedium();

    /**
     * <p>
     * Reads a medium from the memory at the specified index.</p>
     * 
     * @param index The index to read the medium from.
     * @return      The medium read from the memory.
     */
    int readMedium(MemoryIndex index);

    int readUMedium();
    int readUMedium(MemoryIndex index);

    /**
     * <p>
     * Reads an integer from the memory.</p>
     * 
     * @return The integer read from the memory.
     */
    int readInt();

    /**
     * <p>
     * Reads an integer from the memory at the specified index.</p>
     * 
     * @param index The index to read the integer from.
     * @return      The integer read from the memory.
     */
    int readInt(MemoryIndex index);

    int readUInt();
    int readUInt(MemoryIndex index);

    /**
     * <p>
     * Reads a long from the memory.</p>
     * 
     * @return The long read from the memory.
     */
    long readLong();

    /**
     * <p>
     * Reads a long from the memory at the specified index.</p>
     * 
     * @param index The index to read the long from.
     * @return      The long read from the memory.
     */
    long readLong(MemoryIndex index);

    long readULong();
    long readULong(MemoryIndex index);

    /**
     * <p>
     * Reads a float from the memory.</p>
     * 
     * @return The float read from the memory.
     */
    float readFloat();

    /**
     * <p>
     * Reads a float from the memory at the specified index.</p>
     * 
     * @param index The index to read the float from.
     * @return      The float read from the memory.
     */
    float readFloat(MemoryIndex index);

    float readUFloat();
    float readUFloat(MemoryIndex index);

    /**
     * <p>
     * Reads a double from the memory.</p>
     * 
     * @return The double read from the memory.
     */
    double readDouble();

    /**
     * <p>
     * Reads a double from the memory at the specified index.</p>
     * 
     * @param index The index to read the double from.
     * @return      The double read from the memory.
     */
    double readDouble(MemoryIndex index);

    double readUDouble();
    double readUDouble(MemoryIndex index);

    /**
     * <p>
     * Reads a char from the memory.</p>
     * 
     * <p>
     * Note: The char is read as a short.</p>
     * 
     * @return The char read from the memory.
     */
    char readChar();

    /**
     * <p>
     * Reads a char from the memory at the specified index.</p>
     * 
     * <p>
     * Note: The char is read as a short.</p>
     * 
     * @param index The index to read the char from.
     * @return      The char read from the memory.
     */
    char readChar(MemoryIndex index);

    /**
     * <p>
     * Reads a boolean from the memory.</p>
     * 
     * @return The boolean read from the memory.
     */
    boolean readBoolean();

    /**
     * <p>
     * Reads a boolean from the memory at the specified index.</p>
     * 
     * @param index The index to read the boolean from.
     * @return      The boolean read from the memory.
     */
    boolean readBoolean(MemoryIndex index);

    /**
     * <p>
     * Reads a string from the memory.</p>
     * 
     * <p>
     * Note: The string is read as a char array.</p>
     * 
     * @return The string read from the memory.
     */
    String readCharString();

    /**
     * <p>
     * Reads a string from the memory at the specified index.</p>
     * 
     * <p>
     * Note: The string is read as a char array.</p>
     * 
     * @param index The index to read the string from.
     * @return      The string read from the memory.
     */
    String readCharString(MemoryIndex index);

    /**
     * <p>
     * Reads a string from the memory.</p>
     * 
     * <p>
     * Note: The string is read as a byte array.</p>
     * 
     * @return The string read from the memory.
     */
    String readByteString();

    /**
     * <p>
     * Reads a string from the memory at the specified index.</p>
     * 
     * <p>
     * Note: The string is read as a byte array.</p>
     * 
     * @param index The index to read the string from.
     * @return      The string read from the memory.
     */
    String readByteString(MemoryIndex index);

    /**
     * <p>
     * Reads a byte array from the memory.</p>
     * 
     * @param length The length of the byte array to read.
     * @return       The byte array read from the memory.
     */
    byte[] readBytes(int length);

    /**
     * <p>
     * Reads a byte array from the memory at the specified index.</p>
     * 
     * @param index  The index to read the byte array from.
     * @param length The length of the byte array to read.
     * @return       The byte array read from the memory.
     */
    byte[] readBytes(MemoryIndex index, int length);

    /**
     * <p>
     * Reads an object from the memory.</p>
     * 
     * @param type The type of the object to read.
     * @return     The object read from the memory.
     */
    <T> T readObject(Class<T> type);

    /**
     * <p>
     * Reads an object from the memory at the specified index.</p>
     * 
     * @param index The index to read the object from.
     * @param type  The type of the object to read.
     * @return      The object read from the memory.
     */
    <T> T readObject(ObjectSerializer<T> serializer);

}
