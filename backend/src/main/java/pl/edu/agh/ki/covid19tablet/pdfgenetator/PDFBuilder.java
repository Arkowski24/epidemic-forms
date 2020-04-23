package pl.edu.agh.ki.covid19tablet.pdfgenetator;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import pl.edu.agh.ki.covid19tablet.pdfgenetator.containers.MetadataContainer;
import pl.edu.agh.ki.covid19tablet.pdfgenetator.containers.SignaturesContainer;
import pl.edu.agh.ki.covid19tablet.pdfgenetator.containers.personaldata.PersonalData;
import pl.edu.agh.ki.covid19tablet.pdfgenetator.containers.personaldata.PersonalDataContainer;
import pl.edu.agh.ki.covid19tablet.pdfgenetator.containers.question.QuestionContainer;
import pl.edu.agh.ki.covid19tablet.pdfgenetator.containers.question.complex.ComplexQuestion;
import pl.edu.agh.ki.covid19tablet.pdfgenetator.containers.question.complex.SubQuestion;
import pl.edu.agh.ki.covid19tablet.pdfgenetator.containers.question.simple.SimpleQuestion;
import pl.edu.agh.ki.covid19tablet.pdfgenetator.containers.question.text.TextQuestion;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;

public class PDFBuilder {

    private final static int hospitalLogoWidth = 30;
    private final static int hospitalLogoHeight = 30;

    private final static int signatureWidth = 80;
    private final static int signatureHeight = 60;

    private Font hospitalNameFont;
    private Font titleFont;
    private Font standardFont;
    private Font personalDataFont;
    private Font subQuestionFont;
    private Font answerFont;
    private Font answerHighlightedFont;
    private Font additionalInfoFont;
    private Font emptyLineFont;

    private String dirPath;

    public PDFBuilder(String dirPath) throws DocumentException, IOException {
        this.hospitalNameFont = createRegularFont(13);
        this.titleFont = createBoldFont(17);
        this.standardFont = createRegularFont(9);
        this.personalDataFont = createItalicLightFont(9);
        this.subQuestionFont = createItalicFont(9);
        this.answerFont = createItalicLightFont(7);
        this.answerHighlightedFont = createItalicBoldFont(7);
        this.additionalInfoFont = createItalicLightFont(9);
        this.emptyLineFont = createRegularFont(11);

        this.dirPath = dirPath;
    }

    public void build(String name, FormKeyData formKeyData) throws DocumentException, IOException {
        Document document = new Document();

        Path savingPath = Paths.get(dirPath, name);

        PdfWriter writer = PdfWriter.getInstance(document, Files.newOutputStream(savingPath));
        document.open();

        addHospitalName(document, formKeyData.getHospitalName());
        addTitle(document, formKeyData.getTitle());
        addMetadata(document, formKeyData.getMetadata());
        addPersonalData(document, formKeyData.getPersonalData());
        addAllQuestions(document, formKeyData.getQuestions());
        addSignatures(document, formKeyData.getSignatures());

        document.close();
        writer.close();
    }

    private void addHospitalName(Document document, String hospitalName) throws DocumentException, IOException {
        Image hospitalLogo = Image.getInstance(PDFBuilder.class.getResourceAsStream("/hospital_logo.png").readAllBytes());
        hospitalLogo.scaleAbsolute(hospitalLogoWidth, hospitalLogoHeight);

        Chunk hospitalLogoChunk = new Chunk(hospitalLogo, 0, 0);
        Chunk hospitalNameChunk = new Chunk(hospitalName, hospitalNameFont);
        Paragraph hospitalNameParagraph = new Paragraph();
        Paragraph hospitalLogoParagraph = new Paragraph();
        hospitalNameParagraph.add(hospitalNameChunk);
        hospitalLogoParagraph.add(hospitalLogoChunk);
        hospitalNameParagraph.setAlignment(Element.ALIGN_CENTER);
        hospitalLogoParagraph.setAlignment(Element.ALIGN_CENTER);

        document.add(hospitalLogoParagraph);
        document.add(hospitalNameParagraph);
    }

