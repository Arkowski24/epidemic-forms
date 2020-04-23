package pl.edu.agh.ki.covid19tablet.pdfgenetator.containers.question;

import pl.edu.agh.ki.covid19tablet.form.Form;
import pl.edu.agh.ki.covid19tablet.pdfgenetator.containers.question.complex.ComplexQuestion;
import pl.edu.agh.ki.covid19tablet.pdfgenetator.containers.question.complex.ComplexQuestionExtractor;
import pl.edu.agh.ki.covid19tablet.pdfgenetator.containers.question.simple.SimpleQuestion;
import pl.edu.agh.ki.covid19tablet.pdfgenetator.containers.question.simple.SimpleQuestionExtractor;
import pl.edu.agh.ki.covid19tablet.pdfgenetator.containers.question.text.TextQuestion;
import pl.edu.agh.ki.covid19tablet.pdfgenetator.containers.question.text.TextQuestionExtractor;

import java.util.List;

public class QuestionContainer {

    private final List<SimpleQuestion> simpleQuestions;
    private final List<ComplexQuestion> complexQuestions;
    private final List<TextQuestion> textQuestions;

    public QuestionContainer(Form form) {
        SimpleQuestionExtractor simpleExtractor = new SimpleQuestionExtractor();
        this.simpleQuestions = simpleExtractor.extract(form);

        ComplexQuestionExtractor complexExtractor = new ComplexQuestionExtractor();
        this.complexQuestions = complexExtractor.extract(form);

        TextQuestionExtractor textExtractor = new TextQuestionExtractor();
        this.textQuestions = textExtractor.extract(form);
    }

    public List<SimpleQuestion> getSimpleQuestions() {
        return simpleQuestions;
    }
    public List<ComplexQuestion> getComplexQuestions() {
        return complexQuestions;
    }
    public List<TextQuestion> getTextQuestions() {
        return textQuestions;
    }
}
