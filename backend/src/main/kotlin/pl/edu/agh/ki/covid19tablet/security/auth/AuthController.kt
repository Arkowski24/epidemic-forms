package pl.edu.agh.ki.covid19tablet.security.auth

import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pl.edu.agh.ki.covid19tablet.security.auth.dto.LoginRequest
import pl.edu.agh.ki.covid19tablet.security.auth.dto.LoginResponse

@RestController
@RequestMapping("/login")
class AuthController(
    val authService: AuthService
) {
    @PostMapping("/employee")
    @PreAuthorize("isAnonymous()")
    fun login(@RequestBody request: LoginRequest): LoginResponse =
        authService.login(request)

}
