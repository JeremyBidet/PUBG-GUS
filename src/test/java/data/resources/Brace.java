package fr.whyt.pubg.data.resources;

import fr.whyt.pubg.inifile.annotations.IniProperty;
import fr.whyt.pubg.inifile.annotations.IniWrapper;
import fr.whyt.pubg.inifile.core.IniPropertyWrapper;
import fr.whyt.pubg.utils.ClassHelper;

import java.io.Serializable;

@IniWrapper(IniPropertyWrapper.BRACE)
public class Brace extends ClassHelper implements Serializable {
	
	@IniProperty
	public char character;
	
	public Brace(final char character) {
		this.character = character;
	}
	@SuppressWarnings("unused") public Brace() {}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		final Brace brace = (Brace) o;
		return character == brace.character;
	}
	
}
