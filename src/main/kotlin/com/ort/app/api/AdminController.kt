package com.ort.app.api

import com.ort.app.api.request.VillageLeaveForm
import com.ort.app.application.coordinator.VillageCoordinator
import com.ort.app.application.service.CharaService
import com.ort.app.application.service.VillageService
import com.ort.app.fw.exception.WolfMansionBusinessException
import com.ort.dbflute.cbean.VillageDayCB
import com.ort.dbflute.cbean.VillagePlayerCB
import com.ort.dbflute.cbean.VoteCB
import com.ort.dbflute.exbhv.VillageDayBhv
import com.ort.dbflute.exbhv.VillagePlayerBhv
import com.ort.dbflute.exbhv.VoteBhv
import com.ort.dbflute.exentity.VillagePlayer
import com.ort.dbflute.exentity.Vote
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.ResponseBody
import java.time.LocalDateTime
import java.util.function.Consumer
import java.util.stream.Collectors

@Controller
class AdminController(
    private val villageDayBhv: VillageDayBhv,
    private val villagePlayerBhv: VillagePlayerBhv,
    private val voteBhv: VoteBhv,
    private val villageService: VillageService,
    private val villageCoordinator: VillageCoordinator
) {
    // 管理者機能：強制退村
    @PostMapping("/admin/village/{villageId}/leave")
    private fun leave(@PathVariable villageId: Int, leaveForm: VillageLeaveForm): String {
        if (leaveForm.villagePlayerId == null) {
            return "redirect:/village/$villageId#bottom"
        }
        // 退村させる
        val village = villageService.findVillage(villageId) ?: return "redirect:/village/$villageId#bottom"
        val participant = villageService.findVillageParticipant(leaveForm.villagePlayerId)
            ?: return "redirect:/village/$villageId#bottom"
        villageCoordinator.leave(village, participant)
        return "redirect:/village/$villageId#bottom"
    }

    // 管理者機能：全員アクセス
    @PostMapping("/admin/village/{villageId}/access")
    private fun access(@PathVariable villageId: Int): String {
        val vp = VillagePlayer()
        vp.lastAccessDatetime = LocalDateTime.now()
        villagePlayerBhv.queryUpdate(vp) { cb: VillagePlayerCB ->
            cb.query().setVillageId_Equal(villageId)
        }
        return "redirect:/village/$villageId#bottom"
    }

    // 管理者機能：全員自分投票
    @PostMapping("/admin/village/{villageId}/vote")
    private fun vote(@PathVariable villageId: Int): String {
        val latestDay = villageDayBhv.selectEntity { cb: VillageDayCB ->
            cb.query().setVillageId_Equal(villageId)
            cb.query().addOrderBy_Day_Desc()
            cb.fetchFirst(1)
        }.get().day
        val voteCharaIdList = voteBhv.selectList { cb: VoteCB ->
            cb.query().setVillageId_Equal(villageId)
            cb.query().setDay_Equal(latestDay)
        }.stream().map { obj: Vote -> obj.charaId }.collect(Collectors.toList())
        villagePlayerBhv.selectList { cb: VillagePlayerCB ->
            cb.query().setVillageId_Equal(villageId)
            cb.query().setIsGone_Equal_False()
            cb.query().setIsSpectator_Equal_False()
            cb.query().setIsDead_Equal_False()
            if (voteCharaIdList.isNotEmpty()) cb.query().setCharaId_NotInScope(voteCharaIdList)
        }.forEach(Consumer { vp: VillagePlayer ->
            val vote = Vote()
            vote.villageId = villageId
            vote.day = latestDay
            vote.charaId = vp.charaId
            vote.voteCharaId = vp.charaId
            voteBhv.insert(vote)
        })
        return "redirect:/village/$villageId#bottom"
    }

    // 管理者機能：参加プレイヤー
    @GetMapping("/admin/village/{villageId}/player", produces=["application/json;charset=UTF-8"])
    @ResponseBody
    private fun player(@PathVariable villageId: Int): List<VillageCharaPlayerContent> {
        return villagePlayerBhv.selectList { cb: VillagePlayerCB ->
            cb.setupSelect_Player()
            cb.query().setVillageId_Equal(villageId)
            cb.query().setIsGone_Equal_False()
            cb.query().addOrderBy_VillagePlayerId_Asc()
        }.map { VillageCharaPlayerContent(charaName = it.charaName, playerName = it.player.get().playerName) }
    }

    data class VillageCharaPlayerContent(val charaName: String, val playerName: String)
}