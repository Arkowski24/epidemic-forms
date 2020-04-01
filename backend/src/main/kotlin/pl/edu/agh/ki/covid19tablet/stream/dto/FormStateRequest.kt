package pl.edu.agh.ki.covid19tablet.stream.dto

import com.fasterxml.jackson.annotation.JsonRawValue

enum class FormStateRequestType {
    GET_STATE,
    UPDATE_CHOICE,
    UPDATE_SLIDER,
    UPDATE_TEXT,
}

data class FormStateRequest(
    val requestType: FormStateRequestType,
    @JsonRawValue
    val payload: String?
)
