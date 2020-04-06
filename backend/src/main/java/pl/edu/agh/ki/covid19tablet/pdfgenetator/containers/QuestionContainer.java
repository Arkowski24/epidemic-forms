package pl.edu.agh.ki.covid19tablet.pdfgenetator.containers;

import pl.edu.agh.ki.covid19tablet.form.Form;
import pl.edu.agh.ki.covid19tablet.schema.fields.ChoiceField;
import pl.edu.agh.ki.covid19tablet.schema.fields.SliderField;
import pl.edu.agh.ki.covid19tablet.schema.fields.TextField;
import pl.edu.agh.ki.covid19tablet.state.fields.ChoiceFieldState;
import pl.edu.agh.ki.covid19tablet.state.fields.SliderFieldState;
import pl.edu.agh.ki.covid19tablet.state.fields.TextFieldState;

import java.util.ArrayList;
import java.util.List;

public class QuestionContainer {

    private int maxFieldNumber;
    private List<Question> questions;

    public QuestionContainer(Form form) {
        this.maxFieldNumber = 0;
        this.questions = extractQuestions(form);
    }

    public int getMaxFieldNumber() {
        return maxFieldNumber;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    private List<Question> extractQuestions(Form form) {
        List<Question> extractedQuestions = new ArrayList<>();

        extractedQuestions.addAll(extractChoiceQuestions(form));
        extractedQuestions.addAll(extractSliderQuestions(form));
        extractedQuestions.addAll(extractTextQuestions(form));

        return extractedQuestions;
    }

    private List<Question> extractSliderQuestions(Form form) {
        List<Question> extractedQuestions = new ArrayList<>();

        List<SliderFieldState> sliderFieldStates = form.getState().getSlider();
        for (SliderFieldState sliderFieldState : sliderFieldStates) {
            SliderField sliderField = sliderFieldState.getField();

            int fieldNumber = sliderField.getFieldNumber();
            String title = sliderField.getTitle();
            String answer = Double.toString(sliderFieldState.getValue());
            if (sliderFieldState.getValue() < sliderField.getMinValue())
                answer = "B.D.";
            extractedQuestions.add(new Question(fieldNumber, title, answer));

            if (fieldNumber > this.maxFieldNumber) {
                this.maxFieldNumber = fieldNumber;
            }
        }

        return extractedQuestions;
    }

    private List<Question> extractTextQuestions(Form form) {
        List<Question> extractedQuestions = new ArrayList<>();

        List<TextFieldState> textFieldStates = form.getState().getText();
        for (TextFieldState textFieldState : textFieldStates) {
            TextField textField = textFieldState.getField();

            if (!(textField.getTitle().startsWith("Imi") || textField.getTitle().startsWith("Nazwis"))) {
                int fieldNumber = textField.getFieldNumber();
                String title = textField.getTitle();
                String answer = textFieldState.getValue();
                extractedQuestions.add(new Question(fieldNumber, title, answer));

                if (fieldNumber > this.maxFieldNumber) {
                    this.maxFieldNumber = fieldNumber;
                }
            }
        }

        return extractedQuestions;
    }

    private List<Question> extractChoiceQuestions(Form form) {
        List<Question> extractedQuestions = new ArrayList<>();

        List<ChoiceFieldState> choiceFieldStates = form.getState().getChoice();
        for (ChoiceFieldState choiceFieldState : choiceFieldStates) {
            ChoiceField choiceField = choiceFieldState.getField();

            int fieldNumber = choiceField.getFieldNumber();
            String title = choiceField.getTitle();
            String answer = "";

            List<Boolean> fieldAnswers = choiceFieldState.getValue();
            for (int j = 0; j < fieldAnswers.size(); j++) {
                if (fieldAnswers.get(j)) {
                    answer = choiceFieldState.getField().getChoices().get(j);
                    break;
                }
            }

            extractedQuestions.add(new Question(fieldNumber, title, answer));
            if (fieldNumber > this.maxFieldNumber) {
                this.maxFieldNumber = fieldNumber;
            }
        }

        return extractedQuestions;
    }
}
