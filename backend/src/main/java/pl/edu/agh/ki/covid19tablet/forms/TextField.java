package pl.edu.agh.ki.covid19tablet.forms;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class TextField extends BaseField {

    @Column
    private String value;

    public String getValue() {
        return value;
    }
    public void setValue(String value) {
        this.value = value;
    }
}
