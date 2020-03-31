package pl.edu.agh.ki.covid19tablet.schema

import pl.edu.agh.ki.covid19tablet.schema.dto.SchemaDTO
import pl.edu.agh.ki.covid19tablet.schema.fields.SchemaFields
import pl.edu.agh.ki.covid19tablet.schema.fields.toDTO
import javax.persistence.Embedded
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

typealias SchemaId = Long

@Entity
class Schema(
    @Id
    @GeneratedValue
    val id: SchemaId? = null,
    val name: String,
    @Embedded
    val fields: SchemaFields
)

fun Schema.toDTO() = SchemaDTO(
    id = id!!,
    name = name,
    fields = fields.toDTO()
)
