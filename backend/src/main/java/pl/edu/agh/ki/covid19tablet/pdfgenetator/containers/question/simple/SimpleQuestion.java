package pl.edu.agh.ki.covid19tablet.pdfgenetator.containers.question.simple;

public class SimpleQuestion {

    private final int fieldNumber;
    private final String title;
    private final String answer;
    private final boolean highlighted;

    public SimpleQuestion(
            int fieldNumber,
            String title,
            String answer,
            boolean highlighted
    ) {
        this.fieldNumber = fieldNumber;
        this.title = title;
        this.answer = answer;
        this.highlighted = highlighted;
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
}
