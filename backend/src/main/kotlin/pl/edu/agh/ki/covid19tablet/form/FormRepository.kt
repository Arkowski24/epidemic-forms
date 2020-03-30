package pl.edu.agh.ki.covid19tablet.form

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface FormRepository : CrudRepository<Form, FormId>
