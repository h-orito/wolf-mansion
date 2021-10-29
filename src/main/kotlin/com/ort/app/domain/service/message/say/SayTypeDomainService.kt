package com.ort.app.domain.service.message.say

import com.ort.app.domain.model.village.Village
import com.ort.app.domain.model.village.participant.VillageParticipant

interface SayTypeDomainService : MessageTypeDomainService {

    fun isSayable(village: Village, myself: VillageParticipant?): Boolean
}