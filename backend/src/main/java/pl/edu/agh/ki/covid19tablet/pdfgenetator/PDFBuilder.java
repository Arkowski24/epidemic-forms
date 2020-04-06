package pl.edu.agh.ki.covid19tablet.pdfgenetator;

import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import pl.edu.agh.ki.covid19tablet.form.signature.Signature;
import pl.edu.agh.ki.covid19tablet.pdfgenetator.conteners.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class PDFBuilder {

    private final static int signatureWidth = 160;
    private final static int signatureHeight = 120;
    private final static String signatureEmployeeName = "employeeSignature.png";
    private final static String signaturePatientName = "patientSignature.png";

    private Font titleFont;
    private Font standardFont;
    private Font answerFont;
    private Font personalDataFont;

    private String dirPath;

    public PDFBuilder(String dirPath) throws DocumentException, IOException{
        this.titleFont = createBoldFont(18);
        this.standardFont = createRegularFont(10);
        this.answerFont = createItalicFont(8);
        this.personalDataFont = createItalicFont(10);

        this.dirPath = dirPath;
    }

    public void build(String name, FormKeyData formKeyData) throws DocumentException, IOException {
        Document document = new Document();

        Path savingPath = Paths.get(dirPath, name);

        PdfWriter writer = PdfWriter.getInstance(document, Files.newOutputStream(savingPath));
        document.open();

        addCreationDate(document, formKeyData.getCreationDate());
        addTitle(document, formKeyData.getTitle());
        addPersonalData(document, formKeyData.getPersonalData());
        addQuestions(document, formKeyData.getQuestions());
        addSignatures(document, formKeyData.getSignatures());

        document.close();
        writer.close();
    }

    private void addCreationDate(Document document, String creationDate) throws DocumentException {
        document.addCreationDate();

        Paragraph creationDateParagraph = new Paragraph(creationDate, standardFont);
        creationDateParagraph.setAlignment(Element.ALIGN_RIGHT);
        document.add(creationDateParagraph);
    }

    private void addTitle(Document document, String formName) throws DocumentException {
        Paragraph title = new Paragraph(formName, titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        title.setSpacingAfter(20);
        title.setSpacingBefore(10);
        document.add(title);
    }

    private void addPersonalData(Document document, PersonalDataContener personalDataContener)
            throws DocumentException {
        for (PersonalData personalData : personalDataContener.getPersonalDatas()) {
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

    private void addQuestions(Document document, QuestionContener questionContener) throws DocumentException {
        List<Question> questions = questionContener.getQuestions();

        for (int i = 0; i < questionContener.getMaxFieldNumber(); i++) {
            for (Question question : questions) {
                if (i == question.getFieldNumber()) {
                    Paragraph questionParagraph = new Paragraph(question.getTitle(), standardFont);
                    Paragraph answerParagraph =  new Paragraph("    " + question.getAnswer(), answerFont);
                    document.add(questionParagraph);
                    document.add(answerParagraph);
                    break;
                }
            }
        }
    }

    private void addSignatures(Document document, SignaturesContener signaturesContener)
            throws DocumentException, IOException  {
        addEmptyLine(document, answerFont);

        resizeSignature(signaturesContener.getEmployeeSignature(), signatureEmployeeName);
        resizeSignature(signaturesContener.getPatientSignature(), signaturePatientName);

        PdfPTable imageTable = new PdfPTable(2);
        imageTable.setTotalWidth(document.getPageSize().getWidth());
        imageTable.addCell(getImageCell(Image.getInstance(signatureEmployeeName)));
        imageTable.addCell(getImageCell(Image.getInstance(signaturePatientName)));
        document.add(imageTable);

        PdfPTable titleTable = new PdfPTable(2);
        titleTable.setTotalWidth(document.getPageSize().getWidth());
        titleTable.addCell(getTitleCell(new Phrase(signaturesContener.getEmployeeSignatureTitle(), standardFont)));
        titleTable.addCell(getTitleCell(new Phrase(signaturesContener.getPatientSignatureTitle(), standardFont)));
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

        ImageIO.write(signatureImageResized, "png", new File(name) );
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
}
