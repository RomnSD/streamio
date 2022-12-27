package com.broman.streamio;

/**
 * @author Brayan Roman
 * @since 1.0.0
 */
public interface Memory extends AutoCloseable {

    /**
     * @return total size of this memory in bytes.
     */
    int size();

    /**
     * <p>
     * Get a byte from this memory.</p>
     *
     * @param index the index to look for.
     * @return the byte at the provided index.
     */
    byte get(int index);

    /**
     * <p>
     * Put a byte at certain index in this memory representation.</p>
     *
     * @param index the index where to put the value.
     * @param value the value be placed.
     */
    void put(int index, byte value);

}
