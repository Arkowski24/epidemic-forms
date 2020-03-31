package pl.edu.agh.ki.covid19tablet.formState.fields

import pl.edu.agh.ki.covid19tablet.formState.fields.dto.TextFieldStateDTO
import pl.edu.agh.ki.covid19tablet.schema.fields.TextField
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.ManyToOne

typealias TextFieldStateId = Long

@Entity
data class TextFieldState(
    @Id
    @GeneratedValue
    val id: TextFieldStateId? = null,
    @ManyToOne
    val field: TextField,
    val value: String
)

fun TextFieldState.toDTO() =
    TextFieldStateDTO(
        id = id!!,
        field = field.id!!,
        value = value
    )
