package pl.edu.agh.ki.covid19tablet.pdfgenetator.containers;

public class MetadataContainer {

    private static final String usedDeviceTitle = "Punkt wstępnej oceny pacjentów";
    private String usedDevice;

    private static final String creationDateTitle = "Data wypełnienia";
    private String creationDate;

    public MetadataContainer(String creationDate) {
        this.usedDevice = "Educatorium";
        this.creationDate = creationDate;
    }

    public String getUsedDeviceTitle() {
        return usedDeviceTitle;
    }
    public String getUsedDevice() {
        return usedDevice;
    }

    public String getCreationDateTitle() {
        return creationDateTitle;
    }
    public String getCreationDate() {
        return creationDate;
    }
}
