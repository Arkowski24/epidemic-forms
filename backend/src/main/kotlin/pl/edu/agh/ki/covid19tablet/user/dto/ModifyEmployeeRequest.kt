package pl.edu.agh.ki.covid19tablet.user.dto

import pl.edu.agh.ki.covid19tablet.user.employee.EmployeeRole

data class ModifyEmployeeRequest(
    val username: String?,
    val fullName: String?,
    val password: String?,
    val role: EmployeeRole?
)
