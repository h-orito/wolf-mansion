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

/**
 * 管理者向けの村操作。
 *
 * 旧 `AdminController` (Thymeleaf form) で controller 内に書かれていた DBFlute Bhv 直叩きを
 * application 層に移してテスト境界を整理したもの。本来は infrastructure 層 (DataSource)
 * に切り出すのがレイヤー的に正しいが、Step 8e の差分を小さく保つため旧実装に合わせ Bhv を
 * 直接利用している。 Step 11 cutover 後の整理で DataSource 化を検討する。
 */
@Service
class AdminVillageService(
    private val villageDayBhv: VillageDayBhv,
    private val villagePlayerBhv: VillagePlayerBhv,
    private val voteBhv: VoteBhv,
) {

    /** 村の全参加者の最終アクセス時刻を `now` に更新する。 */
    fun updateAllLastAccess(villageId: Int) {
        val vp = VillagePlayer()
        vp.lastAccessDatetime = LocalDateTime.now()
        villagePlayerBhv.queryUpdate(vp) { cb: VillagePlayerCB ->
            cb.query().setVillageId_Equal(villageId)
        }
    }

    /** 当日の生存中・現存参加者のうち、まだ投票していない者を全員「自分に投票」させる。 */
    fun voteForSelfAll(villageId: Int) {
        val latestDay = villageDayBhv.selectEntity { cb: VillageDayCB ->
            cb.query().setVillageId_Equal(villageId)
            cb.query().addOrderBy_Day_Desc()
            cb.fetchFirst(1)
        }.get().day
        val voteCharaIdList = voteBhv.selectList { cb: VoteCB ->
            cb.query().setVillageId_Equal(villageId)
            cb.query().setDay_Equal(latestDay)
        }.map { it.charaId }
        villagePlayerBhv.selectList { cb: VillagePlayerCB ->
            cb.query().setVillageId_Equal(villageId)
            cb.query().setIsGone_Equal_False()
            cb.query().setIsSpectator_Equal_False()
            cb.query().setIsDead_Equal_False()
            if (voteCharaIdList.isNotEmpty()) cb.query().setCharaId_NotInScope(voteCharaIdList)
        }.forEach { vp ->
            val vote = Vote()
            vote.villageId = villageId
            vote.day = latestDay
            vote.charaId = vp.charaId
            vote.voteCharaId = vp.charaId
            voteBhv.insert(vote)
        }
    }

    /** 村の全参加者の「キャラ名 / 中の人名」を返す (gone 含まない)。 */
    fun listVillageCharaPlayers(villageId: Int): List<CharaPlayerPair> {
        return villagePlayerBhv.selectList { cb: VillagePlayerCB ->
            cb.setupSelect_Player()
            cb.query().setVillageId_Equal(villageId)
            cb.query().setIsGone_Equal_False()
            cb.query().addOrderBy_VillagePlayerId_Asc()
        }.map { vp ->
            CharaPlayerPair(
                charaName = vp.charaName,
                playerName = vp.player.get().playerName,
            )
        }
    }

    data class CharaPlayerPair(val charaName: String, val playerName: String)
}
