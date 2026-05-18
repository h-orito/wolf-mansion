package com.ort.app.fw.config

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OpenApiConfig {

    @Bean
    fun openApi(): OpenAPI {
        val cookieAuth = SecurityScheme()
            .type(SecurityScheme.Type.APIKEY)
            .`in`(SecurityScheme.In.COOKIE)
            .name("access_token")
            .description("JWT access token (httpOnly Cookie)")
        return OpenAPI()
            .info(
                Info()
                    .title("wolf-mansion API")
                    .description("人狼ゲーム wolf-mansion の REST API")
                    .version("v1")
            )
            .components(Components().addSecuritySchemes("cookieAuth", cookieAuth))
            .addSecurityItem(SecurityRequirement().addList("cookieAuth"))
    }
}
