package fr.whyt.pubg.inifile.core;

import fr.whyt.pubg.inifile.annotations.IniProperty;
import fr.whyt.pubg.inifile.annotations.IniRoot;
import fr.whyt.pubg.inifile.annotations.IniWrapper;
import fr.whyt.pubg.utils.ClassUtils;
import fr.whyt.pubg.utils.Kind;
import fr.whyt.pubg.utils.StringUtils;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.SortedMap;
import java.util.function.BinaryOperator;
import java.util.regex.Pattern;

@SuppressWarnings("SpellCheckingInspection")
public class IniPropertyPattern {
	
	// Useful patterns for format option in IniProperty annotation
	
	/**
	 * true | True => true<br>
	 * false | False => false
	 */
	public static final String BOOLEAN = "[Tt]rue|[Ff]alse";
	/**
	 * 1.0, -1.2345
	 */
	public static final String DOUBLE = "-?\\d+\\.\\d+";
	/**
	 * 1, -2
	 */
	public static final String INTEGER = "-?\\d+";
	/**
	 * every characters excepted spaces
	 */
	public static final String CHARACTER = "[^\\s]";
	/**
	 * abc-DEF_012
	 */
	public static final String WORD = "[a-zA-Z][\\w\\-]*";
	/**
	 * All characters excepted {@code \r} (carriage return) and {@code \n} (line return).<br>
	 * Double quote {@code "} should be escaped {@code \"}.<br>
	 * <br>
	 * Note: in Java double quote is already escaped in strings {@code \"}, so please use double escape {@code \\\"}
	 */
	public static final String STRING = "(?:\\\"|[^\"\\r\\n])+";
	/**
	 * Like {@linkplain IniPropertyPattern#WORD WORD} but with enclosing double quote {@code "word"}.
	 */
	public static final String DOUBLE_QUOTED_WORD = "\"((?:" + WORD + ")?)\"";
	/**
	 * Like {@linkplain IniPropertyPattern#STRING STRING} but with enclosing double quote {@code "string"}.
	 */
	public static final String DOUBLE_QUOTED_STRING = "\"((?:" + STRING + ")?)\"";
	
	
	// Local regex and pattern used in the mapper IniFileMapper
	
	static final char list_separator = ',';
	static final char object_key_value_separator = ',';
	
	private static final String key_regex = WORD;
	private static final String value_regex = ".+";
	private static final String key_value_regex_named = "(?<key>" + key_regex + ")" + "=" + "(?<value>" + value_regex + ")";
	
	static final Pattern key_value_pattern_named = Pattern.compile(IniPropertyPattern.key_value_regex_named);
	
	/**
	 * Construct the full {@link Pattern} of this INI object.<br>
	 * <br>
	 * @param iniType the INI object class type to construct the pattern
	 * @param <T>     the INI object type
	 * @return the built pattern
	 */
	public static <T extends Serializable> Pattern buildPattern(final Class<T> iniType) {
		return Pattern.compile(buildRegex(iniType));
	}
	
	/**
	 * Build the full regex of this INI object.<br>
	 * <br>
	 * @param iniType the INI object class type to construct the regex
	 * @param <T>     the INI object type
	 * @return the built regex
	 */
	private static <T extends Serializable> String buildRegex(final Class<T> iniType) {
		final boolean isRoot = iniType.isAnnotationPresent(IniRoot.class);
		final BinaryOperator<String> joiner = isRoot ? StringUtils::joinLN : StringUtils::joinComma;
		
		final IniWrapper iniTypeWrapperProp = iniType.getAnnotation(IniWrapper.class);
		final IniPropertyWrapper iniTypeWrapper = iniTypeWrapperProp != null ? iniTypeWrapperProp.value() : IniFileMapper.DEFAULT_OBJECT_WRAPPER;
		
		final SortedMap<String, Field> declaredPropertyFields = IniFileMapper.getDeclaredIniProperties(iniType);
		
		final StringBuilder stringBuilder = new StringBuilder();
		
		stringBuilder.append("\\").append(iniTypeWrapper.head);
		
		stringBuilder.append(declaredPropertyFields.entrySet().stream()
				.map((entry) -> {
					final Field field = entry.getValue();
					final IniProperty iniProperty = field.getAnnotation(IniProperty.class);
					final boolean optional = iniProperty.optional();
					final String entryRegex = entry.getKey() + "=" + "(" + buildRegexGeneric(field, field.getType()) + ")";
					return optional ? "(" + entryRegex + ")?" : entryRegex;
				})
				.reduce(joiner)
				.orElse(""));
		
		stringBuilder.append("\\").append(iniTypeWrapper.tail);
		
		return stringBuilder.toString();
	}
	
