package pl.edu.agh.ki.covid19tablet.pdfgenetator;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.agh.ki.covid19tablet.FormNotFoundException;
import pl.edu.agh.ki.covid19tablet.form.Form;
import pl.edu.agh.ki.covid19tablet.form.FormKt;
import pl.edu.agh.ki.covid19tablet.form.FormRepository;
import pl.edu.agh.ki.covid19tablet.form.dto.FormDTO;

import java.io.FileOutputStream;
import java.util.Optional;

@Service
public class PDFGeneratorService {

    @Autowired
    FormRepository formRepository;

    public byte[] generatePDF(FormDTO formDTO) {
        Document document = new Document();
        try {
            BaseFont baseFont = BaseFont.createFont("aller.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            Font font = new Font(baseFont, 15);
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("Covid-19Form.pdf"));
            document.open();
            document.addCreationDate();

            addTitle(formDTO, document, font);

            document.close();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public FormDTO getForm(long id) {
        Optional<Form> form = formRepository.findById(id);
        if (form.isPresent()) {
            return FormKt.toDTO(form.get());
        }

        throw new FormNotFoundException();
    }

    private void addTitle(FormDTO formDTO, Document document, Font font) {
        try {
            Paragraph title = new Paragraph(formDTO.getFormName(), font);
            document.add(title);

        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }
}
