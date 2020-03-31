package pl.edu.agh.ki.covid19tablet.stream.dto.update

import pl.edu.agh.ki.covid19tablet.state.fields.TextFieldStateId

data class TextFieldStateUpdate(
    val id: TextFieldStateId,
    val newValue: String
)
