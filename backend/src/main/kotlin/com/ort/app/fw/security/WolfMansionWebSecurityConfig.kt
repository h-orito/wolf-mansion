package com.ort.app.fw.security

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@EnableWebSecurity
@ConfigurationProperties(prefix = "security")
class WolfMansionWebSecurityConfig(
    private val userInfoService: UserInfoService
) {

    // CORSを許可するドメイン
    lateinit var corsClientUrls: List<String>

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        return http.authorizeHttpRequests { authz ->
            authz
                .requestMatchers("/admin/**", "/api/admin/**")
                .hasRole("ADMIN")
                .requestMatchers("/app/**", "/lib/**", "/**")
                .permitAll()
                .requestMatchers("/change-password")
                .fullyAuthenticated()
                .anyRequest()
                .authenticated()
        }.formLogin {
            it.loginProcessingUrl("/login")
                .loginPage("/") // ログイン成功時の遷移先URL
                .defaultSuccessUrl("/") // ログイン失敗時の遷移先URL
                .failureUrl("/login?error=true") // usernameのパラメタ名
                .usernameParameter("userId") // passwordのパラメタ名
                .passwordParameter("password")
                .permitAll()
        }.logout {
            it.logoutRequestMatcher(AntPathRequestMatcher("/logout")) // ログアウト成功時の遷移先URL
                .logoutSuccessUrl("/") // ログアウト時に削除するクッキー名
                .deleteCookies("JSESSIONID") // ログアウト時のセッション破棄を有効化
                .invalidateHttpSession(true)
        }.rememberMe {
            it.userDetailsService(userInfoService)
                .key("X7kmptSvar")
        }.csrf {
            it.ignoringRequestMatchers(
                "/village/*/confirm",
                "/village/*/say",
                "/api/*",
                "/village/*/update",
            )
        }.cors {
            it.configurationSource(corsConfigurationSource())
        }.build()
    }

    @Bean
    fun authenticationProvider(): AuthenticationProvider {
        val authProvider = DaoAuthenticationProvider()
        authProvider.setUserDetailsService(userInfoService)
        authProvider.setPasswordEncoder(passwordEncoder())
        return authProvider
    }

    @Bean
    private fun passwordEncoder(): PasswordEncoder? {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val config = CorsConfiguration()
        config.allowedMethods = listOf("OPTIONS", "GET", "POST", "PUT", "DELETE")
        config.allowedHeaders = listOf("*")
        config.allowCredentials = true
        config.allowedOrigins = this.corsClientUrls
        val corsSource = UrlBasedCorsConfigurationSource()
        corsSource.registerCorsConfiguration("/**", config)
        return corsSource
    }
}