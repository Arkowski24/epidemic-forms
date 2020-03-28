package pl.edu.agh.ki.covid19tablet.forms;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FormController {

    private FormRepository formRepository;

    @GetMapping("/")
    public String greet() {
        return "Welcome to the Form Controller!";
    }
}
