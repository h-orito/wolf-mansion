package com.ort.app.domain.service.auth

import com.ort.app.domain.model.auth.LoginAttemptRepository
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.TransactionDefinition
import org.springframework.transaction.TransactionStatus
import org.springframework.transaction.support.SimpleTransactionStatus
import java.time.LocalDateTime

/** テスト用のインメモリ実装。並行テストから呼ばれるので各操作を同期する。 */
internal class FakeLoginAttemptRepository : LoginAttemptRepository {
    data class Row(
        val loginName: String,
        val ipAddress: String,
        val attemptAt: LocalDateTime,
    )

    val rows = mutableListOf<Row>()

    override fun countByLoginNameAndIpAddress(
        loginName: String,
        ipAddress: String,
        since: LocalDateTime,
    ): Int =
        synchronized(rows) {
            rows.count { it.loginName == loginName && it.ipAddress == ipAddress && !it.attemptAt.isBefore(since) }
        }

    override fun countByIpAddress(
        ipAddress: String,
        since: LocalDateTime,
    ): Int = synchronized(rows) { rows.count { it.ipAddress == ipAddress && !it.attemptAt.isBefore(since) } }

    override fun recordAttempt(
        loginName: String,
        ipAddress: String,
        attemptAt: LocalDateTime,
    ) {
        synchronized(rows) { rows.add(Row(loginName, ipAddress, attemptAt)) }
    }

    override fun deleteByLoginNameAndIpAddress(
        loginName: String,
        ipAddress: String,
    ) {
        synchronized(rows) { rows.removeAll { it.loginName == loginName && it.ipAddress == ipAddress } }
    }

    override fun deleteOlderThan(threshold: LocalDateTime) {
        synchronized(rows) { rows.removeAll { it.attemptAt.isBefore(threshold) } }
    }
}

/** トランザクションを張らずにそのまま実行するだけのトランザクションマネージャ。 */
internal class NoOpTransactionManager : PlatformTransactionManager {
    override fun getTransaction(definition: TransactionDefinition?): TransactionStatus = SimpleTransactionStatus()

    override fun commit(status: TransactionStatus) {}

    override fun rollback(status: TransactionStatus) {}
}
