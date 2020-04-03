package pl.edu.agh.ki.covid19tablet.security

import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import pl.edu.agh.ki.covid19tablet.security.employee.EmployeeDetailsService
import pl.edu.agh.ki.covid19tablet.security.employee.EmployeeTokenProvider
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class TokenAuthenticationFilter(
    private val employeeTokenProvider: EmployeeTokenProvider,
    private val employeeDetailsService: EmployeeDetailsService
) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        SecurityContextHolder.getContext().authentication = resolveAuthentication(request)
        filterChain.doFilter(request, response)
    }

    private fun resolveAuthentication(request: HttpServletRequest): Authentication? =
        getTokenFromRequest(request)
            ?.let { token -> employeeTokenProvider.parseToken(token) }
            ?.let { employeeId -> employeeDetailsService.loadUserById(employeeId) }
            ?.let { userDetails ->
                UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)
                    .apply { details = WebAuthenticationDetailsSource().buildDetails(request) }
            }

    private fun getTokenFromRequest(request: HttpServletRequest): String? =
        request
            .getHeader(HttpHeaders.AUTHORIZATION)
            ?.removePrefix("Bearer ")
}
