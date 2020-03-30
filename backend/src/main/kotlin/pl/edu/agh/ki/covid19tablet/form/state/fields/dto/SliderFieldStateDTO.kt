package pl.edu.agh.ki.covid19tablet.form.state.fields.dto

import pl.edu.agh.ki.covid19tablet.form.state.fields.SliderFieldStateId
import pl.edu.agh.ki.covid19tablet.schema.fields.SliderFieldId

data class SliderFieldStateDTO(
    val id: SliderFieldStateId,
    val fieldId: SliderFieldId,
    val value: Double
)
