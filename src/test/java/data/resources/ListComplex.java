package fr.whyt.pubg.data.resources;

import fr.whyt.pubg.inifile.annotations.IniProperty;
import fr.whyt.pubg.inifile.annotations.IniWrapper;
import fr.whyt.pubg.inifile.core.IniPropertyWrapper;

import java.util.List;
import java.util.Objects;

public class ListComplex {
	
	@IniProperty
	@IniWrapper(IniPropertyWrapper.BRACKET)
	public List<List<Integer>> bracketIntListList;
	
	public ListComplex(final List<List<Integer>> bracketIntListList) {
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
