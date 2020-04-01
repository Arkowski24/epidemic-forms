package pl.edu.agh.ki.covid19tablet.schema

import pl.edu.agh.ki.covid19tablet.schema.dto.SchemaDTO
import pl.edu.agh.ki.covid19tablet.schema.fields.SchemaFields
import pl.edu.agh.ki.covid19tablet.schema.fields.toDTO
import pl.edu.agh.ki.covid19tablet.schema.sign.SignField
import pl.edu.agh.ki.covid19tablet.schema.sign.toDTO
import javax.persistence.CascadeType
import javax.persistence.Embedded
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.OneToOne

typealias SchemaId = Long

@Entity
class Schema(
    @Id
    @GeneratedValue
    val id: SchemaId? = null,
    val name: String,

    @Embedded
    val fields: SchemaFields,

    @OneToOne(cascade = [CascadeType.ALL])
    val patientSign: SignField,
    @OneToOne(cascade = [CascadeType.ALL])
    val employeeSign: SignField
)

fun Schema.toDTO() = SchemaDTO(
    id = id!!,
    name = name,
    fields = fields.toDTO(),
    patientSign = patientSign.toDTO(),
    employeeSign = employeeSign.toDTO()
)
