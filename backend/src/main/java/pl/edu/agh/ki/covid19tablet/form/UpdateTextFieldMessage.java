package pl.edu.agh.ki.covid19tablet.form;

public class UpdateTextFieldMessage {

    private int field;
    private String newValue;

    public UpdateTextFieldMessage(int field, String newValue) {
        this.field = field;
        this.newValue = newValue;
    }

    public int getField() {
        return field;
    }
    public void setField(int field) {
        this.field = field;
    }

    public String getNewValue() {
        return newValue;
    }
    public void setNewValue(String newValue) {
        this.newValue = newValue;
    }
}
