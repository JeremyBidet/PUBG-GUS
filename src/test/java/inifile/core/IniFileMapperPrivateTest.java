package fr.whyt.pubg.inifile.core;

import fr.whyt.pubg.data.resources.Brace;
import fr.whyt.pubg.data.resources.Parenthesis;
import fr.whyt.pubg.inifile.exceptions.ParsingException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;

@SuppressWarnings({"unchecked", "ConstantConditions", "CommentedOutCode", "unused"})
public class IniFileMapperPrivateTest {
	
	@Test
	public void testDeserializeGeneric() {
	
	}
	
	@Test
	public void testDeserializeValue() {
	
	}
	
	@Test
	public void testDeserializeList() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
		// given
		final IniPropertyWrapper iterableWrapperProvided = IniPropertyWrapper.BRACKET;
		final IniPropertyWrapper element56WrapperProvided = IniPropertyWrapper.BRACE;
		final IniPropertyWrapper element7WrapperProvided = IniPropertyWrapper.PARENTHESIS;
		final String provided0 = null;
		final String provided1 = "[]";
		final String provided2 = "[1]";
		final String provided3 = "[1,2,3,4,5]";
		final String provided4 = "[\"str1\",\"str2\",\"str3\"]";
		final String provided5 = "[{character=a}]";
		final String provided6 = "[{character=a},{character=b}]";
		final String provided7 = "[(decimal=1.0,precision=1),(decimal=2.00,precision=2)]";
		
		// expected
		final Class<ParsingException> expected0Exception = ParsingException.class;
		final String expected0Message = "Null lists are not authorized";
		final List<?> expected1 = Collections.emptyList();
		final List<Integer> expected2 = List.of(1);
		final List<Integer> expected3 = List.of(1, 2, 3, 4, 5);
		final List<String> expected4 = List.of("str1", "str2", "str3");
		final List<Brace> expected5 = List.of(new Brace('a'));
		final List<Brace> expected6 = List.of(new Brace('a'), new Brace('b'));
		final List<Parenthesis> expected7 = List.of(new Parenthesis(1.0, 1), new Parenthesis(2.00, 2));
		
		// mock
		// FIXME: deserializeGeneric is private, turned public for mocking
		final MockedStatic<IniFileMapper> mock = Mockito.mockStatic(IniFileMapper.class, Mockito.CALLS_REAL_METHODS);
		mock.when(() -> IniFileMapper.deserializeGeneric(Mockito.any(), Mockito.any(), Mockito.any()))
				.thenReturn(1)
				.thenReturn(1).thenReturn(2).thenReturn(3).thenReturn(4).thenReturn(5)
				.thenReturn("str1").thenReturn("str2").thenReturn("str3")
				.thenReturn(new Brace('a'))
				.thenReturn(new Brace('a')).thenReturn(new Brace('b'))
				.thenReturn(new Parenthesis(1.0, 1)).thenReturn(new Parenthesis(2.00, 2));
		
		final Method deserializeIterable = IniFileMapper.class.getDeclaredMethod("deserializeIterable",
				Field.class, Class.class, IniPropertyWrapper.class, Class.class, IniPropertyWrapper.class, String.class);
		deserializeIterable.setAccessible(true);
		
		// actual
		final Executable actual0 = () -> {
			try {
				deserializeIterable.invoke(null,null, List.class, iterableWrapperProvided, null, null, provided0);
			} catch (InvocationTargetException e) {
				throw e.getCause();
			}
		};
		final List<?> actual1 = (List<?>) deserializeIterable.invoke(null, null, List.class, iterableWrapperProvided, null, null, provided1);
		final List<Integer> actual2 = (List<Integer>) deserializeIterable.invoke(null,null, List.class, iterableWrapperProvided, Integer.class, null, provided2);
		final List<Integer> actual3 = (List<Integer>) deserializeIterable.invoke(null,null, List.class, iterableWrapperProvided, Integer.class, null, provided3);
		final List<String> actual4 = (List<String>) deserializeIterable.invoke(null,null, List.class, iterableWrapperProvided, String.class, null, provided4);
		final List<Brace> actual5 = (List<Brace>) deserializeIterable.invoke(null,null, List.class, iterableWrapperProvided, Brace.class, element56WrapperProvided, provided5);
		final List<Brace> actual6 = (List<Brace>) deserializeIterable.invoke(null,null, List.class, iterableWrapperProvided, Brace.class, element56WrapperProvided, provided6);
		final List<Parenthesis> actual7 = (List<Parenthesis>) deserializeIterable.invoke(null,null, List.class, iterableWrapperProvided, Parenthesis.class, element7WrapperProvided, provided7);
		
		// assert
		Assertions.assertThrows(expected0Exception, actual0, expected0Message);
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
