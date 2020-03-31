package pl.edu.agh.ki.covid19tablet.state.fields.dto

import pl.edu.agh.ki.covid19tablet.schema.fields.SliderFieldId
import pl.edu.agh.ki.covid19tablet.state.fields.SliderFieldStateId

data class SliderFieldStateDTO(
    val id: SliderFieldStateId,
    val fieldId: SliderFieldId,
    val value: Double
)
