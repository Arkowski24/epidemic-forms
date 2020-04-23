package pl.edu.agh.ki.covid19tablet.pdfgenetator.containers.question.text;

import pl.edu.agh.ki.covid19tablet.form.Form;
import pl.edu.agh.ki.covid19tablet.schema.fields.TextField;
import pl.edu.agh.ki.covid19tablet.state.fields.TextFieldState;

import java.util.ArrayList;
import java.util.List;

public class TextQuestionExtractor {

    public List<TextQuestion> extract(Form form) {
        return extractTextQuestions(form);
    }

    private List<TextQuestion> extractTextQuestions(Form form) {
        List<TextQuestion> extractedQuestions = new ArrayList<>();

        List<TextFieldState> textFieldStates = form.getState().getText();
        for (TextFieldState textFieldState : textFieldStates) {
            TextField textField = textFieldState.getField();

            if (!(
                    textField.getTitle().startsWith("Imi") ||
                            textField.getTitle().startsWith("Nazwis") ||
                            textField.getTitle().startsWith("Telefon")
            )) {
                int fieldNumber = textField.getFieldNumber();
                String title = textField.getTitle();
                String answer = textFieldState.getValue();
                extractedQuestions.add(new TextQuestion(fieldNumber, title, answer));
            }
        }

        return extractedQuestions;
    }
}
