package com.ort.app.domain.service.message.system

import com.ort.app.domain.model.village.Village
import com.ort.app.domain.model.village.participant.VillageParticipant
import com.ort.app.domain.service.message.say.MessageTypeDomainService
import org.springframework.stereotype.Service

@Service
class DivineMessageDomainService : MessageTypeDomainService {

    override fun isViewable(
        village: Village,
        myself: VillageParticipant?,
        day: Int
    ): Boolean = village.isViewableDivineMessage() || myself?.isViewableDivineMessage() ?: false
}