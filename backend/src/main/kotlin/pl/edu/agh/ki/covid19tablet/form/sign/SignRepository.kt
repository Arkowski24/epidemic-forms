package pl.edu.agh.ki.covid19tablet.form.sign

import org.springframework.data.repository.CrudRepository

interface SignRepository : CrudRepository<Sign, SignId>
