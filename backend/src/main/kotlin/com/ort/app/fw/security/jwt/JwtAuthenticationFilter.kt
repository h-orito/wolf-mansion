package com.ort.app.fw.security.jwt

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.web.filter.OncePerRequestFilter

/**
 * access token Cookie を検証して SecurityContext に認証情報を詰める。
 * `/api/v1` 配下チェーン専用 (SecurityConfig で手動構築・登録するため `@Component` にはしない)。
 * トークンが無い/不正でもここでは弾かず後段の認可判定に委ねる (公開エンドポイントを通すため)。
 */
class JwtAuthenticationFilter(
    private val jwtTokenProvider: JwtTokenProvider,
) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain,
    ) {
        val token = resolveAccessToken(request)
        if (token != null && SecurityContextHolder.getContext().authentication == null) {
            val principal = jwtTokenProvider.parseAccessToken(token)
            if (principal != null) {
                val authorities = principal.authorities.map { SimpleGrantedAuthority(it) }
                val authentication = UsernamePasswordAuthenticationToken(principal, null, authorities)
                authentication.details = WebAuthenticationDetailsSource().buildDetails(request)
                SecurityContextHolder.getContext().authentication = authentication
            }
        }
        filterChain.doFilter(request, response)
    }

    private fun resolveAccessToken(request: HttpServletRequest): String? =
        request.cookies
            ?.firstOrNull { it.name == AuthCookieFactory.ACCESS_TOKEN }
            ?.value
            ?.ifBlank { null }
}
