package pl.edu.agh.ki.covid19tablet.form;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class FormController {

    @Autowired
    private FormService formService;

    @MessageMapping("/choicefield.update")
    @SendTo("/topic/update")
    public UpdateChoiceFieldMessage updateChoiceField(@Payload UpdateChoiceFieldMessage updateChoiceFieldMessage) {
        formService.updateChoiceField(updateChoiceFieldMessage);
        return updateChoiceFieldMessage;
    }

    @MessageMapping("/textfield.update")
    @SendTo("/topic/update")
    public UpdateTextFieldMessage updateTextField(@Payload UpdateTextFieldMessage updateTextFieldMessage) {
        formService.updateTextField(updateTextFieldMessage);
        return updateTextFieldMessage;
    }

    @MessageMapping("/form.get")
    @SendTo("/topic/get")
    public Form getForm() {
        return formService.getCurrentForm();
    }

    @MessageMapping("/form.create")
    public void createForm(@Payload Form form) {
        formService.setCurrentForm(form);
    }
}
