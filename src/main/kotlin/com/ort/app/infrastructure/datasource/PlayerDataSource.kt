package com.ort.app.infrastructure.datasource

import com.ort.app.domain.model.player.Authority
import com.ort.app.domain.model.player.Player
import com.ort.app.domain.model.player.PlayerRepository
import com.ort.app.domain.model.player.Players
import com.ort.app.domain.model.village.VillageStatus
import com.ort.dbflute.exbhv.PlayerBhv
import com.ort.dbflute.exbhv.VillageBhv
import com.ort.dbflute.exentity.Village
import com.ort.dbflute.exentity.Player as DbPlayer
import org.springframework.stereotype.Repository

@Repository
class PlayerDataSource(
    private val playerBhv: PlayerBhv,
    private val villageBhv: VillageBhv
): PlayerRepository {

    override fun findPlayers(villageIdList: List<Int>): Players {
        val playerList = playerBhv.selectList{
            it.query().existsVillagePlayer { vpCB ->
            vpCB.query().setVillageId_InScope(villageIdList)
                vpCB.query().setIsGone_Equal_False()
            }
        }
        return mapPlayers(playerList)
    }

    override fun findPlayer(userName: String): Player? {
        val optPlayer = playerBhv.selectEntity{
            it.query().setPlayerName_Equal(userName)
        }
        if (!optPlayer.isPresent) return null
        val player = optPlayer.get()
        // 決着のついていない村
        playerBhv.loadVillagePlayer(player) {
            it.query().setIsGone_Equal_False()
            it.query().queryVillage().setVillageStatusCode_InScope_AsVillageStatus(VillageStatus.notSettledStatusList)
        }
        // 自分が建てて決着がついていない村
        val villageList = villageBhv.selectList {
            it.query().setCreatePlayerName_Equal(userName)
            it.query().setVillageStatusCode_InScope_AsVillageStatus(VillageStatus.notSettledStatusList)
        }
        return mapPlayer(player, villageList)
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
            isRestrictedParticipation = player.isRestrictedParticipation,
            participatingProgressVillageIdList = emptyList(),
            creatingProgressVillageIdList = emptyList()
        )
    }

    private fun mapPlayer(player: DbPlayer, villageList: List<Village>): Player {
        return Player(
            id = player.playerId,
            name = player.playerName,
            authority = Authority(player.authorityCodeAsAuthority),
            isRestrictedParticipation = player.isRestrictedParticipation,
            participatingProgressVillageIdList = player.villagePlayerList.map { it.villageId },
            creatingProgressVillageIdList = villageList.map { it.villageId }
        )
    }
}