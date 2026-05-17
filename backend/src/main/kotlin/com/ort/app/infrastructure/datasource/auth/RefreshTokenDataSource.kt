package com.ort.app.infrastructure.datasource.auth

import com.ort.app.domain.model.auth.RefreshToken
import com.ort.app.domain.model.auth.RefreshTokenRepository
import com.ort.dbflute.exbhv.RefreshTokenBhv
import com.ort.dbflute.exentity.RefreshToken as DbRefreshToken
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
class RefreshTokenDataSource(
    private val refreshTokenBhv: RefreshTokenBhv,
) : RefreshTokenRepository {

    override fun findByTokenHash(tokenHash: String): RefreshToken? {
        val opt = refreshTokenBhv.selectEntity {
            it.query().setTokenHash_Equal(tokenHash)
        }
        return if (opt.isPresent) mapToken(opt.get()) else null
    }

    override fun register(playerId: Int, tokenHash: String, expiresAt: LocalDateTime): RefreshToken {
        val entity = DbRefreshToken()
        entity.playerId = playerId
        entity.tokenHash = tokenHash
        entity.expiresAt = expiresAt
        entity.revoked = false
        refreshTokenBhv.insert(entity)
        return mapToken(entity)
    }

    override fun revoke(id: Int) {
        refreshTokenBhv.queryUpdate(DbRefreshToken().also { it.revoked = true }) {
            it.query().setRefreshTokenId_Equal(id)
        }
    }

    override fun revokeAllByPlayerId(playerId: Int) {
        refreshTokenBhv.queryUpdate(DbRefreshToken().also { it.revoked = true }) {
            it.query().setPlayerId_Equal(playerId)
            it.query().setRevoked_Equal(false)
        }
    }

    override fun deleteExpired(now: LocalDateTime) {
        refreshTokenBhv.queryDelete {
            it.query().setExpiresAt_LessThan(now)
        }
    }

    private fun mapToken(entity: DbRefreshToken): RefreshToken = RefreshToken(
        id = entity.refreshTokenId,
        playerId = entity.playerId,
        tokenHash = entity.tokenHash,
        expiresAt = entity.expiresAt,
        revoked = entity.revoked,
    )
}
