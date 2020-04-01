package pl.edu.agh.ki.covid19tablet.stream

import org.springframework.messaging.handler.annotation.DestinationVariable
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.stereotype.Controller
import pl.edu.agh.ki.covid19tablet.form.FormId
import pl.edu.agh.ki.covid19tablet.stream.dto.FormStateRequest
import pl.edu.agh.ki.covid19tablet.stream.dto.FormStateResponse
import javax.transaction.Transactional

@Controller
class FormStreamController(
    private val formStreamService: FormStreamService
) {
    @MessageMapping("/requests/{formId}")
    @SendTo("/updates/{formId}")
    @Transactional
    fun handleRequest(@DestinationVariable formId: FormId, request: FormStateRequest): FormStateResponse =
        formStreamService.handleRequest(formId, request)
}
