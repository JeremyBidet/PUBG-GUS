package fr.whyt.pubg.inout;

import fr.whyt.pubg.inout.cis.CustomInputSettings;
import fr.whyt.pubg.inout.cis.Key;
import fr.whyt.pubg.main.Main;

import java.util.Objects;

public class WriterCIS {
    
    private static boolean verbose;
    static {
        String tmp = Main.properties.get("verbose");
        if(tmp != null) {
            verbose = Boolean.parseBoolean(tmp);
        }
    }
    
    public static String deserialize(CustomInputSettings cis) throws WritingException {
        if(verbose) {
            System.out.println("Deserializing Custom Input Settings:");
            System.out.println(cis);
            System.out.println();
        }
        try {
            Objects.requireNonNull(cis);
        } catch(NullPointerException e) {
            throw new WritingException("This Custom Input Settings is not correctly populated!", cis);
        }
        StringBuilder sb = new StringBuilder();
        sb.append("CustomInputSettins=(");
            sb.append("ActionKeyList=(");
            sb.append(cis.getActionKeyList().stream().map(ak -> {
                StringBuilder tmp = new StringBuilder();
                tmp.append("(");
                    tmp.append("ActionName=\""); tmp.append(ak.getActionName()); tmp.append("\""); tmp.append(",");
                    tmp.append("Keys=(");
                        // process Key 1
                        Key k1 = ak.getKeys().get(0);
                        tmp.append("(");
                            if (!k1.getKey().equals("")) { tmp.append("Key="); tmp.append(k1.getKey()); tmp.append(k1.isbCtrl() ? ",bCtrl=True" : ""); tmp.append(k1.isbAlt() ? ",bAlt=True" : "");
                        tmp.append(")");
                        // process Key 2 only if Key 1 not null
                        Key k2 = ak.getKeys().get(1);
                        tmp.append(",(");
                            if (!k2.getKey().equals("")) { tmp.append("Key="); tmp.append(k2.getKey()); tmp.append(k2.isbCtrl() ? ",bCtrl=True" : ""); tmp.append(k2.isbAlt() ? ",bAlt=True" : ""); }
                        tmp.append(")");
                        // process Key 3 only if Key 3 not null
                        Key k3 = ak.getKeys().get(2);
                            if (!k3.getKey().equals("")) { tmp.append(",(Key="); tmp.append(k3.getKey()); tmp.append(k3.isbCtrl() ? ",bCtrl=True" : ""); tmp.append(k3.isbAlt() ? ",bAlt=True" : ""); tmp.append(")"); }
                        } else {
                        tmp.append(")");
                        }
                    tmp.append(")");
                tmp.append(")");
                return tmp.toString();
            }).reduce(",", String::concat));
            sb.append("),");

            sb.append("AxisKeyList=(");
            sb.append(cis.getAxisKeyList().stream().map(ak -> {
                StringBuilder tmp = new StringBuilder();
                tmp.append("(");
                    tmp.append("AxisName=\""); tmp.append(ak.getAxisName()); tmp.append("\""); tmp.append(",");
                    tmp.append("Scale="); tmp.append(ak.getScale()); tmp.append(",");
                    tmp.append("Keys=(");
                        // process Key 1
                        Key k1 = ak.getKeys().get(0);
                        tmp.append("(");
                            if(!k1.getKey().equals("")) { tmp.append("Key="); tmp.append(k1.getKey()); tmp.append(k1.isbCtrl() ? ",bCtrl=True" : ""); tmp.append(k1.isbAlt() ? ",bAlt=True" : "");
                        tmp.append(")");
                        // process Key 2 only if Key 1 not null
                        Key k2 = ak.getKeys().get(1);
                        tmp.append(",(");
                            if(!k2.getKey().equals("")) { tmp.append("Key="); tmp.append(k2.getKey()); tmp.append(k2.isbCtrl() ? ",bCtrl=True" : ""); tmp.append(k2.isbAlt() ? ",bAlt=True" : ""); }
                        tmp.append(")");
                        // process Key 3 only if Key 3 not null
                        Key k3 = ak.getKeys().get(2);
                            if(!k3.getKey().equals("")) { tmp.append(",(Key="); tmp.append(k3.getKey()); tmp.append(k3.isbCtrl() ? ",bCtrl=True" : ""); tmp.append(k3.isbAlt() ? ",bAlt=True" : ""); tmp.append(")"); }
                        } else {
                        tmp.append(")");
                        }
                        tmp.append(")");
                tmp.append(")");
                return tmp.toString();
            }).reduce(",", String::concat));
            sb.append("),");

            sb.append("MouseSensitiveList=(");
            sb.append(cis.getMouseSensitiveList().stream().map(ms -> {
                StringBuilder tmp = new StringBuilder();
                tmp.append("(");
                    tmp.append("MouseSensitiveName=\""); tmp.append(ms.getMouseSensitiveName()); tmp.append("\""); tmp.append(",");
                    tmp.append("LastConvertedMouseSensitivity="); tmp.append(ms.getLastConvertedMouseSensitivity());
                tmp.append(")");
                return tmp.toString();
            }).reduce(",", String::concat));
            sb.append("),");
            sb.append("bInvertMouse="); sb.append(cis.isbInvertMouse());
        sb.append(")");
    
        if(verbose) {
            System.out.println("Succesfully deserialized Custom Input Settings:");
            System.out.println(sb.toString());
            System.out.println();
        }
        
        return sb.toString();
    }

}
