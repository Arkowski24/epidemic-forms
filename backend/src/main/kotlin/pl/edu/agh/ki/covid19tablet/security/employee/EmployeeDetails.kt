package pl.edu.agh.ki.covid19tablet.security.employee

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import pl.edu.agh.ki.covid19tablet.user.employee.Employee

class EmployeeDetails(
    val employee: Employee
) : UserDetails {
    private val _authorities: List<GrantedAuthority> by lazy {
        employee.role.authorities.map(::SimpleGrantedAuthority)
    }

    override fun getUsername(): String = employee.username

    override fun getPassword(): String? = employee.passwordHash

    override fun getAuthorities(): List<GrantedAuthority> = _authorities

    override fun isEnabled() = true

    override fun isCredentialsNonExpired() = true

    override fun isAccountNonExpired() = true

    override fun isAccountNonLocked() = true

    override fun toString(): String = "AuthPrincipal(employeeId=${employee.id})"
}
