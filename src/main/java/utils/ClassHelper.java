package fr.whyt.pubg.utils;

import fr.whyt.pubg.utils.annotations.Identity;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * This class provides the Java Object default overridden methods:
 * <ul>
 *     <li>{@link Object#equals}</li>
 *     <li>{@link Object#hashCode}</li>
 *     <li>{@link Object#toString}</li>
 * </ul>
 * - The {@linkplain Object#equals equals} method uses
 * {@linkplain Class#getFields() all fields} from the class annotated with {@link Identity}.<br>
 * - The {@linkplain Object#hashCode hashcode} method uses
 * {@linkplain Class#getFields() all fields} from the class annotated with {@link Identity}.<br>
 * - The {@linkplain Object#toString toString} method uses
 * {@linkplain Class#getDeclaredFields() all declared fields} from the class.<br>
 */
public abstract class ClassHelper {

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (!obj.getClass().isInstance(this)) {
            return false;
        }

        final Class<? extends ClassHelper> clazz = this.getClass();

        return Arrays.stream(clazz.getFields())
                .filter(f -> f.isAnnotationPresent(Identity.class))
                .map(f -> {
                    try {
                        return Objects.deepEquals(f.get(this), f.get(obj));
                    } catch (IllegalArgumentException | IllegalAccessException e) {
                        return false;
                    }
                })
                .reduce(Boolean::logicalAnd)
                .orElse(false);
    }

    @Override
    public int hashCode() {
        final Class<? extends ClassHelper> clazz = this.getClass();

        return Arrays.stream(clazz.getFields())
                .filter(f -> f.isAnnotationPresent(Identity.class))
                .map(f -> {
                    try {
                        return f.get(this);
                    } catch (IllegalArgumentException | IllegalAccessException e) {
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .map(Object::hashCode)
                .reduce(1, (r, i) -> 31 * r + i, Integer::sum);
    }

    @Override
    public String toString() {
        final Class<? extends ClassHelper> clazz = this.getClass();

        final List<Field> declaredFields = Arrays.asList(clazz.getDeclaredFields());
                
        return clazz.getSimpleName() + " { " 
                + declaredFields.stream()
                        .map(f -> {
                            try {
                                return f.getName() + "=" + f.get(this);
                            } catch (IllegalArgumentException | IllegalAccessException e) {
                                return f.getName() + "=null";
                            }
                        })
                        .reduce(StringUtils::joinCommaWithSpace)
                        .orElse("")
                + " }";
    }
    
}
