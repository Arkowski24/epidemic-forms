package pl.edu.agh.ki.covid19tablet.pdfgenetator.conteners;

import pl.edu.agh.ki.covid19tablet.form.Form;
import pl.edu.agh.ki.covid19tablet.schema.fields.DerivedField;
import pl.edu.agh.ki.covid19tablet.schema.fields.TextField;
import pl.edu.agh.ki.covid19tablet.state.fields.DerivedFieldState;
import pl.edu.agh.ki.covid19tablet.state.fields.TextFieldState;

import java.util.ArrayList;
import java.util.List;

public class PersonalDataContener {

    private List<PersonalData> personalDatas;

    public PersonalDataContener(Form form) {
        this.personalDatas = extractPersonalData(form);
    }

    public List<PersonalData> getPersonalDatas() {
        return personalDatas;
    }

    private List<PersonalData> extractPersonalData(Form form) {
        List<PersonalData> extractedPersonalData = new ArrayList<>();

        extractedPersonalData.add(extractFirstName(form));
        extractedPersonalData.add(extractSurname(form));
        extractedPersonalData.addAll(extractDerivedes(form));

        return extractedPersonalData;
    }

    private PersonalData extractFirstName(Form form) {
        List<TextField> textFields = form.getSchema().getFields().getText();
        List<TextFieldState> textFieldStates = form.getState().getText();
        String name = "Imię:";
        String value = "";

        for (int i = 0; i < textFields.size() && i < textFieldStates.size(); i++) {
            if (textFields.get(i).getTitle().startsWith("Imi")) {
                name = textFields.get(i).getTitle();
                value = textFieldStates.get(i).getValue();
                break;
            }
        }

        return new PersonalData(name, value);
    }

    private PersonalData extractSurname(Form form) {
        List<TextField> textFields = form.getSchema().getFields().getText();
        List<TextFieldState> textFieldStates = form.getState().getText();
        String name = "Nazwisko:";
        String value = "";

        for (int i = 0; i < textFields.size() && i < textFieldStates.size(); i++) {
            if (textFields.get(i).getTitle().startsWith("Nazwis")) {
                name = textFields.get(i).getTitle();
                value = textFieldStates.get(i).getValue();
                break;
            }
        }

        return new PersonalData(name, value);
    }

    private List<PersonalData> extractDerivedes(Form form) {
        List<PersonalData> extractedPersonalData = new ArrayList<>();

        List<DerivedField> derivedFields = form.getSchema().getFields().getDerived();
        List<DerivedFieldState> derivedFieldStates = form.getState().getDerived();

        for (int i = 0; i < derivedFields.size() && i < derivedFieldStates.size(); i++) {
            List<String> titles = derivedFields.get(i).getTitles();
            List<String> values = derivedFieldStates.get(i).getValue();
            for (int j = 0; j < titles.size() && j < values.size(); j++) {
                extractedPersonalData.add(new PersonalData(titles.get(i), values.get(i)));
            }
        }

        return extractedPersonalData;
    }
}
