package pl.edu.agh.ki.covid19tablet.form

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pl.edu.agh.ki.covid19tablet.FormNotFoundException
import pl.edu.agh.ki.covid19tablet.SchemaNotFoundException
import pl.edu.agh.ki.covid19tablet.form.dto.CreateFormRequest
import pl.edu.agh.ki.covid19tablet.form.dto.FormDTO
import javax.validation.Valid

@RestController
@RequestMapping("/forms")
class FormController(
    val formService: FormService
) {
    @GetMapping
    fun getAllForms(): List<FormDTO> =
        formService
            .getAllForms()

    @GetMapping("{formId}")
    fun getForm(@PathVariable formId: FormId): ResponseEntity<FormDTO> =
        try {
            val form = formService.getForm(formId)
            ResponseEntity(form, HttpStatus.OK)
        } catch (ex: FormNotFoundException) {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }

    @PostMapping
    fun createForm(@Valid @RequestBody request: CreateFormRequest): ResponseEntity<FormDTO> =
        try {
            val form = formService.createForm(request)
            ResponseEntity(form, HttpStatus.OK)
        } catch (ex: SchemaNotFoundException) {
            ResponseEntity(HttpStatus.BAD_REQUEST)
        }
}