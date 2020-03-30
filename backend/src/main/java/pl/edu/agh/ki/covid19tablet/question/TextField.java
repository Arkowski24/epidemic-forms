package pl.edu.agh.ki.covid19tablet.question;

import javax.persistence.Entity;

@Entity
public class TextField extends BaseField {

    private String value;

    public String getValue() {
        return value;
    }
    public void setValue(String value) {
        this.value = value;
    }
}
