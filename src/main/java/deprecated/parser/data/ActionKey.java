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

public class ActionKey implements IniFile<ActionKey> {
	
	public final String actionName;
	public final List<Key> keyList;
	
	public ActionKey(final String actionName, final List<Key> keyList) {
		this.actionName = actionName;
		this.keyList = Objects.requireNonNull(keyList);
	}
	
	public ActionKey(final String actionName) {
		this(actionName, new ArrayList<>());
	}
	
	public ActionKey() {
		this(null, new ArrayList<>());
	}
	
	@Override
	public int hashCode() {
		int result = actionName.hashCode();
		return result;
	}
	
	@Override
	public boolean equals(final Object o) {
		if (this == o) return true;
		if (!(o instanceof ActionKey)) return false;
		
		final ActionKey actionKey = (ActionKey) o;
		
		return Objects.equals(actionName, actionKey.actionName);
	}
	
	@Override
	public String toString() {
		return "ActionKey { actionName=" + actionName + ", keys=" + keyList + " }";
	}
	
	@Override
	public ActionKey deserialize(final String source) throws ParsingException {
		final Matcher m_actionKey = ParserCIS.p_actionKeyNamed.matcher(source);
		if (!m_actionKey.matches()) {
			throw new ParsingException("The ActionKey does not match with the expected format!", source, ParserCIS.p_actionKeyNamed);
		}
		final String actionName = m_actionKey.group("actionName");
		final String s_keys = m_actionKey.group("keys");
		
		final ActionKey actionKey = new ActionKey(actionName);
		
		final Matcher m_key = ParserCIS.p_keyNamed.matcher(s_keys);
		while (m_key.find()) {
			final Key key = IniFile.deserialize(Key.class, m_key.group());
			actionKey.keyList.add(key);
		}
		
		return actionKey;
	}
	
	@Override
	public String serialize() throws WritingException {
		if (StringUtils.isEmpty(actionName)) {
			throw new WritingException("The ActionName key cannot be null or empty", this);
		}
		return "("
			       + "ActionName=\"" +actionName + "\","
			       + "Keys=(" +
			            keyList.stream()
					            .map(k -> {
					            	try {
					            		return k.serialize();
					            	} catch (WritingException e) {
					            		e.printStackTrace();
					            		return null;
					            	}})
					            .reduce(StringUtils::joinComma)
					            .orElse("")
			       + ")"
		       + ")";
	}
	
}
