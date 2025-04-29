package com.ort.app.fw.security

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler

class WolfMansionLogoutSuccessHandler : LogoutSuccessHandler {

    override fun onLogoutSuccess(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        authentication: Authentication?
    ) {
        val requestUri = request!!.requestURI
        if (requestUri.startsWith("/api/")) {
            // APIリクエストの場合はJSONレスポンス
            response!!.status = HttpServletResponse.SC_OK
            response.contentType = "application/json"
            response.characterEncoding = "UTF-8"
        } else {
            // 通常のログアウト時はリダイレクト
            response!!.sendRedirect("/wolf-mansion/")
        }
    }
}