package pl.edu.agh.ki.covid19tablet.form;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.agh.ki.covid19tablet.form_pattern.FormPatternRepository;
import pl.edu.agh.ki.covid19tablet.question.ChoiceField;
import pl.edu.agh.ki.covid19tablet.question.QuestionRepository;
import pl.edu.agh.ki.covid19tablet.question.TextField;

import java.util.HashMap;
import java.util.Map;

@Service
public class FormService {

    @Autowired
    private FormRepository formRepository;
    @Autowired
    private FormPatternRepository formPatternRepository;
    @Autowired
    private QuestionRepository questionRepository;

    private FormBuilder formBuilder = new FormBuilder();
    private Map<String, Form> activeForms = new HashMap<>();

    public String createForm(int id) {
        if (formPatternRepository.findById(id).isPresent()) {
            Form pattern = formPatternRepository.findById(id).get();
            Form newForm = formBuilder.build(pattern);
            formRepository.save(newForm);

            String token = "abcdefghijkl" + activeForms.size();
            activeForms.put(token, newForm);

            return token;
        }

        return null;
    }

    public Form getForm(String token) {
        return activeForms.get(token);
    }

    public Boolean updateForm(String token, int question_id, String value) {
        if (questionRepository.findById(question_id).isPresent()) {
            TextField question = (TextField) questionRepository.findById(question_id).get();
            question.setValue(value);
            return true;
        }
        return false;
    }
    public Boolean updateForm(String token, int question_id, Map<String, Boolean> values) {
        if (questionRepository.findById(question_id).isPresent()) {
            ChoiceField question = (ChoiceField) questionRepository.findById(question_id).get();
            question.setValues(values);
            return true;
        }
        return false;
    }
}
