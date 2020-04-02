package pl.edu.agh.ki.covid19tablet.pdfgenetator.converters;

public enum ConvertedBoolean {

    TAK("TAK"),
    NIE("NIE");

    private String name;

    ConvertedBoolean(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
