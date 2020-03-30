package pl.edu.agh.ki.covid19tablet.schema.fields

import pl.edu.agh.ki.covid19tablet.schema.fields.dto.ChoiceFieldDTO
import javax.persistence.ElementCollection
import javax.persistence.Entity
import javax.persistence.Id

typealias ChoiceFieldId = Long

@Entity
data class ChoiceField(
    @Id
    val id: ChoiceFieldId? = null,
    val order: Int,
    val description: String,
    @ElementCollection
    val choices: List<String> = listOf(),
    val isMultiChoice: Boolean
)

fun ChoiceField.toDTO() =
    ChoiceFieldDTO(
        id = id!!,
        order = order,
        description = description,
        choices = choices,
        isMultiChoice = isMultiChoice
    )
