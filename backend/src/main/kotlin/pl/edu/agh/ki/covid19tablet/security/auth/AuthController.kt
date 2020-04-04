package pl.edu.agh.ki.covid19tablet.security.auth

import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pl.edu.agh.ki.covid19tablet.PatientUnauthorizedException
import pl.edu.agh.ki.covid19tablet.security.auth.dto.EmployeeLoginRequest
import pl.edu.agh.ki.covid19tablet.security.auth.dto.EmployeeLoginResponse
import pl.edu.agh.ki.covid19tablet.security.auth.dto.PatientLoginRequest
import pl.edu.agh.ki.covid19tablet.security.auth.dto.PatientLoginResponse
import pl.edu.agh.ki.covid19tablet.security.employee.EmployeeDetails
import pl.edu.agh.ki.covid19tablet.user.dto.EmployeeDTO
import pl.edu.agh.ki.covid19tablet.user.dto.PatientDTO
import pl.edu.agh.ki.covid19tablet.user.employee.toDTO

@RestController
@RequestMapping("/auth")
class AuthController(
    val authService: AuthService
) {
    @PostMapping("/login/employee")
    @PreAuthorize("isAnonymous()")
    fun loginEmployee(@RequestBody request: EmployeeLoginRequest): EmployeeLoginResponse =
        authService.loginEmployee(request)

    @PostMapping("/login/patient")
    @PreAuthorize("isAnonymous()")
    fun loginPatient(@RequestBody request: PatientLoginRequest): ResponseEntity<PatientLoginResponse> =
        try {
            val response = authService.loginPatient(request)
            ResponseEntity(response, HttpStatus.OK)
        } catch (ex: PatientUnauthorizedException) {
            ResponseEntity(HttpStatus.UNAUTHORIZED)
        }

    @GetMapping("/me/employee")
    @PreAuthorize("isAuthenticated()")
    fun getCurrentEmployee(@AuthenticationPrincipal principal: EmployeeDetails): EmployeeDTO =
        principal
            .employee
            .toDTO()

    @GetMapping("/me/patient")
    fun getCurrentPatient(
        @RequestHeader(HttpHeaders.AUTHORIZATION) authorization: String
    ): ResponseEntity<PatientDTO> =
        try {
            val token = authorization.removePrefix("Bearer ")
            val patient = authService.getCurrentPatient(token)
            ResponseEntity(patient, HttpStatus.OK)
        } catch (ex: PatientUnauthorizedException) {
            ResponseEntity(HttpStatus.UNAUTHORIZED)
        }
}
