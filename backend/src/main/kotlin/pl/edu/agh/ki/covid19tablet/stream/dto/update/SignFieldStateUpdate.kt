package pl.edu.agh.ki.covid19tablet.stream.dto.update

import pl.edu.agh.ki.covid19tablet.state.fields.SignFieldStateId

data class SignFieldStateUpdate(
    val id: SignFieldStateId,
    val newValue: String
)
