package pl.edu.agh.ki.covid19tablet.schema.fields.dto

import pl.edu.agh.ki.covid19tablet.schema.fields.FieldType
import pl.edu.agh.ki.covid19tablet.schema.fields.TextFieldId

data class TextFieldDTO(
    val id: TextFieldId,
    val fieldNumber: Int,
    val fieldType: FieldType,

    val title: String,
    val description: String,
    val inline: Boolean,

    val multiLine: Boolean
)
