package fr.whyt.pubg.inifile.core;

import fr.whyt.pubg.inifile.annotations.IniOptional;
import fr.whyt.pubg.inifile.annotations.IniProperty;
import fr.whyt.pubg.inifile.annotations.IniWrapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class IniFileMapperTest {
	
	private static class A {
		@IniProperty
		public int integer;
		
		@IniProperty(name = "int")
		public int integerRenamed;
		
		@IniProperty(format = "%4d")
		public int integerFormatted;
		
		@IniProperty(format = "%4d", pattern = "\\d{4}")
		public int integerPattern;
		
		@IniProperty
		public String string;
		
		@IniProperty(raw = true)
		public String stringRaw;
		
		@IniProperty(optional = true)
		public String stringOptional;
		
		@IniProperty
		public boolean bool;
		
		static { IniOptionalValue.customize(IniOptionalValue.BOOLEAN, true); }
		@IniProperty(optional = true)
		@IniOptional(IniOptionalValue.BOOLEAN)
		public boolean boolOptionalCustomTrue;
		
		@IniProperty(optional = true)
		@IniOptional(IniOptionalValue.BOOLEAN_FALSE)
		public boolean boolOptionalFalse;
		
		@IniProperty
		public B bWrappedWithBrace;
		
		@IniProperty
		public C cWrappedWithParenthesis;
		
		@IniProperty
		@IniWrapper(IniPropertyWrapper.BRACKET)
		public List<Integer> intListWrappedWithBracket;
		
		@IniProperty
		@IniWrapper(IniPropertyWrapper.PARENTHESIS)
		public List<C> cListWrappedWithParenthesis;
		
		public A(final int integer, final int integerRenamed, final int integerFormatted, final int integerPattern,
		         final String string, final String stringRaw, final String stringOptional,
		         final boolean bool, final boolean boolOptionalCustomTrue, final boolean boolOptionalFalse,
		         final B bWrappedWithBrace, final C cWrappedWithParenthesis,
		         final List<Integer> intListWrappedWithBracket, final List<C> cListWrappedWithParenthesis) {
			this.integer = integer;
			this.integerRenamed = integerRenamed;
			this.integerFormatted = integerFormatted;
			this.integerPattern = integerPattern;
			this.string = string;
			this.stringRaw = stringRaw;
			this.stringOptional = stringOptional;
			this.bool = bool;
			this.boolOptionalCustomTrue = boolOptionalCustomTrue;
			this.boolOptionalFalse = boolOptionalFalse;
			this.bWrappedWithBrace = bWrappedWithBrace;
			this.cWrappedWithParenthesis = cWrappedWithParenthesis;
			this.intListWrappedWithBracket = intListWrappedWithBracket;
			this.cListWrappedWithParenthesis = cListWrappedWithParenthesis;
		}
		@SuppressWarnings("unused") public A() {}
		
		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;
			final A a = (A) o;
			return integer == a.integer
			       && integerRenamed == a.integerRenamed
			       && integerFormatted == a.integerFormatted
			       && integerPattern == a.integerPattern
			       && Objects.equals(string, a.string)
			       && Objects.equals(stringRaw, a.stringRaw)
			       && Objects.equals(stringOptional, a.stringOptional)
			       && bool == a.bool
			       && boolOptionalCustomTrue == a.boolOptionalCustomTrue
			       && boolOptionalFalse == a.boolOptionalFalse
			       && Objects.equals(bWrappedWithBrace, a.bWrappedWithBrace)
			       && Objects.equals(cWrappedWithParenthesis, a.cWrappedWithParenthesis)
			       && Objects.equals(intListWrappedWithBracket, a.intListWrappedWithBracket)
			       && Objects.equals(cListWrappedWithParenthesis, a.cListWrappedWithParenthesis);
		}
	}
	
	@IniWrapper(IniPropertyWrapper.BRACE)
	private static class B {
		@IniProperty
		public char character;
		
		public B(final char character) {
			this.character = character;
		}
		@SuppressWarnings("unused") public B() {}
		
		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;
			final B b = (B) o;
			return character == b.character;
		}
	}
	
	@IniWrapper(IniPropertyWrapper.PARENTHESIS)
	private static class C {
		@IniProperty
		public double decimal;
		@IniProperty
		public int precision;
		
		public C(final double decimal, final int precision) {
			this.decimal = decimal;
			this.precision = precision;
		}
		@SuppressWarnings("unused") public C() {}
		
		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;
			final C c = (C) o;
			return Double.compare(c.decimal, decimal) == 0 && precision == precision;
		}
	}
	
	private static Stream deserializeProviders() {
		return Stream.of(
				Arguments.of(
						"integer=1" + ",int=2" + ",integerFormatted=3" + ",integerPattern=0004"
								+ ",string=\"quoted\"" + ",stringRaw=raw" + ",stringOptional=\"\""
								+ ",bool=true" + ",boolOptionalCustomTrue=true" + ",boolOptionalFalse=False"
					            + ",bWrappedWithBrace={character=a}"
					            + ",cWrappedWithParenthesis=(decimal=1.0,precision=1)"
					            + ",listNotWrapped=[1,2,3]"
					            + ",cListWrappedWithParenthesis=((decimal=1.0,precision=1),(decimal=2.10,precision=2),(decimal=-3.001,precision=3))",
						new A(1, 2, 3, 4,
								"quoted", "raw", null,
								true, true, false,
								new B('a'), new C(1.0, 1),
								List.of(1, 2, 3),
								List.of(new C(1.0, 1), new C(2.10, 2), new C(-3.001, 3)))
				),
				Arguments.of(
						"integer=1" + ",int=2" + ",integerFormatted=3" + ",integerPattern=0004"
								+ ",string=\"quoted\"" + ",stringRaw=raw" + ",stringOptional=\"ok\""
								+ ",bool=true" + ",boolOptionalCustomTrue=false" + ",boolOptionalFalse=True"
								+ ",bWrappedWithBrace={character=a}"
								+ ",cWrappedWithParenthesis=(decimal=1.0,precision=1)"
								+ ",listNotWrapped=[],"
								+ ",cListWrappedWithParenthesis=()",
						new A(1, 2, 3, 4,
								"quoted", "raw", "ok",
								true, false, true,
								new B('a'), new C(1.0, 1),
								Collections.emptyList(),
								Collections.emptyList())
				),
				Arguments.of(
						"integer=1" + ",int=2" + ",integerFormatted=3" + ",integerPattern=0004"
								+ ",string=\"\"" + ",stringRaw=raw"
								+ ",bool=true" + ",boolOptionalFalse=True"
								+ ",bWrappedWithBrace={character=a}"
								+ ",cWrappedWithParenthesis=(decimal=1.0,precision=1)"
								+ ",listNotWrapped=[],"
								+ ",cListWrappedWithParenthesis=()",
						new A(1, 2, 3, 4,
								"", "raw", null,
								true, true, true,
								new B('a'), new C(1.0, 1),
								Collections.emptyList(),
								Collections.emptyList())
				)
		);
	}
	
	@ParameterizedTest
	@MethodSource("deserializeProviders")
	public void testDeserialize_full(final String provided, final Object expected) {
		// given
		
		// expected
		
		// actual
		//final A actual = IniFileMapper.deserialize(provided, A.class);
		
		// assert
		//Assertions.assertEquals(expected, actual);
	}
	
	@SuppressWarnings("unchecked")
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
		final List<?> expected1 = Collections.emptyList();
		final List<Integer> expected2 = List.of(1);
		final List<Integer> expected3 = List.of(1, 2, 3, 4, 5);
		final List<String> expected4 = List.of("str1", "str2", "str3");
		//final List<C> expected5 = List.of(new C("a", false, null));
		//final List<C> expected6 = List.of(new C("a", false, null), new C("b", false, null));
		//final List<C> expected7 = List.of(new C("a", true, null), new C("b", false, List.of(1, 2, 3)));
		
		// mock
		final Method deserializeIterable = IniFileMapper.class.getDeclaredMethod("deserializeIterable", Class.class, Class.class, String.class, int.class);
		deserializeIterable.setAccessible(true);
		
		// actual
		final List<?> actual1 = (List<?>) deserializeIterable.invoke(null, List.class, Integer.class, provided1, 0);
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
		//Assertions.assertIterableEquals(expected5, actual5);
		//Assertions.assertIterableEquals(expected6, actual6);
		//Assertions.assertIterableEquals(expected7, actual7);
	}
	
	@Test
	public void testDeserializeObject() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
		// given
		final String provided1 = "(obj=\"a\")";
		final String provided2 = "(obj=\"b\",key=true)";
		final String provided3 = "(obj=\"c\",key=false,list=(1,2,3))";
		
		// expected
		//final C expected1 = new C("a", false, null);
		//final C expected2 = new C("b", true, null);
		//final C expected3 = new C("c", false, List.of(1, 2, 3));
		
		// mock
		final Method deserialize = IniFileMapper.class.getDeclaredMethod("deserialize", Class.class, String.class);
		deserialize.setAccessible(true);
		
		// actual
		final C actual1 = (C) deserialize.invoke(null, C.class, provided1);
		final C actual2 = (C) deserialize.invoke(null, C.class, provided2);
		final C actual3 = (C) deserialize.invoke(null, C.class, provided3);
		
		// assert
		//Assertions.assertEquals(expected1, actual1);
		//Assertions.assertEquals(expected2, actual2);
		//Assertions.assertEquals(expected3, actual3);
	}
	
}
