package pl.edu.agh.ki.covid19tablet.security

import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.messaging.Message
import org.springframework.messaging.MessageChannel
import org.springframework.messaging.simp.config.ChannelRegistration
import org.springframework.messaging.simp.stomp.StompCommand
import org.springframework.messaging.simp.stomp.StompHeaderAccessor
import org.springframework.messaging.support.ChannelInterceptor
import org.springframework.messaging.support.ErrorMessage
import org.springframework.messaging.support.MessageHeaderAccessor
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer
import pl.edu.agh.ki.covid19tablet.PatientUnauthorizedException
import pl.edu.agh.ki.covid19tablet.security.employee.EmployeeDetailsService
import pl.edu.agh.ki.covid19tablet.security.employee.EmployeeTokenProvider
import pl.edu.agh.ki.covid19tablet.security.patient.PatientDetails
import pl.edu.agh.ki.covid19tablet.security.patient.PatientDetailsService
import pl.edu.agh.ki.covid19tablet.security.patient.PatientTokenProvider


@Configuration
@EnableWebSocketMessageBroker
@Order(Ordered.HIGHEST_PRECEDENCE + 99)
@ComponentScan
class WebSocketAuthenticationConfig(
    private val employeeTokenProvider: EmployeeTokenProvider,
    private val employeeDetailsService: EmployeeDetailsService,
    private val patientTokenProvider: PatientTokenProvider,
    private val patientDetailsService: PatientDetailsService
) : WebSocketMessageBrokerConfigurer {

    override fun configureClientInboundChannel(registration: ChannelRegistration) {
        registration.interceptors(object : ChannelInterceptor {
            override fun preSend(message: Message<*>, channel: MessageChannel): Message<*>? {
                val accessor: StompHeaderAccessor =
                    MessageHeaderAccessor
                        .getAccessor(message, StompHeaderAccessor::class.java)!!

                try {
                    if (StompCommand.CONNECT == accessor.command) {
                        accessor.user = resolveAuthentication(accessor)
                            ?: throw PatientUnauthorizedException()
                    } else if (StompCommand.SUBSCRIBE == accessor.command) {
                        val user = accessor.user as UsernamePasswordAuthenticationToken?
                            ?: throw PatientUnauthorizedException()

                        if (user.principal is PatientDetails) {
                            val patient = (user.principal as PatientDetails).patient
                            if (message.headers["simpDestination"] != "/updates/${patient.form?.id!!}")
                                throw PatientUnauthorizedException()
                        }
                    }
                } catch (ex: PatientUnauthorizedException) {
                    return ErrorMessage(ex)
                }

                return message
            }
        })
    }

    private fun resolveAuthentication(accessor: StompHeaderAccessor): Authentication? =
        getTokenFromAccessor(accessor)?.let { mapTokenToAuthentication(token = it) }

    private fun mapTokenToAuthentication(token: String): Authentication? =
        if (employeeTokenProvider.isEmployee(token))
            employeeTokenProvider
                .parseToken(token)
                ?.let { employeeId -> employeeDetailsService.loadUserById(employeeId) }
                ?.let { employeeDetails ->
                    UsernamePasswordAuthenticationToken(employeeDetails, null, employeeDetails.authorities)
                }
        else
            patientTokenProvider
                .parseToken(token)
                ?.let { patientId -> patientDetailsService.loadPatientById(patientId) }
                ?.let { patientDetails ->
                    UsernamePasswordAuthenticationToken(patientDetails, null, patientDetails.authorities)
                }

    private fun getTokenFromAccessor(accessor: StompHeaderAccessor): String? =
        accessor
            .getFirstNativeHeader("Authorization")
            ?.removePrefix("Bearer ")
}
