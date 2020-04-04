package pl.edu.agh.ki.covid19tablet.stream.dto.update

import pl.edu.agh.ki.covid19tablet.state.fields.DerivedFieldStateId

data class DerivedFieldStateUpdate(
    val id: DerivedFieldStateId,
    val newValue: List<String>
)
