package pl.edu.agh.ki.covid19tablet.forms

import javax.persistence.Column
import javax.persistence.ElementCollection
import javax.persistence.Entity

@Entity
data class ChoiceField(
        @Column
        @ElementCollection
        var values: Map<String, Boolean> = HashMap()
) : BaseField(description = "", form = Form())
