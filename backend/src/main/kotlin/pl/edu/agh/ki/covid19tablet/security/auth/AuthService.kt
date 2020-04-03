package pl.edu.agh.ki.covid19tablet.security.auth

import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.stereotype.Service
import pl.edu.agh.ki.covid19tablet.security.auth.dto.LoginRequest
import pl.edu.agh.ki.covid19tablet.security.auth.dto.LoginResponse
import pl.edu.agh.ki.covid19tablet.security.employee.EmployeeDetails
import pl.edu.agh.ki.covid19tablet.security.employee.EmployeeTokenProvider

interface AuthService {
    fun login(request: LoginRequest): LoginResponse
}

@Service
class AuthServiceImpl(
    private val authenticationManager: AuthenticationManager,
    private val employeeTokenProvider: EmployeeTokenProvider
) : AuthService {
    override fun login(request: LoginRequest): LoginResponse {
        val authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(request.username, request.password)
        )

        val employee = (authentication.principal as EmployeeDetails).employee
        val token = employeeTokenProvider.createToken(employee.id!!)
        return LoginResponse(token = token)
    }
}
