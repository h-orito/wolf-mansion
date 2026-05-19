package com.ort.app.api.v1.villages

import com.ort.app.application.coordinator.CreatorCoordinator
import com.ort.app.application.service.PlayerService
import com.ort.app.application.service.VillageService
import com.ort.app.domain.model.player.Player
import com.ort.app.domain.model.village.Village
import com.ort.app.domain.model.village.participant.VillageParticipant
import com.ort.app.fw.exception.WolfMansionBusinessException
import com.ort.app.fw.exception.WolfMansionRecordNotFoundException
import com.ort.app.fw.util.WolfMansionUserInfoUtil
import org.springframework.stereotype.Component

/**
 * `/api/v1/villages/...` 配下の controller で頻出する「村ロード + 認証チェック」を集約する。
 *
 * 各操作系 controller (Say / Participate / Ability / Rp) で重複していたヘルパーを
 * ここに移し、認証メッセージや 404 メッセージのフォーマットを一カ所に集約する。
 */
@Component
class VillageContextLoader(
    private val villageService: VillageService,
    private val playerService: PlayerService,
    private val creatorCoordinator: CreatorCoordinator,
) {

    /** 村だけをロードする (匿名 OK)。村が見つからなければ 404。 */
    fun loadVillage(villageId: Int): Village =
        villageService.findVillage(villageId, excludeGone = false)
            ?: throw WolfMansionRecordNotFoundException("village not found. id=$villageId")

    /** 村と「参加中の自分」をロード。未認証 / 未参加なら 400。 */
    fun loadVillageAndRequireMyself(villageId: Int): Pair<Village, VillageParticipant> {
        val village = loadVillage(villageId)
        val user = WolfMansionUserInfoUtil.getUserInfo()
            ?: throw WolfMansionBusinessException("ログインが必要です")
        val myself = villageService.findVillageParticipant(village.id, user.username)
            ?: throw WolfMansionBusinessException("この村に参加していません")
        return village to myself
    }

    /**
     * 村と「現在ユーザ (= Player、参加していなくてもよい)」をロード。未認証なら 400。
     * 入村系で「これから参加しようとしている」ケース用。
     */
    fun loadVillageAndPlayer(villageId: Int): Pair<Village, Player> {
        val village = loadVillage(villageId)
        val user = WolfMansionUserInfoUtil.getUserInfo()
            ?: throw WolfMansionBusinessException("ログインが必要です")
        val player = playerService.findPlayer(user.username)
            ?: throw WolfMansionBusinessException("プレイヤー情報が見つかりません")
        return village to player
    }

    /** 村だけをロードし、ログイン状況にかかわらず参加情報があれば返す (read-only 系)。 */
    fun loadVillageAndOptionalMyself(villageId: Int): Pair<Village, VillageParticipant?> {
        val village = loadVillage(villageId)
        val user = WolfMansionUserInfoUtil.getUserInfo() ?: return village to null
        val myself = villageService.findVillageParticipant(village.id, user.username)
        return village to myself
    }

    /**
     * 村をロードし、現在ユーザがその村の村建てであることを確認する。
     * 旧 `CreatorCoordinator.isCreator` の判定 (Player ID=1 は全村 creator 扱い) に従う。
     * 未認証 / creator でない場合は 400。
     */
    fun loadVillageAndRequireCreator(villageId: Int): Village {
        val village = loadVillage(villageId)
        val user = WolfMansionUserInfoUtil.getUserInfo()
            ?: throw WolfMansionBusinessException("ログインが必要です")
        if (!creatorCoordinator.isCreator(user.username, villageId)) {
            throw WolfMansionBusinessException("この村の村建てではありません")
        }
        return village
    }
}
