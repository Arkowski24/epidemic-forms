package pl.edu.agh.ki.covid19tablet.formStream

import org.springframework.messaging.handler.annotation.DestinationVariable
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.stereotype.Controller
import pl.edu.agh.ki.covid19tablet.form.FormId
import pl.edu.agh.ki.covid19tablet.formStream.dto.FormStateRequest
import pl.edu.agh.ki.covid19tablet.formStream.dto.FormStateResponse

@Controller
class FormStreamController(
    private val formStreamService: FormStreamService
) {
    @MessageMapping("/requests/{formId}")
    @SendTo("/topic/updates/{formId}")
    fun handleRequest(@DestinationVariable formId: FormId, request: FormStateRequest): FormStateResponse =
        formStreamService.handleRequest(formId, request)
}
