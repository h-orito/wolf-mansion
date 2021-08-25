package com.ort.app.domain.service.message.say

import com.ort.app.domain.model.village.Village
import com.ort.app.domain.model.village.participant.VillageParticipant
import org.springframework.stereotype.Service

@Service
class SpectateSayDomainService : SayTypeDomainService {

    override fun isViewable(village: Village, myself: VillageParticipant?, day: Int): Boolean =
        village.isViewableSpectateSay() || day == 0 || myself?.isViewableSpectateSay() ?: false

    override fun isSayable(village: Village, myself: VillageParticipant?): Boolean =
        village.isSayableSpectateSay() && myself?.isSayableSpectateSay() ?: false
}