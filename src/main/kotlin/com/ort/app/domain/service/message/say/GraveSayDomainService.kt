package com.ort.app.domain.service.message.say

import com.ort.app.domain.model.village.Village
import com.ort.app.domain.model.village.participant.VillageParticipant
import org.springframework.stereotype.Service

@Service
class GraveSayDomainService : SayTypeDomainService {

    override fun isViewable(village: Village, myself: VillageParticipant?, day: Int): Boolean {
        return village.isViewableGraveSay() || myself?.isViewableGraveSay() ?: false
    }

    override fun isSayable(village: Village, myself: VillageParticipant?): Boolean {
        return village.isSayableGraveSay() && myself?.isSayableGraveSay() ?: false
    }
}