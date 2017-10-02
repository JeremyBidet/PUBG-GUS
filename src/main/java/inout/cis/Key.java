package fr.whyt.pubg.inout.cis;

public class Key {

    private String key;
    private boolean bCtrl;
    private boolean bAlt;

    public Key(String key, boolean bCtrl, boolean bAlt) {
        this.key = key;
        this.bCtrl = bCtrl;
        this.bAlt = bAlt;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public boolean isbCtrl() {
        return bCtrl;
    }

    public void setbCtrl(boolean bCtrl) {
        this.bCtrl = bCtrl;
    }

    public boolean isbAlt() {
        return bAlt;
    }

    public void setbAlt(boolean bAlt) {
        this.bAlt = bAlt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Key)) return false;

        Key key1 = (Key) o;

        if (bCtrl != key1.bCtrl) return false;
        if (bAlt != key1.bAlt) return false;
        return key.equals(key1.key);
    }

    @Override
    public int hashCode() {
        int result = key.hashCode();
        result = 31 * result + (bCtrl ? 1 : 0);
        result = 31 * result + (bAlt ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Key{" + "key='" + key + '\'' + ", bCtrl=" + bCtrl + ", bAlt=" + bAlt + '}';
    }

}
