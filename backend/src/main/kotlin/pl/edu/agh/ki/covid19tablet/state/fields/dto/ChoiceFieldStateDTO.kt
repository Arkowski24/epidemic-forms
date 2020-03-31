package pl.edu.agh.ki.covid19tablet.state.fields.dto

import pl.edu.agh.ki.covid19tablet.schema.fields.ChoiceFieldId
import pl.edu.agh.ki.covid19tablet.state.fields.ChoiceFieldStateId

data class ChoiceFieldStateDTO(
    val id: ChoiceFieldStateId,
    val fieldId: ChoiceFieldId,
    val fieldNumber: Int,
    val values: List<Boolean>
)
