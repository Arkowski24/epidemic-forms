package pl.edu.agh.ki.covid19tablet.schema.fields.dto

import pl.edu.agh.ki.covid19tablet.schema.fields.FieldType
import pl.edu.agh.ki.covid19tablet.schema.fields.SimpleFieldId

data class SimpleFieldDTO(
    val id: SimpleFieldId,
    val fieldNumber: Int,
    val fieldType: FieldType,

    val title: String,
    val description: String,
    val inline: Boolean
)
