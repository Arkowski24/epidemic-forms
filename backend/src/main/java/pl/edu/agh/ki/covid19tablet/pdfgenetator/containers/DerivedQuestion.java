package pl.edu.agh.ki.covid19tablet.pdfgenetator.containers;

public class DerivedQuestion {

    private int fieldNumber;
    private String title;
    private String answer;
    private String subtitle;
    private String subanwser;
    private boolean highlighted;

    public DerivedQuestion(
            int fieldNumber,
            String title,
            String answer,
            String subtitle,
            String subanwser,
            boolean highlighted
    ) {
        this.fieldNumber = fieldNumber;
        this.title = title;
        this.answer = answer;
        this.subtitle = subtitle;
        this.subanwser = subanwser;
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
    public String getSubtitle() {
        return subtitle;
    }
    public String getSubanwser() {
        return subanwser;
    }
    public boolean isHighlighted() {
        return highlighted;
    }
}
