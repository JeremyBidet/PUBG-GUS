package fr.whyt.pubg.inifile.annotations;

import fr.whyt.pubg.inifile.core.IniOptionalValue;

import java.lang.annotation.*;

/**
 * This annotation is used in accordance with the {@linkplain IniProperty#optional} property, if set to true.<br>
 * <br>
 * The {@link IniOptional#value} is used to determine the value to make the property optional.<br>
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface IniOptional {
	
	/**
	 * If the {@link IniProperty} is optional, then instead of considering
	 * {@code null} (=no value) as the discriminant value for the property,
	 * we'll use the given {@linkplain IniOptionalValue value}.<br>
	 * <br>
	 * Convenient predefined values exist:
	 * <ul>
	 *     <li>{@link IniOptionalValue#NULL} => {@code no value for any type}</li>
	 *     <li>{@link IniOptionalValue#BOOLEAN_TRUE} => {@code true}</li>
	 *     <li>{@link IniOptionalValue#BOOLEAN_FALSE} => {@code false}</li>
	 *     <li>{@link IniOptionalValue#INTEGER_0} => {@code 0}</li>
	 *     <li>{@link IniOptionalValue#DOUBLE_0} => {@code 0.0}</li>
	 *     <li>{@link IniOptionalValue#STRING_EMPTY} => {@code }</li>
	 *     <li>{@link IniOptionalValue#DOUBLE_QUOTED_STRING_EMPTY} => {@code ""}</li>
	 * </ul>
	 * You also can customize {@link IniOptionalValue} using the {@link IniOptionalValue#customize} method.<br>
	 * It only works with following enum values:
	 * <ul>
	 *     <li>{@link IniOptionalValue#BOOLEAN}</li>
	 *     <li>{@link IniOptionalValue#INTEGER}</li>
	 *     <li>{@link IniOptionalValue#DOUBLE}</li>
	 *     <li>{@link IniOptionalValue#STRING}</li>
	 *     <li>{@link IniOptionalValue#DOUBLE_QUOTED_STRING}</li>
	 * </ul>
	 * Note: once {@linkplain IniOptionalValue#customize custom} is called, it will modify the default value for each occurrence of this enum value.<br>
	 * <br>
	 * For example:<br>
	 * given an optional integer property named {@code divisor},
	 * valid values are negative or positive, but not zero (division by 0 is impossible).<br>
	 * We don't need to serialize the {@code divisor=0} property because it's invalid.
	 * Then we should use the {@linkplain IniOptionalValue#INTEGER_0 INTEGER_0} value for the annotation value.<br>
	 * In other words, when the Java field {@code int divisor = 0}, we won't serialize this property.<br>
	 * For deserialization, the Java field will be set to 0.<br>
	 *<br>
	 * @return the value used to replace the default value
	 */
	IniOptionalValue value() default IniOptionalValue.NULL;
	
}
