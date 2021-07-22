package fr.whyt.pubg.deprecated.parser.data;

import fr.whyt.pubg.deprecated.parser.IniFile;
import fr.whyt.pubg.deprecated.parser.ParserCIS;
import fr.whyt.pubg.deprecated.parser.exceptions.ParsingException;
import fr.whyt.pubg.deprecated.parser.exceptions.WritingException;
import fr.whyt.pubg.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;

public class AxisKeyList implements IniFile<AxisKeyList> {
	
	public final List<AxisKey> axisKeyList;
	
	public AxisKeyList(final List<AxisKey> axisKeyList) {
		this.axisKeyList = Objects.requireNonNull(axisKeyList);
	}
	
	public AxisKeyList() {
		this(new ArrayList<>());
	}
	
	@Override
	public int hashCode() {
		int result = axisKeyList.hashCode();
		return result;
	}
	
	@Override
	public boolean equals(final Object o) {
		if (this == o) return true;
		if (!(o instanceof AxisKeyList)) return false;
		
		final AxisKeyList axisKeyList = (AxisKeyList) o;
		
		return Objects.deepEquals(this.axisKeyList, axisKeyList.axisKeyList);
	}
	
	@Override
	public String toString() {
		return "AxisKeyList { axisKeyList=" + axisKeyList + " }";
	}
	
	@Override
	public AxisKeyList deserialize(final String source) throws ParsingException {
		final Matcher m_axisKeyList = ParserCIS.p_axisKeyListNamed.matcher(source);
		if (!m_axisKeyList.matches()) {
			throw new ParsingException("The AxisKeyList does not match with the expected format!", source, ParserCIS.p_axisKeyListNamed);
		}
		final String s_axisKeyList = m_axisKeyList.group("axisKeyList");
		
		final AxisKeyList axisKeyList = new AxisKeyList();
		
		final Matcher m_axisKey = ParserCIS.p_axisKey.matcher(s_axisKeyList);
		while (m_axisKey.find()) {
			final AxisKey axisKey = IniFile.deserialize(AxisKey.class, m_axisKey.group());
			axisKeyList.axisKeyList.add(axisKey);
		}
		
		return axisKeyList;
	}
	
	@Override
	public String serialize() throws WritingException {
		return "AxisKeyList=("
			       + axisKeyList.stream()
					         .map(ak -> {
					         	try {
					         		return ak.serialize();
					         	} catch (WritingException e) {
					         		e.printStackTrace();
					         		return null;
					         	}})
					         .reduce(StringUtils::joinComma)
					         .orElse("")
		       + ")";
	}
	
}
