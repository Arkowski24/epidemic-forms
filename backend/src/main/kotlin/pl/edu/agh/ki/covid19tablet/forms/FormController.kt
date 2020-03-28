package pl.edu.agh.ki.covid19tablet.forms

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class FormController (val repository: FormRepository) {

    @GetMapping("/")
    fun greet() = println("Welcome to the Form Controller!")
}
