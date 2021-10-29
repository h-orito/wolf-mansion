package com.ort.app.domain.service.message.say

import com.ort.app.domain.model.village.Village
import com.ort.app.domain.model.village.participant.VillageParticipant
import org.springframework.stereotype.Service

@Service
class NormalSayDomainService : SayTypeDomainService {

    override fun isViewable(village: Village, myself: VillageParticipant?, day: Int): Boolean {
        return true
    }

    override fun isSayable(village: Village, myself: VillageParticipant?): Boolean {
        myself ?: return false
        return myself.isSayableNormalSay(village.status.isEpilogue()) && village.isSayableNormalSay()
    }
}