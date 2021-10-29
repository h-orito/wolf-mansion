package com.ort.app.application.coordinator

import com.ort.app.application.service.PlayerService
import com.ort.app.application.service.VillageApplicationService
import com.ort.app.domain.model.village.Village
import com.ort.app.domain.service.MessageDomainService
import com.ort.app.fw.exception.WolfMansionBusinessException
import org.springframework.stereotype.Service

@Service
class CreatorCoordinator(
    private val messageCoordinator: MessageCoordinator,
    private val villageService: VillageApplicationService,
    private val playerService: PlayerService,
    private val messageDomainService: MessageDomainService
) {

    fun isCreator(userName: String?, villageId: Int): Boolean {
        userName ?: return false
        val player = playerService.findPlayer(userName) ?: return false
        return player.id == 1 ||
                (player.createProgressVillageIdList + player.createFinishedVillageIdList).contains(villageId)
    }

    fun kick(villageId: Int, charaId: Int) {
        val village = villageService.findVillage(villageId)
            ?: throw WolfMansionBusinessException("village not found. id: $villageId")
        val target = village.allParticipants(excludeDummy = true).chara(charaId)
        // 退村させる
        villageService.leave(target)
        // 退村メッセージを登録
        messageCoordinator.registerMessage(villageId, messageDomainService.createLeaveMessage(target))
    }

    fun say(villageId: Int, text: String, isConvertDisable: Boolean) {
        val village = villageService.findVillage(villageId)
            ?: throw WolfMansionBusinessException("village not found. id: $villageId")
        messageCoordinator.registerMessage(
            villageId,
            messageDomainService.createCreatorMessage(village, text, isConvertDisable)
        )
    }

    fun cancel(villageId: Int) {
        val village = villageService.findVillage(villageId)
            ?: throw WolfMansionBusinessException("village not found. id: $villageId")
        if (!village.canCancel()) throw WolfMansionBusinessException("プロローグ中でなければ廃村できません")
        villageService.cancel(villageId)
    }

    fun extendEpilogue(villageId: Int) {
        val village = villageService.findVillage(villageId)
            ?: throw WolfMansionBusinessException("village not found. id: $villageId")
        if (!village.canExtendEpilogue()) throw WolfMansionBusinessException("エピローグ中でなければ延長できません")
        // エピローグを1日延長する
        villageService.extendDay(
            villageId,
            village.days.latestDay().day,
            village.days.latestDay().dayChangeDatetime.plusDays(1L)
        )
    }

    fun saveSettings(village: Village) {
        // 設定変更
        village.assertModifySetting()
        villageService.updateSetting(village)
        // 設定変更メッセージ登録
        messageCoordinator.registerMessage(village.id, messageDomainService.createModifySettingMessage())
    }
}