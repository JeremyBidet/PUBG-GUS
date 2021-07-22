package fr.whyt.pubg.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.*;

public class ClassUtilsTest {
	
	@Test
	public void testIsBoxed() {
		// given
		final Integer integerProvided = 1;
		final Long longProvided = 1L;
		final Short shortProvided = 1;
		final Double doubleProvided = 1.;
		final Float floatProvided = Float.valueOf(1);
		final Boolean booleanProvided = true;
		final Byte byteProvided = 1;
		final String stringProvided = "test";
		final Character characterProvided = 'c';
		final Optional<Boolean> optionalProvided = Optional.of(true);
		final List<Integer> listProvided = List.of(1,2,3);
		
		// expected
		
		// actual
		
		// assert
		Assertions.assertTrue(ClassUtils.isBoxed(integerProvided));
		Assertions.assertTrue(ClassUtils.isBoxed(longProvided));
		Assertions.assertTrue(ClassUtils.isBoxed(shortProvided));
		Assertions.assertTrue(ClassUtils.isBoxed(doubleProvided));
		Assertions.assertTrue(ClassUtils.isBoxed(floatProvided));
		Assertions.assertTrue(ClassUtils.isBoxed(booleanProvided));
		Assertions.assertTrue(ClassUtils.isBoxed(byteProvided));
		Assertions.assertTrue(ClassUtils.isBoxed(stringProvided));
		Assertions.assertTrue(ClassUtils.isBoxed(characterProvided));
		Assertions.assertFalse(ClassUtils.isBoxed(optionalProvided));
		Assertions.assertFalse(ClassUtils.isBoxed(listProvided));
	}
	
	@Test
	public void testIsIterableObject() {
		// given
		final Integer integerProvided = 1;
		final List listProvided = List.of();
		final Collection collectionProvided = Collections.emptyList();
		final Iterable iterableProvided = Collections.emptySet();
		final Set setProvided = Set.of();
		final SortedSet sortedSetProvided = new TreeSet();
		final Queue queueProvided = new PriorityQueue();
		final Deque dequeProvided = new ArrayDeque();
		
		// expected
		
		// actual
		
		// assert
		Assertions.assertFalse(ClassUtils.isIterable(integerProvided));
		Assertions.assertTrue(ClassUtils.isIterable(listProvided));
		Assertions.assertTrue(ClassUtils.isIterable(collectionProvided));
		Assertions.assertTrue(ClassUtils.isIterable(iterableProvided));
		Assertions.assertTrue(ClassUtils.isIterable(setProvided));
		Assertions.assertTrue(ClassUtils.isIterable(sortedSetProvided));
		Assertions.assertTrue(ClassUtils.isIterable(queueProvided));
		Assertions.assertTrue(ClassUtils.isIterable(dequeProvided));
	}
	
	@Test
	public void testIsIterableClass() {
		// given
		final Class<Integer> integerProvided = Integer.class;
		final Class<List> listProvided = List.class;
		final Class<Collection> collectionProvided = Collection.class;
		final Class<Iterable> iterableProvided = Iterable.class;
		final Class<Set> setProvided = Set.class;
		final Class<SortedSet> sortedSetProvided = SortedSet.class;
		final Class<Queue> queueProvided = Queue.class;
		final Class<Deque> dequeProvided = Deque.class;
		
		// expected
		
		// actual
		
		// assert
		Assertions.assertFalse(ClassUtils.isIterable(integerProvided));
		Assertions.assertTrue(ClassUtils.isIterable(listProvided));
		Assertions.assertTrue(ClassUtils.isIterable(collectionProvided));
		Assertions.assertTrue(ClassUtils.isIterable(iterableProvided));
		Assertions.assertTrue(ClassUtils.isIterable(setProvided));
		Assertions.assertTrue(ClassUtils.isIterable(sortedSetProvided));
		Assertions.assertTrue(ClassUtils.isIterable(queueProvided));
		Assertions.assertTrue(ClassUtils.isIterable(dequeProvided));
	}
	
