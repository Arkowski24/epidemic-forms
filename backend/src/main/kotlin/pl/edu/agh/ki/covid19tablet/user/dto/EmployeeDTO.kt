package pl.edu.agh.ki.covid19tablet.user.dto

import pl.edu.agh.ki.covid19tablet.user.employee.EmployeeId

data class EmployeeDTO(
    val id: EmployeeId,
    val username: String,
    val fullName: String?
)
