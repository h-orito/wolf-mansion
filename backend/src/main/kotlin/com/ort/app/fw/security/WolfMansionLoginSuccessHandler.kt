package com.ort.app.fw.security

import com.ort.app.api.view.player.PlayerView
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.AuthenticationSuccessHandler


class WolfMansionLoginSuccessHandler(
//    private val playerService: PlayerService
) : AuthenticationSuccessHandler {

    override fun onAuthenticationSuccess(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        authentication: Authentication?
    ) {
        val requestUri = request!!.requestURI

        if (requestUri.startsWith("/api/")) {
//            // APIログイン: ユーザー情報をJSONで返す
//            response!!.status = HttpServletResponse.SC_OK
//            response.contentType = "application/json"
//            response.characterEncoding = "UTF-8"
//
//            val responseBody = if (authentication!!.principal is UserInfo) {
//                playerService.findPlayer((authentication.principal as UserInfo).username)?.let {
//                    LoginResponse(PlayerView(it))
//                } ?: LoginResponse(null)
//            } else {
//                LoginResponse(null)
//            }
//
//            response.writer.write(ObjectMapper().writeValueAsString(responseBody))
        } else {
            // 通常のログイン: `/` にリダイレクト
            response!!.sendRedirect("/wolf-mansion")
        }
    }

    data class LoginResponse(
        val player: PlayerView?
    )
}