package fr.whyt.pubg.inifile.core;

import fr.whyt.pubg.data.resources.Brace;
import fr.whyt.pubg.data.resources.Inline;
import fr.whyt.pubg.data.resources.ObjectNested;
import fr.whyt.pubg.data.resources.Parenthesis;
import fr.whyt.pubg.inifile.exceptions.ParsingException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

@SuppressWarnings({"unchecked", "ConstantConditions", "unused"})
public class IniFileMapperPrivateTest {
	
	private static final int INTEGER_NULL = 0;
	private static final String STRING_NULL = null;
	private static final String STRING_EMPTY = "";
	private static final String STRING_DEFAULT_OPTIONAL = null;
	private static final String STRING_EMPTY_OPTIONAL = "";
	private static final boolean BOOLEAN_FALSE_OPTIONAL = false;
	private static final boolean BOOLEAN_TRUE_OPTIONAL = true;
	
	@Test
	public void testIsOpenWrapper() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
		// given
		final char emptyProvided = '\0';
		final char aProvided = 'a';
		final char simpleQuoteProvided = '\'';
		final char doubleQuoteProvided = '"';
		final char openBraceProvided = '{';
		final char openBracketProvided = '[';
		final char openParenthesisProvided = '(';
		final char closeBraceProvided = '}';
		final char closeBracketProvided = ']';
		final char closeParenthesisProvided = ')';
		
		// expected
		final Method isOpenWrapper = IniFileMapper.class.getDeclaredMethod("isOpenWrapper", char.class, boolean.class);
		isOpenWrapper.setAccessible(true);
		
		// actual
		final boolean emptyActual = (boolean) isOpenWrapper.invoke(null, emptyProvided, false);
		final boolean aActual = (boolean) isOpenWrapper.invoke(null, aProvided, false);
		final boolean simpleQuoteActual = (boolean) isOpenWrapper.invoke(null, simpleQuoteProvided, false);
		final boolean doubleQuoteActual = (boolean) isOpenWrapper.invoke(null, doubleQuoteProvided, false);
		final boolean openBraceWithIgnoreActual = (boolean) isOpenWrapper.invoke(null, openBraceProvided, true);
		final boolean openBraceWithPersistActual = (boolean) isOpenWrapper.invoke(null, openBraceProvided, false);
		final boolean openBracketWithIgnoreActual = (boolean) isOpenWrapper.invoke(null, openBracketProvided, true);
		final boolean openBracketWithPersistActual = (boolean) isOpenWrapper.invoke(null, openBracketProvided, false);
		final boolean openParenthesisWithIgnoreActual = (boolean) isOpenWrapper.invoke(null, openParenthesisProvided, true);
		final boolean openParenthesisWithPersistActual = (boolean) isOpenWrapper.invoke(null, openParenthesisProvided, false);
		final boolean closeBraceActual = (boolean) isOpenWrapper.invoke(null, closeBraceProvided, false);
		final boolean closeBracketActual = (boolean) isOpenWrapper.invoke(null, closeBracketProvided, false);
		final boolean closeParenthesisActual = (boolean) isOpenWrapper.invoke(null, closeParenthesisProvided, false);
		
