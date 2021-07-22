package fr.whyt.pubg.deprecated.parser;

import fr.whyt.pubg.deprecated.parser.exceptions.ParsingException;
import fr.whyt.pubg.deprecated.parser.exceptions.WritingException;

import java.lang.reflect.InvocationTargetException;

/**
 * Define methods to parse and write ini files.<br>
 * Each complex object of the ini file has to be implemented as a Java class
 * implementing the {@linkplain IniFile IniFile&lt;T&gt;}.<br>
 * Also the Java class <b>must</b> implement the <b>default</b> constructor.<br>
 * <br>
 * To serialize a Java class, just call the {@link IniFile#serialize()} method from the class instance.<br>
 * <br>
 * To deserialize, two options :
 * <ul>
 *     <li>call {@link IniFile#deserialize(String)} from an instance of the class</li>
 *     <li>call the {@link IniFile#deserialize(Class, String)} static method from IniFile interface</li>
 * </ul>
 * <i><b>Note:</b> Both {@code deserialize} methods return a new instance of the class.</i><br>
 * <br>
 *
 * @param <T> the Java class representing the INI key
 */
public interface IniFile<T extends IniFile<T>> {
	
	/**
	 * Convenient method to {@code deserialize} an INI config file into a Java class.<br>
	 * <br>
	 * <i><b>Note:</b> This method does not implement business logic. See {@link IniFile#deserialize(String)}</i><br>
	 * <br>
	 *
	 * @param clazz the class type to deserialize
	 * @param source the source to deserialize
	 * @param <T> the class type to deserialize and return
	 * @return the Java class representing the source
	 * @throws ParsingException if the given class type does not implement {@link IniFile} interface
	 * or if the given class cannot be instantiated with the default constructor.
	 */
	static <T extends IniFile<T>> T deserialize(final Class<T> clazz, final String source) throws ParsingException {
		if (IniFile.class.equals(clazz.getSuperclass())) {
			throw new ParsingException("This class does not implements IniFile!", clazz.getCanonicalName(), null);
		}
		try {
			final T instance = clazz.getDeclaredConstructor().newInstance();
			return instance.deserialize(source);
		} catch (InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
			e.printStackTrace();
			throw new ParsingException("Cannot instantiate class!", clazz.getCanonicalName(), null);
		}
	}
	
	/**
	 * Method to override in each implementing classes to properly deserialize source in Java class.<br>
	 * <br>
	 * This method implement the business logic.<br>
	 * <br>
	 * @param source the source to deserialize
	 * @return the deserialized Java class of the source
	 * @throws ParsingException if no deserializer has been found
	 */
	default T deserialize(final String source) throws ParsingException {
		throw new ParsingException("No deserializer found!", source, null);
	}
	
	/**
	 * Method that serialize the Java class into INI config file key<br>
	 * <br>
	 *
	 * @return the INI key representing the Java class
	 * @throws WritingException if error occurred while serialization
	 */
	String serialize() throws WritingException;
	
}
