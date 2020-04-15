package pl.edu.agh.ki.covid19tablet.pdfgenetator.containers;

import pl.edu.agh.ki.covid19tablet.form.Form;
import pl.edu.agh.ki.covid19tablet.schema.fields.ChoiceField;
import pl.edu.agh.ki.covid19tablet.state.fields.ChoiceFieldState;

import java.util.List;

public class MetadataContainer {

    private static final String usedDeviceTitle = "Punkt wstępnej oceny pacjentów";
    private String usedDevice;

    private static final String creationDateTitle = "Data wypełnienia";
    private String creationDate;

    private String purposeOfVisitTitle = "Cel wizyty";
    private String purposeOfVisit;

    public MetadataContainer(Form form, String creationDate) {
        String deviceName = (form.getDevice() == null || form.getDevice().getFullName() == null) ? "" : form.getDevice().getFullName();

        this.usedDevice = deviceName.length() > 0 ? deviceName : "Educatorium";
        this.creationDate = creationDate;
        this.purposeOfVisit = extractPurposeOfVisit(form);
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

    public String getPurposeOfVisitTitle() {
        return purposeOfVisitTitle;
    }
    public String getPurposeOfVisit() {
        return purposeOfVisit;
    }

    private String extractPurposeOfVisit(Form form) {
        List<ChoiceFieldState> choiceFieldStates = form.getState().getChoice();
        String value = "";

        for (ChoiceFieldState choiceFieldState : choiceFieldStates) {
            ChoiceField choiceField = choiceFieldState.getField();
            if (choiceField.getTitle().startsWith("Cel wizy")) {
                List<String> choices = choiceField.getChoices();
                List<Boolean> values = choiceFieldState.getValue();
                for (int i = 0; i < values.size() && i < choices.size(); i++) {
                    if (values.get(i))
                        value = choices.get(i);
                }
            }
        }

        return value;
    }
}
