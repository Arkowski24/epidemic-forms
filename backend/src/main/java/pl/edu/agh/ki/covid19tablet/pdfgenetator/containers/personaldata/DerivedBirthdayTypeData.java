package pl.edu.agh.ki.covid19tablet.pdfgenetator.containers.personaldata;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class DerivedBirthdayTypeData {

    public final String type;
    public final String value;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public DerivedBirthdayTypeData(@JsonProperty("type") String type, @JsonProperty("value") String value) {
        this.type = type;
        this.value = value;
    }

    public String getType() {
        return type;
    }
    public String getValue() {
        return value;
    }
}
