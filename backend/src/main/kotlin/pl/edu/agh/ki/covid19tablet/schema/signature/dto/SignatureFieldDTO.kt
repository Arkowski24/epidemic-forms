package pl.edu.agh.ki.covid19tablet.schema.signature.dto

import pl.edu.agh.ki.covid19tablet.schema.signature.SignFieldId

data class SignatureFieldDTO(
    val id: SignFieldId,

    val title: String,
    val description: String
)
