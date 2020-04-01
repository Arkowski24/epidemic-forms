package pl.edu.agh.ki.covid19tablet.schema.sign

import pl.edu.agh.ki.covid19tablet.schema.sign.dto.SignFieldDTO
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

typealias SignFieldId = Long

@Entity
data class SignField(
    @Id
    @GeneratedValue
    val id: SignFieldId? = null,

    val title: String,
    val description: String = ""
)

fun SignField.toDTO() =
    SignFieldDTO(
        id = id!!,
        title = title,
        description = description
    )
