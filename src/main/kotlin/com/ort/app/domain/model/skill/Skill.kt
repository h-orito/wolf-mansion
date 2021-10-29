package com.ort.app.domain.model.skill

import com.ort.app.domain.model.ability.AbilityType
import com.ort.app.domain.model.camp.Camp
import com.ort.dbflute.allcommon.CDef

data class Skill(
    val code: String,
    val name: String,
    val shortName: String
) {
    constructor(cdef: CDef.Skill) : this(code = cdef.code(), name = cdef.alias(), shortName = cdef.skill_short_name())

    fun toCdef(): CDef.Skill =
        CDef.Skill.codeOf(code) ?: throw IllegalStateException("unknown skill: $code")

    fun camp(): Camp = Camp(CDef.Camp.codeOf(toCdef().campCode()))

    // TODO 見られるのと話せるのを分けた方が良さそう
    fun isViewableWerewolfSay(): Boolean = toCdef().isAvailableWerewolfSay
    fun isSayableWerewolfSay(): Boolean = toCdef().isAvailableWerewolfSay
    fun isViewableSympathizeSay(): Boolean = toCdef() == CDef.Skill.共鳴者
    fun isSayableSympathizeSay(): Boolean = toCdef() == CDef.Skill.共鳴者
    fun hasAttackAbility(): Boolean = toCdef().isHasAttackAbility
    fun hasDivineAbility(): Boolean = toCdef().isHasDivineAbility
    fun hasDisturbAbility(): Boolean = toCdef().isHasDisturbAbility
    fun hasSkillPsychicAbility(): Boolean = toCdef().isHasSkillPsychicAbility
    fun isViewableWolfCharaName(): Boolean = toCdef().isViewableWolfCharaName
    fun isViewablePsychicMessage(): Boolean = toCdef() == CDef.Skill.霊能者
    fun isViewableGuruMessage(): Boolean = toCdef().isHasSkillPsychicAbility
    fun isViewableAttackMessage(): Boolean = toCdef().isHasAttackAbility
    fun isViewableCoronerMessage(): Boolean = toCdef() == CDef.Skill.検死官
    fun isViewableDivineMessage(): Boolean = toCdef() == CDef.Skill.占い師 || toCdef() == CDef.Skill.占星術師
    fun isViewableWiseMessage(): Boolean = toCdef() == CDef.Skill.賢者
    fun isViewableInvestigateMessage(): Boolean = toCdef() == CDef.Skill.探偵
    fun isViewableLoversMessage(): Boolean = camp().code == CDef.Camp.恋人陣営.code()
    fun isWolfCount(): Boolean = toCdef().isWolfCount
    fun isNoCount(): Boolean = toCdef().isNoCount
    fun isFoxCount(): Boolean = toCdef() == CDef.Skill.妖狐 || toCdef() == CDef.Skill.誑狐
    fun isDivineResultWolf(): Boolean = toCdef().isDivineResultWolf
    fun isPsychicResultWolf(): Boolean = toCdef().isPsychicResultWolf
    fun isDeadByDivine(): Boolean = toCdef() == CDef.Skill.妖狐 || toCdef() == CDef.Skill.誑狐
    fun isCounterDeadByDivine(): Boolean = toCdef() == CDef.Skill.呪狼
    fun isNoDeadByAttack(): Boolean = toCdef().isNoDeadByAttack

    fun getAbility(): AbilityType? = skillToAbility[toCdef()]

    companion object {

        private val skillToAbility = CDef.Skill.listOfHasAttackAbility().map {
            it to AbilityType(CDef.AbilityType.襲撃)
        }.toMap() + CDef.Skill.listOfHasDivineAbility().map {
            it to AbilityType(CDef.AbilityType.占い)
        }.toMap() + mapOf(
            CDef.Skill.狩人 to AbilityType(CDef.AbilityType.護衛),
            CDef.Skill.壁殴り代行 to AbilityType(CDef.AbilityType.壁殴り),
            CDef.Skill.探偵 to AbilityType(CDef.AbilityType.捜査),
            CDef.Skill.罠師 to AbilityType(CDef.AbilityType.罠設置),
            CDef.Skill.爆弾魔 to AbilityType(CDef.AbilityType.爆弾設置),
            CDef.Skill.同棲者 to AbilityType(CDef.AbilityType.同棲),
            CDef.Skill.指揮官 to AbilityType(CDef.AbilityType.指揮),
            CDef.Skill.煽動者 to AbilityType(CDef.AbilityType.煽動),
            CDef.Skill.果実籠 to AbilityType(CDef.AbilityType.フルーツバスケット),
            CDef.Skill.求愛者 to AbilityType(CDef.AbilityType.求愛),
            CDef.Skill.ストーカー to AbilityType(CDef.AbilityType.ストーキング),
            CDef.Skill.絡新婦 to AbilityType(CDef.AbilityType.誘惑),
            CDef.Skill.美人局 to AbilityType(CDef.AbilityType.美人局),
            CDef.Skill.誑狐 to AbilityType(CDef.AbilityType.誑かす),
            CDef.Skill.一匹狼 to AbilityType(CDef.AbilityType.単独襲撃)
        )

        private val shortNameToSkill = Skills.all().filterNotSomeone().list.map {
            it.shortName to it.toCdef()
        }.toMap()

        private val villagerSkills = Skills.all().filterNotSomeone().filterByCamp(CDef.Camp.村人陣営).list
        private val wolfSkills = Skills.all().filterNotSomeone().filterByCamp(CDef.Camp.人狼陣営).list
        private val foxSkills = Skills.all().filterNotSomeone().filterByCamp(CDef.Camp.狐陣営).list
        private val loverSkills = Skills.all().filterNotSomeone().filterByCamp(CDef.Camp.恋人陣営).list
        private val criminalSkills = Skills.all().filterNotSomeone().filterByCamp(CDef.Camp.愉快犯陣営).list
        private val jingaiSkills = wolfSkills + foxSkills + criminalSkills
        private val sayableSkills = Skills.all().filterNotSomeone().list.filter {
            it.isSayableWerewolfSay() || it.isSayableSympathizeSay() || it.toCdef() == CDef.Skill.恋人 || it.toCdef() == CDef.Skill.同棲者
        }
        private val notSayableSkills =
            Skills.all().filterNotSomeone().list.filter { !sayableSkills.map { it.code }.contains(it.code) }
        private val footstepSkills = Skills.all().list.filter {
            it.hasAttackAbility() || it.hasDisturbAbility() || it.hasDivineAbility() || it.toCdef() == CDef.Skill.狩人
        }

        val madmanPriorityList = listOf(
            CDef.Skill.C国狂人,
            CDef.Skill.狂信者,
            CDef.Skill.魔神官,
            CDef.Skill.狂人
        )
        val wolfPriorityList = listOf(
            CDef.Skill.智狼,
            CDef.Skill.呪狼,
            CDef.Skill.絶対人狼,
            CDef.Skill.人狼,
            CDef.Skill.角狼,
            CDef.Skill.飛狼,
            CDef.Skill.金狼,
            CDef.Skill.銀狼,
            CDef.Skill.王狼,
            CDef.Skill.歩狼
        )

        val seerPriorityList = listOf(CDef.Skill.賢者, CDef.Skill.占星術師, CDef.Skill.占い師)

        fun byShortName(shortName: String): Skill? {
            val cdef = CDef.Skill.listAll().firstOrNull {
                it.skill_short_name() == shortName
            } ?: return null
            return Skill(cdef)
        }

        fun convertToSkillPersonNumMap(org: String): Map<CDef.Skill, Int> {
            val skillPersonNumMap: MutableMap<CDef.Skill, Int> = mutableMapOf()
            for (skill in CDef.Skill.values()) {
                skillPersonNumMap[skill] = 0
            }
            for (character in org.split("")) {
                shortNameToSkill[character]?.let { cdef ->
                    val currentNum = skillPersonNumMap[cdef]!!
                    skillPersonNumMap[cdef] = currentNum + 1
                }
            }
            return skillPersonNumMap
        }

        fun getSkillListStr(): String =
            Skills.all().filterNotSomeone().list.joinToString(separator = " / ") { "${it.name}:${it.shortName}" }

        fun getSomeoneCandidateList(someoneSkill: Skill): List<Skill> {
            return when (someoneSkill.toCdef()) {
                CDef.Skill.おまかせ村人陣営 -> villagerSkills
                CDef.Skill.おまかせ人狼陣営 -> wolfSkills
                CDef.Skill.おまかせ妖狐陣営 -> foxSkills
                CDef.Skill.おまかせ恋人陣営 -> loverSkills
                CDef.Skill.おまかせ愉快犯陣営 -> criminalSkills
                CDef.Skill.おまかせ人外 -> jingaiSkills
                CDef.Skill.おまかせ足音職 -> footstepSkills
                CDef.Skill.おまかせ役職窓あり -> sayableSkills
                CDef.Skill.おまかせ役職窓なし -> notSayableSkills
                else -> throw IllegalStateException("not someone")
            }
        }
    }
}

fun CDef.Skill.toModel(): Skill = Skill(this)