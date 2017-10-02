package fr.whyt.pubg.inout.cis;

import java.util.List;

public class AxisKey {

    private String axisName;
    private float scale;
    private List<Key> keys;

    public AxisKey(String axisName, float scale, List<Key> keys) {
        this.axisName = axisName;
        this.scale = scale;
        this.keys = keys;
    }

    public String getAxisName() {
        return axisName;
    }

    public void setAxisName(String axisName) {
        this.axisName = axisName;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public List<Key> getKeys() {
        return keys;
    }

    public void setKeys(List<Key> keys) {
        this.keys = keys;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AxisKey)) return false;

        AxisKey axisKey = (AxisKey) o;

        return axisName.equals(axisKey.axisName);
    }

    @Override
    public int hashCode() {
        int result = axisName.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "AxisKey{" + "scale='" + scale + '\'' + ", axisName='" + axisName + '\'' + ", keys=" + keys + '}';
    }

}
