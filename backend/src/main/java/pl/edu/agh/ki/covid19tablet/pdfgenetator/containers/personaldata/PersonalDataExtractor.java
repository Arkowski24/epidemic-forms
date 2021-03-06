package pl.edu.agh.ki.covid19tablet.pdfgenetator.containers.personaldata;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jetbrains.annotations.Nullable;
import pl.edu.agh.ki.covid19tablet.form.Form;
import pl.edu.agh.ki.covid19tablet.schema.fields.DerivedField;
import pl.edu.agh.ki.covid19tablet.schema.fields.DerivedType;
import pl.edu.agh.ki.covid19tablet.schema.fields.TextField;
import pl.edu.agh.ki.covid19tablet.state.fields.DerivedFieldState;
import pl.edu.agh.ki.covid19tablet.state.fields.TextFieldState;

import java.util.ArrayList;
import java.util.List;

public class PersonalDataExtractor {

    public List<PersonalData> extract(Form form) {
        List<PersonalData> extractedPersonalData = new ArrayList<>();

        extractedPersonalData.add(extractPatternText(form, "Nazwisko", "Nazwis"));
        extractedPersonalData.add(extractPatternText(form, "Imię", "Imi"));
        PersonalData phoneNumber = extractPatternText(form, "Telefon Kontaktowy", "Telefon");
        if (phoneNumber != null)
            extractedPersonalData.add(phoneNumber);
        extractedPersonalData.addAll(extractDerived(form));

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

        if (!value.equals(""))
            return new PersonalData(title, value);

        return null;
    }

    private List<PersonalData> extractDerived(Form form) {
        List<PersonalData> extractedPersonalData = new ArrayList<>();
        List<DerivedFieldState> derivedFieldStates = form.getState().getDerived();

        for (DerivedFieldState derivedFieldState : derivedFieldStates) {
            DerivedField derivedField = derivedFieldState.getField();

            List<String> titles = derivedField.getTitles();
            List<String> values = derivedFieldState.getValue();

            if (derivedField.getDerivedType() == DerivedType.BIRTHDAY_PESEL) {
                DerivedBirthdayTypeData data = extractBirthday(values.get(0));
                if (data == null) continue;
                extractedPersonalData.add(new PersonalData(data.getType() + ':', data.getValue()));

                if (!values.get(1).isEmpty())
                    extractedPersonalData.add(new PersonalData(titles.get(1) + ':', values.get(1)));
            }

            else if (derivedField.getDerivedType() == DerivedType.ADDRESS) {
                DerivedAddressTypeData data = extractAddress(values.get(1));
                if (data == null) continue;

                String street = values.get(0);
                String postcode = data.getPostcode();
                String city = data.getCity();

                String title = "Adres:";
                StringBuilder addressBuilder = new StringBuilder();
                addressBuilder.append(street).append(", ");
                if (!postcode.equals(""))
                    addressBuilder.append(postcode).append(" ");
                addressBuilder.append(city);

                extractedPersonalData.add(new PersonalData(title, addressBuilder.toString()));
            }
        }

        return extractedPersonalData;
    }

    @Nullable
    private DerivedAddressTypeData extractAddress(String addressJSON) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(addressJSON, DerivedAddressTypeData.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Nullable
    private DerivedBirthdayTypeData extractBirthday(String birthdayJSON) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(birthdayJSON, DerivedBirthdayTypeData.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
