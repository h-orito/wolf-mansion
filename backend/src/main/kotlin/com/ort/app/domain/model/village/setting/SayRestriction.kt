package com.ort.app.domain.model.village.setting

import com.ort.app.domain.model.message.MessageType
import com.ort.app.domain.model.skill.Skill
import com.ort.app.domain.model.village.participant.VillageParticipant
import com.ort.dbflute.allcommon.CDef

data class SayRestriction(
    val normalSayRestriction: List<NormalSayRestriction>,
    val skillSayRestriction: List<SkillSayRestriction>
) {
    fun restrict(myself: VillageParticipant, type: CDef.MessageType): Restriction? {
        return if (type == CDef.MessageType.通常発言) {
            myself.skill ?: return null
            normalSayRestriction.find { it.skill.toCdef() == myself.skill.toCdef() }
        } else {
            skillSayRestriction.find { it.messageType.toCdef() == type }
        }
    }

    interface Restriction {
        val messageType: MessageType
        val count: Int
        val length: Int

        fun remainingCount(
            status: CDef.VillageStatus,
            latestDayMessageCountMap: Map<CDef.MessageType, Int>?
        ): Int? {
            return if (status == CDef.VillageStatus.募集中 || status == CDef.VillageStatus.エピローグ) count
            else {
                val already = latestDayMessageCountMap?.getOrDefault(messageType.toCdef(), 0) ?: 0
                return count - already
            }
        }
    }

    data class NormalSayRestriction(
        val skill: Skill,
        override val messageType: MessageType,
        override val count: Int,
        override val length: Int
    ) : Restriction

    data class SkillSayRestriction(
        override val messageType: MessageType,
        override val count: Int,
        override val length: Int
    ) : Restriction
}