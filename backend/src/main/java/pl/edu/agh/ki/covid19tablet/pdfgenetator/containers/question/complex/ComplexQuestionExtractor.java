package pl.edu.agh.ki.covid19tablet.pdfgenetator.containers.question.complex;

import pl.edu.agh.ki.covid19tablet.form.Form;
import pl.edu.agh.ki.covid19tablet.schema.fields.ChoiceField;
import pl.edu.agh.ki.covid19tablet.schema.fields.DerivedField;
import pl.edu.agh.ki.covid19tablet.schema.fields.DerivedType;
import pl.edu.agh.ki.covid19tablet.state.fields.ChoiceFieldState;
import pl.edu.agh.ki.covid19tablet.state.fields.DerivedFieldState;

import java.util.ArrayList;
import java.util.List;

public class ComplexQuestionExtractor {

    public List<ComplexQuestion> extract(Form form) {
        List<ComplexQuestion> extractedQuestions = new ArrayList<>();

        extractedQuestions.addAll(extractDerivedQuestions(form));
        extractedQuestions.addAll(extractMultiChoiceQuestions(form));

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
                List<SubQuestion> subQuestions = new ArrayList<>();
                List<String> subtitles = derivedField.getTitles();
                List<String> subAnswers = derivedFieldState.getValue();

                if (answer.equals("TAK")) {
                    for (int i = 1; i < subtitles.size(); i++)
                        subQuestions.add(new SubQuestion(subtitles.get(i), subAnswers.get(i), true, false));
                }
                extractedQuestions.add(new ComplexQuestion(fieldNumber, title, answer, subQuestions));
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
            List<SubQuestion> subQuestions = new ArrayList<>();
            List<String> subtitles = choiceField.getChoices();
            List<Boolean> values = choiceFieldState.getValue();
            for (int i = 0; i < subtitles.size(); i++) {
                if (values.get(i))
                    subQuestions.add(new SubQuestion(subtitles.get(i), "TAK", true, true));
                else
                    subQuestions.add(new SubQuestion(subtitles.get(i), "NIE", false, false));
            }

            extractedQuestions.add(new ComplexQuestion(fieldNumber, title, answer, subQuestions));
        }

        return extractedQuestions;
    }
}
