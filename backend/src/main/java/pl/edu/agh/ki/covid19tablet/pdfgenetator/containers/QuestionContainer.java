package pl.edu.agh.ki.covid19tablet.pdfgenetator.containers;

import pl.edu.agh.ki.covid19tablet.form.Form;
import pl.edu.agh.ki.covid19tablet.schema.fields.*;
import pl.edu.agh.ki.covid19tablet.state.fields.ChoiceFieldState;
import pl.edu.agh.ki.covid19tablet.state.fields.DerivedFieldState;
import pl.edu.agh.ki.covid19tablet.state.fields.SliderFieldState;
import pl.edu.agh.ki.covid19tablet.state.fields.TextFieldState;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QuestionContainer {

    private List<Question> questions;
    private List<ComplexQuestion> complexQuestions;

    public QuestionContainer(Form form) {
        this.questions = extractQuestions(form);
        this.complexQuestions = extractComplexQuestions(form);
    }

    public List<Question> getQuestions() {
        return questions;
    }
    public List<ComplexQuestion> getComplexQuestions() {
        return complexQuestions;
    }

    private List<Question> extractQuestions(Form form) {
        List<Question> extractedQuestions = new ArrayList<>();

        extractedQuestions.addAll(extractSingleChoiceQuestions(form));
        extractedQuestions.addAll(extractSliderQuestions(form));
        extractedQuestions.addAll(extractTextQuestions(form));

        return extractedQuestions;
    }

    private List<ComplexQuestion> extractComplexQuestions(Form form) {
        List<ComplexQuestion> extractedQuestions = new ArrayList<>();

        extractedQuestions.addAll(extractDerivedQuestions(form));
        extractedQuestions.addAll(extractMultiChoiceQuestions(form));

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

            if (!(
                    textField.getTitle().startsWith("Imi") ||
                    textField.getTitle().startsWith("Nazwis") ||
                    textField.getTitle().startsWith("Telefon")
            )) {
                int fieldNumber = textField.getFieldNumber();
                String title = textField.getTitle();
                String answer = textFieldState.getValue();
                extractedQuestions.add(new Question(fieldNumber, title, answer, false, false));
            }
        }

        return extractedQuestions;
    }

    private List<Question> extractSingleChoiceQuestions(Form form) {
        List<Question> extractedQuestions = new ArrayList<>();

        List<ChoiceFieldState> choiceFieldStates = form.getState().getChoice();
        for (ChoiceFieldState choiceFieldState : choiceFieldStates) {
            ChoiceField choiceField = choiceFieldState.getField();

            if (choiceField.getMultiChoice())
                continue;

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

    private List<ComplexQuestion> extractDerivedQuestions(Form form) {
        List<ComplexQuestion> extractedQuestions = new ArrayList<>();

        List<DerivedFieldState> derivedFieldStates = form.getState().getDerived();
        for (DerivedFieldState derivedFieldState : derivedFieldStates) {
            DerivedField derivedField = derivedFieldState.getField();

            int fieldNumber = derivedField.getFieldNumber();
            String title = derivedField.getTitles().get(0);
            String answer = derivedFieldState.getValue().get(0);

            if (derivedField.getDerivedType() == DerivedType.CHOICE_INFO) {
                if (answer.equals("TAK")) {
                    extractedQuestions.add(new ComplexQuestion(
                            fieldNumber,
                            title,
                            answer,
                            new ArrayList<> (Arrays.asList(derivedField.getTitles().get(1))),
                            new ArrayList<> (Arrays.asList(derivedFieldState.getValue().get(1))),
                            true
                    ));
                }
                else {
                    extractedQuestions.add(new ComplexQuestion(
                            fieldNumber,
                            title,
                            answer,
                            new ArrayList<> (Arrays.asList("NIE")),
                            new ArrayList<> (Arrays.asList("")),
                            false
                    ));
                }
            }
        }

        return extractedQuestions;
    }

    private List<ComplexQuestion> extractMultiChoiceQuestions(Form form) {
        List<ComplexQuestion> extractedQuestions = new ArrayList<>();

        List<ChoiceFieldState> choiceFieldStates = form.getState().getChoice();
        for (ChoiceFieldState choiceFieldState : choiceFieldStates) {
            ChoiceField choiceField = choiceFieldState.getField();

            if (!choiceField.getMultiChoice())
                continue;

            int fieldNumber = choiceField.getFieldNumber();
            String title = choiceField.getTitle();
            String answer = "";
            List<String> subtitles = choiceField.getChoices();
            List<Boolean> values = choiceFieldState.getValue();
            ArrayList<String> subanswers = new ArrayList<>();
            for (int i = 0; i < subtitles.size(); i++) {
                if (values.get(i))
                    subanswers.add("TAK");
                else
                    subanswers.add("NIE");
            }

            extractedQuestions.add(new ComplexQuestion(
                    fieldNumber,
                    title,
                    answer,
                    subtitles,
                    subanswers,
                    true
            ));
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
