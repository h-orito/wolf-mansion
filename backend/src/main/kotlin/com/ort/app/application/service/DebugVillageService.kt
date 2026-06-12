package com.ort.app.application.service

import com.ort.app.application.coordinator.DaychangeCoordinator
import com.ort.app.application.coordinator.VillageCoordinator
import com.ort.app.domain.model.skill.Skills
import com.ort.app.fw.exception.WolfMansionBusinessException
import com.ort.dbflute.cbean.VillageDayCB
import com.ort.dbflute.exbhv.VillageDayBhv
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
    private val villageService: VillageService,
    private val playerService: PlayerService,
    private val charaService: CharaService,
    private val daychangeCoordinator: DaychangeCoordinator,
    private val villageCoordinator: VillageCoordinator,
) {
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
        for (i in charaList.indices) {
            val playerId = i + 2
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
            val player = playerService.findPlayer(playerId)
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
