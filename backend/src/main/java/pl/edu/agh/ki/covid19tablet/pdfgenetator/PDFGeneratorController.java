package pl.edu.agh.ki.covid19tablet.pdfgenetator;

import com.itextpdf.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.agh.ki.covid19tablet.FormNotFoundException;
import pl.edu.agh.ki.covid19tablet.form.Form;

import java.io.IOException;

@RestController
@RequestMapping(path = "/pdfgenerator")
public class PDFGeneratorController {

    @Autowired
    private PDFGeneratorService pdfGeneratorService;

    @GetMapping
    public ResponseEntity<byte[]> getPDF(
            @RequestParam int id
    ) {
        Form form;
        try {
            form = pdfGeneratorService.getForm(id);
        }
        catch (FormNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        byte[] content;
        try {
            content = pdfGeneratorService.generatePDF(form);
        }
        catch (DocumentException | IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(content, HttpStatus.OK);
    }
}
