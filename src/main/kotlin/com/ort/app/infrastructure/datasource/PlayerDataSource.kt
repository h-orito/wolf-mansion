package com.ort.app.infrastructure.datasource

import com.ort.app.domain.model.player.Authority
import com.ort.app.domain.model.player.Player
import com.ort.app.domain.model.player.PlayerRepository
import com.ort.app.domain.model.player.Players
import com.ort.app.domain.model.village.VillageStatus
import com.ort.dbflute.exbhv.PlayerBhv
import com.ort.dbflute.exbhv.VillageBhv
import com.ort.dbflute.exentity.Village
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Repository
import com.ort.dbflute.exentity.Player as DbPlayer

@Repository
class PlayerDataSource(
    private val playerBhv: PlayerBhv,
    private val villageBhv: VillageBhv,
    private val passwordEncoder: PasswordEncoder
) : PlayerRepository {

    override fun findPlayers(villageIdList: List<Int>): Players {
        val playerList = playerBhv.selectList {
            it.query().existsVillagePlayer { vpCB ->
                vpCB.query().setVillageId_InScope(villageIdList)
                vpCB.query().setIsGone_Equal_False()
            }
        }
        return mapPlayers(playerList)
    }

    override fun findPlayer(userName: String): Player? {
        val optPlayer = playerBhv.selectEntity {
            it.query().setPlayerName_Equal(userName)
        }
        if (!optPlayer.isPresent) return null
        val player = optPlayer.get()
        // 参加した村
        playerBhv.loadVillagePlayer(player) {
            it.setupSelect_Village()
            it.query().setIsGone_Equal_False()
        }
        // 自分が建てた村
        val villageList = villageBhv.selectList {
            it.query().setCreatePlayerName_Equal(userName)
        }
        return mapPlayer(player, villageList)
    }

    override fun registerPlayer(userName: String, password: String): Player {
        val player = DbPlayer()
        player.playerName = userName
        player.playerPassword = passwordEncoder.encode(password)
        player.setAuthorityCode_プレイヤー()
        player.setIsRestrictedParticipation_False()
        playerBhv.insert(player)
        return findPlayer(userName)!!
    }

    override fun updatePassword(userName: String, password: String) {
        val player = DbPlayer()
        player.playerPassword = passwordEncoder.encode(password)
        playerBhv.queryUpdate(player) {
            it.query().setPlayerName_Equal(userName)
        }
    }

    private fun mapPlayers(playerList: List<DbPlayer>): Players {
        return Players(
            list = playerList.map { mapSimplePlayer(it) }
        )
    }

    private fun mapSimplePlayer(player: DbPlayer): Player {
        return Player(
            id = player.playerId,
            name = player.playerName,
            authority = Authority(player.authorityCodeAsAuthority),
            isRestrictedParticipation = player.isRestrictedParticipation
        )
    }

    private fun mapPlayer(player: DbPlayer, villageList: List<Village>): Player {
        return Player(
            id = player.playerId,
            name = player.playerName,
            authority = Authority(player.authorityCodeAsAuthority),
            isRestrictedParticipation = player.isRestrictedParticipation,
            participateProgressVillageIdList = player.villagePlayerList.filter {
                VillageStatus.notSettledStatusList.contains(it.village.get().villageStatusCodeAsVillageStatus)
            }.map { it.villageId },
            participateFinishedVillageIdList = player.villagePlayerList.filter {
                VillageStatus.settledStatusList.contains(it.village.get().villageStatusCodeAsVillageStatus)
            }.map { it.villageId },
            createProgressVillageIdList = villageList.filter {
                VillageStatus.notSettledStatusList.contains(it.villageStatusCodeAsVillageStatus)
            }.map { it.villageId },
            createFinishedVillageIdList = villageList.filter {
                VillageStatus.settledStatusList.contains(it.villageStatusCodeAsVillageStatus)
            }.map { it.villageId }
        )
    }
}