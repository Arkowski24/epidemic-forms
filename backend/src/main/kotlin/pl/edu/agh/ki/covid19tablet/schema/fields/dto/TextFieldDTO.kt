package pl.edu.agh.ki.covid19tablet.schema.fields.dto

import pl.edu.agh.ki.covid19tablet.schema.fields.TextFieldId

data class TextFieldDTO(
    val id: TextFieldId,
    val order: Int,
    val description: String,
    val isMultiline: Boolean
)
