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
import pl.edu.agh.ki.covid19tablet.schema.fields.*;
import pl.edu.agh.ki.covid19tablet.schema.fields.dto.*;
import pl.edu.agh.ki.covid19tablet.state.FormState;
import pl.edu.agh.ki.covid19tablet.state.dto.FormStateDTO;
import pl.edu.agh.ki.covid19tablet.state.fields.ChoiceFieldState;
import pl.edu.agh.ki.covid19tablet.state.fields.ChoiceFieldStateKt;
import pl.edu.agh.ki.covid19tablet.state.fields.TextFieldState;
import pl.edu.agh.ki.covid19tablet.state.fields.dto.ChoiceFieldStateDTO;
import pl.edu.agh.ki.covid19tablet.state.fields.dto.SliderFieldStateDTO;
import pl.edu.agh.ki.covid19tablet.state.fields.dto.TextFieldStateDTO;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
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

    public byte[] generatePDF(FormDTO formDTO) {
        PDFBuilder pdfBuilder = new PDFBuilder();
        pdfBuilder.build("Covid19Form.pdf", formDTO);

        return null;
    }
}
