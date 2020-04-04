package pl.edu.agh.ki.covid19tablet.state.fields

import pl.edu.agh.ki.covid19tablet.schema.fields.DerivedField
import pl.edu.agh.ki.covid19tablet.state.fields.dto.DerivedFieldStateDTO
import javax.persistence.ElementCollection
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.ManyToOne

typealias DerivedFieldStateId = Long

@Entity
data class DerivedFieldState(
    @Id
    @GeneratedValue
    val id: DerivedFieldStateId? = null,
    @ManyToOne
    val field: DerivedField,
    @ElementCollection
    val value: List<String>
)

fun DerivedFieldState.toDTO() =
    DerivedFieldStateDTO(
        id = id!!,
        fieldId = field.id!!,
        fieldNumber = field.fieldNumber,
        value = value
    )
