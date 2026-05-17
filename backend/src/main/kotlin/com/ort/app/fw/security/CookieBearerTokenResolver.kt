package com.ort.app.fw.security

import jakarta.servlet.http.HttpServletRequest
import org.springframework.security.oauth2.server.resource.web.BearerTokenResolver
import org.springframework.security.oauth2.server.resource.web.DefaultBearerTokenResolver

/**
 * Cookie `access_token` を優先して読み取り、無ければ Authorization ヘッダにフォールバックする。
 */
class CookieBearerTokenResolver(
    private val cookieName: String = "access_token",
) : BearerTokenResolver {
    private val fallback = DefaultBearerTokenResolver()

    override fun resolve(request: HttpServletRequest): String? {
        val cookieToken = request.cookies?.firstOrNull { it.name == cookieName }?.value
        if (!cookieToken.isNullOrBlank()) return cookieToken
        return fallback.resolve(request)
    }
}
