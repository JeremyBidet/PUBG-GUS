package fr.whyt.pubg.utils;

import org.junit.jupiter.api.Assertions;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class AssertionsHelper {
	
	private static String[] simpleTypes = { "Integer", "Double", "Long", "Boolean", "String" };
	private static String[] iterableTypes = { "Iterable", "Collection", "List", "Set", "Map" };
	
	public static void assertObjectEquals(final Object expected, final Object actual) {
		final List<Field> expectedFields = Arrays.stream(expected.getClass().getFields()).collect(Collectors.toList());
		final List<Field> actualFields = Arrays.stream(actual.getClass().getFields()).collect(Collectors.toList());
		
		final List<Field> commonFields = expectedFields.stream().distinct().filter(actualFields::contains).collect(Collectors.toList());
		
		for(final Field field : commonFields) {
			try {
				final String type = field.getType().getSimpleName();
				
				final Object expectedValue = field.get(expected);
				final Object actualValue = field.get(actual);
				
				if (field.getType().isPrimitive() || Arrays.stream(simpleTypes).anyMatch(t -> t.equalsIgnoreCase(type))) {
					Assertions.assertEquals(expectedValue, actualValue);
				}
				else if (Arrays.stream(iterableTypes).anyMatch(t -> t.equalsIgnoreCase(type))) {
					final Iterable expectedIterable = (Iterable) expectedValue;
					final Iterable actualIterable = (Iterable) actualValue;
					
					// XOR
					if((expectedIterable == null && actualIterable != null) || (expectedIterable != null && actualIterable == null)) {
						Assertions.fail("Both iterables should be null or not null!");
					}
					
					if(expectedIterable != null && actualIterable != null) {
						final Iterator expectedIterator = expectedIterable.iterator();
						final Iterator actualIterator = actualIterable.iterator();
						
						while (true) {
							final boolean expectedHasNext = expectedIterator.hasNext();
							final boolean actualHasNext = actualIterator.hasNext();
							if ((expectedHasNext && !actualHasNext) || (!expectedHasNext && actualHasNext)) {
								Assertions.fail("Both iterables should have the same elements quantity!");
							}
							if(!expectedHasNext && !actualHasNext) {
								break;
							}
							assertObjectEquals(expectedIterator.next(), actualIterator.next());
						}
					}
				}
				else {
					assertObjectEquals(expectedValue, actualValue);
				}
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		
	}
	
}
