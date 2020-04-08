package pl.edu.agh.ki.covid19tablet.user.patient

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface PatientRepository : CrudRepository<Patient, PatientId>
