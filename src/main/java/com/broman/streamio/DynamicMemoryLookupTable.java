package com.broman.streamio;

import static java.util.Arrays.copyOf;

/**
 * @author Brayan Roman
 * @since  1.0.0
 */
public class DynamicMemoryLookupTable implements MemoryLookupTable {

    public final int size;
    public final MemoryTable buffers;

    public DynamicMemoryLookupTable(int size, int blockSize) {
        this.size = Streamio.blockIndexes(size, blockSize);
        this.buffers = new MemoryTable(this.size, MemoryTable.DEFAULT_LOAD_FACTOR);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Memory getAt(int index) {
        return buffers.get(index);
    }

    @Override
    public Memory setAt(int index, Memory memory) {
        buffers.put(index, memory);
        return memory;
    }

    @Override
    public void close() {
        buffers.clear();
    }

    /**
     * Basic Hashtable implementation to hold Memory instances.
     */
    public static class MemoryTable {

        public final static int DEFAULT_SIZE = 16;
        public final static float DEFAULT_LOAD_FACTOR = 0.75f;

        private MemoryEntry[] table;
        private int size;
        private int maxSize;
        private float loadFactor;

        public MemoryTable() {
            this(16, DEFAULT_LOAD_FACTOR);
        }

        public MemoryTable(int maxSize, float loadFactor) {
            this.maxSize = maxSize;
            this.loadFactor = Math.max(DEFAULT_LOAD_FACTOR, loadFactor);
            this.table = new MemoryEntry[Math.min(maxSize, DEFAULT_SIZE)];
        }

        public int size() {
            return size;
        }

        public Memory get(int key) {
            int index = key % table.length;
            MemoryEntry value = table[index];

            if (value != null) {
                if (value.key == key) {
                    return value.val;
                } else {
                    while ((value = value.next) != null) {
                        if (value.key == key) {
                            return value.val;
                        }
                    }
                    return null;
                }
            } else {
                return null;
            }
        }

        void put(int key, Memory val) {
            if (table.length != maxSize) {
                if (((size + 1f) / table.length) > loadFactor) {
                    table = copyOf(table, Math.min(maxSize, table.length * 2));
                }
            }

            int index = key % table.length;
            MemoryEntry value = table[index];

            if (value != null) {
                if (value.key == key) {
                    value.val = val;
                    return;
                } else {
                    while (value.next != null) {
                        value = value.next;
                    }
                    value.next = new MemoryEntry(key, val, null);
                }
            } else {
                table[index] = new MemoryEntry(key, val, null);
            }
            size += 1;
        }

        public void delete(int key) {
            int index = key % table.length;
            MemoryEntry value = table[index];

            if (value != null) {
                MemoryEntry prev = null;
                while (value.next != null && value.key != key) {
                    prev = value;
                    value = value.next;
                }
                if (value.key == key) {
                    if (prev != null) {
                        prev.next = value.next;
                    } else {
                        table[index] = value.next;
                    }
                    size--;
                }
            }
        }

        void clear() {
            table = new MemoryEntry[Math.min(DEFAULT_SIZE, maxSize)];
        }

        static class MemoryEntry {

            int key;
            Memory val;
            MemoryEntry next;

            MemoryEntry(int key, Memory val, MemoryEntry next) {
                this.key = key;
                this.val = val;
                this.next = next;
            }

            @Override
            public String toString() {
                return "Entry(key=" + key + ", next=" + (next == null ? "null" : next.key) + ")";
            }
        }

    }

}
