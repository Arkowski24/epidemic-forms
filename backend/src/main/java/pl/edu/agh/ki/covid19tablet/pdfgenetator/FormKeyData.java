package pl.edu.agh.ki.covid19tablet.pdfgenetator;

import pl.edu.agh.ki.covid19tablet.form.Form;
import pl.edu.agh.ki.covid19tablet.pdfgenetator.containers.PersonalDataContainer;
import pl.edu.agh.ki.covid19tablet.pdfgenetator.containers.QuestionContainer;
import pl.edu.agh.ki.covid19tablet.pdfgenetator.containers.SignaturesContainer;

public class FormKeyData {

    private String creationDate;
    private String title;
    private String employeeFullName;
    private PersonalDataContainer personalData;
    private QuestionContainer questions;
    private SignaturesContainer signatures;

    public FormKeyData(Form form, String creationDate) {
        this.creationDate = creationDate;
        this.title = form.getSchema().getName();
        this.employeeFullName = form.getCreatedBy().getFullName();
        this.personalData = new PersonalDataContainer(form);
        this.questions = new QuestionContainer(form);
        this.signatures = new SignaturesContainer(form);
    }

    public String getCreationDate() {
        return creationDate;
    }

    public String getTitle() {
        return title;
    }

    public String getEmployeeFullName() {
        return employeeFullName;
    }

    public PersonalDataContainer getPersonalData() {
        return personalData;
    }

    public QuestionContainer getQuestions() {
        return questions;
    }

    public SignaturesContainer getSignatures() {
        return signatures;
    }
}
