package fr.whyt.pubg.deprecated.parser;

import fr.whyt.pubg.main.Main;

public class WriterCIS {
    
    private static boolean verbose;
    static {
        String tmp = Main.properties.get("verbose");
        if(tmp != null) {
            verbose = Boolean.parseBoolean(tmp);
        }
    }

}
