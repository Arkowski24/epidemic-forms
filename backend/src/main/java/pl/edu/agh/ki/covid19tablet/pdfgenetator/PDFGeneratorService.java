package pl.edu.agh.ki.covid19tablet.pdfgenetator;

import com.itextpdf.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.agh.ki.covid19tablet.FormNotFoundException;
import pl.edu.agh.ki.covid19tablet.form.Form;
import pl.edu.agh.ki.covid19tablet.form.FormRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

@Service
public class PDFGeneratorService {

    @Autowired
    private FormRepository formRepository;

    private static final String pdfBasicDirPath = "/tmp/forms";
    private static final SimpleDateFormat sdfFileSuffix = new SimpleDateFormat("yyyyMMddHHmm");
    private static final SimpleDateFormat sdfPdfDateHeader = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    public Form getForm(long id) {
        Optional<Form> form = formRepository.findById(id);
        if (form.isPresent()) {
            return form.get();
        }

        throw new FormNotFoundException();
    }

    public void generatePDF(Form form) throws DocumentException, IOException {
        String pdfCreationDate = generatePDFCreationDate();
        FormKeyData formKeyData = new FormKeyData(form, pdfCreationDate, pdfBasicDirPath);
        String pdfName = generatePDFName(
                formKeyData.getPersonalData().getFirstName(),
                formKeyData.getPersonalData().getSurname()
        );

        createPDFDirectory(formKeyData.getPdfDirPath());

        PDFBuilder pdfBuilder = new PDFBuilder(formKeyData.getPdfDirPath());
        pdfBuilder.build(pdfName, formKeyData);
    }

    private void createPDFDirectory(String path) throws IOException {
        Path pdfPath = Paths.get(path);
        boolean pdfDirExists = Files.exists(pdfPath);
        if (!pdfDirExists) {
            Files.createDirectories(pdfPath);
        }
    }

    private String generatePDFName(String firstName, String surname) {
        Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now(ZoneId.of("CET")));
        String currentTime = sdfFileSuffix.format(timestamp);

        return currentTime + "-" + surname + "_" + firstName + ".pdf";
    }

    private String generatePDFCreationDate() {
        Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now(ZoneId.of("CET")));
        return sdfPdfDateHeader.format(timestamp);
    }
}
