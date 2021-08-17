package com.ort.app.infrastructure.datasource

import com.ort.app.domain.model.message.MessageType
import com.ort.app.domain.model.skill.RequestSkill
import com.ort.app.domain.model.skill.Skill
import com.ort.app.domain.model.camp.Camp
import com.ort.app.domain.model.village.Village
import com.ort.app.domain.model.village.VillageDay
import com.ort.app.domain.model.village.VillageDays
import com.ort.app.domain.model.village.VillageRepository
import com.ort.app.domain.model.village.VillageSetting
import com.ort.app.domain.model.village.VillageStatus
import com.ort.app.domain.model.village.Villages
import com.ort.app.domain.model.village.participant.VillageParticipant
import com.ort.app.domain.model.village.participant.VillageParticipantName
import com.ort.app.domain.model.village.participant.VillageParticipantStatus
import com.ort.app.domain.model.village.participant.VillageParticipants
import com.ort.app.domain.model.village.participant.dead.Dead
import com.ort.app.domain.model.village.participant.dead.DeadHistories
import com.ort.app.domain.model.village.participant.dead.DeadHistory
import com.ort.app.domain.model.village.participant.dead.DeadReason
import com.ort.app.domain.model.village.room.Room
import com.ort.app.domain.model.village.room.RoomHistories
import com.ort.app.domain.model.village.room.RoomHistory
import com.ort.app.domain.model.village.room.RoomSize
import com.ort.dbflute.allcommon.CDef
import com.ort.dbflute.bsbhv.loader.LoaderOfVillagePlayer
import com.ort.dbflute.cbean.VillageCB
import com.ort.dbflute.exbhv.VillageBhv
import com.ort.dbflute.exbhv.VillagePlayerBhv
import com.ort.dbflute.exentity.VillagePlayer
import org.springframework.stereotype.Repository
import com.ort.dbflute.exentity.Village as DbVillage

