package com.ort.app.fw.security

import com.fasterxml.jackson.databind.ObjectMapper
import com.ort.app.api.view.player.PlayerView
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.AuthenticationFailureHandler


class WolfMansionLoginFailureHandler : AuthenticationFailureHandler {

    override fun onAuthenticationFailure(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        exception: AuthenticationException?
    ) {
        val requestUri = request!!.requestURI

        if (requestUri.startsWith("/api/")) {
            response!!.status = HttpServletResponse.SC_UNAUTHORIZED
            response.contentType = "application/json"
            response.characterEncoding = "UTF-8"
            response.writer.write(ObjectMapper().writeValueAsString(LoginFailureResponse(null)))
        } else {
            response!!.sendRedirect("/wolf-mansion/login?error=true")
        }
    }

    data class LoginFailureResponse(
        val player: PlayerView?
    )
}