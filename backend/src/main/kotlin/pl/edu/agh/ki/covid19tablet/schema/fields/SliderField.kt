package pl.edu.agh.ki.covid19tablet.schema.fields

import pl.edu.agh.ki.covid19tablet.schema.fields.dto.SliderFieldDTO
import javax.persistence.Entity
import javax.persistence.Id

typealias SliderFieldId = Long

@Entity
data class SliderField(
    @Id
    val id: SliderFieldId? = null,
    val order: Int,
    val description: String,
    val minValue: Double,
    val maxValue: Double,
    val step: Double
)

fun SliderField.toDTO() =
    SliderFieldDTO(
        id = id!!,
        order = order,
        description = description,
        minValue = minValue,
        maxValue = maxValue,
        step = step
    )
