package com.ort.app.infrastructure.datasource

import com.ort.app.domain.model.player.Authority
import com.ort.app.domain.model.player.Player
import com.ort.app.domain.model.player.PlayerRepository
import com.ort.app.domain.model.player.Players
import com.ort.app.domain.model.village.VillageStatus
import com.ort.dbflute.exbhv.PlayerBhv
import com.ort.dbflute.exbhv.PlayerDetailBhv
import com.ort.dbflute.exbhv.VillageBhv
import com.ort.dbflute.exentity.PlayerDetail
import com.ort.dbflute.exentity.Village
import org.dbflute.cbean.result.PagingResultBean
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Repository
import com.ort.dbflute.exentity.Player as DbPlayer

@Repository
class PlayerDataSource(
    private val playerBhv: PlayerBhv,
    private val playerDetailBhv: PlayerDetailBhv,
    private val villageBhv: VillageBhv,
    private val passwordEncoder: PasswordEncoder
) : PlayerRepository {

    override fun findPlayers(
        pageSize: Int,
        pageNum: Int,
        playerName: String?
    ): Players {
        val playerPage = playerBhv.selectPage {
            // 参加したことがあるプレイヤーのみ
            it.query().existsVillagePlayer { vpCB ->
                vpCB.query().setIsGone_Equal_False()
                vpCB.query().queryVillage()
                    .setVillageStatusCode_InScope_AsVillageStatus(VillageStatus.settledStatusList)
            }
            playerName?.let { name ->
                it.query().setPlayerName_LikeSearch(name) { it.likeContain() }
            }
            it.query().addOrderBy_PlayerName_Asc()
            it.paging(pageSize, pageNum)
        }
        return mapPlayersWithPaging(playerPage)
    }

    override fun findPlayers(villageIdList: List<Int>): Players {
        val playerList = playerBhv.selectList {
            it.query().existsVillagePlayer { vpCB ->
                if (villageIdList.isNotEmpty()) {
                    vpCB.query().setVillageId_InScope(villageIdList)
                }
                vpCB.query().setIsGone_Equal_False()
            }
        }
        return mapPlayers(playerList)
    }

    override fun findPlayers(villageId: Int): Players {
        val playerList = playerBhv.selectList {
            it.query().existsVillagePlayer { vpCB ->
                vpCB.query().setVillageId_Equal(villageId)
            }
        }
        return mapPlayers(playerList)
    }

    override fun findPlayer(userName: String): Player? {
        val optPlayer = playerBhv.selectEntity {
            it.setupSelect_PlayerDetailAsOne()
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

    override fun findPlayer(id: Int): Player {
        val player = playerBhv.selectEntityWithDeletedCheck {
            it.setupSelect_PlayerDetailAsOne()
            it.query().setPlayerId_Equal(id)
        }
        // 参加した村
        playerBhv.loadVillagePlayer(player) {
            it.setupSelect_Village()
            it.query().setIsGone_Equal_False()
        }
        // 自分が建てた村
        val villageList = villageBhv.selectList {
            it.query().setCreatePlayerName_Equal(player.playerName)
        }
        return mapPlayer(player, villageList)
    }

    override fun registerPlayer(userName: String, password: String): Player {
        val player = DbPlayer()
        player.playerName = userName
        player.playerPassword = passwordEncoder.encode(password)
        player.setAuthorityCode_プレイヤー()
        player.setIsRestrictedParticipation_False()
        player.shouldCheckAccessInfo = true
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

    override fun updateDetail(userName: String, twitterUserName: String?, introduction: String?) {
        val player = findPlayer(userName) ?: return
        val detail = PlayerDetail()
        detail.playerId = player.id
        detail.twitterUserName = twitterUserName
        detail.introduction = introduction
        val exists = playerDetailBhv.selectByPK(player.id)
        if (exists.isPresent) {
            playerDetailBhv.update(detail)
        } else playerDetailBhv.insert(detail)
    }

    override fun updateDifference(current: Players, changed: Players) {
        changed.list.forEach { changedP ->
            val currentP = current.list.first { it.id == changedP.id }
            if (changedP.isRestrictedParticipation != currentP.isRestrictedParticipation) {
                val p = DbPlayer()
                p.playerId = changedP.id
                p.isRestrictedParticipation = changedP.isRestrictedParticipation
                playerBhv.update(p)
            }
        }
    }

    private fun mapPlayersWithPaging(playerPage: PagingResultBean<DbPlayer>): Players {
        return Players(
            list = playerPage.map { mapSimplePlayer(it) },
            allPageCount = playerPage.allPageCount,
            isExistPrePage = playerPage.existsPreviousPage(),
            isExistNextPage = playerPage.existsNextPage(),
            currentPageNum = playerPage.currentPageNumber
        )
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
            twitterUserName = null,
            introduction = null,
            authority = Authority(player.authorityCodeAsAuthority),
            isRestrictedParticipation = player.isRestrictedParticipation,
            shouldCheckAccessInfo = player.shouldCheckAccessInfo
        )
    }

    private fun mapPlayer(player: DbPlayer, villageList: List<Village>): Player {
        return Player(
            id = player.playerId,
            name = player.playerName,
            twitterUserName = player.playerDetailAsOne.map { it.twitterUserName }.orElse(null),
            introduction = player.playerDetailAsOne.map { it.introduction }.orElse(null),
            authority = Authority(player.authorityCodeAsAuthority),
            isRestrictedParticipation = player.isRestrictedParticipation,
            shouldCheckAccessInfo = player.shouldCheckAccessInfo,
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