package pl.edu.agh.ki.covid19tablet.form_scheme;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.edu.agh.ki.covid19tablet.form.Form;

import java.util.Optional;

@RestController
@RequestMapping(path = "/formschemes")
public class FormSchemeController {

    @Autowired
    private FormSchemeService formSchemeService;

    private FormBuilder formBuilder = new FormBuilder();

    @PostMapping(path = "/")
    public String addFormScheme(
            @RequestBody Form form
    ) {
        formSchemeService.add(form);
        return "Added Form Scheme";
    }

    @GetMapping(path = "/")
    public Form createForm(
            @RequestParam int id
    ) {
        Optional<Form> formSchemeOptional = formSchemeService.get(id);
        if (formSchemeOptional.isPresent()) {
            Form formScheme = formSchemeOptional.get();
            return formBuilder.build(formScheme);
        }
        return null;
    }

    @GetMapping(path = "/all")
    public Iterable<Form> getAllFormSchemes() {
        return formSchemeService.getAll();
    }
}
