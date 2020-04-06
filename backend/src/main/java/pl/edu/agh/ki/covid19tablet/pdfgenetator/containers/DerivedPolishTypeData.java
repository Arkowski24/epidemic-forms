package pl.edu.agh.ki.covid19tablet.pdfgenetator.containers;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class DerivedPolishTypeData {
    public final String type;
    public final String value;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public DerivedPolishTypeData(@JsonProperty("type") String type, @JsonProperty("value") String value) {
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
