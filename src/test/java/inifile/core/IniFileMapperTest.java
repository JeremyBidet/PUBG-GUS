package fr.whyt.pubg.inifile.core;

import fr.whyt.pubg.data.resources.Brace;
import fr.whyt.pubg.data.resources.Inline;
import fr.whyt.pubg.data.resources.Parenthesis;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

@SuppressWarnings({"rawtypes", "unchecked", "unused", "CommentedOutCode"})
public class IniFileMapperTest {
	
	private static Stream inlineProviders() {
		return Stream.of(
				Arguments.of(
						"inline.ini",
						List.of(
								new Inline(1, 2, 3, 4,
										"quoted", "raw", null,
										true, true, false,
										new Brace('a'), new Parenthesis(1.0, 1),
										List.of(1, 2, 3),
										List.of(new Parenthesis(1.0, 1),
												new Parenthesis(2.10, 2),
												new Parenthesis(-3.001, 3))),
								new Inline(1, 2, 3, 4,
										"quoted", "raw", "ok",
										true, false, true,
										new Brace('a'), new Parenthesis(1.0, 1),
										Collections.emptyList(),
										Collections.emptyList()),
								new Inline(1, 2, 3, 4,
										"", "raw", null,
										true, true, true,
										new Brace('a'), new Parenthesis(1.0, 1),
										Collections.emptyList(),
										Collections.emptyList())
						)
				)
		);
	}
	
	@ParameterizedTest
	@MethodSource("inlineProviders")
	public void testDeserialize_full(final String provided, final Object expected) {
		// given
		
		// expected
		
		// actual
		
		// assert
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
		final List<?> expected1 = Collections.emptyList();
		final List<Integer> expected2 = List.of(1);
		final List<Integer> expected3 = List.of(1, 2, 3, 4, 5);
		final List<String> expected4 = List.of("str1", "str2", "str3");
		// final List<C> expected5 = List.of(new C("a", false, null));
		// final List<C> expected6 = List.of(new C("a", false, null), new C("b", false, null));
		// final List<C> expected7 = List.of(new C("a", true, null), new C("b", false, List.of(1, 2, 3)));
		
		// mock
		final Method deserializeIterable = IniFileMapper.class.getDeclaredMethod("deserializeIterable",
				Field.class, Class.class, IniPropertyWrapper.class, Class.class, IniPropertyWrapper.class, String.class);
		deserializeIterable.setAccessible(true);
		
		// actual
		final List<?> actual1 = (List<?>) deserializeIterable.invoke(null, null, List.class, null, Integer.class, null, provided1);
		final List<Integer> actual2 = (List<Integer>) deserializeIterable.invoke(null, null,List.class, null, Integer.class, null, provided2);
		final List<Integer> actual3 = (List<Integer>) deserializeIterable.invoke(null,null, List.class, null, Integer.class, null, provided3);
		final List<String> actual4 = (List<String>) deserializeIterable.invoke(null,null, List.class, null, String.class, null, provided4);
		final List<Parenthesis> actual5 = (List<Parenthesis>) deserializeIterable.invoke(null,null, List.class, null, Parenthesis.class, null, provided5);
		final List<Parenthesis> actual6 = (List<Parenthesis>) deserializeIterable.invoke(null,null, List.class, null, Parenthesis.class, null, provided6);
		final List<Parenthesis> actual7 = (List<Parenthesis>) deserializeIterable.invoke(null,null, List.class, null, Parenthesis.class, null, provided7);
		
		// assert
		Assertions.assertIterableEquals(expected1, actual1);
		Assertions.assertIterableEquals(expected2, actual2);
		Assertions.assertIterableEquals(expected3, actual3);
		Assertions.assertIterableEquals(expected4, actual4);
		// Assertions.assertIterableEquals(expected5, actual5);
		// Assertions.assertIterableEquals(expected6, actual6);
		// Assertions.assertIterableEquals(expected7, actual7);
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
		final Method deserialize = IniFileMapper.class.getDeclaredMethod("deserializeObject",
				Class.class, IniPropertyWrapper.class, String.class);
		deserialize.setAccessible(true);
		
		// actual
		final Parenthesis actual1 = (Parenthesis) deserialize.invoke(null, Parenthesis.class, null, provided1);
		final Parenthesis actual2 = (Parenthesis) deserialize.invoke(null, Parenthesis.class, null, provided2);
		final Parenthesis actual3 = (Parenthesis) deserialize.invoke(null, Parenthesis.class, null, provided3);
		
		// assert
		//Assertions.assertEquals(expected1, actual1);
		//Assertions.assertEquals(expected2, actual2);
		//Assertions.assertEquals(expected3, actual3);
	}
	
}
