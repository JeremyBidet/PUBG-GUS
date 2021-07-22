package fr.whyt.pubg.inifile.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation define a Java field as an INI property.<br>
 * 
 * Three properties can be defined:
 * <ul>
 *  <li>{@link Property#name}</li>
 *  <li>{@link Property#pattern}</li>
 *  <li>{@link Property#format}</li>
 * </ul>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface Property {

    /**
     * Used to define the property name.<br>
     * If empty, the field name will be used as property name.<br>
     * 
     * @return the property name
     */
    String name() default "";
    
    /**
     * Tell if this property is optional.<br>
     * By default, if the property is optional and its value is null,
     * this property won't be parsed neither printed.<br>
     * <br>
     * But, if null is not the desired value to determine if the property is optional are not,
     * you can inject additional information using {@link Optional} annotation.<br>
     *
     * @return true if this property is optional
     */
    boolean optional() default false;

    /**
     * Used to define a parsing pattern for this property's value.<br>
     * If empty, the property will accept all data matching the field type.<br>
     * <br>
     * For example:<br>
     * if the field type is {@link String}, by default the property will accept any String (any characters) as value,
     * but if you provide a pattern, such as {@code "\\w+"}, the property's value has to be a simple word String: [a-zA-Z0-9_]+.<br>
     * <br>
     * Note: the pattern is intended to be wrote as a Java regex.<br>
     * 
     * @return the pattern used to parse the property's value
     */
    String pattern() default "";

    /**
     * Used to define the print format for this property's value.<br>
     * If empty, the standard Java print format will be used.<br>
     * <br>
     * Note: the format is intended to be wrote as a Java {@link String#format}.<br>
     * 
     * @return the format used to print the property's value
     */
    String format() default "";

    /**
     * Tell if the property will be parsed/printed as raw value.<br>
     * <br>
     * Note: This property is mainly used for {@link String} properties,
     * as Strings can be wrote with or without "". {@code raw=true} means no "".
     * @return true if raw is intended
     */
    boolean raw() default false;
    
    

}