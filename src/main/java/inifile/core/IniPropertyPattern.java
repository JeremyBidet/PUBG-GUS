package fr.whyt.pubg.inifile.core;

import java.util.regex.Pattern;

@SuppressWarnings("SpellCheckingInspection")
public class IniPropertyPattern {
	
	// Useful patterns for format option in IniProperty annotation
	
	/**
	 * true | True => true<br>
	 * false | False => false
	 */
	public static final String BOOLEAN = "[Tt]rue|[Ff]alse";
	/**
	 * 1.0, -1.2345
	 */
	public static final String DOUBLE = "-?\\d+\\.\\d+";
	/**
	 * 1, -2
	 */
	public static final String INTEGER = "-?\\d+";
	/**
	 * every characters excepted spaces
	 */
	public static final String CHARACTER = "[^\\s]";
	/**
	 * abc-DEF_012
	 */
	public static final String WORD = "[a-zA-Z][\\w\\-]*";
	/**
	 * All characters excepted {@code \r} (carriage return) and {@code \n} (line return).<br>
	 * Double quote {@code "} should be escaped {@code \"}.<br>
	 * <br>
	 * Note: in Java double quote is already escaped in strings {@code \"}, so please use double escape {@code \\\"}
	 */
	public static final String STRING = "(?:\\\"|[^\"\\r\\n])+";
	/**
	 * Like {@linkplain IniPropertyPattern#WORD WORD} but with enclosing double quote {@code "word"}.
	 */
	public static final String DOUBLE_QUOTED_WORD = "\"((?:" + WORD + ")?)\"";
	/**
	 * Like {@linkplain IniPropertyPattern#STRING STRING} but with enclosing double quote {@code "string"}.
	 */
	public static final String DOUBLE_QUOTED_STRING = "\"((?:" + STRING + ")?)\"";
	
	
	// Local regex and pattern used in the mapper IniFileMapper
	
	static final char list_separator = ',';
	static final char object_key_value_separator = ',';
	
	private static final String key_regex = WORD;
	private static final String value_regex = ".+";
	private static final String key_value_regex_named = "(?<key>" + key_regex + ")" + "=" + "(?<value>" + value_regex + ")";
	
	static final Pattern key_value_pattern_named = Pattern.compile(IniPropertyPattern.key_value_regex_named);
	
}
