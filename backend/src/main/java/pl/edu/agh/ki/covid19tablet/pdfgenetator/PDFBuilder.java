package pl.edu.agh.ki.covid19tablet.pdfgenetator;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import pl.edu.agh.ki.covid19tablet.form.dto.FormDTO;
import pl.edu.agh.ki.covid19tablet.pdfgenetator.converters.BooleanConverter;
import pl.edu.agh.ki.covid19tablet.pdfgenetator.converters.ConvertedBoolean;
import pl.edu.agh.ki.covid19tablet.schema.fields.dto.*;
import pl.edu.agh.ki.covid19tablet.state.dto.FormStateDTO;
import pl.edu.agh.ki.covid19tablet.state.fields.dto.ChoiceFieldStateDTO;
import pl.edu.agh.ki.covid19tablet.state.fields.dto.SliderFieldStateDTO;
import pl.edu.agh.ki.covid19tablet.state.fields.dto.TextFieldStateDTO;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class PDFBuilder {

    public void build(String name, FormDTO formDTO) {
        Document document = new Document();

        try {
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(name));
            document.open();

            document.addCreationDate();
            addTitle(document, formDTO);
            addQuestions(formDTO.getSchema().getFields(), formDTO.getState(), document);

            document.close();
            writer.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addTitle(Document document, FormDTO formDTO) {
        try {
            Font titleFont = createTitleFont(20);
            Paragraph title = new Paragraph(formDTO.getFormName(), titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
        }
        catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    private void addQuestions(
            SchemaFieldsDTO schemaFields,
            FormStateDTO formState,
            Document document
        ) {
        try {
            Font standardFont = createStandardFont(12);

            int fieldNumber = 0;

            while (true) {

                Field currentField = findCurrentField(schemaFields, fieldNumber);
                if (currentField == null) {
                    return;
                }

                if (currentField.getFieldType().equals(FieldType.CHOICE)) {
                    Paragraph title = new Paragraph(
                            fieldNumber + ". " + currentField.getDescription(),
                            standardFont
                    );
                    document.add(title);

                    List<ConvertedBoolean> answers = new ArrayList<>();
                    List<String> questions = new ArrayList<>();

                    for (ChoiceFieldStateDTO choiceFieldStateDTO : formState.getChoice()) {
                        if (choiceFieldStateDTO.getFieldNumber() == fieldNumber) {
                            BooleanConverter converter = new BooleanConverter();
                            answers = converter.convert(choiceFieldStateDTO.getValue());
                            break;
                        }
                    }
                    for (ChoiceFieldDTO choiceFieldDTO : schemaFields.getChoice()) {
                        if (choiceFieldDTO.getFieldNumber() == fieldNumber) {
                            questions = choiceFieldDTO.getChoices();
                            break;
                        }
                    }

                    for (int i = 0; i < answers.size() && i < questions.size(); i++) {
                        String line = "    " + questions.get(i) + " " + answers.get(i);
                        Paragraph paragraph = new Paragraph(line, standardFont);
                        document.add(paragraph);
                    }
                }

                if (currentField.getFieldType().equals(FieldType.SIMPLE)) {
                    Paragraph title = new Paragraph(
                            fieldNumber + ". " +currentField.getDescription(),
                            standardFont
                    );
                    document.add(title);
                }

                if (currentField.getFieldType().equals(FieldType.SLIDER)) {
                    double value = 0.0;

                    for (SliderFieldStateDTO sliderFieldStateDTO : formState.getSlider()) {
                        if (sliderFieldStateDTO.getFieldNumber() == fieldNumber) {
                            value = sliderFieldStateDTO.getValue();
                            break;
                        }
                    }

                    Paragraph line = new Paragraph(
                            fieldNumber + ". " + currentField.getDescription() + " " + value,
                            standardFont
                    );
                    document.add(line);
                }

                if (currentField.getFieldType().equals(FieldType.TEXT)) {
                    String value = "";

                    for (TextFieldStateDTO textFieldStateDTO : formState.getText()) {
                        if (textFieldStateDTO.getFieldNumber() == fieldNumber) {
                            value = textFieldStateDTO.getValue();
                            break;
                        }
                    }

                    Paragraph line = new Paragraph(
                            fieldNumber + ". " + currentField.getDescription() + " " + value,
                            standardFont
                    );
                    document.add(line);
                }

                fieldNumber++;
            }

        }
        catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    private Field findCurrentField(SchemaFieldsDTO schemaFields, int fieldNumber) {
        boolean isFound = false;
        Field currentField = null;

        for (ChoiceFieldDTO choiceField : schemaFields.getChoice()) {
            if (isFound) break;
            if (choiceField.getFieldNumber() == fieldNumber) {
                currentField = new Field(choiceField.getDescription(), FieldType.CHOICE);
                isFound = true;
            }
        }
        for (SimpleFieldDTO simpleField : schemaFields.getSimple()) {
            if (isFound) break;
            if (simpleField.getFieldNumber() == fieldNumber) {
                currentField = new Field(simpleField.getDescription(), FieldType.SIMPLE);
                isFound = true;
            }
        }
        for (SliderFieldDTO sliderField : schemaFields.getSlider()) {
            if (isFound) break;
            if (sliderField.getFieldNumber() == fieldNumber) {
                currentField = new Field(sliderField.getDescription(), FieldType.SLIDER);
                isFound = true;
            }
        }
        for (TextFieldDTO textField : schemaFields.getText()) {
            if (isFound) break;
            if (textField.getFieldNumber() == fieldNumber) {
                currentField = new Field(textField.getDescription(), FieldType.TEXT);
                isFound = true;
            }
        }

        return currentField;
    }

    private Font createTitleFont(int size) {
        try {
            BaseFont baseFont = BaseFont.createFont("aller_bold.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            return new Font(baseFont, size);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private Font createStandardFont(int size) {
        try {
            BaseFont baseFont = BaseFont.createFont("aller_regular.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            return new Font(baseFont, size);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
