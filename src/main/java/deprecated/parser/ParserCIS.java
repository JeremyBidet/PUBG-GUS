package fr.whyt.pubg.deprecated.parser;

import fr.whyt.pubg.main.Main;

import java.util.regex.Pattern;

@SuppressWarnings("unused")
public class ParserCIS {

    public static boolean verbose;
    
    static {
        String tmp = Main.properties.get("verbose");
        if(tmp != null) {
            verbose = Boolean.parseBoolean(tmp);
        }
    }
    
    // Primitive type regex
    private static final String r_int       = "-?\\d+";
    private static final String r_float     = "-?\\d+\\.\\d+";
    private static final String r_boolean   = "(False|True)";
    private static final String r_keyboard  = "\\w+";
    private static final String r_name      = "[a-zA-Z0-9_-]+";
    
    // Key regex
    private static final String r_key       = "\\((Key=" + r_keyboard + "(,b(Alt|Ctrl|Shift)=" + r_boolean + ")?)?\\)";
    private static final String r_keyNamed  = "\\((Key=(?<key>" + r_keyboard + ")(,b(?<bKey>Alt|Ctrl|Shift)=" + r_boolean + ")?)?\\)";
    private static final String r_keys      = "Keys=\\((" + r_key + "(," + r_key + "){0,2})?" + "\\)";
    private static final String r_keysNamed = "Keys=\\((?<keys>(" + r_key + "(," + r_key + "){0,2})?" + ")\\)";
    // Key pattern
    public static final Pattern p_key       = Pattern.compile(r_key);
    public static final Pattern p_keyNamed  = Pattern.compile(r_keyNamed);
    public static final Pattern p_keys      = Pattern.compile(r_keys);
    public static final Pattern p_keysNamed = Pattern.compile(r_keysNamed);
    
    // ActionKey regex
    private static final String r_actionName            = "ActionName=\"" + r_name + "\"";
    private static final String r_actionNameNamed       = "ActionName=\"(?<actionName>" + r_name + ")\"";
    private static final String r_actionKey             = "\\(" + r_actionName + "," + r_keys + "\\)";
    private static final String r_actionKeyNamed        = "\\(" + r_actionNameNamed + "," + r_keysNamed + "\\)";
    // ActionKeyList regex
    private static final String r_actionKeyList         = "ActionKeyList=\\((" + r_actionKey + "(," + r_actionKey + ")*" + ")?\\)";
    private static final String r_actionKeyListNamed    = "ActionKeyList=\\((?<actionKeyList>(" + r_actionKey + "(," + r_actionKey + ")*" + ")?)\\)";
    
    // ActionKey pattern
    public static final Pattern p_actionName            = Pattern.compile(r_actionName);
    public static final Pattern p_actionNameNamed       = Pattern.compile(r_actionNameNamed);
    public static final Pattern p_actionKey             = Pattern.compile(r_actionKey);
    public static final Pattern p_actionKeyNamed        = Pattern.compile(r_actionKeyNamed);
    // ActionKeyList pattern
    public static final Pattern p_actionKeyList         = Pattern.compile(r_actionKeyList);
    public static final Pattern p_actionKeyListNamed    = Pattern.compile(r_actionKeyListNamed);
    
    // AxisKey regex
    private static final String r_axisName              = "AxisName=\"" + r_name + "\"";
    private static final String r_axisNameNamed         = "AxisName=\"(?<axisName>" + r_name + ")\"";
    private static final String r_scale                 = "Scale=" + r_float + "";
    private static final String r_scaleNamed            = "Scale=(?<scale>" + r_float + ")";
    private static final String r_axisKey               = "\\(" + r_axisName + "," + r_scale + "," + r_keys + "\\)";
    private static final String r_axisKeyNamed          = "\\(" + r_axisNameNamed + "," + r_scaleNamed + "," + r_keysNamed + "\\)";
    // AxisKeyList regex
    private static final String r_axisKeyList           = "AxisKeyList=\\((" + r_axisKey + "(," + r_axisKey + ")*" + ")?\\)";
    private static final String r_axisKeyListNamed      = "AxisKeyList=\\((?<axisKeyList>(" + r_axisKey + "(," + r_axisKey + ")*" + ")?)\\)";
    
    // AxisKey pattern
    public static final Pattern p_axisName              = Pattern.compile(r_axisName);
    public static final Pattern p_axisNameNamed         = Pattern.compile(r_axisNameNamed);
    public static final Pattern p_scale                 = Pattern.compile(r_scale);
    public static final Pattern p_scaleNamed            = Pattern.compile(r_scaleNamed);
    public static final Pattern p_axisKey               = Pattern.compile(r_axisKey);
    public static final Pattern p_axisKeyNamed          = Pattern.compile(r_axisKeyNamed);
    // AxisKeyList pattern
    public static final Pattern p_axisKeyList           = Pattern.compile(r_axisKeyList);
    public static final Pattern p_axisKeyListNamed      = Pattern.compile(r_axisKeyListNamed);
    
    // Global regex
    // Note: the 'Settins' mistake is volunteer, wrote as it in the config file
    private static final String r_customInputSettings = "^CustomInputSettins=\\(" + p_actionKeyList + "," + p_axisKeyList + "\\)$";
    private static final String r_customInputSettingsNamed = "^CustomInputSettins=\\((?<customInputSettings>" + p_actionKeyList + "," + p_axisKeyList + ")\\)$";
    // Global pattern
    public static final Pattern p_customInputSettings = Pattern.compile(r_customInputSettings);
    public static final Pattern p_customInputSettingsNamed = Pattern.compile(r_customInputSettingsNamed);

}
