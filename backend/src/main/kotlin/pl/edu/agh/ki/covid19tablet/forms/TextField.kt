package pl.edu.agh.ki.covid19tablet.forms

import javax.persistence.Column
import javax.persistence.Entity

@Entity
data class TextField(
        @Column
        var value: String
) : BaseField(description = "", form = Form())
