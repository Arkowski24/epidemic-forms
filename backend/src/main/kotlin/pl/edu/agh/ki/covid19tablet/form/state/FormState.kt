package pl.edu.agh.ki.covid19tablet.form.state

import pl.edu.agh.ki.covid19tablet.form.state.dto.FormStateDTO
import pl.edu.agh.ki.covid19tablet.form.state.fields.ChoiceFieldState
import pl.edu.agh.ki.covid19tablet.form.state.fields.SignFieldState
import pl.edu.agh.ki.covid19tablet.form.state.fields.SliderFieldState
import pl.edu.agh.ki.covid19tablet.form.state.fields.TextFieldState
import pl.edu.agh.ki.covid19tablet.form.state.fields.toDTO
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
