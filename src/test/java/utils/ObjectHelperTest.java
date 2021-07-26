package fr.whyt.pubg.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ObjectHelperTest {
	
	@Test
	public void testOrElse() {
		// given
		final String backupProvided = "backup";
		final String provided1 = "test";
		final String provided2 = null;
		
		// expected
		final String expected1 = "test";
		final String expected2 = backupProvided;
		
		// actual
		final String actual1 = ObjectHelper.orElse(provided1, backupProvided);
		final String actual2 = ObjectHelper.orElse(provided2, backupProvided);
		
		// assert
		Assertions.assertEquals(expected1, actual1);
		Assertions.assertEquals(expected2, actual2);
	}
	
	@Test
	public void testGetOrDefault_simple() {
		// given
		final int backupProvided = 0;
		final String provided1 = "test";
		final String provided2 = null;
		
		// expected
		final int expected1 = 4;
		final int expected2 = backupProvided;
		
		// actual
		final int actual1 = ObjectHelper.getOrDefault(provided1, backupProvided, String::length);
		final int actual2 = ObjectHelper.getOrDefault(provided2, backupProvided, String::length);
		
		// assert
		Assertions.assertEquals(expected1, actual1);
		Assertions.assertEquals(expected2, actual2);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testGetOrDefault_varargs() {
		// given
		final int backupProvided = 0;
		final String provided1 = "test";
		final String provided2 = null;
		
		// expected
		final int expected1 = 4;
		final int expected2 = backupProvided;
		
		// actual
		final int actual1 = ObjectHelper.<String, Integer>getOrDefault(provided1, backupProvided, s -> ((String) s).toCharArray(), a -> ((char[]) a).length);
		final int actual2 = ObjectHelper.<String, Integer>getOrDefault(provided2, backupProvided, s -> ((String) s).toCharArray(), a -> ((char[]) a).length);
		
		// assert
		Assertions.assertEquals(expected1, actual1);
		Assertions.assertEquals(expected2, actual2);
	}
	
}
