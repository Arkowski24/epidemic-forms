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
import java.util.Optional;

@Service
public class PDFGeneratorService {

    @Autowired
    private FormRepository formRepository;

    private static final String pdfDirPath = "/tmpformsdir";
    private static final String pdfNamePrefix = "Covid19Form";
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss-SSS");



    public Form getForm(long id) {
        Optional<Form> form = formRepository.findById(id);
        if (form.isPresent()) {
            return form.get();
        }

        throw new FormNotFoundException();
    }

    public void generatePDF(Form form) throws DocumentException, IOException {
        createPDFDirectory();

        PDFBuilder pdfBuilder = new PDFBuilder(pdfDirPath);
        String pdfName = generatePDFName();
        pdfBuilder.build(pdfName, form);
    }

    private String generatePDFName() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String currentTime = sdf.format(timestamp);

        return pdfNamePrefix + "_" + currentTime + ".pdf";
    }

    private void createPDFDirectory() throws IOException {
        Path pdfPath = Paths.get(pdfDirPath);
        boolean pdfDirExists = Files.exists(pdfPath);
        if (!pdfDirExists) {
            Files.createDirectories(pdfPath);
        }
    }
}
