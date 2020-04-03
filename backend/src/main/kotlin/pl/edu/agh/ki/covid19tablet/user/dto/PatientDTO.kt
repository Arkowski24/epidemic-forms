package pl.edu.agh.ki.covid19tablet.user.dto

import pl.edu.agh.ki.covid19tablet.user.patient.PatientId

data class PatientDTO(
    val id: PatientId,
    val loggedIn: Boolean
)
