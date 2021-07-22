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
    public static boolean isBoxed(final Class<?> type) {
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
    public static boolean isBoxed(final Object object) {
        return boxeds.stream().anyMatch(t -> t.isInstance(object));
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
    public static boolean isIterable(final Object object) {
        return iterables.stream().anyMatch(t -> t.isInstance(object));
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
    public static boolean isIterable(final Class<?> type) {
        return type.isInstance(Collections.emptyList())
                       || type.isInstance(Collections.emptySet())
                       || type.isInstance(Collections.emptySortedSet())
                       || type.isInstance(new ArrayDeque<>())
                       || immutables.stream().anyMatch(i -> i.equals(type.getSimpleName()));
    }

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
            case "Character":   return type.cast(Character.valueOf(value.charAt(0)));
            case "char":        return (T) Character.valueOf(value.charAt(0));
            // not found type
            default: throw new UnsupportedOperationException("No converter found for type <" + type + ">");
        }
    }

    public static <T> Collection<T> convertCollection(final Class<T> type) {
        switch (type.getSimpleName()) {
            case "Iterable":
            case "Collection":
            case "List":
            case "List12":
            case "ListN":       return new ArrayList<>();
            case "Set":
            case "Set12":
            case "SetN":
            case "SortedSet":   return new HashSet<>();
            case "Queue":       return new LinkedList<>();
            default:            return new ArrayList<>();
        }
    }
    
}
