package com.broman.streamio;

import java.util.function.Consumer;

import com.broman.streamio.memory.MemoryType;

/**
 * @author Brayan Roman
 * @since  1.0.0
 */
public abstract class Memory implements AutoCloseable {

    private boolean closed = false;
    private Consumer<Memory> closeHandler;

    /**
     * @return total size of this memory in bytes.
     */
    public abstract int size();

    /**
     * <p>
     * Verify if this memory is closed.</p>
     * 
     * @return true if this memory is closed, false otherwise.
     */
    public boolean closed() {
        return closed;
    }

    /**
     * <p>
     * Get the identity of this memory, the default implementation uses {@link System#identityHashCode(Object)}.</p>
     * @return the identity of this memory.
     */
    public int identity() {
        return System.identityHashCode(this);
    }

    /**
     * <p>
     * Get a byte from this memory.</p>
     *
     * @param index the index to look for.
     * @return the byte at the provided index.
     */
    public abstract byte get(int index);

    /**
     * <p>
     * Put a byte at certain index in this memory representation.</p>
     *
     * @param index the index where to put the value.
     * @param value the value be placed.
     */
    public abstract void put(int index, byte value);

    /**
     * <p>
     * Get the type of this memory.</p>
     * @return the type of this memory.
     */
    public abstract MemoryType getType();

    /**
     * <p>
     * Set a handler to be called when this memory is closed.</p>
     *
     * @param closeHandler the handler to be called when this memory is closed.
     */
    public void setCloseHandler(Consumer<Memory> closeHandler) {
        this.closeHandler = closeHandler;
    }

    @Override
    public void close() {
        if (closed) {
            throw new IllegalStateException("Memory already closed");
        }
        try {
            if (closeHandler != null) {
                closeHandler.accept(this);
            }
        } finally {
            closed = true;
        }
    }

}
