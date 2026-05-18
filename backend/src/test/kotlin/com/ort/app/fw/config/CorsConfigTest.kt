package com.ort.app.fw.config

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

class CorsConfigTest {

    @Test
    fun `allowedOrigins はカンマ区切り文字列を分割しトリムする`() {
        val config = CorsConfig(" http://localhost:5173 , https://wolfort.dev ").corsConfigurationSource()
            as UrlBasedCorsConfigurationSource
        val matched: CorsConfiguration = config.getCorsConfiguration(stubRequest()) ?: error("missing cors config")

        assertThat(matched.allowedOrigins).containsExactly("http://localhost:5173", "https://wolfort.dev")
        assertThat(matched.allowedMethods).contains("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
        assertThat(matched.allowedHeaders).contains("Content-Type", "Authorization", "X-XSRF-TOKEN")
        assertThat(matched.allowCredentials).isTrue
    }

    @Test
    fun `空文字列なら allowedOrigins は空リスト`() {
        val config = CorsConfig("").corsConfigurationSource() as UrlBasedCorsConfigurationSource
        val matched = config.getCorsConfiguration(stubRequest()) ?: error("missing cors config")
        assertThat(matched.allowedOrigins).isEmpty()
    }

    private fun stubRequest(): jakarta.servlet.http.HttpServletRequest =
        org.springframework.mock.web.MockHttpServletRequest("GET", "/api/v1/auth/me")
}
