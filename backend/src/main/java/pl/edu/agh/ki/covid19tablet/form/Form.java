package pl.edu.agh.ki.covid19tablet.form;

import pl.edu.agh.ki.covid19tablet.question.BaseField;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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
}
