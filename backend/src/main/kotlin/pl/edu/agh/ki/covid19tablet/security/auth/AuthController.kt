package pl.edu.agh.ki.covid19tablet.security.auth

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pl.edu.agh.ki.covid19tablet.PatientUnauthorizedException
import pl.edu.agh.ki.covid19tablet.security.auth.dto.EmployeeLoginRequest
import pl.edu.agh.ki.covid19tablet.security.auth.dto.EmployeeLoginResponse
import pl.edu.agh.ki.covid19tablet.security.auth.dto.PatientLoginRequest
import pl.edu.agh.ki.covid19tablet.security.auth.dto.PatientLoginResponse

@RestController
@RequestMapping("/login")
class AuthController(
    val authService: AuthService
) {
    @PostMapping("/employee")
    @PreAuthorize("isAnonymous()")
    fun loginEmployee(@RequestBody request: EmployeeLoginRequest): EmployeeLoginResponse =
        authService.loginEmployee(request)

    @PostMapping("/patient")
    @PreAuthorize("isAnonymous()")
    fun loginPatient(@RequestBody request: PatientLoginRequest): ResponseEntity<PatientLoginResponse> =
        try {
            val response = authService.loginPatient(request)
            ResponseEntity(response, HttpStatus.OK)
        } catch (ex: PatientUnauthorizedException) {
            ResponseEntity(HttpStatus.UNAUTHORIZED)
        }

}
