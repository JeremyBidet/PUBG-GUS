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

public class ActionKeyList implements IniFile<ActionKeyList> {
	
	public final List<ActionKey> actionKeyList;
	
	public ActionKeyList(final List<ActionKey> actionKeyList) {
		this.actionKeyList = Objects.requireNonNull(actionKeyList);
	}
	
	public ActionKeyList() {
		this(new ArrayList<>());
	}
	
	@Override
	public int hashCode() {
		return actionKeyList.hashCode();
	}
	
	@Override
	public boolean equals(final Object o) {
		if (this == o) return true;
		if (!(o instanceof ActionKeyList)) return false;
		
		final ActionKeyList actionKeyList = (ActionKeyList) o;
		
		return Objects.deepEquals(this.actionKeyList, actionKeyList.actionKeyList);
	}
	
	@Override
	public String toString() {
		return "ActionKeyList { actionKeyList=" + actionKeyList + " }";
	}
	
	@Override
	public ActionKeyList deserialize(final String source) throws ParsingException {
		final Matcher m_actionKeyList = ParserCIS.p_actionKeyListNamed.matcher(source);
		if (!m_actionKeyList.matches()) {
			throw new ParsingException("The ActionKeyList does not match with the expected format!", source, ParserCIS.p_actionKeyListNamed);
		}
		final String s_actionKeyList = m_actionKeyList.group("actionKeyList");
		
		final ActionKeyList actionKeyList = new ActionKeyList();
		
		final Matcher m_actionKey = ParserCIS.p_actionKey.matcher(s_actionKeyList);
		while (m_actionKey.find()) {
			final ActionKey actionKey = IniFile.deserialize(ActionKey.class, m_actionKey.group());
			actionKeyList.actionKeyList.add(actionKey);
		}
		
		return actionKeyList;
	}
	
	@Override
	public String serialize() throws WritingException {
		return "ActionKeyList=("
			       + actionKeyList.stream()
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
