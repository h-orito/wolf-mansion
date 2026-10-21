package com.ort.app.infrastructure.datasource

import com.ort.app.domain.model.auth.LoginAttemptRepository
import com.ort.dbflute.exbhv.LoginFailureBhv
import org.springframework.stereotype.Repository
import java.time.LocalDateTime
import com.ort.dbflute.exentity.LoginFailure as DbLoginFailure

@Repository
class LoginAttemptDataSource(
    private val loginFailureBhv: LoginFailureBhv,
) : LoginAttemptRepository {
    override fun countByLoginNameAndIpAddress(
        loginName: String,
        ipAddress: String,
        since: LocalDateTime,
    ): Int =
        loginFailureBhv.selectCount {
            it.query().setLoginName_Equal(loginName)
            it.query().setIpAddress_Equal(ipAddress)
            it.query().setAttemptDatetime_GreaterEqual(since)
        }

    override fun countByIpAddress(
        ipAddress: String,
        since: LocalDateTime,
    ): Int =
        loginFailureBhv.selectCount {
            it.query().setIpAddress_Equal(ipAddress)
            it.query().setAttemptDatetime_GreaterEqual(since)
        }

    override fun recordAttempt(
        loginName: String,
        ipAddress: String,
        attemptAt: LocalDateTime,
    ) {
        val entity = DbLoginFailure()
        entity.loginName = loginName
        entity.ipAddress = ipAddress
        entity.attemptDatetime = attemptAt
        loginFailureBhv.insert(entity)
    }

    override fun deleteByLoginNameAndIpAddress(
        loginName: String,
        ipAddress: String,
    ) {
        loginFailureBhv.queryDelete {
            it.query().setLoginName_Equal(loginName)
            it.query().setIpAddress_Equal(ipAddress)
        }
    }

    override fun deleteOlderThan(threshold: LocalDateTime) {
        loginFailureBhv.queryDelete {
            it.query().setAttemptDatetime_LessThan(threshold)
        }
    }
}
