package pl.edu.agh.ki.covid19tablet.security.patient

import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import pl.edu.agh.ki.covid19tablet.user.patient.PatientId
import pl.edu.agh.ki.covid19tablet.user.patient.PatientRepository

@Component
class PatientDetailsService(
    val patientRepository: PatientRepository
) {
    fun loadPatientById(patientId: PatientId): UserDetails? =
        patientRepository.findByIdOrNull(patientId)
            ?.let { patient ->
                PatientDetails(
                    patient
                )
            }
}
