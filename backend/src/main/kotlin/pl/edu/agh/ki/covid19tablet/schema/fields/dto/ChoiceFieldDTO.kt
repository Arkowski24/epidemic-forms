package pl.edu.agh.ki.covid19tablet.schema.fields.dto

import pl.edu.agh.ki.covid19tablet.schema.fields.ChoiceFieldId

data class ChoiceFieldDTO(
    val id: ChoiceFieldId,
    val order: Int,
    val description: String,
    val choices: List<String>,
    val isMultiChoice: Boolean
)