	/**
	 * Construct the regex according to the given field type.<br>
	 * If primitive/boxed type: will return a predefined associated pattern (or a custom pattern if any)<br>
	 * If iterable type: will return a repeated pattern of its elements according to its wrapper character<br>
	 * Else, an object type: will return the object pattern according to its {@linkplain IniProperty property} fields<br>
	 * <br>
	 * @param field     the Java field of the property
	 * @param fieldType the Java class type of the property
	 * @return the built regex
	 */
	private static String buildRegexGeneric(final Field field, final Class<?> fieldType) {
		
		final Kind fieldKind = ClassUtils.getKind(fieldType);
		
		if (fieldKind == Kind.PRIMITIVE || fieldKind == Kind.BOXED) {
			final IniProperty iniProperty = field.getAnnotation(IniProperty.class);
			final String pattern = iniProperty.pattern();
			final String regex = !StringUtils.isEmpty(pattern) ? pattern : getTypeRegex(fieldType, iniProperty.raw());
			return "(?:" + regex + ")";
		}
		
		if (fieldKind == Kind.ITERABLE) {
			final IniWrapper iniWrapper = field.getAnnotation(IniWrapper.class);
			final IniPropertyWrapper iniPropertyWrapper = iniWrapper != null ? iniWrapper.value() : IniFileMapper.DEFAULT_LIST_WRAPPER;
			
			// FIXME: fails when List<List<>>
			final Class<?> elementType = (Class<?>) ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0];
			
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append("\\").append(iniPropertyWrapper.head);
			
			stringBuilder.append("(").append("(?:");
			
			final String elementRegex = buildRegexGeneric(field, elementType);
			stringBuilder.append(elementRegex).append("(?:,").append(elementRegex).append(")*");
			
			stringBuilder.append(")?").append(")");
			
			stringBuilder.append("\\").append(iniPropertyWrapper.tail);
			
			return stringBuilder.toString();
		}
		
		// else it's an object
		@SuppressWarnings("unchecked")
		final String regex = buildRegex((Class<? extends Serializable>) fieldType);
		return regex;
	}
	
	/**
	 * Get the regex associated to the given type.<br>
	 * <br>
	 * @param type the class type to match
	 * @param raw  used for strings only, determine if the string will be double-quoted are not
	 * @return the associated regex
	 */
	private static String getTypeRegex(final Class<?> type, final boolean raw) {
		switch (type.getSimpleName()) {
			// primitive boxed type
			case "String":      return raw ? IniPropertyPattern.STRING : IniPropertyPattern.DOUBLE_QUOTED_STRING;
			case "Integer":
			case "int":
			case "Long":
			case "long":
			case "Short":
			case "short":
			case "Byte":
			case "byte":        return IniPropertyPattern.INTEGER;
			case "Double":
			case "double":
			case "Float":
			case "float":       return IniPropertyPattern.DOUBLE;
			case "Boolean":
			case "boolean":     return IniPropertyPattern.BOOLEAN;
			case "Character":
			case "char":        return IniPropertyPattern.CHARACTER;
			// not found type
			default: throw new UnsupportedOperationException("No pattern associated with this type <" + type + ">");
		}
	}
	
}
