package com.broman.streamio;

/**
 * <p>A memory pool interface.</p>
 * 
 * @author Brayan Roman
 * @since  1.0.0
 */
public interface MemoryPool extends AutoCloseable {

    /**
     * <p>Get a memory block from the pool.</p>
     * <p>Memory blocks are allocated from the pool using the provided allocator.</p>
     * 
     * @param allocator The allocator to use for allocating memory blocks.
     * @return          A memory block.
     */
    Memory get(MemoryAllocator allocator);

    /**
     * <p>Return a memory block to the pool.</p>
     * 
     * @param memory The memory block to return to the pool.
     */
    void offer(Memory memory);

}
