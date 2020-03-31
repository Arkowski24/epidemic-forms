package pl.edu.agh.ki.covid19tablet.state.fields.dto

import pl.edu.agh.ki.covid19tablet.schema.fields.TextFieldId
import pl.edu.agh.ki.covid19tablet.state.fields.TextFieldStateId

data class TextFieldStateDTO(
    val id: TextFieldStateId,
    val fieldId: TextFieldId,
    val fieldNumber: Int,
    val value: String
)
