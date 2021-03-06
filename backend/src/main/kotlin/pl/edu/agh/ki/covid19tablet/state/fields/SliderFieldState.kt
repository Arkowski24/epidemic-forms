package pl.edu.agh.ki.covid19tablet.state.fields

import pl.edu.agh.ki.covid19tablet.schema.fields.SliderField
import pl.edu.agh.ki.covid19tablet.state.fields.dto.SliderFieldStateDTO
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.ManyToOne

typealias SliderFieldStateId = Long

@Entity
data class SliderFieldState(
    @Id
    @GeneratedValue
    val id: SliderFieldStateId? = null,
    @ManyToOne
    val field: SliderField,
    val value: Double
)

fun SliderFieldState.toDTO() =
    SliderFieldStateDTO(
        id = id!!,
        fieldId = field.id!!,
        fieldNumber = field.fieldNumber,
        value = value
    )
