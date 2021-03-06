package pl.edu.agh.ki.covid19tablet.form.dto

import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer
import pl.edu.agh.ki.covid19tablet.device.DeviceDTO
import pl.edu.agh.ki.covid19tablet.form.FormId
import pl.edu.agh.ki.covid19tablet.form.FormStatus
import pl.edu.agh.ki.covid19tablet.schema.dto.SchemaDTO
import pl.edu.agh.ki.covid19tablet.state.dto.FormStateDTO
import pl.edu.agh.ki.covid19tablet.user.dto.EmployeeDTO
import pl.edu.agh.ki.covid19tablet.user.dto.PatientDTO
import java.time.Instant

data class FormDTO(
    val id: FormId,
    val formName: String,
    val status: FormStatus,

    val patient: PatientDTO,
    val createdBy: EmployeeDTO,
    @JsonSerialize(using = ToStringSerializer::class)
    val createdAt: Instant,
    val device: DeviceDTO?,

    val schema: SchemaDTO,
    val state: FormStateDTO
)
