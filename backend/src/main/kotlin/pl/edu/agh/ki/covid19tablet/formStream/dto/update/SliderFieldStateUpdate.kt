package pl.edu.agh.ki.covid19tablet.formStream.dto.update

import pl.edu.agh.ki.covid19tablet.formState.fields.SliderFieldStateId

data class SliderFieldStateUpdate(
    val id: SliderFieldStateId,
    val newValue: Double
)
