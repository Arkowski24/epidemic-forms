package pl.edu.agh.ki.covid19tablet.pdfgenetator.containers.question.complex;

public class SubQuestion {

    private final String title;
    private final String answer;
    private final boolean highlighted;
    private final boolean withExclamationMark;

    public SubQuestion(
            String title,
            String answer,
            boolean highlighted,
            boolean withExclamationMark
    ) {
        this.title = title;
        this.answer = answer;
        this.highlighted = highlighted;
        this.withExclamationMark = withExclamationMark;
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
    public boolean isWithExclamationMark() {
        return withExclamationMark;
    }
}
