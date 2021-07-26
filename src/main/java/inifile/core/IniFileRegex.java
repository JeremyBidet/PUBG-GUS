package fr.whyt.pubg.inifile.core;

import java.util.regex.Pattern;

public class IniFileRegex {
	
	private static final String boolean_regex = "[Tt]rue|[Ff]alse";
	private static final String double_regex = "-?\\d+\\.\\d+";
	private static final String integer_regex = "-?\\d+";
	private static final String word_regex = "[\\w\\-]+";
	private static final String word_quote_regex = "\"(" + word_regex + ")?\"";
	private static final String string_regex = "(\\\"|[^\"\r\n])+";
	private static final String string_quote_regex = "\"(" + string_regex + ")?\"";
	private static final String object_start_regex = "\\(";
	private static final String object_end_regex = "\\)";
	private static final String list_start_regex = "\\(";
	private static final String list_end_regex = "\\)";
	
	private static final String key_regex = word_regex;
	private static final String value_regex = "(" + boolean_regex + "|" + double_regex + "|" + integer_regex + "|" + string_quote_regex + "|" + word_regex + "|" + list_start_regex + "|" + object_start_regex + ")";
	private static final String key_value_regex = key_regex + "=" + value_regex + "(,\\n)?";
	private static final String key_value_regex_named = "(?<key>" + key_regex + ")" + "=" + "(?<value>" + value_regex + ")" + "(,\\n)?";
	
	private static final String object_regex = key_value_regex + "(," + key_value_regex + ")*";
	private static final String object_wrapper_regex = "\\(" + object_regex + "\\)";
	
	private static final String element_regex = "(" + boolean_regex + "|" + double_regex + "|" + integer_regex + "|" + string_quote_regex + "|" + word_regex + "|" + object_wrapper_regex + ")";
	private static final String list_regex = element_regex + "(," + element_regex + ")*";
	private static final String list_wrapper_regex = "\\(" + list_regex + "\\)";
	
	public static final Pattern boolean_pattern = Pattern.compile(boolean_regex);
	public static final Pattern double_pattern = Pattern.compile(double_regex);
	public static final Pattern integer_pattern = Pattern.compile(integer_regex);
	public static final Pattern word_pattern = Pattern.compile(word_regex);
	public static final Pattern word_quote_pattern = Pattern.compile(word_quote_regex);
	public static final Pattern string_pattern = Pattern.compile(string_regex);
	public static final Pattern string_quote_pattern = Pattern.compile(string_quote_regex);
	public static final Pattern object_start_pattern = Pattern.compile(object_start_regex);
	public static final Pattern object_end_pattern = Pattern.compile(object_end_regex);
	public static final Pattern list_start_pattern = Pattern.compile(list_start_regex);
	public static final Pattern list_end_pattern = Pattern.compile(list_end_regex);
	
	public static final Pattern key_pattern = Pattern.compile(key_regex);
	public static final Pattern value_pattern = Pattern.compile(value_regex);
	public static final Pattern key_value_pattern = Pattern.compile(key_value_regex);
	public static final Pattern key_value_pattern_named = Pattern.compile(key_value_regex_named);
	public static final Pattern object_pattern = Pattern.compile(object_regex);
	public static final Pattern object_wrapper_pattern = Pattern.compile(object_wrapper_regex);
	public static final Pattern element_pattern = Pattern.compile(element_regex);
	public static final Pattern list_pattern = Pattern.compile(list_regex);
	public static final Pattern list_wrapper_pattern = Pattern.compile(list_wrapper_regex);
	
}
