package com.ort.app.domain.service

import com.ort.app.domain.model.situation.participant.ParticipantSkillRequestSituation
import com.ort.app.domain.model.skill.RequestSkill
import com.ort.app.domain.model.village.Village
import com.ort.app.domain.model.village.participant.VillageParticipant
import com.ort.app.fw.exception.WolfMansionBusinessException
import org.springframework.stereotype.Service

@Service
class SkillRequestDomainService {

    fun convertToSituation(
        village: Village,
        myself: VillageParticipant?,
        requestSkill: RequestSkill?
    ): ParticipantSkillRequestSituation = ParticipantSkillRequestSituation(
        isAvailableSkillRequest = isAvailableSkillRequest(village, myself),
        selectableSkillList = village.allRequestableSkillList(),
        skillRequest = requestSkill
    )

    private fun isAvailableSkillRequest(village: Village, myself: VillageParticipant?): Boolean {
        return village.canRequestSkill() && myself?.isAvailableRequestSkill() ?: false
    }

    fun assertChangeRequestSkill(village: Village, myself: VillageParticipant) {
        if (!isAvailableSkillRequest(village, myself)) {
            throw WolfMansionBusinessException("役職希望変更できません")
        }
    }
}