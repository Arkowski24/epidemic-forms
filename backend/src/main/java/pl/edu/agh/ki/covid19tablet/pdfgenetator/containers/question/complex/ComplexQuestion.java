package pl.edu.agh.ki.covid19tablet.pdfgenetator.containers.question.complex;

import java.util.List;

public class ComplexQuestion {

    private int fieldNumber;
    private String title;
    private String answer;
    private List<SubQuestion> subQuestions;

    public ComplexQuestion(
            int fieldNumber,
            String title,
            String answer,
            List<SubQuestion> subQuestions
    ) {
        this.fieldNumber = fieldNumber;
        this.title = title;
        this.answer = answer;
        this.subQuestions = subQuestions;
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
    public List<SubQuestion> getSubQuestions() {
        return subQuestions;
    }
}
