package pl.edu.agh.ki.covid19tablet.form.signature.dto

import pl.edu.agh.ki.covid19tablet.form.signature.SignatureId

data class SignatureDTO(
    val id: SignatureId,
    val value: String
)
