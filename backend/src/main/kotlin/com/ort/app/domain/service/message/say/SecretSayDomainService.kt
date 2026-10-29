package com.ort.app.domain.service.message.say

import com.ort.app.domain.model.player.Player
import com.ort.app.domain.model.village.Village
import com.ort.app.domain.model.village.participant.VillageParticipant
import com.ort.app.fw.exception.WolfMansionBusinessException
import org.springframework.stereotype.Service

@Service
class SecretSayDomainService : SayTypeDomainService {
    override fun isViewable(
        village: Village,
        myself: VillageParticipant?,
        player: Player?,
        day: Int,
    ): Boolean = village.isViewableSecretSay(player) || myself?.isViewableSecretSay() ?: false

    override fun isSayable(
        village: Village,
        myself: VillageParticipant?,
        player: Player?,
    ): Boolean =
        if (myself?.isAdmin() == true) {
            true
        } else if (village.isCreator(player)) {
            true
        } else {
            village.isSayableSecretSay() && myself?.isSayableSecretSay() ?: false
        }

    fun getSelectableTargetList(
        village: Village,
        creatorPlayerId: Int,
        myself: VillageParticipant,
        player: Player,
    ): List<VillageParticipant> {
        val base =
            village
                .allParticipants()
                .sortedByRoomNumber()
                .list
                .filter { it.id != myself.id }
        // 管理者および村建ては全員
        return if (myself.isAdmin() || village.isCreator(player)) {
            base
        } // 全員秘話可能
        else if (village.setting.rule.secretSayRange
                .canSayAll()
        ) {
            base
        } // 村建てとのみ
        else if (village.setting.rule.secretSayRange
                .canOnlyCreator()
        ) {
            base.filter { it.playerId == creatorPlayerId }
        } // 秘話なし
        else {
            emptyList()
        }
    }

    /**
     * 秘話の宛先が、村の秘話の範囲 (全員・村建てとのみ等) で選べる相手か
     */
    fun assertSecretSayTarget(
        village: Village,
        creatorPlayerId: Int,
        myself: VillageParticipant,
        player: Player,
        target: VillageParticipant?,
    ) {
        target ?: throw WolfMansionBusinessException("秘話の相手を選択してください")
        if (getSelectableTargetList(village, creatorPlayerId, myself, player).none { it.id == target.id }) {
            throw WolfMansionBusinessException("秘話を送れない相手です")
        }
    }
}
