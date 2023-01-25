package com.broman.streamio.serialization;

import com.broman.streamio.io.MemoryReader;
import com.broman.streamio.io.MemoryWriter;

/**
 * <p>
 * A simple object serializer interface.</p>
 * 
 * @author Brayan Roman
 * @since  1.0.0
 */
public interface ObjectSerializer<T> {
    
    /**
     * <p>
     * Gets the type of the object that this serializer serializes.</p>
     * 
     * @See {@link SerializationRegistry#register(ObjectSerializer)} for more information.
     * @return the type of the object that this serializer serializes.
     */
    Class<T> getType();

    /**
     * <p>
     * Serializes an object to a memory writer.</p>
     * 
     * @param object the object to serialize.
     * @param writer the writer to write to.
     */
    void serialize(T object, MemoryWriter writer);

    /**
     * <p>
     * Unserializes an object from a memory reader.</p>
     * 
     * @param reader the reader to read from.
     * @return       the unserialized object.
     */
    T unserialize(MemoryReader reader);

}
