package fr.whyt.pubg.data.resources;

import fr.whyt.pubg.inifile.annotations.IniProperty;
import fr.whyt.pubg.inifile.annotations.IniWrapper;
import fr.whyt.pubg.inifile.core.IniPropertyWrapper;

public class ObjectNested {
	
	public Level1 level1;
	
	public ObjectNested(final Level1 level1) {
		this.level1 = level1;
	}
	@SuppressWarnings("unused") public ObjectNested() {}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		final ObjectNested objectNested = (ObjectNested) o;
		return level1 == objectNested.level1;
	}

	@IniWrapper(IniPropertyWrapper.PARENTHESIS)
	public static class Level1 {
		@IniProperty
		public Level2 level2;
		
		public Level1(final Level2 level2) {
			this.level2 = level2;
		}
		@SuppressWarnings("unused") public Level1() {}
		
		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;
			final Level1 level1 = (Level1) o;
			return level2 == level1.level2;
		}
	}
	
	@IniWrapper(IniPropertyWrapper.PARENTHESIS)
	public static class Level2 {
		@IniProperty
		public Level3 level3;
		
		public Level2(final Level3 level3) {
			this.level3 = level3;
		}
		@SuppressWarnings("unused") public Level2() {}
		
		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;
			final Level2 level2 = (Level2) o;
			return level3 == level2.level3;
		}
	}
	
	@IniWrapper(IniPropertyWrapper.BRACE)
	public static class Level3 {
		@IniProperty
		public int integer;
		
		public Level3(final int integer) {
			this.integer = integer;
		}
		@SuppressWarnings("unused") public Level3() {}
		
		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;
			final Level3 level3 = (Level3) o;
			return integer == level3.integer;
		}
	}

}
