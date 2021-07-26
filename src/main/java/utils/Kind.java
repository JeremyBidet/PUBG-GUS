package fr.whyt.pubg.utils;

/**
 * Used to determine the kind of an object or class.<br>
 * 4 values available:
 * <ul>
 *     <li>{@link Kind#PRIMITIVE} for primitive types only (int, char, boolean, ...)</li>
 *     <li>{@link Kind#BOXED} for primitive boxed types (Integer, Character, Boolean, ...)</li>
 *     <li>{@link Kind#ITERABLE} for iterable types (List, Set, Queue, ...)</li>
 *     <li>{@link Kind#OBJECT} for other types (none of above)</li>
 * </ul>
 */
public enum Kind {
	
	PRIMITIVE,
	BOXED,
	ITERABLE,
	OBJECT,
	;
	
}
