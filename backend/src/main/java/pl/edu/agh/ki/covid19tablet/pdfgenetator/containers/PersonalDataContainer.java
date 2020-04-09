package pl.edu.agh.ki.covid19tablet.pdfgenetator.containers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jetbrains.annotations.Nullable;
import pl.edu.agh.ki.covid19tablet.form.Form;
import pl.edu.agh.ki.covid19tablet.schema.fields.ChoiceField;
import pl.edu.agh.ki.covid19tablet.schema.fields.DerivedField;
import pl.edu.agh.ki.covid19tablet.schema.fields.DerivedType;
import pl.edu.agh.ki.covid19tablet.schema.fields.TextField;
import pl.edu.agh.ki.covid19tablet.state.fields.ChoiceFieldState;
import pl.edu.agh.ki.covid19tablet.state.fields.DerivedFieldState;
import pl.edu.agh.ki.covid19tablet.state.fields.TextFieldState;

import java.util.ArrayList;
import java.util.List;

public class PersonalDataContainer {
    private List<PersonalData> personalDataList;


    public PersonalDataContainer(Form form) {
        this.personalDataList = extractPersonalData(form);
    }

    public List<PersonalData> getPersonalDataList() {
        return personalDataList;
    }

    public String getSurname() {
        for (PersonalData personalData : personalDataList) {
            if (personalData.getTitle().startsWith("Nazwis")) {
                String surname = personalData.getValue();
                surname = surname.replaceAll(" ", "");
                return surname;
            }
        }

        return "";
    }

    public String getFirstName() {
        for (PersonalData personalData : personalDataList) {
            if (personalData.getTitle().startsWith("Imi")) {
                String firstName = personalData.getValue();
                firstName = firstName.replaceAll(" ", "");
                firstName = firstName.replaceAll(",", "");
                return firstName;
            }
        }

        return "";
    }

    private List<PersonalData> extractPersonalData(Form form) {
        List<PersonalData> extractedPersonalData = new ArrayList<>();

        extractedPersonalData.add(extractPatternText(form, "Nazwisko", "Nazwis"));
        extractedPersonalData.add(extractPatternText(form, "Imię", "Imi"));
        extractedPersonalData.addAll(extractDerived(form));
        extractedPersonalData.add(extractPatternChoice(form, "Cel wizyty", "Cel wizy"));

        return extractedPersonalData;
    }

    private PersonalData extractPatternText(Form form, String defaultName, String pattern) {
        List<TextFieldState> textFieldStates = form.getState().getText();

        String title = defaultName;
        String value = "";

        for (TextFieldState textFieldState : textFieldStates) {
            TextField textField = textFieldState.getField();
            if (textField.getTitle().startsWith(pattern)) {
                title = textField.getTitle();
                value = textFieldState.getValue();
                break;
            }
        }

        if (title.charAt(title.length() - 1) != ':') {
            title = title + ':';
        }

        return new PersonalData(title, value);
    }

    private PersonalData extractPatternChoice(Form form, String defaultName, String pattern) {
        List<ChoiceFieldState> choiceFieldStates = form.getState().getChoice();

        String title = defaultName;
        String value = "";

        for (ChoiceFieldState choiceFieldState : choiceFieldStates) {
            ChoiceField choiceField = choiceFieldState.getField();
            if (choiceField.getTitle().startsWith(pattern)) {
                List<String> choices = choiceField.getChoices();
                List<Boolean> values = choiceFieldState.getValue();
                for (int i = 0; i < values.size() && i < choices.size(); i++) {
                    if (values.get(i))
                        value = choices.get(i);
                }
            }
        }

        if (title.charAt(title.length() - 1) != ':') {
            title = title + ':';
        }

        return new PersonalData(title, value);
    }

    private List<PersonalData> extractDerived(Form form) {
        List<PersonalData> extractedPersonalData = new ArrayList<>();
        List<DerivedFieldState> derivedFieldStates = form.getState().getDerived();

        for (DerivedFieldState derivedFieldState : derivedFieldStates) {
            DerivedField derivedField = derivedFieldState.getField();

            List<String> titles = derivedField.getTitles();
            List<String> values = derivedFieldState.getValue();
            for (int j = 0; j < titles.size() && j < values.size(); j++) {
                String value = values.get(j);

                if (j == 0 && derivedField.getDerivedType() == DerivedType.BIRTHDAY_PESEL) {
                    if (value.isEmpty()) {
                        extractedPersonalData.add(new PersonalData("PESEL" + ':', "B.D."));
                    } else {
                        DerivedPolishTypeData data = extractPersonal(value);
                        if (data == null) continue;

                        String newValue = data.getValue();
                        if (newValue.isEmpty()) newValue = "B.D";
                        extractedPersonalData.add(new PersonalData(data.getType() + ':', newValue));
                    }
                    continue;
                }
                if (value.isEmpty()) value = "B.D.";

                String title = titles.get(j);
                if (title.charAt(title.length() - 1) != ':') {
                    title = title + ':';
                }

                extractedPersonalData.add(new PersonalData(title, value));
            }
        }

        return extractedPersonalData;
    }

    @Nullable
    private DerivedPolishTypeData extractPersonal(String personalJSON) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(personalJSON, DerivedPolishTypeData.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