	@Test
	public void testConvert() {
		// given
		final String intProvided = "0";
		final String integerProvided = "1";
		final String longBProvided = "1";
		final String longProvided = "1";
		final String shortBProvided = "1";
		final String shortProvided = "1";
		final String doubleBProvided = "1.";
		final String doubleProvided = "1.";
		final String floatBProvided = "1.";
		final String floatProvided = "1.";
		final String booleanBProvided = "true";
		final String booleanProvided = "true";
		final String byteBProvided = "1";
		final String byteProvided = "1";
		final String characterProvided = "c";
		final String charProvided = "c";
		final String stringProvided = "test";
		final String optionalProvided = "Optional.of(true)";
		
		// expected
		final int intExpected = 0;
		final Integer integerExpected = 1;
		final Long longBExpected = 1L;
		final long longExpected = 1L;
		final Short shortBExpected = 1;
		final short shortExpected = 1;
		final Double doubleBExpected = 1.;
		final double doubleExpected = 1.;
		final Float floatBExpected = Float.valueOf(1);
		final float floatExpected = 1F;
		final Boolean booleanBExpected = true;
		final boolean booleanExpected = true;
		final Byte byteBExpected = 1;
		final byte byteExpected = 1;
		final Character characterExpected = 'c';
		final char charExpected = 'c';
		final String stringExpected = "test";
		final Optional<Boolean> optionalExpected = Optional.of(true);
		
		// actual
		final int intActual = ClassUtils.convert(int.class, intProvided);
		final Integer integerActual  = ClassUtils.convert(Integer.class, integerProvided);
		final Long longBActual  = ClassUtils.convert(Long.class, longBProvided);
		final long longActual  = ClassUtils.convert(long.class, longProvided);
		final Short shortBActual  = ClassUtils.convert(Short.class, shortBProvided);
		final short shortActual  = ClassUtils.convert(short.class, shortProvided);
		final Double doubleBActual  = ClassUtils.convert(Double.class, doubleBProvided);
		final double doubleActual  = ClassUtils.convert(double.class, doubleProvided);
		final Float floatBActual  = ClassUtils.convert(Float.class, floatBProvided);
		final float floatActual  = ClassUtils.convert(float.class, floatProvided);
		final Boolean booleanBActual  = ClassUtils.convert(Boolean.class, booleanBProvided);
		final boolean booleanActual  = ClassUtils.convert(boolean.class, booleanProvided);
		final Byte byteBActual  = ClassUtils.convert(Byte.class, byteBProvided);
		final byte byteActual  = ClassUtils.convert(byte.class, byteProvided);
		final Character characterActual  = ClassUtils.convert(Character.class, characterProvided);
		final char charActual  = ClassUtils.convert(char.class, charProvided);
		final String stringActual  = ClassUtils.convert(String.class, stringProvided);
		final Executable optionalActual = () -> ClassUtils.convert(Optional.class, optionalProvided);
		
		// assert
		Assertions.assertEquals(intExpected, intActual);
		Assertions.assertEquals(integerExpected, integerActual);
		Assertions.assertEquals(longBExpected, longBActual);
		Assertions.assertEquals(longExpected, longActual);
		Assertions.assertEquals(shortBExpected, shortBActual);
		Assertions.assertEquals(shortExpected, shortActual);
		Assertions.assertEquals(doubleBExpected, doubleBActual);
		Assertions.assertEquals(doubleExpected, doubleActual);
		Assertions.assertEquals(floatBExpected, floatBActual);
		Assertions.assertEquals(floatExpected, floatActual);
		Assertions.assertEquals(booleanBExpected, booleanBActual);
		Assertions.assertEquals(booleanExpected, booleanActual);
		Assertions.assertEquals(byteBExpected, byteBActual);
		Assertions.assertEquals(byteExpected, byteActual);
		Assertions.assertEquals(characterExpected, characterActual);
		Assertions.assertEquals(charExpected, charActual);
		Assertions.assertEquals(stringExpected, stringActual);
		Assertions.assertThrows(UnsupportedOperationException.class, optionalActual);
	}
	
}
