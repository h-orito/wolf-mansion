package com.ort.app.infrastructure.datasource

import com.ort.app.domain.model.ability.Abilities
import com.ort.app.domain.model.ability.Ability
import com.ort.app.domain.model.ability.AbilityRepository
import com.ort.app.domain.model.ability.AbilityType
import com.ort.app.domain.model.village.Village
import com.ort.dbflute.allcommon.CDef
import com.ort.dbflute.exbhv.AbilityBhv
import org.springframework.stereotype.Repository
import com.ort.dbflute.exentity.Ability as DbAbility

@Repository
class AbilityDataSource(
    private val abilityBhv: AbilityBhv
) : AbilityRepository {

    override fun findAbilities(villageId: Int): Abilities {
        val list = abilityBhv.selectList {
            it.query().setVillageId_Equal(villageId)
        }
        return mapAbilities(list)
    }

    override fun updateAbility(
        village: Village,
        ability: Ability
    ) {
        deleteAbility(village, ability)
        insertAbility(village, ability)
    }

    override fun updateDaychangeDifference(village: Village, current: Abilities, changed: Abilities) {
        changed.list.filterNot { changedAbility ->
            current.list.any { currentAbility ->
                changedAbility.day == currentAbility.day
                        && changedAbility.charaId == currentAbility.charaId
                        && changedAbility.type.code == currentAbility.type.code
            }
        }.forEach { insertAbility(village, it) }
    }

    private fun deleteAbility(village: Village, ability: Ability) {
        val type = ability.type.toCdef()
        abilityBhv.queryDelete {
            it.query().setVillageId_Equal(village.id)
            it.query().setDay_Equal(ability.day)
            it.query().setAbilityTypeCode_Equal_AsAbilityType(ability.type.toCdef())
            if (type == CDef.AbilityType.同棲) {
                val target = village.participants.chara(ability.targetCharaId!!)
                val partner = target.getTargetCohabitor(village)!!
                it.query().setCharaId_InScope(listOf(target.charaId, partner.charaId))
            } else {
                it.query().setCharaId_Equal(ability.charaId)
            }
        }
    }

    private fun insertAbility(village: Village, ability: Ability) {
        val type = ability.type.toCdef()
        if (type != CDef.AbilityType.捜査 && ability.targetCharaId == null) return

        val a = DbAbility()
        a.villageId = village.id
        a.day = ability.day
        a.abilityTypeCodeAsAbilityType = ability.type.toCdef()
        when (type) {
            CDef.AbilityType.同棲 -> {
                val target = village.participants.chara(ability.targetCharaId!!)
                val partner = target.getTargetCohabitor(village)!!
                a.charaId = partner.charaId
                a.targetCharaId = target.charaId
            }
            CDef.AbilityType.捜査 -> {
                a.charaId = ability.charaId
                a.targetFootstep = ability.targetFootstep
            }
            else -> {
                a.charaId = ability.charaId
                a.attackerCharaId = ability.attackerCharaId
                a.targetCharaId = ability.targetCharaId
            }
        }
        abilityBhv.insert(a)
    }

    private fun mapAbilities(list: List<DbAbility>): Abilities = Abilities(list = list.map { mapAbility(it) })

    private fun mapAbility(ability: DbAbility): Ability = Ability(
        day = ability.day,
        type = AbilityType(ability.abilityTypeCodeAsAbilityType),
        charaId = ability.charaId,
        attackerCharaId = ability.attackerCharaId,
        targetCharaId = ability.targetCharaId,
        targetFootstep = ability.targetFootstep
    )
}