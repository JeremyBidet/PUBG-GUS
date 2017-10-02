package fr.whyt.pubg.inout.cis;

import java.util.List;

public class ActionKey {

    private String actionName;
    private List<Key> keys;

    public ActionKey(String actionName, List<Key> keys) {
        this.actionName = actionName;
        this.keys = keys;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
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
        if (!(o instanceof ActionKey)) return false;

        ActionKey actionKey = (ActionKey) o;

        return actionName.equals(actionKey.actionName);
    }

    @Override
    public int hashCode() {
        int result = actionName.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "ActionKey{" + "actionName='" + actionName + '\'' + ", keys=" + keys + '}';
    }

}
