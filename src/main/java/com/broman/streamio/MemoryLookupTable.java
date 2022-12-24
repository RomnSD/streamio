package com.broman.streamio;

/**
 * <p>This class is destined to store blocks of memory.<p>
 * 
 * <p>
 * Implementations may use very different approaches for an optimized 
 * performing of getAt and setAt, since they may be called very frequently.
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
     * <p>Get a memory block at certain index of this table.</p>
     * 
     * @param index the index where to look for the value.
     * @return      the Memory instance if found, null otherwise. 
     */
    Memory getAt(int index);

    /**
     * <p>Set a memory block at certain index of this table.</p>
     * 
     * @param index  the index where to set the new value.
     * @param memory the memory block.
     * @return       the memory block that has been set.
     */
    Memory setAt(int index, Memory memory);
    
}
