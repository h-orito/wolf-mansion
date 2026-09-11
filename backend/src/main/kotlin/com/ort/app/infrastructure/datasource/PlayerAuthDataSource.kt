package com.ort.app.infrastructure.datasource

import com.ort.app.domain.model.auth.PlayerAuth
import com.ort.app.domain.model.auth.PlayerAuthRepository
import com.ort.dbflute.exbhv.PlayerBhv
import org.springframework.stereotype.Repository

@Repository
class PlayerAuthDataSource(
    private val playerBhv: PlayerBhv,
) : PlayerAuthRepository {
    override fun findByName(name: String): PlayerAuth? {
        val optPlayer =
            playerBhv.selectEntity {
                it.query().setPlayerName_Equal(name)
            }
        return optPlayer.map { toPlayerAuth(it) }.orElse(null)
    }

    override fun findById(playerId: Int): PlayerAuth? {
        val optPlayer =
            playerBhv.selectEntity {
                it.query().setPlayerId_Equal(playerId)
            }
        return optPlayer.map { toPlayerAuth(it) }.orElse(null)
    }

    private fun toPlayerAuth(player: com.ort.dbflute.exentity.Player): PlayerAuth =
        PlayerAuth(
            playerId = player.playerId,
            name = player.playerName,
            passwordHash = player.playerPassword,
            authorities = listOf(player.authorityCodeAsAuthority.code()),
        )
}
