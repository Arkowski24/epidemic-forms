package pl.edu.agh.ki.covid19tablet.question;

import pl.edu.agh.ki.covid19tablet.form.Form;

import javax.persistence.*;

@Entity
public class BaseField {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "form_id")
    private Form form;

    private String description;

    @Column(name = "order_number")
    private int order;

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

    public int getOrder() {
        return order;
    }
    public void setOrder(int order) {
        this.order = order;
    }
}
