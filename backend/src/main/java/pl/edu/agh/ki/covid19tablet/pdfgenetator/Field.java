package pl.edu.agh.ki.covid19tablet.pdfgenetator;

public class Field {

    private String description;
    private FieldType fieldType;

    public Field(String description, FieldType fieldType) {
        this.description = description;
        this.fieldType = fieldType;
    }

    public String getDescription() {
        return description;
    }

    public FieldType getFieldType() {
        return fieldType;
    }
}
