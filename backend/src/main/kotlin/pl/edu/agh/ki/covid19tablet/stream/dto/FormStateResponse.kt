package pl.edu.agh.ki.covid19tablet.stream.dto

import com.fasterxml.jackson.annotation.JsonRawValue

enum class FormStateResponseType {
    STATE,

    UPDATE_CHOICE,
    UPDATE_DERIVED,
    UPDATE_SLIDER,
    UPDATE_TEXT,

    MOVE_NEW,
    MOVE_FILLED,
    MOVE_ACCEPTED,
    MOVE_SIGNED,
    MOVE_CLOSED
}

data class FormStateResponse(
    val responseType: FormStateResponseType,
    @JsonRawValue
    val payload: String? = null
)
