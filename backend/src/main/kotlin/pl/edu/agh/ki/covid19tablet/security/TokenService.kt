package pl.edu.agh.ki.covid19tablet.security

import io.jsonwebtoken.Claims
import io.jsonwebtoken.JwtBuilder
import io.jsonwebtoken.JwtParser
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Component
import pl.edu.agh.ki.covid19tablet.AppConfig
import java.time.Instant
import java.util.Base64
import java.util.Date

@Component
class TokenService(
    config: AppConfig
) {
    private val key =
        config.security.tokenSecretKey
            .let(Base64.getDecoder()::decode)
            .let(Keys::hmacShaKeyFor)

    fun createToken(claims: JwtBuilder.() -> Unit): String =
        Jwts.builder()
            .signWith(key)
            .apply(claims)
            .compact()

    fun parseToken(token: String, requirements: JwtParser.() -> Unit = {}): Claims =
        Jwts.parser()
            .setSigningKey(key)
            .apply(requirements)
            .parseClaimsJws(token)
            .body
}

fun JwtBuilder.setExpiration(exp: Instant): JwtBuilder =
    this.setExpiration(Date.from(exp))
