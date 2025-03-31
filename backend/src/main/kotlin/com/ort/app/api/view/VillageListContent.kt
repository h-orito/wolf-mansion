package com.ort.app.api.view

import com.ort.app.api.view.IndexContent.IndexVillage.Tag
import com.ort.app.domain.model.chara.Charachip
import com.ort.app.domain.model.chara.Charachips
import com.ort.app.domain.model.skill.Skill
import com.ort.app.domain.model.skill.Skills
import com.ort.app.domain.model.village.Village
import com.ort.app.domain.model.village.Villages
import com.ort.app.domain.model.village.setting.VillageTags
import com.ort.dbflute.allcommon.CDef

data class VillageListContent(
    val villageList: List<VillageListVillage>,
    val charachipList: List<CharachipContent>,
    val skillList: List<SkillContent>
) {
    constructor(
        villages: Villages,
        charachips: Charachips,
        skills: Skills
    ) : this(
        villageList = villages.list.reversed().map { VillageListVillage(it) },
        charachipList = charachips.list.map { CharachipContent(it) },
        skillList = skills.list.map { SkillContent(it) }
    )

    data class VillageListVillage(
        val villageId: Int,
        val villageNumber: String,
        val villageName: String,
        val participateNum: String,
        val status: String,
        val tags: List<Tag>,
    ) {
        constructor(village: Village) : this(
            villageId = village.id,
            villageNumber = village.id.toString().padStart(4, '0'),
            villageName = village.name,
            participateNum = mapParticipateNum(village),
            status = village.status.name,
            tags = mapTags(village.setting.tags),
        )

        companion object {
            private fun mapParticipateNum(village: Village): String {
                val participantCount = village.participants.count
                val spectatorCount = village.spectators.count
                return if (spectatorCount == 0) {
                    "${participantCount}人"
                } else {
                    "$participantCount ($spectatorCount)人"
                }
            }

            private fun mapTags(tags: VillageTags): List<Tag> {
                val age =
                    tags.list.find { it.toCdef() == CDef.VillageTagItem.R15 || it.toCdef() == CDef.VillageTagItem.R18 }
                        ?.let {
                            listOf(Tag(level = "danger", name = it.name))
                        } ?: emptyList()
                val welcome =
                    tags.list.find { it.toCdef() == CDef.VillageTagItem.誰歓 || it.toCdef() == CDef.VillageTagItem.身内 }
                        ?.let {
                            listOf(Tag(level = "success", name = it.name))
                        } ?: emptyList()
                return age + welcome
            }
        }
    }

    data class CharachipContent(
        val id: Int,
        val name: String
    ) {
        constructor(
            charachip: Charachip
        ) : this(
            id = charachip.id,
            name = charachip.name
        )
    }

    data class SkillContent(
        val code: String,
        val name: String
    ) {
        constructor(
            skill: Skill
        ) : this(
            code = skill.code,
            name = skill.name
        )
    }
}