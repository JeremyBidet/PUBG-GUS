package fr.whyt.pubg.inifile.annotations;

import fr.whyt.pubg.inifile.core.IniPropertyPattern;

import java.lang.annotation.*;

/**
 * This annotation define a Java field as an INI property.<br>
 * 
 * Three properties can be defined:
 * <ul>
 *  <li>{@link IniProperty#name}</li>
 *  <li>{@link IniProperty#optional}</li>
 *  <li>{@link IniProperty#pattern}</li>
 *  <li>{@link IniProperty#format}</li>
 *  <li>{@link IniProperty#raw}</li>
 * </ul>
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface IniProperty {

    /**
     * Used to define the property name.<br>
     * If empty, the field name will be used as property name.<br>
     * 
     * @return the property name
     */
    String name() default "";
    
    /**
     * Define the order of the property in the INI object.<br>
     * @return the order of the property.
     */
    int order() default 0;
    
    /**
     * Tell if this property is optional.<br>
     * By default, if the property is optional and its value is null,
     * this property won't be parsed neither printed.<br>
     * <br>
     * But, if null is not the desired value to determine if the property is optional are not,
     * you can inject additional information using {@link IniOptional} annotation.<br>
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
     * but if you provide a pattern, such as {@code "\\w+"},
     * the property's value has to be a simple word String: [a-zA-Z0-9_]+.<br>
     * <br>
     * Note: the pattern is intended to be wrote as a Java regex.<br>
     * <br>
     * Note: when a pattern is given, we recommend to provide
     * and match a {@linkplain IniProperty#format format} with it.<br>
     * <br>
     * Convenient constants are available in {@link IniPropertyPattern} class, such as:
     * <ul>
     *     <li>{@link IniPropertyPattern#BOOLEAN}</li>
     *     <li>{@link IniPropertyPattern#DOUBLE}</li>
     *     <li>{@link IniPropertyPattern#INTEGER}</li>
     *     <li>{@link IniPropertyPattern#WORD}</li>
     *     <li>{@link IniPropertyPattern#STRING}</li>
     *     <li>{@link IniPropertyPattern#DOUBLE_QUOTED_WORD}</li>
     *     <li>{@link IniPropertyPattern#DOUBLE_QUOTED_STRING}</li>
     * </ul>
     *
     * @return the pattern used to parse the property's value
     */
    String pattern() default "";

    /**
     * Used to define the print format for this property's value.<br>
     * If empty, the standard Java print format will be used.<br>
     * <br>
     * Note: the format is intended to be wrote as a Java {@link String#format}.<br>
     * <br>
     * Note: when a {@linkplain IniProperty#pattern() pattern} is used,
     * we recommend to match the format with it.<br>
     * <br>
     * @return the format used to print the property's value
     */
    String format() default "";

    /**
     * Tell if the property will be parsed/printed as raw value.<br>
     * <br>
     * Note: This property is only used for {@link String} properties,
     * as Strings can be wrote with or without "". {@code raw=true} means no "".
     * @return true if raw is intended
     */
    boolean raw() default false;

}