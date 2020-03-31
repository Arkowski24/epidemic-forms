package pl.edu.agh.ki.covid19tablet.schema.fields

import pl.edu.agh.ki.covid19tablet.schema.fields.dto.SimpleFieldDTO
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

typealias SimpleFieldId = Long

@Entity
data class SimpleField(
    @Id
    @GeneratedValue
    val id: SimpleFieldId? = null,
    val fieldNumber: Int,
    val description: String
)

fun SimpleField.toDTO() =
    SimpleFieldDTO(
        id = id!!,
        fieldNumber = fieldNumber,
        description = description
    )
