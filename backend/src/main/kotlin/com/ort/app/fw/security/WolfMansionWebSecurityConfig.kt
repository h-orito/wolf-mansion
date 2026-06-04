package com.ort.app.fw.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler

@Configuration
@EnableWebSecurity
class WolfMansionWebSecurityConfig {

    @Bean
    fun filterChain(http: HttpSecurity, userInfoService: UserInfoService): SecurityFilterChain {
        // Spring Security 5 互換: BREACH 対策の XOR を無効化 (multipart フォームとの互換性のため)
        val csrfTokenHandler = CsrfTokenRequestAttributeHandler()
        http
            .authorizeHttpRequests { auth ->
                auth
                    .requestMatchers("/admin/**").hasRole("ADMIN")
                    .requestMatchers("/change-password").fullyAuthenticated()
                    .anyRequest().permitAll()
            }
            .formLogin { form ->
                form
                    .loginProcessingUrl("/login")
                    .loginPage("/")
                    .defaultSuccessUrl("/")
                    .failureUrl("/login?error=true")
                    .usernameParameter("userId")
                    .passwordParameter("password")
                    .permitAll()
            }
            .logout { logout ->
                logout
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/")
                    .deleteCookies("JSESSIONID")
                    .invalidateHttpSession(true)
                    .permitAll()
            }
            .rememberMe { rm ->
                rm
                    .userDetailsService(userInfoService)
                    .key("X7kmptSvar")
            }
            .csrf { csrf ->
                csrf.csrfTokenRequestHandler(csrfTokenHandler)
                csrf.ignoringRequestMatchers(
                    "/village/*/confirm",
                    "/village/*/say",
                    "/api/login",
                    "/village/*/update"
                )
            }
            .authenticationProvider(authenticationProvider(userInfoService))
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
