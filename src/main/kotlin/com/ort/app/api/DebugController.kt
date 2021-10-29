package com.ort.app.api

import com.ort.app.api.request.VillageParticipateForm
import com.ort.app.application.coordinator.DaychangeCoordinator
import com.ort.app.application.coordinator.VillageCoordinator
import com.ort.app.application.service.PlayerService
import com.ort.app.application.service.VillageService
import com.ort.app.domain.model.skill.Skills
import com.ort.dbflute.cbean.CharaCB
import com.ort.dbflute.cbean.VillageDayCB
import com.ort.dbflute.exbhv.CharaBhv
import com.ort.dbflute.exbhv.VillageDayBhv
import com.ort.dbflute.exentity.VillageDay
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import java.time.LocalDateTime

@Controller
class DebugController(
    private val charaBhv: CharaBhv,
    private val villageDayBhv: VillageDayBhv,
    private val villageService: VillageService,
    private val playerService: PlayerService,
    private val daychangeCoordinator: DaychangeCoordinator,
    private val villageCoordinator: VillageCoordinator
) {

    @Value("\${app.debug:}")
    private lateinit var debug: String

    // 管理者機能：参戦
    @PostMapping("/village/{villageId}/allparticipate")
    private fun allparticipate(
        @PathVariable villageId: Int,
        participateForm: VillageParticipateForm
    ): String {
        if (!debug.toBoolean()) return "redirect:/village/$villageId#bottom"

        // 参戦していないキャラを人数分探す
        val charaList = charaBhv.selectList { cb: CharaCB ->
            cb.query()
                .queryCharaGroup()
                .existsVillageSettings { it.query().setVillageId_Equal(villageId) }
            cb.query().notExistsVillagePlayer {
                it.query().setVillageId_Equal(villageId)
                it.query().setIsGone_Equal_False()
            }
            cb.fetchFirst(participateForm.personNumber!!)
        }
        val village = villageService.findVillage(villageId)!!
        for (i in charaList.indices) {
            val playerId = i + 2
            // 希望役職をランダムに取得
            val randomSkill = Skills.all().list.shuffled().first()
            val randomSkill2 = Skills.all().list.shuffled().first()
            // 入村
            val player = playerService.findPlayer(playerId)
            villageCoordinator.participate(
                village = village,
                player = player,
                charaId = charaList[i].charaId,
                firstRequestSkill = randomSkill,
                secondRequestSkill = randomSkill2,
                joinMessage = "テストアカウントによる入村です。",
                joinPassword = village.setting.joinPassword,
                isSpectator = false
            )
        }
        return "redirect:/village/$villageId#bottom"
    }

    // 管理者機能：時間を進める
    @PostMapping("/village/{villageId}/dayChange")
    private fun daychange(@PathVariable villageId: Int): String {
        if (!debug.toBoolean()) return "redirect:/village/$villageId#bottom"

        // 最新の日付の更新日時を今にする
        val latestDay = villageDayBhv.selectEntity { cb: VillageDayCB ->
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
        return "redirect:/village/$villageId#bottom"
    }
}