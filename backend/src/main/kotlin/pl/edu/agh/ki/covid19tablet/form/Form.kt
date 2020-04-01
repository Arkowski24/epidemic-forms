package pl.edu.agh.ki.covid19tablet.form

import pl.edu.agh.ki.covid19tablet.form.dto.FormDTO
import pl.edu.agh.ki.covid19tablet.schema.Schema
import pl.edu.agh.ki.covid19tablet.schema.toDTO
import pl.edu.agh.ki.covid19tablet.state.FormState
import pl.edu.agh.ki.covid19tablet.state.toDTO
import javax.persistence.Embedded
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.ManyToOne

typealias FormId = Long

@Entity
data class Form(
    @Id
    @GeneratedValue
    val id: FormId? = null,
    @ManyToOne
    val schema: Schema,
    val patientName: String,
    @Embedded
    val state: FormState,
    val finished: Boolean = false
)

fun Form.toDTO() = FormDTO(
    id = id!!,
    schema = schema.toDTO(),
    patientName = patientName,
    state = state.toDTO(),
    finished = finished
)
