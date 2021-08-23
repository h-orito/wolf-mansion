package com.ort.app.domain.model.player

import com.ort.app.domain.model.skill.Skill
import com.ort.app.domain.model.village.Villages

data class SkillRecord(
    val skill: Skill,
    val record: Record
) {
    constructor(
        skill: Skill,
        player: Player,
        villages: Villages
    ) : this(
        skill = skill,
        record = Record(player, villages)
    )
}
