package pl.edu.agh.ki.covid19tablet.pdfgenetator.containers;

public class Question {

    private int fieldNumber;
    private String title;
    private String answer;
    private boolean highlighted;
    private boolean inTable;

    public Question(int fieldNumber, String title, String answer, boolean highlighted, boolean inTable) {
        this.fieldNumber = fieldNumber;
        this.title = title;
        this.answer = answer;
        this.highlighted = highlighted;
        this.inTable = inTable;
    }

    public int getFieldNumber() {
        return fieldNumber;
    }
    public String getTitle() {
        return title;
    }
    public String getAnswer() {
        return answer;
    }
    public boolean isHighlighted() {
        return highlighted;
    }
    public boolean isInTable() {
        return inTable;
    }
}
