package pl.edu.agh.ki.covid19tablet.form.state.fields

import pl.edu.agh.ki.covid19tablet.form.state.fields.dto.SliderFieldStateDTO
import pl.edu.agh.ki.covid19tablet.schema.fields.SliderField
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.ManyToOne

typealias SliderFieldStateId = Long

@Entity
data class SliderFieldState(
    @Id
    val id: SliderFieldStateId? = null,
    @ManyToOne
    val field: SliderField,
    val value: Double
)

fun SliderFieldState.toDTO() =
    SliderFieldStateDTO(
        id = id!!,
        fieldId = field.id!!,
        value = value
    )
