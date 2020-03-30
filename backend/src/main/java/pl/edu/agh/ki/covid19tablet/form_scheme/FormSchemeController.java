package pl.edu.agh.ki.covid19tablet.form_scheme;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.edu.agh.ki.covid19tablet.form.Form;

@RestController
@RequestMapping(path = "/formschemes")
public class FormSchemeController {

    @Autowired
    private FormSchemeService formSchemeService;

    @PostMapping(path = "/")
    public String addFormScheme(
            @RequestBody Form form
    ) {
        return formSchemeService.add(form);
    }

    @GetMapping(path = "/")
    public Form createForm(
            @RequestParam int id
    ) {
        return formSchemeService.createForm(id);
    }

    @GetMapping(path = "/all")
    public Iterable<Form> getAllFormSchemes() {
        return formSchemeService.getAll();
    }
}
