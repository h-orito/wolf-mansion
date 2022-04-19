package com.ort.app.infrastructure.datasource.village

import com.ort.app.domain.model.camp.Camp
import com.ort.app.domain.model.message.MessageType
import com.ort.app.domain.model.skill.Skill
import com.ort.app.domain.model.village.VillageSetting
import com.ort.app.domain.model.village.setting.*
import com.ort.dbflute.allcommon.CDef
import com.ort.dbflute.exbhv.CampAllocationBhv
import com.ort.dbflute.exbhv.NormalSayRestrictionBhv
import com.ort.dbflute.exbhv.SkillAllocationBhv
import com.ort.dbflute.exbhv.SkillSayRestrictionBhv
import com.ort.dbflute.exbhv.VillageCharaGroupBhv
import com.ort.dbflute.exbhv.VillageSettingsBhv
import com.ort.dbflute.exbhv.VillageTagBhv
import com.ort.dbflute.exentity.CampAllocation
import com.ort.dbflute.exentity.NormalSayRestriction
import com.ort.dbflute.exentity.SkillAllocation
import com.ort.dbflute.exentity.SkillSayRestriction
import com.ort.dbflute.exentity.Village
import com.ort.dbflute.exentity.VillageCharaGroup
import com.ort.dbflute.exentity.VillageSettings
import org.springframework.stereotype.Repository
import java.time.format.DateTimeFormatter
import com.ort.dbflute.exentity.VillageTag as DbVillageTag

