package pl.edu.agh.ki.covid19tablet.form_scheme;

import com.fasterxml.jackson.databind.ObjectMapper;
import pl.edu.agh.ki.covid19tablet.form.Form;

public class FormBuilder {

    public Form build(Form form) {

        try {
            ObjectMapper mapper = new ObjectMapper();
            String jsonForm = mapper.writeValueAsString(form);
            return mapper.readValue(jsonForm, Form.class);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
