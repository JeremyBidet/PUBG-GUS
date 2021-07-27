package fr.whyt.pubg.inifile.core;

/**
 * Defines convenient wrapper for object and list INI properties:
 * <ul>
 *     <li>{@link IniPropertyWrapper#PARENTHESIS} => {@code (...)}</li>
 *     <li>{@link IniPropertyWrapper#BRACKET} => {@code [...]}</li>
 *     <li>{@link IniPropertyWrapper#BRACE} => {@code {...}}</li>
 * </ul>
 */
public enum IniPropertyWrapper {
	
	PARENTHESIS('(', ')'),
	BRACKET('[', ']'),
	BRACE('{', '}'),
	;
	
	public final char head;
	public final char tail;
	
	IniPropertyWrapper(final char head, final char tail) {
		this.head = head;
		this.tail = tail;
	}
	
	public static String wrap(final IniPropertyWrapper wrapper, final String source) {
		return wrapper.head + source + wrapper.tail;
	}
	
}
