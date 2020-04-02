package pl.edu.agh.ki.covid19tablet.pdfgenetator;

public class Field {

    private String title;
    private FieldType fieldType;

    public Field(String title, FieldType fieldType) {
        this.title = title;
        this.fieldType = fieldType;
    }

    public String getTitle() {
        return title;
    }

    public FieldType getFieldType() {
        return fieldType;
    }
}
