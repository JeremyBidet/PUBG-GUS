package fr.whyt.pubg.deprecated.parser.data;

import fr.whyt.pubg.deprecated.parser.IniFile;
import fr.whyt.pubg.deprecated.parser.ParserCIS;
import fr.whyt.pubg.deprecated.parser.exceptions.ParsingException;
import fr.whyt.pubg.deprecated.parser.exceptions.WritingException;
import fr.whyt.pubg.utils.ObjectHelper;

import java.util.Objects;
import java.util.regex.Matcher;

public class Key implements IniFile<Key> {
    
    public final String key;
    public final boolean bCtrl;
    public final boolean bAlt;
    public final boolean bShift;

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
    
    @Override
    public int hashCode() {
        int result = key.hashCode();
        result = 31 * result + (bCtrl ? 1 : 0);
        result = 31 * result + (bAlt ? 1 : 0);
        result = 31 * result + (bShift ? 1 : 0);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Key)) {
            return false;
        }

        final Key key = (Key) o;
        
        return Objects.equals(this.key, key.key)
                && bCtrl == key.bCtrl
                && bAlt == key.bAlt
                && bShift == key.bShift;
    }

    @Override
    public String toString() {
        return "Key { key=" + key + ", bCtrl=" + bCtrl + ", bAlt=" + bAlt + ", bShift=" + bShift + " }";
    }
    
    @Override
    public Key deserialize(final String source) throws ParsingException {
        final Matcher m_key = ParserCIS.p_keyNamed.matcher(source);
        if (!m_key.matches()) {
            throw new ParsingException("The Key does not match with the expected format!", source, ParserCIS.p_keyNamed);
        }
        final String key = m_key.group("key");
        final String bKey = m_key.group("bKey");
        
        return new Key(key, isCtrl(bKey), isAlt(bKey), isShift(bKey));
    }
    
    @SuppressWarnings("RedundantThrows")
    @Override
    public String serialize() throws WritingException {
        return "("
                   + ObjectHelper.getOrDefault(key, "", k -> "Key=" + k)
                   + (bAlt || bCtrl || bShift ? "," + Key.getBKey(this) + "=True" : "")
               + ")";
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
        if(key.bCtrl) {
            return "bCtrl";
        }
        if(key.bAlt) {
            return "bAlt";
        }
        if(key.bShift) {
            return "bShift";
        }
        return "";
    }
    
}
