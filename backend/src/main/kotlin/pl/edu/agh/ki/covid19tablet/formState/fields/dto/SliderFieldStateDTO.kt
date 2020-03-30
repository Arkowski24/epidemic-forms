package pl.edu.agh.ki.covid19tablet.formState.fields.dto

import pl.edu.agh.ki.covid19tablet.formState.fields.SliderFieldStateId
import pl.edu.agh.ki.covid19tablet.schema.fields.SliderFieldId

data class SliderFieldStateDTO(
    val id: SliderFieldStateId,
    val fieldId: SliderFieldId,
    val value: Double
)
