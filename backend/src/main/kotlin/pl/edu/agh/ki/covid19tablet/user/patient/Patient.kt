package pl.edu.agh.ki.covid19tablet.user.patient

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

typealias PatientId = Long

@Entity
data class Patient(
    @Id
    @GeneratedValue
    val id: PatientId? = null,

    val pinCode: String
)
