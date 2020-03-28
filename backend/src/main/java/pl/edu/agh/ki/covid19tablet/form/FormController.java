package pl.edu.agh.ki.covid19tablet.form;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(path = "/forms")
public class FormController {

    @Autowired
    FormService formService;

    @RequestMapping(path = "/")
    public String greet() {
        return "Welcome to the Form Controller!";
    }

    @GetMapping(path = "/create")
    public String createForm(
            @RequestParam int id
    ) {
        return formService.createForm(id);
    }

    @GetMapping(path = "/get")
    public Form getForm(
            @RequestParam String token
    ) {
        return formService.getForm(token);
    }

    @PutMapping(path = "/update/textfield")
    public Boolean updateForm(
            @RequestParam String token,
            @RequestParam int question_id,
            @RequestParam String value
    ) {
        return formService.updateForm(token, question_id, value);
    }
    @PutMapping(path = "/update/choicefield")
    public Boolean updateForm(
            @RequestParam String token,
            @RequestParam int question_id,
            @RequestParam Map<String, Boolean> values
    ) {
        return formService.updateForm(token, question_id, values);
    }

    /*
    @PutMapping(path = "/updateForm")
    public Boolean updateForm(
            @RequestParam String token,
            @RequestParam int question_id,
            @RequestParam Map<String, Boolean> values
    ) {
        if (questionRepository.findById(question_id).isPresent()) {
            ChoiceField question = (ChoiceField) questionRepository.findById(question_id).get();
            question.setValues(values);

            return true;
        }

        return false;
    }
    */
}
