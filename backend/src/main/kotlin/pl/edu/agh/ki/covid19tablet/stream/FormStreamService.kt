package pl.edu.agh.ki.covid19tablet.stream

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Service
import pl.edu.agh.ki.covid19tablet.form.FormId
import pl.edu.agh.ki.covid19tablet.form.FormService
import pl.edu.agh.ki.covid19tablet.state.FormStateService
import pl.edu.agh.ki.covid19tablet.stream.dto.FormStateRequest
import pl.edu.agh.ki.covid19tablet.stream.dto.FormStateRequestType
import pl.edu.agh.ki.covid19tablet.stream.dto.FormStateResponse
import pl.edu.agh.ki.covid19tablet.stream.dto.FormStateResponseType
import pl.edu.agh.ki.covid19tablet.stream.dto.update.ChoiceFieldStateUpdate
import pl.edu.agh.ki.covid19tablet.stream.dto.update.SignFieldStateUpdate
import pl.edu.agh.ki.covid19tablet.stream.dto.update.SliderFieldStateUpdate
import pl.edu.agh.ki.covid19tablet.stream.dto.update.TextFieldStateUpdate

interface FormStreamService {
    fun handleRequest(formId: FormId, request: FormStateRequest): FormStateResponse
}

@Service
class FormStreamServiceImpl(
    val formService: FormService,
    val formStateService: FormStateService,
    val objectMapper: ObjectMapper
) : FormStreamService {
    override fun handleRequest(formId: FormId, request: FormStateRequest): FormStateResponse {
        when (request.requestType) {
            FormStateRequestType.GET_STATE -> {
                val formState = formService.getForm(formId).state
                val formStateAsJSON = serializeState(formState)

                return FormStateResponse(
                    responseType = FormStateResponseType.STATE,
                    payload = formStateAsJSON
                )
            }
            FormStateRequestType.UPDATE_CHOICE -> {
                val updatePayload = objectMapper.readValue(request.payload, ChoiceFieldStateUpdate::class.java)
                formStateService.modifyChoiceFieldState(updatePayload.id, updatePayload.newValues)

                return FormStateResponse(
                    responseType = FormStateResponseType.UPDATE_CHOICE,
                    payload = request.payload!!
                )
            }
            FormStateRequestType.UPDATE_SIGN -> {
                val updatePayload = objectMapper.readValue(request.payload, SignFieldStateUpdate::class.java)
                formStateService.modifySignFieldState(updatePayload.id, updatePayload.newValue)

                return FormStateResponse(
                    responseType = FormStateResponseType.UPDATE_SIGN,
                    payload = request.payload!!
                )
            }
            FormStateRequestType.UPDATE_SLIDER -> {
                val updatePayload = objectMapper.readValue(request.payload, SliderFieldStateUpdate::class.java)
                formStateService.modifySliderFieldState(updatePayload.id, updatePayload.newValue)

                return FormStateResponse(
                    responseType = FormStateResponseType.UPDATE_SLIDER,
                    payload = request.payload!!
                )
            }
            FormStateRequestType.UPDATE_TEXT -> {
                val updatePayload = objectMapper.readValue(request.payload, TextFieldStateUpdate::class.java)
                formStateService.modifyTextFieldState(updatePayload.id, updatePayload.newValue)

                return FormStateResponse(
                    responseType = FormStateResponseType.UPDATE_TEXT,
                    payload = request.payload!!
                )
            }
        }
    }

    private fun serializeState(state: Any): String =
        objectMapper.writeValueAsString(state)
}
