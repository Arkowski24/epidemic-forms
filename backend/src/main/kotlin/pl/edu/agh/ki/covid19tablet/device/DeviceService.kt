package pl.edu.agh.ki.covid19tablet.device

import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import pl.edu.agh.ki.covid19tablet.DeviceNotFoundException
import pl.edu.agh.ki.covid19tablet.device.dto.CreateDeviceRequest
import pl.edu.agh.ki.covid19tablet.user.employee.EmployeeRepository
import pl.edu.agh.ki.covid19tablet.user.employee.EmployeeRole
import pl.edu.agh.ki.covid19tablet.user.employee.toDTO

interface DeviceService {
    fun getAllDevices(): List<DeviceDTO>

    fun createDevice(request: CreateDeviceRequest): DeviceDTO
    fun deleteDevice(deviceId: DeviceId)
}

@Service
class DeviceServiceImpl(
    private val employeeRepository: EmployeeRepository,
    private val passwordEncoder: PasswordEncoder
) : DeviceService {
    override fun getAllDevices(): List<DeviceDTO> =
        employeeRepository
            .findAll()
            .filter { it.role == EmployeeRole.DEVICE }
            .map { it.toDTO() }

    override fun createDevice(request: CreateDeviceRequest): DeviceDTO =
        employeeRepository
            .save(
                Device(
                    username = request.username,
                    passwordHash = passwordEncoder.encode(request.password),
                    fullName = request.fullName,
                    role = EmployeeRole.DEVICE
                )
            ).toDTO()

    override fun deleteDevice(deviceId: DeviceId) {
        val device = employeeRepository
            .findById(deviceId)
            .orElseThrow { DeviceNotFoundException() }

        employeeRepository.delete(device)
    }
}
