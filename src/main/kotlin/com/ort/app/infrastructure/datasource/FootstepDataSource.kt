package com.ort.app.infrastructure.datasource

import com.ort.app.domain.model.footstep.Footstep
import com.ort.app.domain.model.footstep.FootstepRepository
import com.ort.app.domain.model.footstep.Footsteps
import com.ort.app.domain.model.village.Village
import com.ort.app.domain.model.village.participant.VillageParticipant
import com.ort.app.domain.service.ability.AbilityDomainService
import com.ort.dbflute.allcommon.CDef
import com.ort.dbflute.exbhv.FootstepBhv
import org.springframework.stereotype.Repository
import com.ort.dbflute.exentity.Footstep as DbFootstep

@Repository
class FootstepDataSource(
    private val footstepBhv: FootstepBhv,
    private val abilityDomainService: AbilityDomainService
) : FootstepRepository {

    override fun findFootsteps(villageId: Int): Footsteps {
        val list = footstepBhv.selectList {
            it.query().setVillageId_Equal(villageId)
        }
        return mapFootsteps(list)
    }

    override fun updateFootstep(village: Village, myself: VillageParticipant, footstep: String?) {
        if (myself.skill!!.hasDisturbAbility()) {
            // 徘徊
            deleteFootstep(village, myself.charaId)
            insertFootstep(village.id, village.latestDay(), myself.charaId, footstep!!)
            return
        }
        val abilityType = myself.skill.getAbility()!!
        if (abilityDomainService.detectAbilityTypeService(abilityType).isTargetingAndFootstep()) {
            if (abilityType.toCdef() == CDef.AbilityType.襲撃) {
                deleteWolfFootstep(village)
            } else {
                deleteFootstep(village, myself.charaId)
            }
            insertFootstep(village.id, village.latestDay(), myself.charaId, footstep!!)
        }
    }

    override fun updateDaychangeDifference(villageId: Int, current: Footsteps, changed: Footsteps) {
        changed.list.filterNot { changedFootstep ->
            current.list.any { currentFootstep ->
                currentFootstep.day == changedFootstep.day
                        && currentFootstep.charaId == changedFootstep.charaId
            }
        }.forEach { insertFootstep(villageId, it.day, it.charaId, it.roomNumbers) }
    }

    private fun deleteWolfFootstep(village: Village) {
        footstepBhv.queryDelete {
            it.query().setVillageId_Equal(village.id)
            it.query().setDay_Equal(village.latestDay())
            it.query().setCharaId_InScope(village.participants.list.filter { it.skill!!.hasAttackAbility() }
                .map { it.charaId })
        }
    }

    private fun deleteFootstep(village: Village, charaId: Int) {
        footstepBhv.queryDelete {
            it.query().setVillageId_Equal(village.id)
            it.query().setDay_Equal(village.latestDay())
            it.query().setCharaId_Equal(charaId)
        }
    }

    private fun insertFootstep(villageId: Int, day: Int, charaId: Int, footstep: String) {
        val f = DbFootstep()
        f.villageId = villageId
        f.day = day
        f.charaId = charaId
        f.footstepRoomNumbers = footstep
        footstepBhv.insert(f)
    }

    private fun mapFootsteps(list: List<DbFootstep>): Footsteps = Footsteps(list = list.map { mapFootstep(it) })

    private fun mapFootstep(footstep: DbFootstep): Footstep =
        Footstep(day = footstep.day, charaId = footstep.charaId, roomNumbers = footstep.footstepRoomNumbers)
}