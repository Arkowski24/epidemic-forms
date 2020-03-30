package pl.edu.agh.ki.covid19tablet.form

import pl.edu.agh.ki.covid19tablet.form.dto.FormDTO
import pl.edu.agh.ki.covid19tablet.schema.Schema
import pl.edu.agh.ki.covid19tablet.schema.toDTO
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.ManyToOne

typealias FormId = Long

@Entity
data class Form(
    @Id
    val id: FormId? = null,
    @ManyToOne
    val schema: Schema,
    val patientName: String
)

fun Form.toDTO() = FormDTO(
    id = id!!,
    schema = schema.toDTO(),
    patientName = patientName
)
