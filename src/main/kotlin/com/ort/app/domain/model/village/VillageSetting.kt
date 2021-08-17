package com.ort.app.domain.model.village

import com.ort.app.domain.model.camp.Camp
import com.ort.app.domain.model.message.MessageType
import com.ort.app.domain.model.skill.Skill
import java.time.LocalDateTime

data class VillageSetting(
    val dummyCharaId: Int,
    val charachipId: Int,
    val personMin: Int,
    val personMax: Int,
    val startDatetime: LocalDateTime,
    val dayChangeIntervalSeconds: Int,
    val rule: VillageRule,
    val joinPassword: String?,
    val organize: VillageOrganize,
    val sayRestriction: SayRestriction
) {
    data class VillageRule(
        val isOpenVote: Boolean,
        val isPossibleSkillRequest: Boolean,
        val isAvailableSpectate: Boolean,
        val isAvailableSameWolfAttack: Boolean,
        val isOpenSkillInGrave: Boolean,
        val isVisibleGraveSpectateMessage: Boolean,
        val isAvailableSuddenlyDeath: Boolean,
        val isAvailableCommit: Boolean,
        val isAvailableGuardSameTarget: Boolean,
        val isAvailableSecretSay: Boolean,
        val isAvailableAction: Boolean,
        val isRandomOrganization: Boolean
    )

    data class VillageOrganize(
        val fixedOrganization: String,
        val randomOrganization: VillageRandomOrganize
    ) {
        data class VillageRandomOrganize(
            val skillAllocation: List<SkillAllocation>,
            val campAllocation: List<CampAllocation>
        ) {
            data class SkillAllocation(
                val skill: Skill,
                val min: Int,
                val max: Int?,
                val allocation: Int
            )

            data class CampAllocation(
                val camp: Camp,
                val min: Int,
                val max: Int?,
                val allocation: Int
            )
        }
    }

    data class SayRestriction(
        val normalSayRestriction: List<NormalSayRestriction>,
        val skillSayRestriction: List<SkillSayRestriction>
    ) {
        data class NormalSayRestriction(
            val skill: Skill,
            val messageType: MessageType,
            val count: Int,
            val length: Int
        )

        data class SkillSayRestriction(
            val messageType: MessageType,
            val count: Int,
            val length: Int
        )
    }
}