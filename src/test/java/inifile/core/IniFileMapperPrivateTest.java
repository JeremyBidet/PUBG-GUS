package fr.whyt.pubg.inifile.core;

import fr.whyt.pubg.data.resources.Brace;
import fr.whyt.pubg.data.resources.Inline;
import fr.whyt.pubg.data.resources.ObjectNested;
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
	
	@Test
	public void testDeserializeGeneric() {
		// given
		
		// expected
		
		// actual
		
		// assert
	}
	
	@Test
	public void testDeserializeValue() {
		// given
		
		// expected
		
		// actual
		
		// assert
	}
	
	@Test
	public void testDeserializeList() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
		// given
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
				Field.class, Class.class, Class.class, String.class);
		deserializeIterable.setAccessible(true);
		
		// actual
		final Executable actual0 = () -> {
			try {
				deserializeIterable.invoke(null,null, List.class, null, provided0);
			} catch (InvocationTargetException e) {
				throw e.getCause();
			}
		};
		final List<?> actual1 = (List<?>) deserializeIterable.invoke(null, null, List.class, null, provided1);
		final List<Integer> actual2 = (List<Integer>) deserializeIterable.invoke(null,null, List.class, Integer.class, provided2);
		final List<Integer> actual3 = (List<Integer>) deserializeIterable.invoke(null,null, List.class, Integer.class, provided3);
		final List<String> actual4 = (List<String>) deserializeIterable.invoke(null,null, List.class, String.class, provided4);
		final List<Brace> actual5 = (List<Brace>) deserializeIterable.invoke(null,null, List.class, Brace.class, provided5);
		final List<Brace> actual6 = (List<Brace>) deserializeIterable.invoke(null,null, List.class, Brace.class, provided6);
		final List<Parenthesis> actual7 = (List<Parenthesis>) deserializeIterable.invoke(null,null, List.class, Parenthesis.class, provided7);
		
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
		final String provided0 = null;
		final String provided1 = "{}";
		final String provided2 = "()";
		final String provided3 = "{character=a}";
		final String provided4 = "(decimal=1.0,precision=1)";
		final String provided5 = "{level1=(level2=(level3={integer=1}))}";
		final String provided6 = "(integer=1,int=2,intF=3,intP=0004,string=\"quoted\",raw=raw,strOptDef=\"test\",strOptEmpty=\"test\",bool=true,boolOptFalse=true,boolOptCustomTrue=False,brace={character=a},parenthesis=(decimal=1.0,precision=1),bracketList=[1,2,3],parenthesisList=((decimal=1.0,precision=1),(decimal=2.10,precision=2),(decimal=-3.001,precision=3)))";
		// integerPattern no matches
		final String provided7 = "(integer=1,int=2,intF=3,intP=4,string=\"quoted\",raw=raw,strOptDef=\"test\",strOptEmpty=\"test\",bool=true,boolOptFalse=true,boolOptCustomTrue=False,brace={character=a},parenthesis=(decimal=1.0,precision=1),bracketList=[1,2,3],parenthesisList=((decimal=1.0,precision=1),(decimal=2.10,precision=2),(decimal=-3.001,precision=3)))";
		// string empty
		final String provided8 = "(integer=1,int=2,intF=3,intP=0004,string=\"\",raw=raw,strOptDef=\"test\",strOptEmpty=\"test\",bool=false,boolOptFalse=true,boolOptCustomTrue=False,brace={character=a},parenthesis=(decimal=1.0,precision=1),bracketList=[1,2,3],parenthesisList=((decimal=1.0,precision=1),(decimal=2.10,precision=2),(decimal=-3.001,precision=3)))";
		// raw quoted
		final String provided9 = "(integer=1,int=2,intF=3,intP=0004,string=\"quoted\",raw=\"raw\",strOptDef=\"test\",strOptEmpty=\"test\",bool=False,boolOptFalse=true,boolOptCustomTrue=False,brace={character=a},parenthesis=(decimal=1.0,precision=1),bracketList=[1,2,3],parenthesisList=((decimal=1.0,precision=1),(decimal=2.10,precision=2),(decimal=-3.001,precision=3)))";
		// strOptDef omitted
		final String provided10 = "(integer=1,int=2,intF=3,intP=0004,string=\"quoted\",raw=raw,strOptEmpty=\"test\",bool=true,boolOptFalse=true,boolOptCustomTrue=False,brace={character=a},parenthesis=(decimal=1.0,precision=1),bracketList=[1,2,3],parenthesisList=((decimal=1.0,precision=1),(decimal=2.10,precision=2),(decimal=-3.001,precision=3)))";
		// strOptEmpty empty
		final String provided11 = "(integer=1,int=2,intF=3,intP=0004,string=\"quoted\",raw=raw,strOptDef=\"test\",strOptEmpty=\"\",bool=True,boolOptFalse=true,boolOptCustomTrue=False,brace={character=a},parenthesis=(decimal=1.0,precision=1),bracketList=[1,2,3],parenthesisList=((decimal=1.0,precision=1),(decimal=2.10,precision=2),(decimal=-3.001,precision=3)))";
		// boolOptFalse False
		final String provided12 = "(integer=1,int=2,intF=3,intP=0004,string=\"quoted\",raw=raw,strOptDef=\"test\",strOptEmpty=\"test\",bool=true,boolOptFalse=False,boolOptCustomTrue=False,brace={character=a},parenthesis=(decimal=1.0,precision=1),bracketList=[1,2,3],parenthesisList=((decimal=1.0,precision=1),(decimal=2.10,precision=2),(decimal=-3.001,precision=3)))";
		// boolOptCustomTrue true
		final String provided13 = "(integer=1,int=2,intF=3,intP=0004,string=\"quoted\",raw=raw,strOptDef=\"test\",strOptEmpty=\"test\",bool=true,boolOptFalse=true,boolOptCustomTrue=true,brace={character=a},parenthesis=(decimal=1.0,precision=1),bracketList=[1,2,3],parenthesisList=((decimal=1.0,precision=1),(decimal=2.10,precision=2),(decimal=-3.001,precision=3)))";
		// bracketList empty
		final String provided14 = "(integer=1,int=2,intF=3,intP=0004,string=\"quoted\",raw=raw,strOptDef=\"test\",strOptEmpty=\"test\",bool=true,boolOptFalse=true,boolOptCustomTrue=False,brace={character=a},parenthesis=(decimal=1.0,precision=1),bracketList=[],parenthesisList=((decimal=1.0,precision=1),(decimal=2.10,precision=2),(decimal=-3.001,precision=3)))";
		// parenthesisList empty
		final String provided15 = "(integer=1,int=2,intF=3,intP=0004,string=\"quoted\",raw=raw,strOptDef=\"test\",strOptEmpty=\"test\",bool=true,boolOptFalse=true,boolOptCustomTrue=False,brace={character=a},parenthesis=(decimal=1.0,precision=1),bracketList=[1,2,3],parenthesisList=()";
		
		// expected
		final Class<ParsingException> expected0Exception = ParsingException.class;
		final String expected0Message = "Null objects are not authorized";
		final Class<ParsingException> expected1Exception = ParsingException.class;
		final String expected1Message = "Empty objects are not authorized";
		final Class<ParsingException> expected2Exception = ParsingException.class;
		final String expected2Message = "Empty objects are not authorized";
		final Brace expected3 = new Brace('a');
		final Parenthesis expected4 = new Parenthesis(1.0, 1);
		final ObjectNested expected5 = new ObjectNested(new ObjectNested.Level1(new ObjectNested.Level2(new ObjectNested.Level3(1))));
		final Inline expected6 = new Inline(1, 2, 3, 4,
				"quoted", "raw", "test", "test",
				true, true, false,
				new Brace('a'), new Parenthesis(1.0, 1),
				List.of(1, 2, 3),
				List.of(new Parenthesis(1.0, 1), new Parenthesis(2.10, 2), new Parenthesis(-3.001, 3)));
		final Inline expected7 = new Inline(1, 2, 3, INTEGER_NULL,
				"quoted", "raw", "test", "test",
				true, true, false,
				new Brace('a'), new Parenthesis(1.0, 1),
				List.of(1, 2, 3),
				List.of(new Parenthesis(1.0, 1), new Parenthesis(2.10, 2), new Parenthesis(-3.001, 3)));
		final Inline expected8 = new Inline(1, 2, 3, 4,
				STRING_EMPTY, null, "test", "test",
				true, true, false,
				new Brace('a'), new Parenthesis(1.0, 1),
				List.of(1, 2, 3),
				List.of(new Parenthesis(1.0, 1), new Parenthesis(2.10, 2), new Parenthesis(-3.001, 3)));
		final Inline expected9 = new Inline(1, 2, 3, 4,
				"quoted", STRING_NULL, "test", "test",
				true, true, false,
				new Brace('a'), new Parenthesis(1.0, 1),
				List.of(1, 2, 3),
				List.of(new Parenthesis(1.0, 1), new Parenthesis(2.10, 2), new Parenthesis(-3.001, 3)));
		final Inline expected10 = new Inline(1, 2, 3, 4,
				"quoted", "raw", STRING_DEFAULT_OPTIONAL, "test",
				true, true, false,
				new Brace('a'), new Parenthesis(1.0, 1),
				List.of(1, 2, 3),
				List.of(new Parenthesis(1.0, 1), new Parenthesis(2.10, 2), new Parenthesis(-3.001, 3)));
		final Inline expected11 = new Inline(1, 2, 3, 4,
				"quoted", "raw", "test", STRING_EMPTY_OPTIONAL,
				true, true, false,
				new Brace('a'), new Parenthesis(1.0, 1),
				List.of(1, 2, 3),
				List.of(new Parenthesis(1.0, 1), new Parenthesis(2.10, 2), new Parenthesis(-3.001, 3)));
		final Inline expected12 = new Inline(1, 2, 3, 4,
				"quoted", "raw", "test", "test",
				true, BOOLEAN_FALSE_OPTIONAL, false,
				new Brace('a'), new Parenthesis(1.0, 1),
				List.of(1, 2, 3),
				List.of(new Parenthesis(1.0, 1), new Parenthesis(2.10, 2), new Parenthesis(-3.001, 3)));
		final Inline expected13 = new Inline(1, 2, 3, 4,
				"quoted", "raw", "test", "test",
				true, true, BOOLEAN_TRUE_OPTIONAL,
				new Brace('a'), new Parenthesis(1.0, 1),
				List.of(1, 2, 3),
				List.of(new Parenthesis(1.0, 1), new Parenthesis(2.10, 2), new Parenthesis(-3.001, 3)));
		final Inline expected14 = new Inline(1, 2, 3, 4,
				"quoted", "raw", "test", "test",
				true, true, false,
				new Brace('a'), new Parenthesis(1.0, 1),
				Collections.emptyList(),
				List.of(new Parenthesis(1.0, 1), new Parenthesis(2.10, 2), new Parenthesis(-3.001, 3)));
		final Inline expected15 = new Inline(1, 2, 3, 4,
				"quoted", "raw", "test", "test",
				true, true, false,
				new Brace('a'), new Parenthesis(1.0, 1),
				List.of(1, 2, 3),
				Collections.emptyList());
		
		final Method deserializeObject = IniFileMapper.class.getDeclaredMethod("deserializeObject", Class.class, String.class);
		deserializeObject.setAccessible(true);
		
		// actual
		final Executable actual0 = () -> {
			try {
				deserializeObject.invoke(null,Inline.class, provided0);
			} catch (InvocationTargetException e) {
				throw e.getCause();
			}
		};
		final Executable actual1 = () -> {
			try {
				deserializeObject.invoke(null, Inline.class, provided1);
			} catch (InvocationTargetException e) {
				throw e.getCause();
			}
		};
		final Executable actual2 = () -> {
			try {
				deserializeObject.invoke(null, Inline.class, provided2);
			} catch (InvocationTargetException e) {
				throw e.getCause();
			}
		};
		final Brace actual3 = (Brace) deserializeObject.invoke(null, Brace.class, provided3);
		final Parenthesis actual4 = (Parenthesis) deserializeObject.invoke(null,Parenthesis.class, provided4);
		final ObjectNested actual5 = (ObjectNested) deserializeObject.invoke(null,ObjectNested.class, provided5);
		final Inline actual6 = (Inline) deserializeObject.invoke(null,Inline.class, provided6);
		final Inline actual7 = (Inline) deserializeObject.invoke(null,Inline.class, provided7);
		final Inline actual8 = (Inline) deserializeObject.invoke(null,Inline.class, provided8);
		final Inline actual9 = (Inline) deserializeObject.invoke(null, Inline.class, provided9);
		final Inline actual10 = (Inline) deserializeObject.invoke(null, Inline.class, provided10);
		final Inline actual11 = (Inline) deserializeObject.invoke(null, Inline.class, provided11);
		final Inline actual12 = (Inline) deserializeObject.invoke(null, Inline.class, provided12);
		final Inline actual13 = (Inline) deserializeObject.invoke(null, Inline.class, provided13);
		final Inline actual14 = (Inline) deserializeObject.invoke(null, Inline.class, provided14);
		final Inline actual15 = (Inline) deserializeObject.invoke(null, Inline.class, provided15);
		
		// assert
		Assertions.assertThrows(expected0Exception, actual0, expected0Message);
		Assertions.assertThrows(expected1Exception, actual1, expected1Message);
		Assertions.assertThrows(expected2Exception, actual2, expected2Message);
		Assertions.assertEquals(expected3, actual3);
		Assertions.assertEquals(expected4, actual4);
		Assertions.assertEquals(expected5, actual5);
		Assertions.assertEquals(expected6, actual6);
		Assertions.assertEquals(expected7, actual7);
		Assertions.assertEquals(expected8, actual8);
		Assertions.assertEquals(expected9, actual9);
		Assertions.assertEquals(expected10, actual10);
		Assertions.assertEquals(expected11, actual11);
		Assertions.assertEquals(expected12, actual12);
		Assertions.assertEquals(expected13, actual13);
		Assertions.assertEquals(expected14, actual14);
		Assertions.assertEquals(expected15, actual15);
	}
	
}
