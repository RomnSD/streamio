package com.broman.streamio.table;

import com.broman.streamio.Memory;
import com.broman.streamio.MemoryPool;

/**
 * <p>
 * This class is destined to store blocks of memory.<p>
 *
 * <p>
 * Implementations may use very different approaches for an optimized performing
 * of getAt and setAt, since they may be called very frequently.
 * </p>
 *
 * @author Brayan Roman
 * @since  1.0.0
 * @see    Memory
 */
public interface MemoryLookupTable extends AutoCloseable {

    /**
     * @return the size of memory blocks this table can store.
     */
    int size();

    /**
     * <p>
     * Get a memory block at certain index of this table.</p>
     *
     * @param index the index where to look for the value.
     * @return the Memory instance if found, null otherwise.
     */
    Memory getAt(int index);

    /**
     * <p>
     * Set a memory block at certain index of this table.</p>
     *
     * @param index the index where to set the new value.
     * @param memory the memory block.
     * @return the memory block that has been set.
     */
    Memory setAt(int index, Memory memory);

    /**
     * <p>
     * Offer all the memory blocks of this table to a pool.</p>
     * 
     * <p>
     * This method is similar to {@link #close()}, but it does not close the memory blocks.</p>
     *
     * @param pool the pool to offer the memory blocks.
     */
    void offer(MemoryPool pool);

}
