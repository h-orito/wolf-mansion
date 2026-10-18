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

@Configuration
@EnableWebSecurity
class WolfMansionWebSecurityConfig {
    /**
     * REST API チェーン (`/api/v1` 配下)。JWT による stateless 認証。それ以外は [webFilterChain]。
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
                    // 村一覧・村詳細・村状況・キャラセット一覧・役職一覧・ルール情報・ランダムキーワード・
                    // プレイヤープロフィールの閲覧は公開情報 (ランダムキーワードの書き込み系はログイン必須のため GET のみ)。
                    // 村状況はログイン時のみ視点をマスクに反映する (JWT filter が principal を積む)
                    .requestMatchers(
                        org.springframework.http.HttpMethod.GET,
                        "/api/v1/villages",
                        "/api/v1/villages/{id}",
                        "/api/v1/villages/{id}/setting",
                        "/api/v1/villages/{id}/info",
                        "/api/v1/villages/{id}/situation",
                        "/api/v1/villages/{id}/messages",
                        "/api/v1/villages/{id}/messages/*",
                        "/api/v1/villages/{id}/participants",
                        "/api/v1/villages/{id}/debug",
                        "/api/v1/charachips",
                        "/api/v1/charachips/{id}",
                        "/api/v1/rooms",
                        "/api/v1/skills",
                        "/api/v1/skills/search",
                        "/api/v1/rule/judges",
                        "/api/v1/random-keywords",
                        "/api/v1/random-keywords/{id}",
                        "/api/v1/players/{name}",
                    ).permitAll()
                    // 村ポーリングは匿名の閲覧者も日付更新を駆動するため公開
                    .requestMatchers(
                        org.springframework.http.HttpMethod.POST,
                        "/api/v1/villages/{id}/update",
                    ).permitAll()
                    // デバッグ操作はローカル開発専用 (app.debug 無効時は 404) のため匿名でも実行可
                    .requestMatchers(
                        org.springframework.http.HttpMethod.POST,
                        "/api/v1/villages/{id}/debug/*",
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
     * `/api/v1` 以外のチェーン。外部ツール向けの公開 API (`/recruiting`, `/api/login` 等) だけが残っており、
     * 認証を要する画面は無い。フォームログインや remember-me は資格情報の総当たりの抜け道になるため持たない。
     */
    @Bean
    @Order(2)
    fun webFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf { it.disable() }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .authorizeHttpRequests { it.anyRequest().permitAll() }
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
