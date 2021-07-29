package fr.whyt.pubg.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

@SuppressWarnings({"rawtypes", "unchecked", "ConstantConditions"})
public class ClassUtilsTest {
	
	private static class A {
	}
	
	@Test
	public void testGetKind_class() {
		// given
		final Class intProvided = int.class;
		final Class doubleProvided = double.class;
		final Class booleanProvided = Boolean.class;
		final Class stringProvided = String.class;
		final Class arrayProvided = String[].class;
		final Class listProvided = List.class;
		final Class setProvided = Set.class;
		final Class objectProvided = A.class;
		
		// expected
		final Kind primitiveExpected = Kind.PRIMITIVE;
		final Kind boxedExpected = Kind.BOXED;
		final Kind iterableExpected = Kind.ITERABLE;
		final Kind objectExpected = Kind.OBJECT;
		
		// actual
		final Kind intActual = ClassUtils.getKind(intProvided);
		final Kind doubleActual = ClassUtils.getKind(doubleProvided);
		final Kind booleanActual = ClassUtils.getKind(booleanProvided);
		final Kind stringActual = ClassUtils.getKind(stringProvided);
		final Kind arrayActual = ClassUtils.getKind(arrayProvided);
		final Kind listActual = ClassUtils.getKind(listProvided);
		final Kind setActual = ClassUtils.getKind(setProvided);
		final Kind objectActual = ClassUtils.getKind(objectProvided);
		
		// assert
		Assertions.assertEquals(primitiveExpected, intActual);
		Assertions.assertEquals(primitiveExpected, doubleActual);
		Assertions.assertEquals(boxedExpected, booleanActual);
		Assertions.assertEquals(boxedExpected, stringActual);
		Assertions.assertEquals(iterableExpected, arrayActual);
		Assertions.assertEquals(iterableExpected, listActual);
		Assertions.assertEquals(iterableExpected, setActual);
		Assertions.assertEquals(objectExpected, objectActual);
		
	}
	
	@Test
	public void testGetKind_object() {
		// given
		final int intProvided = 1;
		final double doubleProvided = -1.;
		final Boolean booleanProvided = true;
		final String stringProvided = "test";
		final Integer[] arrayProvided = {1, 2, 3};
		final List<Integer> listProvided = List.of(0, 1, 2);
		final Set<String> setProvided = Set.of("a", "b", "c");
		final A objectProvided = new A();
		
		// expected
		final Kind boxedExpected = Kind.BOXED;
		final Kind iterableExpected = Kind.ITERABLE;
		final Kind objectExpected = Kind.OBJECT;
		
		// actual
		final Kind intActual = ClassUtils.getKind(intProvided);
		final Kind doubleActual = ClassUtils.getKind(doubleProvided);
		final Kind booleanActual = ClassUtils.getKind(booleanProvided);
		final Kind stringActual = ClassUtils.getKind(stringProvided);
		final Kind arrayActual = ClassUtils.getKind(arrayProvided);
		final Kind listActual = ClassUtils.getKind(listProvided);
		final Kind setActual = ClassUtils.getKind(setProvided);
		final Kind objectActual = ClassUtils.getKind(objectProvided);
		
		// assert
		Assertions.assertEquals(boxedExpected, intActual);
		Assertions.assertEquals(boxedExpected, doubleActual);
		Assertions.assertEquals(boxedExpected, booleanActual);
		Assertions.assertEquals(boxedExpected, stringActual);
		Assertions.assertEquals(iterableExpected, arrayActual);
		Assertions.assertEquals(iterableExpected, listActual);
		Assertions.assertEquals(iterableExpected, setActual);
		Assertions.assertEquals(objectExpected, objectActual);
		
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
		final String nullIntProvided = null;
		final String nullIntegerProvided = null;
		final String nullStringProvided = null;
		final String emptyStringProvided = "";
		
		// expected
		final int intExpected = 0;
		final Integer integerExpected = 1;
		final Long longBExpected = 1L;
		final long longExpected = 1L;
		final Short shortBExpected = 1;
		final short shortExpected = 1;
		final Double doubleBExpected = 1.;
		final double doubleExpected = 1.;
		final Float floatBExpected = 1F;
		final float floatExpected = 1F;
		final Boolean booleanBExpected = true;
		final boolean booleanExpected = true;
		final Byte byteBExpected = 1;
		final byte byteExpected = 1;
		final Character characterExpected = 'c';
		final char charExpected = 'c';
		final String stringExpected = "test";
		final int nullIntExpected = 0;
		final Integer nullIntegerExpected = null;
		final String nullStringExpected = null;
		final String emptyStringExpected = "";
		
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
		final int nullIntActual = ClassUtils.convert(int.class, nullIntProvided);
		final Integer nullIntegerActual = ClassUtils.convert(Integer.class, nullIntegerProvided);
		final String nullStringActual = ClassUtils.convert(String.class, nullStringProvided);
		final String emptyStringActual = ClassUtils.convert(String.class, emptyStringProvided);
		
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
		Assertions.assertEquals(nullIntExpected, nullIntActual);
		Assertions.assertEquals(nullIntegerExpected, nullIntegerActual);
		Assertions.assertEquals(nullStringExpected, nullStringActual);
		Assertions.assertEquals(emptyStringExpected, emptyStringActual);
	}
	
