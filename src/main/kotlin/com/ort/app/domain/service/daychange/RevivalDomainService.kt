package com.ort.app.domain.service.daychange

import com.ort.app.domain.model.daychange.Daychange
import com.ort.app.domain.model.message.Message
import com.ort.app.domain.model.skill.Skills
import com.ort.app.domain.model.skill.toModel
import com.ort.app.domain.model.village.Village
import com.ort.app.domain.model.village.participant.VillageParticipant
import com.ort.dbflute.allcommon.CDef
import org.springframework.stereotype.Service

@Service
class RevivalDomainService {

    fun revival(orgDaychange: Daychange): Daychange {
        // 絶対人狼
        var daychange = revivalAbsoluteWolf(orgDaychange)
        // 転生者
        daychange = revivalReincarnation(daychange)
        // 申し子
        return revivalHeavenChild(daychange)
    }

    private fun revivalAbsoluteWolf(daychange: Daychange): Daychange {
        var village = daychange.village.copy()
        // 絶対人狼以外の人狼系役職が生存しているか
        val existsNonAbsoluteAliveWolf = village.participants
            .filterAlive()
            .list.any { it.skill!!.hasAttackAbility() && it.skill.toCdef() != CDef.Skill.絶対人狼 }
        if (!existsNonAbsoluteAliveWolf) return daychange

        var messages = daychange.messages.copy()
        village.participants.filterDead().filterBySkill(CDef.Skill.絶対人狼.toModel()).list.forEach {
            village = village.reviveParticipant(it.id)
            messages = messages.add(createRevivalMessage(village, it))
        }
        return daychange.copy(village = village, messages = messages)
    }

    private fun revivalReincarnation(daychange: Daychange): Daychange {
        var village = daychange.village.copy()
        var messages = daychange.messages.copy()
        village.participants.filterDead().filterBySkill(CDef.Skill.転生者.toModel()).list.forEach {
            village = village.reviveParticipant(it.id)
            // ランダム役職で転生
            val skill = Skills.revivables().list.shuffled().first()
            village = village.assignParticipantSkill(it.id, skill)
            messages = messages.add(createRevivalMessage(village, it))
        }
        return daychange.copy(village = village, messages = messages)
    }

    private fun revivalHeavenChild(daychange: Daychange): Daychange {
        var village = daychange.village.copy()
        var messages = daychange.messages.copy()
        village.participants.filterDead().filterBySkill(CDef.Skill.申し子.toModel()).list.forEach {
            village = village.reviveParticipant(it.id)
            // 村陣営のランダム役職で転生
            val skill = Skills.revivables().filterByCamp(CDef.Camp.村人陣営).list.shuffled().first()
            village = village.assignParticipantSkill(it.id, skill)
            messages = messages.add(createRevivalMessage(village, it))
        }
        return daychange.copy(village = village, messages = messages)
    }

    private fun createRevivalMessage(village: Village, participant: VillageParticipant): Message {
        return Message.ofSystemMessage(
            day = village.latestDay(),
            message = "不思議なことに、${participant.name()}が生き返った。"
        )
    }
}
