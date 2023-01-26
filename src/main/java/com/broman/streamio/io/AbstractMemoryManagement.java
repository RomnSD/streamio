package com.broman.streamio.io;

import com.broman.streamio.io.encoding.IntEncoding;
import com.broman.streamio.serialization.SerializationRegistry;

public abstract class AbstractMemoryManagement implements MemoryManagement {

    protected MemoryIndex index;
    protected IntEncoding encoding;
    protected SerializationRegistry serializers;

    public AbstractMemoryManagement() {
        this(new MemoryIndex(0), MemoryEncoding.BIG_ENDIAN);
    }

    public AbstractMemoryManagement(MemoryIndex index, IntEncoding encoding) {
        setIndex(index);
        setEncoding(encoding);
    }

    @Override
    public void setIndex(MemoryIndex index) {
        if (index == null) {
            throw new NullPointerException("Index cannot be null");
        }
        this.index = index;
    }

    @Override
    public MemoryIndex getIndex() {
        return index;
    }

    @Override
    public void setEncoding(IntEncoding encoding) {
        if (encoding == null) {
            throw new NullPointerException("Encoding cannot be null");
        }
        this.encoding = encoding;
    }

    @Override
    public IntEncoding getEncoding() {
        return encoding;
    }

    @Override
    public SerializationRegistry getSerializers() {
        return serializers;
    }

    @Override
    public void setSerializers(SerializationRegistry serializers) {
        this.serializers = serializers;
    }

}
