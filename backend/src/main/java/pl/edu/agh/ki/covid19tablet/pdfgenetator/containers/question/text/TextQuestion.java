package pl.edu.agh.ki.covid19tablet.pdfgenetator.containers.question.text;

public class TextQuestion {

    private final int fieldNumber;
    private final String title;
    private final String answer;

    public TextQuestion(
            int fieldNumber,
            String title,
            String answer
    ) {
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
