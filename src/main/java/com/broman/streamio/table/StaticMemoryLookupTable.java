package com.broman.streamio.table;

import com.broman.streamio.Memory;
import com.broman.streamio.MemoryPool;
import com.broman.streamio.Streamio;

/**
 * <p>
 * A memory lookup table that contained a fixed number of memory blocks.</p>
 * 
 * @author Brayan Roman
 * @since  1.0.0
 */
public class StaticMemoryLookupTable implements MemoryLookupTable {

    private Memory[] table;

    public StaticMemoryLookupTable(int size, int blockSize) {
        this(new Memory[Streamio.blockIndexes(size, blockSize)]);
    }

    public StaticMemoryLookupTable(Memory[] table) {
        this.table = table;
    }

    @Override
    public int size() {
        return table.length;
    }

    @Override
    public Memory getAt(int index) {
        return table[index];
    }

    @Override
    public Memory setAt(int index, Memory memory) {
        table[index] = memory;
        return memory;
    }

    @Override
    public void offer(MemoryPool pool) {
        if (pool != null) {
            for (Memory memory : table) {
                pool.offer(memory);
            }
        }
        table = new Memory[0];
    }

    @Override
    public void close() {
        for (Memory memory : table) {
            memory.close();
        }
        table = new Memory[0];
    }

}
