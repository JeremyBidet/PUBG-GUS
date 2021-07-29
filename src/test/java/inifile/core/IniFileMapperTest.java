package fr.whyt.pubg.inifile.core;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

@SuppressWarnings({"rawtypes", "unchecked", "unused", "CommentedOutCode"})
public class IniFileMapperTest {
	
	private static Stream inlineProviders() {
		return Stream.of(
				Arguments.of("inline.ini", List.of())
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
	
}
