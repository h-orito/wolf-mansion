package com.ort.app.infrastructure.datasource

import com.ort.app.domain.model.auth.RefreshToken
import com.ort.app.domain.model.auth.RefreshTokenRepository
import com.ort.dbflute.exbhv.RefreshTokenBhv
import org.springframework.stereotype.Repository
import java.time.LocalDateTime
import com.ort.dbflute.exentity.RefreshToken as DbRefreshToken

@Repository
class RefreshTokenDataSource(
    private val refreshTokenBhv: RefreshTokenBhv,
) : RefreshTokenRepository {
    override fun insert(
        playerId: Int,
        tokenHash: String,
        issuedDatetime: LocalDateTime,
        expiresDatetime: LocalDateTime,
    ): RefreshToken {
        val entity = DbRefreshToken()
        entity.playerId = playerId
        entity.tokenHash = tokenHash
        entity.issuedDatetime = issuedDatetime
        entity.expiresDatetime = expiresDatetime
        refreshTokenBhv.insert(entity)
        return mapToModel(entity)
    }

    override fun findByHash(tokenHash: String): RefreshToken? {
        val optToken =
            refreshTokenBhv.selectEntity {
                it.query().setTokenHash_Equal(tokenHash)
            }
        return if (optToken.isPresent) mapToModel(optToken.get()) else null
    }

    override fun markUsed(
        id: Int,
        usedDatetime: LocalDateTime,
    ): Boolean {
        // 条件付き UPDATE (used_datetime IS NULL のときだけ)。queryUpdate は更新件数を返す。
        // entity にセットした列 (+ 共通更新列) のみが更新され、未セット列は NULL 上書きされない。
        val entity = DbRefreshToken()
        entity.usedDatetime = usedDatetime
        val updatedCount =
            refreshTokenBhv.queryUpdate(entity) {
                it.query().setRefreshTokenId_Equal(id)
                it.query().setUsedDatetime_IsNull()
            }
        return updatedCount > 0
    }

    override fun revoke(
        id: Int,
        revokedDatetime: LocalDateTime,
    ) {
        // PK 指定の partial update。DBFlute はセットした列 (revoked_datetime + 共通更新列) のみ更新する。
        val entity = DbRefreshToken()
        entity.refreshTokenId = id
        entity.revokedDatetime = revokedDatetime
        refreshTokenBhv.update(entity)
    }

    override fun revokeAllByPlayer(
        playerId: Int,
        revokedDatetime: LocalDateTime,
    ) {
        val entity = DbRefreshToken()
        entity.revokedDatetime = revokedDatetime
        refreshTokenBhv.queryUpdate(entity) {
            it.query().setPlayerId_Equal(playerId)
            it.query().setRevokedDatetime_IsNull()
        }
    }

    override fun deleteExpiredByPlayer(
        playerId: Int,
        now: LocalDateTime,
    ) {
        refreshTokenBhv.queryDelete {
            it.query().setPlayerId_Equal(playerId)
            it.query().setExpiresDatetime_LessThan(now)
        }
    }

    private fun mapToModel(entity: DbRefreshToken): RefreshToken =
        RefreshToken(
            id = entity.refreshTokenId,
            playerId = entity.playerId,
            tokenHash = entity.tokenHash,
            issuedDatetime = entity.issuedDatetime,
            expiresDatetime = entity.expiresDatetime,
            usedDatetime = entity.usedDatetime,
            revokedDatetime = entity.revokedDatetime,
        )
}
