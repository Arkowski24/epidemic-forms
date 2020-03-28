package pl.edu.agh.ki.covid19tablet.forms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class FormController {

    @Autowired
    private FormRepository formRepository;

    @RequestMapping("/")
    public String greet() {
        return "Welcome to the Form Controller!";
    }

    @PostMapping(path = "/add")
    public String addNewForm(
            @RequestParam List<BaseField> fields
    ) {
        Form form = new Form(fields);
        formRepository.save(form);
        return "Saved";
    }

    @GetMapping(path = "/get")
    public Optional<Form> getForm(int id) {
        return formRepository.findById(id);
    }

    @GetMapping(path = "/getAll")
    public Iterable<Form> getAllForm() {
        return formRepository.findAll();
    }
}
