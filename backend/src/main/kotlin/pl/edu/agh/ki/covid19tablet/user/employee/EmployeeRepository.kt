package pl.edu.agh.ki.covid19tablet.user.employee

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface EmployeeRepository : CrudRepository<Employee, EmployeeId> {
    fun findByUsername(username: String): Employee?
}
