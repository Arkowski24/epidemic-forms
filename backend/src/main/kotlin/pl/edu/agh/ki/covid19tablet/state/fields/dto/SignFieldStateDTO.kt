package pl.edu.agh.ki.covid19tablet.state.fields.dto

import pl.edu.agh.ki.covid19tablet.schema.fields.ChoiceFieldId
import pl.edu.agh.ki.covid19tablet.state.fields.ChoiceFieldStateId

data class SignFieldStateDTO(
    val id: ChoiceFieldStateId,
    val fieldId: ChoiceFieldId,
    val value: String
)