@Repository
class VillageDataSource(
    private val villageBhv: VillageBhv,
    private val villagePlayerBhv: VillagePlayerBhv
) : VillageRepository {

    override fun findLatestVillageId(statusList: List<VillageStatus>): Int {
        return villageBhv.selectScalar(Int::class.java).max { cb: VillageCB ->
            cb.specify().columnVillageId()
            cb.query()
                .setVillageStatusCode_InScope_AsVillageStatus(statusList.map { it.toCdef() })
        }.orElse(0)
    }

    override fun findVillages(
        statusList: List<VillageStatus>,
        idList: List<Int>
    ): Villages {
        val villageList = villageBhv.selectList {
            it.setupSelect_VillageSettingsAsOne()
            it.query()
                .setVillageStatusCode_InScope_AsVillageStatus(
                    statusList.map { status -> status.toCdef() }
                )
            if (idList.isNotEmpty()) {
                it.query().setVillageId_InScope(idList)
            }
            it.query().addOrderBy_VillageId_Asc()
        }
        villageBhv.load(villageList) { loader ->
            loader.loadVillagePlayer {
                it.query().setIsGone_Equal_False()
            }
            loader.loadVillageDay { it.query().addOrderBy_Day_Asc() }
        }
        return mapVillages(villageList)
    }

    override fun findVillage(
        id: Int,
        excludeGone: Boolean
    ): Village? {
        val optVillage = villageBhv.selectEntity {
            it.setupSelect_VillageSettingsAsOne()
            it.query().setVillageId_Equal(id)
        }
        if (!optVillage.isPresent) return null
        val village = optVillage.get()
        villageBhv.load(village) { loader ->
            loader.loadVillageDay {
                it.query().addOrderBy_Day_Asc()
            }
            loader.loadVillagePlayer {
                it.setupSelect_Player()
            }.withNestedReferrer { withNestedVillagePlayer(it) }
            loader.loadNormalSayRestriction { }
            loader.loadSkillSayRestriction { }
            loader.loadSkillAllocation { }
            loader.loadCampAllocation { }
        }
        return mapVillage(village)
    }

    override fun findVillageParticipant(
        id: Int,
        excludeGone: Boolean
    ): VillageParticipant? {
        val optVillagePlayer = villagePlayerBhv.selectEntity {
            it.setupSelect_Player()
            it.query().setVillagePlayerId_Equal(id)
            if (excludeGone) it.query().setIsGone_Equal_False()
        }
        if (!optVillagePlayer.isPresent) return null
        val villagePlayer = optVillagePlayer.get()
        villagePlayerBhv.load(villagePlayer) { withNestedVillagePlayer(it) }
        return mapVillageParticipant(villagePlayer)
    }

    override fun findVillageParticipant(
        villageId: Int,
        userName: String,
        excludeGone: Boolean
    ): VillageParticipant? {
        val optVillagePlayer = villagePlayerBhv.selectEntity {
            it.setupSelect_Player()
            it.query().setVillageId_Equal(villageId)
            it.query().queryPlayer().setPlayerName_Equal(userName)
            if (excludeGone) it.query().setIsGone_Equal_False()
        }
        if (!optVillagePlayer.isPresent) return null
        val villagePlayer = optVillagePlayer.get()
        villagePlayerBhv.load(villagePlayer) { withNestedVillagePlayer(it) }
        return mapVillageParticipant(villagePlayer)
    }

    private fun withNestedVillagePlayer(loader: LoaderOfVillagePlayer) {
        loader.loadVillagePlayerDeadHistory {
            it.query().addOrderBy_Day_Asc()
        }
        loader.loadVillagePlayerRoomHistory {
            it.query().addOrderBy_Day_Asc()
        }
        loader.loadVillagePlayerStatusByVillagePlayerId { }
        loader.loadVillagePlayerStatusByToVillagePlayerId { }
    }

    private fun mapVillages(villageList: List<DbVillage>): Villages {
        return Villages(
            list = villageList.map { mapSimpleVillage(it) }
        )
    }

    private fun mapSimpleVillage(village: DbVillage): Village {
        return Village(
            id = village.villageId,
            name = village.villageDisplayName,
            createPlayerName = village.createPlayerName,
            createDatetime = village.registerDatetime,
            status = VillageStatus(village.villageStatusCodeAsVillageStatus),
            roomSize = if (village.roomSizeWidth != null) RoomSize(
                width = village.roomSizeWidth,
                height = village.roomSizeHeight
            ) else null,
            participants = mapSimpleVillageParticipants(village.villagePlayerList),
            spectators = mapSimpleVillageSpectators(village.villagePlayerList),
            days = mapVillageDays(village),
            setting = mapSimpleSetting(village),
            epilogueDay = village.epilogueDay,
            winCamp = if (village.winCampCode.isNullOrBlank()) null else Camp(village.winCampCodeAsCamp)
        )
    }

    private fun mapVillage(village: DbVillage): Village {
        return Village(
            id = village.villageId,
            name = village.villageDisplayName,
            createPlayerName = village.createPlayerName,
            createDatetime = village.registerDatetime,
            status = VillageStatus(village.villageStatusCodeAsVillageStatus),
            roomSize = if (village.roomSizeWidth != null) RoomSize(
                width = village.roomSizeWidth,
                height = village.roomSizeHeight
            ) else null,
            participants = mapVillageParticipants(village.villagePlayerList),
            spectators = mapVillageSpectators(village.villagePlayerList),
            days = mapVillageDays(village),
            setting = mapSetting(village),
            epilogueDay = village.epilogueDay,
            winCamp = if (village.winCampCode.isNullOrBlank()) null else Camp(village.winCampCodeAsCamp)
        )
    }

    private fun mapVillageDays(village: DbVillage): VillageDays {
        return VillageDays(
            list = village.villageDayList.map {
                VillageDay(
                    day = it.day,
                    dayChangeDatetime = it.daychangeDatetime
                )
            }
        )
    }

    private fun mapSimpleVillageParticipants(villagePlayerList: List<VillagePlayer>): VillageParticipants {
        val participantList = villagePlayerList.filterNot { it.isSpectator }
        return VillageParticipants(
            count = participantList.size,
            list = participantList.map { mapSimpleVillageParticipant(it) }
        )
    }

    private fun mapVillageParticipants(villagePlayerList: List<VillagePlayer>): VillageParticipants {
        val participantList = villagePlayerList.filterNot { it.isSpectator }
        return VillageParticipants(
            count = participantList.size,
            list = participantList.map { mapVillageParticipant(it) }
        )
    }

    private fun mapSimpleVillageSpectators(villagePlayerList: List<VillagePlayer>): VillageParticipants {
        val spectatorList = villagePlayerList.filter { it.isSpectator }
        return VillageParticipants(
            count = spectatorList.size,
            list = spectatorList.map { mapSimpleVillageParticipant(it) }
        )
    }

    private fun mapVillageSpectators(villagePlayerList: List<VillagePlayer>): VillageParticipants {
        val spectatorList = villagePlayerList.filter { it.isSpectator }
        return VillageParticipants(
            count = spectatorList.size,
            list = spectatorList.map { mapVillageParticipant(it) }
        )
    }

    private fun mapSimpleVillageParticipant(villagePlayer: VillagePlayer): VillageParticipant {
        return VillageParticipant(
            id = villagePlayer.villagePlayerId,
            charaName = VillageParticipantName(
                name = villagePlayer.charaName,
                shortName = villagePlayer.charaShortName
            ),
            playerId = villagePlayer.playerId,
            charaId = villagePlayer.charaId,
            skill = if (villagePlayer.skillCode.isNullOrBlank()) null else Skill(villagePlayer.skillCodeAsSkill),
            requestSkill = if (villagePlayer.requestSkillCode.isNullOrBlank()) null
            else RequestSkill(
                first = Skill(villagePlayer.requestSkillCodeAsSkill),
                second = Skill(villagePlayer.secondRequestSkillCodeAsSkill)
            ),
            room = null, // simple
            status = VillageParticipantStatus( // simple
                loverIdList = emptyList(),
                foxPossessionIdList = emptyList(),
                foxPossessionedIdList = emptyList()
            ),
            dead = mapSimpleDead(villagePlayer),
            isSpectator = villagePlayer.isSpectator,
            isGone = villagePlayer.isGone,
            isWin = villagePlayer.isWin,
            camp = if (villagePlayer.campCode.isNullOrBlank()) null else Camp(CDef.Camp.codeOf(villagePlayer.campCode)),
            lastAccessDatetime = villagePlayer.lastAccessDatetime,
            memo = villagePlayer.memo
        )
    }

    private fun mapVillageParticipant(villagePlayer: VillagePlayer): VillageParticipant {
        return VillageParticipant(
            id = villagePlayer.villagePlayerId,
            charaName = VillageParticipantName(
                name = villagePlayer.charaName,
                shortName = villagePlayer.charaShortName
            ),
            playerId = villagePlayer.playerId,
            charaId = villagePlayer.charaId,
            skill = if (villagePlayer.skillCode.isNullOrBlank()) null else Skill(villagePlayer.skillCodeAsSkill),
            requestSkill = if (villagePlayer.requestSkillCode.isNullOrBlank()) null
            else RequestSkill(
                first = Skill(villagePlayer.requestSkillCodeAsSkill),
                second = Skill(villagePlayer.secondRequestSkillCodeAsSkill)
            ),
            room = if (villagePlayer.roomNumber == null) null else mapRoom(villagePlayer),
            status = mapVillageParticipantStatus(villagePlayer),
            dead = mapDead(villagePlayer),
            isSpectator = villagePlayer.isSpectator,
            isGone = villagePlayer.isGone,
            isWin = villagePlayer.isWin,
            camp = if (villagePlayer.campCode.isNullOrBlank()) null else Camp(CDef.Camp.codeOf(villagePlayer.campCode)),
            lastAccessDatetime = villagePlayer.lastAccessDatetime,
            memo = villagePlayer.memo
        )
    }

    private fun mapSimpleDead(villagePlayer: VillagePlayer): Dead {
        return Dead(
            isDead = villagePlayer.isDead,
            deadDay = villagePlayer.deadDay,
            reason = if (villagePlayer.deadReasonCode.isNullOrBlank()) null else DeadReason(villagePlayer.deadReasonCodeAsDeadReason),
            histories = DeadHistories( // simple
                list = emptyList()
            )
        )
    }

    private fun mapDead(villagePlayer: VillagePlayer): Dead {
        return Dead(
            isDead = villagePlayer.isDead,
            deadDay = villagePlayer.deadDay,
            reason = if (villagePlayer.deadReasonCode.isNullOrBlank()) null else DeadReason(villagePlayer.deadReasonCodeAsDeadReason),
            histories = DeadHistories(
                list = villagePlayer.villagePlayerDeadHistoryList.map {
                    DeadHistory(
                        day = it.day,
                        isDead = it.isDead,
                        reason = if (it.deadReasonCode.isNullOrBlank()) null else DeadReason(it.deadReasonCodeAsDeadReason)
                    )
                }
            )
        )
    }

    private fun mapRoom(villagePlayer: VillagePlayer): Room {
        return Room(
            number = villagePlayer.roomNumber,
            histories = RoomHistories(
                list = villagePlayer.villagePlayerRoomHistoryList.map {
                    RoomHistory(day = it.day, number = it.roomNumber)
                }
            )
        )
    }

    private fun mapVillageParticipantStatus(villagePlayer: VillagePlayer): VillageParticipantStatus {
        // 自分からのステータス
        val statusList = villagePlayer.villagePlayerStatusByVillagePlayerIdList
        // 自分へのステータス
        val toStatusList = villagePlayer.villagePlayerStatusByToVillagePlayerIdList

        return VillageParticipantStatus(
            loverIdList = statusList.filter { it.isVillagePlayerStatusCode後追い }.map { it.toVillagePlayerId },
            foxPossessionIdList = statusList.filter { it.isVillagePlayerStatusCode狐憑き }.map { it.toVillagePlayerId },
            foxPossessionedIdList = toStatusList.filter { it.isVillagePlayerStatusCode狐憑き }.map { it.villagePlayerId }
        )
    }

    private fun mapSimpleSetting(village: DbVillage): VillageSetting {
        val setting = village.villageSettingsAsOne.get()
        return VillageSetting(
            dummyCharaId = setting.dummyCharaId,
            charachipId = setting.characterGroupId,
            personMin = setting.startPersonMinNum,
            personMax = setting.personMaxNum,
            startDatetime = setting.startDatetime,
            dayChangeIntervalSeconds = setting.dayChangeIntervalSeconds,
            rule = VillageSetting.VillageRule(
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
                isRandomOrganization = setting.isRandomOrganize
            ),
            joinPassword = setting.joinPassword,
            organize = VillageSetting.VillageOrganize(
                fixedOrganization = setting.organize,
                randomOrganization = VillageSetting.VillageOrganize.VillageRandomOrganize(
                    skillAllocation = emptyList(),
                    campAllocation = emptyList()
                )
            ),
            sayRestriction = VillageSetting.SayRestriction(
                normalSayRestriction = emptyList(),
                skillSayRestriction = emptyList()
            )
        )
    }

    private fun mapSetting(village: DbVillage): VillageSetting {
        val setting = village.villageSettingsAsOne.get()
        val skillAllocationList = village.skillAllocationList
        val campAllocationList = village.campAllocationList
        val normalSayRestrictionList = village.normalSayRestrictionList
        val skillSayRestrictionList = village.skillSayRestrictionList
        return VillageSetting(
            dummyCharaId = setting.dummyCharaId,
            charachipId = setting.characterGroupId,
            personMin = setting.startPersonMinNum,
            personMax = setting.personMaxNum,
            startDatetime = setting.startDatetime,
            dayChangeIntervalSeconds = setting.dayChangeIntervalSeconds,
            rule = VillageSetting.VillageRule(
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
                isRandomOrganization = setting.isRandomOrganize
            ),
            joinPassword = setting.joinPassword,
            organize = VillageSetting.VillageOrganize(
                fixedOrganization = setting.organize,
                randomOrganization = VillageSetting.VillageOrganize.VillageRandomOrganize(
                    skillAllocation = skillAllocationList.map {
                        VillageSetting.VillageOrganize.VillageRandomOrganize.SkillAllocation(
                            skill = Skill(it.skillCodeAsSkill),
                            min = it.minNum,
                            max = it.maxNum,
                            allocation = it.allocation
                        )
                    },
                    campAllocation = campAllocationList.map {
                        VillageSetting.VillageOrganize.VillageRandomOrganize.CampAllocation(
                            camp = Camp(it.campCodeAsCamp),
                            min = it.minNum,
                            max = it.maxNum,
                            allocation = it.allocation
                        )
                    }
                )
            ),
            sayRestriction = VillageSetting.SayRestriction(
                normalSayRestriction = normalSayRestrictionList.map {
                    VillageSetting.SayRestriction.NormalSayRestriction(
                        skill = Skill(it.skillCodeAsSkill),
                        messageType = MessageType(it.messageTypeCodeAsMessageType),
                        count = it.messageMaxNum,
                        length = it.messageMaxLength
                    )
                },
                skillSayRestriction = skillSayRestrictionList.map {
                    VillageSetting.SayRestriction.SkillSayRestriction(
                        messageType = MessageType(it.messageTypeCodeAsMessageType),
                        count = it.messageMaxNum,
                        length = it.messageMaxLength
                    )
                }
            )
        )
    }
}