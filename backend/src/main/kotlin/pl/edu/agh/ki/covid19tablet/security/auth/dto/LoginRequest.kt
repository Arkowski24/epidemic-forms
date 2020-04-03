package pl.edu.agh.ki.covid19tablet.security.auth.dto

data class LoginRequest(
    val username: String,
    val password: String
)
