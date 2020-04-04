package pl.edu.agh.ki.covid19tablet.state

import pl.edu.agh.ki.covid19tablet.state.dto.FormStateDTO
import pl.edu.agh.ki.covid19tablet.state.fields.ChoiceFieldState
import pl.edu.agh.ki.covid19tablet.state.fields.DerivedFieldState
import pl.edu.agh.ki.covid19tablet.state.fields.SliderFieldState
import pl.edu.agh.ki.covid19tablet.state.fields.TextFieldState
import pl.edu.agh.ki.covid19tablet.state.fields.toDTO
import javax.persistence.CascadeType
import javax.persistence.Embeddable
import javax.persistence.OneToMany

@Embeddable
data class FormState(
    @OneToMany(cascade = [CascadeType.ALL])
    val choice: List<ChoiceFieldState> = listOf(),
    @OneToMany(cascade = [CascadeType.ALL])
    val derived: List<DerivedFieldState> = listOf(),
    @OneToMany(cascade = [CascadeType.ALL])
    val slider: List<SliderFieldState> = listOf(),
    @OneToMany(cascade = [CascadeType.ALL])
    val text: List<TextFieldState> = listOf()
)

fun FormState.toDTO() =
    FormStateDTO(
        choice = choice.map { it.toDTO() },
        derived = derived.map { it.toDTO() },
        slider = slider.map { it.toDTO() },
        text = text.map { it.toDTO() }
    )
