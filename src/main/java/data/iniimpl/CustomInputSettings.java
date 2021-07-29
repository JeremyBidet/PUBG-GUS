package fr.whyt.pubg.data.iniimpl;

import fr.whyt.pubg.inifile.annotations.IniProperty;
import fr.whyt.pubg.inifile.annotations.IniRoot;
import fr.whyt.pubg.utils.ClassHelper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"unused", "CanBeFinal"})
@IniRoot
public class CustomInputSettings extends ClassHelper implements Serializable {
	
	@IniProperty(name = "ActionKeyList")
	public List<ActionKey> actionKeyList;
	
	@IniProperty(name = "AxisKeyList")
	public List<AxisKey> axisKeyList;
	
	public CustomInputSettings(final List<ActionKey> actionKeyList,
	                           final List<AxisKey> axisKeyList) {
		this.actionKeyList = actionKeyList;
		this.axisKeyList = axisKeyList;
	}
	
	public CustomInputSettings() {
		this(new ArrayList<>(), new ArrayList<>());
	}
	
}
