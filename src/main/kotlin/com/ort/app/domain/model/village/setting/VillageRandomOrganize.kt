package com.ort.app.domain.model.village.setting

import com.ort.app.domain.model.camp.Camp
import com.ort.app.domain.model.skill.Skill
import com.ort.app.domain.model.skill.Skills
import com.ort.dbflute.allcommon.CDef
import org.slf4j.LoggerFactory
import java.util.*

data class VillageRandomOrganize(
    val skillAllocation: List<SkillAllocation>,
    val campAllocation: List<CampAllocation>,
    val wolfAllocation: WolfAllocation?
) {
    private val logger = LoggerFactory.getLogger(this::class.java)

    fun mapToSkillCount(participantsCount: Int): Map<CDef.Skill, Int> {
        return createSkillPersonCountMap(participantsCount, 0)
    }

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

    data class WolfAllocation(
        val min: Int,
        val max: Int?
    )

    private fun createSkillPersonCountMap(
        participantsCount: Int,
        retryCount: Int
    ): Map<CDef.Skill, Int> {
        check(retryCount < 50) { "50回試行しましたが割り振れませんでした。" }
        // TODO: 転生でのみ発生する役職は最少最多を0扱いにする
        val countMap = Skills.all().filterNotSomeone().list.map { it.toCdef() to 0 }.toMap().toMutableMap()

        // 最少人数が決まっている役職を先に割り当てる
        skillAllocation.filter { it.min > 0 }.forEach {
            countMap[it.skill.toCdef()] = it.min
        }

        // 最少人数が決まっている陣営を先に割り当てる
        campAllocation.filter { it.min > 0 }.forEach { c ->
            val campSkillSum = countMap.entries.filter { it.key.campCode() == c.camp.code }.sumOf { it.value }
            repeat(c.min - campSkillSum) {
                // 割り振っても良い役職
                val skillList = skillAllocation.filter { s ->
                    isAllocatableSkill(countMap, s, c.camp)
                }
                val skill: CDef.Skill = gachaSkill(skillList)
                addSkillPerson(countMap, skill)
            }
        }

        // 人数を満たすまで継続
        while (countMap.values.sum() < participantsCount) {
            val skill = gachaCampAndSkill(campAllocation, skillAllocation, countMap)
            addSkillPerson(countMap, skill)
        }
        if (shouldRetry(countMap, participantsCount)) {
            // やり直し
            countMap.entries.stream().forEach { entry: Map.Entry<CDef.Skill, Int> ->
                logger.info("${entry.key.alias()}: ${entry.value}")
            }
            return createSkillPersonCountMap(participantsCount, retryCount + 1)
        }
        return countMap
    }

    private fun gachaCampAndSkill(
        campAllocationList: List<CampAllocation>,
        skillAllocationList: List<SkillAllocation>,
        countMap: Map<CDef.Skill, Int>
    ): CDef.Skill {
        // 割り振っても良い陣営
        val campList = campAllocationList.filter { c ->
            isAllocatableCamp(skillAllocationList, countMap, c)
        }
        // 陣営を抽選
        val camp = gachaCamp(campList)
        // 割り振っても良い役職
        val skillList = skillAllocationList.filter { s ->
            isAllocatableSkill(countMap, s, Camp(camp))
        }
        // 役職を抽選
        return gachaSkill(skillList)
    }

    private fun isAllocatableCamp(
        skillAllocationList: List<SkillAllocation>,
        countMap: Map<CDef.Skill, Int>,
        camp: CampAllocation
    ): Boolean {
        val campSkillAllocationList = skillAllocationList.filter { s ->
            s.skill.camp().code == camp.camp.code
        }

        // 配分が0
        if (camp.allocation <= 0) return false

        // 配分が0より大きい役職がいない
        if (campSkillAllocationList.none { it.allocation > 0 }) return false

        // 全役職が既に最多人数（maxが設定されていない、割り振られていない役職が1つでもあれば問題なし）
        if (campSkillAllocationList.all { s ->
                val max = s.max
                val current = countMap[s.skill.toCdef()]
                max != null && current != null && max <= current
            }
        ) return false

        // 陣営の役職の合計数が既に陣営配分のmax人数
        val campSkillSum = countMap.entries.filter { it.key.campCode() == camp.camp.code }.sumOf { it.value }
        return camp.max == null || campSkillSum < camp.max
    }

    private fun isAllocatableSkill(
        countMap: Map<CDef.Skill, Int>,
        skillAllocation: SkillAllocation,
        camp: Camp
    ): Boolean {
        // 配分が0
        if (skillAllocation.allocation <= 0) {
            return false
        }
        // 既に最多人数
        val max = skillAllocation.max
        val current = countMap[skillAllocation.skill.toCdef()]
        return if (max != null && current != null && max <= current) false
        // 陣営が指定のもの
        else skillAllocation.skill.camp().code == camp.code
    }

    private fun addSkillPerson(
        countMap: MutableMap<CDef.Skill, Int>,
        skill: CDef.Skill
    ) {
        if (countMap.containsKey(skill)) {
            countMap[skill] = countMap[skill]!!.plus(1)
        } else {
            countMap[skill] = 1
        }
    }

    private fun gachaSkill(skillAllocationList: List<SkillAllocation>): CDef.Skill {
        var sum = skillAllocationList.sumOf { it.allocation }
        for (skill in skillAllocationList) {
            val rand = Random()
            val random = rand.nextInt(sum)
            val allocation = skill.allocation
            if (random < allocation) {
                return skill.skill.toCdef()
            }
            sum -= allocation
        }
        throw IllegalStateException("should not reach")
    }

    private fun gachaCamp(campAllocationList: List<CampAllocation>): CDef.Camp {
        var sum = campAllocationList.sumOf { it.allocation }
        for (camp in campAllocationList) {
            val rand = Random()
            val random = rand.nextInt(sum)
            val allocation = camp.allocation
            if (random < allocation) {
                return camp.camp.toCdef()
            }
            sum -= allocation
        }
        throw IllegalStateException("should not reach")
    }

    private fun shouldRetry(countMap: Map<CDef.Skill, Int>, participantCount: Int): Boolean {
        // 人数オーバー
        val sum = countMap.values.sum()
        if (participantCount < sum) {
            logger.info("人数オーバーしているのでやり直し")
            return true
        }

        // 人狼が規定数を満たしていないのでやりなおし
        val wolfCount = countMap.entries.filter { it.key.isWolfCount }.sumOf { it.value }
        if (wolfCount < wolfAllocation!!.min) {
            logger.info("人狼カウントが足りていないのでやり直し")
            return true
        }
        if (wolfAllocation.max != null && wolfAllocation.max < wolfCount) {
            logger.info("人狼カウントが多すぎるのでやり直し")
            return true
        }

        // 襲撃可能な狼がいないのでやりなおし
        val attackableCount = countMap.entries.filter { it.key.isHasAttackAbility }.sumOf { it.value }
        if (attackableCount <= 0) {
            logger.info("襲撃可能な狼がいないのでやり直し")
            return true
        }

        // 恋人と同棲者は二人ペアでなければいけないので奇数ならやりなおし
        val loversCount = countMap[CDef.Skill.恋人]
        if (loversCount != null && loversCount % 2 == 1) {
            logger.info("恋人が奇数だったのでやり直し")
            return true
        }
        val cohabitersCount = countMap[CDef.Skill.同棲者]
        if (cohabitersCount != null && cohabitersCount % 2 == 1) {
            logger.info("同棲者が奇数だったのでやり直し")
            return true
        }

        // 勝利できない役職がいたらやり直し（妖狐系なしの背徳者）
        val foxCount = Skills.all().list.filter { it.isFoxCount() }.sumOf {
            countMap.getOrDefault(it.toCdef(), 0)
        }
        val immoralCount = countMap.getOrDefault(CDef.Skill.背徳者, 0)
        val onmyojiCount = countMap.getOrDefault(CDef.Skill.陰陽師, 0)
        if (foxCount == 0 && 0 < immoralCount + onmyojiCount) {
            logger.info("妖狐系がいないのに背徳者陰陽師がいるのでやり直し")
            return true
        }
        // 勝利できない役職がいたらやり直し（パン屋なしの餡麺麭者）
        val anpanmanCount = countMap.getOrDefault(CDef.Skill.餡麺麭者, 0)
        val bakeryCount = countMap.getOrDefault(CDef.Skill.パン屋, 0) + countMap.getOrDefault(CDef.Skill.闇パン屋, 0)
        if (bakeryCount == 0 && 0 < anpanmanCount) {
            logger.info("パン屋なしで餡麺麭者がいるのでやり直し")
            return true
        }

        // PPチェック
        val wolfSum = countMap.entries.filter { it.key.isWolfCount }.sumOf { it.value }
        val villagerSum = countMap.entries.filterNot {
            val skill = it.key
            skill.isWolfCount || skill.isNoCount
        }.sumOf { it.value }
        // 2日目に村人が一人減るので村人は人狼よりも二人以上多くなければいけない
        if (villagerSum <= wolfSum + 1) {
            logger.info("即PPになるのでやり直し villager: $villagerSum, wolf: $wolfSum")
            return true
        }
        return false
    }
}
