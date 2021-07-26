package fr.whyt.pubg.data.iniimpl;

import fr.whyt.pubg.inifile.core.IniFileMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

public class ActionKeyTest {
	
	@ParameterizedTest
	@MethodSource("dataProvider")
	public void testDeserialize(final String provided, final ActionKey expected) {
		// actual
		final ActionKey actual = IniFileMapper.deserialize(provided, ActionKey.class);
		
		// assert
		Assertions.assertEquals(expected, actual);
	}
	
	@ParameterizedTest
	@MethodSource("dataProvider")
	public void testSerialize(final String expected, final ActionKey provided) {
		// actual
		final String actual = IniFileMapper.serialize(provided);
		
		// assert
		Assertions.assertEquals(expected, actual);
	}
	
	/**
	 * The first argument is the serialized data.<br>
	 * The second argument is the deserialized data.<br>
	 * @return the stream containing the arguments
	 */
	private static Stream<Arguments> dataProvider() {
		return Stream.of(
				Arguments.of("ActionName=\"Walk\","            + "Keys=((Key=ThumbMouseButton2),())", new ActionKey("Walk",           List.of(new Key("ThumbMouseButton2"), new Key()))),
				Arguments.of("ActionName=\"Sprint\","          + "Keys=((Key=LeftShift))",            new ActionKey("Sprint",         List.of(new Key("LeftShift")))),
				Arguments.of("ActionName=\"Jump\","            + "Keys=((Key=SpaceBar))",             new ActionKey("Jump",           List.of(new Key("SpaceBar")))),
				Arguments.of("ActionName=\"JumpOnly\","        + "Keys=((Key=B),())",                 new ActionKey("JumpOnly",       List.of(new Key("B"), new Key()))),
				Arguments.of("ActionName=\"VaultOnly\","       + "Keys=(())",                         new ActionKey("VaultOnly",      List.of(new Key()))),
				Arguments.of("ActionName=\"ToggleCrouch\","    + "Keys=((Key=LeftControl),())",       new ActionKey("ToggleCrouch",   List.of(new Key("LeftControl"), new Key()))),
				Arguments.of("ActionName=\"ToggleProne\","     + "Keys=((Key=C),())",                 new ActionKey("ToggleProne",    List.of(new Key("C"), new Key()))),
				Arguments.of("ActionName=\"Interact\","        + "Keys=((Key=F))",                    new ActionKey("Interact",       List.of(new Key("F"))))
		);
	}
	
}
