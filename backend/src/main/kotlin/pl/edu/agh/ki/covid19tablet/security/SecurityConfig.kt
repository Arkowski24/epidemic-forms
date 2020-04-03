package pl.edu.agh.ki.covid19tablet.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.BeanIds
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import pl.edu.agh.ki.covid19tablet.security.employee.EmployeeDetailsService


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
    securedEnabled = true,
    jsr250Enabled = true,
    prePostEnabled = true
)
class SecurityConfig(
    private val employeeDetailsService: EmployeeDetailsService,
    private val tokenAuthenticationFilter: TokenAuthenticationFilter
) : WebSecurityConfigurerAdapter() {

    override fun configure(http: HttpSecurity) {
        // @formatter:off
        http
            .csrf()
                .disable()
            .cors()
                .disable()
            .sessionManagement()
                .disable()
            .authorizeRequests()
                .anyRequest()
                .permitAll()
            .and()
                .addFilterAt(tokenAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)
        // @formatter:on
    }

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(employeeDetailsService)
            .passwordEncoder(passwordEncoder())
    }

    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    override fun authenticationManagerBean(): AuthenticationManager {
        return super.authenticationManagerBean()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder =
        BCryptPasswordEncoder()
}