	@Test
	public void testNewCollection() {
		// given
		final Class<Collection> collectionProvided = Collection.class;
		final Class<List> listProvided = List.class;
		final Class<Set> setProvided = Set.class;
		final Class<SortedSet> sortedSetProvided = SortedSet.class;
		final Class<Queue> queueProvided = Queue.class;
		
		final List untypedList = Collections.emptyList();
		final Class<List> untypedListProvided = (Class<List>) untypedList.getClass();
		
		// expected
		final Collection<Integer> collectionExpected = new ArrayList<>();
		final Collection<Integer> listExpected = new ArrayList<>();
		final Collection<Integer> setExpected = new HashSet<>();
		final Collection<Integer> sortedSetExpected = new HashSet<>();
		final Collection<Integer> queueExpected = new LinkedList<>();
		final Collection<?> untypedListExpected = new LinkedList<>();
		
		// actual
		final Collection<Integer> collectionsActual  = ClassUtils.newCollection(collectionProvided);
		final Collection<Integer> listActual  = ClassUtils.newCollection(listProvided);
		final Collection<Integer> setActual  = ClassUtils.newCollection(setProvided);
		final Collection<Integer> sortedSetActual  = ClassUtils.newCollection(sortedSetProvided);
		final Collection<Integer> queueActual  = ClassUtils.newCollection(queueProvided);
		final Collection<?> untypedListActual  = ClassUtils.newCollection(untypedListProvided);
		
		// assert
		Assertions.assertEquals(collectionExpected, collectionsActual);
		Assertions.assertEquals(listExpected, listActual);
		Assertions.assertEquals(setExpected, setActual);
		Assertions.assertEquals(sortedSetExpected, sortedSetActual);
		Assertions.assertEquals(queueExpected, queueActual);
		Assertions.assertEquals(untypedListExpected, untypedListActual);
	}
	
	@Test
	public void testNewPrimitive_withValue() {
		// given
		final Class intClassProvided = int.class;
		final Class longClassProvided = long.class;
		final Class shortClassProvided = short.class;
		final Class doubleClassProvided = double.class;
		final Class floatClassProvided = float.class;
		final Class booleanClassProvided = boolean.class;
		final Class byteClassProvided = byte.class;
		final Class charClassProvided = char.class;
		
		final int intProvided = 123;
		final long longProvided = 456L;
		final short shortProvided = 789;
		final double doubleProvided = -123.0;
		final float floatProvided = -456F;
		final boolean booleanProvided = true;
		final byte byteProvided = 9;
		final char charProvided = 'a';
		
		// expected
		final int intExpected = 123;
		final long longExpected = 456L;
		final short shortExpected = 789;
		final double doubleExpected = -123.0;
		final float floatExpected = -456F;
		final boolean booleanExpected = true;
		final byte byteExpected = 9;
		final char charExpected = 'a';
		
		// actual
		final int intActual = ClassUtils.newPrimitive(intClassProvided, intProvided);
		final long longActual = ClassUtils.newPrimitive(longClassProvided, longProvided);
		final short shortActual = ClassUtils.newPrimitive(shortClassProvided, shortProvided);
		final double doubleActual = ClassUtils.newPrimitive(doubleClassProvided, doubleProvided);
		final float floatActual = ClassUtils.newPrimitive(floatClassProvided, floatProvided);
		final boolean booleanActual = ClassUtils.newPrimitive(booleanClassProvided, booleanProvided);
		final byte byteActual = ClassUtils.newPrimitive(byteClassProvided, byteProvided);
		final char charActual = ClassUtils.newPrimitive(charClassProvided, charProvided);
		
		// assert
		Assertions.assertEquals(intExpected, intActual);
		Assertions.assertEquals(longExpected, longActual);
		Assertions.assertEquals(shortExpected, shortActual);
		Assertions.assertEquals(doubleExpected, doubleActual);
		Assertions.assertEquals(floatExpected, floatActual);
		Assertions.assertEquals(booleanExpected, booleanActual);
		Assertions.assertEquals(byteExpected, byteActual);
		Assertions.assertEquals(charExpected, charActual);
	}
	
