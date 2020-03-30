package pl.edu.agh.ki.covid19tablet.form_scheme;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.agh.ki.covid19tablet.form.Form;
import pl.edu.agh.ki.covid19tablet.question.BaseField;
import pl.edu.agh.ki.covid19tablet.question.QuestionRepository;

import java.util.Optional;

@Service
public class FormSchemeService {

    @Autowired
    private FormSchemeRepository formSchemeRepository;
    @Autowired
    private QuestionRepository questionRepository;

    private FormBuilder formBuilder = new FormBuilder();

    public String add(Form form) {
        Form newForm = new Form();
        formSchemeRepository.save(newForm);

        for (BaseField question : form.getFields()) {
            question.setForm(newForm);
            newForm.addField(question);
            questionRepository.save(question);
        }
        formSchemeRepository.save(newForm);

        return "Added Form Scheme!";
    }

    public Iterable<Form> getAll() {
        return formSchemeRepository.findAll();
    }

    public Form createForm(int id) {
        Optional<Form> formSchemeOptional = formSchemeRepository.findById(id);
        if (formSchemeOptional.isPresent()) {
            Form formScheme = formSchemeOptional.get();
            return formBuilder.build(formScheme);
        }
        return null;
    }
}
