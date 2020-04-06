package pl.edu.agh.ki.covid19tablet.schema.fields

import pl.edu.agh.ki.covid19tablet.schema.fields.dto.TextFieldDTO
import pl.edu.agh.ki.covid19tablet.state.fields.TextFieldState
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
    val fieldType: FieldType = FieldType.NORMAL,

    val title: String,
    val description: String = "",
    val inline: Boolean = true,

    val multiLine: Boolean = false,

    val required: Boolean = false
)

fun TextField.toDTO() =
    TextFieldDTO(
        id = id!!,
        fieldNumber = fieldNumber,
        fieldType = fieldType,
        title = title,
        description = description,
        inline = inline,
        multiLine = multiLine,
        required = required
    )

fun TextField.buildInitialState() =
    TextFieldState(
        field = this,
        value = ""
    )
