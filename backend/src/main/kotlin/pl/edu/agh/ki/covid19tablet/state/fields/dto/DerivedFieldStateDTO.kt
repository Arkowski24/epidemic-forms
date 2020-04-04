package pl.edu.agh.ki.covid19tablet.state.fields.dto

import pl.edu.agh.ki.covid19tablet.schema.fields.DerivedFieldId
import pl.edu.agh.ki.covid19tablet.state.fields.DerivedFieldStateId

data class DerivedFieldStateDTO(
    val id: DerivedFieldStateId,
    val fieldId: DerivedFieldId,
    val fieldNumber: Int,
    val value: List<String>
)
