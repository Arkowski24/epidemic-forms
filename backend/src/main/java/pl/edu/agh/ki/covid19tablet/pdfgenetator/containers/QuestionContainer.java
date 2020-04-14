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

    private List<Question> questions;

    public QuestionContainer(Form form) {
        this.questions = extractQuestions(form);
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
            double value = sliderFieldState.getValue();
            String answer = Double.toString(value);

            answer = addUnits(title, answer);

            boolean isHighlighted = isOutOfNorm(title, value);

            if (sliderFieldState.getValue() < sliderField.getMinValue()) {
                answer = "B.D.";
                isHighlighted = false;
            }

            extractedQuestions.add(new Question(fieldNumber, title, answer, isHighlighted, true));
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
                extractedQuestions.add(new Question(fieldNumber, title, answer, false, false));
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
            StringBuilder answer = new StringBuilder();

            boolean isSingleChoice = !choiceField.getMultiChoice();
            List<Boolean> fieldAnswers = choiceFieldState.getValue();
            List<String> choices = choiceField.getChoices();
            for (int j = 0; j < fieldAnswers.size(); j++) {
                if (fieldAnswers.get(j)) {
                    answer.append(choices.get(j)).append(" ");
                    if (isSingleChoice) break;
                }
            }
            if (answer.length() > 0) answer.deleteCharAt(answer.length() - 1);

            boolean isHighlighted = isOutOfNorm(title, answer.toString());
            if (!answer.toString().equals("B.D."))
                answer = new StringBuilder(addUnits(title, answer.toString()));

            if (!title.startsWith("Cel wizy"))
                extractedQuestions.add(new Question(fieldNumber, title, answer.toString(), isHighlighted, true));
        }

        return extractedQuestions;
    }

    private String addUnits(String title, String answer) {
        if (title.startsWith("Tempera"))
            answer += " C";

        else if (title.startsWith("Tętn"))
            answer += " /min";

        else if (title.startsWith("Satura"))
            answer += " %";

        else if (title.startsWith("Częstość odd"))
            answer += " /min";

        else if (title.startsWith("Ciśnienie skur"))
            answer += " mm Hg";

        return answer;
    }

    private boolean isOutOfNorm(String title, double value) {
        if (title.startsWith("Tempera")) {
            if (value > LimitValues.getMaxTemperature())
                return true;
        }

        if (title.startsWith("Tętn")) {
            if (value > LimitValues.getMaxPulseRate())
                return true;
            if (value < LimitValues.getMinPulseRate())
                return true;
        }

        if (title.startsWith("Satura")) {
            if (value < LimitValues.getMinAeration())
                return true;
        }

        return false;
    }

    private boolean isOutOfNorm(String title, String value) {
        if (title.startsWith("Częstość odd")) {
            for (String elem : LimitValues.getOutOfNormBreathRates()) {
                if (value.equals(elem))
                    return true;
            }
        }

        if (value.equals("TAK"))
            return true;

        return false;
    }
}
