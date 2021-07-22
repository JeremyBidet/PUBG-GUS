package fr.whyt.pubg.inifile.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is used in accordance with the {@linkplain Property#optional} property, if set to true.<br>
 * <br>
 * The {@link Optional#value} is used to determine the value to make the property optional.<br>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface Optional {
	
	/**
	 * If the {@link Property} is optional, then instead of considering
	 * {@code null}, for boxed type, and {@code false, 0, ''} for primitive type,
	 * as the discriminant value for the property, we'll use the given value.<br>
	 * <br>
	 * For example: given an optional integer property named {@code distance},
	 * set to a positive integer when valid and negative integer when invalid (-1).<br>
	 * We don't need to serialize the {@code distance=-1} property because it's invalid,
	 * but only {@code distance=0+} property when positive.<br>
	 * Then the {@code -1} value is the optional value.<br>
	 * In other words, when the Java field {@code int distance = -1},
	 * we don't want to serialize this property.<br>
	 * For deserialization, if the {@code distance=-1} property is present in the data source,
	 * the property will be parsed and set to the Java field.<br>
	 * But for serialization, because the Java field {@code int distance = -1},
	 * we won't serialize this property. It will remains absent.<br>
	 *<br>
	 * @return the value used to replace the default value
	 */
	String value() default "null";
	
}
