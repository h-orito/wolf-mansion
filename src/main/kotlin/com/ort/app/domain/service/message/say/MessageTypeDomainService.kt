package com.ort.app.domain.service.message.say

import com.ort.app.domain.model.village.Village
import com.ort.app.domain.model.village.participant.VillageParticipant

interface MessageTypeDomainService {

    fun isViewable(village: Village, myself: VillageParticipant?, day: Int): Boolean
}