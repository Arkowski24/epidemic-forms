package pl.edu.agh.ki.covid19tablet.form_pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.agh.ki.covid19tablet.form.Form;

@Service
public class FormPatternService {

    @Autowired
    private FormPatternRepository formPatternRepository;

    public void add(Form form) {
        formPatternRepository.save(form);
    }

    public Iterable<Form> getAll() {
        return formPatternRepository.findAll();
    }
}
