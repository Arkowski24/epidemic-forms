package pl.edu.agh.ki.covid19tablet.pdfgenetator.containers;

import pl.edu.agh.ki.covid19tablet.form.Form;
import pl.edu.agh.ki.covid19tablet.form.signature.Signature;

public class SignaturesContainer {

    private Signature employeeSignature;
    private Signature patientSignature;

    private String employeeSignatureTitle;
    private String patientSignatureTitle;

    private String employeeFullName;

    public SignaturesContainer(Form form) {
        this.employeeSignature = form.getEmployeeSignature();
        this.patientSignature = form.getPatientSignature();
        this.employeeSignatureTitle = form.getSchema().getEmployeeSignature().getTitle();
        this.patientSignatureTitle = form.getSchema().getPatientSignature().getTitle();
        this.employeeFullName = form.getCreatedBy().getFullName();
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

    public String getEmployeeFullName() {
        return employeeFullName;
    }
}
