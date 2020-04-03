package pl.edu.agh.ki.covid19tablet.user.employee

import pl.edu.agh.ki.covid19tablet.user.employee.Authorities.FORM_CREATE
import pl.edu.agh.ki.covid19tablet.user.employee.Authorities.FORM_MODIFY
import pl.edu.agh.ki.covid19tablet.user.employee.Roles.ROLE_ADMIN
import pl.edu.agh.ki.covid19tablet.user.employee.Roles.ROLE_EMPLOYEE


object Authorities {
    const val FORM_CREATE = "FORM_CREATE"
    const val FORM_MODIFY = "FORM_MODIFY"
}

object Roles {
    const val ROLE_EMPLOYEE = "ROLE_EMPLOYEE"
    const val ROLE_ADMIN = "ROLE_ADMIN"
}

enum class EmployeeRole(val authorities: List<String>) {
    EMPLOYEE(
        authorities = listOf(
            ROLE_EMPLOYEE,
            FORM_CREATE,
            FORM_MODIFY
        )
    ),
    ADMIN(
        authorities = listOf(
            ROLE_ADMIN,
            FORM_CREATE,
            FORM_MODIFY
        )
    )
}
