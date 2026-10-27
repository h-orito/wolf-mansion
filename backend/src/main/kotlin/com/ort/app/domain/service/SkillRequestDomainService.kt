package com.ort.app.domain.service

import com.ort.app.domain.model.situation.participant.ParticipantSkillRequestSituation
import com.ort.app.domain.model.skill.RequestSkill
import com.ort.app.domain.model.skill.Skill
import com.ort.app.domain.model.village.Village
import com.ort.app.domain.model.village.participant.VillageParticipant
import com.ort.app.fw.exception.WolfMansionBusinessException
import com.ort.dbflute.allcommon.CDef
import org.springframework.stereotype.Service

@Service
class SkillRequestDomainService {
    fun convertToSituation(
        village: Village,
        myself: VillageParticipant?,
        requestSkill: RequestSkill?,
    ): ParticipantSkillRequestSituation =
        ParticipantSkillRequestSituation(
            isAvailableSkillRequest = isAvailableSkillRequest(village, myself),
            selectableSkillList = village.allRequestableSkillList(),
            skillRequest = requestSkill,
        )

    private fun isAvailableSkillRequest(
        village: Village,
        myself: VillageParticipant?,
    ): Boolean = village.canRequestSkill() && myself?.isAvailableRequestSkill() ?: false

    fun assertChangeRequestSkill(
        village: Village,
        myself: VillageParticipant,
        first: Skill,
        second: Skill,
    ) {
        if (!isAvailableSkillRequest(village, myself)) {
            throw WolfMansionBusinessException("役職希望変更できません")
        }
        assertRequestable(village, first)
        assertRequestable(village, second)
    }

    /**
     * 入村時の役職希望
     * 役職希望ができない村では、送られた希望によらずおまかせにする
     */
    fun fixParticipateRequestSkill(
        village: Village,
        skill: Skill,
    ): Skill {
        if (!village.canRequestSkill()) return Skill(CDef.Skill.おまかせ)
        assertRequestable(village, skill)
        return skill
    }

    private fun assertRequestable(
        village: Village,
        skill: Skill,
    ) {
        if (village.allRequestableSkillList().none { it.code == skill.code }) {
            throw WolfMansionBusinessException("希望できない役職です")
        }
    }
}
