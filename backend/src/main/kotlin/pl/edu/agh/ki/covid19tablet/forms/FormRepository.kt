package pl.edu.agh.ki.covid19tablet.forms

import org.springframework.data.repository.CrudRepository

interface FormRepository : CrudRepository<Form, Long> {
    fun findById(id: Int): Form
}