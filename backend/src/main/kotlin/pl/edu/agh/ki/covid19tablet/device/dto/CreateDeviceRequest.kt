package pl.edu.agh.ki.covid19tablet.device.dto

data class CreateDeviceRequest(
    val username: String,
    val password: String,
    val fullName: String?
)
