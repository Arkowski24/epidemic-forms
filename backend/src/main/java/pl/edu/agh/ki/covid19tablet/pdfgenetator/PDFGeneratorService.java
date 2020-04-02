package pl.edu.agh.ki.covid19tablet.pdfgenetator;

import com.itextpdf.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.agh.ki.covid19tablet.FormNotFoundException;
import pl.edu.agh.ki.covid19tablet.form.Form;
import pl.edu.agh.ki.covid19tablet.form.FormKt;
import pl.edu.agh.ki.covid19tablet.form.FormRepository;
import pl.edu.agh.ki.covid19tablet.form.dto.FormDTO;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Service
public class PDFGeneratorService {

    @Autowired
    private FormRepository formRepository;

    private static final String pdfName = "Covid19Form.pdf";



    public FormDTO getForm(long id) {
        Optional<Form> form = formRepository.findById(id);
        if (form.isPresent()) {
            return FormKt.toDTO(form.get());
        }

        throw new FormNotFoundException();
    }

    public byte[] generatePDF(FormDTO formDTO) throws DocumentException, IOException {
        PDFBuilder pdfBuilder = new PDFBuilder();
        pdfBuilder.build(pdfName, formDTO);

        Path pdfPath = Paths.get(pdfName);
        return Files.readAllBytes(pdfPath);
    }
}
