package pl.edu.agh.ki.covid19tablet.pdfgenetator.containers;

public class Question {

    private int fieldNumber;
    private String title;
    private String answer;

    public Question(int fieldNumber, String title, String answer) {
        this.fieldNumber = fieldNumber;
        this.title = title;
        this.answer = answer;
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
}
