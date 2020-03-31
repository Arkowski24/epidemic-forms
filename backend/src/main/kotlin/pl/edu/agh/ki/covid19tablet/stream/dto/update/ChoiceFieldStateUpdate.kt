package pl.edu.agh.ki.covid19tablet.stream.dto.update

import pl.edu.agh.ki.covid19tablet.state.fields.ChoiceFieldStateId

data class ChoiceFieldStateUpdate(
    val id: ChoiceFieldStateId,
    val newValues: List<Boolean>
)
