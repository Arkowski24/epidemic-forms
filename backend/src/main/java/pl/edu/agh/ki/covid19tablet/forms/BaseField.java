package pl.edu.agh.ki.covid19tablet.forms;

import javax.persistence.*;

@Entity
public class BaseField {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "form_id")
    private Form form;

    @Column(nullable = false)
    private String description;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public Form getForm() {
        return form;
    }
    public void setForm(Form form) {
        this.form = form;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
}
