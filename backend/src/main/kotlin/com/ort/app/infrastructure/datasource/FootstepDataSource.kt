package com.ort.app.infrastructure.datasource

import com.ort.app.domain.model.footstep.Footstep
import com.ort.app.domain.model.footstep.FootstepRepository
import com.ort.app.domain.model.footstep.Footsteps
import com.ort.app.domain.model.village.Village
import com.ort.app.domain.model.village.participant.VillageParticipant
import com.ort.app.domain.service.ability.AbilityDomainService
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

    override fun updateFootstep(
        village: Village,
        myself: VillageParticipant,
        charaId: Int?,
        footstep: String?
    ) {
        if (!myself.skill!!.hasDisturbAbility() &&
            !abilityDomainService.detectAbilityTypeService(myself.skill.getAbility()!!).isTargetingAndFootstep()
        ) {
            return
        }
        deleteFootstep(village.id, village.latestDay(), myself.charaId)
        insertFootstep(village.id, village.latestDay(), myself.charaId, charaId ?: myself.charaId, footstep ?: "なし")
    }

    override fun updateDaychangeDifference(village: Village, current: Footsteps, changed: Footsteps) {
        changed.list.filterNot { changedFootstep ->
            current.list.any { currentFootstep ->
                currentFootstep.day == changedFootstep.day
                        && currentFootstep.registerCharaId == changedFootstep.registerCharaId
                        && currentFootstep.charaId == changedFootstep.charaId
            }
        }.forEach { insertFootstep(village.id, it.day, it.registerCharaId, it.charaId, it.roomNumbers) }

        current.list.filterNot { currentFootstep ->
            changed.list.any { changedFootstep ->
                currentFootstep.day == changedFootstep.day
                        && currentFootstep.registerCharaId == changedFootstep.registerCharaId
                        && currentFootstep.charaId == changedFootstep.charaId
            }
        }.forEach { deleteFootstep(village.id, it.day, it.registerCharaId) }
    }

    private fun deleteFootstep(villageId: Int, day: Int, registerCharaId: Int) {
        footstepBhv.queryDelete {
            it.query().setVillageId_Equal(villageId)
            it.query().setDay_Equal(day)
            it.query().setRegisterCharaId_Equal(registerCharaId)
        }
    }

    private fun insertFootstep(
        villageId: Int,
        day: Int,
        registerCharaId: Int,
        charaId: Int,
        footstep: String
    ) {
        val f = DbFootstep()
        f.villageId = villageId
        f.day = day
        f.registerCharaId = registerCharaId
        f.charaId = charaId
        f.footstepRoomNumbers = footstep
        footstepBhv.insert(f)
    }

    private fun mapFootsteps(list: List<DbFootstep>): Footsteps = Footsteps(list = list.map { mapFootstep(it) })

    private fun mapFootstep(footstep: DbFootstep): Footstep =
        Footstep(
            day = footstep.day,
            registerCharaId = footstep.registerCharaId,
            charaId = footstep.charaId,
            roomNumbers = footstep.footstepRoomNumbers
        )
}