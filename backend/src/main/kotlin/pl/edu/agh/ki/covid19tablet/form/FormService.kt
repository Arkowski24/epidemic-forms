package pl.edu.agh.ki.covid19tablet.form

import org.springframework.stereotype.Service
import pl.edu.agh.ki.covid19tablet.FormNotFoundException
import pl.edu.agh.ki.covid19tablet.SchemaNotFoundException
import pl.edu.agh.ki.covid19tablet.form.dto.CreateFormRequest
import pl.edu.agh.ki.covid19tablet.form.dto.FormDTO
import pl.edu.agh.ki.covid19tablet.schema.SchemaRepository

interface FormService {
    fun getAllForms(): List<FormDTO>
    fun getForm(formId: FormId): FormDTO
    fun createForm(createFormRequest: CreateFormRequest): FormDTO
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

    override fun createForm(createFormRequest: CreateFormRequest): FormDTO {
        val schema = schemaRepository
            .findById(createFormRequest.schemaId)
            .orElseThrow { SchemaNotFoundException() }

        val form = formRepository.save(
            Form(
                schema = schema,
                patientName = createFormRequest.patientName
            )
        )
        return form.toDTO()
    }

}
