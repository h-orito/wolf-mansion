package com.ort.app.domain.model.skill

enum class SkillTag {
    共鳴発言,
    囁き発言,
    恋人発言,
    念話,
    人狼系,
    妖狐系,
    占い,
    霊視,
    護衛,
    徘徊,
    調査,
    指差し,
    人狼把握,
    検死,
    人間カウント,
    人狼カウント,
    カウントなし,
    占い結果人狼,
    霊能結果人狼,
    呪殺,
    逆呪殺,
    襲撃耐性,
    足音除去,
    陣営変化,
    役職変化,
    他者蘇生,
    自己蘇生,
    踏むと死亡,
    部屋交換,
    語尾変化,
    投票,
    被投票,
    後追い,
    単独襲撃,
    足音発生,
    回数制限,
    毎日使用可能,
    対象指定_足音発生
    ;

    companion object {
        private fun safeValueOf(name: String): SkillTag? {
            return try {
                valueOf(name)
            } catch (e: Exception) {
                null
            }
        }

        fun of(tags: List<String>): List<SkillTag> {
            return tags.mapNotNull { safeValueOf(it) }
        }

        private val map = mapOf(
            共鳴発言 to listOfSkill { it.isViewableSympathizeSay() },
            囁き発言 to listOfSkill { it.isViewableWerewolfSay() },
            恋人発言 to listOfSkill { it.camp().isLovers() },
            念話 to listOfSkill { it.isSayableTelepathy() },
            人狼系 to listOfSkill { it.hasAttackAbility() },
            妖狐系 to listOfSkill { it.isFoxCount() },
            占い to listOfSkill { it.hasDivineAbility() },
            護衛 to Skill.hasGuardAbilitySkills.map { it.toModel() },
            徘徊 to listOfSkill { it.hasDisturbAbility() },
            調査 to listOfSkill { it.hasInvestigateAbility() },
            指差し to Skill.hasCommandAbilitySkills.map { it.toModel() },
            人狼把握 to listOfSkill { it.isViewableWolfCharaName() },
            霊視 to listOfSkill { it.hasSkillPsychicAbility() || it.isViewablePsychicMessage() },
            検死 to listOfSkill { it.isViewableCoronerMessage() },
            単独襲撃 to Skill.hasLoneAttackAbilitySkills.map { it.toModel() },
            人間カウント to listOfSkill { !it.isWolfCount() && !it.isNoCount() },
            人狼カウント to listOfSkill { it.isWolfCount() },
            カウントなし to listOfSkill { it.isNoCount() },
            占い結果人狼 to listOfSkill { it.isDivineResultWolf() },
            霊能結果人狼 to listOfSkill { it.isPsychicResultWolf() },
            呪殺 to listOfSkill { it.isDeadByDivine() },
            逆呪殺 to listOfSkill { it.isCounterDeadByDivine() },
            襲撃耐性 to listOfSkill { it.isNoDeadByAttack() },
            足音発生 to Skill.hasAutoFootstepAbilitySkills.map { it.toModel() },
            足音除去 to listOfSkill { it.isNoSound() },
            陣営変化 to Skill.hasChangeCampAbilitySkills.map { it.toModel() },
            役職変化 to Skill.hasChangeSkillAbilitySkills.map { it.toModel() },
            他者蘇生 to Skill.hasRevivalOtherAbilitySkills.map { it.toModel() },
            自己蘇生 to Skill.hasRevivalMyselfAbilitySkills.map { it.toModel() },
            踏むと死亡 to Skill.hasTrapAbilitySkills.map { it.toModel() },
            部屋交換 to Skill.hasChangeRoomAbilitySkills.map { it.toModel() },
            語尾変化 to Skill.hasChangeMessageAbilitySkills.map { it.toModel() },
            投票 to Skill.hasVoteAbilitySkills.map { it.toModel() },
            被投票 to Skill.hasVotedAbilitySkills.map { it.toModel() },
            後追い to Skill.isSuicideSkills.map { it.toModel() },
            回数制限 to Skill.limitedCountSkills.map { it.toModel() },
            毎日使用可能 to Skill.availableEverydaySkills.map { it.toModel() },
            対象指定_足音発生 to Skill.targetingAndFootstepSkills.map { it.toModel() }
        )

        private fun listOfSkill(predicate: (Skill) -> Boolean): List<Skill> =
            Skills.all().filter(predicate).list
    }

    fun getSkillList(): List<Skill> = map.getOrDefault(this, emptyList())
}