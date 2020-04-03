package pl.edu.agh.ki.covid19tablet.user.employee

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

typealias EmployeeId = Long

@Entity
data class Employee(
    @Id
    @GeneratedValue
    val id: EmployeeId? = null,

    val username: String,
    val passwordHash: String,
    val role: EmployeeRole = EmployeeRole.EMPLOYEE
)
