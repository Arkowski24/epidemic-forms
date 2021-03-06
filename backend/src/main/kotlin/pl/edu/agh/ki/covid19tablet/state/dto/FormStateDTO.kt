package pl.edu.agh.ki.covid19tablet.state.dto

import pl.edu.agh.ki.covid19tablet.state.fields.dto.ChoiceFieldStateDTO
import pl.edu.agh.ki.covid19tablet.state.fields.dto.DerivedFieldStateDTO
import pl.edu.agh.ki.covid19tablet.state.fields.dto.SliderFieldStateDTO
import pl.edu.agh.ki.covid19tablet.state.fields.dto.TextFieldStateDTO

data class FormStateDTO(
    val choice: List<ChoiceFieldStateDTO> = listOf(),
    val derived: List<DerivedFieldStateDTO> = listOf(),
    val slider: List<SliderFieldStateDTO> = listOf(),
    val text: List<TextFieldStateDTO> = listOf()
)
