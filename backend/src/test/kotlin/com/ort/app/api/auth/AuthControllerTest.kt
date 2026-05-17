package com.ort.app.api.auth

import com.fasterxml.jackson.databind.ObjectMapper
import com.ort.app.application.service.PlayerService
import com.ort.app.domain.model.auth.RefreshToken
import com.ort.app.domain.model.auth.RefreshTokenRepository
import com.ort.app.domain.model.player.Authority
import com.ort.app.domain.model.player.Player
import com.ort.app.fw.security.JwtTokenService
import com.ort.app.fw.security.UserInfo
import com.ort.app.fw.security.UserInfoService
import com.ort.dbflute.allcommon.CDef
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.eq
import org.mockito.kotlin.never
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.cookie
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.header
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import java.time.LocalDateTime

@SpringBootTest
class AuthControllerTest {

    @Autowired
    private lateinit var context: WebApplicationContext

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Autowired
    private lateinit var jwtTokenService: JwtTokenService

    @MockitoBean
    private lateinit var userInfoService: UserInfoService

    @MockitoBean
    private lateinit var playerService: PlayerService

    @MockitoBean
    private lateinit var refreshTokenRepository: RefreshTokenRepository

    private lateinit var mockMvc: MockMvc
    private val encoder: PasswordEncoder = BCryptPasswordEncoder()
    private val rawPassword = "p@ssword-1!"
    private val hashedPassword = encoder.encode(rawPassword)

