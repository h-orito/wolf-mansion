package com.ort.app.application.service

import com.ort.dbflute.cbean.VillageDayCB
import com.ort.dbflute.cbean.VillagePlayerCB
import com.ort.dbflute.cbean.VoteCB
import com.ort.dbflute.exbhv.VillageDayBhv
import com.ort.dbflute.exbhv.VillagePlayerBhv
import com.ort.dbflute.exbhv.VoteBhv
import com.ort.dbflute.exentity.VillagePlayer
import com.ort.dbflute.exentity.Vote
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.function.Consumer
import java.util.stream.Collectors

/**
 * 管理者操作のうち集計的な一括更新は domain モデルを介さず DB を直接操作する
 * (AdminController からの移設。ドメインモデル経由に整理する場合は別途検討)。
 */
@Service
class AdminVillageService(
    private val villageDayBhv: VillageDayBhv,
    private val villagePlayerBhv: VillagePlayerBhv,
    private val voteBhv: VoteBhv,
) {
    fun updateAllLastAccessDatetime(villageId: Int) {
        val vp = VillagePlayer()
        vp.lastAccessDatetime = LocalDateTime.now()
        villagePlayerBhv.queryUpdate(vp) { cb: VillagePlayerCB ->
            cb.query().setVillageId_Equal(villageId)
        }
    }

    fun insertSelfVotesForNonVoters(villageId: Int) {
        val latestDay =
            villageDayBhv
                .selectEntity { cb: VillageDayCB ->
                    cb.query().setVillageId_Equal(villageId)
                    cb.query().addOrderBy_Day_Desc()
                    cb.fetchFirst(1)
                }.get()
                .day
        val voteCharaIdList =
            voteBhv
                .selectList { cb: VoteCB ->
                    cb.query().setVillageId_Equal(villageId)
                    cb.query().setDay_Equal(latestDay)
                }.stream()
                .map { obj: Vote -> obj.charaId }
                .collect(Collectors.toList())
        villagePlayerBhv
            .selectList { cb: VillagePlayerCB ->
                cb.query().setVillageId_Equal(villageId)
                cb.query().setIsGone_Equal_False()
                cb.query().setIsSpectator_Equal_False()
                cb.query().setIsDead_Equal_False()
                if (voteCharaIdList.isNotEmpty()) cb.query().setCharaId_NotInScope(voteCharaIdList)
            }.forEach(
                Consumer { vp: VillagePlayer ->
                    val vote = Vote()
                    vote.villageId = villageId
                    vote.day = latestDay
                    vote.charaId = vp.charaId
                    vote.voteCharaId = vp.charaId
                    voteBhv.insert(vote)
                },
            )
    }

    fun findVillageCharaPlayers(villageId: Int): List<VillageCharaPlayer> =
        villagePlayerBhv
            .selectList { cb: VillagePlayerCB ->
                cb.setupSelect_Player()
                cb.query().setVillageId_Equal(villageId)
                cb.query().setIsGone_Equal_False()
                cb.query().addOrderBy_VillagePlayerId_Asc()
            }.map { VillageCharaPlayer(charaName = it.charaName, playerName = it.player.get().playerName) }
}

data class VillageCharaPlayer(
    val charaName: String,
    val playerName: String,
)
