package fr.whyt.pubg.inifile.core;

import fr.whyt.pubg.inifile.annotations.Property;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class IniFileMapperTest {

	private static class A {
		@Property
		public int integer;
		@Property
		public String string;
		@Property
		public boolean bool;
		@Property
		public List<Integer> list;
		@Property
		public B b;
		
		public A(final int integer, final String string, final boolean bool, final List<Integer> list, final B b) {
			this.integer = integer;
			this.string = string;
			this.bool = bool;
			this.list = list;
			this.b = b;
		}
		public A() {}
		
		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;
			final A a = (A) o;
			return integer == a.integer
			       && bool == a.bool
			       && Objects.equals(string, a.string)
			       && Objects.equals(list, a.list)
			       && Objects.equals(b, a.b);
		}
	}
	
	private static class B {
		@Property
		public double dooble;
		@Property
		public char character;
		
		public B(final double dooble, final char character) {
			this.dooble = dooble;
			this.character = character;
		}
		public B() {}
		
		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;
			final B b = (B) o;
			return Double.compare(b.dooble, dooble) == 0
			       && character == b.character;
		}
	}
	
	private static class C {
		@Property
		public String obj;
		@Property
		public boolean key;
		@Property
		public List<Integer> list;
		
		public C(final String obj, final boolean key, final List<Integer> list) {
			this.obj = obj;
			this.key = key;
			this.list = list;
		}
		public C() {}
		
		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;
			final C c = (C) o;
			return key == c.key
			       && Objects.equals(obj, c.obj)
			       && Objects.equals(list, c.list);
		}
	}
	
	@Test
	public void testTo_simple() {
		// given
		final String provided1 = "integer=1";
		final String provided2 = "integer=2,string=\"ok\"";
		final String provided3 = "integer=3,string=\"\",bool=true";
		final String provided4 = "integer=4,string=,bool=true,list=(),b=(dooble=3.14,character=c)";
		
		// expected
		final A expected1 = new A(1, null, false, null,null);
		final A expected2 = new A(2, "ok", false, null,null);
		final A expected3 = new A(3, "", true, null,null);
		final A expected4 = new A(4, null, true, Collections.emptyList(),new B(3.14, 'c'));
		
		// actual
		final A actual1 = IniFileMapper.to(provided1, A.class);
		final A actual2 = IniFileMapper.to(provided2, A.class);
		final A actual3 = IniFileMapper.to(provided3, A.class);
		final A actual4 = IniFileMapper.to(provided4, A.class);
		
		// assert
		Assertions.assertEquals(expected1, actual1);
		Assertions.assertEquals(expected2, actual2);
		Assertions.assertEquals(expected3, actual3);
		Assertions.assertEquals(expected4, actual4);
	}
	
	@Test
	public void testDeserializeList() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
		// given
		final String provided1 = "()";
		final String provided2 = "(1)";
		final String provided3 = "(1,2,3,4,5)";
		final String provided4 = "(\"str1\",\"str2\",\"str3\")";
		final String provided5 = "((obj=\"a\"))";
		final String provided6 = "((obj=\"a\"),(obj=\"b\"))";
		final String provided7 = "((obj=\"a\",key=true),(obj=\"b\",key=false,list=(1,2,3)))";
		
		// expected
		final List expected1 = Collections.emptyList();
		final List<Integer> expected2 = List.of(1);
		final List<Integer> expected3 = List.of(1, 2, 3, 4, 5);
		final List<String> expected4 = List.of("str1", "str2", "str3");
		final List<C> expected5 = List.of(new C("a", false, null));
		final List<C> expected6 = List.of(new C("a", false, null), new C("b", false, null));
		final List<C> expected7 = List.of(new C("a", true, null), new C("b", false, List.of(1, 2, 3)));
		
		// mock
		final Method deserializeIterable = IniFileMapper.class.getDeclaredMethod("deserializeIterable", Class.class, Class.class, String.class, int.class);
		deserializeIterable.setAccessible(true);
		
		// actual
		final List actual1 = (List) deserializeIterable.invoke(null, List.class, Integer.class, provided1, 0);
		final List<Integer> actual2 = (List<Integer>) deserializeIterable.invoke(null,List.class, Integer.class, provided2, 0);
		final List<Integer> actual3 = (List<Integer>) deserializeIterable.invoke(null,List.class, Integer.class, provided3, 0);
		final List<String> actual4 = (List<String>) deserializeIterable.invoke(null,List.class, String.class, provided4, 0);
		final List<C> actual5 = (List<C>) deserializeIterable.invoke(null,List.class, C.class, provided5, 0);
		final List<C> actual6 = (List<C>) deserializeIterable.invoke(null,List.class, C.class, provided6, 0);
		final List<C> actual7 = (List<C>) deserializeIterable.invoke(null,List.class, C.class, provided7, 0);
		
		// assert
		Assertions.assertIterableEquals(expected1, actual1);
		Assertions.assertIterableEquals(expected2, actual2);
		Assertions.assertIterableEquals(expected3, actual3);
		Assertions.assertIterableEquals(expected4, actual4);
		Assertions.assertIterableEquals(expected5, actual5);
		Assertions.assertIterableEquals(expected6, actual6);
		Assertions.assertIterableEquals(expected7, actual7);
	}
	
	@Test
	public void testDeserializeObject() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
		// given
		final String provided1 = "(obj=\"a\")";
		final String provided2 = "(obj=\"b\",key=true)";
		final String provided3 = "(obj=\"c\",key=false,list=(1,2,3))";
		
		// expected
		final C expected1 = new C("a", false, null);
		final C expected2 = new C("b", true, null);
		final C expected3 = new C("c", false, List.of(1, 2, 3));
		
		// mock
		final Method deserialize = IniFileMapper.class.getDeclaredMethod("deserialize", Class.class, String.class);
		deserialize.setAccessible(true);
		
		// actual
		final C actual1 = (C) deserialize.invoke(null, C.class, provided1);
		final C actual2 = (C) deserialize.invoke(null, C.class, provided2);
		final C actual3 = (C) deserialize.invoke(null, C.class, provided3);
		
		// assert
		Assertions.assertEquals(expected1, actual1);
		Assertions.assertEquals(expected2, actual2);
		Assertions.assertEquals(expected3, actual3);
	}
	
}
