package fr.whyt.pubg.data.iniimpl;

import fr.whyt.pubg.inifile.annotations.Property;
import fr.whyt.pubg.utils.ClassHelper;

import java.util.ArrayList;
import java.util.List;

public class CustomInputSettings extends ClassHelper {
	
	@Property(name = "ActionKeyList")
	public List<ActionKey> actionKeyList;
	
	@Property(name = "AxisKeyList")
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
