package pl.edu.agh.ki.covid19tablet.schema.fields

import pl.edu.agh.ki.covid19tablet.formState.fields.TextFieldState
import pl.edu.agh.ki.covid19tablet.schema.fields.dto.TextFieldDTO
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

typealias TextFieldId = Long

@Entity
data class TextField(
    @Id
    @GeneratedValue
    val id: TextFieldId? = null,
    val fieldNumber: Int,
    val description: String,
    val isMultiline: Boolean = false
)

fun TextField.toDTO() =
    TextFieldDTO(
        id = id!!,
        fieldNumber = fieldNumber,
        description = description,
        isMultiline = isMultiline
    )

fun TextField.buildInitialState() =
    TextFieldState(
        field = this,
        value = ""
    )
