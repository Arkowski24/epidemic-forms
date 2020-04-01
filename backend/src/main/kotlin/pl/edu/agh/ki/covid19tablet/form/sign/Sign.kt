package pl.edu.agh.ki.covid19tablet.form.sign

import org.hibernate.annotations.Type
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Lob

typealias SignId = Long

@Entity
data class Sign(
    @Id
    @GeneratedValue
    val id: SignId,

    @Lob
    @Type(type = "org.hibernate.type.ImageType")
    val value: ByteArray = byteArrayOf()
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Sign

        if (id != other.id) return false
        if (!value.contentEquals(other.value)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + value.contentHashCode()
        return result
    }
}
