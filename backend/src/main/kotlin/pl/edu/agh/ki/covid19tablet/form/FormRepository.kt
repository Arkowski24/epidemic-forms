package pl.edu.agh.ki.covid19tablet.form

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import pl.edu.agh.ki.covid19tablet.user.employee.Employee

@Repository
interface FormRepository : CrudRepository<Form, FormId> {
    fun findAllByCreatedBy(createdBy: Employee): List<Form>
}
