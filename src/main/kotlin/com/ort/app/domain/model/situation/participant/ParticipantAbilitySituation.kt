package com.ort.app.domain.model.situation.participant

import com.ort.app.domain.model.ability.AbilityType
import com.ort.app.domain.model.village.participant.VillageParticipant

data class ParticipantAbilitySituation(
    val canUseAbility: Boolean = false,
    val type: AbilityType? = null,
    val targetList: List<VillageParticipant> = emptyList(),
    val targetFootstepList: List<String> = emptyList(),
    // 襲撃担当者
    val attacker: VillageParticipant? = null,
    // 能力行使のセット先
    val target: VillageParticipant? = null,
    // 探偵が選択している足音
    val targetFootstep: String? = null,
    // 選択している対象の文言
    val targetingMessage: String? = null,
    // 足音セット職の足音
    val footstep: String? = null,
    val isAvailableNoTarget: Boolean = false,
    val attackerList: List<VillageParticipant> = emptyList(),
    val skillHistoryList: List<String> = emptyList(),
    val wolfList: List<VillageParticipant> = emptyList(),
    val cMadmanList: List<VillageParticipant> = emptyList(),
    val foxList: List<VillageParticipant> = emptyList(),
    val loversList: List<VillageParticipant> = emptyList(),
    val targetPrefix: String? = null,
    val targetSuffix: String? = null,
    val isTargetingAndFootstep: Boolean = false
)
