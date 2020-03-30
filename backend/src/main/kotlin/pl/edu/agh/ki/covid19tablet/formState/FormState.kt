package pl.edu.agh.ki.covid19tablet.formState

import pl.edu.agh.ki.covid19tablet.formState.dto.FormStateDTO
import pl.edu.agh.ki.covid19tablet.formState.fields.ChoiceFieldState
import pl.edu.agh.ki.covid19tablet.formState.fields.SignFieldState
import pl.edu.agh.ki.covid19tablet.formState.fields.SliderFieldState
import pl.edu.agh.ki.covid19tablet.formState.fields.TextFieldState
import pl.edu.agh.ki.covid19tablet.formState.fields.toDTO
import javax.persistence.CascadeType
import javax.persistence.Embeddable
import javax.persistence.OneToMany

@Embeddable
data class FormState(
    @OneToMany(cascade = [CascadeType.ALL])
    val choice: List<ChoiceFieldState> = listOf(),
    @OneToMany(cascade = [CascadeType.ALL])
    val sign: List<SignFieldState> = listOf(),
    @OneToMany(cascade = [CascadeType.ALL])
    val slider: List<SliderFieldState> = listOf(),
    @OneToMany(cascade = [CascadeType.ALL])
    val text: List<TextFieldState> = listOf()
)

fun FormState.toDTO() =
    FormStateDTO(
        choice = choice.map { it.toDTO() },
        sign = sign.map { it.toDTO() },
        slider = slider.map { it.toDTO() },
        text = text.map { it.toDTO() }
    )
