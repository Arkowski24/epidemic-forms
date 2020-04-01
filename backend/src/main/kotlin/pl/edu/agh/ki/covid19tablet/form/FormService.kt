package pl.edu.agh.ki.covid19tablet.form

import org.springframework.stereotype.Service
import pl.edu.agh.ki.covid19tablet.FormNotFoundException
import pl.edu.agh.ki.covid19tablet.SchemaNotFoundException
import pl.edu.agh.ki.covid19tablet.form.dto.CreateFormRequest
import pl.edu.agh.ki.covid19tablet.form.dto.CreateSignRequest
import pl.edu.agh.ki.covid19tablet.form.dto.FormDTO
import pl.edu.agh.ki.covid19tablet.form.sign.Sign
import pl.edu.agh.ki.covid19tablet.schema.SchemaRepository
import pl.edu.agh.ki.covid19tablet.schema.fields.buildInitialState
import java.util.Base64

interface FormService {
    fun getAllForms(): List<FormDTO>
    fun getForm(formId: FormId): FormDTO
    fun createForm(request: CreateFormRequest): FormDTO

    fun createPatientSign(formId: FormId, request: CreateSignRequest)
    fun createEmployeeSign(formId: FormId, request: CreateSignRequest)
}

@Service
class FormServiceImpl(
    private val formRepository: FormRepository,
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

    override fun createPatientSign(formId: FormId, request: CreateSignRequest) {
        val sign = Sign(
            value = serializeImage(request.sign)
        )

        val form = formRepository
            .findById(formId)
            .orElseThrow { FormNotFoundException() }
            .copy(patientSign = sign)

        formRepository.save(form)
    }

    override fun createEmployeeSign(formId: FormId, request: CreateSignRequest) {
        val sign = Sign(
            value = serializeImage(request.sign)
        )

        val form = formRepository
            .findById(formId)
            .orElseThrow { FormNotFoundException() }
            .copy(employeeSign = sign)

        formRepository.save(form)
    }

    private fun serializeImage(image: String): ByteArray =
        Base64.getUrlDecoder().decode(image)

}
