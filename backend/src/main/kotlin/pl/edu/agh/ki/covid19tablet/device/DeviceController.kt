package pl.edu.agh.ki.covid19tablet.device

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pl.edu.agh.ki.covid19tablet.DeviceNotFoundException
import pl.edu.agh.ki.covid19tablet.device.dto.CreateDeviceRequest
import pl.edu.agh.ki.covid19tablet.user.employee.Authorities
import javax.validation.Valid

@RestController
@RequestMapping("/devices")
class DeviceController(
    private val deviceService: DeviceService
) {
    @GetMapping
    @PreAuthorize("hasAuthority('${Authorities.DEVICE_READ}')")
    fun getAllDevices(): List<DeviceDTO> =
        deviceService.getAllDevices()

    @PostMapping
    @PreAuthorize("hasAuthority('${Authorities.DEVICE_CREATE}')")
    fun createDevice(@Valid @RequestBody request: CreateDeviceRequest): DeviceDTO =
        deviceService.createDevice(request)

    @DeleteMapping("{deviceId}")
    @PreAuthorize("hasAuthority('${Authorities.DEVICE_DELETE}')")
    fun deleteDevice(@PathVariable deviceId: DeviceId): ResponseEntity<Nothing> =
        try {
            deviceService.deleteDevice(deviceId)
            ResponseEntity(HttpStatus.OK)
        } catch (ex: DeviceNotFoundException) {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
}
