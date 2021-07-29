package fr.whyt.pubg.data.resources;

import fr.whyt.pubg.inifile.annotations.IniOptional;
import fr.whyt.pubg.inifile.annotations.IniProperty;
import fr.whyt.pubg.inifile.annotations.IniWrapper;
import fr.whyt.pubg.inifile.core.IniOptionalValue;
import fr.whyt.pubg.inifile.core.IniPropertyWrapper;
import fr.whyt.pubg.utils.ClassHelper;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class Inline extends ClassHelper implements Serializable {
	
	@IniProperty
	public int integer;
	
	@IniProperty(name = "int")
	public int integerRenamed;
	
	@IniProperty(format = "%4d")
	public int integerFormatted;
	
	@IniProperty(format = "%4d", pattern = "\\d{4}")
	public int integerPattern;
	
	@IniProperty
	public String string;
	
	@IniProperty(raw = true)
	public String stringRaw;
	
	@IniProperty(optional = true)
	public String stringOptional;
	
	@IniProperty
	public boolean bool;
	
	static { IniOptionalValue.customize(IniOptionalValue.BOOLEAN, true); }
	@IniProperty(optional = true)
	@IniOptional(IniOptionalValue.BOOLEAN)
	public boolean boolOptionalCustomTrue;
	
	@IniProperty(optional = true)
	@IniOptional(IniOptionalValue.BOOLEAN_FALSE)
	public boolean boolOptionalFalse;
	
	@IniProperty
	public Brace brace;
	
	@IniProperty
	public Parenthesis parenthesis;
	
	@IniProperty
	@IniWrapper(IniPropertyWrapper.BRACKET)
	public List<Integer> intListWrappedWithBracket;
	
	@IniProperty
	@IniWrapper(IniPropertyWrapper.PARENTHESIS)
	public List<Parenthesis> parenthesisListWrappedWithParentheses;
	
	public Inline(final int integer, final int integerRenamed, final int integerFormatted, final int integerPattern,
	              final String string, final String stringRaw, final String stringOptional,
	              final boolean bool, final boolean boolOptionalCustomTrue, final boolean boolOptionalFalse,
	              final Brace brace, final Parenthesis parenthesis,
	              final List<Integer> intListWrappedWithBracket, final List<Parenthesis> parenthesisListWrappedWithParentheses) {
		this.integer = integer;
		this.integerRenamed = integerRenamed;
		this.integerFormatted = integerFormatted;
		this.integerPattern = integerPattern;
		this.string = string;
		this.stringRaw = stringRaw;
		this.stringOptional = stringOptional;
		this.bool = bool;
		this.boolOptionalCustomTrue = boolOptionalCustomTrue;
		this.boolOptionalFalse = boolOptionalFalse;
		this.brace = brace;
		this.parenthesis = parenthesis;
		this.intListWrappedWithBracket = intListWrappedWithBracket;
		this.parenthesisListWrappedWithParentheses = parenthesisListWrappedWithParentheses;
	}
	@SuppressWarnings("unused") public Inline() {}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		final Inline inline = (Inline) o;
		return integer == inline.integer
				       && integerRenamed == inline.integerRenamed
				       && integerFormatted == inline.integerFormatted
				       && integerPattern == inline.integerPattern
				       && Objects.equals(string, inline.string)
				       && Objects.equals(stringRaw, inline.stringRaw)
				       && Objects.equals(stringOptional, inline.stringOptional)
				       && bool == inline.bool
				       && boolOptionalCustomTrue == inline.boolOptionalCustomTrue
				       && boolOptionalFalse == inline.boolOptionalFalse
				       && Objects.equals(brace, inline.brace)
				       && Objects.equals(parenthesis, inline.parenthesis)
				       && Objects.equals(intListWrappedWithBracket, inline.intListWrappedWithBracket)
				       && Objects.equals(parenthesisListWrappedWithParentheses, inline.parenthesisListWrappedWithParentheses);
	}
	
}
