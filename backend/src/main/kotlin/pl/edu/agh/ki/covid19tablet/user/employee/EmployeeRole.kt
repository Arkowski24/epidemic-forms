package pl.edu.agh.ki.covid19tablet.user.employee

import pl.edu.agh.ki.covid19tablet.user.employee.Authorities.DEVICE_CREATE
import pl.edu.agh.ki.covid19tablet.user.employee.Authorities.DEVICE_DELETE
import pl.edu.agh.ki.covid19tablet.user.employee.Authorities.DEVICE_READ
import pl.edu.agh.ki.covid19tablet.user.employee.Authorities.DEVICE_SUBSCRIBE
import pl.edu.agh.ki.covid19tablet.user.employee.Authorities.FORM_CREATE
import pl.edu.agh.ki.covid19tablet.user.employee.Authorities.FORM_DELETE
import pl.edu.agh.ki.covid19tablet.user.employee.Authorities.FORM_MODIFY
import pl.edu.agh.ki.covid19tablet.user.employee.Authorities.FORM_READ
import pl.edu.agh.ki.covid19tablet.user.employee.Authorities.SCHEMA_READ
import pl.edu.agh.ki.covid19tablet.user.employee.Roles.ROLE_ADMIN
import pl.edu.agh.ki.covid19tablet.user.employee.Roles.ROLE_EMPLOYEE


object Authorities {
    const val FORM_READ = "FORM_READ"
    const val FORM_CREATE = "FORM_CREATE"
    const val FORM_MODIFY = "FORM_MODIFY"
    const val FORM_DELETE = "FORM_DELETE"

    const val SCHEMA_READ = "SCHEMA_READ"

    const val DEVICE_READ = "DEVICE_READ"
    const val DEVICE_CREATE = "DEVICE_CREATE"
    const val DEVICE_DELETE = "DEVICE_DELETE"

    const val DEVICE_SUBSCRIBE = "DEVICE_SUBSCRIBE"
}

object Roles {
    const val ROLE_EMPLOYEE = "ROLE_EMPLOYEE"
    const val ROLE_ADMIN = "ROLE_ADMIN"
}

enum class EmployeeRole(val authorities: List<String>) {
    EMPLOYEE(
        authorities = listOf(
            ROLE_EMPLOYEE,
            FORM_READ,
            FORM_CREATE,
            FORM_MODIFY,
            FORM_DELETE,
            SCHEMA_READ,
            DEVICE_READ
        )
    ),
    ADMIN(
        authorities = listOf(
            ROLE_ADMIN,
            FORM_READ,
            FORM_CREATE,
            FORM_MODIFY,
            FORM_DELETE,
            SCHEMA_READ,
            DEVICE_READ,
            DEVICE_CREATE,
            DEVICE_DELETE
        )
    ),
    DEVICE(
        authorities = listOf(
            DEVICE_SUBSCRIBE
        )
    )
}
