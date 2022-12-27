package com.broman.streamio;

import java.nio.ByteBuffer;

/**
 * @author Brayan Roman
 * @since 1.0.0
 */
public interface MemoryAllocator {

    /**
     * <p>
     * Allocate a new memory block using the implementation default values.</p>
     *
     * @return the created Memory instance.
     */
    Memory allocate();

    /**
     * <p>
     * Allocate a new memory block of {@code size} bytes.</p>
     *
     * @param size the size in bytes to be allocated.
     * @return the created Memory instance.
     */
    Memory allocate(int size);

    /**
     * <p>
     * Wrap a byte array as a {@code Memory}</p>
     *
     * @param array the byte array to be wrapped.
     * @return the created Memory instance.
     */
    Memory allocate(byte[] array);

    /**
     * <p>
     * Wrap a {@code ByteBuffer} as a {@code Memory}</p>
     *
     * @param buffer the ByteBuffer to be wrapped.
     * @return the created Memory instance.
     */
    Memory allocate(ByteBuffer buffer);

}
