package pl.edu.agh.ki.covid19tablet.pdfgenetator.containers;

public class Question {

    private int fieldNumber;
    private String title;
    private String answer;
    private boolean isDistinguished;

    public Question(int fieldNumber, String title, String answer, boolean isDistinguished) {
        this.fieldNumber = fieldNumber;
        this.title = title;
        this.answer = answer;
        this.isDistinguished = isDistinguished;
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
    public boolean isDistinguished() {
        return isDistinguished;
    }
}
