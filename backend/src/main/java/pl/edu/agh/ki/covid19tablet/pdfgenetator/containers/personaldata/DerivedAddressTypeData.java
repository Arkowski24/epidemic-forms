package pl.edu.agh.ki.covid19tablet.pdfgenetator.containers.personaldata;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class DerivedAddressTypeData {

    public final String postcode;
    public final String city;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public DerivedAddressTypeData(@JsonProperty("postcode") String postcode, @JsonProperty("city") String city) {
        this.postcode = postcode;
        this.city = city;
    }

    public String getPostcode() {
        return postcode;
    }
    public String getCity() {
        return city;
    }
}