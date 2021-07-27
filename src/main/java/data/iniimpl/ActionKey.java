package fr.whyt.pubg.data.iniimpl;

import fr.whyt.pubg.inifile.annotations.IniProperty;
import fr.whyt.pubg.utils.ClassHelper;
import fr.whyt.pubg.utils.annotations.Identity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ActionKey extends ClassHelper implements Serializable {
	
	@Identity
	@IniProperty(name = "ActionName", raw = false)
	public String actionName;
	
	@IniProperty(name = "Keys")
	public List<Key> keyList;
	
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
	
}
