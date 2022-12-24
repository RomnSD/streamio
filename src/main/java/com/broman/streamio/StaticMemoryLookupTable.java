package com.broman.streamio;

import static java.util.Arrays.fill;
 
/**
 * @author Brayan Roman
 * @since  1.0.0
 */
public class StaticMemoryLookupTable implements MemoryLookupTable {

    private final Memory[] buffers;

    public StaticMemoryLookupTable(int size, int blockSize) {
        this(new Memory[Streamio.blockIndexes(size, blockSize)]);
    }

    StaticMemoryLookupTable(Memory[] buffers) {
        this.buffers = buffers;
    }

    @Override
    public int size() {
        return buffers.length;
    }

    @Override
    public Memory getAt(int index) {
        return buffers[index];
    }

    @Override
    public Memory setAt(int index, Memory memory) {
        buffers[index] = memory;
        return memory;
    }

    @Override
    public void close() {
        fill(buffers, null);
    }

}
