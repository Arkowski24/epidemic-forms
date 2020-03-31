package pl.edu.agh.ki.covid19tablet.formStream.dto.update

import pl.edu.agh.ki.covid19tablet.formState.fields.TextFieldStateId

data class TextFieldStateUpdate(
    val id: TextFieldStateId,
    val newValue: String
)
