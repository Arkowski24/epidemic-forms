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

    public byte[] generatePDF(FormDTO formDTO) throws DocumentException, IOException {
        PDFBuilder pdfBuilder = new PDFBuilder();
        pdfBuilder.build("Covid19Form.pdf", formDTO);

        return null;
    }
}
