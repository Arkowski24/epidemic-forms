package pl.edu.agh.ki.covid19tablet

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class Covid19TabletApplication

@Suppress("SpreadOperator")
fun main(args: Array<String>) {
    runApplication<Covid19TabletApplication>(*args)
}