	@Test
	public void testNewPrimitive_defaultValue() {
		// given
		final Class intProvided = int.class;
		final Class longProvided = long.class;
		final Class shortProvided = short.class;
		final Class doubleProvided = double.class;
		final Class floatProvided = float.class;
		final Class booleanProvided = boolean.class;
		final Class byteProvided = byte.class;
		final Class charProvided = char.class;
		
		// expected
		final int intExpected = 0;
		final long longExpected = 0L;
		final short shortExpected = 0;
		final double doubleExpected = 0.;
		final float floatExpected = 0F;
		final boolean booleanExpected = false;
		final byte byteExpected = 0;
		final char charExpected = '\0';
		
		// actual
		final int intActual = (int) ClassUtils.newPrimitive(intProvided);
		final long longActual = (long) ClassUtils.newPrimitive(longProvided);
		final short shortActual = (short) ClassUtils.newPrimitive(shortProvided);
		final double doubleActual = (double) ClassUtils.newPrimitive(doubleProvided);
		final float floatActual = (float) ClassUtils.newPrimitive(floatProvided);
		final boolean booleanActual = (boolean) ClassUtils.newPrimitive(booleanProvided);
		final byte byteActual = (byte) ClassUtils.newPrimitive(byteProvided);
		final char charActual = (char) ClassUtils.newPrimitive(charProvided);
		
		// assert
		Assertions.assertEquals(intExpected, intActual);
		Assertions.assertEquals(longExpected, longActual);
		Assertions.assertEquals(shortExpected, shortActual);
		Assertions.assertEquals(doubleExpected, doubleActual);
		Assertions.assertEquals(floatExpected, floatActual);
		Assertions.assertEquals(booleanExpected, booleanActual);
		Assertions.assertEquals(byteExpected, byteActual);
		Assertions.assertEquals(charExpected, charActual);
	}
	
	@Test
	public void testIsBoxed_class() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
		// given
		final Class<Integer> integerProvided = Integer.class;
		final Class<Long> longProvided = Long.class;
		final Class<Short> shortProvided = Short.class;
		final Class<Double> doubleProvided = Double.class;
		final Class<Float> floatProvided = Float.class;
		final Class<Boolean> booleanProvided = Boolean.class;
		final Class<Byte> byteProvided = Byte.class;
		final Class<String> stringProvided = String.class;
		final Class<Character> characterProvided = Character.class;
		final Class<Optional> optionalProvided = Optional.class;
		final Class<List> listProvided = List.class;
		
		// mock
		final Method isBoxed = ClassUtils.class.getDeclaredMethod("isBoxed", Class.class);
		isBoxed.setAccessible(true);
		
		// expected
		
		// actual
		
