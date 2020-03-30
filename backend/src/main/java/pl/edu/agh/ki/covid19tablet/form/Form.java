package pl.edu.agh.ki.covid19tablet.form;

import pl.edu.agh.ki.covid19tablet.question.BaseField;
import pl.edu.agh.ki.covid19tablet.question.ChoiceField;
import pl.edu.agh.ki.covid19tablet.question.TextField;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Entity
public class Form {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "form")
    private List<BaseField> fields;

    public Form() {
        fields = new ArrayList<>();
    }

    public Form(List<BaseField> fields) {
        this.fields = fields;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public List<BaseField> getFields() {
        return fields;
    }
    public void setFields(List<BaseField> fields) {
        this.fields = fields;
    }

    public void addField(BaseField field) {
        fields.add(field);
    }

    public void updateChoiceField(Map<String, Boolean> values, int field) {
        ChoiceField choiceField = (ChoiceField) fields.get(field);
        choiceField.setValues(values);
    }
    public void updateTextField(String value, int field) {
        TextField textField = (TextField) fields.get(field);
        textField.setValue(value);
    }
}
