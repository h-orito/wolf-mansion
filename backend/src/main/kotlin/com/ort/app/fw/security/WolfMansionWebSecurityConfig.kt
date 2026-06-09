package com.ort.app.fw.security

import com.ort.app.fw.security.jwt.JwtAuthenticationEntryPoint
import com.ort.app.fw.security.jwt.JwtAuthenticationFilter
import com.ort.app.fw.security.jwt.JwtTokenProvider
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler

@Configuration
@EnableWebSecurity
class WolfMansionWebSecurityConfig {
    /**
     * REST API チェーン (`/api/v1` 配下)。JWT による stateless 認証。
     * 移行期間は本チェーンと既存 SSR チェーン ([webFilterChain]) が共存する。
     */
    @Bean
    @Order(1)
    fun apiFilterChain(
        http: HttpSecurity,
        jwtTokenProvider: JwtTokenProvider,
        jwtAuthenticationEntryPoint: JwtAuthenticationEntryPoint,
    ): SecurityFilterChain {
        http
            .securityMatcher("/api/v1/**")
            .csrf { it.disable() }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .authorizeHttpRequests { auth ->
                auth
                    .requestMatchers(
                        "/api/v1/auth/login",
                        "/api/v1/auth/signup",
                        "/api/v1/auth/refresh",
                        "/api/v1/auth/logout",
                    ).permitAll()
                    // 村一覧・キャラセット一覧・役職一覧・ルール情報は公開情報
                    .requestMatchers(
                        org.springframework.http.HttpMethod.GET,
                        "/api/v1/villages",
                        "/api/v1/charachips",
                        "/api/v1/charachips/{id}",
                        "/api/v1/rooms",
                        "/api/v1/skills",
                        "/api/v1/skills/search",
                        "/api/v1/rule/judges",
                    ).permitAll()
                    .anyRequest()
                    .authenticated()
            }.exceptionHandling { it.authenticationEntryPoint(jwtAuthenticationEntryPoint) }
            .addFilterBefore(
                JwtAuthenticationFilter(jwtTokenProvider),
                UsernamePasswordAuthenticationFilter::class.java,
            )
        return http.build()
    }

    /**
     * 既存 SSR チェーン (`/api/v1` 配下 以外すべて)。セッション + formLogin。
     * 旧 Thymeleaf 画面と公開 API (`/api/login` 等) は Step 10 までこの構成で動かす。
     */
    @Bean
    @Order(2)
    fun webFilterChain(
        http: HttpSecurity,
        userInfoService: UserInfoService,
    ): SecurityFilterChain {
        // Spring Security 5 互換: BREACH 対策の XOR を無効化 (multipart フォームとの互換性のため)
        val csrfTokenHandler = CsrfTokenRequestAttributeHandler()
        http
            .authorizeHttpRequests { auth ->
                auth
                    .requestMatchers("/admin/**")
                    .hasRole("ADMIN")
                    .requestMatchers("/change-password")
                    .fullyAuthenticated()
                    .anyRequest()
                    .permitAll()
            }.formLogin { form ->
                form
                    .loginProcessingUrl("/login")
                    .loginPage("/")
                    .defaultSuccessUrl("/")
                    .failureUrl("/login?error=true")
                    .usernameParameter("userId")
                    .passwordParameter("password")
                    .permitAll()
            }.logout { logout ->
                logout
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/")
                    .deleteCookies("JSESSIONID")
                    .invalidateHttpSession(true)
                    .permitAll()
            }.rememberMe { rm ->
                rm
                    .userDetailsService(userInfoService)
                    .key("X7kmptSvar")
            }.csrf { csrf ->
                csrf.csrfTokenRequestHandler(csrfTokenHandler)
                csrf.ignoringRequestMatchers(
                    "/village/*/confirm",
                    "/village/*/say",
                    "/api/login",
                    "/village/*/update",
                )
            }.authenticationProvider(authenticationProvider(userInfoService))
        return http.build()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()

    @Bean
    fun authenticationProvider(userInfoService: UserInfoService): DaoAuthenticationProvider {
        val provider = DaoAuthenticationProvider(userInfoService)
        provider.setPasswordEncoder(passwordEncoder())
        return provider
    }
}
