package pl.edu.agh.ki.covid19tablet.stream.dto.update

import pl.edu.agh.ki.covid19tablet.state.fields.SliderFieldStateId

data class SliderFieldStateUpdate(
    val id: SliderFieldStateId,
    val newValue: Double
)
