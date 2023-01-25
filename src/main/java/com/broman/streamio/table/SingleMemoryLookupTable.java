package com.broman.streamio.table;

import com.broman.streamio.Memory;
import com.broman.streamio.MemoryPool;

/**
 * <p>
 * A memory lookup table that only contains a single memory block.</p>
 * 
 * @author Brayan Roman
 * @since  1.0.0
 */
public class SingleMemoryLookupTable implements MemoryLookupTable {

    private Memory memory;

    public SingleMemoryLookupTable(Memory memory) {
        this.memory = memory;
    }

    @Override
    public int size() {
        return 1;
    }

    @Override
    public Memory getAt(int index) {
        return memory;
    }

    @Override
    public Memory setAt(int index, Memory memory) {
        throw new UnsupportedOperationException("Cannot set memory in a single memory lookup table.");
    }

    @Override
    public void offer(MemoryPool pool) {
        if (pool != null) {
            pool.offer(memory);
        }
        memory = null;
    }

    @Override
    public void close() {
        if (memory != null) {
            memory.close();
        }
        memory = null;
    }
    
}
