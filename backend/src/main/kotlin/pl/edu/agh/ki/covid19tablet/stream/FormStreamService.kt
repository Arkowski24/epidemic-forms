package pl.edu.agh.ki.covid19tablet.stream

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Service
import pl.edu.agh.ki.covid19tablet.form.FormId
import pl.edu.agh.ki.covid19tablet.form.FormService
import pl.edu.agh.ki.covid19tablet.form.FormStatus
import pl.edu.agh.ki.covid19tablet.state.FormStateService
import pl.edu.agh.ki.covid19tablet.stream.dto.FormStateRequest
import pl.edu.agh.ki.covid19tablet.stream.dto.FormStateRequestType
import pl.edu.agh.ki.covid19tablet.stream.dto.FormStateResponse
import pl.edu.agh.ki.covid19tablet.stream.dto.FormStateResponseType
import pl.edu.agh.ki.covid19tablet.stream.dto.update.ChoiceFieldStateUpdate
import pl.edu.agh.ki.covid19tablet.stream.dto.update.DerivedFieldStateUpdate
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
                val formState = formService.getForm(formId)
                val formStateAsJSON = serializeState(formState)

                return FormStateResponse(
                    responseType = FormStateResponseType.STATE,
                    payload = formStateAsJSON
                )
            }

            FormStateRequestType.UPDATE_CHOICE -> {
                val updatePayload = objectMapper.readValue(request.payload, ChoiceFieldStateUpdate::class.java)
                val response = formStateService.modifyChoiceFieldState(updatePayload.id, updatePayload.newValue)

                return FormStateResponse(
                    responseType = FormStateResponseType.UPDATE_CHOICE,
                    payload = objectMapper.writeValueAsString(response)
                )
            }
            FormStateRequestType.UPDATE_DERIVED -> {
                val updatePayload = objectMapper.readValue(request.payload, DerivedFieldStateUpdate::class.java)
                val response = formStateService.modifyDerivedFieldState(updatePayload.id, updatePayload.newValue)

                return FormStateResponse(
                    responseType = FormStateResponseType.UPDATE_DERIVED,
                    payload = objectMapper.writeValueAsString(response)
                )
            }
            FormStateRequestType.UPDATE_SLIDER -> {
                val updatePayload = objectMapper.readValue(request.payload, SliderFieldStateUpdate::class.java)
                val response = formStateService.modifySliderFieldState(updatePayload.id, updatePayload.newValue)

                return FormStateResponse(
                    responseType = FormStateResponseType.UPDATE_SLIDER,
                    payload = objectMapper.writeValueAsString(response)
                )
            }
            FormStateRequestType.UPDATE_TEXT -> {
                val updatePayload = objectMapper.readValue(request.payload, TextFieldStateUpdate::class.java)
                val response = formStateService.modifyTextFieldState(updatePayload.id, updatePayload.newValue)

                return FormStateResponse(
                    responseType = FormStateResponseType.UPDATE_TEXT,
                    payload = objectMapper.writeValueAsString(response)
                )
            }

            FormStateRequestType.MOVE_NEW -> {
                formService.updateFormStatus(formId, FormStatus.NEW)

                return FormStateResponse(
                    responseType = FormStateResponseType.MOVE_NEW
                )
            }
            FormStateRequestType.MOVE_FILLED -> {
                formService.updateFormStatus(formId, FormStatus.FILLED)

                return FormStateResponse(
                    responseType = FormStateResponseType.MOVE_FILLED
                )
            }
            FormStateRequestType.MOVE_ACCEPTED -> {
                formService.updateFormStatus(formId, FormStatus.ACCEPTED)

                return FormStateResponse(
                    responseType = FormStateResponseType.MOVE_ACCEPTED
                )
            }
            FormStateRequestType.MOVE_SIGNED -> {
                formService.updateFormStatus(formId, FormStatus.SIGNED)

                return FormStateResponse(
                    responseType = FormStateResponseType.MOVE_SIGNED
                )
            }
            FormStateRequestType.MOVE_CLOSED -> {
                formService.updateFormStatus(formId, FormStatus.CLOSED)

                return FormStateResponse(
                    responseType = FormStateResponseType.MOVE_CLOSED
                )
            }
        }
    }

    private fun serializeState(state: Any): String =
        objectMapper.writeValueAsString(state)
}
