package com.broman.streamio;

import java.nio.ByteBuffer;

import com.broman.streamio.memory.MemoryType;

/**
 * <p>
 * A MemoryAllocator is responsible for creating {@code Memory} instances.</p>
 * 
 * @author Brayan Roman
 * @since  1.0.0
 */
public interface MemoryAllocator {

    /**
     * <p>
     * Get the type of memory this allocator creates.</p>
     * 
     * @return the type of memory this allocator creates.
     */
    MemoryType getType();

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
