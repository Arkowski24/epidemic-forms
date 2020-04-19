package pl.edu.agh.ki.covid19tablet.pdfgenetator;

import pl.edu.agh.ki.covid19tablet.form.Form;
import pl.edu.agh.ki.covid19tablet.pdfgenetator.containers.MetadataContainer;
import pl.edu.agh.ki.covid19tablet.pdfgenetator.containers.PersonalDataContainer;
import pl.edu.agh.ki.covid19tablet.pdfgenetator.containers.question.QuestionContainer;
import pl.edu.agh.ki.covid19tablet.pdfgenetator.containers.SignaturesContainer;

public class FormKeyData {

    private final String hospitalName = "Krakowski Szpital Specjalistyczny im. Jana Pawła II";
    private String title = "Formularz epidemiczny";

    private MetadataContainer metadata;
    private PersonalDataContainer personalData;
    private QuestionContainer questions;
    private SignaturesContainer signatures;

    private String pdfDirPath;

    public FormKeyData(Form form, String creationDate, String pdfBasicDirPath) {
        this.metadata = new MetadataContainer(form, creationDate);
        this.personalData = new PersonalDataContainer(form);
        this.questions = new QuestionContainer(form);
        this.signatures = new SignaturesContainer(form);

        String purposeOfVisit = metadata.getPurposeOfVisit();
        purposeOfVisit = purposeOfVisit.replaceAll(" ", "_");
        this.pdfDirPath = pdfBasicDirPath + "/" + purposeOfVisit;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public String getTitle() {
        return title;
    }

    public MetadataContainer getMetadata() {
        return metadata;
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

    public String getPdfDirPath() {
        return pdfDirPath;
    }
}
