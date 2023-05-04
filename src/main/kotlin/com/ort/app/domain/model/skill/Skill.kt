package com.ort.app.domain.model.skill

import com.ort.app.domain.model.ability.AbilityType
import com.ort.app.domain.model.camp.Camp
import com.ort.dbflute.allcommon.CDef

data class Skill(
    val code: String,
    val name: String,
    val shortName: String,
    val histories: SkillHistories = SkillHistories(list = emptyList())
) {
    constructor(
        cdef: CDef.Skill
    ) : this(
        code = cdef.code(),
        name = cdef.alias(),
        shortName = cdef.skill_short_name()
    )

    fun toCdef(): CDef.Skill = CDef.Skill.codeOf(code) ?: throw IllegalStateException("unknown skill: $code")

    fun camp(): Camp = Camp(CDef.Camp.codeOf(toCdef().campCode()))

    // その日の変化後が取得される
    fun skillWhen(day: Int): Skill {
        val maxDay = histories.list.filter { it.day <= day }.maxOfOrNull { it.day } ?: return this
        return histories.list.lastOrNull { it.day == maxDay }!!.skill
    }

    fun isViewableWerewolfSay(): Boolean = toCdef().isViewableWerewolfSay
    fun isSayableWerewolfSay(): Boolean = toCdef().isAvailableWerewolfSay
    fun isViewableSympathizeSay(): Boolean = toCdef() == CDef.Skill.共鳴者
    fun isSayableSympathizeSay(): Boolean = toCdef() == CDef.Skill.共鳴者
    fun isViewableTelepathy(): Boolean = camp().isFoxs()
    fun isSayableTelepathy(): Boolean = toCdef() == CDef.Skill.仙狐
    fun isViewableLoversSay(): Boolean = toCdef() == CDef.Skill.耳年増
    fun hasAttackAbility(): Boolean = toCdef().isHasAttackAbility
    fun hasDivineAbility(): Boolean = toCdef().isHasDivineAbility
    fun hasDisturbAbility(): Boolean = toCdef().isHasDisturbAbility
    fun hasSkillPsychicAbility(): Boolean = toCdef().isHasSkillPsychicAbility
    fun hasInvestigateAbility(): Boolean =
        toCdef() == CDef.Skill.探偵 || toCdef() == CDef.Skill.監視者 || toCdef() == CDef.Skill.闇探偵

    fun isViewableWolfCharaName(): Boolean = toCdef().isViewableWolfCharaName
    fun isViewablePsychicMessage(): Boolean = toCdef() == CDef.Skill.霊能者
    fun isViewableGuruMessage(): Boolean = toCdef().isHasSkillPsychicAbility
    fun isViewableAttackMessage(): Boolean = toCdef().isHasAttackAbility
    fun isViewableCoronerMessage(): Boolean = toCdef() == CDef.Skill.検死官
    fun isViewableDivineMessage(): Boolean =
        listOf(
            CDef.Skill.占い師,
            CDef.Skill.占星術師,
            CDef.Skill.花占い師,
            CDef.Skill.感覚者,
            CDef.Skill.管狐
        ).contains(toCdef())

    fun isViewableWiseMessage(): Boolean = toCdef() == CDef.Skill.賢者
    fun isViewableInvestigateMessage(): Boolean =
        toCdef() == CDef.Skill.探偵 || toCdef() == CDef.Skill.監視者 || toCdef() == CDef.Skill.闇探偵

    fun isViewableLoversMessage(): Boolean = camp().code == CDef.Camp.恋人陣営.code()
    fun isViewableFoxMessage(): Boolean = toCdef() == CDef.Skill.誑狐
    fun isWolfCount(): Boolean = toCdef().isWolfCount
    fun isNoCount(): Boolean = toCdef().isNoCount
    fun isFoxCount(): Boolean = listOf(
        CDef.Skill.妖狐,
        CDef.Skill.誑狐,
        CDef.Skill.ごん,
        CDef.Skill.仙狐,
        CDef.Skill.管狐,
        CDef.Skill.稲荷,
        CDef.Skill.騙狐,
        CDef.Skill.夜狐
    ).contains(toCdef())

    fun isDivineResultWolf(): Boolean = toCdef().isDivineResultWolf
    fun isPsychicResultWolf(): Boolean = toCdef().isPsychicResultWolf
    fun isDeadByDivine(): Boolean = isFoxCount()
    fun isCounterDeadByDivine(): Boolean = toCdef() == CDef.Skill.呪狼
    fun isCounterDeadByInvestigate(): Boolean = toCdef() == CDef.Skill.臭狼
    fun isNoDeadByAttack(): Boolean = toCdef().isNoDeadByAttack
    fun isNoSound(): Boolean = toCdef() == CDef.Skill.防音者

    fun getAbility(): AbilityType? = skillToAbility[toCdef()]

    fun assignSkill(skill: Skill, day: Int): Skill = copy(
        code = skill.code,
        name = skill.name,
        shortName = skill.shortName,
        histories = histories.copy(
            list = histories.list + SkillHistory(skill = skill, day = day)
        )
    )

    fun isShogiWolf(): Boolean {
        return listOf(
            CDef.Skill.角狼,
            CDef.Skill.飛狼,
            CDef.Skill.金狼,
            CDef.Skill.銀狼,
            CDef.Skill.王狼,
            CDef.Skill.歩狼
        ).contains(this.toCdef())
    }

    companion object {

        private val skillToAbility = CDef.Skill.listOfHasAttackAbility().associateWith {
            AbilityType(CDef.AbilityType.襲撃希望)
        } + CDef.Skill.listOfHasDivineAbility().associateWith {
            AbilityType(CDef.AbilityType.占い)
        } + mapOf(
            CDef.Skill.狩人 to AbilityType(CDef.AbilityType.護衛),
            CDef.Skill.風来狩人 to AbilityType(CDef.AbilityType.風来護衛),
            CDef.Skill.壁殴り代行 to AbilityType(CDef.AbilityType.壁殴り),
            CDef.Skill.探偵 to AbilityType(CDef.AbilityType.捜査),
            CDef.Skill.監視者 to AbilityType(CDef.AbilityType.捜査),
            CDef.Skill.罠師 to AbilityType(CDef.AbilityType.罠設置),
            CDef.Skill.牧師 to AbilityType(CDef.AbilityType.説得),
            CDef.Skill.全知者 to AbilityType(CDef.AbilityType.全知),
            CDef.Skill.爆弾魔 to AbilityType(CDef.AbilityType.爆弾設置),
            CDef.Skill.同棲者 to AbilityType(CDef.AbilityType.同棲),
            CDef.Skill.指揮官 to AbilityType(CDef.AbilityType.指揮),
            CDef.Skill.煽動者 to AbilityType(CDef.AbilityType.煽動),
            CDef.Skill.騙狐 to AbilityType(CDef.AbilityType.煽動),
            CDef.Skill.破局者 to AbilityType(CDef.AbilityType.破局),
            CDef.Skill.教唆者 to AbilityType(CDef.AbilityType.教唆),
            CDef.Skill.濡衣者 to AbilityType(CDef.AbilityType.濡衣),
            CDef.Skill.果実籠 to AbilityType(CDef.AbilityType.フルーツバスケット),
            CDef.Skill.求愛者 to AbilityType(CDef.AbilityType.求愛),
            CDef.Skill.ストーカー to AbilityType(CDef.AbilityType.ストーキング),
            CDef.Skill.絡新婦 to AbilityType(CDef.AbilityType.誘惑),
            CDef.Skill.美人局 to AbilityType(CDef.AbilityType.美人局),
            CDef.Skill.誑狐 to AbilityType(CDef.AbilityType.誑かす),
            CDef.Skill.一匹狼 to AbilityType(CDef.AbilityType.単独襲撃),
            CDef.Skill.虹職人 to AbilityType(CDef.AbilityType.虹塗り),
            CDef.Skill.拡声者 to AbilityType(CDef.AbilityType.拡声),
            CDef.Skill.濁点者 to AbilityType(CDef.AbilityType.叫び),
            CDef.Skill.トラック to AbilityType(CDef.AbilityType.強制転生),
            CDef.Skill.蘇生者 to AbilityType(CDef.AbilityType.蘇生),
            CDef.Skill.死霊術師 to AbilityType(CDef.AbilityType.死霊蘇生),
            CDef.Skill.陰陽師 to AbilityType(CDef.AbilityType.降霊),
            CDef.Skill.ババ to AbilityType(CDef.AbilityType.ババを渡す),
            CDef.Skill.当選者 to AbilityType(CDef.AbilityType.当選),
            CDef.Skill.不止者 to AbilityType(CDef.AbilityType.指差死),
            CDef.Skill.保険屋 to AbilityType(CDef.AbilityType.保険),
            CDef.Skill.翻訳者 to AbilityType(CDef.AbilityType.翻訳),
            CDef.Skill.マタギ to AbilityType(CDef.AbilityType.狩猟),
            CDef.Skill.黒箱者 to AbilityType(CDef.AbilityType.隠蔽),
            CDef.Skill.泥棒猫 to AbilityType(CDef.AbilityType.恋泥棒),
            CDef.Skill.バールのようなもの to AbilityType(CDef.AbilityType.殴打),
            CDef.Skill.闇探偵 to AbilityType(CDef.AbilityType.捜査),
            CDef.Skill.冷凍者 to AbilityType(CDef.AbilityType.戦闘力発揮),
            CDef.Skill.道化師 to AbilityType(CDef.AbilityType.道化),
            CDef.Skill.伝説の殺し屋 to AbilityType(CDef.AbilityType.殺し屋化),
            CDef.Skill.革命者 to AbilityType(CDef.AbilityType.革命),
            CDef.Skill.呪縛者 to AbilityType(CDef.AbilityType.呪縛),
            CDef.Skill.反呪者 to AbilityType(CDef.AbilityType.反呪),
        )

        private val shortNameToSkill = Skills.all().filterNotSomeone().list.associate {
            it.shortName to it.toCdef()
        }

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
            it.hasAttackAbility() || it.hasDisturbAbility() || it.hasDivineAbility() || listOf(
                CDef.Skill.狩人,
                CDef.Skill.風来狩人,
                CDef.Skill.罠師,
                CDef.Skill.蘇生者,
                CDef.Skill.保険屋,
                CDef.Skill.マタギ,
                CDef.Skill.牧師,
                CDef.Skill.死霊術師,
                CDef.Skill.破局者,
                CDef.Skill.教唆者,
                CDef.Skill.求愛者,
                CDef.Skill.ストーカー,
                CDef.Skill.絡新婦,
                CDef.Skill.美人局,
                CDef.Skill.誑狐,
                CDef.Skill.爆弾魔,
                CDef.Skill.一匹狼,
                CDef.Skill.虹職人,
                CDef.Skill.拡声者,
                CDef.Skill.濁点者,
                CDef.Skill.道化師,
                CDef.Skill.伝説の殺し屋,
                CDef.Skill.翻訳者,
                CDef.Skill.トラック,
                CDef.Skill.ババ,
                CDef.Skill.泥棒猫,
                CDef.Skill.壁殴り代行,
                CDef.Skill.バールのようなもの
            ).contains(it.toCdef())
        }

        // タグ用
        val hasCommandAbilitySkills = listOf(CDef.Skill.指揮官, CDef.Skill.煽動者, CDef.Skill.騙狐, CDef.Skill.不止者)
        val hasGuardAbilitySkills = listOf(CDef.Skill.狩人, CDef.Skill.風来狩人, CDef.Skill.壁殴り代行)
        val hasChangeCampAbilitySkills =
            listOf(
                CDef.Skill.牧師,
                CDef.Skill.教唆者,
                CDef.Skill.求愛者,
                CDef.Skill.ストーカー,
                CDef.Skill.絡新婦,
                CDef.Skill.美人局,
                CDef.Skill.誑狐,
                CDef.Skill.夜狐,
                CDef.Skill.泥棒猫,
                CDef.Skill.破局者
            )
        val hasChangeSkillAbilitySkills =
            listOf(
                CDef.Skill.申し子,
                CDef.Skill.転生者,
                CDef.Skill.トラック,
                CDef.Skill.ババ,
                CDef.Skill.当選者,
                CDef.Skill.死霊術師
            )
        val hasRevivalOtherAbilitySkills = listOf(CDef.Skill.蘇生者, CDef.Skill.死霊術師)
        val hasRevivalMyselfAbilitySkills = listOf(CDef.Skill.申し子, CDef.Skill.転生者, CDef.Skill.餡麺麭者, CDef.Skill.絶対人狼)
        val hasTrapAbilitySkills = listOf(CDef.Skill.罠師, CDef.Skill.爆弾魔, CDef.Skill.画鋲, CDef.Skill.箪笥)
        val hasChangeRoomAbilitySkills = listOf(CDef.Skill.果実籠)
        val hasChangeMessageAbilitySkills =
            listOf(CDef.Skill.虹職人, CDef.Skill.拡声者, CDef.Skill.濁点者, CDef.Skill.道化師, CDef.Skill.伝説の殺し屋, CDef.Skill.翻訳者)
        val hasVoteAbilitySkills =
            listOf(
                CDef.Skill.強運者,
                CDef.Skill.執行人,
                CDef.Skill.弁護士,
                CDef.Skill.市長,
                CDef.Skill.組長,
                CDef.Skill.黒箱者,
                CDef.Skill.冷凍者,
                CDef.Skill.王族,
                CDef.Skill.革命者
            )
        val hasVotedAbilitySkills = listOf(CDef.Skill.バールのようなもの, CDef.Skill.怨恨者)
        val isSuicideSkills =
            listOf(
                CDef.Skill.餡麺麭者,
                CDef.Skill.壁殴り代行,
                CDef.Skill.恋人,
                CDef.Skill.同棲者,
                CDef.Skill.ストーカー,
                CDef.Skill.背徳者,
                CDef.Skill.陰陽師
            )
        val hasLoneAttackAbilitySkills = listOf(CDef.Skill.マタギ, CDef.Skill.バールのようなもの, CDef.Skill.一匹狼)
        val hasAutoFootstepAbilitySkills =
            listOf(CDef.Skill.妄想癖, CDef.Skill.夢遊病者, CDef.Skill.冤罪者, CDef.Skill.濡衣者, CDef.Skill.リア充)

        val madmanPriorityList = listOf(
            CDef.Skill.C国狂人,
            CDef.Skill.聴狂人,
            CDef.Skill.狂信者,
            CDef.Skill.魔神官,
            CDef.Skill.狂人
        )
        val wolfPriorityList = listOf(
            CDef.Skill.智狼,
            CDef.Skill.呪狼,
            CDef.Skill.絶対人狼,
            CDef.Skill.堅狼,
            CDef.Skill.臭狼,
            CDef.Skill.人狼,
            CDef.Skill.黙狼,
            CDef.Skill.静狼,
            CDef.Skill.角狼,
            CDef.Skill.飛狼,
            CDef.Skill.金狼,
            CDef.Skill.銀狼,
            CDef.Skill.王狼,
            CDef.Skill.歩狼
        )

        val seerPriorityList = listOf(CDef.Skill.賢者, CDef.Skill.占星術師, CDef.Skill.占い師, CDef.Skill.花占い師, CDef.Skill.感覚者)

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