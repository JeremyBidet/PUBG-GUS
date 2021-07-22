package fr.whyt.pubg.data.iniimpl;

import fr.whyt.pubg.inifile.annotations.Property;
import fr.whyt.pubg.utils.ClassHelper;
import fr.whyt.pubg.utils.annotations.Identity;

public class Key extends ClassHelper {
	
	@Identity
	@Property(name = "Key")
	public String key;
	
	@Identity
	@Property(name = "bCtrl", optional = true)
	public boolean bCtrl;
	
	@Identity
	@Property(name = "bAlt", optional = true)
	public boolean bAlt;
	
	@Identity
	@Property(name = "bShift", optional = true)
	public boolean bShift;
	
	public Key(final String key, final boolean bCtrl, final boolean bAlt, final boolean bShift) {
		this.key = key;
		this.bCtrl = bCtrl;
		this.bAlt = bAlt;
		this.bShift = bShift;
	}
	
	public Key(final String key) {
		this(key, false, false, false);
	}
	
	public Key() {
		this(null, false, false, false);
	}
	
	public static boolean isCtrl(final String bKey) {
		return "Ctrl".equals(bKey);
	}
	
	public static boolean isAlt(final String bKey) {
		return "Alt".equals(bKey);
	}
	
	public static boolean isShift(final String bKey) {
		return "Shift".equals(bKey);
	}
	
	public static String getBKey(final Key key) {
		if (key.bCtrl) {
			return "bCtrl";
		}
		if (key.bAlt) {
			return "bAlt";
		}
		if (key.bShift) {
			return "bShift";
		}
		return "";
	}
	
}
