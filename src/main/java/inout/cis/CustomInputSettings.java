package fr.whyt.pubg.inout.cis;

import java.util.List;

public class CustomInputSettings {

    private List<ActionKey> actionKeyList;
    private List<AxisKey> axisKeyList;
    private List<MouseSensitive> mouseSensitiveList;
    private boolean bInvertMouse;

    public CustomInputSettings(List<ActionKey> actionKeyList, List<AxisKey> axisKeyList, List<MouseSensitive> mouseSensitiveList, boolean bInvertMouse) {
        this.actionKeyList = actionKeyList;
        this.axisKeyList = axisKeyList;
        this.mouseSensitiveList = mouseSensitiveList;
        this.bInvertMouse = bInvertMouse;
    }

    public List<ActionKey> getActionKeyList() {
        return actionKeyList;
    }

    public void setActionKeyList(List<ActionKey> actionKeyList) {
        this.actionKeyList = actionKeyList;
    }

    public List<AxisKey> getAxisKeyList() {
        return axisKeyList;
    }

    public void setAxisKeyList(List<AxisKey> axisKeyList) {
        this.axisKeyList = axisKeyList;
    }

    public List<MouseSensitive> getMouseSensitiveList() {
        return mouseSensitiveList;
    }

    public void setMouseSensitiveList(List<MouseSensitive> mouseSensitiveList) {
        this.mouseSensitiveList = mouseSensitiveList;
    }

    public boolean isbInvertMouse() {
        return bInvertMouse;
    }

    public void setbInvertMouse(boolean bInvertMouse) {
        this.bInvertMouse = bInvertMouse;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CustomInputSettings)) return false;

        CustomInputSettings that = (CustomInputSettings) o;

        if (bInvertMouse != that.bInvertMouse) return false;
        if (!actionKeyList.equals(that.actionKeyList)) return false;
        if (!axisKeyList.equals(that.axisKeyList)) return false;
        return mouseSensitiveList.equals(that.mouseSensitiveList);
    }

    @Override
    public int hashCode() {
        int result = actionKeyList.hashCode();
        result = 31 * result + axisKeyList.hashCode();
        result = 31 * result + mouseSensitiveList.hashCode();
        result = 31 * result + (bInvertMouse ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CustomInputSettings{" + "actionKeyList=" + actionKeyList + ", axisKeyList=" + axisKeyList + ", mouseSensitiveList=" + mouseSensitiveList + ", bInvertMouse=" + bInvertMouse + '}';
    }

}
