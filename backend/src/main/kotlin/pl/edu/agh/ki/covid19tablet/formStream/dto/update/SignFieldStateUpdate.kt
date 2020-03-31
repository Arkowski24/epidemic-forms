package pl.edu.agh.ki.covid19tablet.formStream.dto.update

import pl.edu.agh.ki.covid19tablet.formState.fields.SignFieldStateId

data class SignFieldStateUpdate(
    val id: SignFieldStateId,
    val newValue: String
)
