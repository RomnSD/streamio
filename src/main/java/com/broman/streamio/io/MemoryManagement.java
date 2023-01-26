package com.broman.streamio.io;

import com.broman.streamio.io.encoding.IntEncoding;
import com.broman.streamio.serialization.SerializationRegistry;

/**
 * <p>
 * The interface for managing memory.</p>
 * 
 * @author Brayan Roman
 * @since  1.0.0
 */
public interface MemoryManagement {

    /**
     * <p>
     * Gets the current index of the memory.</p>
     * 
     * @return The current index of the memory.
     */
    MemoryIndex getIndex();

    /**
     * <p>
     * Sets the current index of the memory.</p>
     * 
     * @param index The new index of the memory.
     */
    void setIndex(MemoryIndex index);

    /**
     * <p>
     * Gets the current memory encoding of the memory.</p>
     * 
     * @return The current memory encoding of the memory.
     */
    IntEncoding getEncoding();

    /**
     * <p>
     * Sets the current memory encoding of the memory.</p>
     * 
     * @param encoding The new memory encoding of the memory.
     */
    void setEncoding(IntEncoding encoding);

    /**
     * <p>
     * Gets the current serialization registry of the memory.</p>
     * 
     * @return The current serialization registry of the memory.
     */
    SerializationRegistry getSerializers();

    /**
     * <p>
     * Sets the current serialization registry of the memory.</p>
     * 
     * @param serializers The new serialization registry of the memory.
     */
    void setSerializers(SerializationRegistry serializers);
    
}
