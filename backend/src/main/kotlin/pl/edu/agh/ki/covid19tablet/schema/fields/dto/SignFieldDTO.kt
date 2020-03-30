package pl.edu.agh.ki.covid19tablet.schema.fields.dto

import pl.edu.agh.ki.covid19tablet.schema.fields.SignFieldId

data class SignFieldDTO(
    val id: SignFieldId,
    val order: Int,
    val description: String
)
