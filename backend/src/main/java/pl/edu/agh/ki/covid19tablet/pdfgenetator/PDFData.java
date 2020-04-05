package pl.edu.agh.ki.covid19tablet.pdfgenetator;

import pl.edu.agh.ki.covid19tablet.form.Form;
import pl.edu.agh.ki.covid19tablet.pdfgenetator.conteners.PersonalDataContener;
import pl.edu.agh.ki.covid19tablet.pdfgenetator.conteners.QuestionContener;
import pl.edu.agh.ki.covid19tablet.pdfgenetator.conteners.SignaturesContener;

public class PDFData {

    private String creationDate;
    private String title;
    private PersonalDataContener personalData;
    private QuestionContener questions;
    private SignaturesContener signatures;

    public PDFData(Form form, String creationDate) {
        this.creationDate = creationDate;
        this.title = form.getFormName();
        this.questions = new QuestionContener(form);
        this.signatures = new SignaturesContener(form);
    }

    public String getCreationDate() {
        return creationDate;
    }

    public String getTitle() {
        return title;
    }

    public QuestionContener getQuestions() {
        return questions;
    }

    public SignaturesContener getSignatures() {
        return signatures;
    }

}
