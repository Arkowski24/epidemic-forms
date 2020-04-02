package pl.edu.agh.ki.covid19tablet.pdfgenetator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.agh.ki.covid19tablet.FormNotFoundException;
import pl.edu.agh.ki.covid19tablet.form.dto.FormDTO;

@RestController
@RequestMapping(path = "/pdfgenerator")
public class PDFGeneratorController {

    @Autowired
    private PDFGeneratorService pdfGeneratorService;

    @GetMapping
    public ResponseEntity<byte[]> getPDF(
            @RequestParam int id
    ) {
        FormDTO formDTO;
        try {
            formDTO = pdfGeneratorService.getForm(id);
        } catch (FormNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        pdfGeneratorService.generatePDF(formDTO);

        return null;
    }
}
