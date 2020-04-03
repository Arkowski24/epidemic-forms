package pl.edu.agh.ki.covid19tablet.security.auth

import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.stereotype.Service
import pl.edu.agh.ki.covid19tablet.PatientUnauthorizedException
import pl.edu.agh.ki.covid19tablet.security.auth.dto.EmployeeLoginRequest
import pl.edu.agh.ki.covid19tablet.security.auth.dto.EmployeeLoginResponse
import pl.edu.agh.ki.covid19tablet.security.auth.dto.PatientLoginRequest
import pl.edu.agh.ki.covid19tablet.security.auth.dto.PatientLoginResponse
import pl.edu.agh.ki.covid19tablet.security.employee.EmployeeDetails
import pl.edu.agh.ki.covid19tablet.security.employee.EmployeeTokenProvider
import pl.edu.agh.ki.covid19tablet.security.patient.PatientTokenProvider
import pl.edu.agh.ki.covid19tablet.user.patient.PatientRepository

interface AuthService {
    fun loginEmployee(request: EmployeeLoginRequest): EmployeeLoginResponse
    fun loginPatient(request: PatientLoginRequest): PatientLoginResponse
}

@Service
class AuthServiceImpl(
    private val authenticationManager: AuthenticationManager,
    private val employeeTokenProvider: EmployeeTokenProvider,
    private val patientTokenProvider: PatientTokenProvider,
    private val patientRepository: PatientRepository
) : AuthService {
    override fun loginEmployee(request: EmployeeLoginRequest): EmployeeLoginResponse {
        val authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(request.username, request.password)
        )

        val employee = (authentication.principal as EmployeeDetails).employee
        val token = employeeTokenProvider.createToken(employee.id!!)
        return EmployeeLoginResponse(token = token)
    }

    override fun loginPatient(request: PatientLoginRequest): PatientLoginResponse {
        val patient = patientRepository
            .findByIdOrNull(request.pinCode)
            ?.takeIf { !it.loggedIn }
            ?: throw PatientUnauthorizedException()

        val token = patientTokenProvider.createToken(patient.id!!)
        patientRepository.save(patient.copy(loggedIn = true))

        return PatientLoginResponse(
            token = token,
            formId = patient.form?.id!!
        )
    }
}
