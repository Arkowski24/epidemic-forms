package pl.edu.agh.ki.covid19tablet.schema

import pl.edu.agh.ki.covid19tablet.schema.dto.SchemaDTO
import pl.edu.agh.ki.covid19tablet.schema.fields.SchemaFields
import pl.edu.agh.ki.covid19tablet.schema.fields.toDTO
import pl.edu.agh.ki.covid19tablet.schema.signature.SignatureField
import pl.edu.agh.ki.covid19tablet.schema.signature.toDTO
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
    val multiPage: Boolean = false,

    @OneToOne(cascade = [CascadeType.ALL])
    val patientSignature: SignatureField,
    @OneToOne(cascade = [CascadeType.ALL])
    val employeeSignature: SignatureField
)

fun Schema.toDTO() = SchemaDTO(
    id = id!!,
    name = name,
    fields = fields.toDTO(),
    multiPage = multiPage,
    patientSignature = patientSignature.toDTO(),
    employeeSignature = employeeSignature.toDTO()
)
