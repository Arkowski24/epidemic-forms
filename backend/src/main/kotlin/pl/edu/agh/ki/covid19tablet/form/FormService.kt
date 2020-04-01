package pl.edu.agh.ki.covid19tablet.form

import org.springframework.stereotype.Service
import pl.edu.agh.ki.covid19tablet.FormNotFoundException
import pl.edu.agh.ki.covid19tablet.SchemaNotFoundException
import pl.edu.agh.ki.covid19tablet.form.dto.CreateFormRequest
import pl.edu.agh.ki.covid19tablet.form.dto.CreateSignatureRequest
import pl.edu.agh.ki.covid19tablet.form.dto.FormDTO
import pl.edu.agh.ki.covid19tablet.form.signature.Signature
import pl.edu.agh.ki.covid19tablet.form.signature.SignatureRepository
import pl.edu.agh.ki.covid19tablet.schema.SchemaRepository
import pl.edu.agh.ki.covid19tablet.schema.fields.buildInitialState
import java.util.Base64

interface FormService {
    fun getAllForms(): List<FormDTO>
    fun getForm(formId: FormId): FormDTO

    fun createForm(request: CreateFormRequest): FormDTO
    fun updateFormStatus(formId: FormId, newStatus: FormStatus)

    fun createPatientSignature(formId: FormId, request: CreateSignatureRequest)
    fun createEmployeeSignature(formId: FormId, request: CreateSignatureRequest)
}

@Service
class FormServiceImpl(
    private val formRepository: FormRepository,
    private val signatureRepository: SignatureRepository,
    private val schemaRepository: SchemaRepository
) : FormService {
    override fun getAllForms(): List<FormDTO> =
        formRepository
            .findAll()
            .map { it.toDTO() }
            .toList()

    override fun getForm(formId: FormId): FormDTO =
        formRepository
            .findById(formId)
            .orElseThrow { FormNotFoundException() }
            .toDTO()

    override fun createForm(request: CreateFormRequest): FormDTO {
        val schema = schemaRepository
            .findById(request.schemaId)
            .orElseThrow { SchemaNotFoundException() }

        val form = formRepository.save(
            Form(
                schema = schema,
                formName = request.formName,
                state = schema.fields.buildInitialState()
            )
        )
        return form.toDTO()
    }

    override fun updateFormStatus(formId: FormId, newStatus: FormStatus) {
        val form = formRepository
            .findById(formId)
            .orElseThrow { FormNotFoundException() }
            .copy(status = newStatus)

        formRepository.save(form)
    }

    override fun createPatientSignature(formId: FormId, request: CreateSignatureRequest) {
        val signature = signatureRepository.save(
            Signature(value = serializeImage(request.signature))
        )

        val form = formRepository
            .findById(formId)
            .orElseThrow { FormNotFoundException() }
            .copy(patientSignature = signature)

        formRepository.save(form)
    }

    override fun createEmployeeSignature(formId: FormId, request: CreateSignatureRequest) {
        val signature = signatureRepository.save(
            Signature(value = serializeImage(request.signature))
        )

        val form = formRepository
            .findById(formId)
            .orElseThrow { FormNotFoundException() }
            .copy(employeeSignature = signature)

        formRepository.save(form)
    }

    private fun serializeImage(image: String): ByteArray =
        Base64.getDecoder().decode(image)

}
