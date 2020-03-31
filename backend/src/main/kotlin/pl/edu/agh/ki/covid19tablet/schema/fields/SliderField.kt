package pl.edu.agh.ki.covid19tablet.schema.fields

import pl.edu.agh.ki.covid19tablet.formState.fields.SliderFieldState
import pl.edu.agh.ki.covid19tablet.schema.fields.dto.SliderFieldDTO
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

typealias SliderFieldId = Long

@Entity
data class SliderField(
    @Id
    @GeneratedValue
    val id: SliderFieldId? = null,
    val fieldNumber: Int,
    val description: String,
    val minValue: Double,
    val maxValue: Double,
    val step: Double
)

fun SliderField.toDTO() =
    SliderFieldDTO(
        id = id!!,
        fieldNumber = fieldNumber,
        description = description,
        minValue = minValue,
        maxValue = maxValue,
        step = step
    )

fun SliderField.buildInitialState() =
    SliderFieldState(
        field = this,
        value = minValue
    )
