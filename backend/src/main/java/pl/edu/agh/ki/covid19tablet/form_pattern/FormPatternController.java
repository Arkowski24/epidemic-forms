package pl.edu.agh.ki.covid19tablet.form_pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.edu.agh.ki.covid19tablet.form.Form;

@RestController
@RequestMapping(path = "/formPatterns")
public class FormPatternController {

    @Autowired
    private FormPatternService formPatternService;

    @PostMapping(path = "/")
    public String addFormPattern(
            @RequestParam Form form
    ) {
        formPatternService.add(form);
        return "Added Form Pattern";
    }

    @GetMapping(path = "/all")
    public Iterable<Form> getAllFormPatterns() {
        return formPatternService.getAll();
    }
}
