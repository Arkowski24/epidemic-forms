package pl.edu.agh.ki.covid19tablet.form

import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pl.edu.agh.ki.covid19tablet.FormNotFoundException
import pl.edu.agh.ki.covid19tablet.SchemaNotFoundException
import pl.edu.agh.ki.covid19tablet.form.dto.CreateFormRequest
import pl.edu.agh.ki.covid19tablet.form.dto.CreateSignatureRequest
import pl.edu.agh.ki.covid19tablet.form.dto.FormDTO
import pl.edu.agh.ki.covid19tablet.user.employee.Authorities.FORM_CREATE
import pl.edu.agh.ki.covid19tablet.user.employee.Authorities.FORM_MODIFY
import pl.edu.agh.ki.covid19tablet.user.employee.Authorities.FORM_READ
import javax.validation.Valid

@RestController
@RequestMapping("/forms")
class FormController(
    val formService: FormService
) {
    @GetMapping
    @PreAuthorize("hasAuthority('$FORM_READ')")
    fun getAllForms(): List<FormDTO> =
        formService
            .getAllForms()

    @GetMapping("{formId}")
    @PreAuthorize("hasAuthority('$FORM_READ')")
    fun getForm(@PathVariable formId: FormId): ResponseEntity<FormDTO> =
        try {
            val form = formService.getForm(formId)
            ResponseEntity(form, HttpStatus.OK)
        } catch (ex: FormNotFoundException) {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }

    @PostMapping
    @PreAuthorize("hasAuthority('$FORM_CREATE')")
    fun createForm(@Valid @RequestBody request: CreateFormRequest): ResponseEntity<FormDTO> =
        try {
            val form = formService.createForm(request)
            ResponseEntity(form, HttpStatus.OK)
        } catch (ex: SchemaNotFoundException) {
            ResponseEntity(HttpStatus.BAD_REQUEST)
        }

    @PostMapping("{formId}/signature/patient")
    fun createPatientSign(
        @RequestHeader(HttpHeaders.AUTHORIZATION) authorization: String,
        @PathVariable formId: FormId,
        @Valid @RequestBody request: CreateSignatureRequest
    ): ResponseEntity<Nothing> =
        try {
            formService.createPatientSignature(formId, request)
            ResponseEntity(HttpStatus.NO_CONTENT)
        } catch (ex: FormNotFoundException) {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }

    @PostMapping("{formId}/signature/employee")
    @PreAuthorize("hasAuthority('$FORM_MODIFY')")
    fun createEmployeeSign(
        @PathVariable formId: FormId,
        @Valid @RequestBody request: CreateSignatureRequest
    ): ResponseEntity<Nothing> =
        try {
            formService.createEmployeeSignature(formId, request)
            ResponseEntity(HttpStatus.NO_CONTENT)
        } catch (ex: FormNotFoundException) {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
}
