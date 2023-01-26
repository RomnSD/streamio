package com.broman.streamio.io;

import com.broman.streamio.serialization.ObjectSerializer;

/**
 * <p>
 * The interface for writing to memory.</p>
 * 
 * @author Brayan Roman
 * @since  1.0.0
 */
public interface MemoryWriter extends MemoryManagement {
    
    /**
     * <p>
     * Writes a byte to the memory. </p>
     * 
     * @param value The byte to write to the memory.
     */
    void writeByte(byte value);

    /**
     * <p>
     * Writes a byte to the memory at the specified index.</p>
     * 
     * @param index The index to write the byte to.
     * @param value The byte to write to the memory.
     */
    void writeByte(MemoryIndex index, byte value);

    /**
     * <p>
     * Writes a short to the memory.</p>
     * 
     * @param value The short to write to the memory.
     */
    void writeShort(short value);

    /**
     * <p>
     * Writes a short to the memory at the specified index.</p>
     * 
     * @param index The index to write the short to.
     * @param value The short to write to the memory.
     */
    void writeShort(MemoryIndex index, short value);

    void writeUShort(short value);
    void writeUShort(MemoryIndex index, short value);

    /**
     * <p>
     * Writes a medium to the memory.</p>
     * 
     * @param value The medium to write to the memory.
     */
    void writeMedium(int value);

    /**
     * <p>
     * Writes a medium to the memory at the specified index.</p>
     * 
     * @param index The index to write the medium to.
     * @param value The medium to write to the memory.
     */
    void writeMedium(MemoryIndex index, int value);

    void writeUMedium(int value);
    void writeUMedium(MemoryIndex index, int value);

    /**
     * <p>
     * Writes an integer to the memory.</p>
     * 
     * @param value The integer to write to the memory.
     */
    void writeInt(int value);

    /**
     * <p>
     * Writes an integer to the memory at the specified index.</p>
     * 
     * @param index The index to write the integer to.
     * @param value The integer to write to the memory.
     */
    void writeInt(MemoryIndex index, int value);

    void writeUInt(int value);
    void writeUInt(MemoryIndex index, int value);

    /**
     * <p>
     * Writes a long to the memory.</p>
     * 
     * @param value The long to write to the memory.
     */
    void writeLong(long value);

    /**
     * <p>
     * Writes a long to the memory at the specified index.</p>
     * 
     * @param index The index to write the long to.
     * @param value The long to write to the memory.
     */
    void writeLong(MemoryIndex index, long value);

    void writeULong(long value);
    void writeULong(MemoryIndex index, long value);

    /**
     * <p>
     * Writes a float to the memory.</p>
     * 
     * @param value The float to write to the memory.
     */
    void writeFloat(float value);

    /**
     * <p>
     * Writes a float to the memory at the specified index.</p>
     * 
     * @param index The index to write the float to.
     * @param value The float to write to the memory.
     */
    void writeFloat(MemoryIndex index, float value);

    void writeUFloat(float value);
    void writeUFloat(MemoryIndex index, float value);

    /**
     * <p>
     * Writes a double to the memory.</p>
     * 
     * @param value The double to write to the memory.
     */
    void writeDouble(double value);

    /**
     * <p>
     * Writes a double to the memory at the specified index.</p>
     * 
     * @param index The index to write the double to.
     * @param value The double to write to the memory.
     */
    void writeDouble(MemoryIndex index, double value);

    void writeUDouble(double value);
    void writeUDouble(MemoryIndex index, double value);

    /**
     * <p>
     * Writes a char to the memory.</p>
     * 
     * <p>
     * Note: The char is written as short.</p>
     * 
     * @param value The char to write to the memory.
     */
    void writeChar(char value);

    /**
     * <p>
     * Writes a char to the memory at the specified index.</p>
     * 
     * <p>
     * Note: The char is written as short.</p>
     * 
     * @param index The index to write the char to.
     * @param value The char to write to the memory.
     */
    void writeChar(MemoryIndex index, char value);

    /**
     * <p>
     * Writes a boolean to the memory.</p>
     * 
     * @param value The boolean to write to the memory.
     */
    void writeBoolean(boolean value);

    /**
     * <p>
     * Writes a boolean to the memory at the specified index.</p>
     * 
     * @param index The index to write the boolean to.
     * @param value The boolean to write to the memory.
     */
    void writeBoolean(MemoryIndex index, boolean value);

    /**
     * <p>
     * Writes a string to the memory.</p>
     * 
     * <p>
     * Note: The string is written as char array.</p>
     * 
     * @param value The string to write to the memory.
     */
    void writeCharString(String value);

    /**
     * <p>
     * Writes a string to the memory at the specified index.</p>
     * 
     * <p>
     * Note: The string is written as char array.</p>
     * 
     * @param index The index to write the string to.
     * @param value The string to write to the memory.
     */
    void writeCharString(MemoryIndex index, String value);

    /**
     * <p>
     * Writes a string to the memory.</p>
     * 
     * <p>
     * Note: The string is written as byte array.</p>
     * 
     * @param value The string to write to the memory.
     */
    void writeByteString(String value);

    /**
     * <p>
     * Writes a string to the memory at the specified index.</p>
     * 
     * <p>
     * Note: The string is written as byte array.</p>
     * 
     * @param index The index to write the string to.
     * @param value The string to write to the memory.
     */
    void writeByteString(MemoryIndex index, String value);

    /**
     * <p>
     * Writes a byte array to the memory.</p>
     * 
     * @param value The byte array to write to the memory.
     */
    void writeBytes(byte[] value);

    /**
     * <p>
     * Writes a byte array to the memory at the specified index.</p>
     * 
     * @param index The index to write the byte array to.
     * @param value The byte array to write to the memory.
     */
    void writeBytes(MemoryIndex index, byte[] value);

    /**
     * <p>
     * Writes an object to the memory.</p>
     * 
     * <p>
     * The object serializer will be provided by the current {@link SerializationRegistry}.</p>
     * 
     * @param value The object to write to the memory.
     */
    <T> void writeObject(T value);

    /**
     * <p>
     * Writes an object to the memory using the provided serializer.</p>
     * 
     * @param value The object to write to the memory.
     * @param serializer The serializer to use for writing the object.
     */
    <T> void writeObject(T value, ObjectSerializer<T> serializer);

}
