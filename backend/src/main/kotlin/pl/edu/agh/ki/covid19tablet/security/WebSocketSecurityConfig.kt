package pl.edu.agh.ki.covid19tablet.security

import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.messaging.MessageSecurityMetadataSourceRegistry

import org.springframework.security.config.annotation.web.socket.AbstractSecurityWebSocketMessageBrokerConfigurer


@Configuration
class WebSocketSecurityConfig
    : AbstractSecurityWebSocketMessageBrokerConfigurer() {
    override fun configureInbound(messages: MessageSecurityMetadataSourceRegistry) {
        messages
            .anyMessage()
            .permitAll()
    }

    override fun sameOriginDisabled(): Boolean = true
}
