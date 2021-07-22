package fr.whyt.pubg.data.iniimpl;

import fr.whyt.pubg.inifile.annotations.Property;
import fr.whyt.pubg.utils.ClassHelper;
import fr.whyt.pubg.utils.annotations.Identity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ActionKey extends ClassHelper {
	
	@Identity
	@Property(name = "ActionName", raw = false)
	public String actionName;
	
	@Property(name = "Keys")
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
