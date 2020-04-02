package pl.edu.agh.ki.covid19tablet.schema.fields.dto

import pl.edu.agh.ki.covid19tablet.schema.fields.ChoiceFieldId
import pl.edu.agh.ki.covid19tablet.schema.fields.FieldType

data class ChoiceFieldDTO(
    val id: ChoiceFieldId,
    val fieldNumber: Int,
    val fieldType: FieldType,

    val title: String,
    val description: String,

    val choices: List<String>,
    val isMultiChoice: Boolean
)
