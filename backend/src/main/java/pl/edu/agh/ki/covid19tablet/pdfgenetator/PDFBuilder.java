package pl.edu.agh.ki.covid19tablet.pdfgenetator;

import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import pl.edu.agh.ki.covid19tablet.form.Form;
import pl.edu.agh.ki.covid19tablet.form.signature.Signature;
import pl.edu.agh.ki.covid19tablet.pdfgenetator.converters.BooleanConverter;
import pl.edu.agh.ki.covid19tablet.pdfgenetator.converters.ConvertedBoolean;
import pl.edu.agh.ki.covid19tablet.schema.fields.*;
import pl.edu.agh.ki.covid19tablet.schema.fields.TextField;
import pl.edu.agh.ki.covid19tablet.state.FormState;
import pl.edu.agh.ki.covid19tablet.state.fields.ChoiceFieldState;
import pl.edu.agh.ki.covid19tablet.state.fields.DerivedFieldState;
import pl.edu.agh.ki.covid19tablet.state.fields.SliderFieldState;
import pl.edu.agh.ki.covid19tablet.state.fields.TextFieldState;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class PDFBuilder {

    private final static int signatureWidth = 160;
    private final static int signatureHeight = 120;
    private final static String signatureEmployeeName = "employeeSignature.jpg";
    private final static String signaturePatientName = "patientSignature.jpg";

    private Font titleFont;
    private Font questionFont;
    private Font choiceQuestionFont;
    private Font answerFont;

    private String dirPath;

    public PDFBuilder(String dirPath) throws DocumentException, IOException{
        this.titleFont = createTitleFont(20);
        this.questionFont = createQuestionFont(12);
        this.choiceQuestionFont = createQuestionFont(10);
        this.answerFont = createAnswerFont(10);

        this.dirPath = dirPath;
    }

    public void build(String name, String creationDate, Form form) throws DocumentException, IOException {
        Document document = new Document();

        Path savingPath = Paths.get(dirPath, name);

        PdfWriter writer = PdfWriter.getInstance(document, Files.newOutputStream(savingPath));
        document.open();

        addCreationDate(document, creationDate);
        addDerivedData(document, form.getSchema().getFields().getDerived(), form.getState().getDerived());
        addTitle(document, form.getFormName());
        addQuestions(document, form.getSchema().getFields(), form.getState());
        addSignatures(
                document,
                form.getEmployeeSignature(),
                form.getPatientSignature(),
                form.getSchema().getEmployeeSignature().getTitle(),
                form.getSchema().getPatientSignature().getTitle()
        );

        document.close();
        writer.close();
    }

    private void addCreationDate(Document document, String creationDate) throws DocumentException {
        document.addCreationDate();

        Paragraph creationDateParagraph = new Paragraph(creationDate, questionFont);
        creationDateParagraph.setAlignment(Element.ALIGN_RIGHT);
        document.add(creationDateParagraph);
    }

    private void addDerivedData(
            Document document,
            List<DerivedField> derivedFields,
            List<DerivedFieldState> derivedFieldStates
    ) throws DocumentException {

        addEmptyLine(document, questionFont);

        for (int i = 0 ; i < derivedFields.size() && i < derivedFieldStates.size(); i++) {
            List<String> derivedTitles = derivedFields.get(i).getTitles();
            List<String> derivedValues = derivedFieldStates.get(i).getValue();

            for (int j = 0; j < derivedTitles.size() && j < derivedValues.size(); j++) {
                String line = derivedTitles.get(j) + ": " + derivedValues.get(j);
                Paragraph derivedParagraph = new Paragraph(line, questionFont);
                derivedParagraph.setAlignment(Element.ALIGN_RIGHT);
                document.add(derivedParagraph);
            }

            addEmptyLine(document, questionFont);
        }
    }

    private void addTitle(Document document, String formName) throws DocumentException {
        Paragraph title = new Paragraph(formName, titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        title.setSpacingAfter(15);
        document.add(title);
    }

    private void addQuestions(
            Document document,
            SchemaFields schemaFields,
            FormState formState
        ) throws DocumentException {

        int fieldNumber = 0;
        int questionNumber = 1;
        boolean isWritten;

        while (true) {
            Field currentField = findCurrentField(schemaFields, fieldNumber);
            if (currentField == null) {
                return;
            }

            isWritten = true;

            if (currentField.getFieldType().equals(FieldType.CHOICE)) {
                Paragraph title = new Paragraph(
                        questionNumber + ". " + currentField.getTitle(),
                        questionFont
                );
                document.add(title);

                List<ConvertedBoolean> answers = new ArrayList<>();
                List<String> questions = new ArrayList<>();

                for (ChoiceFieldState choiceFieldState : formState.getChoice()) {
                    if (choiceFieldState.getField().getFieldNumber() == fieldNumber) {
                        BooleanConverter converter = new BooleanConverter();
                        answers = converter.convert(choiceFieldState.getValue());
                        break;
                    }
                }
                for (ChoiceField choiceField : schemaFields.getChoice()) {
                    if (choiceField.getFieldNumber() == fieldNumber) {
                        questions = choiceField.getChoices();
                        break;
                    }
                }

                for (int i = 0; i < answers.size() && i < questions.size(); i++) {
                    String question = "    " + questions.get(i);
                    String answer = " " + answers.get(i);

                    Chunk chunkQuestion = new Chunk(question, choiceQuestionFont);
                    Chunk chunkAnswer = new Chunk(answer, answerFont);

                    Paragraph paragraph = new Paragraph();
                    paragraph.add(chunkQuestion);
                    paragraph.add(chunkAnswer);

                    document.add(paragraph);
                }
            }

            if (currentField.getFieldType().equals(FieldType.SIMPLE)) {
                questionNumber--;
                isWritten = false;
            }

            if (currentField.getFieldType().equals(FieldType.SLIDER)) {
                double value = 0.0;

                for (SliderFieldState sliderFieldState : formState.getSlider()) {
                    if (sliderFieldState.getField().getFieldNumber() == fieldNumber) {
                        value = sliderFieldState.getValue();
                        break;
                    }
                }

                Paragraph question = new Paragraph(
                        questionNumber + ". " + currentField.getTitle(),
                        questionFont
                );
                Paragraph answer = new Paragraph(
                        "    " + value,
                        answerFont
                );

                document.add(question);
                document.add(answer);
            }

            if (currentField.getFieldType().equals(FieldType.TEXT)) {
                String value = "";

                for (TextFieldState textFieldState : formState.getText()) {
                    if (textFieldState.getField().getFieldNumber() == fieldNumber) {
                        value = textFieldState.getValue();
                        break;
                    }
                }

                Paragraph question = new Paragraph(
                        questionNumber + ". " + currentField.getTitle(),
                        questionFont
                );
                Paragraph answer = new Paragraph(
                        "    " + value,
                        answerFont
                );

                document.add(question);
                document.add(answer);
            }

            if (isWritten) addEmptyLine(document, questionFont);

            fieldNumber++;
            questionNumber++;
        }
    }

    private void addSignatures(
            Document document,
            Signature employeeSignature,
            Signature patientSignature,
            String employeeSignatureTitle,
            String patientSignatureTitle
    ) throws DocumentException, IOException  {

        resizeSignature(employeeSignature, signatureEmployeeName);
        resizeSignature(patientSignature, signaturePatientName);

        PdfPTable imageTable = new PdfPTable(2);
        imageTable.setTotalWidth(document.getPageSize().getWidth());
        imageTable.addCell(getImageCell(Image.getInstance(signatureEmployeeName)));
        imageTable.addCell(getImageCell(Image.getInstance(signaturePatientName)));
        document.add(imageTable);

        PdfPTable titleTable = new PdfPTable(2);
        titleTable.setTotalWidth(document.getPageSize().getWidth());
        titleTable.addCell(getTitleCell(new Phrase(employeeSignatureTitle, questionFont)));
        titleTable.addCell(getTitleCell(new Phrase(patientSignatureTitle, questionFont)));
        document.add(titleTable);
    }

    private void resizeSignature(Signature signature, String name) throws IOException {
        byte[] signatureData = signature.getValue();
        ByteArrayInputStream signatureStream = new ByteArrayInputStream(signatureData);
        BufferedImage signatureImage = ImageIO.read(signatureStream);
        BufferedImage signatureImageResized = new BufferedImage(signatureWidth, signatureHeight, BufferedImage.TYPE_INT_RGB);

        Graphics graphicsEmployee = signatureImageResized.createGraphics();
        graphicsEmployee.drawImage(signatureImage, 0, 0 , signatureWidth, signatureHeight, null);
        graphicsEmployee.dispose();

        ImageIO.write(signatureImageResized, "jpg", new File(name) );
    }

    private PdfPCell getImageCell(Image image) {
        PdfPCell cell = new PdfPCell(image);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setBorder(Rectangle.NO_BORDER);
        return cell;
    }

    private PdfPCell getTitleCell(Phrase phrase) {
        PdfPCell cell = new PdfPCell(phrase);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setBorder(Rectangle.NO_BORDER);
        return cell;
    }

    private Field findCurrentField(SchemaFields schemaFields, int fieldNumber) {
        boolean isFound = false;
        Field currentField = null;

        for (ChoiceField choiceField : schemaFields.getChoice()) {
            if (isFound) break;
            if (choiceField.getFieldNumber() == fieldNumber) {
                currentField = new Field(choiceField.getTitle(), FieldType.CHOICE);
                isFound = true;
            }
        }
        for (DerivedField derivedField : schemaFields.getDerived()) {
            if (isFound) break;
            if (derivedField.getFieldNumber() == fieldNumber) {
                currentField = new Field("", FieldType.DERIVED);
                isFound = true;
            }
        }
        for (SimpleField simpleField : schemaFields.getSimple()) {
            if (isFound) break;
            if (simpleField.getFieldNumber() == fieldNumber) {
                currentField = new Field(simpleField.getTitle(), FieldType.SIMPLE);
                isFound = true;
            }
        }
        for (SliderField sliderField : schemaFields.getSlider()) {
            if (isFound) break;
            if (sliderField.getFieldNumber() == fieldNumber) {
                currentField = new Field(sliderField.getTitle(), FieldType.SLIDER);
                isFound = true;
            }
        }
        for (TextField textField : schemaFields.getText()) {
            if (isFound) break;
            if (textField.getFieldNumber() == fieldNumber) {
                currentField = new Field(textField.getTitle(), FieldType.TEXT);
                isFound = true;
            }
        }

        return currentField;
    }

    private void addEmptyLine(Document document, Font font) throws DocumentException {
        Paragraph emptyLine = new Paragraph(" ", font);
        document.add(emptyLine);
    }

    private Font createTitleFont(int size) throws DocumentException, IOException {
        BaseFont baseFont = BaseFont.createFont("aller_bold.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        return new Font(baseFont, size);
    }

    private Font createQuestionFont(int size) throws DocumentException, IOException {
        BaseFont baseFont = BaseFont.createFont("aller_regular.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        return new Font(baseFont, size);
    }

    private Font createAnswerFont(int size) throws DocumentException, IOException {
        BaseFont baseFont = BaseFont.createFont("aller_italic_light.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        return new Font(baseFont, size);
    }
}