		// assert
		Assertions.assertFalse(emptyActual);
		Assertions.assertFalse(aActual);
		Assertions.assertFalse(simpleQuoteActual);
		Assertions.assertFalse(doubleQuoteActual);
		Assertions.assertFalse(openBraceWithIgnoreActual);
		Assertions.assertTrue(openBraceWithPersistActual);
		Assertions.assertFalse(openBracketWithIgnoreActual);
		Assertions.assertTrue(openBracketWithPersistActual);
		Assertions.assertFalse(openParenthesisWithIgnoreActual);
		Assertions.assertTrue(openParenthesisWithPersistActual);
		Assertions.assertFalse(closeBraceActual);
		Assertions.assertFalse(closeBracketActual);
		Assertions.assertFalse(closeParenthesisActual);
	}
	
	@Test
	public void testIsCloseWrapper() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
		// given
		final char emptyProvided = '\0';
		final char aProvided = 'a';
		final char simpleQuoteProvided = '\'';
		final char doubleQuoteProvided = '"';
		final char openBraceProvided = '{';
		final char openBracketProvided = '[';
		final char openParenthesisProvided = '(';
		final char closeBraceProvided = '}';
		final char closeBracketProvided = ']';
		final char closeParenthesisProvided = ')';
		
		// expected
		final Method isCloseWrapper = IniFileMapper.class.getDeclaredMethod("isCloseWrapper", char.class, boolean.class);
		isCloseWrapper.setAccessible(true);
		
		// actual
		final boolean emptyActual = (boolean) isCloseWrapper.invoke(null, emptyProvided, false);
		final boolean aActual = (boolean) isCloseWrapper.invoke(null, aProvided, false);
		final boolean simpleQuoteActual = (boolean) isCloseWrapper.invoke(null, simpleQuoteProvided, false);
		final boolean doubleQuoteActual = (boolean) isCloseWrapper.invoke(null, doubleQuoteProvided, false);
		final boolean openBraceActual = (boolean) isCloseWrapper.invoke(null, openBraceProvided, false);
		final boolean openBracketActual = (boolean) isCloseWrapper.invoke(null, openBracketProvided, false);
		final boolean openParenthesisActual = (boolean) isCloseWrapper.invoke(null, openParenthesisProvided, false);
		final boolean closeBraceWithIgnoreActual = (boolean) isCloseWrapper.invoke(null, closeBraceProvided, true);
		final boolean closeBraceWithPersistActual = (boolean) isCloseWrapper.invoke(null, closeBraceProvided, false);
		final boolean closeBracketWithIgnoreActual = (boolean) isCloseWrapper.invoke(null, closeBracketProvided, true);
		final boolean closeBracketWithPersistActual = (boolean) isCloseWrapper.invoke(null, closeBracketProvided, false);
		final boolean closeParenthesisWithIgnoreActual = (boolean) isCloseWrapper.invoke(null, closeParenthesisProvided, true);
		final boolean closeParenthesisWithPersistActual = (boolean) isCloseWrapper.invoke(null, closeParenthesisProvided, false);
		
		// assert
		Assertions.assertFalse(emptyActual);
		Assertions.assertFalse(aActual);
		Assertions.assertFalse(simpleQuoteActual);
		Assertions.assertFalse(doubleQuoteActual);
		Assertions.assertFalse(openBraceActual);
		Assertions.assertFalse(openBracketActual);
		Assertions.assertFalse(openParenthesisActual);
		Assertions.assertFalse(closeBraceWithIgnoreActual);
		Assertions.assertTrue(closeBraceWithPersistActual);
		Assertions.assertFalse(closeBracketWithIgnoreActual);
		Assertions.assertTrue(closeBracketWithPersistActual);
		Assertions.assertFalse(closeParenthesisWithIgnoreActual);
		Assertions.assertTrue(closeParenthesisWithPersistActual);
	}
	
	// TODO
	@Test
	public void testDeserializeGeneric() {
		// given
		
		// expected
		
		// actual
		
		// assert
	}
	
	// TODO
	@Test
	public void testDeserializeValue() {
		// given
		
		// expected
		
		// actual
		
		// assert
	}
	
	private static Stream<Arguments> deserializeListProvider() {
		return Stream.of(
				Arguments.of(null, null, ParsingException.class, "Null lists are not authorized"),
				Arguments.of("[]", Collections.emptyList(), null, null),
				Arguments.of("[1]", List.of(1), null, null),
				Arguments.of("[1,2,3,4,5]", List.of(1, 2, 3, 4, 5), null, null),
				Arguments.of("[\"str1\",\"str2\",\"str3\"]", List.of("str1", "str2", "str3"), null, null),
				Arguments.of("[{character=a}]", List.of(new Brace('a')), null, null),
				Arguments.of("[{character=a},{character=b}]", List.of(new Brace('a'), new Brace('b')), null, null),
				Arguments.of("[(decimal=1.0,precision=1),(decimal=2.00,precision=2)]", List.of(new Parenthesis(1.0, 1), new Parenthesis(2.00, 2)), null, null)
		);
	}
	
	@ParameterizedTest
	@MethodSource("deserializeListProvider")
	public void testDeserializeList(final String provided,
	                                final List<?> expectedList,
	                                final Class<Throwable> expectedException,
	                                final String expectedMessage)
			throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
		// given
		
		// expected
		
		// mock
		// FIXME: deserializeGeneric used to be private, turned public for mocking
		final MockedStatic<IniFileMapper> mock = Mockito.mockStatic(IniFileMapper.class, Mockito.CALLS_REAL_METHODS);
		if (expectedList != null && expectedList.size() > 0) {
			if (expectedList.size() == 1) {
				mock.when(() -> IniFileMapper.deserializeGeneric(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(expectedList.get(0));
			} else {
				mock.when(() -> IniFileMapper.deserializeGeneric(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(expectedList.get(0), expectedList.subList(1, expectedList.size()).toArray());
			}
		}
		
		final Method deserializeIterable = IniFileMapper.class.getDeclaredMethod("deserializeIterable",
				Field.class, Class.class, Class.class, String.class);
		deserializeIterable.setAccessible(true);
		
		// actual - assert
		if (expectedException == null) {
			Assertions.assertIterableEquals(expectedList, (List<?>) deserializeIterable.invoke(null, null, List.class, null, provided));
		} else {
			Assertions.assertThrows(
					expectedException,
					() -> {
						try {
							deserializeIterable.invoke(null,null, List.class, null, provided);
						} catch (InvocationTargetException e) {
							throw e.getCause();
						}
					},
					expectedMessage);
		}
		
		mock.close();
	}
	
	private static Stream<Arguments> deserializeObjectProvider() {
		return Stream.of(
				Arguments.of(null, null, null, ClassNotFoundException.class, "Please provide a class implementing Serializable!"),
				Arguments.of(null, Integer.class, null, ClassNotFoundException.class, "This class should implements Serializable!"),
				Arguments.of("{}", Brace.class, null, ParsingException.class, "Empty objects are not authorized"),
				Arguments.of("()", Parenthesis.class, null, ParsingException.class, "Empty objects are not authorized"),
				Arguments.of("[]", Inline.class, null, ParsingException.class, "Unrecognized wrapper characters"),
				Arguments.of("{character=a}", Brace.class, new Brace('a'), null, null),
				Arguments.of("(decimal=1.0,precision=1)", Parenthesis.class, new Parenthesis(1.0, 1), null, null),
				Arguments.of("{level1=(level2=(level3={integer=1}))}", ObjectNested.class, new ObjectNested(new ObjectNested.Level1(new ObjectNested.Level2(new ObjectNested.Level3(1)))), null, null),
				Arguments.of("(integer=1,int=2,intF=3,intP=0004,string=\"quoted\",raw=raw,strOptDef=\"test\",strOptEmpty=\"test\",bool=true,boolOptFalse=true,boolOptCustomTrue=False,brace={character=a},parenthesis=(decimal=1.0,precision=1),bracketList=[1,2,3],parenthesisList=((decimal=1.0,precision=1),(decimal=2.10,precision=2),(decimal=-3.001,precision=3)))",
						Inline.class,
						new Inline(1, 2, 3, 4, "quoted", "raw", "test", "test", true, true, false, new Brace('a'), new Parenthesis(1.0, 1), List.of(1, 2, 3), List.of(new Parenthesis(1.0, 1), new Parenthesis(2.10, 2), new Parenthesis(-3.001, 3))),
						null, null),
				// intP no matches
				Arguments.of("(integer=1,int=2,intF=3,intP=4,string=\"quoted\",raw=raw,strOptDef=\"test\",strOptEmpty=\"test\",bool=true,boolOptFalse=true,boolOptCustomTrue=False,brace={character=a},parenthesis=(decimal=1.0,precision=1),bracketList=[1,2,3],parenthesisList=((decimal=1.0,precision=1),(decimal=2.10,precision=2),(decimal=-3.001,precision=3)))",
						Inline.class,
						new Inline(1, 2, 3, INTEGER_NULL, "quoted", "raw", "test", "test", true, true, false, new Brace('a'), new Parenthesis(1.0, 1), List.of(1, 2, 3), List.of(new Parenthesis(1.0, 1), new Parenthesis(2.10, 2), new Parenthesis(-3.001, 3))),
						ParsingException.class, "Cannot parse the value! The pattern does not match the value"),
				// string empty
				Arguments.of("(integer=1,int=2,intF=3,intP=0004,string=\"\",raw=raw,strOptDef=\"test\",strOptEmpty=\"test\",bool=false,boolOptFalse=true,boolOptCustomTrue=False,brace={character=a},parenthesis=(decimal=1.0,precision=1),bracketList=[1,2,3],parenthesisList=((decimal=1.0,precision=1),(decimal=2.10,precision=2),(decimal=-3.001,precision=3)))",
						Inline.class,
						new Inline(1, 2, 3, 4, STRING_EMPTY, null, "test", "test", true, true, false, new Brace('a'), new Parenthesis(1.0, 1), List.of(1, 2, 3), List.of(new Parenthesis(1.0, 1), new Parenthesis(2.10, 2), new Parenthesis(-3.001, 3))),
						null, null),
				// raw quoted
				Arguments.of("(integer=1,int=2,intF=3,intP=0004,string=\"quoted\",raw=\"raw\",strOptDef=\"test\",strOptEmpty=\"test\",bool=False,boolOptFalse=true,boolOptCustomTrue=False,brace={character=a},parenthesis=(decimal=1.0,precision=1),bracketList=[1,2,3],parenthesisList=((decimal=1.0,precision=1),(decimal=2.10,precision=2),(decimal=-3.001,precision=3)))",
						Inline.class,
						new Inline(1, 2, 3, 4, "quoted", STRING_NULL, "test", "test", true, true, false, new Brace('a'), new Parenthesis(1.0, 1), List.of(1, 2, 3), List.of(new Parenthesis(1.0, 1), new Parenthesis(2.10, 2), new Parenthesis(-3.001, 3))),
						ParsingException.class, "Cannot parse the value! It should be a raw string and there are enclosing quotes."),
				// strOptDef omitted
				Arguments.of("(integer=1,int=2,intF=3,intP=0004,string=\"quoted\",raw=raw,strOptEmpty=\"test\",bool=true,boolOptFalse=true,boolOptCustomTrue=False,brace={character=a},parenthesis=(decimal=1.0,precision=1),bracketList=[1,2,3],parenthesisList=((decimal=1.0,precision=1),(decimal=2.10,precision=2),(decimal=-3.001,precision=3)))",
						Inline.class,
						new Inline(1, 2, 3, 4, "quoted", "raw", STRING_DEFAULT_OPTIONAL, "test", true, true, false, new Brace('a'), new Parenthesis(1.0, 1), List.of(1, 2, 3), List.of(new Parenthesis(1.0, 1), new Parenthesis(2.10, 2), new Parenthesis(-3.001, 3))),
						null, null),
				// strOptEmpty empty
				Arguments.of("(integer=1,int=2,intF=3,intP=0004,string=\"quoted\",raw=raw,strOptDef=\"test\",strOptEmpty=\"\",bool=True,boolOptFalse=true,boolOptCustomTrue=False,brace={character=a},parenthesis=(decimal=1.0,precision=1),bracketList=[1,2,3],parenthesisList=((decimal=1.0,precision=1),(decimal=2.10,precision=2),(decimal=-3.001,precision=3)))",
						Inline.class,
						new Inline(1, 2, 3, 4, "quoted", "raw", "test", STRING_EMPTY_OPTIONAL, true, true, false, new Brace('a'), new Parenthesis(1.0, 1), List.of(1, 2, 3), List.of(new Parenthesis(1.0, 1), new Parenthesis(2.10, 2), new Parenthesis(-3.001, 3))),
						null, null),
				// boolOptFalse False
				Arguments.of("(integer=1,int=2,intF=3,intP=0004,string=\"quoted\",raw=raw,strOptDef=\"test\",strOptEmpty=\"test\",bool=true,boolOptFalse=False,boolOptCustomTrue=False,brace={character=a},parenthesis=(decimal=1.0,precision=1),bracketList=[1,2,3],parenthesisList=((decimal=1.0,precision=1),(decimal=2.10,precision=2),(decimal=-3.001,precision=3)))",
						Inline.class,
						new Inline(1, 2, 3, 4, "quoted", "raw", "test", "test", true, BOOLEAN_FALSE_OPTIONAL, false, new Brace('a'), new Parenthesis(1.0, 1), List.of(1, 2, 3), List.of(new Parenthesis(1.0, 1), new Parenthesis(2.10, 2), new Parenthesis(-3.001, 3))),
						null, null),
				// boolOptCustomTrue true
				Arguments.of("(integer=1,int=2,intF=3,intP=0004,string=\"quoted\",raw=raw,strOptDef=\"test\",strOptEmpty=\"test\",bool=true,boolOptFalse=true,boolOptCustomTrue=true,brace={character=a},parenthesis=(decimal=1.0,precision=1),bracketList=[1,2,3],parenthesisList=((decimal=1.0,precision=1),(decimal=2.10,precision=2),(decimal=-3.001,precision=3)))",
						Inline.class,
						new Inline(1, 2, 3, 4, "quoted", "raw", "test", "test", true, true, BOOLEAN_TRUE_OPTIONAL, new Brace('a'), new Parenthesis(1.0, 1), List.of(1, 2, 3), List.of(new Parenthesis(1.0, 1), new Parenthesis(2.10, 2), new Parenthesis(-3.001, 3))),
						null, null),
				// bracketList empty
				Arguments.of("(integer=1,int=2,intF=3,intP=0004,string=\"quoted\",raw=raw,strOptDef=\"test\",strOptEmpty=\"test\",bool=true,boolOptFalse=true,boolOptCustomTrue=False,brace={character=a},parenthesis=(decimal=1.0,precision=1),bracketList=[],parenthesisList=((decimal=1.0,precision=1),(decimal=2.10,precision=2),(decimal=-3.001,precision=3)))",
						Inline.class,
						new Inline(1, 2, 3, 4, "quoted", "raw", "test", "test", true, true, false, new Brace('a'), new Parenthesis(1.0, 1), Collections.emptyList(), List.of(new Parenthesis(1.0, 1), new Parenthesis(2.10, 2), new Parenthesis(-3.001, 3))),
						null, null),
				// parenthesisList empty
				Arguments.of("(integer=1,int=2,intF=3,intP=0004,string=\"quoted\",raw=raw,strOptDef=\"test\",strOptEmpty=\"test\",bool=true,boolOptFalse=true,boolOptCustomTrue=False,brace={character=a},parenthesis=(decimal=1.0,precision=1),bracketList=[1,2,3],parenthesisList=()",
						Inline.class,
						new Inline(1, 2, 3, 4, "quoted", "raw", "test", "test", true, true, false, new Brace('a'), new Parenthesis(1.0, 1), List.of(1, 2, 3), Collections.emptyList()),
						null, null)
		);
	}
	
	@ParameterizedTest
	@MethodSource("deserializeObjectProvider")
	public void testDeserializeObject(final String provided,
	                                  final Class<Serializable> expectedClass,
	                                  final Serializable expectedObject,
	                                  final Class<Throwable> expectedException,
	                                  final String expectedMessage)
			throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
		// given
		
		// expected
		
		final Method deserializeObject = IniFileMapper.class.getDeclaredMethod("deserializeObject", Class.class, String.class);
		deserializeObject.setAccessible(true);
		
		// actual - assert
		if (expectedException == null) {
			Assertions.assertEquals(expectedObject, (Serializable) deserializeObject.invoke(null, expectedClass, provided));
		} else {
			Assertions.assertThrows(
					expectedException,
					() -> {
						try {
							deserializeObject.invoke(null, expectedClass, provided);
						} catch (InvocationTargetException e) {
							throw e.getCause();
						}
					},
					expectedMessage);
		}
	}
	
}
