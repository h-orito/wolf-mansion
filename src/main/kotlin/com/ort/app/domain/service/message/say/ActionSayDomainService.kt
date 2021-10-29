package com.ort.app.domain.service.message.say

import com.ort.app.domain.model.village.Village
import com.ort.app.domain.model.village.participant.VillageParticipant
import org.springframework.stereotype.Service

@Service
class ActionSayDomainService : SayTypeDomainService {

    override fun isViewable(village: Village, myself: VillageParticipant?, day: Int): Boolean = true

    override fun isSayable(village: Village, myself: VillageParticipant?): Boolean =
        myself?.isAdmin() ?: false || village.isSayableActionSay()
}