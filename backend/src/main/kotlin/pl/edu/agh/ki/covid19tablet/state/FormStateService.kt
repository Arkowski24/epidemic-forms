package pl.edu.agh.ki.covid19tablet.state

import org.springframework.stereotype.Service
import pl.edu.agh.ki.covid19tablet.FieldNotFoundException
import pl.edu.agh.ki.covid19tablet.state.fields.ChoiceFieldStateId
import pl.edu.agh.ki.covid19tablet.state.fields.SliderFieldStateId
import pl.edu.agh.ki.covid19tablet.state.fields.TextFieldStateId
import pl.edu.agh.ki.covid19tablet.state.fields.dto.ChoiceFieldStateDTO
import pl.edu.agh.ki.covid19tablet.state.fields.dto.SliderFieldStateDTO
import pl.edu.agh.ki.covid19tablet.state.fields.dto.TextFieldStateDTO
import pl.edu.agh.ki.covid19tablet.state.fields.repositories.ChoiceFieldStateRepository
import pl.edu.agh.ki.covid19tablet.state.fields.repositories.SliderFieldStateRepository
import pl.edu.agh.ki.covid19tablet.state.fields.repositories.TextFieldStateRepository
import pl.edu.agh.ki.covid19tablet.state.fields.toDTO

interface FormStateService {
    fun modifyChoiceFieldState(id: ChoiceFieldStateId, values: List<Boolean>): ChoiceFieldStateDTO
    fun modifySliderFieldState(id: SliderFieldStateId, value: Double): SliderFieldStateDTO
    fun modifyTextFieldState(id: TextFieldStateId, value: String): TextFieldStateDTO
}

@Service
class FormStateServiceImpl(
    private val choiceFieldStateRepository: ChoiceFieldStateRepository,
    private val sliderFieldStateRepository: SliderFieldStateRepository,
    private val textFieldStateRepository: TextFieldStateRepository
) : FormStateService {
    override fun modifyChoiceFieldState(id: ChoiceFieldStateId, values: List<Boolean>): ChoiceFieldStateDTO {
        val fieldState = choiceFieldStateRepository
            .findById(id)
            .orElseThrow { FieldNotFoundException() }
            .copy(value = values)

        return choiceFieldStateRepository
            .save(fieldState)
            .toDTO()
    }

    override fun modifySliderFieldState(id: SliderFieldStateId, value: Double): SliderFieldStateDTO {
        val fieldState = sliderFieldStateRepository
            .findById(id)
            .orElseThrow { FieldNotFoundException() }
            .copy(value = value)

        return sliderFieldStateRepository
            .save(fieldState)
            .toDTO()
    }

    override fun modifyTextFieldState(id: TextFieldStateId, value: String): TextFieldStateDTO {
        val fieldState = textFieldStateRepository
            .findById(id)
            .orElseThrow { FieldNotFoundException() }
            .copy(value = value)

        return textFieldStateRepository
            .save(fieldState)
            .toDTO()
    }

}
