package pl.edu.agh.ki.covid19tablet.pdfgenetator.conteners;

import pl.edu.agh.ki.covid19tablet.form.Form;
import pl.edu.agh.ki.covid19tablet.form.signature.Signature;

public class SignaturesContener {

    private Signature employeeSignature;
    private Signature patientSignature;

    private String employeeSignatureTitle;
    private String patientSignatureTitle;

    public SignaturesContener(Form form) {
        this.employeeSignature = form.getEmployeeSignature();
        this.patientSignature = form.getPatientSignature();
        this.employeeSignatureTitle = form.getSchema().getEmployeeSignature().getTitle();
        this.patientSignatureTitle = form.getSchema().getPatientSignature().getTitle();
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
