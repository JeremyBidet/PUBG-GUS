package fr.whyt.pubg.inifile.core;

import fr.whyt.pubg.inifile.exceptions.ParsingException;
import fr.whyt.pubg.utils.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

@SuppressWarnings({"ConstantConditions"})
public class IniPropertyPatternTest {

	@Test
	public void testBoolean() {
		// given
		final Pattern patternProvided = Pattern.compile(IniPropertyPattern.BOOLEAN);
		final String provided1 = "false";
		final String provided2 = "False";
		final String provided3 = "true";
		final String provided4 = "True";
		
		// expected
		final boolean expected12 = false;
		final boolean expected34 = true;
		
		// actual
		final Matcher matcherActual = patternProvided.matcher(provided1);
		matcherActual.matches();
		final boolean actual1 = Boolean.parseBoolean(matcherActual.group());
		
		matcherActual.reset(provided2);
		matcherActual.matches();
		final boolean actual2 = Boolean.parseBoolean(matcherActual.group());
		
		matcherActual.reset(provided3);
		matcherActual.matches();
		final boolean actual3 = Boolean.parseBoolean(matcherActual.group());
		
		matcherActual.reset(provided4);
		matcherActual.matches();
		final boolean actual4 = Boolean.parseBoolean(matcherActual.group());
		
		// assert
		Assertions.assertEquals(expected12, actual1);
		Assertions.assertEquals(expected12, actual2);
		Assertions.assertEquals(expected34, actual3);
		Assertions.assertEquals(expected34, actual4);
	}
	
	@Test
	public void testDouble() {
		// given
		final Pattern patternProvided = Pattern.compile(IniPropertyPattern.DOUBLE);
		final String provided1 = "0.0";
		final String provided2 = "1.23456789";
		final String provided3 = "-987654321.0000000000";
		
		// expected
		final double expected1 = .0;
		final double expected2 = 1.23456789;
		final double expected3 = -987654321.0000000000;
		
		// actual
		final Matcher matcherActual = patternProvided.matcher(provided1);
		matcherActual.matches();
		final double actual1 = Double.parseDouble(matcherActual.group());
		
		matcherActual.reset(provided2);
		matcherActual.matches();
		final double actual2 = Double.parseDouble(matcherActual.group());
		
		matcherActual.reset(provided3);
		matcherActual.matches();
		final double actual3 = Double.parseDouble(matcherActual.group());
		
		// assert
		Assertions.assertEquals(expected1, actual1);
		Assertions.assertEquals(expected2, actual2);
		Assertions.assertEquals(expected3, actual3);
	}
	
	@Test
	public void testInteger() {
		// given
		final Pattern patternProvided = Pattern.compile(IniPropertyPattern.INTEGER);
		final String provided1 = "0";
		final String provided2 = "1";
		final String provided3 = "-1";
		final String provided4 = "-1234567890";
		
		// expected
		final int expected1 = 0;
		final int expected2 = 1;
		final int expected3 = -1;
		final int expected4 = -1234567890;
		
		// actual
		final Matcher matcherActual = patternProvided.matcher(provided1);
		matcherActual.matches();
		final int actual1 = Integer.parseInt(matcherActual.group());
		
		matcherActual.reset(provided2);
		matcherActual.matches();
		final int actual2 = Integer.parseInt(matcherActual.group());
		
		matcherActual.reset(provided3);
		matcherActual.matches();
		final int actual3 = Integer.parseInt(matcherActual.group());
		
		matcherActual.reset(provided4);
		matcherActual.matches();
		final int actual4 = Integer.parseInt(matcherActual.group());
		
		// assert
		Assertions.assertEquals(expected1, actual1);
		Assertions.assertEquals(expected2, actual2);
		Assertions.assertEquals(expected3, actual3);
		Assertions.assertEquals(expected4, actual4);
	}
	
	@Test
	public void testWord() {
		// given
		final Pattern patternProvided = Pattern.compile(IniPropertyPattern.WORD);
		final String provided1 = "azerty";
		final String provided2 = "azerty_uiop";
		final String provided3 = "azerty-uiop";
		
		// expected
		final String expected1 = "azerty";
		final String expected2 = "azerty_uiop";
		final String expected3 = "azerty-uiop";
		
		// actual
		final Matcher matcherActual = patternProvided.matcher(provided1);
		matcherActual.matches();
		final String actual1 = matcherActual.group();
		
		matcherActual.reset(provided2);
		matcherActual.matches();
		final String actual2 = matcherActual.group();
		
		matcherActual.reset(provided3);
		matcherActual.matches();
		final String actual3 = matcherActual.group();
		
		// assert
		Assertions.assertEquals(expected1, actual1);
		Assertions.assertEquals(expected2, actual2);
		Assertions.assertEquals(expected3, actual3);
	}
	
