package pl.edu.agh.ki.covid19tablet.formState.fields

import pl.edu.agh.ki.covid19tablet.formState.fields.dto.SignFieldStateDTO
import pl.edu.agh.ki.covid19tablet.schema.fields.SignField
import java.util.Base64
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Lob
import javax.persistence.ManyToOne

typealias SignFieldStateId = Long

@Entity
data class SignFieldState(
    @Id
    @GeneratedValue
    val id: SignFieldStateId? = null,
    @ManyToOne
    val field: SignField,
    @Lob
    val value: ByteArray
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SignFieldState

        if (id != other.id) return false
        if (!value.contentEquals(other.value)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + value.contentHashCode()
        return result
    }
}

fun SignFieldState.toDTO() =
    SignFieldStateDTO(
        id = id!!,
        fieldId = field.id!!,
        value = Base64.getUrlEncoder().encodeToString(value)
    )
