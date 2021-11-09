package com.ort.app.domain.service.message.say

import com.ort.app.domain.model.village.Village
import com.ort.app.domain.model.village.participant.VillageParticipant
import org.springframework.stereotype.Service

@Service
class TelepathyDomainService : SayTypeDomainService {

    override fun isViewable(village: Village, myself: VillageParticipant?, day: Int): Boolean =
        village.isViewableTelepathy() || myself?.isViewableTelepathy() ?: false

    override fun isSayable(village: Village, myself: VillageParticipant?): Boolean =
        village.isSayableTelepathy() && myself?.isSayableTelepathy() ?: false
}