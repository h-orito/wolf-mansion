package com.ort.app.application.coordinator

import com.ort.app.domain.model.auth.LoginAttemptRepository
import com.ort.app.fw.exception.WolfMansionAuthException
import com.ort.app.fw.exception.WolfMansionTooManyRequestsException
import org.dbflute.hook.AccessContext
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDateTime
import java.util.UUID
import java.util.concurrent.Callable
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

/**
 * 実 DB と Spring のトランザクション管理で、ログインの同時送信を確認する (DB が必要: docker-compose の mysql)。
 * プールを小さくし、1 リクエストが接続を 2 本同時に使う作りだと枯渇して取得待ちのタイムアウトになることを検出する。
 */
@SpringBootTest(
    properties = [
        "spring.datasource.hikari.maximum-pool-size=4",
        "spring.datasource.hikari.connection-timeout=3000",
    ],
)
class AuthCoordinatorConcurrencyDbTest {
    @Autowired
    private lateinit var authCoordinator: AuthCoordinator

    @Autowired
    private lateinit var loginAttemptRepository: LoginAttemptRepository

    // 存在しないアカウント名にし、実在のプレイヤーや他のテストの記録と混ざらないようにする
    private val loginName = "test-${UUID.randomUUID().toString().take(8)}"
    private val clientIp = "192.0.2.10"

    @AfterEach
    fun cleanUp() {
        loginAttemptRepository.deleteByLoginNameAndIpAddress(loginName, clientIp)
    }

    @Test
    fun concurrent_logins_neither_exhaust_pool_nor_exceed_limit() {
        val threads = 16
        val start = CountDownLatch(1)
        val executor = Executors.newFixedThreadPool(threads)
        val results =
            try {
                val futures =
                    (1..threads).map {
                        executor.submit(
                            Callable {
                                // 本番ではリクエストごとに AccessContextInterceptor が設定する (DBFlute の共通カラム用)
                                AccessContext.setAccessContextOnThread(
                                    AccessContext().apply {
                                        accessLocalDateTime = LocalDateTime.now()
                                        accessUser = "test"
                                    },
                                )
                                start.await()
                                try {
                                    authCoordinator.login(loginName, "wrong-password", clientIp)
                                    "ok"
                                } catch (_: WolfMansionAuthException) {
                                    "401"
                                } catch (_: WolfMansionTooManyRequestsException) {
                                    "429"
                                }
                            },
                        )
                    }
                start.countDown()
                // 接続の取り合いで止まれば取得待ちのタイムアウト (3 秒) で例外になり、ここで失敗する
                futures.map { it.get(30, TimeUnit.SECONDS) }
            } finally {
                executor.shutdownNow()
            }
        val counts = results.groupingBy { it }.eachCount()
        assertEquals(threads, (counts["401"] ?: 0) + (counts["429"] ?: 0), "counts=$counts")
        // パスワードを試せるのは上限 (5 回) まで
        assertTrue((counts["401"] ?: 0) <= 5, "counts=$counts")
    }
}
