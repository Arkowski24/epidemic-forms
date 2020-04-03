package pl.edu.agh.ki.covid19tablet.user.patient

import org.springframework.data.repository.CrudRepository

interface PatientRepository : CrudRepository<Patient, PatientId>
