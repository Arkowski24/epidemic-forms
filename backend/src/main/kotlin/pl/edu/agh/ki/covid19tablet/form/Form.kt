package pl.edu.agh.ki.covid19tablet.form

import pl.edu.agh.ki.covid19tablet.form.dto.FormDTO
import pl.edu.agh.ki.covid19tablet.form.signature.Signature
import pl.edu.agh.ki.covid19tablet.schema.Schema
import pl.edu.agh.ki.covid19tablet.schema.toDTO
import pl.edu.agh.ki.covid19tablet.state.FormState
import pl.edu.agh.ki.covid19tablet.state.toDTO
import pl.edu.agh.ki.covid19tablet.user.employee.Employee
import pl.edu.agh.ki.covid19tablet.user.employee.toDTO
import pl.edu.agh.ki.covid19tablet.user.patient.Patient
import pl.edu.agh.ki.covid19tablet.user.patient.toDTO
import java.time.Instant
import javax.persistence.CascadeType
import javax.persistence.Embedded
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.ManyToOne
import javax.persistence.OneToOne

typealias FormId = Long

@Entity
data class Form(
    @Id
    @GeneratedValue
    val id: FormId? = null,
    val formName: String,
    val status: FormStatus = FormStatus.NEW,

    @ManyToOne
    val schema: Schema,
    @Embedded
    val state: FormState,

    @OneToOne(cascade = [CascadeType.ALL])
    val patient: Patient,
    @ManyToOne
    val createdBy: Employee,
    val createdAt: Instant = Instant.now(),

    @OneToOne(cascade = [CascadeType.ALL])
    val patientSignature: Signature? = null,
    @OneToOne(cascade = [CascadeType.ALL])
    val employeeSignature: Signature? = null
)

fun Form.toDTO() = FormDTO(
    id = id!!,
    formName = formName,
    status = status,
    schema = schema.toDTO(),
    state = state.toDTO(),
    patient = patient.toDTO(),
    createdBy = createdBy.toDTO(),
    createdAt = createdAt
)
