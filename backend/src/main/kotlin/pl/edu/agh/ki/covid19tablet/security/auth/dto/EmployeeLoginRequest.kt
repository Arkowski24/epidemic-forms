package pl.edu.agh.ki.covid19tablet.security.auth.dto

data class EmployeeLoginRequest(
    val username: String,
    val password: String
)
