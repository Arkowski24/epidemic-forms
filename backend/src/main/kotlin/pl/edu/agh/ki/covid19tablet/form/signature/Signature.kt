package pl.edu.agh.ki.covid19tablet.form.signature

import org.hibernate.annotations.Type
import pl.edu.agh.ki.covid19tablet.form.signature.dto.SignatureDTO
import java.time.Instant
import java.util.Base64
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Lob

typealias SignatureId = Long

@Entity
data class Signature(
    @Id
    @GeneratedValue
    val id: SignatureId? = null,
    val createdAt: Instant = Instant.now(),

    @Lob
    @Type(type = "org.hibernate.type.ImageType")
    val value: ByteArray = byteArrayOf()
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Signature

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

fun Signature.toDTO() =
    SignatureDTO(
        id = id!!,
        value = Base64.getEncoder().encodeToString(value)
    )
