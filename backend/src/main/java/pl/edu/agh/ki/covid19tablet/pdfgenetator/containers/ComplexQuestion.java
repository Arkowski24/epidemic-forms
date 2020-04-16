package pl.edu.agh.ki.covid19tablet.pdfgenetator.containers;

import java.util.List;

public class ComplexQuestion {

    private int fieldNumber;
    private String title;
    private String answer;
    private List<String> subtitles;
    private List<String> subanwsers;
    private boolean complex;

    public ComplexQuestion(
            int fieldNumber,
            String title,
            String answer,
            List<String> subtitles,
            List<String> subanwsers,
            boolean complex
    ) {
        this.fieldNumber = fieldNumber;
        this.title = title;
        this.answer = answer;
        this.subtitles = subtitles;
        this.subanwsers = subanwsers;
        this.complex = complex;
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
    public List<String> getSubtitles() {
        return subtitles;
    }
    public List<String> getSubanwsers() {
        return subanwsers;
    }
    public boolean isComplex() {
        return complex;
    }
}