		// assert
		Assertions.assertTrue((boolean) isBoxed.invoke(null, integerProvided));
		Assertions.assertTrue((boolean) isBoxed.invoke(null, longProvided));
		Assertions.assertTrue((boolean) isBoxed.invoke(null, shortProvided));
		Assertions.assertTrue((boolean) isBoxed.invoke(null, doubleProvided));
		Assertions.assertTrue((boolean) isBoxed.invoke(null, floatProvided));
		Assertions.assertTrue((boolean) isBoxed.invoke(null, booleanProvided));
		Assertions.assertTrue((boolean) isBoxed.invoke(null, byteProvided));
		Assertions.assertTrue((boolean) isBoxed.invoke(null, stringProvided));
		Assertions.assertTrue((boolean) isBoxed.invoke(null, characterProvided));
		Assertions.assertFalse((boolean) isBoxed.invoke(null, optionalProvided));
		Assertions.assertFalse((boolean) isBoxed.invoke(null, listProvided));
	}
	
	@Test
	public void testIsBoxed_object() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
		// given
		final Integer integerProvided = 1;
		final Long longProvided = 1L;
		final Short shortProvided = 1;
		final Double doubleProvided = 1.;
		final Float floatProvided = 1F;
		final Boolean booleanProvided = true;
		final Byte byteProvided = 1;
		final String stringProvided = "test";
		final Character characterProvided = 'c';
		final Optional<Boolean> optionalProvided = Optional.of(true);
		final List<Integer> listProvided = List.of(1,2,3);
		
		// mock
		final Method isBoxed = ClassUtils.class.getDeclaredMethod("isBoxed", Object.class);
		isBoxed.setAccessible(true);
		
		// expected
		
		// actual
		
		// assert
		Assertions.assertTrue((boolean) isBoxed.invoke(null, integerProvided));
		Assertions.assertTrue((boolean) isBoxed.invoke(null, longProvided));
		Assertions.assertTrue((boolean) isBoxed.invoke(null, shortProvided));
		Assertions.assertTrue((boolean) isBoxed.invoke(null, doubleProvided));
		Assertions.assertTrue((boolean) isBoxed.invoke(null, floatProvided));
		Assertions.assertTrue((boolean) isBoxed.invoke(null, booleanProvided));
		Assertions.assertTrue((boolean) isBoxed.invoke(null, byteProvided));
		Assertions.assertTrue((boolean) isBoxed.invoke(null, stringProvided));
		Assertions.assertTrue((boolean) isBoxed.invoke(null, characterProvided));
		Assertions.assertFalse((boolean) isBoxed.invoke(null, optionalProvided));
		Assertions.assertFalse((boolean) isBoxed.invoke(null, listProvided));
	}
	
	@Test
	public void testIsIterable_class() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
		// given
		final Class<Integer> integerProvided = Integer.class;
		final Class<List> listProvided = List.class;
		final Class<Collection> collectionProvided = Collection.class;
		final Class<Iterable> iterableProvided = Iterable.class;
		final Class<Set> setProvided = Set.class;
		final Class<SortedSet> sortedSetProvided = SortedSet.class;
		final Class<Queue> queueProvided = Queue.class;
		final Class<Deque> dequeProvided = Deque.class;
		
		// mock
		final Method isIterable = ClassUtils.class.getDeclaredMethod("isIterable", Class.class);
		isIterable.setAccessible(true);
		
		// expected
		
		// actual
		
		// assert
		Assertions.assertFalse((boolean) isIterable.invoke(null, integerProvided));
		Assertions.assertTrue((boolean) isIterable.invoke(null, listProvided));
		Assertions.assertTrue((boolean) isIterable.invoke(null, collectionProvided));
		Assertions.assertTrue((boolean) isIterable.invoke(null, iterableProvided));
		Assertions.assertTrue((boolean) isIterable.invoke(null, setProvided));
		Assertions.assertTrue((boolean) isIterable.invoke(null, sortedSetProvided));
		Assertions.assertTrue((boolean) isIterable.invoke(null, queueProvided));
		Assertions.assertTrue((boolean) isIterable.invoke(null, dequeProvided));
	}
	
	@Test
	public void testIsIterable_object() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
		// given
		final Integer integerProvided = 1;
		final List listProvided = List.of();
		final Collection collectionProvided = Collections.emptyList();
		final Iterable iterableProvided = Collections.emptySet();
		final Set setProvided = Set.of();
		final SortedSet sortedSetProvided = new TreeSet();
		final Queue queueProvided = new PriorityQueue();
		final Deque dequeProvided = new ArrayDeque();
		
		// mock
		final Method isIterable = ClassUtils.class.getDeclaredMethod("isIterable", Object.class);
		isIterable.setAccessible(true);
		
		// expected
		
		// actual
		
		// assert
		Assertions.assertFalse((boolean) isIterable.invoke(null, integerProvided));
		Assertions.assertTrue((boolean) isIterable.invoke(null, listProvided));
		Assertions.assertTrue((boolean) isIterable.invoke(null, collectionProvided));
		Assertions.assertTrue((boolean) isIterable.invoke(null, iterableProvided));
		Assertions.assertTrue((boolean) isIterable.invoke(null, setProvided));
		Assertions.assertTrue((boolean) isIterable.invoke(null, sortedSetProvided));
		Assertions.assertTrue((boolean) isIterable.invoke(null, queueProvided));
		Assertions.assertTrue((boolean) isIterable.invoke(null, dequeProvided));
	}
	
}
