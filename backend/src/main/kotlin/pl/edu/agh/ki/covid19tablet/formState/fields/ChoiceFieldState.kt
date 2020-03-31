package pl.edu.agh.ki.covid19tablet.formState.fields

import pl.edu.agh.ki.covid19tablet.formState.fields.dto.ChoiceFieldStateDTO
import pl.edu.agh.ki.covid19tablet.schema.fields.ChoiceField
import javax.persistence.ElementCollection
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.ManyToOne

typealias ChoiceFieldStateId = Long

@Entity
data class ChoiceFieldState(
    @Id
    @GeneratedValue
    val id: ChoiceFieldStateId? = null,
    @ManyToOne
    val field: ChoiceField,
    @ElementCollection
    val values: List<Boolean>
)

fun ChoiceFieldState.toDTO() =
    ChoiceFieldStateDTO(
        id = id!!,
        fieldId = field.id!!,
        values = values
    )
