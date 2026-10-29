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

    fun assertParticipateRequestSkill(
        village: Village,
        first: Skill,
        second: Skill,
    ) {
        assertRequestable(village, first)
        assertRequestable(village, second)
    }

    /** 希望できる役職 (役職希望ができない村ではおまかせのみ) */
    fun getRequestableSkillList(village: Village): List<Skill> =
        if (village.canRequestSkill()) village.allRequestableSkillList() else listOf(Skill(CDef.Skill.おまかせ))

    private fun assertRequestable(
        village: Village,
        skill: Skill,
    ) {
        if (getRequestableSkillList(village).none { it.code == skill.code }) {
            throw WolfMansionBusinessException("希望できない役職です")
        }
    }
}
