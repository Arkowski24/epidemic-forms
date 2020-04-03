package pl.edu.agh.ki.covid19tablet.security.auth.dto

import pl.edu.agh.ki.covid19tablet.form.FormId

data class PatientLoginResponse(
    val token: String,
    val formId: FormId
)
