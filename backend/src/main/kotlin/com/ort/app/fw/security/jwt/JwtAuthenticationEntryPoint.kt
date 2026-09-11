package com.ort.app.fw.security.jwt

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ProblemDetail
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets

/**
 * 認証必須エンドポイントに未認証アクセスした場合に 401 を ProblemDetail (RFC 7807) で返す。
 */
@Component
class JwtAuthenticationEntryPoint(
    private val objectMapper: ObjectMapper,
) : AuthenticationEntryPoint {
    override fun commence(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authException: AuthenticationException,
    ) {
        val problem = ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, "認証が必要です")
        problem.setProperty("error", "unauthorized")
        response.status = HttpStatus.UNAUTHORIZED.value()
        response.contentType = MediaType.APPLICATION_PROBLEM_JSON_VALUE
        response.characterEncoding = StandardCharsets.UTF_8.name()
        objectMapper.writeValue(response.outputStream, problem)
    }
}
