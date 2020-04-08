package pl.edu.agh.ki.covid19tablet.user.employee

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pl.edu.agh.ki.covid19tablet.EmployeeNotFoundException
import pl.edu.agh.ki.covid19tablet.EmployeeSelfDeletionException
import pl.edu.agh.ki.covid19tablet.security.employee.EmployeeDetails
import pl.edu.agh.ki.covid19tablet.user.dto.CreateEmployeeRequest
import pl.edu.agh.ki.covid19tablet.user.dto.EmployeeDTO
import pl.edu.agh.ki.covid19tablet.user.dto.ModifyEmployeeRequest
import javax.validation.Valid

@RestController
@RequestMapping("/employee")
class EmployeeController(
    private val employeeService: EmployeeService
) {
    @GetMapping
    @PreAuthorize("hasAuthority('${Authorities.EMPLOYEE_READ}')")
    fun getAllEmployees(): List<EmployeeDTO> =
        employeeService.getAllEmployees()

    @GetMapping("{employeeId}")
    @PreAuthorize("hasAuthority('${Authorities.EMPLOYEE_READ}')")
    fun getEmployees(@PathVariable employeeId: EmployeeId): ResponseEntity<EmployeeDTO> =
        try {
            val employee = employeeService.getEmployee(employeeId)
            ResponseEntity(employee, HttpStatus.OK)
        } catch (ex: EmployeeNotFoundException) {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }

    @PostMapping
    @PreAuthorize("hasAuthority('${Authorities.EMPLOYEE_CREATE}')")
    fun createEmployee(@Valid @RequestBody request: CreateEmployeeRequest): EmployeeDTO =
        employeeService.createEmployee(request)

    @PutMapping
    @PreAuthorize("hasAuthority('${Authorities.EMPLOYEE_MODIFY}')")
    fun modifyEmployee(
        @PathVariable employeeId: EmployeeId,
        @Valid @RequestBody request: ModifyEmployeeRequest
    ): ResponseEntity<EmployeeDTO> =
        try {
            val employee = employeeService.modifyEmployee(employeeId, request)
            ResponseEntity(employee, HttpStatus.OK)
        } catch (ex: EmployeeNotFoundException) {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }

    @DeleteMapping("{deviceId}")
    @PreAuthorize("hasAuthority('${Authorities.EMPLOYEE_DELETE}')")
    fun deleteDevice(
        @AuthenticationPrincipal employeeDetails: EmployeeDetails,
        @PathVariable employeeId: EmployeeId
    ): ResponseEntity<Nothing> =
        try {
            employeeService.deleteEmployee(employeeDetails, employeeId)
            ResponseEntity(HttpStatus.OK)
        } catch (ex: EmployeeNotFoundException) {
            ResponseEntity(HttpStatus.NOT_FOUND)
        } catch (ex: EmployeeSelfDeletionException) {
            ResponseEntity(HttpStatus.BAD_REQUEST)
        }
}
