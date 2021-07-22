package fr.whyt.pubg.deprecated.parser.data;

public class MouseSensitive {

    private String mouseSensitiveName;
    private float lastConvertedMouseSensitivity;

    public MouseSensitive(String mouseSensitiveName, float lastConvertedMouseSensitivity) {
        this.mouseSensitiveName = mouseSensitiveName;
        this.lastConvertedMouseSensitivity = lastConvertedMouseSensitivity;
    }

    public String getMouseSensitiveName() {
        return mouseSensitiveName;
    }

    public void setMouseSensitiveName(String mouseSensitiveName) {
        this.mouseSensitiveName = mouseSensitiveName;
    }

    public float getLastConvertedMouseSensitivity() {
        return lastConvertedMouseSensitivity;
    }

    public void setLastConvertedMouseSensitivity(float lastConvertedMouseSensitivity) {
        this.lastConvertedMouseSensitivity = lastConvertedMouseSensitivity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MouseSensitive)) return false;

        MouseSensitive that = (MouseSensitive) o;

        return mouseSensitiveName.equals(that.mouseSensitiveName);
    }

    @Override
    public int hashCode() {
        int result = mouseSensitiveName.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "MouseSensitive{" + "mouseSensitiveName='" + mouseSensitiveName + '\'' + ", lastConvertedMouseSensitivity=" + lastConvertedMouseSensitivity + '}';
    }

}
