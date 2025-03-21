package com.ort.app.fw.security

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.AuthenticationSuccessHandler


class WolfMansionLoginSuccessHandler : AuthenticationSuccessHandler {

    private val logger = LoggerFactory.getLogger(this::class.java)

    override fun onAuthenticationSuccess(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        authentication: Authentication?
    ) {
        val requestUri = request!!.requestURI
        logger.info("login success: $requestUri")
        if (!requestUri.startsWith("/api/")) {
            // 通常のログイン: `/` にリダイレクト
            response!!.sendRedirect("/wolf-mansion")
        }
    }
}