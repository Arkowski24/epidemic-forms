package pl.edu.agh.ki.covid19tablet.user.employee

import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import pl.edu.agh.ki.covid19tablet.EmployeeNotFoundException
import pl.edu.agh.ki.covid19tablet.EmployeeSelfDeletionException
import pl.edu.agh.ki.covid19tablet.security.employee.EmployeeDetails
import pl.edu.agh.ki.covid19tablet.user.dto.CreateEmployeeRequest
import pl.edu.agh.ki.covid19tablet.user.dto.EmployeeDTO
import pl.edu.agh.ki.covid19tablet.user.dto.ModifyEmployeeRequest

interface EmployeeService {
    fun getAllEmployees(): List<EmployeeDTO>
    fun getEmployee(employeeId: EmployeeId): EmployeeDTO

    fun createEmployee(request: CreateEmployeeRequest): EmployeeDTO
    fun modifyEmployee(employeeId: EmployeeId, request: ModifyEmployeeRequest): EmployeeDTO

    fun deleteEmployee(employeeDetails: EmployeeDetails, employeeId: EmployeeId)
}

@Service
class EmployeeServiceImpl(
    private val employeeRepository: EmployeeRepository,
    private val passwordEncoder: PasswordEncoder
) : EmployeeService {
    override fun getAllEmployees(): List<EmployeeDTO> =
        employeeRepository
            .findAll()
            .map { it.toDTO() }

    override fun getEmployee(employeeId: EmployeeId): EmployeeDTO =
        employeeRepository
            .findById(employeeId)
            .orElseThrow { EmployeeNotFoundException() }
            .toDTO()

    override fun createEmployee(request: CreateEmployeeRequest): EmployeeDTO {
        return employeeRepository.save(
            Employee(
                username = request.username,
                fullName = request.fullName,
                passwordHash = passwordEncoder.encode(request.password),
                role = request.role
            )
        ).toDTO()

    }

    override fun modifyEmployee(employeeId: EmployeeId, request: ModifyEmployeeRequest): EmployeeDTO {
        val employee = employeeRepository
            .findById(employeeId)
            .orElseThrow { EmployeeNotFoundException() }

        return employeeRepository.save(
            employee.copy(
                username = request.username ?: employee.username,
                fullName = request.fullName ?: employee.fullName,
                passwordHash = request.password.let { passwordEncoder.encode(it) } ?: employee.passwordHash,
                role = request.role ?: employee.role
            )
        ).toDTO()
    }

    override fun deleteEmployee(employeeDetails: EmployeeDetails, employeeId: EmployeeId) {
        if (employeeDetails.employee.id!! == employeeId) {
            throw EmployeeSelfDeletionException()
        }

        val employee = employeeRepository
            .findById(employeeId)
            .orElseThrow { EmployeeNotFoundException() }
        employeeRepository.delete(employee)
    }
}
