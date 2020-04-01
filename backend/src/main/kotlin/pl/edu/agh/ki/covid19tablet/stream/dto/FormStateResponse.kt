package pl.edu.agh.ki.covid19tablet.stream.dto

import com.fasterxml.jackson.annotation.JsonRawValue

enum class FormStateResponseType {
    STATE,
    UPDATE_CHOICE,
    UPDATE_SIGN,
    UPDATE_SLIDER,
    UPDATE_TEXT,
}

data class FormStateResponse(
    val responseType: FormStateResponseType,
    @JsonRawValue
    val payload: String
)
