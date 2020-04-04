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
    val fieldType: FieldType = FieldType.NORMAL,

    val title: String,
    val description: String = "",
    val inline: Boolean = false,

    @ElementCollection
    val choices: List<String> = listOf(),
    val multiChoice: Boolean = false
)

fun ChoiceField.toDTO() =
    ChoiceFieldDTO(
        id = id!!,
        fieldNumber = fieldNumber,
        fieldType = fieldType,
        title = title,
        description = description,
        inline = inline,
        choices = choices,
        multiChoice = multiChoice
    )

fun ChoiceField.buildInitialState() =
    ChoiceFieldState(
        field = this,
        value = choices.map { false }
    )