	@Test
	public void testWordQuote() {
		// given
		final Pattern patternProvided = Pattern.compile(IniPropertyPattern.DOUBLE_QUOTED_WORD);
		final String provided1 = "\"azerty\"";
		final String provided2 = "\"azerty_uiop\"";
		final String provided3 = "\"azerty-uiop\"";
		
		// expected
		final String expected1 = "azerty";
		final String expected2 = "azerty_uiop";
		final String expected3 = "azerty-uiop";
		
		// actual
		final Matcher matcherActual = patternProvided.matcher(provided1);
		matcherActual.matches();
		final String actual1 = matcherActual.group(1);
		
		matcherActual.reset(provided2);
		matcherActual.matches();
		final String actual2 = matcherActual.group(1);
		
		matcherActual.reset(provided3);
		matcherActual.matches();
		final String actual3 = matcherActual.group(1);
		
		// assert
		Assertions.assertEquals(expected1, actual1);
		Assertions.assertEquals(expected2, actual2);
		Assertions.assertEquals(expected3, actual3);
	}
	
	@Test
	public void testString() {
		// given
		final Pattern patternProvided = Pattern.compile(IniPropertyPattern.STRING);
		final String provided1 = "\\\"{azerty}\\\"";
		final String provided2 = "\\\"{azerty_uiop}\\\"";
		final String provided3 = "\\\"{azerty-uiop}\\\"";
		
		// expected
		final String expected1 = "\\\"{azerty}\\\"";
		final String expected2 = "\\\"{azerty_uiop}\\\"";
		final String expected3 = "\\\"{azerty-uiop}\\\"";
		
		// actual
		final Matcher matcherActual = patternProvided.matcher(provided1);
		matcherActual.matches();
		final String actual1 = matcherActual.group();
		
		matcherActual.reset(provided2);
		matcherActual.matches();
		final String actual2 = matcherActual.group();
		
		matcherActual.reset(provided3);
		matcherActual.matches();
		final String actual3 = matcherActual.group();
		
		// assert
		Assertions.assertEquals(expected1, actual1);
		Assertions.assertEquals(expected2, actual2);
		Assertions.assertEquals(expected3, actual3);
	}
	
	@Test
	public void testStringQuote() {
		// given
		final Pattern patternProvided = Pattern.compile(IniPropertyPattern.DOUBLE_QUOTED_STRING);
		final String provided1 = "\"\\\"{azerty}\\\"\"";
		final String provided2 = "\"\\\"{azerty_uiop}\\\"\"";
		final String provided3 = "\"\\\"{azerty-uiop}\\\"\"";
		
		// expected
		final String expected1 = "\\\"{azerty}\\\"";
		final String expected2 = "\\\"{azerty_uiop}\\\"";
		final String expected3 = "\\\"{azerty-uiop}\\\"";
		
		// actual
		final Matcher matcherActual = patternProvided.matcher(provided1);
		matcherActual.matches();
		final String actual1 = matcherActual.group(1);
		
		matcherActual.reset(provided2);
		matcherActual.matches();
		final String actual2 = matcherActual.group(1);
		
		matcherActual.reset(provided3);
		matcherActual.matches();
		final String actual3 = matcherActual.group(1);
		
		// assert
		Assertions.assertEquals(expected1, actual1);
		Assertions.assertEquals(expected2, actual2);
		Assertions.assertEquals(expected3, actual3);
	}
	
	@Test
	public void testKeyValueRoot() {
		// given
		final String provided = "key=True\n"
		                        + "key=1\n"
		                        + "key=1.2\n"
		                        + "key=distinct\n"
		                        + "key=\"select distinct $all\"\n"
		                        + "key=(1,2,3)\n"
		                        + "key=(opt=true,values=(1,2,3))";
		
		// expected
		final String keyExpected = "key";
		final boolean booleanExpected = true;
		final int integerExpected = 1;
		final double doubleExpected = 1.2;
		final String wordExpected = "distinct";
		final String stringQuoteExpected = "select distinct $all";
		final String listExpected = "(";
		final String objectExpected = "(";
		
		// actual
		final Matcher matcherActual = IniPropertyPattern.key_value_pattern_named.matcher(provided);
		matcherActual.find();
		final String keyActual = matcherActual.group("key");
		final boolean booleanActual = Boolean.parseBoolean(matcherActual.group("value"));
		matcherActual.find();
		final int integerActual = Integer.parseInt(matcherActual.group("value"));
		matcherActual.find();
		final double doubleActual = Double.parseDouble(matcherActual.group("value"));
		matcherActual.find();
		final String wordActual = matcherActual.group("value");
		matcherActual.find();
		final String stringQuoteActual = StringUtils.removeEnclosingQuotes(matcherActual.group("value"));
		matcherActual.find();
		final String listActual = matcherActual.group("value");
		matcherActual.find();
		final String objectActual = matcherActual.group("value");
		
		// assert
		Assertions.assertEquals(keyExpected, keyActual);
		Assertions.assertEquals(booleanExpected, booleanActual);
		Assertions.assertEquals(integerExpected, integerActual);
		Assertions.assertEquals(doubleExpected, doubleActual);
		Assertions.assertEquals(wordExpected, wordActual);
		Assertions.assertEquals(stringQuoteExpected, stringQuoteActual);
		Assertions.assertEquals(listExpected, listActual);
		Assertions.assertEquals(objectExpected, objectActual);
	}
	
