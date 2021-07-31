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

@IniWrapper(IniPropertyWrapper.PARENTHESIS)
public class Inline extends ClassHelper implements Serializable {
	
	@IniProperty(order = 1)
	public int integer;
	
	@IniProperty(name = "int", order = 2)
	public int integerRenamed;
	
	@IniProperty(name = "intF", format = "%4d", order = 3)
	public int integerFormatted;
	
	@IniProperty(name = "intP", format = "%4d", pattern = "\\d{4}", order = 4)
	public int integerPattern;
	
	@IniProperty(order = 5)
	public String string;
	
	@IniProperty(name = "raw", raw = true, order = 6)
	public String stringRaw;
	
	@IniProperty(name = "strOptDef", optional = true, order = 7)
	public String stringOptionalDefault;
	
	@IniProperty(name = "strOptEmpty", optional = true, order = 8)
	@IniOptional(IniOptionalValue.STRING_EMPTY)
	public String stringOptionalEmpty;
	
	@IniProperty(order = 9)
	public boolean bool;
	
	@IniProperty(name = "boolOptFalse", optional = true, order = 10)
	@IniOptional(IniOptionalValue.BOOLEAN_FALSE)
	public boolean boolOptionalFalse;
	
	static { IniOptionalValue.customize(IniOptionalValue.BOOLEAN, true); }
	@IniProperty(name = "boolOptCustomTrue", optional = true, order = 11)
	@IniOptional(IniOptionalValue.BOOLEAN)
	public boolean boolOptionalCustomTrue;
	
	@IniProperty(order = 12)
	public Brace brace;
	
	@IniProperty(order = 13)
	public Parenthesis parenthesis;
	
	@IniProperty(name = "bracketList", order = 14)
	@IniWrapper(IniPropertyWrapper.BRACKET)
	public List<Integer> intListWrappedWithBracket;
	
	@IniProperty(name = "parenthesisList", order = 15)
	@IniWrapper(IniPropertyWrapper.PARENTHESIS)
	public List<Parenthesis> parenthesisListWrappedWithParentheses;
	
	public Inline(final int integer, final int integerRenamed, final int integerFormatted, final int integerPattern,
	              final String string, final String stringRaw, final String stringOptionalDefault, final String stringOptionalEmpty,
	              final boolean bool, final boolean boolOptionalFalse, final boolean boolOptionalCustomTrue,
	              final Brace brace, final Parenthesis parenthesis,
	              final List<Integer> intListWrappedWithBracket, final List<Parenthesis> parenthesisListWrappedWithParentheses) {
		this.integer = integer;
		this.integerRenamed = integerRenamed;
		this.integerFormatted = integerFormatted;
		this.integerPattern = integerPattern;
		this.string = string;
		this.stringRaw = stringRaw;
		this.stringOptionalDefault = stringOptionalDefault;
		this.stringOptionalEmpty = stringOptionalEmpty;
		this.bool = bool;
		this.boolOptionalFalse = boolOptionalFalse;
		this.boolOptionalCustomTrue = boolOptionalCustomTrue;
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
				       && Objects.equals(stringOptionalDefault, inline.stringOptionalDefault)
				       && Objects.equals(stringOptionalEmpty, inline.stringOptionalEmpty)
				       && bool == inline.bool
				       && boolOptionalFalse == inline.boolOptionalFalse
				       && boolOptionalCustomTrue == inline.boolOptionalCustomTrue
				       && Objects.equals(brace, inline.brace)
				       && Objects.equals(parenthesis, inline.parenthesis)
				       && Objects.equals(intListWrappedWithBracket, inline.intListWrappedWithBracket)
				       && Objects.equals(parenthesisListWrappedWithParentheses, inline.parenthesisListWrappedWithParentheses);
	}
	
}
