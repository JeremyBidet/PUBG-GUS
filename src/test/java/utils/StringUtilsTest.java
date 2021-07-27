package fr.whyt.pubg.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@SuppressWarnings({"ConstantConditions"})
public class StringUtilsTest {
	
	@Test
	public void testIsBlank() {
		// given
		final String provided1 = "     ";
		final String provided2 = "";
		final String provided3 = null;
		
		// expected
		
		// actual
		
		// assert
		Assertions.assertTrue(StringUtils.isBlank(provided1));
		Assertions.assertTrue(StringUtils.isBlank(provided2));
		Assertions.assertTrue(StringUtils.isBlank(provided3));
	}
	
	@Test
	public void testIsEmpty() {
		// given
		final String provided1 = "     ";
		final String provided2 = "";
		final String provided3 = null;
		
		// expected
		
		// actual
		
		// assert
		Assertions.assertFalse(StringUtils.isEmpty(provided1));
		Assertions.assertTrue(StringUtils.isEmpty(provided2));
		Assertions.assertTrue(StringUtils.isEmpty(provided3));
	}
	
	@Test
	public void testRemoveEnclosingQuotes() {
		// given
		final String provided1 = "dfsdf";
		final String provided2 = "\"dfsfds\"";
		final String provided3 = "\"\"";
		final String provided4 = "";
		final String provided5 = null;
		
		// expected
		final String expected1 = "dfsdf";
		final String expected2 = "dfsfds";
		final String expected3 = "";
		final String expected4 = "";
		final String expected5 = null;
		
		// actual
		final String actual1 = StringUtils.removeEnclosingQuotes(provided1);
		final String actual2 = StringUtils.removeEnclosingQuotes(provided2);
		final String actual3 = StringUtils.removeEnclosingQuotes(provided3);
		final String actual4 = StringUtils.removeEnclosingQuotes(provided4);
		final String actual5 = StringUtils.removeEnclosingQuotes(provided5);
		
		// assert
		Assertions.assertEquals(expected1, actual1);
		Assertions.assertEquals(expected2, actual2);
		Assertions.assertEquals(expected3, actual3);
		Assertions.assertEquals(expected4, actual4);
		Assertions.assertEquals(expected5, actual5);
	}
	
	@Test
	public void testJoinComma() {
		// given
		final String[] provided1 = { "A", "B", "C" };
		final String[] provided2 = { null, null, null };
		final String[] provided3 = { "A", null, "C" };
		final String provided4 = "";
		final String provided5 = null;
		final String[] provided6 = null;
		
		// expected
		final String expected1 = "A,B,C";
		final String expected2 = "null,null,null";
		final String expected3 = "A,null,C";
		final String expected4 = "";
		final String expected5 = "null";
		final String expected6 = "";
		
		// actual
		final String actual1 = StringUtils.joinComma(provided1);
		final String actual2 = StringUtils.joinComma(provided2);
		final String actual3 = StringUtils.joinComma(provided3);
		final String actual4 = StringUtils.joinComma(provided4);
		final String actual5 = StringUtils.joinComma(provided5);
		final String actual6 = StringUtils.joinComma(provided6);
		
		// assert
		Assertions.assertEquals(expected1, actual1);
		Assertions.assertEquals(expected2, actual2);
		Assertions.assertEquals(expected3, actual3);
		Assertions.assertEquals(expected4, actual4);
		Assertions.assertEquals(expected5, actual5);
		Assertions.assertEquals(expected6, actual6);
	}
	
}
