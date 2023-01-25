package com.broman.streamio;

import java.util.ArrayDeque;
import java.util.Deque;

import com.broman.streamio.memory.MemoryType;

/**
 * <p>
 * A basic memory pool implementation.</p>
 * 
 * @author Brayan Roman
 * @since  1.0.0
 */
public class BasicMemoryPool implements MemoryPool {

    private final int size;
    private final MemoryType type;
    private final Deque<Memory> pool = new ArrayDeque<>();

    public BasicMemoryPool(int size, MemoryType type) {
        this.size = size;
        this.type = type;
    }

    public void reserve(int count, MemoryAllocator allocator) {
        checkType(allocator.getType());
        for (int i = 0; i < count; i++) {
            pool.add(allocator.allocate(size));
        }
    }

    @Override
    public Memory get(MemoryAllocator allocator) {
        checkType(allocator.getType());
        Memory memory = pool.poll();

        if (memory == null) {
            memory = allocator.allocate(size);
        }
        else {
            for (int i = 0; i < size; i++) {
                // Clear memory
                memory.put(i, (byte) 0);
            }
        }

        checkMemory(memory);
        memory.setCloseHandler((m) -> {
            throw new RuntimeException("Memory block was not returned to pool");
        });

        return memory;
    }

    @Override
    public void offer(Memory memory) {
        checkMemory(memory);
        pool.add(memory);
    }

    @Override
    public String toString() {
        return "BasicMemoryPool(available=" + pool.size() + ")";
    }

    @Override
    public void close() {
        try {
            for (Memory memory : pool) {
                memory.close();
            }
            pool.clear();
        }
        catch(Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    private void checkType(MemoryType type) {
        if (this.type != type) {
            throw new IllegalArgumentException("Memory block type does not match pool block type");
        }
    }

    private void checkMemory(Memory memory) {
        if (memory == null) {
            throw new IllegalArgumentException("Memory block is null");
        }
        if (memory.closed()) {
            throw new IllegalArgumentException("Memory block is closed");
        }
        if (memory.size() != size) {
            throw new IllegalArgumentException("Memory block size does not match pool block size");
        }
    }
    
}
