package pl.edu.agh.ki.covid19tablet.question;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import java.util.Map;

@Entity
public class ChoiceField extends BaseField {

    @ElementCollection
    private Map<String, Boolean> values;

    public Map<String, Boolean> getValues() {
        return values;
    }
    public void setValues(Map<String, Boolean> values) {
        this.values = values;
    }
}
