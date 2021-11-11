package com.ort.app.domain.service.message.say

import com.ort.app.domain.model.village.Village
import com.ort.app.domain.model.village.participant.VillageParticipant
import org.springframework.stereotype.Service

@Service
class SecretSayDomainService : SayTypeDomainService {

    override fun isViewable(village: Village, myself: VillageParticipant?, day: Int): Boolean =
        village.isViewableSecretSay() || myself?.isViewableSecretSay() ?: false

    override fun isSayable(village: Village, myself: VillageParticipant?): Boolean {
        return if (myself?.isAdmin() == true) true
        else village.isSayableSecretSay() && myself?.isSayableSecretSay() ?: false
    }
}