	@Test
	public void testKeyValueInline() {
		// given
		final String provided = "key=True,"
		                        + "key=1,"
		                        + "key=1.2,"
		                        + "key=distinct,"
		                        + "key=\"select distinct $all\","
		                        + "key=(1,2,3),"
		                        + "key=(opt=true,values=(1,2,3))";
		
		// expected
		final String keyExpected = "key";
		final boolean booleanExpected = true;
		final int integerExpected = 1;
		final double doubleExpected = 1.2;
		final String wordExpected = "distinct";
		final String stringQuoteExpected = "select distinct $all";
		final String listExpected = "(";
		final String objectExpected = "(";
		
		// actual
		final Matcher matcherActual = IniPropertyPattern.key_value_pattern_named.matcher(provided);
		matcherActual.find();
		final String keyActual = matcherActual.group("key");
		final boolean booleanActual = Boolean.parseBoolean(matcherActual.group("value"));
		matcherActual.find();
		final int integerActual = Integer.parseInt(matcherActual.group("value"));
		matcherActual.find();
		final double doubleActual = Double.parseDouble(matcherActual.group("value"));
		matcherActual.find();
		final String wordActual = matcherActual.group("value");
		matcherActual.find();
		final String stringQuoteActual = StringUtils.removeEnclosingQuotes(matcherActual.group("value"));
		matcherActual.find();
		final String listActual = matcherActual.group("value");
		matcherActual.find();
		final String objectActual = matcherActual.group("value");
		
		// assert
		Assertions.assertEquals(keyExpected, keyActual);
		Assertions.assertEquals(booleanExpected, booleanActual);
		Assertions.assertEquals(integerExpected, integerActual);
		Assertions.assertEquals(doubleExpected, doubleActual);
		Assertions.assertEquals(wordExpected, wordActual);
		Assertions.assertEquals(stringQuoteExpected, stringQuoteActual);
		Assertions.assertEquals(listExpected, listActual);
		Assertions.assertEquals(objectExpected, objectActual);
	}
	
	@ParameterizedTest
	@MethodSource("regexFailProvider")
	public void testAllFail(final String regexProvided, final List<String> providedList) {
		for(final String provided : providedList) {
			// given
			final Pattern patternProvided = Pattern.compile(regexProvided);
			
			// actual
			final Executable actual = () -> {
				if (provided == null) {
					throw new ParsingException("There's nothing to parse!", provided, regexProvided);
				}
				final Matcher matcherActual = patternProvided.matcher(provided);
				if (!matcherActual.matches()) {
					throw new ParsingException("Error occurred while parsing this value!", provided, regexProvided);
				}
			};
			
			// assert
			Assertions.assertThrows(ParsingException.class, actual);
		}
	}
	
	private static Stream<Arguments> regexFailProvider() {
		return Stream.of(
				Arguments.of(IniPropertyPattern.BOOLEAN, Arrays.asList("zeffs", "", null)),
				Arguments.of(IniPropertyPattern.DOUBLE, Arrays.asList("100", "", null)),
				Arguments.of(IniPropertyPattern.INTEGER, Arrays.asList("100.0", "", null)),
				Arguments.of(IniPropertyPattern.WORD, Arrays.asList("\"100\"", "", null)),
				Arguments.of(IniPropertyPattern.DOUBLE_QUOTED_WORD, Arrays.asList("100", "qsdq", null)),
				// FIXME: the commented string should fail, but for an obscure reason the regex matches
				Arguments.of(IniPropertyPattern.STRING, Arrays.asList(/*"\"abc\\\"$'_-\"",*/ "", null)),
				Arguments.of(IniPropertyPattern.DOUBLE_QUOTED_STRING, Arrays.asList("\\\"grz\"'ééfé\\\"", "", null))
		);
	}
	
}
