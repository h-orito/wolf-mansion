package com.ort.app.api

import com.ort.app.logic.VillageParticipateLogic
import com.ort.app.web.form.VillageLeaveForm
import com.ort.app.web.form.VillageParticipateForm
import com.ort.app.web.model.VillageCharaPlayerResultContent
import com.ort.dbflute.allcommon.CDef
import com.ort.dbflute.cbean.CharaCB
import com.ort.dbflute.cbean.VillageDayCB
import com.ort.dbflute.cbean.VillagePlayerCB
import com.ort.dbflute.cbean.VillageSettingsCB
import com.ort.dbflute.cbean.VoteCB
import com.ort.dbflute.exbhv.CharaBhv
import com.ort.dbflute.exbhv.VillageDayBhv
import com.ort.dbflute.exbhv.VillagePlayerBhv
import com.ort.dbflute.exbhv.VoteBhv
import com.ort.dbflute.exentity.VillageDay
import com.ort.dbflute.exentity.VillagePlayer
import com.ort.dbflute.exentity.Vote
import com.ort.fw.util.WolfMansionDateUtil
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.util.UriComponentsBuilder
import java.util.function.Consumer
import java.util.stream.Collectors

@Controller
class AdminController(
    private val charaBhv: CharaBhv,
    private val villageDayBhv: VillageDayBhv,
    private val villagePlayerBhv: VillagePlayerBhv,
    private val voteBhv: VoteBhv,
    private val villageLogic: VillageParticipateLogic
) {

    // 管理者機能：参戦
    @PostMapping("/admin/village/{villageId}/allparticipate")
    private fun allparticipate(
        @PathVariable villageId: Int,
        participateForm: VillageParticipateForm,
        model: Model
    ): String {
        // 参戦していないキャラを人数分探す
        val charaList = charaBhv.selectList { cb: CharaCB ->
            cb.query()
                .queryCharaGroup()
                .existsVillageSettings { villageSettingsCB: VillageSettingsCB ->
                    villageSettingsCB.query().setVillageId_Equal(villageId)
                }
            cb.query().notExistsVillagePlayer { villagePlayerCB: VillagePlayerCB ->
                villagePlayerCB.query().setVillageId_Equal(villageId)
                villagePlayerCB.query().setIsGone_Equal_False()
            }
            cb.fetchFirst(participateForm.personNumber)
        }
        for (i in charaList.indices) {
            val playerId = i + 2
            // 希望役職をランダムに取得
            val randomSkill = CDef.Skill.values()[(Math.random() * CDef.Skill.values().size - 1).toInt()]
            val randomSkill2 = CDef.Skill.values()[(Math.random() * CDef.Skill.values().size - 1).toInt()]
            // 入村
            villageLogic.participate(
                villageId, playerId, charaList[i].charaId, randomSkill, randomSkill2,
                "テストアカウント入村です。playerId：$playerId", false, true
            )
        }
        return "redirect:/village/$villageId#bottom"
    }

    // 管理者機能：時間を進める
    @PostMapping("/admin/village/{villageId}/dayChange")
    private fun daychange(@PathVariable villageId: Int): String {
        // 最新の日付の更新日時を今にする
        val latestDay = villageDayBhv.selectEntity { cb: VillageDayCB ->
            cb.query().setVillageId_Equal(villageId)
            cb.query().addOrderBy_Day_Desc()
            cb.fetchFirst(1)
        }.get()
        val villageDay = VillageDay()
        villageDay.daychangeDatetime = WolfMansionDateUtil.currentLocalDateTime().minusSeconds(1L)
        villageDayBhv.queryUpdate(villageDay) { cb: VillageDayCB ->
            cb.query().setVillageId_Equal(villageId)
            cb.query().setDay_Equal(latestDay.day)
        }
        return "redirect:/village/$villageId#bottom"
    }

    // 管理者機能：強制退村
    @PostMapping("/admin/village/{villageId}/leave")
    private fun leave(@PathVariable villageId: Int, leaveForm: VillageLeaveForm): String {
        if (leaveForm.villagePlayerId == null) {
            return "redirect:/village/$villageId#bottom"
        }
        val optVPlayer = villagePlayerBhv.selectByPK(leaveForm.villagePlayerId)
        if (!optVPlayer.isPresent) {
            return "redirect:/village/$villageId#bottom"
        }
        // 退村させる
        villageLogic.leave(optVPlayer.get())
        return "redirect:/village/$villageId#bottom"
    }

    // 管理者機能：全員アクセス
    @PostMapping("/admin/village/{villageId}/access")
    private fun leave(@PathVariable villageId: Int): String {
        val vp = VillagePlayer()
        vp.lastAccessDatetime = WolfMansionDateUtil.currentLocalDateTime()
        villagePlayerBhv.queryUpdate(vp) { cb: VillagePlayerCB ->
            cb.query().setVillageId_Equal(villageId)
        }
        return "redirect:/village/$villageId#bottom"
    }

    // 管理者機能：全員自分投票
    @PostMapping("/admin/village/{villageId}/vote")
    private fun vote(@PathVariable villageId: Int, builder: UriComponentsBuilder): String {
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
            cb.query().setCharaId_NotInScope(voteCharaIdList)
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
    @GetMapping("/admin/village/{villageId}/player")
    @ResponseBody
    private fun player(@PathVariable villageId: Int): List<VillageCharaPlayerResultContent> {
        return villagePlayerBhv.selectList { cb: VillagePlayerCB ->
            cb.setupSelect_Chara()
            cb.setupSelect_Player()
            cb.query().setVillageId_Equal(villageId)
            cb.query().setIsGone_Equal_False()
            cb.query().addOrderBy_VillagePlayerId_Asc()
        }.stream().map { vp: VillagePlayer ->
            val charaPlayer = VillageCharaPlayerResultContent()
            charaPlayer.charaName = vp.charaName
            charaPlayer.playerName = vp.player.get().playerName
            charaPlayer
        }.collect(Collectors.toList())
    }

}