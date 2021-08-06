package fr.whyt.pubg.data.resources;

import fr.whyt.pubg.inifile.annotations.IniProperty;
import fr.whyt.pubg.inifile.annotations.IniWrapper;
import fr.whyt.pubg.inifile.core.IniPropertyWrapper;
import fr.whyt.pubg.utils.ClassHelper;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@IniWrapper(IniPropertyWrapper.PARENTHESIS)
public class ListComplex extends ClassHelper implements Serializable {
	
	@IniProperty
	@IniWrapper(IniPropertyWrapper.BRACKET)
	public List<Set<Integer>> bracketIntListList;
	
	public ListComplex(final List<Set<Integer>> bracketIntListList) {
		this.bracketIntListList = bracketIntListList;
	}
	@SuppressWarnings("unused") public ListComplex() {}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		final ListComplex listComplex = (ListComplex) o;
		return Objects.deepEquals(bracketIntListList, listComplex.bracketIntListList);
	}
	
}
