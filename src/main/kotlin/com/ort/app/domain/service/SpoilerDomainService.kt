package com.ort.app.domain.service

import com.ort.app.domain.model.village.Village
import com.ort.app.domain.model.village.participant.VillageParticipant
import org.springframework.stereotype.Service

@Service
class SpoilerDomainService {

    fun isViewableSpoilerContent(village: Village, myself: VillageParticipant?): Boolean {
        return village.isViewableSpoilerContent() ||
                myself?.isViewableSpoilerContent(village.setting.rule.isOpenSkillInGrave) == true
    }
}