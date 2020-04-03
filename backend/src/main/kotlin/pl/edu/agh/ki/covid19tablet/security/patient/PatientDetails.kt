package pl.edu.agh.ki.covid19tablet.security.patient

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import pl.edu.agh.ki.covid19tablet.user.patient.Patient

class PatientDetails(
    val patient: Patient
) : UserDetails {
    override fun getUsername(): String = patient.id!!.toString()

    override fun getPassword(): String? = null

    override fun getAuthorities(): List<GrantedAuthority> = listOf(SimpleGrantedAuthority("ROLE_PATIENT"))

    override fun isEnabled() = true

    override fun isCredentialsNonExpired() = false

    override fun isAccountNonExpired() = !patient.loggedIn

    override fun isAccountNonLocked() = true

    override fun toString(): String = "PatientDetails(patientId=${patient.id})"
}
