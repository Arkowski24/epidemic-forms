package pl.edu.agh.ki.covid19tablet.forms

import javax.persistence.*

@Entity
data class Form(

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Int = 0,

        @Column
        @OneToMany(cascade = [(CascadeType.ALL)], fetch = FetchType.LAZY, mappedBy = "form")
        var fields: MutableList<BaseField> = ArrayList()
)
