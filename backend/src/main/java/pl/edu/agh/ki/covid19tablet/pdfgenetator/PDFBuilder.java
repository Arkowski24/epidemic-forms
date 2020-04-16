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

    private final static int signatureWidth = 120;
    private final static int signatureHeight = 90;

    private Font hospitalNameFont;
    private Font titleFont;
    private Font standardFont;
    private Font answerInTableFont;
    private Font answerHighlightedFont;
    private Font subquestionFont;
    private Font personalDataFont;
    private Font answerFont;

    private String dirPath;

    public PDFBuilder(String dirPath) throws DocumentException, IOException {
        this.hospitalNameFont = createRegularFont(15);
        this.titleFont = createBoldFont(20);
        this.standardFont = createRegularFont(10);
        this.answerInTableFont = createItalicLightFont(8);
        this.answerHighlightedFont = createItalicBoldFont(8);
        this.subquestionFont = createItalicBoldFont(10);
        this.personalDataFont = createItalicLightFont(10);
        this.answerFont = createItalicLightFont(10);

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

    private void addAllQuestions(Document document, QuestionContainer questionContainer) throws DocumentException {
        List<Question> questions = questionContainer.getQuestions();
        List<ComplexQuestion> complexQuestions = questionContainer.getComplexQuestions();

        addQuestions(document, questions, 0, questions.size() - 1);
        addEmptyLine(document, standardFont);
        addComplexQuestions(document, complexQuestions, 0, complexQuestions.size());
        addQuestions(document, questions, questions.size() - 1, questions.size());
    }

    private void addQuestions(
            Document document,
            List<Question> questions,
            int beg,
            int end
    ) throws DocumentException {

        questions.sort((final Question a, final Question b) -> a.getFieldNumber() - b.getFieldNumber());

        for (int i = beg; i < end; i++) {
            if (questions.get(i).isInTable()) {
                float[] widths = {0.85f, 0.15f};
                PdfPTable questionTable = new PdfPTable(widths);
                questionTable.setTotalWidth(PageSize.A4.getWidth() * 0.88f);    // xDDDD
                questionTable.setLockedWidth(true);

                PdfPCell questionCell = new PdfPCell(new Phrase(questions.get(i).getTitle(), standardFont));
                PdfPCell answerCell = new PdfPCell(new Phrase("    " + questions.get(i).getAnswer(), answerInTableFont));
                if (questions.get(i).isHighlighted())
                    answerCell = new PdfPCell(new Phrase("    " + questions.get(i).getAnswer() + " (!)", answerHighlightedFont));

                questionCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                answerCell.setHorizontalAlignment(Element.ALIGN_CENTER);

                questionTable.addCell(questionCell);
                questionTable.addCell(answerCell);

                document.add(questionTable);
            }
            else {
                Paragraph questionParagraph = new Paragraph(questions.get(i).getTitle(), standardFont);
                Paragraph answerParagraph = new Paragraph("    " + questions.get(i).getAnswer(), answerFont);
                if (questions.get(i).isHighlighted())
                    answerParagraph = new Paragraph("    " + questions.get(i).getAnswer() + " (!)", answerHighlightedFont);

                questionParagraph.setSpacingBefore(10);

                document.add(questionParagraph);
                document.add(answerParagraph);
            }
        }
    }

    private void addComplexQuestions(
            Document document,
            List<ComplexQuestion> complexQuestions,
            int beg,
            int end
    ) throws DocumentException {

        complexQuestions.sort((final ComplexQuestion a, final ComplexQuestion b) -> a.getFieldNumber() - b.getFieldNumber());

        for (int i = beg; i < end; i++) {
            PdfPTable questionTable;
            if (complexQuestions.get(i).getAnswer().equals("")){
                float[] widths = {1.0f};
                questionTable = new PdfPTable(widths);
            }
            else {
                float[] widths = {0.85f, 0.15f};
                questionTable = new PdfPTable(widths);
            }
            questionTable.setTotalWidth(PageSize.A4.getWidth() * 0.88f);
            questionTable.setLockedWidth(true);
            PdfPCell questionCell = new PdfPCell(new Phrase(complexQuestions.get(i).getTitle(), standardFont));
            PdfPCell answerCell = new PdfPCell(new Phrase(complexQuestions.get(i).getAnswer(), answerInTableFont));
            if (complexQuestions.get(i).isComplex()) {
                answerCell = new PdfPCell(new Phrase("    " + complexQuestions.get(i).getAnswer() + " (!)", answerHighlightedFont));
            }
            questionCell.setHorizontalAlignment(Element.ALIGN_LEFT);
            answerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            questionTable.addCell(questionCell);
            if (!complexQuestions.get(i).getAnswer().equals(""))
                questionTable.addCell(answerCell);
            document.add(questionTable);

            if (complexQuestions.get(i).isComplex()) {
                for (int j = 0; j < complexQuestions.get(i).getSubanwsers().size(); j++) {
                    float[] subwidths = {0.3f, 0.7f};
                    PdfPTable subquestionTable = new PdfPTable(subwidths);
                    subquestionTable.setTotalWidth(PageSize.A4.getWidth() * 0.88f);
                    subquestionTable.setLockedWidth(true);
                    PdfPCell subquestionCell = new PdfPCell(new Phrase(complexQuestions.get(i).getSubtitles().get(j), subquestionFont));
                    PdfPCell subanswerCell = new PdfPCell(new Phrase(complexQuestions.get(i).getSubanwsers().get(j), answerInTableFont));
                    if (complexQuestions.get(i).getSubanwsers().get(j).equals("TAK"))
                        subanswerCell = new PdfPCell(new Phrase("TAK (!)", answerHighlightedFont));
                    subquestionCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    subanswerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    subquestionTable.addCell(subquestionCell);
                    subquestionTable.addCell(subanswerCell);
                    document.add(subquestionTable);
                }
            }
        }
    }

    private void addSignatures(Document document, SignaturesContainer signaturesContainer)
            throws DocumentException, IOException {
        addEmptyLine(document, answerFont);

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
