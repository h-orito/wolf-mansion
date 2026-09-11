package com.ort.app.application.service

import com.ort.app.application.coordinator.DaychangeCoordinator
import com.ort.app.application.coordinator.VillageCoordinator
import com.ort.app.domain.model.skill.Skills
import com.ort.app.fw.exception.WolfMansionBusinessException
import com.ort.dbflute.cbean.PlayerCB
import com.ort.dbflute.cbean.VillageDayCB
import com.ort.dbflute.exbhv.PlayerBhv
import com.ort.dbflute.exbhv.VillageDayBhv
import com.ort.dbflute.exentity.Player
import com.ort.dbflute.exentity.VillageDay
import org.springframework.stereotype.Service
import java.time.LocalDateTime

/**
 * ローカル開発向けのデバッグ操作。集計的な一括更新は domain モデルを介さず DB を直接操作する
 * (既存 DebugController からの移設)。
 */
@Service
class DebugVillageService(
    private val villageDayBhv: VillageDayBhv,
    private val playerBhv: PlayerBhv,
    private val villageService: VillageService,
    private val playerService: PlayerService,
    private val charaService: CharaService,
    private val daychangeCoordinator: DaychangeCoordinator,
    private val villageCoordinator: VillageCoordinator,
) {
    companion object {
        // ローカル開発 DB のテストアカウント (testuser01〜16)
        private val TEST_PLAYER_IDS = (2..17).toList()
    }

    fun allParticipate(
        villageId: Int,
        personNumber: Int,
    ) {
        val village = villageService.findVillage(villageId) ?: throw WolfMansionBusinessException("village not found.")
        val charas =
            charaService.findCharas(
                village.setting.chara.charachipIds
                    .first(),
                false,
            )
        val charaList =
            charas.list
                .filterNot { c -> village.participants.list.any { it.charaId == c.id } }
                .take(personNumber)
        // 放置村の突然死ペナルティで参加制限が付いたテストアカウントがいると
        // 村を用意できなくなるため、デバッグ用途では制限を解除してから参加させる
        unrestrictTestPlayers()
        val players =
            TEST_PLAYER_IDS
                .map { playerService.findPlayer(it) }
                .filter { it.isAvailableParticipateVillage(village.id) }
                .take(charaList.size)
        if (players.size < charaList.size) {
            throw WolfMansionBusinessException("参加可能なテストアカウントが不足しています")
        }
        players.forEachIndexed { i, player ->
            val randomSkill =
                Skills
                    .all()
                    .list
                    .shuffled()
                    .first()
            val randomSkill2 =
                Skills
                    .all()
                    .list
                    .shuffled()
                    .first()
            villageCoordinator.participate(
                village = village,
                player = player,
                charaId = charaList[i].id,
                charaName = charaList[i].name,
                charaShortName = charaList[i].shortName,
                charaImageFile = null,
                firstRequestSkill = randomSkill,
                secondRequestSkill = randomSkill2,
                joinMessage = "テストアカウントによる入村です。",
                joinPassword = village.setting.joinPassword,
                isSpectator = false,
                ipAddress = "test account $i",
            )
        }
    }

    private fun unrestrictTestPlayers() {
        val entity = Player()
        entity.setIsRestrictedParticipation(false)
        playerBhv.queryUpdate(entity) { cb: PlayerCB ->
            cb.query().setPlayerId_InScope(TEST_PLAYER_IDS)
            cb.query().setIsRestrictedParticipation_Equal(true)
        }
    }

    fun forceDayChange(villageId: Int) {
        val latestDay =
            villageDayBhv
                .selectEntity { cb: VillageDayCB ->
                    cb.query().setVillageId_Equal(villageId)
                    cb.query().addOrderBy_Day_Desc()
                    cb.fetchFirst(1)
                }.get()
        val villageDay = VillageDay()
        villageDay.daychangeDatetime = LocalDateTime.now().minusSeconds(1L)
        villageDayBhv.queryUpdate(villageDay) { cb: VillageDayCB ->
            cb.query().setVillageId_Equal(villageId)
            cb.query().setDay_Equal(latestDay.day)
        }
        val village = villageService.findVillage(villageId)!!
        daychangeCoordinator.changeDayIfNeeded(village)
    }
}
