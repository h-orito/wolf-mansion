package com.ort.app.fw.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.oauth2.jwt.JwtDecoder

@Configuration
class JwtConfig {

    /**
     * `JwtDecoder` を Bean 化することで `SecurityFilterChain` から DI 経由で
     * 解決させ、複数 SecurityFilterChain 構成時の二重生成を防ぐ。
     */
    @Bean
    fun jwtDecoder(jwtTokenService: JwtTokenService): JwtDecoder = jwtTokenService.jwtDecoder()
}
