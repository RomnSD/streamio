package com.broman.streamio.io;

import com.broman.streamio.serialization.SerializationRegistry;

public abstract class AbstractMemoryManagement implements MemoryManagement {

    protected MemoryIndex index;
    protected MemoryEncoding encoding;
    protected SerializationRegistry serializers;

    public AbstractMemoryManagement() {
        this(new MemoryIndex(0), MemoryEncoding.BigEndian);
    }

    public AbstractMemoryManagement(MemoryIndex index, MemoryEncoding encoding) {
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
    public void setEncoding(MemoryEncoding encoding) {
        if (encoding == null) {
            throw new NullPointerException("Encoding cannot be null");
        }
        this.encoding = encoding;
    }

    @Override
    public MemoryEncoding getEncoding() {
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
