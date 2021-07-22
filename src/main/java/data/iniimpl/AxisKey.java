package fr.whyt.pubg.data.iniimpl;

import fr.whyt.pubg.inifile.annotations.Property;
import fr.whyt.pubg.utils.ClassHelper;
import fr.whyt.pubg.utils.annotations.Identity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AxisKey extends ClassHelper {
	
	@Identity
	@Property(name = "AxisName")
	public String axisName;
	
	@Property(name = "Scale")
	public double scale;
	
	@Property(name = "Keys")
	public final List<Key> keyList;
	
	public AxisKey(final String axisName, final double scale, final List<Key> keyList) {
		this.axisName = axisName;
		this.scale = scale;
		this.keyList = Objects.requireNonNull(keyList);
	}
	
	public AxisKey(final String axisName, final double scale) {
		this(axisName, scale, new ArrayList<>());
	}
	
	public AxisKey() {
		this(null, 0.0, new ArrayList<>());
	}
	
}
