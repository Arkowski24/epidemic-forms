package pl.edu.agh.ki.covid19tablet.pdfgenetator.containers;

import pl.edu.agh.ki.covid19tablet.form.Form;
import pl.edu.agh.ki.covid19tablet.form.signature.Signature;

public class SignaturesContainer {

    private final String patientSignatureDescription;

    private final Signature employeeSignature;
    private final Signature patientSignature;

    private final String employeeSignatureTitle;
    private final String patientSignatureTitle;

    public SignaturesContainer(Form form) {
        this.patientSignatureDescription = form.getSchema().getPatientSignature().getDescription();
        this.employeeSignature = form.getEmployeeSignature();
        this.patientSignature = form.getPatientSignature();
        this.employeeSignatureTitle = form.getSchema().getEmployeeSignature().getTitle();
        this.patientSignatureTitle = form.getSchema().getPatientSignature().getTitle();
    }

    public String getPatientSignatureDescription() {
        return patientSignatureDescription;
    }

    public Signature getEmployeeSignature() {
        return employeeSignature;
    }
    public Signature getPatientSignature() {
        return patientSignature;
    }

    public String getEmployeeSignatureTitle() {
        return employeeSignatureTitle;
    }
    public String getPatientSignatureTitle() {
        return patientSignatureTitle;
    }
}
