package pl.edu.agh.ki.covid19tablet.pdfgenetator;

import com.itextpdf.text.DocumentException;
import org.springframework.stereotype.Service;
import pl.edu.agh.ki.covid19tablet.form.Form;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

@Service
public class PDFGeneratorService {
    private static final String pdfDirPath = "/tmp/forms";
    private static final String pdfNamePrefix = "Covid19Form";
    private static final SimpleDateFormat sdfFileSuffix = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss-SSS");
    private static final SimpleDateFormat sdfPdfDateHeader = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public void generatePDF(Form form) throws DocumentException, IOException {
        createPDFDirectory();

        PDFBuilder pdfBuilder = new PDFBuilder(pdfDirPath);
        String pdfName = generatePDFName();
        String pdfCreationDate = generatePDFCreationDate();
        pdfBuilder.build(pdfName, pdfCreationDate, form);
    }

    private void createPDFDirectory() throws IOException {
        Path pdfPath = Paths.get(pdfDirPath);
        boolean pdfDirExists = Files.exists(pdfPath);
        if (!pdfDirExists) {
            Files.createDirectories(pdfPath);
        }
    }

    private String generatePDFName() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String currentTime = sdfFileSuffix.format(timestamp);

        return pdfNamePrefix + "_" + currentTime + ".pdf";
    }

    private String generatePDFCreationDate() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        return sdfPdfDateHeader.format(timestamp);
    }

}
