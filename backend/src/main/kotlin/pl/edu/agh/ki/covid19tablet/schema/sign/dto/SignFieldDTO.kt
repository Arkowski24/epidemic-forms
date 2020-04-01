package pl.edu.agh.ki.covid19tablet.schema.sign.dto

import pl.edu.agh.ki.covid19tablet.schema.sign.SignFieldId

data class SignFieldDTO(
    val id: SignFieldId,

    val title: String,
    val description: String
)
