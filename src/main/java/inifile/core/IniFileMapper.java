package fr.whyt.pubg.inifile.core;

import fr.whyt.pubg.inifile.annotations.IniOptional;
import fr.whyt.pubg.inifile.annotations.IniProperty;
import fr.whyt.pubg.inifile.annotations.IniRoot;
import fr.whyt.pubg.inifile.annotations.IniWrapper;
import fr.whyt.pubg.inifile.exceptions.ParsingException;
import fr.whyt.pubg.utils.ClassUtils;
import fr.whyt.pubg.utils.Kind;
import fr.whyt.pubg.utils.StringUtils;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * Class used to parse {@link IniFileMapper#serialize}/{@link IniFileMapper#deserializeGeneric} an INI file.<br>
 * <br>
 * This class uses {@link IniRoot} and {@link IniProperty} to match Java classes with INI files.<br>
 * Additional params can be set up using {@link IniOptional} and {@link IniWrapper} annotations.<br>
 * <br>
 * <b>Important:</b> the Java classes designed to be deserialized should implements {@link Serializable},
 * and so on, implements a <b>public no-args constructor</b>.<br>
 * Also, nowadays, the current version of this INI mapper does not use any getter/setter,
 * so the field members of these classes have to be <b>public</b> and <b>not final</b>
 */
public class IniFileMapper {
	
	private static final IniPropertyWrapper DEFAULT_LIST_WRAPPER = IniPropertyWrapper.BRACKET;
	private static final IniPropertyWrapper DEFAULT_OBJECT_WRAPPER = IniPropertyWrapper.PARENTHESIS;
	
	// DESERIALIZER
	
	/**
	 * Deserialize (parse) an INI file into a Java class.<br>
	 *
	 * @param <T>  the Java class type
	 * @param type the Java class of the type
	 *
	 * @return the Java class instance with parsed values
	 */
	public static <T> T deserialize(final Path iniPath, final Class<T> type) throws IOException, ClassNotFoundException {
		final Path absolutePath = iniPath.toAbsolutePath();
		final File iniFile = absolutePath.toFile();
		
		if (!iniFile.exists()) {
			throw new IOException("This file does not exists: " + absolutePath);
		}
		if (!iniFile.isFile()) {
			throw new IOException("This file is not a file: " + absolutePath);
		}
		if (!iniFile.canRead()) {
			throw new IOException("Cannot open and read this file: " + absolutePath);
		}
		
		if (Arrays.stream(type.getInterfaces()).noneMatch(i -> i.equals(Serializable.class))) {
			throw new ClassNotFoundException("This class should implements Serializable!");
		}
		
		if (!type.isAnnotationPresent(IniRoot.class)) {
			return null;
		}
		
		final Map<String, Field> declaredPropertyFields = Arrays.stream(type.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(IniProperty.class))
                .collect(Collectors.toMap(
		                field -> {
                			final String name = field.getAnnotation(IniProperty.class).name();
                			return StringUtils.isEmpty(name) ? field.getName() : name;
                        },
		                Function.identity())
                );
		
		final T object;
		try {
			object = type.getConstructor().newInstance();
		} catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException instantiationException) {
			instantiationException.printStackTrace();
			return null;
		}
		
		try (final Stream<String> stream = Files.lines(absolutePath)) {
			stream.forEach(line -> {
				final Matcher matcher = IniPropertyPattern.key_value_pattern_named.matcher(line);
				if (!matcher.matches()) {
					new ParsingException("Cannot parse this line", line, IniPropertyPattern.key_value_pattern_named.pattern()).printStackTrace();
					return;
				}
				final String key = matcher.group("key");
				String rawValue = matcher.group("value");
				
				if (StringUtils.isBlank(key)) {
					new ParsingException("The key cannot be null, empty, or blank", key, null).printStackTrace();
					return;
				}
				
				final Field field = declaredPropertyFields.get(key);
				if (field == null) {
					// TODO: when no field has been found, use the default mapper Map<String, Object> fields.
					return;
				}
				
				final Class<?> fieldType = field.getType();
				
				Object convertedValue;
				try {
					convertedValue = deserializeGeneric(field, fieldType, rawValue);
				} catch (ParsingException | ClassNotFoundException e) {
					e.printStackTrace();
					return;
				}
				
				try {
					field.set(object, convertedValue);
				} catch (IllegalArgumentException | IllegalAccessException e) {
					e.printStackTrace();
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return object;
	}
	
	/**
	 * Parse an INI object as a Java type.<br>
	 *
	 * @param <T>       the Java class type
	 * @param fieldType the Java object class type
	 * @param rawValue  the raw value to deserialize
	 *
	 * @return the Java class instance with parsed value
	 */
	// FIXME: public for tests purpose, should be private
	public static <T> T deserializeGeneric(final Field field, Class<T> fieldType, final String rawValue)
			throws ParsingException, ClassNotFoundException {
		final Kind fieldKind = ClassUtils.getKind(fieldType);
		
		final IniProperty property = field.getAnnotation(IniProperty.class);
		
		if (fieldKind == Kind.PRIMITIVE || fieldKind == Kind.BOXED) {
			String value = rawValue;
			
			final boolean raw = property.raw();
			if (!raw) {
				value = StringUtils.removeEnclosingQuotes(rawValue);
			}
			
			final String pattern = property.pattern();
			if (!StringUtils.isEmpty(pattern)) {
				if (!value.matches(pattern)) {
					throw new ParsingException("Cannot parse the value! The pattern does not match the value", value, pattern);
				}
			}
			
			final Object convertedValue = deserializeValue(fieldType, value);
			return fieldType.cast(convertedValue);
		}
		
		if (fieldKind == Kind.ITERABLE) {
			IniPropertyWrapper iterableWrapper = DEFAULT_LIST_WRAPPER;
			if (fieldType.isAnnotationPresent(IniWrapper.class)) {
				iterableWrapper = fieldType.getDeclaredAnnotation(IniWrapper.class).value();
			}
			
			final Class<?> elementType = (Class<?>) ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0];
			IniPropertyWrapper elementWrapper = DEFAULT_OBJECT_WRAPPER;
			if (elementType.isAnnotationPresent(IniWrapper.class)) {
				elementWrapper = elementType.getDeclaredAnnotation(IniWrapper.class).value();
			} else {
				final Kind elementKind = ClassUtils.getKind(elementType);
				if (elementKind == Kind.PRIMITIVE || elementKind == Kind.BOXED) {
					elementWrapper = null;
				}
				if (elementKind == Kind.ITERABLE) {
					elementWrapper = DEFAULT_LIST_WRAPPER;
				}
			}
			
			@SuppressWarnings({"unchecked", "rawtypes"})
			final Iterable<?> iterable = deserializeIterable(field, (Class<Collection>) fieldType, iterableWrapper, elementType, elementWrapper, rawValue);
			return fieldType.cast(iterable);
		}
		
		// else it's an object
		IniPropertyWrapper wrapper = DEFAULT_OBJECT_WRAPPER;
		final IniWrapper objectWrapper = fieldType.getDeclaredAnnotation(IniWrapper.class);
		if (objectWrapper != null) {
			wrapper = objectWrapper.value();
		}
		final Object object = deserializeObject(fieldType, wrapper, rawValue);
		return fieldType.cast(object);
	}
	
	/**
	 * Deserialize an INI primitive value in Java object.<br>
	 *
	 * @param valueType the java type of the deserialized value
	 * @param value     the value to deserialize
	 *
	 * @return the deserialized INI value.
	 */
	private static Object deserializeValue(final Class<?> valueType, final String value) {
		return ClassUtils.convert(valueType, value);
	}
	
	/**
	 * Deserialize an INI list in Java collection.<br>
	 *
	 * @param <T>             the java type of the collection
	 * @param <U>             the java type of the collection elements
	 * @param field           the java field associated with the object
	 * @param iterableType    the java class type of collection
	 * @param iterableWrapper the wrapper enclosing the list
	 * @param elementType     the java class type of the deserialized value
	 * @param elementWrapper  the wrapper enclosing the list
	 * @param rawValue        the whole INI object containing the list
	 * @return the deserialized INI list.
	 */
	private static <T extends Collection<U>, U>
			Collection<U> deserializeIterable(final Field field,
			                                  final Class<T> iterableType,
			                                  final IniPropertyWrapper iterableWrapper,
			                                  final Class<U> elementType,
			                                  final IniPropertyWrapper elementWrapper,
			                                  final String rawValue)
			throws ParsingException, ClassNotFoundException {
		final Collection<U> values = ClassUtils.newCollection(iterableType);
		
		if (StringUtils.isBlank(rawValue)) {
			throw new ParsingException("Null lists are not authorized", rawValue, null);
		}
		
		if (iterableWrapper == null) {
			throw new ParsingException("Cannot parse a list without enclosing wrapper!", rawValue, null);
		}
		
		if (rawValue.equals("" + iterableWrapper.head + iterableWrapper.tail)) {
			return values;
		}
		
		final Set<Character> headWrappers = new HashSet<>();
		final Set<Character> tailWrappers = new HashSet<>();
		
		headWrappers.add(iterableWrapper.head);
		tailWrappers.add(iterableWrapper.tail);
		
		if (elementWrapper != null) {
			headWrappers.add(elementWrapper.head);
			tailWrappers.add(elementWrapper.tail);
		}
		
		final String valueStart = rawValue.substring(1);
		
		final StringBuilder stringBuilder = new StringBuilder();
		int cpt = 1;
		for (final char c : valueStart.toCharArray()) {
			if (headWrappers.contains(c)) { cpt++; }
			if (tailWrappers.contains(c)) { cpt--; }
			// if list end has been reached
			if (cpt == 0) {
				// the last element or the first (and unique for singleton list)
				if (!StringUtils.isEmpty(stringBuilder.toString())) {
					values.add(deserializeGeneric(field, elementType, stringBuilder.toString()));
				}
				// leave loop, list is complete
				break;
			}
			// if list separator has been reached, only at the first level of the list
			if (c == IniPropertyPattern.list_separator && cpt == 1) {
				values.add(deserializeGeneric(field, elementType, stringBuilder.toString()));
				// reset the element buffer to leave the place to the new incoming element
				stringBuilder.delete(0, stringBuilder.length());
				// skip the list separator
				continue;
			}
			// element list parsing in progress
			stringBuilder.append(c);
		}
		
		return values;
	}
	
	/**
	 * Deserialize an INI object in Java object.<br>
	 *
	 * @param <T>         the java type of the object
	 * @param valueType   the java class type of the object
	 * @param wrapper     the wrapper enclosing the list
	 * @param rawValue    the whole INI object containing the object
	 * @return the deserialized INI object.
	 */
	private static <T> T deserializeObject(final Class<T> valueType,
	                                       final IniPropertyWrapper wrapper,
	                                       final String rawValue)
			throws ParsingException, ClassNotFoundException {
		if (Arrays.stream(valueType.getInterfaces()).noneMatch(i -> i.equals(Serializable.class))) {
			throw new ClassNotFoundException("This class should implements Serializable!");
		}
		
		if (StringUtils.isBlank(rawValue)) {
			throw new ParsingException("Null objects are not authorized", rawValue, null);
		}
		
		if (rawValue.equals("" + wrapper.head + wrapper.tail)) {
			throw new ParsingException("Empty objects are not authorized", rawValue, null);
		}
		
		final String valueStart = rawValue.substring(1);
		
		final Map<String, Field> declaredPropertyFields = Arrays.stream(valueType.getDeclaredFields())
				.filter(field -> field.isAnnotationPresent(IniProperty.class))
                .collect(Collectors.toMap(
                		field -> {
                			final String name = field.getAnnotation(IniProperty.class).name();
                			return StringUtils.isEmpty(name) ? field.getName() : name;
                        },
		                Function.identity())
                );
		
		final T object;
		try {
			object = valueType.getConstructor().newInstance();
		} catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException instantiationException) {
			instantiationException.printStackTrace();
			return null;
		}
		
		final StringBuilder stringBuilder = new StringBuilder();
		int cpt = 1;
		for (final char c : valueStart.toCharArray()) {
			if (c == wrapper.head) { cpt++; }
			if (c == wrapper.tail) { cpt--; }
			// if object end has been reached
			if (cpt == 0) {
				return object;
			}
			// if object member separator has been reached, only at the first level of the object
			if (c == IniPropertyPattern.object_key_value_separator && cpt == 1) {
				final String key_value = stringBuilder.toString();
				final Matcher matcher = IniPropertyPattern.key_value_pattern_named.matcher(key_value);
				
				// skip this key/value pair
				if (!matcher.matches()) {
					new ParsingException("Cannot parse this object key/value", key_value, IniPropertyPattern.key_value_pattern_named.pattern()).printStackTrace();
					continue;
				}
				final String key = matcher.group("key");
				String value = matcher.group("value");
				
				if (StringUtils.isBlank(key)) {
					new ParsingException("The key cannot be null, empty, or blank", key, null).printStackTrace();
					continue;
				}
				
				final Field field = declaredPropertyFields.get(key);
				if (field == null) {
					// TODO: when no field has been found, use the default mapper Map<String, Object> fields.
					continue;
				}
				
				final Class<?> fieldType = field.getType();
				
				final Object convertedValue;
				try {
					convertedValue = deserializeGeneric(field, fieldType, value);
				} catch (ParsingException e) {
					e.printStackTrace();
					continue;
				}
				
				try {
					field.set(object, convertedValue);
				} catch (IllegalArgumentException | IllegalAccessException e) {
					e.printStackTrace();
				}
				
				// reset the key/value buffer to leave the place to the new incoming key/value pair
				stringBuilder.delete(0, stringBuilder.length());
				
				// skip the list separator
				continue;
			}
			stringBuilder.append(c);
		}
		
		return object;
	}
	
	
	
	// SERIALIZER
	
	/**
	 * Write an INI file from a Java class.<br>
	 *
	 * @param <T>    The Java class type
	 * @param object the Java class instance
	 *
	 * @return the serialized property from the Java class
	 */
	public static <T> String serialize(final T object) {
		boolean isRoot = object.getClass().isAnnotationPresent(IniRoot.class);
		
		final BinaryOperator<String> joiner = isRoot ? StringUtils::joinLN : StringUtils::joinComma;
		
		//noinspection UnnecessaryLocalVariable
		final String data = Arrays.stream(object.getClass().getDeclaredFields())
				.filter(f -> f.isAnnotationPresent(IniProperty.class))
				.map(field -> {
					final IniProperty iniProperty = field.getAnnotation(IniProperty.class);
					final String name = StringUtils.isEmpty(iniProperty.name()) ? field.getName() : iniProperty.name();
					final String format = iniProperty.format();
					final boolean raw = iniProperty.raw();
					final boolean optional = iniProperty.optional();
					
					Object value;
					try {
						value = field.get(object);
						if (optional) {
							Object fieldOptionalValue = getFieldOptionalValue(field);
							if (Objects.equals(value, fieldOptionalValue)) {
								return null;
							}
						}
					} catch (IllegalArgumentException | IllegalAccessException e) {
						e.printStackTrace();
						value = null;
					}
					
					return name + "=" + serializeGeneric(value, format, raw);
				})
			    .filter(Objects::nonNull)
				.reduce(joiner)
				.orElse("");
		
		return data;
	}
	
	/**
	 * Serialize an object according to its type.<br>
	 * <br>
	 * {@code format} is used only when the object type is {@link Kind#PRIMITIVE} or {@link Kind#BOXED}.<br>
	 * {@code raw} is used only when the object is instance of String.<br>
	 *
	 * @param object the object to serialize
	 * @param format the format to apply on primitive or boxed objects
	 * @param raw    add or remove "" on String objects
	 * @param <T>    the object type
	 *
	 * @return the serialized object
	 */
	private static <T> String serializeGeneric(final T object, final String format, final boolean raw) {
		if (object == null) {
			return "";
		}
		
		final Class<?> objectType = object.getClass();
		final Kind objectKind = ClassUtils.getKind(objectType);
		
		if (objectKind == Kind.PRIMITIVE || objectKind == Kind.BOXED) {
			String value = StringUtils.isEmpty(format) ? object.toString() : String.format(format, object);
			
			if (object instanceof String && !raw) {
				value = "\"" + value + "\"";
			}
			
			return value;
		}
		
		if (objectKind == Kind.ITERABLE) {
			Iterable<?> values = (Iterable<?>) object;
			return "("
				       + StreamSupport.stream(values.spliterator(), false)
						         .map(value -> IniFileMapper.serializeGeneric(value, format, false))
						         .reduce(StringUtils::joinComma)
						         .orElse("")
			       + ")";
		}
		
		return "(" + serialize(object) + ")";
	}
	
	/**
	 * Get the associated optional value for this field, if any,
	 * or the default optional value {@code null}.<br>
	 * <br>
	 * @param field the field to get the optional value
	 * @return the optional value converted in this field type
	 */
	private static Object getFieldOptionalValue(final Field field) {
		final Class<?> fieldType = field.getType();
		
		IniOptionalValue optionalValue = IniOptionalValue.NULL;
		if (field.isAnnotationPresent(IniOptional.class)) {
			final IniOptional iniOptionalProp = field.getAnnotation(IniOptional.class);
			optionalValue = iniOptionalProp.value();
		}
		
		@SuppressWarnings({"UnnecessaryLocalVariable"})
		final Object fieldDefaultValue = ClassUtils.convert(fieldType, optionalValue.getValue().toString());
		
		return fieldDefaultValue;
	}
	
}
