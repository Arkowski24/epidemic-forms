package pl.edu.agh.ki.covid19tablet.formState.fields.dto

import pl.edu.agh.ki.covid19tablet.formState.fields.ChoiceFieldStateId
import pl.edu.agh.ki.covid19tablet.schema.fields.ChoiceFieldId

data class ChoiceFieldStateDTO(
    val id: ChoiceFieldStateId,
    val fieldId: ChoiceFieldId,
    val values: List<Boolean>
)
