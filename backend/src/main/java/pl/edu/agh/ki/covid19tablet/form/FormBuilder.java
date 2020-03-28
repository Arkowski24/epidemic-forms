package pl.edu.agh.ki.covid19tablet.form;

import com.google.gson.Gson;

public class FormBuilder {

    public Form build(Form form) {

        Gson gson = new Gson();
        String jsonForm = gson.toJson(form);
        return gson.fromJson(jsonForm, Form.class);
    }
}
