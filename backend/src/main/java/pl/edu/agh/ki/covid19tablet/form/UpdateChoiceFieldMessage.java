package pl.edu.agh.ki.covid19tablet.form;

import java.util.Map;

public class UpdateChoiceFieldMessage {

    private int field;
    private Map<String, Boolean> newValues;

    public UpdateChoiceFieldMessage(int field, Map<String, Boolean> newValues) {
        this.field = field;
        this.newValues = newValues;
    }

    public int getField() {
        return field;
    }
    public void setField(int field) {
        this.field = field;
    }

    public Map<String, Boolean> getNewValues() {
        return newValues;
    }
    public void setNewValues(Map<String, Boolean> newValues) {
        this.newValues = newValues;
    }
}
