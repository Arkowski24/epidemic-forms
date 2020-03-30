package pl.edu.agh.ki.covid19tablet.formState.dto

import pl.edu.agh.ki.covid19tablet.formState.fields.dto.ChoiceFieldStateDTO
import pl.edu.agh.ki.covid19tablet.formState.fields.dto.SignFieldStateDTO
import pl.edu.agh.ki.covid19tablet.formState.fields.dto.SliderFieldStateDTO
import pl.edu.agh.ki.covid19tablet.formState.fields.dto.TextFieldStateDTO

data class FormStateDTO(
    val choice: List<ChoiceFieldStateDTO> = listOf(),
    val sign: List<SignFieldStateDTO> = listOf(),
    val slider: List<SliderFieldStateDTO> = listOf(),
    val text: List<TextFieldStateDTO> = listOf()
)
