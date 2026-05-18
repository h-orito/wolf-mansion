package com.ort.app.fw.config

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext

@SpringBootTest
class OpenApiEndpointTest {

    @Autowired
    private lateinit var context: WebApplicationContext

    private lateinit var mockMvc: MockMvc

    @BeforeEach
    fun setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
            .apply<org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder>(springSecurity())
            .build()
    }

    @Test
    fun `v3 api-docs は認証不要で 200 と OpenAPI JSON を返す`() {
        val result = mockMvc.perform(get("/v3/api-docs"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.info.title").value("wolf-mansion API"))
            .andExpect(jsonPath("$.paths./api/v1/auth/login").exists())
            .andExpect(jsonPath("$.paths./api/v1/auth/me").exists())
            .andExpect(jsonPath("$.components.securitySchemes.cookieAuth.in").value("cookie"))
            .andReturn()

        assertThat(result.response.contentType).contains("application/json")
    }

    @Test
    fun `swagger-ui index は認証不要で取得可能`() {
        mockMvc.perform(get("/swagger-ui/index.html"))
            .andExpect(status().isOk)
    }
}
