package pl.edu.agh.ki.covid19tablet.schema.fields

import pl.edu.agh.ki.covid19tablet.schema.fields.dto.SchemaFieldsDTO
import pl.edu.agh.ki.covid19tablet.state.FormState
import javax.persistence.CascadeType
import javax.persistence.Embeddable
import javax.persistence.OneToMany

@Embeddable
data class SchemaFields(
    @OneToMany(cascade = [CascadeType.ALL])
    val choice: List<ChoiceField> = listOf(),
    @OneToMany(cascade = [CascadeType.ALL])
    val derived: List<DerivedField> = listOf(),
    @OneToMany(cascade = [CascadeType.ALL])
    val simple: List<SimpleField> = listOf(),
    @OneToMany(cascade = [CascadeType.ALL])
    val slider: List<SliderField> = listOf(),
    @OneToMany(cascade = [CascadeType.ALL])
    val text: List<TextField> = listOf()
)

fun SchemaFields.toDTO() =
    SchemaFieldsDTO(
        choice = choice.map { it.toDTO() },
        derived = derived.map { it.toDTO() },
        simple = simple.map { it.toDTO() },
        slider = slider.map { it.toDTO() },
        text = text.map { it.toDTO() }
    )

fun SchemaFields.buildInitialState() =
    FormState(
        choice = choice.map { it.buildInitialState() },
        derived = derived.map { it.buildInitialState() },
        slider = slider.map { it.buildInitialState() },
        text = text.map { it.buildInitialState() }
    )
