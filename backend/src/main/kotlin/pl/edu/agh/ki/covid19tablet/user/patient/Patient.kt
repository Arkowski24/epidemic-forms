package pl.edu.agh.ki.covid19tablet.user.patient

import pl.edu.agh.ki.covid19tablet.form.Form
import pl.edu.agh.ki.covid19tablet.user.dto.PatientDTO
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.OneToOne

typealias PatientId = Long

@Entity
data class Patient(
    @Id
    @GeneratedValue
    val id: PatientId? = null,

    @OneToOne(mappedBy = "patient")
    val form: Form? = null,
    val loggedIn: Boolean = false
)

fun Patient.toDTO() = PatientDTO(
    id = id!!,
    loggedIn = loggedIn
)
