package fr.whyt.pubg.data.iniimpl;

import fr.whyt.pubg.inifile.annotations.IniProperty;
import fr.whyt.pubg.utils.ClassHelper;
import fr.whyt.pubg.utils.annotations.Identity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@SuppressWarnings("CanBeFinal")
public class AxisKey extends ClassHelper implements Serializable {
	
	@Identity
	@IniProperty(name = "AxisName")
	public String axisName;
	
	@IniProperty(name = "Scale")
	public double scale;
	
	@IniProperty(name = "Keys")
	public List<Key> keyList;
	
	public AxisKey(final String axisName, final double scale, final List<Key> keyList) {
		this.axisName = axisName;
		this.scale = scale;
		this.keyList = Objects.requireNonNull(keyList);
	}
	
	@SuppressWarnings("unused")
	public AxisKey(final String axisName, final double scale) {
		this(axisName, scale, new ArrayList<>());
	}
	
	@SuppressWarnings("unused")
	public AxisKey() {
		this(null, 0.0, new ArrayList<>());
	}
	
}
