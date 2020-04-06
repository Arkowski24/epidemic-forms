package pl.edu.agh.ki.covid19tablet.schema.fields.dto

import pl.edu.agh.ki.covid19tablet.schema.fields.FieldType
import pl.edu.agh.ki.covid19tablet.schema.fields.SliderFieldId

data class SliderFieldDTO(
    val id: SliderFieldId,
    val fieldNumber: Int,
    val fieldType: FieldType,

    val title: String,
    val description: String,
    val inline: Boolean,

    val minValue: Double,
    val maxValue: Double,
    val step: Double,
    val defaultValue: Double,

    val required: Boolean
)
