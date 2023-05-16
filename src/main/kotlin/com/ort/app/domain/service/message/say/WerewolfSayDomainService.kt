package com.ort.app.domain.service.message.say

import com.ort.app.domain.model.player.Player
import com.ort.app.domain.model.village.Village
import com.ort.app.domain.model.village.participant.VillageParticipant
import org.springframework.stereotype.Service

@Service
class WerewolfSayDomainService : SayTypeDomainService {

    override fun isViewable(
        village: Village,
        myself: VillageParticipant?,
        player: Player?,
        day: Int
    ): Boolean =
        village.isViewableWerewolfSay(player) || myself?.isViewableWerewolfSay() ?: false

    override fun isSayable(village: Village, myself: VillageParticipant?): Boolean =
        village.isSayableWerewolfSay() && myself?.isSayableWerewolfSay() ?: false
}