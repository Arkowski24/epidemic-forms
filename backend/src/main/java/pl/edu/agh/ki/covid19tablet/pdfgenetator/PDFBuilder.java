package pl.edu.agh.ki.covid19tablet.pdfgenetator;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import pl.edu.agh.ki.covid19tablet.pdfgenetator.containers.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class PDFBuilder {

    private final static int hospitalLogoWidth = 30;
    private final static int hospitalLogoHeight = 30;

    private final static int signatureWidth = 160;
    private final static int signatureHeight = 120;

    private Font hospitalNameFont;
    private Font titleFont;
    private Font standardFont;
    private Font answerInTableFont;
    private Font answerHighlightedFont;
    private Font personalDataFont;
    private Font answerFont;

    private String dirPath;

    public PDFBuilder(String dirPath) throws DocumentException, IOException {
        this.hospitalNameFont = createRegularFont(15);
        this.titleFont = createBoldFont(20);
        this.standardFont = createRegularFont(10);
        this.answerInTableFont = createItalicFont(8);
        this.answerHighlightedFont = createItalicBoldFont(8);
        this.personalDataFont = createItalicFont(10);
        this.answerFont = createItalicFont(10);

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
        addQuestions(document, formKeyData.getQuestions());
        addSignatures(document, formKeyData.getSignatures());

        document.close();
        writer.close();
    }

    private void addHospitalName(Document document, String hospitalName) throws DocumentException, IOException {
        Image hospitalLogo = Image.getInstance(PDFBuilder.class.getResourceAsStream("/hospital_logo.png").readAllBytes());
        hospitalLogo.scaleAbsolute(hospitalLogoWidth, hospitalLogoHeight);

        Chunk hospitalLogoChunk = new Chunk(hospitalLogo, 0, 0);
        Chunk hospitalNameChunk = new Chunk("  " + hospitalName, hospitalNameFont);
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
        Paragraph deviceParagraph = new Paragraph(
                metadataContainer.getUsedDeviceTitle()
                + ": "
                + metadataContainer.getUsedDevice(),
                standardFont
        );
        document.add(deviceParagraph);

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

        addEmptyLine(document, standardFont);
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
        addEmptyLine(document, standardFont);
    }

    private void addQuestions(Document document, QuestionContainer questionContainer) throws DocumentException {
        List<ComplexQuestion> complexQuestions = questionContainer.getComplexQuestions();
        complexQuestions.sort((final ComplexQuestion a, final ComplexQuestion b) -> a.getFieldNumber() - b.getFieldNumber());

        for(ComplexQuestion question : complexQuestions) {
            float[] widths = {0.85f, 0.15f};
            PdfPTable questionTable = new PdfPTable(widths);
            questionTable.setTotalWidth(PageSize.A4.getWidth() * 0.88f);
            questionTable.setLockedWidth(true);
            PdfPCell questionCell = new PdfPCell(new Phrase(question.getTitle(), standardFont));
            PdfPCell answerCell = new PdfPCell(new Phrase(question.getAnswer(), answerInTableFont));
            if (question.isHighlighted()) {
                answerCell = new PdfPCell(new Phrase("    " + question.getAnswer() + " (!)", answerHighlightedFont));
            }
            questionCell.setHorizontalAlignment(Element.ALIGN_LEFT);
            answerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            questionTable.addCell(questionCell);
            questionTable.addCell(answerCell);
            document.add(questionTable);

            if (question.isHighlighted()) {
                for (int i = 0; i < question.getSubanwsers().size(); i++) {
                    float[] subwidths = {0.3f, 0.7f};
                    PdfPTable subquestionTable = new PdfPTable(subwidths);
                    subquestionTable.setTotalWidth(PageSize.A4.getWidth() * 0.88f);
                    subquestionTable.setLockedWidth(true);
                    PdfPCell subquestionCell = new PdfPCell(new Phrase(question.getSubtitles().get(i), standardFont));
                    PdfPCell subanswerCell = new PdfPCell(new Phrase(question.getSubanwsers().get(i), answerInTableFont));
                    subquestionCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                    subanswerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    subquestionTable.addCell(subquestionCell);
                    subquestionTable.addCell(subanswerCell);
                    document.add(subquestionTable);
                }
            }
        }

        List<Question> questions = questionContainer.getQuestions();
        questions.sort((final Question a, final Question b) -> a.getFieldNumber() - b.getFieldNumber());

        for (Question question : questions) {
            if (question.isInTable()) {
                float[] widths = {0.85f, 0.15f};
                PdfPTable questionTable = new PdfPTable(widths);
                questionTable.setTotalWidth(PageSize.A4.getWidth() * 0.88f);    // xDDDD
                questionTable.setLockedWidth(true);

                PdfPCell questionCell = new PdfPCell(new Phrase(question.getTitle(), standardFont));
                PdfPCell answerCell = new PdfPCell(new Phrase("    " + question.getAnswer(), answerInTableFont));
                if (question.isHighlighted())
                    answerCell = new PdfPCell(new Phrase("    " + question.getAnswer() + " (!)", answerHighlightedFont));

                questionCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                answerCell.setHorizontalAlignment(Element.ALIGN_CENTER);

                questionTable.addCell(questionCell);
                questionTable.addCell(answerCell);

                document.add(questionTable);
            }
            else {
                Paragraph questionParagraph = new Paragraph(question.getTitle(), standardFont);
                Paragraph answerParagraph = new Paragraph("    " + question.getAnswer(), answerFont);
                if (question.isHighlighted())
                    answerParagraph = new Paragraph("    " + question.getAnswer() + " (!)", answerHighlightedFont);

                questionParagraph.setSpacingBefore(10);

                document.add(questionParagraph);
                document.add(answerParagraph);
            }
        }
    }

    private void addSignatures(Document document, SignaturesContainer signaturesContainer)
            throws DocumentException, IOException {
        addEmptyLine(document, answerFont);

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
        titleTable.addCell(getTitleCell(new Phrase(signaturesContainer.getEmployeeSignatureTitle(), standardFont)));
        titleTable.addCell(getTitleCell(new Phrase(signaturesContainer.getPatientSignatureTitle(), standardFont)));
        document.add(titleTable);

        PdfPTable employeeNameTable = new PdfPTable(2);
        employeeNameTable.setTotalWidth(document.getPageSize().getWidth());
        employeeNameTable.addCell(getTitleCell(new Phrase(signaturesContainer.getEmployeeFullName(), standardFont)));
        employeeNameTable.addCell(getTitleCell(new Phrase("", standardFont)));
        document.add(employeeNameTable);
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
        BaseFont baseFont = BaseFont.createFont("aller_italic_light.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        return new Font(baseFont, size);
    }
    private Font createItalicBoldFont(int size) throws DocumentException, IOException {
        BaseFont baseFont = BaseFont.createFont("aller_italic_bold.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        return new Font(baseFont, size);
    }
}
