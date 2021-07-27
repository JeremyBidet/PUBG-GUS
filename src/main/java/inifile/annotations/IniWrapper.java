package fr.whyt.pubg.inifile.annotations;

import fr.whyt.pubg.inifile.core.IniPropertyWrapper;

import java.lang.annotation.*;

/**
 * This annotation is used to define a wrapper for custom object and list in an INI file.<br>
 * @see IniPropertyWrapper
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD})
public @interface IniWrapper {
	
	/**
	 * Used to define a {@linkplain IniPropertyWrapper wrapper} for the property value.<br>
	 * <br>
	 * It is not intended to be used with primitive values (such as integer, string, boolean, etc...),
	 * but further for object and list values.<br>
	 * It is also high recommended to set a wrapper for list and custom object.<br>
	 * <br>
	 * Available values are:
	 * <ul>
	 *     <li>{@linkplain IniPropertyWrapper#PARENTHESIS PARENTHESIS} => {@code (...)}</li>
	 *     <li>{@linkplain IniPropertyWrapper#BRACKET BRACKET} => {@code [...]}</li>
	 *     <li>{@linkplain IniPropertyWrapper#BRACE BRACE} => {@code {...}}</li>
	 * </ul>
	 * @return
	 */
	IniPropertyWrapper value() default IniPropertyWrapper.PARENTHESIS;
	
}
