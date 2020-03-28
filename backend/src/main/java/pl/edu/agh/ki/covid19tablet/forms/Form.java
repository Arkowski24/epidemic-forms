package pl.edu.agh.ki.covid19tablet.forms;

import javax.persistence.*;
import java.util.List;

@Entity
public class Form {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "form")
    private List<BaseField> fields;

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
}
