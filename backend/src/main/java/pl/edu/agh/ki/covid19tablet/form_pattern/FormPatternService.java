package pl.edu.agh.ki.covid19tablet.form_pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.agh.ki.covid19tablet.form.Form;
import pl.edu.agh.ki.covid19tablet.question.BaseField;
import pl.edu.agh.ki.covid19tablet.question.QuestionRepository;

@Service
public class FormPatternService {

    @Autowired
    private FormPatternRepository formPatternRepository;
    @Autowired
    private QuestionRepository questionRepository;


    public void add(Form form) {
        Form newForm = new Form();
        formPatternRepository.save(newForm);

        for (BaseField question : form.getFields()) {
            question.setForm(newForm);
            newForm.addField(question);
            questionRepository.save(question);
        }
        formPatternRepository.save(newForm);
    }

    public Iterable<Form> getAll() {
        return formPatternRepository.findAll();
    }
}
