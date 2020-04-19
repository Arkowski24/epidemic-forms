package pl.edu.agh.ki.covid19tablet.pdfgenetator.containers.personaldata;

import pl.edu.agh.ki.covid19tablet.form.Form;

import java.util.List;

public class PersonalDataContainer {
    private List<PersonalData> personalDataList;

    public PersonalDataContainer(Form form) {
        PersonalDataExtractor personalDataExtractor = new PersonalDataExtractor();
        this.personalDataList = personalDataExtractor.extract(form);
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
}
