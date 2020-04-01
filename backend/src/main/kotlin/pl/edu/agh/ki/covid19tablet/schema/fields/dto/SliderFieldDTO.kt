package pl.edu.agh.ki.covid19tablet.schema.fields.dto

import pl.edu.agh.ki.covid19tablet.schema.fields.SliderFieldId

data class SliderFieldDTO(
    val id: SliderFieldId,
    val fieldNumber: Int,

    val title: String,
    val description: String,

    val minValue: Double,
    val maxValue: Double,
    val step: Double
)
