package fr.whyt.pubg.data.resources;

import fr.whyt.pubg.inifile.annotations.IniProperty;
import fr.whyt.pubg.inifile.annotations.IniWrapper;
import fr.whyt.pubg.inifile.core.IniPropertyWrapper;
import fr.whyt.pubg.utils.ClassHelper;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@IniWrapper(IniPropertyWrapper.PARENTHESIS)
public class ListSimple extends ClassHelper implements Serializable {
	
	@IniProperty(order = 1)
	@IniWrapper(IniPropertyWrapper.BRACKET)
	public List<Integer> bracketIntList;
	
	@IniProperty(order = 2)
	@IniWrapper(IniPropertyWrapper.PARENTHESIS)
	public List<String> parenthesisStringList;
	
	public ListSimple(final List<Integer> bracketIntList,
	                  final List<String> parenthesisStringList) {
		this.bracketIntList = bracketIntList;
		this.parenthesisStringList = parenthesisStringList;
	}
	@SuppressWarnings("unused") public ListSimple() {}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		final ListSimple listSimple = (ListSimple) o;
		return Objects.equals(bracketIntList, listSimple.bracketIntList)
		       && Objects.equals(parenthesisStringList, listSimple.parenthesisStringList);
	}
	
}
