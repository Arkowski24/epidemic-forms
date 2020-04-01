package pl.edu.agh.ki.covid19tablet.form.dto

import pl.edu.agh.ki.covid19tablet.form.FormId
import pl.edu.agh.ki.covid19tablet.schema.dto.SchemaDTO
import pl.edu.agh.ki.covid19tablet.state.dto.FormStateDTO

data class FormDTO(
    val id: FormId,
    val schema: SchemaDTO,
    val patientName: String,
    val state: FormStateDTO,
    val finished: Boolean
)
