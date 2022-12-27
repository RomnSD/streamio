package com.broman.streamio;

import java.io.OutputStream;
import java.nio.ByteBuffer;

/**
 * @author Brayan Roman
 * @since 1.0.0
 */
public interface IStreamio {

    /**
     * <p>
     * Get the total amount of bytes this stream can handle.</p>
     *
     * @return the maximum size of this stream in bytes.
     */
    int size();

    /**
     * <p>
     * Get this stream's current block size (allocation size).</p>
     *
     * @return the size in bytes of this stream's block size.
     */
    int blockSize();

    /**
     * <p>
     * Set a new default value for the stream.</p>
     *
     * <p>
     * If not memory is found at any valid index of this stream, the default
     * byte value will be used instead. This is for preventing memory allocation
     * and act as if there is an actual memory.
     * </p>
     *
     * <p>
     * <b>Note:</b>
     * This value by default must be 0, but this may be different in custom
     * implementations.
     * </p>
     *
     * <p>
     * <b>Note:</b>
     * Memory allocators may use a different value when memory blocks are
     * allocated (by default 0), but again, this depends on the implementation.
     * </p>
     *
     * @param value the value to be set.
     */
    void defaultByte(byte value);

    /**
     * <p>
     * Get a byte from this stream.</p>
     *
     * Also see {@link #defaultByte(byte)}
     *
     * @param index the position of the byte to look for.
     * @return if memory block is still not allocated the result is 0, otherwise
     * the respective value at the provided index.
     */
    byte get(int index);

    /**
     * <p>
     * Put a byte into this stream.</p>
     *
     * <p>
     * If the memory block was not found at {@code index}, the provided memory
     * allocator will be called to create the memory block for the {@code value}
     * to be placed.
     * </p>
     *
     * @param index the position where to set the byte.
     * @param value the value to be set.
     */
    void put(int index, byte value);

    /**
     * <p>
     * Put a byte array into this stream.</p>
     *
     * @see #put(int, byte)
     *
     * @param index the position where to set the byte.
     * @param value the value to be set.
     */
    void put(int index, int value);

    /**
     * <p>
     * Put a byte array into this stream.</p>
     *
     * @param index the position where to set the byte.
     * @param value the byte array to be included.
     */
    void put(int index, byte[] value);

    /**
     * <p>
     * Slice this stream content into a byte array.</p>
     *
     * <p>
     * If not memory block was found in any index of the range, the value
     * provided with {@link #defaultByte(byte)} will be used for fill the byte
     * array.
     * </p>
     *
     * <p>
     * Also see {@link #defaultByte(byte)}</p>
     *
     * @param offset the starting index.
     * @param length the amount of bytes to take.
     * @return the byte array result.
     */
    byte[] array(int offset, int length);

    /**
     * <p>
     * Write this stream content into a ByteBuffer.</p>
     *
     * @param buffer the buffer to write into.
     * @param offset the starting index.
     * @param length the amount of bytes to write.
     */
    void writeTo(ByteBuffer buffer, int offset, int length);

    /**
     * <p>
     * Write this stream content into an OutputStream.</p>
     *
     * @param stream the stream to write into.
     * @param offset the starting index.
     * @param length the amount of bytes to write.
     */
    void writeTo(OutputStream stream, int offset, int length);

}
