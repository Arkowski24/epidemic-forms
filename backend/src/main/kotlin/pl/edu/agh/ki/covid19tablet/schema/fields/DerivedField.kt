package pl.edu.agh.ki.covid19tablet.schema.fields

import pl.edu.agh.ki.covid19tablet.schema.fields.dto.DerivedFieldDTO
import pl.edu.agh.ki.covid19tablet.state.fields.DerivedFieldState
import javax.persistence.ElementCollection
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

typealias DerivedFieldId = Long

enum class DerivedType {
    BIRTHDAY_PESEL
}

@Entity
data class DerivedField(
    @Id
    @GeneratedValue
    val id: DerivedFieldId? = null,
    val fieldNumber: Int,
    val fieldType: FieldType = FieldType.NORMAL,

    val derivedType: DerivedType,
    @ElementCollection
    val titles: List<String>,
    @ElementCollection
    val descriptions: List<String> = listOf(),
    val inline: Boolean = true
)

fun DerivedField.toDTO() =
    DerivedFieldDTO(
        id = id!!,
        fieldNumber = fieldNumber,
        fieldType = fieldType,
        derivedType = derivedType,
        titles = titles,
        descriptions = descriptions,
        inline = inline
    )

fun DerivedField.buildInitialState() =
    DerivedFieldState(
        field = this,
        value = titles.map { "" }
    )
