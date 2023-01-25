package com.broman.streamio;

public class MemoryClosedException extends IllegalStateException {
    
    public MemoryClosedException() {
        super("memory has been closed");
    }
    
}