    private void addTitle(Document document, String formName) throws DocumentException {
        Paragraph title = new Paragraph(formName, titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        title.setSpacingAfter(20);
        document.add(title);
    }

    private void addMetadata(Document document, MetadataContainer metadataContainer) throws DocumentException {
        Paragraph usedDeviceParagraph = new Paragraph(
                metadataContainer.getUsedDeviceTitle()
                + ": "
                + metadataContainer.getUsedDevice(),
                standardFont
        );
        document.add(usedDeviceParagraph);

        Paragraph schemaNameParagraph = new Paragraph(
                metadataContainer.getSchemaNameTitle()
                        + ": "
                        + metadataContainer.getSchemaName(),
                standardFont
        );
        document.add(schemaNameParagraph);

        Paragraph creationDateParagraph = new Paragraph(
                metadataContainer.getCreationDateTitle()
                + ": "
                + metadataContainer.getCreationDate(),
                standardFont
        );
        document.add(creationDateParagraph);

        Paragraph purposeOfVisitParagraph = new Paragraph(
                metadataContainer.getPurposeOfVisitTitle()
                        + ": "
                        + metadataContainer.getPurposeOfVisit(),
                standardFont
        );
        document.add(purposeOfVisitParagraph);

        addEmptyLine(document, emptyLineFont);
    }

    private void addPersonalData(Document document, PersonalDataContainer personalDataContainer)
            throws DocumentException {
        for (PersonalData personalData : personalDataContainer.getPersonalDataList()) {
            Chunk titleChunk = new Chunk(personalData.getTitle(), standardFont);
            Chunk valueChunk = new Chunk(personalData.getValue(), personalDataFont);

            Paragraph personalDataParagraph = new Paragraph();
            personalDataParagraph.add(titleChunk);
            personalDataParagraph.add(" ");
            personalDataParagraph.add(valueChunk);

            document.add(personalDataParagraph);
        }
        addEmptyLine(document, emptyLineFont);
    }

    private void addAllQuestions(Document document, QuestionContainer questionContainer) throws DocumentException {
        List<SimpleQuestion> simpleQuestions = questionContainer.getSimpleQuestions();
        List<ComplexQuestion> complexQuestions = questionContainer.getComplexQuestions();
        List<TextQuestion> textQuestions = questionContainer.getTextQuestions();

        addSimpleQuestions(document, simpleQuestions);
        addEmptyLine(document, emptyLineFont);
        addComplexQuestions(document, complexQuestions);
        addEmptyLine(document, emptyLineFont);
        addTextQuestions(document, textQuestions);
    }

    private void addSimpleQuestions(
            Document document,
            List<SimpleQuestion> simpleQuestions
    ) throws DocumentException {

        simpleQuestions.sort(Comparator.comparingInt(SimpleQuestion::getFieldNumber));

        for (SimpleQuestion question : simpleQuestions) {
            float[] widths = {0.85f, 0.15f};
            PdfPTable questionTable = new PdfPTable(widths);
            questionTable.setTotalWidth(PageSize.A4.getWidth() * 0.88f);
            questionTable.setLockedWidth(true);

            PdfPCell questionCell = new PdfPCell(new Phrase(question.getTitle(), standardFont));
            PdfPCell answerCell = new PdfPCell(new Phrase(question.getAnswer(), answerFont));
            if (question.isHighlighted())
                answerCell = new PdfPCell(new Phrase(question.getAnswer() + " (!)", answerHighlightedFont));

            questionCell.setHorizontalAlignment(Element.ALIGN_LEFT);
            answerCell.setHorizontalAlignment(Element.ALIGN_CENTER);

            questionTable.addCell(questionCell);
            questionTable.addCell(answerCell);

            document.add(questionTable);
        }
    }

    private void addComplexQuestions(
            Document document,
            List<ComplexQuestion> complexQuestions
    ) throws DocumentException {

        complexQuestions.sort(Comparator.comparingInt(ComplexQuestion::getFieldNumber));

        for (ComplexQuestion question : complexQuestions) {
            PdfPTable questionTable;
            float[] widths;
            if (question.getAnswer().equals("")) {
                widths = new float[]{1.0f};
            } else {
                widths = new float[]{0.85f, 0.15f};
            }
            questionTable = new PdfPTable(widths);
            questionTable.setTotalWidth(PageSize.A4.getWidth() * 0.88f);
            questionTable.setLockedWidth(true);
            PdfPCell questionCell = new PdfPCell(new Phrase(question.getTitle(), standardFont));
            PdfPCell answerCell = new PdfPCell(new Phrase(question.getAnswer(), answerFont));
            if (question.getAnswer().equals("TAK")) {
                answerCell = new PdfPCell(new Phrase(question.getAnswer() + " (!)", answerHighlightedFont));
            }
            questionCell.setHorizontalAlignment(Element.ALIGN_LEFT);
            answerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            questionTable.addCell(questionCell);

            if (!question.getAnswer().equals(""))
                questionTable.addCell(answerCell);
            document.add(questionTable);

            for (SubQuestion subQuestion : question.getSubQuestions()) {
                float[] subWidths = {0.3f, 0.7f};
                PdfPTable subQuestionTable = new PdfPTable(subWidths);
                subQuestionTable.setTotalWidth(PageSize.A4.getWidth() * 0.88f);
                subQuestionTable.setLockedWidth(true);
                PdfPCell subQuestionCell = new PdfPCell(new Phrase(subQuestion.getTitle(), subQuestionFont));
                PdfPCell subAnswerCell = new PdfPCell(new Phrase(subQuestion.getAnswer(), answerFont));
                if (subQuestion.isHighlighted() && subQuestion.isWithExclamationMark()) {
                    subAnswerCell = new PdfPCell(new Phrase(subQuestion.getAnswer() + " (!)", answerHighlightedFont));
                }
                else if (subQuestion.isHighlighted())
                    subAnswerCell = new PdfPCell(new Phrase(subQuestion.getAnswer(), answerHighlightedFont));
                subQuestionCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                subAnswerCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                subQuestionTable.addCell(subQuestionCell);
                subQuestionTable.addCell(subAnswerCell);
                document.add(subQuestionTable);
            }
        }
    }

    private void addTextQuestions(
            Document document,
            List<TextQuestion> textQuestions
    ) throws DocumentException {

        textQuestions.sort(Comparator.comparingInt(TextQuestion::getFieldNumber));

        for (TextQuestion question : textQuestions) {
            Paragraph questionParagraph = new Paragraph(question.getTitle() + ':', standardFont);
            Paragraph answerParagraph = new Paragraph("    " + question.getAnswer(), additionalInfoFont);
            document.add(questionParagraph);
            document.add(answerParagraph);
        }
    }

    private void addSignatures(Document document, SignaturesContainer signaturesContainer)
            throws DocumentException, IOException {
        addEmptyLine(document, emptyLineFont);

        PdfPTable describtionTable = new PdfPTable(2);
        describtionTable.setTotalWidth(document.getPageSize().getWidth());
        describtionTable.addCell(getTextCell(new Phrase("", standardFont)));
        describtionTable.addCell(getTextCell(new Phrase(signaturesContainer.getPatientSignatureDescription(), standardFont)));
        document.add(describtionTable);

        PdfPTable imageTable = new PdfPTable(2);
        imageTable.setTotalWidth(document.getPageSize().getWidth());
        ByteArrayInputStream signatureEmployee = new ByteArrayInputStream(signaturesContainer.getEmployeeSignature().getValue());
        ByteArrayInputStream signaturePatient = new ByteArrayInputStream(signaturesContainer.getPatientSignature().getValue());
        Image employeeSignatureImage = Image.getInstance(Image.getInstance(signatureEmployee.readAllBytes()));
        Image patientSignatureImage = Image.getInstance(Image.getInstance(signaturePatient.readAllBytes()));
        employeeSignatureImage.scaleAbsolute(signatureWidth, signatureHeight);
        patientSignatureImage.scaleAbsolute(signatureWidth, signatureHeight);
        imageTable.addCell(getImageCell(employeeSignatureImage));
        imageTable.addCell(getImageCell(patientSignatureImage));
        document.add(imageTable);

        PdfPTable titleTable = new PdfPTable(2);
        titleTable.setTotalWidth(document.getPageSize().getWidth());
        titleTable.addCell(getTextCell(new Phrase(signaturesContainer.getEmployeeSignatureTitle(), standardFont)));
        titleTable.addCell(getTextCell(new Phrase(signaturesContainer.getPatientSignatureTitle(), standardFont)));
        document.add(titleTable);
    }

    private PdfPCell getImageCell(Image image) {
        PdfPCell cell = new PdfPCell(image);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setBorder(Rectangle.NO_BORDER);
        return cell;
    }

    private PdfPCell getTextCell(Phrase phrase) {
        PdfPCell cell = new PdfPCell(phrase);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setBorder(Rectangle.NO_BORDER);
        return cell;
    }

    private void addEmptyLine(Document document, Font font) throws DocumentException {
        Paragraph emptyLine = new Paragraph(" ", font);
        document.add(emptyLine);
    }

    private Font createBoldFont(int size) throws DocumentException, IOException {
        BaseFont baseFont = BaseFont.createFont("aller_bold.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        return new Font(baseFont, size);
    }
    private Font createRegularFont(int size) throws DocumentException, IOException {
        BaseFont baseFont = BaseFont.createFont("aller_regular.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        return new Font(baseFont, size);
    }
    private Font createItalicFont(int size) throws DocumentException, IOException {
        BaseFont baseFont = BaseFont.createFont("aller_italic.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        return new Font(baseFont, size);
    }
    private Font createItalicLightFont(int size) throws DocumentException, IOException {
        BaseFont baseFont = BaseFont.createFont("aller_italic_light.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        return new Font(baseFont, size);
    }
    private Font createItalicBoldFont(int size) throws DocumentException, IOException {
        BaseFont baseFont = BaseFont.createFont("aller_italic_bold.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        return new Font(baseFont, size);
    }
}
