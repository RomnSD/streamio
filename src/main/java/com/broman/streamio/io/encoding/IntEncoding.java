package com.broman.streamio.io.encoding;

import com.broman.streamio.IStreamio;
import com.broman.streamio.io.MemoryIndex;

/**
 * <p>Interface for encoding integers.</p>
 * 
 * @author Brayan Roman
 * @since  1.0.0
 */
public interface IntEncoding {

    /**
     * <p>Encodes a signed int16 into a stream.</p>
     * 
     * @param value    The value to encode.
     * @param streamio The stream encode into.
     * @param index    The index to encode at.
     */
    void putSInt16(short value, IStreamio streamio, MemoryIndex index);

    /**
     * <p>Decodes a signed int16 from a stream.</p>
     * 
     * @param  streamio The stream to decode from.
     * @param  index    The index to decode at.
     * @return          The decoded value.
     */
    short  getSInt16(IStreamio streamio, MemoryIndex index);

    /**
     * <p>Encodes an unsigned int16 into a stream.</p>
     * 
     * @param value    The value to encode.
     * @param streamio The stream encode into.
     * @param index    The index to encode at.
     */
    void putUInt16(short value, IStreamio streamio, MemoryIndex index);

    /**
     * <p>Decodes an unsigned int16 from a stream.</p>
     * 
     * @param  streamio The stream to decode from.
     * @param  index    The index to decode at.
     * @return          The decoded value.
     */
    short getUInt16(IStreamio streamio, MemoryIndex index);

    /**
     * <p>Encodes a signed int24 into a stream.</p>
     * 
     * @param value    The value to encode.
     * @param streamio The stream encode into.
     * @param index    The index to encode at.
     */
    void putSInt24(int value, IStreamio streamio, MemoryIndex index);

    /**
     * <p>Decodes a signed int24 from a stream.</p>
     * 
     * @param  streamio The stream to decode from.
     * @param  index    The index to decode at.
     * @return          The decoded value.
     */
    int getSInt24(IStreamio streamio, MemoryIndex index);

    /**
     * <p>Encodes an unsigned int24 into a stream.</p>
     * 
     * @param value    The value to encode.
     * @param streamio The stream encode into.
     * @param index    The index to encode at.
     */
    void putUInt24(int value, IStreamio streamio, MemoryIndex index);

    /**
     * <p>Decodes an unsigned int24 from a stream.</p>
     * 
     * @param  streamio The stream to decode from.
     * @param  index    The index to decode at.
     * @return          The decoded value.
     */
    int getUInt24(IStreamio streamio, MemoryIndex index);

    /**
     * <p>Encodes a signed int32 into a stream.</p>
     * 
     * @param value    The value to encode.
     * @param streamio The stream encode into.
     * @param index    The index to encode at.
     */
    void putSInt32(int value, IStreamio streamio, MemoryIndex index);

    /**
     * <p>Decodes a signed int32 from a stream.</p>
     * 
     * @param  streamio The stream to decode from.
     * @param  index    The index to decode at.
     * @return          The decoded value.
     */
    int getSInt32(IStreamio streamio, MemoryIndex index);

    /**
     * <p>Encodes an unsigned int32 into a stream.</p>
     * 
     * @param value    The value to encode.
     * @param streamio The stream encode into.
     * @param index    The index to encode at.
     */
    void putUInt32(int value, IStreamio streamio, MemoryIndex index);

    /**
     * <p>Decodes an unsigned int32 from a stream.</p>
     * 
     * @param  streamio The stream to decode from.
     * @param  index    The index to decode at.
     * @return          The decoded value.
     */
    int getUInt32(IStreamio streamio, MemoryIndex index);

    /**
     * <p>Encodes a signed int64 into a stream.</p>
     * 
     * @param value    The value to encode.
     * @param streamio The stream encode into.
     * @param index    The index to encode at.
     */
    void putSInt64(long value, IStreamio streamio, MemoryIndex index);

    /**
     * <p>Decodes a signed int64 from a stream.</p>
     * 
     * @param  streamio The stream to decode from.
     * @param  index    The index to decode at.
     * @return          The decoded value.
     */
    long getSInt64(IStreamio streamio, MemoryIndex index);

    /**
     * <p>Encodes an unsigned int64 into a stream.</p>
     * 
     * @param value    The value to encode.
     * @param streamio The stream encode into.
     * @param index    The index to encode at.
     */
    void putUInt64(long value, IStreamio streamio, MemoryIndex index);

    /**
     * <p>Decodes an unsigned int64 from a stream.</p>
     * 
     * @param  streamio The stream to decode from.
     * @param  index    The index to decode at.
     * @return          The decoded value.
     */
    long getUInt64(IStreamio streamio, MemoryIndex index);
    
}
