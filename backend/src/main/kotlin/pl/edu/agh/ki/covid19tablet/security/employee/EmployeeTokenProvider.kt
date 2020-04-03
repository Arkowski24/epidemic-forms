package pl.edu.agh.ki.covid19tablet.security.employee

import org.springframework.stereotype.Component
import pl.edu.agh.ki.covid19tablet.security.TokenService
import pl.edu.agh.ki.covid19tablet.security.setExpiration
import pl.edu.agh.ki.covid19tablet.user.employee.EmployeeId
import java.time.Duration
import java.time.Instant

@Component
class EmployeeTokenProvider(
    private val tokenService: TokenService
) {
    fun createToken(employeeId: EmployeeId) =
        tokenService
            .createToken {
                setExpiration(Instant.now() + TokenLifetime)
                setSubject(employeeId.toString())
            }

    fun parseToken(token: String) =
        kotlin.runCatching {
            tokenService
                .parseToken(token)
                .subject
                .toLong()
        }
            .getOrNull()

    companion object {
        private val TokenLifetime = Duration.ofDays(7)
    }
}
