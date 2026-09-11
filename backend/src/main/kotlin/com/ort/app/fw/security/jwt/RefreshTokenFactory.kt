package com.ort.app.fw.security.jwt

import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets
import java.security.MessageDigest
import java.security.SecureRandom
import java.util.Base64

/**
 * refresh token の生成とハッシュ化。
 * トークンそのものは Cookie でクライアントに渡し、DB には SHA-256 ハッシュ (hex 64 文字) のみ保存する。
 */
@Component
class RefreshTokenFactory {
    private val secureRandom = SecureRandom()

    /** 256bit の乱数を URL-safe Base64 (パディング無し) で返す。 */
    fun generate(): String {
        val bytes = ByteArray(32)
        secureRandom.nextBytes(bytes)
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes)
    }

    /** SHA-256 hex (64 文字)。`REFRESH_TOKEN.TOKEN_HASH` (CHAR(64)) に対応。 */
    fun hash(value: String): String {
        val digest = MessageDigest.getInstance("SHA-256").digest(value.toByteArray(StandardCharsets.UTF_8))
        return digest.joinToString("") { "%02x".format(it) }
    }
}
