package pl.edu.agh.ki.covid19tablet.pdfgenetator.containers;

public class PersonalData {

    private String title;
    private String value;

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
