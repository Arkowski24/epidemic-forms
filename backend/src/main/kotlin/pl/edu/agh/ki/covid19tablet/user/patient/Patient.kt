package pl.edu.agh.ki.covid19tablet.user.patient

import pl.edu.agh.ki.covid19tablet.user.dto.PatientDTO
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

typealias PatientId = Long

@Entity
data class Patient(
    @Id
    @GeneratedValue
    val id: PatientId? = null,

    val loggedIn: Boolean = false
)

fun Patient.toDTO() = PatientDTO(
    id = id!!,
    loggedIn = loggedIn
)