    @BeforeEach
    fun setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
            .apply<org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder>(springSecurity())
            .build()
    }

    private fun stubUserAndPlayer(
        userName: String = "alice",
        playerId: Int = 42,
        authority: CDef.Authority = CDef.Authority.プレイヤー,
        passwordHash: String = hashedPassword,
    ): Player {
        val ui = UserInfo().apply {
            setUsername(userName)
            setPassword(passwordHash)
            setAuthority(authority)
        }
        whenever(userInfoService.loadUserByUsername(userName)).thenReturn(ui)
        val player = Player(
            id = playerId,
            name = userName,
            twitterUserName = null,
            introduction = null,
            authority = Authority(authority),
            isRestrictedParticipation = false,
            shouldCheckAccessInfo = false,
        )
        whenever(playerService.findPlayer(userName)).thenReturn(player)
        whenever(playerService.findPlayer(playerId)).thenReturn(player)
        whenever(refreshTokenRepository.register(eq(playerId), any(), any())).thenAnswer {
            RefreshToken(
                id = 1,
                playerId = playerId,
                tokenHash = it.getArgument(1),
                expiresAt = it.getArgument(2),
                revoked = false,
            )
        }
        return player
    }

    @Test
    fun `me は未認証で 200 と user null を返す`() {
        mockMvc.perform(get("/api/v1/auth/me"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.user").doesNotExist())
    }

    @Test
    fun `login 成功で 200 と access_token refresh_token Cookie が発行される`() {
        stubUserAndPlayer()
        val body = mapOf("userId" to "alice", "password" to rawPassword)

        mockMvc.perform(
            post("/api/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(body))
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.user.userId").value("alice"))
            .andExpect(jsonPath("$.user.authority").value(CDef.Authority.プレイヤー.code()))
            .andExpect(header().exists("Set-Cookie"))
            .andExpect(cookie().exists("access_token"))
            .andExpect(cookie().httpOnly("access_token", true))
            .andExpect(cookie().exists("refresh_token"))
            .andExpect(cookie().httpOnly("refresh_token", true))

        // refresh token が DB に保存される
        verify(refreshTokenRepository).register(eq(42), any(), any())
    }

    @Test
    fun `login で password 不一致なら 401`() {
        stubUserAndPlayer()
        val body = mapOf("userId" to "alice", "password" to "wrong-password")

        mockMvc.perform(
            post("/api/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(body))
        )
            .andExpect(status().isUnauthorized)

        verify(refreshTokenRepository, never()).register(any(), any(), any())
    }

    @Test
    fun `login で存在しないユーザは 401`() {
        whenever(userInfoService.loadUserByUsername("ghost")).thenReturn(null)
        val body = mapOf("userId" to "ghost", "password" to "anything")

        mockMvc.perform(
            post("/api/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(body))
        )
            .andExpect(status().isUnauthorized)
    }

    @Test
    fun `login で userId が空なら 400 validation error`() {
        val body = mapOf("userId" to "", "password" to "p")

        mockMvc.perform(
            post("/api/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(body))
        )
            .andExpect(status().isBadRequest)
    }

    @Test
    fun `me は有効な access_token Cookie 付きで 200 と user 情報を返す`() {
        val token = jwtTokenService.issueAccessToken("bob", CDef.Authority.管理者)

        mockMvc.perform(
            get("/api/v1/auth/me")
                .cookie(jakarta.servlet.http.Cookie("access_token", token))
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.user.userId").value("bob"))
            .andExpect(jsonPath("$.user.authority").value(CDef.Authority.管理者.code()))
    }

    @Test
    fun `refresh は cookie 無しで 401`() {
        mockMvc.perform(post("/api/v1/auth/refresh"))
            .andExpect(status().isUnauthorized)
    }

    @Test
    fun `refresh は不明なトークンで 401`() {
        whenever(refreshTokenRepository.findByTokenHash(any())).thenReturn(null)

        mockMvc.perform(
            post("/api/v1/auth/refresh")
                .cookie(jakarta.servlet.http.Cookie("refresh_token", "unknown"))
        )
            .andExpect(status().isUnauthorized)
    }

    @Test
    fun `refresh は有効トークンで rotation し古いトークンを revoke して新トークンを発行`() {
        stubUserAndPlayer()
        val rawToken = "valid-refresh-token"
        val hash = jwtTokenService.hashRefreshToken(rawToken)
        val stored = RefreshToken(
            id = 99,
            playerId = 42,
            tokenHash = hash,
            expiresAt = LocalDateTime.now().plusDays(7),
            revoked = false,
        )
        whenever(refreshTokenRepository.findByTokenHash(hash)).thenReturn(stored)

        mockMvc.perform(
            post("/api/v1/auth/refresh")
                .cookie(jakarta.servlet.http.Cookie("refresh_token", rawToken))
        )
            .andExpect(status().isOk)
            .andExpect(cookie().exists("access_token"))
            .andExpect(cookie().exists("refresh_token"))

        verify(refreshTokenRepository).revoke(99)
        verify(refreshTokenRepository).register(eq(42), any(), any())
    }

    @Test
    fun `refresh は失効済みトークンで 401`() {
        val rawToken = "revoked"
        val hash = jwtTokenService.hashRefreshToken(rawToken)
        val stored = RefreshToken(
            id = 100,
            playerId = 42,
            tokenHash = hash,
            expiresAt = LocalDateTime.now().plusDays(7),
            revoked = true,
        )
        whenever(refreshTokenRepository.findByTokenHash(hash)).thenReturn(stored)

        mockMvc.perform(
            post("/api/v1/auth/refresh")
                .cookie(jakarta.servlet.http.Cookie("refresh_token", rawToken))
        )
            .andExpect(status().isUnauthorized)

        verify(refreshTokenRepository, never()).revoke(any())
    }

    @Test
    fun `logout は cookie をクリアし refresh を revoke する`() {
        val rawToken = "to-be-revoked"
        val hash = jwtTokenService.hashRefreshToken(rawToken)
        val stored = RefreshToken(
            id = 7,
            playerId = 42,
            tokenHash = hash,
            expiresAt = LocalDateTime.now().plusDays(7),
            revoked = false,
        )
        whenever(refreshTokenRepository.findByTokenHash(hash)).thenReturn(stored)

        val result = mockMvc.perform(
            post("/api/v1/auth/logout")
                .cookie(jakarta.servlet.http.Cookie("refresh_token", rawToken))
        )
            .andExpect(status().isNoContent)
            .andReturn()

        verify(refreshTokenRepository).revoke(7)
        // Set-Cookie で expire の cookie が返る
        val setCookies = result.response.getHeaders("Set-Cookie")
        assertThat(setCookies.any { it.contains("access_token=") && it.contains("Max-Age=0") }).isTrue
        assertThat(setCookies.any { it.contains("refresh_token=") && it.contains("Max-Age=0") }).isTrue
    }

    @Test
    fun `logout は cookie 無しでも 204 を返し副作用なし`() {
        mockMvc.perform(post("/api/v1/auth/logout"))
            .andExpect(status().isNoContent)
        verify(refreshTokenRepository, never()).revoke(any())
    }
}
