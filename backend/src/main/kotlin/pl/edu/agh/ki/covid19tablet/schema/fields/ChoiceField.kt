package pl.edu.agh.ki.covid19tablet.schema.fields

import pl.edu.agh.ki.covid19tablet.schema.fields.dto.ChoiceFieldDTO
import pl.edu.agh.ki.covid19tablet.state.fields.ChoiceFieldState
import javax.persistence.ElementCollection
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

typealias ChoiceFieldId = Long

@Entity
data class ChoiceField(
    @Id
    @GeneratedValue
    val id: ChoiceFieldId? = null,
    val fieldNumber: Int,

    val title: String,
    val description: String = "",

    @ElementCollection
    val choices: List<String> = listOf(),
    val isMultiChoice: Boolean = false
)

fun ChoiceField.toDTO() =
    ChoiceFieldDTO(
        id = id!!,
        fieldNumber = fieldNumber,
        title = title,
        description = description,
        choices = choices,
        isMultiChoice = isMultiChoice
    )

fun ChoiceField.buildInitialState() =
    ChoiceFieldState(
        field = this,
        value = choices.map { false }
    )
