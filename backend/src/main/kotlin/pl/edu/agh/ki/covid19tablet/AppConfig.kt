package pl.edu.agh.ki.covid19tablet

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "app")
@Suppress("MagicNumber")
class AppConfig {
    val security = Security()

    class Security {
        lateinit var tokenSecretKey: String
    }
}
