package com.ort.app.fw.security

import com.ort.dbflute.allcommon.CDef
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.ProviderManager
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter
import org.springframework.security.web.SecurityFilterChain
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
@EnableWebSecurity
class WolfMansionWebSecurityConfig {

    @Value("\${app.cors.allowed-origins:}")
    private lateinit var allowedOrigins: String

    @Bean
    fun filterChain(
        http: HttpSecurity,
        jwtTokenService: JwtTokenService,
        corsConfigurationSource: CorsConfigurationSource,
    ): SecurityFilterChain {
        http
            .cors { it.configurationSource(corsConfigurationSource) }
            .csrf { it.disable() }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .authorizeHttpRequests { auth ->
                auth
                    .requestMatchers(HttpMethod.POST, "/api/v1/auth/login", "/api/v1/auth/refresh", "/api/v1/auth/logout").permitAll()
                    .requestMatchers(HttpMethod.GET, "/api/v1/auth/me").permitAll()
                    .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
                    .requestMatchers("/api/v1/admin/**").hasRole("ADMIN")
                    .anyRequest().permitAll()
            }
            .oauth2ResourceServer { oauth ->
                oauth.bearerTokenResolver(CookieBearerTokenResolver())
                oauth.jwt { jwt ->
                    jwt.decoder(jwtTokenService.jwtDecoder())
                    jwt.jwtAuthenticationConverter(jwtAuthenticationConverter())
                }
            }
        return http.build()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()

    @Bean
    fun authenticationManager(userInfoService: UserInfoService): AuthenticationManager {
        val provider = DaoAuthenticationProvider(userInfoService)
        provider.setPasswordEncoder(passwordEncoder())
        return ProviderManager(provider)
    }

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val config = CorsConfiguration()
        config.allowedOrigins = allowedOrigins.split(",").map { it.trim() }.filter { it.isNotEmpty() }
        config.allowedMethods = listOf("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
        config.allowedHeaders = listOf("Content-Type", "Authorization", "X-XSRF-TOKEN")
        config.allowCredentials = true
        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", config)
        return source
    }

    private fun jwtAuthenticationConverter(): JwtAuthenticationConverter {
        val converter = JwtAuthenticationConverter()
        converter.setJwtGrantedAuthoritiesConverter { jwt ->
            val authorityCode = jwt.getClaimAsString(JwtTokenService.CLAIM_AUTHORITY)
            val cdef = authorityCode?.let { CDef.Authority.codeOf(it) } ?: CDef.Authority.プレイヤー
            // map "管理者" → ROLE_ADMIN, "プレイヤー" → ROLE_USER for hasRole() compatibility
            val role = when (cdef) {
                CDef.Authority.管理者 -> "ROLE_ADMIN"
                else -> "ROLE_USER"
            }
            listOf(SimpleGrantedAuthority(role))
        }
        converter.setPrincipalClaimName("sub")
        // suppress default scope authorities
        val empty = JwtGrantedAuthoritiesConverter()
        empty.setAuthoritiesClaimName("__none__")
        return converter
    }
}
