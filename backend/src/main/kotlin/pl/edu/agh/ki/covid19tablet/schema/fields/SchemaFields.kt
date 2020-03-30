package pl.edu.agh.ki.covid19tablet.schema.fields

import pl.edu.agh.ki.covid19tablet.form.state.FormState
import pl.edu.agh.ki.covid19tablet.schema.fields.dto.SchemaFieldsDTO
import javax.persistence.Embeddable
import javax.persistence.OneToMany

@Embeddable
data class SchemaFields(
    @OneToMany
    val choice: List<ChoiceField> = listOf(),
    @OneToMany
    val sign: List<SignField> = listOf(),
    @OneToMany
    val simple: List<SimpleField> = listOf(),
    @OneToMany
    val slider: List<SliderField> = listOf(),
    @OneToMany
    val text: List<TextField> = listOf()
)

fun SchemaFields.toDTO() =
    SchemaFieldsDTO(
        choice = choice.map { it.toDTO() },
        sign = sign.map { it.toDTO() },
        simple = simple.map { it.toDTO() },
        slider = slider.map { it.toDTO() },
        text = text.map { it.toDTO() }
    )

fun SchemaFields.buildInitialState() =
    FormState(
        choice = choice.map { it.buildInitialState() },
        sign = sign.map { it.buildInitialState() },
        slider = slider.map { it.buildInitialState() },
        text = text.map { it.buildInitialState() }
    )
