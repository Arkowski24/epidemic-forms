package pl.edu.agh.ki.covid19tablet.schema.signature

import pl.edu.agh.ki.covid19tablet.schema.signature.dto.SignatureFieldDTO
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

typealias SignFieldId = Long

@Entity
data class SignatureField(
    @Id
    @GeneratedValue
    val id: SignFieldId? = null,

    val title: String,
    val description: String = ""
)

fun SignatureField.toDTO() =
    SignatureFieldDTO(
        id = id!!,
        title = title,
        description = description
    )
