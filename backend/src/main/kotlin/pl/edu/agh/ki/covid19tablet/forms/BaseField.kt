package pl.edu.agh.ki.covid19tablet.forms

import javax.persistence.*

@Entity
open class BaseField(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Int? = null,

        @ManyToOne(fetch = FetchType.LAZY, optional = false)
        @JoinColumn(name = "form_id")
        val form: Form,

        @Column(nullable = false)
        var description: String
)
