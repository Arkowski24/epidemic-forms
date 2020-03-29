package pl.edu.agh.ki.covid19tablet.form;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.agh.ki.covid19tablet.form_scheme.FormSchemeRepository;
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
    private FormSchemeRepository formSchemeRepository;
    @Autowired
    private QuestionRepository questionRepository;

    private FormBuilder formBuilder = new FormBuilder();
    private Map<String, Form> activeForms = new HashMap<>();

    public String createForm(int id) {
        if (formSchemeRepository.findById(id).isPresent()) {
            Form pattern = formSchemeRepository.findById(id).get();
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

    public Boolean updateForm(String token, int questionId, String value) {
        if (questionRepository.findById(questionId).isPresent()) {
            TextField question = (TextField) questionRepository.findById(questionId).get();
            question.setValue(value);
            return true;
        }
        return false;
    }
    public Boolean updateForm(String token, int questionId, Map<String, Boolean> values) {
        if (questionRepository.findById(questionId).isPresent()) {
            ChoiceField question = (ChoiceField) questionRepository.findById(questionId).get();
            question.setValues(values);
            return true;
        }
        return false;
    }
}
