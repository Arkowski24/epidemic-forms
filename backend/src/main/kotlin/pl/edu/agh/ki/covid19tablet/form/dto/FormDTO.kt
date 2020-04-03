package pl.edu.agh.ki.covid19tablet.form.dto

import pl.edu.agh.ki.covid19tablet.form.FormId
import pl.edu.agh.ki.covid19tablet.form.FormStatus
import pl.edu.agh.ki.covid19tablet.schema.dto.SchemaDTO
import pl.edu.agh.ki.covid19tablet.state.dto.FormStateDTO
import pl.edu.agh.ki.covid19tablet.user.dto.EmployeeDTO
import pl.edu.agh.ki.covid19tablet.user.dto.PatientDTO

data class FormDTO(
    val id: FormId,
    val formName: String,
    val status: FormStatus,

    val patient: PatientDTO,
    val createdBy: EmployeeDTO,

    val schema: SchemaDTO,
    val state: FormStateDTO
)
