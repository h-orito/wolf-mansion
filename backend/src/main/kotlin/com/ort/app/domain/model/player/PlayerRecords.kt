package com.ort.app.domain.model.player

import com.ort.app.domain.model.camp.Camps
import com.ort.app.domain.model.skill.Skills
import com.ort.app.domain.model.village.Villages

data class PlayerRecords(
    val player: Player,
    val wholeRecord: Record,
    val campRecordList: List<CampRecord>,
    val skillRecordList: List<SkillRecord>,
    val participateVillageList: List<ParticipateVillage>
) {
    constructor(
        player: Player,
        villages: Villages
    ) : this(
        player = player,
        wholeRecord = Record(player, villages.filterNotSpectate(player.id)),
        campRecordList = createCampRecordList(player, villages.filterNotSpectate(player.id)),
        skillRecordList = createSkillRecordList(player, villages.filterNotSpectate(player.id)),
        participateVillageList = villages.list.sortedByDescending { it.id }.map { ParticipateVillage(player, it) }
    )

    companion object {
        private fun Villages.filterNotSpectate(playerId: Int): Villages {
            return copy(
                list = list.filter { v ->
                    v.participants.list.any { it.playerId == playerId }
                }
            )
        }

        private fun createCampRecordList(player: Player, villages: Villages): List<CampRecord> {
            return Camps.all().list.map { camp ->
                val campVillageList = villages.list.filter { village ->
                    val myCampCode = village.participants.list.first {
                        !it.isGone && it.playerId == player.id
                    }.camp?.code
                    myCampCode == camp.code
                }
                CampRecord(camp, player, Villages(campVillageList))
            }
        }

        private fun createSkillRecordList(player: Player, villages: Villages): List<SkillRecord> {
            return Skills.all().filterNotSomeone().list
                .map { skill ->
                    val skillVillageList = villages.list.filter { village ->
                        val mySkillCode = village.participants.list.first {
                            !it.isGone && it.playerId == player.id
                        }.skill!!.code
                        mySkillCode == skill.code
                    }
                    SkillRecord(skill, player, Villages(skillVillageList))
                }
        }
    }
}