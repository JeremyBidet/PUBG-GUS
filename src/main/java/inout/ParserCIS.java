package fr.whyt.pubg.inout;

import fr.whyt.pubg.inout.cis.*;
import fr.whyt.pubg.main.Main;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParserCIS {

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
    private static Pattern p_Key                           = Pattern.compile("\\((Key=(" + p_keyboard + "+)(,((bCtrl=(" + p_boolean + "))|(bAlt=(" + p_boolean + "))))?)?\\)");
    private static Pattern p_Keys                          = Pattern.compile("Keys=(\\((" + p_Key + ")(,(" + p_Key + ")(,(" + p_Key + "))?)?\\))");
    // ActionKeyList regex
    private static Pattern p_ActionName                    = Pattern.compile("ActionName=\"(" + p_name + "+)\"");
    private static Pattern p_ActionKey                     = Pattern.compile("\\((" + p_ActionName + "," + p_Keys + ")\\)");
    private static Pattern p_ActionKeyList                 = Pattern.compile("ActionKeyList=\\((?<ActionKeyList>" + p_ActionKey + "(," + p_ActionKey + ")*)\\)");
    // AxisKeyList regex
    private static Pattern p_Scale                         = Pattern.compile("Scale=(" + p_float + ")");
    private static Pattern p_AxisName                      = Pattern.compile("AxisName=\"(" + p_name + "+)\"");
    private static Pattern p_AxisKey                       = Pattern.compile("\\((" + p_AxisName + "," + p_Scale + "," + p_Keys + ")\\)");
    private static Pattern p_AxisKeyList                   = Pattern.compile("AxisKeyList=\\((?<AxisKeyList>" + p_AxisKey + "(," + p_AxisKey + ")*)\\)");
    // MouseSensitiveList regex
    private static Pattern p_LastConvertedMouseSensitivity = Pattern.compile("LastConvertedMouseSensitivity=(" + p_float + ")");
    private static Pattern p_MouseSensitiveName            = Pattern.compile("MouseSensitiveName=\"(" + p_name + "+)\"");
    private static Pattern p_MouseSensitive                = Pattern.compile("\\((" + p_MouseSensitiveName + "," + p_LastConvertedMouseSensitivity + ")\\)");
    private static Pattern p_MouseSensitiveList            = Pattern.compile("MouseSensitiveList=\\((?<MouseSensitiveList>" + p_MouseSensitive + "(," + p_MouseSensitive + ")*)\\)");
    // bInvertMouse regex
    private static Pattern p_bInvertMouse                  = Pattern.compile("bInvertMouse=(?<bInvertMouse>" + p_boolean + ")");
    // Global regex (the 'Settins' mistake is volunteer, wrote as it in the config file)
    private static Pattern p_CustomInputSettings = Pattern.compile("^CustomInputSettins=\\((?<CustomInputSettins>" + p_ActionKeyList + "," + p_AxisKeyList + "," + p_MouseSensitiveList + "," + p_bInvertMouse + ")\\)$");
    
    // previous patterns but with Named-Capturing Groups
    private static Pattern p_Key_withNCG                           = Pattern.compile("\\((Key=(?<Key>" + p_keyboard + "+)(,((bCtrl=(?<bCtrl>" + p_boolean + "))|(bAlt=(?<bAlt>" + p_boolean + "))))?)?\\)");
    private static Pattern p_Keys_withNCG                          = Pattern.compile("Keys=(?<Keys>\\((" + p_Key + ")(,(" + p_Key + ")(,(" + p_Key + "))?)?\\))");
    private static Pattern p_ActionName_withNCG                    = Pattern.compile("ActionName=\"(?<ActionName>" + p_name + "+)\"");
    private static Pattern p_ActionKey_withNCG                     = Pattern.compile("\\((?<ActionKey>" + p_ActionName_withNCG + "," + p_Keys_withNCG + ")\\)");
    private static Pattern p_Scale_withNCG                         = Pattern.compile("Scale=(?<Scale>" + p_float + ")");
    private static Pattern p_AxisName_withNCG                      = Pattern.compile("AxisName=\"(?<AxisName>" + p_name + "+)\"");
    private static Pattern p_AxisKey_withNCG                       = Pattern.compile("\\((?<AxisKey>" + p_AxisName_withNCG + "," + p_Scale_withNCG + "," + p_Keys_withNCG + ")\\)");
    private static Pattern p_LastConvertedMouseSensitivity_withNCG = Pattern.compile("LastConvertedMouseSensitivity=(?<LastConvertedMouseSensitivity>" + p_float + ")");
    private static Pattern p_MouseSensitiveName_withNCG            = Pattern.compile("MouseSensitiveName=\"(?<MouseSensitivityName>" + p_name + "+)\"");
    private static Pattern p_MouseSensitive_withNCG                = Pattern.compile("\\((?<MouseSensitive>" + p_MouseSensitiveName_withNCG + "," + p_LastConvertedMouseSensitivity_withNCG + ")\\)");
    

    private static List<ActionKey> processActionKeyList(String source_ActionKeyList) throws ParsingException {
        if(verbose) {
            System.out.println("Retreiving Action Key List...");
        }
        if(!p_ActionKeyList.matcher(source_ActionKeyList).matches()) {
            if(verbose) {
                System.out.println("The Action Key List does not match with the expected format!");
            }
            throw new ParsingException("The Action Key List does not match with the expected format!", source_ActionKeyList, p_ActionKeyList);
        }
        List<ActionKey> actionKeyList = new ArrayList<>();
        Matcher m = p_ActionKey_withNCG.matcher(source_ActionKeyList);
        while(m.find()) {
            String actionName = m.group("ActionName");
            if(verbose) {
                System.out.println("`tExtracting Action Key: " + actionName + "...");
            }
            String result_Keys = m.group("Keys");
            if(!p_Keys.matcher(result_Keys).matches()) {
                if(verbose) {
                    System.out.println("The Keys in the Action Key List does not match with the expected format!");
                }
                throw new ParsingException("The Keys in the Action Key List does not match with the expected format!", result_Keys, p_Keys);
            }
            List<Key> keys = new ArrayList<>();
            Matcher m2 = p_Key_withNCG.matcher(result_Keys);
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

    private static List<AxisKey> processAxisKeyList(String source_AxisKeyList) throws ParsingException {
        if(verbose) {
            System.out.println("Retreiving Axis Key List...");
        }
        if(!p_AxisKeyList.matcher(source_AxisKeyList).matches()) {
            if(verbose) {
                System.out.println("The Axis Key List does not match with the expected format!");
            }
            throw new ParsingException("The Axis Key List does not match with the expected format!", source_AxisKeyList, p_AxisKeyList);
        }
        List<AxisKey> axisKeyList = new ArrayList<>();
        Matcher m = p_AxisKey_withNCG.matcher(source_AxisKeyList);
        while(m.find()) {
            String axisName = m.group("AxisName");
            float scale = Float.parseFloat(m.group("Scale"));
            if(verbose) {
                System.out.println("`tExtracting Axis Key: " + axisName + "...");
            }
            String result_Keys = m.group("Keys");
            if(!p_Keys.matcher(result_Keys).matches()) {
                if(verbose) {
                    System.out.println("The Keys in the Axis Key List does not match with the expected format!");
                }
                throw new ParsingException("The Keys in the Axis Key List does not match with the expected format!", result_Keys, p_Keys);
            }
            List<Key> keys = new ArrayList<>();
            Matcher m2 = p_Key_withNCG.matcher(result_Keys);
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

    private static List<MouseSensitive> processMouseSensitiveList(String source_MouseSensitiveList) throws ParsingException {
        if(verbose) {
            System.out.println("Retreiving Mouse Sensitive List...");
        }
        if(!p_MouseSensitiveList.matcher(source_MouseSensitiveList).matches()) {
            if(verbose) {
                System.out.println("The Mouse Sensitive List does not match with the expected format!");
            }
            throw new ParsingException("The Mouse Sensitive List does not match with the expected format!", source_MouseSensitiveList, p_MouseSensitiveList);
        }
        List<MouseSensitive> mouseSensitiveList = new ArrayList<>();
        Matcher m = p_MouseSensitive_withNCG.matcher(source_MouseSensitiveList);
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

    public static CustomInputSettings serialize(String source) throws ParsingException {
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
                System.out.println("The Custom Input Settings does not match with the expected format!");
                System.out.println("Stopping...");
            }
            throw new ParsingException("The Custom Input Settings does not match with the expected format!", source, p_CustomInputSettings);
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
