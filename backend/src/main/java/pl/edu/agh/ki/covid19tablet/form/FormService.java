package pl.edu.agh.ki.covid19tablet.form;

import org.springframework.stereotype.Service;

@Service
public class FormService {

    private Form currentForm = new Form();

    public Form getCurrentForm() {
        return currentForm;
    }
    public void setCurrentForm(Form currentForm) {
        this.currentForm = currentForm;
    }

    public void updateChoiceField(UpdateChoiceFieldMessage updateChoiceFieldMessage) {
        currentForm.updateChoiceField(updateChoiceFieldMessage.getNewValues(), updateChoiceFieldMessage.getField());
    }
    public void updateTextField(UpdateTextFieldMessage updateTextFieldMessage) {
        currentForm.updateTextField(updateTextFieldMessage.getNewValue(), updateTextFieldMessage.getField());
    }
}
