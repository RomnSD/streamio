package com.broman.streamio.io;

/**
 * <p>
 * A simple index class.</p>
 * 
 * @author Brayan Roman
 * @since  1.0.0
 */
public final class MemoryIndex implements Cloneable {
    private int index;
    
    public MemoryIndex(int index) {
        set(index);
    }

    /**
     * <p>
     * Gets the current index.</p>
     * 
     * @return the current index.
     */
    public int get() {
        return index;
    }

    /**
     * <p>
     * Increments the index by one.</p>
     * 
     * @return the new index.
     */
    public int inc() {
        return index++;
    }

    /**
     * <p>
     * Decrements the index by one.</p>
     * 
     * @return the new index.
     */
    public int dec() {
        // TODO: Throw exception if index is <= 0
        return index--;
    }

    /**
     * <p>
     * Sets the index to a new value.</p>
     * 
     * @param index the new index.
     * @return      the new index.
     * @throws IndexOutOfBoundsException if the index is negative.
     */
    public int set(int index) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("Index cannot be negative");
        }
        this.index = index;
        return index;
    }

    @Override
    public MemoryIndex clone() {
        return new MemoryIndex(index);
    }

}
