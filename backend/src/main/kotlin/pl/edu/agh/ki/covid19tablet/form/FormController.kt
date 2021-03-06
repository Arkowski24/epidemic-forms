package pl.edu.agh.ki.covid19tablet.form

import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pl.edu.agh.ki.covid19tablet.DeviceNotFoundException
import pl.edu.agh.ki.covid19tablet.EmployeeUnauthorizedException
import pl.edu.agh.ki.covid19tablet.FormNotFoundException
import pl.edu.agh.ki.covid19tablet.PatientUnauthorizedException
import pl.edu.agh.ki.covid19tablet.SchemaNotFoundException
import pl.edu.agh.ki.covid19tablet.form.dto.CreateFormRequest
import pl.edu.agh.ki.covid19tablet.form.dto.CreateSignatureRequest
import pl.edu.agh.ki.covid19tablet.form.dto.FormDTO
import pl.edu.agh.ki.covid19tablet.security.employee.EmployeeDetails
import pl.edu.agh.ki.covid19tablet.user.employee.Authorities.FORM_CREATE
import pl.edu.agh.ki.covid19tablet.user.employee.Authorities.FORM_DELETE
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
    fun getAllForms(
        @AuthenticationPrincipal employeeDetails: EmployeeDetails
    ): List<FormDTO> =
        formService.getAllForms(employeeDetails)

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
    fun createForm(
        @Valid @RequestBody request: CreateFormRequest,
        @AuthenticationPrincipal employeeDetails: EmployeeDetails
    ): ResponseEntity<FormDTO> =
        try {
            val form = formService.createForm(request, employeeDetails)
            ResponseEntity(form, HttpStatus.OK)
        } catch (ex: SchemaNotFoundException) {
            ResponseEntity(HttpStatus.BAD_REQUEST)
        } catch (ex: DeviceNotFoundException) {
            ResponseEntity(HttpStatus.BAD_REQUEST)
        }

    @PostMapping("{formId}/signature/patient")
    fun createPatientSign(
        @RequestHeader(HttpHeaders.AUTHORIZATION) authorization: String,
        @PathVariable formId: FormId,
        @Valid @RequestBody request: CreateSignatureRequest
    ): ResponseEntity<Nothing> =
        try {
            val token = authorization.removePrefix("Bearer ")
            formService.createPatientSignature(formId, request, token)
            ResponseEntity(HttpStatus.NO_CONTENT)
        } catch (ex: FormNotFoundException) {
            ResponseEntity(HttpStatus.NOT_FOUND)
        } catch (ex: PatientUnauthorizedException) {
            ResponseEntity(HttpStatus.UNAUTHORIZED)
        }

    @PostMapping("{formId}/signature/employee")
    @PreAuthorize("hasAuthority('$FORM_MODIFY')")
    fun createEmployeeSign(
        @PathVariable formId: FormId,
        @Valid @RequestBody request: CreateSignatureRequest,
        @AuthenticationPrincipal employeeDetails: EmployeeDetails
    ): ResponseEntity<Nothing> =
        try {
            formService.createEmployeeSignature(formId, request, employeeDetails)
            ResponseEntity(HttpStatus.NO_CONTENT)
        } catch (ex: FormNotFoundException) {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }

    @DeleteMapping("{formId}")
    @PreAuthorize("hasAuthority('$FORM_DELETE')")
    fun deleteForm(
        @PathVariable formId: FormId,
        @AuthenticationPrincipal employeeDetails: EmployeeDetails
    ): ResponseEntity<Nothing> =
        try {
            formService.deleteForm(formId, employeeDetails)
            ResponseEntity(HttpStatus.NO_CONTENT)
        } catch (ex: FormNotFoundException) {
            ResponseEntity(HttpStatus.NOT_FOUND)
        } catch (ex: EmployeeUnauthorizedException) {
            ResponseEntity(HttpStatus.UNAUTHORIZED)
        }
}
