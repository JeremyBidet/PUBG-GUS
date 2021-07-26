package fr.whyt.pubg.inifile.core;

import fr.whyt.pubg.inifile.annotations.Optional;
import fr.whyt.pubg.inifile.annotations.Property;
import fr.whyt.pubg.inifile.annotations.Root;
import fr.whyt.pubg.utils.ClassUtils;
import fr.whyt.pubg.utils.Kind;
import fr.whyt.pubg.utils.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Class used to parse {@link IniFileMapper#serialize}/{@link IniFileMapper#deserialize} an INI file.<br>
 */
public class IniFileMapper {
	
	/**
	 * Parse an INI object as a Java class.<br>
	 *
	 * @param <T>  the Java class type
	 * @param type the Java class of the type
	 *
	 * @return the Java class instance with parsed values
	 */
	public static <T> T deserialize(final String iniObject, final Class<T> type) {
		final Map<String, Field> declaredPropertyFields = Arrays.stream(type.getDeclaredFields())
				.filter(f -> f.isAnnotationPresent(Property.class))
				.collect(Collectors.toMap(
					f -> {
						final String name = f.getAnnotation(Property.class).name();
						return StringUtils.isEmpty(name) ? f.getName() : name;
					},
					Function.identity())
				);
		final Kind objectKind = ClassUtils.getKind(type);
		
		final T object;
		try {
			if (objectKind == Kind.PRIMITIVE) {
				object = (T) ClassUtils.newPrimitive(type);
			} else {
				object = type.getConstructor().newInstance();
			}
		} catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException instantiationException) {
			instantiationException.printStackTrace();
			return null;
		}
		
		final Matcher matcher = IniFileRegex.key_value_pattern_named.matcher(iniObject);
		
		while (matcher.find()) {
			final String key = matcher.group("key");
			String value = matcher.group("value");
			
			if (StringUtils.isEmpty(key)) {
				continue;
			}
			
			final Field field = declaredPropertyFields.get(key);
			if (field == null) {
				continue;
			}
			
			// TODO: implement property.pattern()
			final String pattern = field.getAnnotation(Property.class).pattern();
			if (!StringUtils.isEmpty(pattern)) {
				if (!value.matches(pattern)) {
					continue;
				}
			}
			
			final Class<?> fieldType = field.getType();
			
			final Kind fieldKind = ClassUtils.getKind(fieldType);
			
			Object convertedValue;
			if (fieldKind == Kind.PRIMITIVE || fieldKind == Kind.BOXED) {
				convertedValue = deserialize(fieldType, value);
			}
			else if (fieldKind == Kind.ITERABLE) {
				final Class<?> elementType = (Class<?>) ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0];
				convertedValue = deserializeIterable(fieldType, elementType, iniObject, matcher.start("value"));
			} else {
				convertedValue = deserializeObject(fieldType, iniObject, matcher.start("value"));
			}
			
			try {
				field.set(object, convertedValue);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		
		return object;
	}
	
	/**
	 * Deserialize an INI primitive value in Java object.<br>
	 *
	 * @param valueType the java type of the deserialized value
	 * @param value     the value to deserialize
	 *
	 * @return the deserialized INI value.
	 */
	private static Object deserialize(final Class<?> valueType, final String value) {
		final Kind valueKind = ClassUtils.getKind(valueType);
		
		if (valueKind == Kind.PRIMITIVE || valueKind == Kind.BOXED) {
			return ClassUtils.convert(valueType, value);
		}
		
		return deserialize(value, valueType);
	}
	
	/**
	 * Deserialize an INI list in Java collection.<br>
	 *
	 * @param iterableType   the java type of collection
	 * @param elementType the java type of the deserialized value
	 * @param iniObject   the whole INI object containing the list
	 * @param regionStart the start region of the list
	 *
	 * @return the deserialized INI list.
	 */
	@SuppressWarnings("unchecked")
	private static Object deserializeIterable(final Class<?> iterableType, final Class<?> elementType, final String iniObject, final int regionStart) {
		final Collection<Object> values = (Collection<Object>) ClassUtils.newCollection(iterableType);
		
		if (StringUtils.isEmpty(iniObject)) {
			return values;
		}
		
		final String valueStart = iniObject.substring(regionStart+1);
		
		final StringBuilder stringBuilder = new StringBuilder();
		int cpt = 1;
		for (final char c : valueStart.toCharArray()) {
			if (c == '(') { cpt++; }
			if (c == ')') { cpt--; }
			if (cpt == 0) {
				if (!StringUtils.isEmpty(stringBuilder.toString())) {
					values.add(deserialize(elementType, stringBuilder.toString()));
				}
				break;
			}
			if (c == ',' && cpt == 1) {
				values.add(deserialize(elementType, stringBuilder.toString()));
				stringBuilder.delete(0, stringBuilder.length());
				continue;
			}
			stringBuilder.append(c);
		}
		
		return values;
	}
	
	/**
	 * Deserialize an INI object in Java object.<br>
	 *
	 * @param valueType   the java type of the object
	 * @param iniObject   the whole INI object containing the object
	 * @param regionStart the start region of the object
	 *
	 * @return the deserialized INI object.
	 */
	@SuppressWarnings("unchecked")
	private static Object deserializeObject(final Class<?> valueType, final String iniObject, final int regionStart) {
		if (StringUtils.isEmpty(iniObject)) {
			return null;
		}
		
		final String valueStart = iniObject.substring(regionStart+1);
		
		final StringBuilder stringBuilder = new StringBuilder();
		int cpt = 1;
		for (final char c : valueStart.toCharArray()) {
			if (c == '(') { cpt++; }
			if (c == ')') { cpt--; }
			if (cpt == 0) {
				break;
			}
			stringBuilder.append(c);
		}
		
		return deserialize(valueType, stringBuilder.toString());
	}
	
	/**
	 * Write an INI file from a Java class.<br>
	 *
	 * @param <T>    The Java class type
	 * @param object the Java class instance
	 *
	 * @return the serialized property from the Java class
	 */
	public static <T> String serialize(final T object) {
		boolean isRoot = object.getClass().isAnnotationPresent(Root.class);
		
		final BinaryOperator<String> joiner = isRoot ? StringUtils::joinLN : StringUtils::joinComma;
		
		final String data = Arrays.asList(object.getClass().getDeclaredFields()).stream()
				.filter(f -> f.isAnnotationPresent(Property.class))
				.map(field -> {
					final Property property = field.getAnnotation(Property.class);
					final String name = StringUtils.isEmpty(property.name()) ? field.getName() : property.name();
					final String format = property.format();
					final boolean raw = property.raw();
					final boolean optional = property.optional();
					
					Object value;
					try {
						value = field.get(object);
						
						if (optional) {
							final Class<?> fieldType = field.getType();
							final Kind fieldKind = ClassUtils.getKind(fieldType);
							
							Object fieldDefaultValue = null;
							try {
								if (fieldKind == Kind.PRIMITIVE) {
									fieldDefaultValue = ClassUtils.newPrimitive(fieldType);
								} else {
									fieldDefaultValue = fieldType.getConstructor().newInstance();
								}
								if (field.isAnnotationPresent(Optional.class)) {
									final Optional optionalProp = field.getAnnotation(Optional.class);
									final String optionalValue = optionalProp.value();
									fieldDefaultValue = ClassUtils.convert(fieldType, optionalValue);
								}
							} catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
								e.printStackTrace();
							}
							if (Objects.equals(value, fieldDefaultValue)) {
								return null;
							}
						}
					} catch (IllegalArgumentException | IllegalAccessException e) {
						e.printStackTrace();
						value = null;
					}
					
					return name + "=" + serialize(value, format, raw);
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
	private static <T> String serialize(final T object, final String format, final boolean raw) {
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
						         .map(value -> IniFileMapper.serialize(value, format, false))
						         .reduce(StringUtils::joinComma)
						         .orElse("")
			       + ")";
		}
		
		return "(" + serialize(object) + ")";
	}
	
}
