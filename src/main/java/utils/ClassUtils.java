package fr.whyt.pubg.utils;

import java.util.*;

/**
 * Convenient class for checking class type
 */
public class ClassUtils {
    
    private static final List<Class> boxeds = List.of(
            Integer.class, Long.class, Short.class,
            Double.class, Float.class,
            Boolean.class, Byte.class,
            String.class, Character.class
    );
    
    private static final List<Class> iterables = List.of(
            Iterable.class, Collection.class,
            List.class, Set.class, SortedSet.class, Queue.class
    );
    
    private static final List<String> immutables = List.of(
            "SetN", "Set12", "ListN", "List12"
    );
    
    /**
     * Determine the {@link Kind} of the given class type.
     *
     * @param type the class type
     * @return the {@link Kind}
     */
    public static Kind getKind(final Class<?> type) {
        if (type.isPrimitive()) {
            return Kind.PRIMITIVE;
        }
        if (isBoxed(type)) {
            return Kind.BOXED;
        }
        if (isIterable(type)) {
            return Kind.ITERABLE;
        }
        return Kind.OBJECT;
    }
    
    /**
     * Determine the {@link Kind} of the given object.<br>
     *
     * Note: primitive types will be boxed,
     * please use {@link ClassUtils#getKind(Class)} to get primitive kind result.
     *
     * @param object the object
     * @return the {@link Kind}
     */
    public static Kind getKind(final Object object) {
        if (object.getClass().isPrimitive()) {
            return Kind.PRIMITIVE;
        }
        if (isBoxed(object)) {
            return Kind.BOXED;
        }
        if (isIterable(object)) {
            return Kind.ITERABLE;
        }
        return Kind.OBJECT;
    }
    
    /**
     * Convert a string value into a given type.
     *
     * @param type the result class type of the conversion
     * @param value the value to convert
     * @param <T> the result type of the conversion
     * @return the value converted
     * @throws UnsupportedOperationException
     * @throws ClassCastException
     */
    @SuppressWarnings("unchecked")
    public static <T> T convert(final Class<T> type, final String value) throws UnsupportedOperationException, ClassCastException {
        if (StringUtils.isEmpty(value)) {
            return null;
        }
        
        switch (type.getSimpleName()) {
            // primitive boxed type
            case "String":      return type.cast(StringUtils.removeEnclosingQuotes(value));
            case "Integer":     return type.cast(Integer.valueOf(value));
            case "int":         return (T) Integer.valueOf(value);
            case "Long":        return type.cast(Long.valueOf(value));
            case "long":        return (T) Long.valueOf(value);
            case "Short":       return type.cast(Short.valueOf(value));
            case "short":       return (T) Short.valueOf(value);
            case "Double":      return type.cast(Double.valueOf(value));
            case "double":      return (T) Double.valueOf(value);
            case "Float":       return type.cast(Float.valueOf(value));
            case "float":       return (T) Float.valueOf(value);
            case "Boolean":     return type.cast(Boolean.valueOf(value));
            case "boolean":     return (T) Boolean.valueOf(value);
            case "Byte":        return type.cast(Byte.valueOf(value));
            case "byte":        return (T) Byte.valueOf(value);
            case "Character":   return type.cast(value.charAt(0));
            case "char":        return (T) Character.valueOf(value.charAt(0));
            // not found type
            default: throw new UnsupportedOperationException("No converter found for type <" + type + ">");
        }
    }
    
    /**
     * Create an instance of a given collection type
     * @param type the collection type
     * @param <T> the type to instantiate
     * @return the new collection instance
     */
    public static <T> Collection<T> newCollection(final Class<T> type) {
        switch (type.getSimpleName()) {
            case "Queue":       return new LinkedList<>();
            case "Set":
            case "SortedSet":   return new HashSet<>();
            case "Iterable":
            case "Collection":
            case "List":
            default:            return new ArrayList<>();
        }
    }
    
    /**
     * Initialize a primitive value.
     *
     * @param type the primitive class type
     * @param value the value
     * @param <T> the primitive type
     * @return the value as primitive
     */
    public static <T> Object newPrimitive(final Class<T> type, final T value) {
        switch (type.getSimpleName()) {
            case "int": return (int) value;
            case "boolean": return (boolean) value;
            case "char": return (char) value;
            case "double": return (Double) value;
            case "float": return (float) value;
            case "long": return (long) value;
            case "short": return (short) value;
            case "byte": return (byte) value;
            default: return null;
        }
    }
    
    /**
     * Initialize a primitive with default value.
     *
     * @param type the primitive class type
     * @param <T> the primitive type
     * @return the primitive default value
     */
    public static <T> Object newPrimitive(final Class<T> type) {
        switch (type.getSimpleName()) {
            case "int": return 0;
            case "boolean": return false;
            case "char": return '\0';
            case "double": return 0.;
            case "float": return 0F;
            case "long": return 0L;
            case "short": return (short) 0;
            case "byte": return (byte) 0;
            default: return null;
        }
    }
    
    /**
     * Tell if the given type is a primitive boxed type.<br>
     * <br>
     * One of:
     * {@link Integer}, {@link Long}, {@link Short},
     * {@link Double}, {@link Float},
     * {@link Boolean}, {@link Byte},
     * {@link String}, {@link Character}<br>
     *
     * @param type the type to test
     * @return true if type is boxed
     */
    private static boolean isBoxed(final Class<?> type) {
        return boxeds.stream().anyMatch(t -> t.equals(type));
    }
    
    /**
     * Tell if the given object is a primitive boxed type.<br>
     * <br>
     * One of:
     * {@link Integer}, {@link Long}, {@link Short},
     * {@link Double}, {@link Float},
     * {@link Boolean}, {@link Byte},
     * {@link String}, {@link Character}<br>
     *
     * @param object the object to test
     * @return true if object is boxed
     */
    private static boolean isBoxed(final Object object) {
        return boxeds.stream().anyMatch(t -> t.isInstance(object));
    }
    
    /**
     * Tell if the given type is an iterable type.<br>
     * <br>
     * One of:
     * {@link Iterable}, {@link Collection},
     * {@link List}, {@link Set}, {@link SortedSet}, {@link Queue}<br>
     *
     * @param type the type to test
     * @return true if type is an iterable
     */
    private static boolean isIterable(final Class<?> type) {
        return type.isArray()
                       || type.isInstance(Collections.emptyList())
                       || type.isInstance(Collections.emptySet())
                       || type.isInstance(Collections.emptySortedSet())
                       || type.isInstance(new ArrayDeque<>())
                       || immutables.stream().anyMatch(i -> i.equals(type.getSimpleName()));
    }
    
    /**
     * Tell if the given object is an iterable type.<br>
     * <br>
     * One of:
     * {@link Iterable}, {@link Collection},
     * {@link List}, {@link Set}, {@link SortedSet}, {@link Queue}<br>
     *
     * @param object the object to test
     * @return true if object is an iterable
     */
    private static boolean isIterable(final Object object) {
        return object.getClass().isArray() || iterables.stream().anyMatch(t -> t.isInstance(object));
    }
    
    
}
