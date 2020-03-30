package pl.edu.agh.ki.covid19tablet.schema.fields.dto

import pl.edu.agh.ki.covid19tablet.schema.fields.SimpleFieldId

data class SimpleFieldDTO(
    val id: SimpleFieldId,
    val order: Int,
    val description: String
)