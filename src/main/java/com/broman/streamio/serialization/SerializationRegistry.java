package com.broman.streamio.serialization;

import java.util.HashMap;
import java.util.Map;

/**
 * A registry of serializers for different types.
 * 
 * @author Brayan Roman
 * @since  1.0.0
 */
@SuppressWarnings("unchecked")
public final class SerializationRegistry {
    private final Map<Class<?>, ObjectSerializer<?>> serializers = new HashMap<>();

    /**
     * <p>
     * Add a serializer to the registry.</p>
     *
     * <p>
     * Take in consideration that Java does not support generics at runtime due to the type erasure.</br>
     * This means that there is no difference between a {@code Map<String, String>} and a {@code Map<Integer, Integer>} class,
     * so the type of the serializer will be the same for both of them, which is {@code Map}.
     * </p>
     * 
     * @param serializer the serializer to add, if the serializer's type is null, an
     *               {@link IllegalArgumentException} will be thrown.
     */
    public<T> void register(ObjectSerializer<T> serializer) {
        Class<?> type = serializer.getType();
        if (type == null) {
            throw new IllegalArgumentException("Serializer type cannot be null");
        }
        serializers.put(type, serializer);
    }

    /**
     * <p>
     * Removes a serializer from the registry.</p>
     * 
     * @param type the type of the serializer to remove.
     */
    public void unregister(Class<?> type) {
        serializers.remove(type);
    }

    /**
     * <p>
     * Gets an serializer from the registry.</p>
     * 
     * @param type the type of the serializer to get.
     * @return     the serializer, or null if the serializer does not exist.
     */
    public<T> ObjectSerializer<T> find(Class<?> type) {
        return (ObjectSerializer<T>) serializers.get(type);
    }

    /**
     * <p>
     * Gets an serializer from the registry.</p>
     * 
     * @param object the object to get the serializer for.
     * @return       the serializer, or null if the serializer does not exist.
     */
    public<T> ObjectSerializer<T> find(T object) {
        return find(object.getClass());
    }

    /**
     * <p>
     * Checks if an serializer exists in the registry.</p>
     * 
     * @param type the type of the serializer to check.
     * @return     true if the serializer exists, false otherwise.
     */
    public boolean hasSerializer(Class<?> type) {
        return serializers.containsKey(type);
    }

    /**
     * <p>
     * Checks if an serializer exists in the registry.</p>
     * 
     * @param object the object to check the serializer for.
     * @return       true if the serializer exists, false otherwise.
     */
    public boolean hasSerializer(Object object) {
        return hasSerializer(object.getClass());
    }

    /**
     * <p>
     * Clears the registry.</p>
     */
    public void clear() {
        serializers.clear();
    }

    /**
     * <p>
     * Gets the number of serializers in the registry.</p>
     * 
     * @return the number of serializers in the registry.
     */
    public int size() {
        return serializers.size();
    }

    /**
     * <p>
     * Checks if the registry is empty.</p>
     * 
     * @return true if the registry is empty, false otherwise.
     */
    public boolean isEmpty() {
        return serializers.isEmpty();
    }

}
