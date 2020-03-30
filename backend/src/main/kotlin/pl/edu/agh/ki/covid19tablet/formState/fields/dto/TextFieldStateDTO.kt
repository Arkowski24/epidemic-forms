package pl.edu.agh.ki.covid19tablet.formState.fields.dto

import pl.edu.agh.ki.covid19tablet.formState.fields.TextFieldStateId
import pl.edu.agh.ki.covid19tablet.schema.fields.TextFieldId

data class TextFieldStateDTO(
    val id: TextFieldStateId,
    val field: TextFieldId,
    val value: String
)
