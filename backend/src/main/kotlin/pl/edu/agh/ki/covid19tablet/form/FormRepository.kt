package pl.edu.agh.ki.covid19tablet.form

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import pl.edu.agh.ki.covid19tablet.user.employee.EmployeeId

@Repository
interface FormRepository : CrudRepository<Form, FormId> {
    fun findAllByCreatedBy_Id(id: EmployeeId): List<Form>
}
