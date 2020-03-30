package pl.edu.agh.ki.covid19tablet.schema.fields

import pl.edu.agh.ki.covid19tablet.schema.fields.dto.SimpleFieldDTO
import javax.persistence.Entity
import javax.persistence.Id

typealias SimpleFieldId = Long

@Entity
data class SimpleField(
    @Id
    val id: SimpleFieldId? = null,
    val order: Int,
    val description: String
)

fun SimpleField.toDTO() =
    SimpleFieldDTO(
        id = id!!,
        order = order,
        description = description
    )
