package pl.edu.agh.ki.covid19tablet.schema.fields

import pl.edu.agh.ki.covid19tablet.schema.fields.dto.SliderFieldDTO
import pl.edu.agh.ki.covid19tablet.state.fields.SliderFieldState
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
    val fieldType: FieldType = FieldType.NORMAL,

    val title: String,
    val description: String = "",
    val inline: Boolean = false,

    val minValue: Double,
    val maxValue: Double,
    val step: Double
)

fun SliderField.toDTO() =
    SliderFieldDTO(
        id = id!!,
        fieldNumber = fieldNumber,
        fieldType = fieldType,
        title = title,
        description = description,
        inline = inline,
        minValue = minValue,
        maxValue = maxValue,
        step = step
    )

fun SliderField.buildInitialState() =
    SliderFieldState(
        field = this,
        value = minValue
    )
