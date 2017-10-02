package fr.whyt.pubg.inout;

import fr.whyt.pubg.inout.cis.*;
import fr.whyt.pubg.main.Main;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {

    private static boolean verbose;
    static {
        String tmp = Main.properties.get("verbose");
        if(tmp != null) {
            verbose = Boolean.parseBoolean(tmp);
        }
    }

    // Primitive type regex
    private static Pattern p_float                         = Pattern.compile("-?\\d+(\\.\\d+)?");
    private static Pattern p_boolean                       = Pattern.compile("(False|True)");
    private static Pattern p_keyboard                      = Pattern.compile("[a-zA-Z0-9_<]");
    private static Pattern p_name                          = Pattern.compile("[a-zA-Z0-9_<\\-]");
    // Common regex
    private static Pattern p_Key                           = Pattern.compile("\\((Key=(?<Key>" + p_keyboard + "+)(,((bCtrl=(?<bCtrl>" + p_boolean + "))|(bAlt=(?<bAlt>" + p_boolean + "))))?)?\\)");
    private static Pattern p_Keys                          = Pattern.compile("Keys=(?<Keys>\\((?<Key1>" + p_Key + ")(,(?<Key2>" + p_Key + ")(,(?<Key3>" + p_Key + "))?)?\\))");
    // ActionKeyList regex
    private static Pattern p_ActionName                    = Pattern.compile("ActionName=\"(?<ActionName>" + p_name + "+)\"");
    private static Pattern p_ActionKey                     = Pattern.compile("\\((?<ActionKey>" + p_ActionName + "," + p_Keys + ")\\)");
    private static Pattern p_ActionKeyList                 = Pattern.compile("ActionKeyList=\\((?<ActionKeyList>" + p_ActionKey + "(," + p_ActionKey + ")*)\\)");
    // AxisKeyList regex
    private static Pattern p_Scale                         = Pattern.compile("Scale=(?<Scale>" + p_float + ")");
    private static Pattern p_AxisName                      = Pattern.compile("AxisName=\"(?<AxisName>" + p_name + "+)\"");
    private static Pattern p_AxisKey                       = Pattern.compile("\\((?<AxisKey>" + p_AxisName + "," + p_Scale + "," + p_Keys + ")\\)");
    private static Pattern p_AxisKeyList                   = Pattern.compile("AxisKeyList=\\((?<AxisKeyList>" + p_AxisKey + "(," + p_AxisKey + ")*)\\)");
    // MouseSensitiveList regex
    private static Pattern p_LastConvertedMouseSensitivity = Pattern.compile("LastConvertedMouseSensitivity=(?<LastConvertedMouseSensitivity>" + p_float + ")");
    private static Pattern p_MouseSensitiveName            = Pattern.compile("MouseSensitiveName=\"(?<MouseSensitivityName>" + p_name + "+)\"");
    private static Pattern p_MouseSensitive                = Pattern.compile("\\((?<MouseSensitiveList>" + p_MouseSensitiveName + "," + p_LastConvertedMouseSensitivity + ")\\)");
    private static Pattern p_MouseSensitiveList            = Pattern.compile("MouseSensitiveList=\\((?<MouseSensitiveList>" + p_MouseSensitive + "(," + p_MouseSensitive + ")*)\\)");
    // bInvertMouse regex
    private static Pattern p_bInvertMouse                  = Pattern.compile("bInvertMouse=(?<bInvertMouse>" + p_boolean + ")");
    // Global regex (the 'Settins' mistake is volunteer, wrote as it in the config file)
    private static Pattern p_CustomInputSettings = Pattern.compile("^CustomInputSettins=\\((?<CustomInputSettins>" + p_ActionKeyList + "," + p_AxisKeyList + "," + p_MouseSensitiveList + "," + p_bInvertMouse + ")\\)$");


    private static List<ActionKey> processActionKeyList(String source_ActionKeyList) {
        if(verbose) {
            System.out.println("Retreiving Action Key List...");
        }
        List<ActionKey> actionKeyList = new ArrayList<>();
        Matcher m = p_ActionKey.matcher(source_ActionKeyList);
        while(m.find()) {
            String actionName = m.group("ActionName");
            if(verbose) {
                System.out.println("`tExtracting Action Key: " + actionName + "...");
            }
            String result_Keys = m.group("Keys");
            List<Key> keys = new ArrayList<>();
            Matcher m2 = p_Key.matcher(result_Keys);
            while(m2.find()) {
                String key = m2.group("Key");
                if(verbose) {
                    System.out.println("`t`tFound Key: " + key + "!");
                }
                keys.add(new Key(key, Boolean.parseBoolean(m2.group("bCtrl")), Boolean.parseBoolean(m2.group("bAlt"))));
            }
            actionKeyList.add(new ActionKey(actionName, keys));
        }
        if(verbose) {
            System.out.println("Found " + actionKeyList.size() + " Action Keys!");
        }
        return actionKeyList;
    }

    private static List<AxisKey> processAxisKeyList(String source_AxisKeyList) {
        if(verbose) {
            System.out.println("Retreiving Axis Key List...");
        }
        List<AxisKey> axisKeyList = new ArrayList<>();
        Matcher m = p_AxisKey.matcher(source_AxisKeyList);
        while(m.find()) {
            String axisName = m.group("AxisName");
            float scale = Float.parseFloat(m.group("Scale"));
            if(verbose) {
                System.out.println("`tExtracting Axis Key: " + axisName + "...");
            }
            String result_Keys = m.group("Keys");
            List<Key> keys = new ArrayList<>();
            Matcher m2 = p_Key.matcher(result_Keys);
            while(m2.find()) {
                String key = m2.group("Key");
                if(verbose) {
                    System.out.println("`t`tFound Key: " + key + "!");
                }
                keys.add(new Key(key, Boolean.parseBoolean(m2.group("bCtrl")), Boolean.parseBoolean(m2.group("bAlt"))));
            }
            axisKeyList.add(new AxisKey(axisName, scale, keys));
        }
        if(verbose) {
            System.out.println("Found " + axisKeyList.size() + " Axis Keys!");
        }
        return axisKeyList;
    }

    private static List<MouseSensitive> processMouseSensitiveList(String source_MouseSensitiveList) {
        if(verbose) {
            System.out.println("Retreiving Mouse Sensitive List...");
        }
        List<MouseSensitive> mouseSensitiveList = new ArrayList<>();
        Matcher m = p_MouseSensitive.matcher(source_MouseSensitiveList);
        while(m.find()){
            String mouseSensitiveName = m.group("MouseSensitivityName");
            if(verbose) {
                System.out.println("`tExtracting Mouse Sensitive: " + mouseSensitiveName + "...");
            }
            float lastConvertedMouseSensitivity = Float.parseFloat(m.group("LastConvertedMouseSensitivity"));
            mouseSensitiveList.add( new MouseSensitive(mouseSensitiveName, lastConvertedMouseSensitivity));
        }
        if(verbose) {
            System.out.println("Found " + mouseSensitiveList.size() + " Mouse Sensitive!");
        }
        return mouseSensitiveList;
    }

    public static CustomInputSettings processCustomInputSettings(String source) {
        if(verbose) {
            System.out.println("Using pattern:");
            System.out.println(p_CustomInputSettings);
            System.out.println();
            System.out.println("Using source:");
            System.out.println(source);
            System.out.println();
        }
        if(verbose) {
            System.out.println("Processing...");
        }
        // Stop application if source does not match the global regex
        Matcher m = p_CustomInputSettings.matcher(source);
        if(!m.matches()) {
            if(verbose) {
                System.out.println("Stopping...");
            }
            return null;
        }

        if(verbose) {
            System.out.println("Extracting matches results from source...");
        }
        String source_ActionKeyList      = m.group("ActionKeyList");
        String source_AxisKeyList        = m.group("AxisKeyList");
        String source_MouseSensitiveList = m.group("MouseSensitiveList");
        boolean bInvertMouse             = Boolean.parseBoolean(m.group("bInvertMouse"));

        List<ActionKey>      actionKeyList      = processActionKeyList      (source_ActionKeyList);
        List<AxisKey>        axisKeyList        = processAxisKeyList        (source_AxisKeyList);
        List<MouseSensitive> mouseSensitiveList = processMouseSensitiveList (source_MouseSensitiveList);

        CustomInputSettings cis = new CustomInputSettings(actionKeyList, axisKeyList, mouseSensitiveList, bInvertMouse);
        if(verbose) {
            System.out.println("Succesfully terminated!");
        }
        return cis;
    }

}
