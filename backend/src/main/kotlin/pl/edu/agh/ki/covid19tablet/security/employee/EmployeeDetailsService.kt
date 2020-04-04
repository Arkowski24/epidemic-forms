package pl.edu.agh.ki.covid19tablet.security.employee

import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Component
import pl.edu.agh.ki.covid19tablet.user.employee.EmployeeId
import pl.edu.agh.ki.covid19tablet.user.employee.EmployeeRepository

@Component
class EmployeeDetailsService(
    val employeeRepository: EmployeeRepository
) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        val employee = employeeRepository.findByUsername(username)
            ?: throw UsernameNotFoundException("Employee not found with username: $username")

        return EmployeeDetails(employee)
    }

    fun loadUserById(employeeId: EmployeeId): UserDetails? =
        employeeRepository.findByIdOrNull(employeeId)
            ?.let { employee ->
                EmployeeDetails(
                    employee
                )
            }
}
