package pl.edu.agh.ki.covid19tablet.pdfgenetator.containers.personaldata;

public class PersonalData {

    private final String title;
    private final String value;

    public PersonalData(String title, String value) {
        this.title = title;
        this.value = value;
    }

    public String getTitle() {
        return title;
    }
    public String getValue() {
        return value;
    }
}
