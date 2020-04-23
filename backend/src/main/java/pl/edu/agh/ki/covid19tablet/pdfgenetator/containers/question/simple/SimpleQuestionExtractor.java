package pl.edu.agh.ki.covid19tablet.pdfgenetator.containers.question.simple;

import pl.edu.agh.ki.covid19tablet.form.Form;
import pl.edu.agh.ki.covid19tablet.pdfgenetator.containers.LimitValues;
import pl.edu.agh.ki.covid19tablet.schema.fields.ChoiceField;
import pl.edu.agh.ki.covid19tablet.schema.fields.SliderField;
import pl.edu.agh.ki.covid19tablet.state.fields.ChoiceFieldState;
import pl.edu.agh.ki.covid19tablet.state.fields.SliderFieldState;

import java.util.ArrayList;
import java.util.List;

public class SimpleQuestionExtractor {

    public List<SimpleQuestion> extract(Form form) {
        List<SimpleQuestion> extractedQuestions = new ArrayList<>();

        extractedQuestions.addAll(extractChoiceQuestions(form));
        extractedQuestions.addAll(extractSliderQuestions(form));

        return extractedQuestions;
    }

    private List<SimpleQuestion> extractChoiceQuestions(Form form) {
        List<SimpleQuestion> extractedQuestions = new ArrayList<>();

        List<ChoiceFieldState> choiceFieldStates = form.getState().getChoice();
        for (ChoiceFieldState choiceFieldState : choiceFieldStates) {
            ChoiceField choiceField = choiceFieldState.getField();

            if (choiceField.getMultiChoice())
                continue;

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

            boolean isHighlighted = isOutOfNorm(title, answer);

            if (!answer.equals("B.D."))
                answer = addUnits(title, answer);

            if (!title.startsWith("Cel wizy"))
                extractedQuestions.add(new SimpleQuestion(fieldNumber, title, answer, isHighlighted));
        }

        return extractedQuestions;
    }

    private List<SimpleQuestion> extractSliderQuestions(Form form) {
        List<SimpleQuestion> extractedQuestions = new ArrayList<>();

        List<SliderFieldState> sliderFieldStates = form.getState().getSlider();
        for (SliderFieldState sliderFieldState : sliderFieldStates) {
            SliderField sliderField = sliderFieldState.getField();

            int fieldNumber = sliderField.getFieldNumber();
            String title = sliderField.getTitle();
            double value = sliderFieldState.getValue();
            String answer = Double.toString(value);

            boolean isHighlighted = isOutOfNorm(title, value);
            if (value < sliderField.getMinValue()) {
                answer = "B.D.";
                isHighlighted = false;
            }
            answer = addUnits(title, answer);

            extractedQuestions.add(new SimpleQuestion(fieldNumber, title, answer, isHighlighted));
        }

        return extractedQuestions;
    }

    private String addUnits(String title, String answer) {
        if (title.startsWith("Tempera"))
            answer += " °C";

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
            return value < LimitValues.getMinAeration();
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

        return false;
    }

}
