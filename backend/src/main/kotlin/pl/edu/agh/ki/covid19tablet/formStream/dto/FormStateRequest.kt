package pl.edu.agh.ki.covid19tablet.formStream.dto

import com.fasterxml.jackson.annotation.JsonRawValue

enum class FormStateRequestType {
    GET_STATE,
    UPDATE_CHOICE,
    UPDATE_SIGN,
    UPDATE_SLIDER,
    UPDATE_TEXT,
}

data class FormStateRequest(
    val requestType: FormStateRequestType,
    @JsonRawValue
    val payload: String?
)
