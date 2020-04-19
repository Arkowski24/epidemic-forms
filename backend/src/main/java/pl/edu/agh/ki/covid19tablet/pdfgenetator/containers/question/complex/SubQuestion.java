package pl.edu.agh.ki.covid19tablet.pdfgenetator.containers.question.complex;

public class SubQuestion {

    private final String title;
    private final String answer;
    private final boolean highlighted;

    public SubQuestion(
            String title,
            String answer,
            boolean highlighted
    ) {
        this.title = title;
        this.answer = answer;
        this.highlighted = highlighted;
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