@Repository
class VillageSettingsDataSource(
    private val villageSettingsBhv: VillageSettingsBhv,
    private val villageCharaGroupBhv: VillageCharaGroupBhv,
    private val normalSayRestrictionBhv: NormalSayRestrictionBhv,
    private val skillSayRestrictionBhv: SkillSayRestrictionBhv,
    private val campAllocationBhv: CampAllocationBhv,
    private val skillAllocationBhv: SkillAllocationBhv,
    private val villageTagBhv: VillageTagBhv
) {
    private val formatter = DateTimeFormatter.ofPattern("uuuuMMddhhmm")

    fun insertVillageSettings(villageId: Int, paramVillage: com.ort.app.domain.model.village.Village) {
        val settings = VillageSettings()
        settings.villageId = villageId
        paramVillage.setting.let {
            settings.dummyCharaId = it.chara.dummyCharaId
            settings.startPersonMinNum = it.personMin
            settings.personMaxNum = it.personMax
            settings.dayChangeIntervalSeconds = it.dayChangeIntervalSeconds
            settings.startDatetime = it.startDatetime
            settings.isOpenVote = it.rule.isOpenVote
            settings.isPossibleSkillRequest = it.rule.isPossibleSkillRequest
            settings.isAvailableSpectate = it.rule.isAvailableSpectate
            settings.joinPassword = it.joinPassword
            settings.isAvailableSameWolfAttack = it.rule.isAvailableSameWolfAttack
            settings.isAvailableGuardSameTarget = it.rule.isAvailableGuardSameTarget
            settings.isOpenSkillInGrave = it.rule.isOpenSkillInGrave
            settings.isVisibleGraveSpectateMessage = it.rule.isVisibleGraveSpectateMessage
            settings.isAvailableSuddonlyDeath = it.rule.isAvailableSuddenlyDeath
            settings.isAvailableCommit = it.rule.isAvailableCommit
            settings.isAvailableAction = it.rule.isAvailableAction
            settings.organize = it.organize.fixedOrganization.replace("\r\n", "\n")
            settings.allowedSecretSayCodeAsAllowedSecretSay =
                if (it.rule.isAvailableSecretSay) CDef.AllowedSecretSay.全員
                else CDef.AllowedSecretSay.なし
            settings.isRandomOrganize = it.rule.isRandomOrganization
            settings.isReincarnationSkillAll = it.rule.isReincarnationSkillAll
            settings.originalCharaGroupId = if (it.chara.isOriginalCharachip) it.chara.charachipIds.first() else null
        }
        villageSettingsBhv.insert(settings)
    }

    fun insertVillageCharaGroups(villageId: Int, paramVillage: com.ort.app.domain.model.village.Village) {
        if (paramVillage.setting.chara.isOriginalCharachip) return
        paramVillage.setting.chara.charachipIds.forEach {
            val v = VillageCharaGroup()
            v.villageId = villageId
            v.charaGroupId = it
            villageCharaGroupBhv.insert(v)
        }
    }

    fun insertAllocation(id: Int, paramVillage: com.ort.app.domain.model.village.Village) {
        paramVillage.setting.organize.randomOrganization.let { org ->
            org.campAllocation.forEach { insertCampAllocation(id, it) }
            org.skillAllocation.forEach { insertSkillAllocation(id, it) }
        }
    }

    fun insertMessageRestrict(id: Int, paramVillage: com.ort.app.domain.model.village.Village) {
        paramVillage.setting.sayRestriction.let { restriction ->
            restriction.normalSayRestriction.forEach { insertNormalSayRestriction(id, it) }
            restriction.skillSayRestriction.forEach { insertSkillSayRestriction(id, it) }
        }
    }

    fun insertVillageTags(id: Int, paramVillage: com.ort.app.domain.model.village.Village) {
        paramVillage.setting.tags.list.forEach {
            insertVillageTag(id, it)
        }
    }

    fun updateSetting(village: com.ort.app.domain.model.village.Village) {
        updateVillageSettings(village.id, village.setting)
        updateRestriction(village.id, village.setting.sayRestriction)
        updateAllocation(village.id, village.setting.organize)
        updateVillageTags(village.id, village.setting.tags)
    }

    fun updateDummyCharaId(id: Int, charaId: Int) {
        val s = VillageSettings()
        s.villageId = id
        s.dummyCharaId = charaId
        villageSettingsBhv.update(s)
    }

    fun updateDaychangeDifference(villageId: Int, current: VillageSetting, changed: VillageSetting) {
        // 変更する可能性があるのは開始時間と連続襲撃可能かのみ
        if (current.startDatetime.format(formatter) == changed.startDatetime.format(formatter)
            && current.rule.isAvailableSameWolfAttack == changed.rule.isAvailableSameWolfAttack
        ) return
        val setting = VillageSettings()
        setting.villageId = villageId
        setting.startDatetime = changed.startDatetime
        setting.isAvailableSameWolfAttack = changed.rule.isAvailableSameWolfAttack
        villageSettingsBhv.update(setting)
    }

    fun mapSimpleSetting(village: Village): VillageSetting {
        val setting = village.villageSettingsAsOne.get()
        val isOriginalCharaGroup = setting.originalCharaGroupId != null
        return VillageSetting(
            chara = VillageCharaSetting(
              isOriginalCharachip = isOriginalCharaGroup,
                dummyCharaId = setting.dummyCharaId,
                charachipIds =
                if (isOriginalCharaGroup) listOf(setting.originalCharaGroupId)
                else village.villageCharaGroupList.map { it.charaGroupId }
            ),
            personMin = setting.startPersonMinNum,
            personMax = setting.personMaxNum,
            startDatetime = setting.startDatetime,
            dayChangeIntervalSeconds = setting.dayChangeIntervalSeconds,
            rule = VillageRule(
                isOpenVote = setting.isOpenVote,
                isPossibleSkillRequest = setting.isPossibleSkillRequest,
                isAvailableSpectate = setting.isAvailableSpectate,
                isAvailableSameWolfAttack = setting.isAvailableSameWolfAttack,
                isOpenSkillInGrave = setting.isOpenSkillInGrave,
                isVisibleGraveSpectateMessage = setting.isVisibleGraveSpectateMessage,
                isAvailableSuddenlyDeath = setting.isAvailableSuddonlyDeath,
                isAvailableCommit = setting.isAvailableCommit,
                isAvailableGuardSameTarget = setting.isAvailableGuardSameTarget,
                isAvailableSecretSay = !setting.isAllowedSecretSayCodeなし,
                isAvailableAction = setting.isAvailableAction,
                isRandomOrganization = setting.isRandomOrganize,
                isReincarnationSkillAll = setting.isReincarnationSkillAll
            ),
            joinPassword = setting.joinPassword,
            organize = VillageOrganize(
                fixedOrganization = setting.organize,
                randomOrganization = VillageRandomOrganize(
                    skillAllocation = emptyList(),
                    campAllocation = emptyList()
                )
            ),
            sayRestriction = SayRestriction(
                normalSayRestriction = emptyList(),
                skillSayRestriction = emptyList()
            ),
            tags = VillageTags(
                list = village.villageTagList.map { it.villageTagItemCodeAsVillageTagItem.toModel() }
            )
        )
    }

    fun mapSetting(village: Village): VillageSetting {
        val setting = village.villageSettingsAsOne.get()
        val skillAllocationList = village.skillAllocationList
        val campAllocationList = village.campAllocationList
        val normalSayRestrictionList = village.normalSayRestrictionList
        val skillSayRestrictionList = village.skillSayRestrictionList
        val tagList = village.villageTagList
        val isOriginalCharaGroup = setting.originalCharaGroupId != null
        return VillageSetting(
            chara = VillageCharaSetting(
                isOriginalCharachip = isOriginalCharaGroup,
                dummyCharaId = setting.dummyCharaId,
                charachipIds =
                if (isOriginalCharaGroup) listOf(setting.originalCharaGroupId)
                else village.villageCharaGroupList.map { it.charaGroupId }
            ),
            personMin = setting.startPersonMinNum,
            personMax = setting.personMaxNum,
            startDatetime = setting.startDatetime,
            dayChangeIntervalSeconds = setting.dayChangeIntervalSeconds,
            rule = VillageRule(
                isOpenVote = setting.isOpenVote,
                isPossibleSkillRequest = setting.isPossibleSkillRequest,
                isAvailableSpectate = setting.isAvailableSpectate,
                isAvailableSameWolfAttack = setting.isAvailableSameWolfAttack,
                isOpenSkillInGrave = setting.isOpenSkillInGrave,
                isVisibleGraveSpectateMessage = setting.isVisibleGraveSpectateMessage,
                isAvailableSuddenlyDeath = setting.isAvailableSuddonlyDeath,
                isAvailableCommit = setting.isAvailableCommit,
                isAvailableGuardSameTarget = setting.isAvailableGuardSameTarget,
                isAvailableSecretSay = !setting.isAllowedSecretSayCodeなし,
                isAvailableAction = setting.isAvailableAction,
                isRandomOrganization = setting.isRandomOrganize,
                isReincarnationSkillAll = setting.isReincarnationSkillAll
            ),
            joinPassword = setting.joinPassword,
            organize = VillageOrganize(
                fixedOrganization = setting.organize,
                randomOrganization = VillageRandomOrganize(
                    skillAllocation = skillAllocationList.map {
                        VillageRandomOrganize.SkillAllocation(
                            skill = Skill(it.skillCodeAsSkill),
                            min = it.minNum,
                            max = it.maxNum,
                            allocation = it.allocation
                        )
                    },
                    campAllocation = campAllocationList.map {
                        VillageRandomOrganize.CampAllocation(
                            camp = Camp(it.campCodeAsCamp),
                            min = it.minNum,
                            max = it.maxNum,
                            allocation = it.allocation
                        )
                    }
                )
            ),
            sayRestriction = SayRestriction(
                normalSayRestriction = normalSayRestrictionList.map {
                    SayRestriction.NormalSayRestriction(
                        skill = Skill(it.skillCodeAsSkill),
                        messageType = MessageType(it.messageTypeCodeAsMessageType),
                        count = it.messageMaxNum,
                        length = it.messageMaxLength
                    )
                },
                skillSayRestriction = skillSayRestrictionList.map {
                    SayRestriction.SkillSayRestriction(
                        messageType = MessageType(it.messageTypeCodeAsMessageType),
                        count = it.messageMaxNum,
                        length = it.messageMaxLength
                    )
                }
            ),
            tags = VillageTags(
                list = tagList.map { it.villageTagItemCodeAsVillageTagItem.toModel() }
            )
        )
    }

    private fun updateVillageSettings(villageId: Int, setting: VillageSetting) {
        val s = VillageSettings()
        s.villageId = villageId
        s.startPersonMinNum = setting.personMin
        s.personMaxNum = setting.personMax
        s.dayChangeIntervalSeconds = setting.dayChangeIntervalSeconds
        s.startDatetime = setting.startDatetime
        s.isOpenVote = setting.rule.isOpenVote
        s.isAvailableSameWolfAttack = setting.rule.isAvailableSameWolfAttack
        s.isAvailableGuardSameTarget = setting.rule.isAvailableGuardSameTarget
        s.isAvailableCommit = setting.rule.isAvailableCommit
        s.isOpenSkillInGrave = setting.rule.isOpenSkillInGrave
        s.isVisibleGraveSpectateMessage = setting.rule.isVisibleGraveSpectateMessage
        s.isAvailableSpectate = setting.rule.isAvailableSpectate
        s.isAvailableSuddonlyDeath = setting.rule.isAvailableSuddenlyDeath
        s.isAvailableAction = setting.rule.isAvailableAction
        s.organize = setting.organize.fixedOrganization
        s.isRandomOrganize = setting.rule.isRandomOrganization
        s.isReincarnationSkillAll = setting.rule.isReincarnationSkillAll
        s.joinPassword = setting.joinPassword
        s.allowedSecretSayCodeAsAllowedSecretSay = if (setting.rule.isAvailableSecretSay) CDef.AllowedSecretSay.全員
        else CDef.AllowedSecretSay.なし
        villageSettingsBhv.update(s)
    }

    private fun updateRestriction(villageId: Int, restriction: SayRestriction) {
        normalSayRestrictionBhv.queryDelete { it.query().setVillageId_Equal(villageId) }
        skillSayRestrictionBhv.queryDelete { it.query().setVillageId_Equal(villageId) }
        restriction.normalSayRestriction.forEach { insertNormalSayRestriction(villageId, it) }
        restriction.skillSayRestriction.forEach { insertSkillSayRestriction(villageId, it) }
    }

    private fun insertNormalSayRestriction(
        villageId: Int,
        restriction: SayRestriction.NormalSayRestriction
    ) {
        val r = NormalSayRestriction()
        r.villageId = villageId
        r.messageMaxNum = restriction.count
        r.messageMaxLength = restriction.length
        r.messageTypeCodeAsMessageType = restriction.messageType.toCdef()
        r.skillCodeAsSkill = restriction.skill.toCdef()
        normalSayRestrictionBhv.insert(r)
    }

    private fun insertSkillSayRestriction(
        villageId: Int,
        restriction: SayRestriction.SkillSayRestriction
    ) {
        val r = SkillSayRestriction()
        r.villageId = villageId
        r.messageMaxNum = restriction.count
        r.messageMaxLength = restriction.length
        r.messageTypeCodeAsMessageType = restriction.messageType.toCdef()
        skillSayRestrictionBhv.insert(r)
    }

    private fun updateAllocation(villageId: Int, organize: VillageOrganize) {
        campAllocationBhv.queryDelete { it.query().setVillageId_Equal(villageId) }
        skillAllocationBhv.queryDelete { it.query().setVillageId_Equal(villageId) }
        organize.randomOrganization.campAllocation.forEach { insertCampAllocation(villageId, it) }
        organize.randomOrganization.skillAllocation.forEach { insertSkillAllocation(villageId, it) }
    }

    private fun insertCampAllocation(
        villageId: Int,
        campAllocation: VillageRandomOrganize.CampAllocation
    ) {
        val a = CampAllocation()
        a.villageId = villageId
        a.campCodeAsCamp = campAllocation.camp.toCdef()
        a.minNum = campAllocation.min
        a.maxNum = campAllocation.max
        a.allocation = campAllocation.allocation
        campAllocationBhv.insert(a)
    }

    private fun insertSkillAllocation(
        villageId: Int,
        skillAllocation: VillageRandomOrganize.SkillAllocation
    ) {
        val a = SkillAllocation()
        a.villageId = villageId
        a.skillCodeAsSkill = skillAllocation.skill.toCdef()
        a.minNum = skillAllocation.min
        a.maxNum = skillAllocation.max
        a.allocation = skillAllocation.allocation
        skillAllocationBhv.insert(a)
    }

    private fun updateVillageTags(villageId: Int, tags: VillageTags) {
        villageTagBhv.queryDelete {
            it.query().setVillageId_Equal(villageId)
        }
        tags.list.forEach { insertVillageTag(villageId, it) }
    }

    private fun insertVillageTag(id: Int, tag: VillageTag) {
        val t = DbVillageTag()
        t.villageId = id
        t.villageTagItemCodeAsVillageTagItem = tag.toCdef()
        villageTagBhv.insert(t)
    }
}