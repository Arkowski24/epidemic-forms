package pl.edu.agh.ki.covid19tablet.form

import org.springframework.stereotype.Service
import pl.edu.agh.ki.covid19tablet.DeviceNotFoundException
import pl.edu.agh.ki.covid19tablet.EmployeeUnauthorizedException
import pl.edu.agh.ki.covid19tablet.FormNotFoundException
import pl.edu.agh.ki.covid19tablet.PatientUnauthorizedException
import pl.edu.agh.ki.covid19tablet.SchemaNotFoundException
import pl.edu.agh.ki.covid19tablet.device.Device
import pl.edu.agh.ki.covid19tablet.form.dto.CreateFormRequest
import pl.edu.agh.ki.covid19tablet.form.dto.CreateSignatureRequest
import pl.edu.agh.ki.covid19tablet.form.dto.FormDTO
import pl.edu.agh.ki.covid19tablet.form.signature.Signature
import pl.edu.agh.ki.covid19tablet.form.signature.SignatureRepository
import pl.edu.agh.ki.covid19tablet.pdfgenetator.PDFGeneratorService
import pl.edu.agh.ki.covid19tablet.schema.SchemaRepository
import pl.edu.agh.ki.covid19tablet.schema.fields.buildInitialState
import pl.edu.agh.ki.covid19tablet.security.employee.EmployeeDetails
import pl.edu.agh.ki.covid19tablet.security.patient.PatientTokenProvider
import pl.edu.agh.ki.covid19tablet.user.employee.Employee
import pl.edu.agh.ki.covid19tablet.user.employee.EmployeeRepository
import pl.edu.agh.ki.covid19tablet.user.employee.EmployeeRole
import pl.edu.agh.ki.covid19tablet.user.patient.Patient
import pl.edu.agh.ki.covid19tablet.user.patient.PatientRepository
import java.util.Base64

interface FormService {
    fun getAllForms(employeeDetails: EmployeeDetails): List<FormDTO>
    fun getForm(formId: FormId): FormDTO

    fun createForm(request: CreateFormRequest, employeeDetails: EmployeeDetails): FormDTO
    fun updateFormStatus(formId: FormId, newStatus: FormStatus)

    fun deleteForm(formId: FormId, employeeDetails: EmployeeDetails)

    fun createPatientSignature(formId: FormId, request: CreateSignatureRequest, token: String)
    fun createEmployeeSignature(formId: FormId, request: CreateSignatureRequest, employeeDetails: EmployeeDetails)
}

@Service
class FormServiceImpl(
    private val formRepository: FormRepository,
    private val patientRepository: PatientRepository,
    private val employeeRepository: EmployeeRepository,
    private val signatureRepository: SignatureRepository,
    private val schemaRepository: SchemaRepository,
    private val patientTokenProvider: PatientTokenProvider,
    private val pdfGeneratorService: PDFGeneratorService
) : FormService {
    override fun getAllForms(employeeDetails: EmployeeDetails): List<FormDTO> {
        val forms =
            if (employeeDetails.employee.role == EmployeeRole.ADMIN) formRepository.findAll()
            else formRepository.findAllByCreatedBy_Id(employeeDetails.employee.id!!)

        return forms.map { it.toDTO() }
    }

    override fun getForm(formId: FormId): FormDTO =
        formRepository
            .findById(formId)
            .orElseThrow { FormNotFoundException() }
            .toDTO()

    override fun createForm(request: CreateFormRequest, employeeDetails: EmployeeDetails): FormDTO {
        val schema = schemaRepository
            .findById(request.schemaId)
            .orElseThrow { SchemaNotFoundException() }

        val employee = employeeRepository
            .findById(employeeDetails.employee.id!!)
            .get()

        val device = request.deviceId
            ?.let {
                employeeRepository
                    .findById(it)
                    .orElseThrow { DeviceNotFoundException() } as Device
            }

        val patient = Patient()
        val form = formRepository.save(
            Form(
                schema = schema,
                formName = request.formName,
                patient = patient,
                createdBy = employeeDetails.employee,
                device = device,
                state = schema.fields.buildInitialState()
            )
        )

        employee.forms.add(form)
        patientRepository.save(patient.copy(form = form))
        employeeRepository.save(employee)

        return form.toDTO()
    }

    override fun updateFormStatus(formId: FormId, newStatus: FormStatus) {
        val form = formRepository
            .findById(formId)
            .orElseThrow { FormNotFoundException() }
            .copy(status = newStatus)

        formRepository.save(form)
    }

    override fun deleteForm(formId: FormId, employeeDetails: EmployeeDetails) {
        val form = formRepository
            .findById(formId)
            .orElseThrow { FormNotFoundException() }

        if (!canDeleteForm(employee = employeeDetails.employee, form = form))
            throw EmployeeUnauthorizedException()

        formRepository.delete(form)
    }

    override fun createPatientSignature(
        formId: FormId,
        request: CreateSignatureRequest,
        token: String
    ) {
        val form = formRepository
            .findById(formId)
            .orElseThrow { FormNotFoundException() }

        patientTokenProvider
            .parseToken(token)
            .takeIf { it == form.patient.id }
            ?: throw PatientUnauthorizedException()

        val signature = signatureRepository.save(
            Signature(value = serializeImage(request.signature))
        )

        val newForm = form.copy(patientSignature = signature)
        formRepository.save(newForm)
    }

    override fun createEmployeeSignature(
        formId: FormId,
        request: CreateSignatureRequest,
        employeeDetails: EmployeeDetails
    ) {
        val signature = signatureRepository.save(
            Signature(value = serializeImage(request.signature))
        )

        val form = formRepository
            .findById(formId)
            .orElseThrow { FormNotFoundException() }
            .copy(
                signedBy = employeeDetails.employee,
                employeeSignature = signature
            )

        pdfGeneratorService.generatePDF(form)
        formRepository.save(form)
    }

    private fun serializeImage(image: String): ByteArray =
        Base64.getDecoder().decode(image)

    private fun canDeleteForm(form: Form, employee: Employee) =
        if (employee.role == EmployeeRole.ADMIN) true
        else form.createdBy.id == employee.id

}
