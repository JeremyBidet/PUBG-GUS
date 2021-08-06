package fr.whyt.pubg.data.resources;

import fr.whyt.pubg.inifile.annotations.IniProperty;
import fr.whyt.pubg.inifile.annotations.IniWrapper;
import fr.whyt.pubg.inifile.core.IniPropertyWrapper;
import fr.whyt.pubg.utils.ClassHelper;

import java.io.Serializable;

@IniWrapper(IniPropertyWrapper.PARENTHESIS)
public class Parenthesis extends ClassHelper implements Serializable {
	
	@IniProperty
	public double decimal;
	@IniProperty
	public int precision;
	
	public Parenthesis(final double decimal, final int precision) {
		this.decimal = decimal;
		this.precision = precision;
	}
	
	@SuppressWarnings("unused")
	public Parenthesis() {}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		final Parenthesis parenthesis = (Parenthesis) o;
		return Double.compare(decimal, parenthesis.decimal) == 0 && precision == parenthesis.precision;
	}
	
}
