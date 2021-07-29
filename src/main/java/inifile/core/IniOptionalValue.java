package fr.whyt.pubg.inifile.core;

import java.util.EnumSet;

/**
 * Define some convenient default optional values, and customizable values.<br>
 * <br>
 * Predefined values:
 * <ul>
 *     <li>{@link IniOptionalValue#NULL} => {@code no value for any type}</li>
 *     <li>{@link IniOptionalValue#BOOLEAN_TRUE} => {@code true}</li>
 *     <li>{@link IniOptionalValue#BOOLEAN_FALSE} => {@code false}</li>
 *     <li>{@link IniOptionalValue#INTEGER_0} => {@code 0}</li>
 *     <li>{@link IniOptionalValue#DOUBLE_0} => {@code 0.0}</li>
 *     <li>{@link IniOptionalValue#STRING_EMPTY} => {@code }</li>
 *     <li>{@link IniOptionalValue#DOUBLE_QUOTED_STRING_EMPTY} => {@code ""}</li>
 * </ul>
 *
 * Custom values:
 * <ul>
 *     <li>{@link IniOptionalValue#BOOLEAN}</li>
 *     <li>{@link IniOptionalValue#INTEGER}</li>
 *     <li>{@link IniOptionalValue#DOUBLE}</li>
 *     <li>{@link IniOptionalValue#STRING}</li>
 *     <li>{@link IniOptionalValue#DOUBLE_QUOTED_STRING}</li>
 * </ul>
 */
public enum IniOptionalValue {
	
	NULL(null),
	
	BOOLEAN(null),
	BOOLEAN_TRUE(true),
	BOOLEAN_FALSE(false),
	
	INTEGER(null),
	INTEGER_0(0),
	
	DOUBLE(null),
	DOUBLE_0(0.0),
	
	STRING(null),
	STRING_EMPTY(""),
	
	DOUBLE_QUOTED_STRING(null),
	DOUBLE_QUOTED_STRING_EMPTY("\"\""),
	;
	
	private Object value;
	
	IniOptionalValue(final Object value) {
		this.value = value;
	}
	
	public Object getValue() {
		return this.value;
	}
	private void setValue(final Object value) {
		this.value = value;
	}
	
	public static void customize(final IniOptionalValue iniOptionalValue, final Object value) {
		if (!EnumSet.of(BOOLEAN, INTEGER, DOUBLE, STRING, DOUBLE_QUOTED_STRING).contains(iniOptionalValue)) {
			return;
		}
		iniOptionalValue.setValue(value);
	}
	
}
