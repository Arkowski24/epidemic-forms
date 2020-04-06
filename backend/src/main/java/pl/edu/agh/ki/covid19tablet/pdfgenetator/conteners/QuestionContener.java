package pl.edu.agh.ki.covid19tablet.pdfgenetator.conteners;

import pl.edu.agh.ki.covid19tablet.form.Form;
import pl.edu.agh.ki.covid19tablet.schema.fields.ChoiceField;
import pl.edu.agh.ki.covid19tablet.schema.fields.SliderField;
import pl.edu.agh.ki.covid19tablet.schema.fields.TextField;
import pl.edu.agh.ki.covid19tablet.state.fields.ChoiceFieldState;
import pl.edu.agh.ki.covid19tablet.state.fields.SliderFieldState;
import pl.edu.agh.ki.covid19tablet.state.fields.TextFieldState;

import java.util.ArrayList;
import java.util.List;

public class QuestionContener {

    private int maxFieldNumber;
    private List<Question> questions;

    public QuestionContener(Form form) {
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

        List<SliderField> sliderFields = form.getSchema().getFields().getSlider();
        List<SliderFieldState> sliderFieldStates = form.getState().getSlider();
        for (int i = 0; i < sliderFields.size() && i < sliderFieldStates.size(); i++) {
            int fieldNumber = sliderFields.get(i).getFieldNumber();
            String title = sliderFields.get(i).getTitle();
            String answer = Double.toString(sliderFieldStates.get(i).getValue());
            extractedQuestions.add(new Question(fieldNumber, title, answer));

            if (fieldNumber > this.maxFieldNumber) {
                this.maxFieldNumber = fieldNumber;
            }
        }

        return extractedQuestions;
    }

    private List<Question> extractTextQuestions(Form form) {
        List<Question> extractedQuestions = new ArrayList<>();

        List<TextField> textFields = form.getSchema().getFields().getText();
        List<TextFieldState> textFieldStates = form.getState().getText();
        for (int i = 0; i < textFields.size() && i < textFieldStates.size(); i++) {
            if (!(textFields.get(i).getTitle().startsWith("Imi") || textFields.get(i).getTitle().startsWith("Nazwis"))) {
                int fieldNumber = textFields.get(i).getFieldNumber();
                String title = textFields.get(i).getTitle();
                String answer = textFieldStates.get(i).getValue();
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

        List<ChoiceField> choiceFields = form.getSchema().getFields().getChoice();
        List<ChoiceFieldState> choiceFieldStates = form.getState().getChoice();
        for (int i = 0; i < choiceFields.size() && i < choiceFieldStates.size(); i++) {
            int fieldNumber = choiceFields.get(i).getFieldNumber();
            String title = choiceFields.get(i).getTitle();
            String answer = "";

            List<Boolean> fieldAnwsers = choiceFieldStates.get(i).getValue();
            for (int j = 0; j < fieldAnwsers.size(); j++) {
                if (fieldAnwsers.get(j)) {
                    answer = choiceFieldStates.get(i).getField().getChoices().get(j);
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
