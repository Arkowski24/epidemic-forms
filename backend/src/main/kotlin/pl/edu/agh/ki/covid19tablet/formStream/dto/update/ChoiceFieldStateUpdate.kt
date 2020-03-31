package pl.edu.agh.ki.covid19tablet.formStream.dto.update

import pl.edu.agh.ki.covid19tablet.formState.fields.ChoiceFieldStateId

data class ChoiceFieldStateUpdate(
    val id: ChoiceFieldStateId,
    val newValues: List<Boolean>
)
