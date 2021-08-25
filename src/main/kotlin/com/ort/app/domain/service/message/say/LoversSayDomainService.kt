package com.ort.app.domain.service.message.say

import com.ort.app.domain.model.village.Village
import com.ort.app.domain.model.village.participant.VillageParticipant
import org.springframework.stereotype.Service

@Service
class LoversSayDomainService : SayTypeDomainService {

    override fun isViewable(village: Village, myself: VillageParticipant?, day: Int): Boolean =
        village.isViewableLoversSay() || myself?.isViewableLoversSay() ?: false

    override fun isSayable(village: Village, myself: VillageParticipant?): Boolean =
        village.isSayableLoversSay() && myself?.isSayableLoversSay() ?: false
}