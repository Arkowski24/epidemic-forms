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

    public String getSurname() {
        for (PersonalData personalData : personalDatas) {
            if (personalData.getTitle().startsWith("Nazwis")) {
                return personalData.getValue();
            }
        }

        return "";
    }

    private List<PersonalData> extractPersonalData(Form form) {
        List<PersonalData> extractedPersonalData = new ArrayList<>();

        extractedPersonalData.add(extactPattern(form, "Nazwisko", "Nazwis"));
        extractedPersonalData.add(extactPattern(form, "Imię", "Imi"));
        extractedPersonalData.addAll(extractDerivedes(form));

        return extractedPersonalData;
    }

    private PersonalData extactPattern(Form form, String defaultName, String pattern) {
        List<TextField> textFields = form.getSchema().getFields().getText();
        List<TextFieldState> textFieldStates = form.getState().getText();
        String title = defaultName;
        String value = "";

        for (int i = 0; i < textFields.size() && i < textFieldStates.size(); i++) {
            if (textFields.get(i).getTitle().startsWith(pattern)) {
                title = textFields.get(i).getTitle();
                value = textFieldStates.get(i).getValue();
                break;
            }
        }

        if (title.charAt(title.length() - 1) != ':') {
            title = title + ':';
        }

        return new PersonalData(title, value);
    }

    private List<PersonalData> extractDerivedes(Form form) {
        List<PersonalData> extractedPersonalData = new ArrayList<>();

        List<DerivedField> derivedFields = form.getSchema().getFields().getDerived();
        List<DerivedFieldState> derivedFieldStates = form.getState().getDerived();

        for (int i = 0; i < derivedFields.size() && i < derivedFieldStates.size(); i++) {
            List<String> titles = derivedFields.get(i).getTitles();
            List<String> values = derivedFieldStates.get(i).getValue();
            for (int j = 0; j < titles.size() && j < values.size(); j++) {
                String title = titles.get(j);
                String value = values.get(j);

                if (title.charAt(title.length() - 1) != ':') {
                    title = title + ':';
                }

                extractedPersonalData.add(new PersonalData(title, value));
            }
        }

        return extractedPersonalData;
    }
}
