package pl.edu.agh.ki.covid19tablet.pdfgenetator;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.agh.ki.covid19tablet.FormNotFoundException;
import pl.edu.agh.ki.covid19tablet.form.Form;
import pl.edu.agh.ki.covid19tablet.form.FormKt;
import pl.edu.agh.ki.covid19tablet.form.FormRepository;
import pl.edu.agh.ki.covid19tablet.form.dto.FormDTO;
import pl.edu.agh.ki.covid19tablet.schema.fields.*;
import pl.edu.agh.ki.covid19tablet.schema.fields.dto.*;
import pl.edu.agh.ki.covid19tablet.state.FormState;
import pl.edu.agh.ki.covid19tablet.state.dto.FormStateDTO;
import pl.edu.agh.ki.covid19tablet.state.fields.ChoiceFieldState;
import pl.edu.agh.ki.covid19tablet.state.fields.ChoiceFieldStateKt;
import pl.edu.agh.ki.covid19tablet.state.fields.TextFieldState;
import pl.edu.agh.ki.covid19tablet.state.fields.dto.ChoiceFieldStateDTO;
import pl.edu.agh.ki.covid19tablet.state.fields.dto.SliderFieldStateDTO;
import pl.edu.agh.ki.covid19tablet.state.fields.dto.TextFieldStateDTO;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PDFGeneratorService {

    @Autowired
    FormRepository formRepository;

    public FormDTO getForm(long id) {
        Optional<Form> form = formRepository.findById(id);
        if (form.isPresent()) {
            return FormKt.toDTO(form.get());
        }

        throw new FormNotFoundException();
    }

    public byte[] generatePDF(FormDTO formDTO) {
        Document document = new Document();
        try {
            BaseFont baseFont = BaseFont.createFont("aller.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            Font font = new Font(baseFont, 15);
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("Covid-19Form.pdf"));
            document.open();
            document.addCreationDate();

            addTitle(formDTO, document, font);
            addQuestions(formDTO.getSchema().getFields(), formDTO.getState(), document, font);

            document.close();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private void addTitle(FormDTO formDTO, Document document, Font font) {
        try {
            Paragraph title = new Paragraph(formDTO.getFormName(), font);
            document.add(title);

        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    private void addQuestions(
            SchemaFieldsDTO schemaFields,
            FormStateDTO formState,
            Document document,
            Font font) {
        try {
            while (true) {
                int fieldNumber = 0;
                Field currentField = findCurrentField(schemaFields, fieldNumber);
                if (currentField == null) {
                    return;
                }

                if (currentField.getFieldType().equals(FieldType.CHOICE)) {
                    Paragraph title = new Paragraph(currentField.getDescription(), font);
                    document.add(title);

                    List<Boolean> answers = new ArrayList<>();
                    List<String> questions = new ArrayList<>();

                    for (ChoiceFieldStateDTO choiceFieldStateDTO : formState.getChoice()) {
                        if (choiceFieldStateDTO.getFieldNumber() == fieldNumber) {
                            answers = choiceFieldStateDTO.getValue();
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
                        Paragraph paragraph = new Paragraph(line, font);
                        document.add(paragraph);
                    }
                }

                if (currentField.getFieldType().equals(FieldType.SIMPLE)) {
                    Paragraph title = new Paragraph(currentField.getDescription(), font);
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

                    Paragraph line = new Paragraph(currentField.getDescription() + " " + value, font);
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

                    Paragraph line = new Paragraph(currentField.getDescription() + " " + value, font);
                    document.add(line);
                }
            }

        } catch (DocumentException e) {
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
}
