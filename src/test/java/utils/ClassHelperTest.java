package fr.whyt.pubg.utils;

import fr.whyt.pubg.utils.annotations.Identity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class ClassHelperTest {
	
	private static class A extends ClassHelper {
		@Identity
		public final String key1;
		@Identity
		public final B key2;
		public final List<Integer> key3;
		
		public A(final String key1, final B key2, final List<Integer> key3) {
			this.key1 = key1;
			this.key2 = key2;
			this.key3 = key3;
		}
	}
	
	private static class B extends ClassHelper {
		@Identity
		public final int key1;
		public final String key2;
		public final boolean key3;
		
		public B(final int key1, final String key2, final boolean key3) {
			this.key1 = key1;
			this.key2 = key2;
			this.key3 = key3;
		}
	}
	
	private static final A a1Provided = new A("A", new B(1, "B", true), Arrays.asList(1, 2, 3));
	private static final A a2Provided = new A("A", new B(1, "BB", false), Arrays.asList(4, 5, 6));
	private static final A a3Provided = new A("AA", new B(1, "B", true), Arrays.asList(1, 2, 3));
	private static final A a4Provided = new A("A", new B(2, "B", true), Arrays.asList(1, 2, 3));
	private static final A a5Provided = null;
	
	@Test
	public void testEquals() {
		// given
		
		// expected
		
		// actual
		
		// assert
		Assertions.assertEquals(a1Provided, a2Provided);
		Assertions.assertNotEquals(a1Provided, a3Provided);
		Assertions.assertNotEquals(a1Provided, a4Provided);
		Assertions.assertNotEquals(a5Provided, a1Provided);
		Assertions.assertNotEquals(a3Provided, a4Provided);
	}
	
	@Test
	public void testHashCode() {
		// given
		
		// expected
		final int a1Expected = 31 * (31 + a1Provided.key1.hashCode()) + (31 + Integer.hashCode(a1Provided.key2.key1));
		final int a2Expected = 31 * (31 + a2Provided.key1.hashCode()) + (31 + Integer.hashCode(a2Provided.key2.key1));
		final int a3Expected = 31 * (31 + a3Provided.key1.hashCode()) + (31 + Integer.hashCode(a3Provided.key2.key1));
		final int a4Expected = 31 * (31 + a4Provided.key1.hashCode()) + (31 + Integer.hashCode(a4Provided.key2.key1));
		
		// actual
		final int a1Actual = a1Provided.hashCode();
		final int a2Actual = a2Provided.hashCode();
		final int a3Actual = a3Provided.hashCode();
		final int a4Actual = a4Provided.hashCode();
		
		// assert
		Assertions.assertEquals(a1Expected, a1Actual);
		Assertions.assertEquals(a2Expected, a2Actual);
		Assertions.assertEquals(a3Expected, a3Actual);
		Assertions.assertEquals(a4Expected, a4Actual);
	}
	
	@Test
	public void testToString() {
		// given
		
		// expected
		final String a1Expected = "A { key1=A, key2=B { key1=1, key2=B, key3=true }, key3=[1, 2, 3] }";
		final String a2Expected = "A { key1=A, key2=B { key1=1, key2=BB, key3=false }, key3=[4, 5, 6] }";
		final String a3Expected = "A { key1=AA, key2=B { key1=1, key2=B, key3=true }, key3=[1, 2, 3] }";
		final String a4Expected = "A { key1=A, key2=B { key1=2, key2=B, key3=true }, key3=[1, 2, 3] }";
		
		// actual
		final String a1Actual = a1Provided.toString();
		final String a2Actual = a2Provided.toString();
		final String a3Actual = a3Provided.toString();
		final String a4Actual = a4Provided.toString();
		
		// assert
		Assertions.assertEquals(a1Expected, a1Actual);
		Assertions.assertEquals(a2Expected, a2Actual);
		Assertions.assertEquals(a3Expected, a3Actual);
		Assertions.assertEquals(a4Expected, a4Actual);
	}
	
}
