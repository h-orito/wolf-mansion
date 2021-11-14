package com.ort.app.infrastructure.datasource

import com.ort.app.domain.model.camp.Camp
import com.ort.app.domain.model.chara.Chara
import com.ort.app.domain.model.skill.Skill
import com.ort.app.domain.model.village.Village
import com.ort.app.domain.model.village.VillageDay
import com.ort.app.domain.model.village.VillageDays
import com.ort.app.domain.model.village.VillageRepository
import com.ort.app.domain.model.village.VillageStatus
import com.ort.app.domain.model.village.Villages
import com.ort.app.domain.model.village.participant.VillageParticipant
import com.ort.app.domain.model.village.room.RoomSize
import com.ort.app.infrastructure.datasource.village.VillageDayDataSource
import com.ort.app.infrastructure.datasource.village.VillagePlayerDataSource
import com.ort.app.infrastructure.datasource.village.VillageSettingsDataSource
import com.ort.dbflute.cbean.VillageCB
import com.ort.dbflute.exbhv.VillageBhv
import com.ort.dbflute.exbhv.VillageDayBhv
import com.ort.dbflute.exbhv.VillagePlayerBhv
import org.springframework.stereotype.Repository
import java.time.LocalDateTime
import com.ort.dbflute.exentity.Village as DbVillage

@Repository
class VillageDataSource(
    private val villageBhv: VillageBhv,
    private val villagePlayerDataSource: VillagePlayerDataSource,
    private val villageSettingsDataSource: VillageSettingsDataSource,
    private val villageDayDataSource: VillageDayDataSource
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
            loader.loadVillageCharaGroup {
                it.addOrderBy_PK_Asc()
            }
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
            loader.loadVillageCharaGroup {
                it.addOrderBy_PK_Asc()
            }
            loader.loadVillageDay {
                it.query().addOrderBy_Day_Asc()
            }
            loader.loadVillagePlayer {
                it.setupSelect_Player()
                if (excludeGone) it.query().setIsGone_Equal_False()
            }.withNestedReferrer { villagePlayerDataSource.withNestedVillagePlayer(it) }
            loader.loadNormalSayRestriction { }
            loader.loadSkillSayRestriction { }
            loader.loadSkillAllocation { }
            loader.loadCampAllocation { }
        }
        return mapVillage(village)
    }

    override fun updateStatus(id: Int, status: VillageStatus) {
        val v = DbVillage()
        v.villageStatusCodeAsVillageStatus = status.toCdef()
        villageBhv.queryUpdate(v) { it.query().setVillageId_Equal(id) }
    }

    override fun extendDay(id: Int, day: Int, datetime: LocalDateTime) =
        villageDayDataSource.extendDay(id, day, datetime)

    override fun shortenDay(id: Int, day: Int, datetime: LocalDateTime) =
        villageDayDataSource.shortenDay(id, day, datetime)

    override fun findVillageParticipant(
        id: Int,
        excludeGone: Boolean
    ): VillageParticipant? = villagePlayerDataSource.findVillageParticipant(id, excludeGone)

    override fun findVillageParticipant(
        villageId: Int,
        userName: String,
        excludeGone: Boolean
    ): VillageParticipant? = villagePlayerDataSource.findVillageParticipant(villageId, userName, excludeGone)

    override fun registerVillage(paramVillage: Village): Village {
        val id = insertVillage(paramVillage)
        villageSettingsDataSource.insertVillageSettings(id, paramVillage)
        villageSettingsDataSource.insertVillageCharaGroups(id, paramVillage)
        villageSettingsDataSource.insertAllocation(id, paramVillage)
        villageSettingsDataSource.insertMessageRestrict(id, paramVillage)
        villageDayDataSource.insertVillageDays(id, paramVillage)
        return findVillage(id, true)!!
    }

    override fun updateDaychangeDifference(current: Village, changed: Village) {
        updateVillageDaychangeDifference(current, changed)
        villagePlayerDataSource.updateDaychangeDifference(current.allParticipants(), changed.allParticipants())
        villageDayDataSource.updateDaychangeDifference(changed.id, current.days, changed.days)
        villageSettingsDataSource.updateDaychangeDifference(changed.id, current.setting, changed.setting)
    }

    override fun participate(
        villageId: Int,
        playerId: Int,
        chara: Chara,
        firstRequestSkill: Skill,
        secondRequestSkill: Skill,
        spectator: Boolean
    ): VillageParticipant {
        val vPlayer = villagePlayerDataSource.insertVillagePlayer(
            villageId,
            playerId,
            chara,
            spectator,
            firstRequestSkill,
            secondRequestSkill
        )
        return findVillageParticipant(vPlayer.villagePlayerId, true)!!
    }

    override fun leave(participant: VillageParticipant) = villagePlayerDataSource.leave(participant)

    override fun changeParticipantName(
        participant: VillageParticipant,
        name: String,
        shortName: String
    ) = villagePlayerDataSource.changeParticipantName(participant, name, shortName)

    override fun changeRequestSkill(participant: VillageParticipant, first: Skill, second: Skill) =
        villagePlayerDataSource.changeRequestSkill(participant, first, second)

    override fun changeMemo(participant: VillageParticipant, memo: String) =
        villagePlayerDataSource.changeMemo(participant, memo)

    override fun updateLastAccessDatetime(participant: VillageParticipant) =
        villagePlayerDataSource.updateLastAccessDatetime(participant)

    override fun updateSetting(village: Village) {
        villageSettingsDataSource.updateSetting(village)
        villageDayDataSource.updateVillageStartDateTime(village.id, village.days)
    }

    private fun insertVillage(paramVillage: Village): Int {
        val v = DbVillage()
        v.villageDisplayName = paramVillage.name
        v.setVillageStatusCode_募集中()
        v.createPlayerName = paramVillage.createPlayerName
        villageBhv.insert(v)
        return v.villageId
    }

    private fun updateVillageDaychangeDifference(current: Village, changed: Village) {
        if (current.status.code == changed.status.code
            && current.roomSize?.width == changed.roomSize?.width
            && current.epilogueDay == changed.epilogueDay
            && current.winCamp?.code == changed.winCamp?.code
        ) return
        val v = DbVillage()
        v.villageStatusCodeAsVillageStatus = changed.status.toCdef()
        v.roomSizeWidth = changed.roomSize?.width
        v.roomSizeHeight = changed.roomSize?.height
        v.epilogueDay = changed.epilogueDay
        v.winCampCodeAsCamp = changed.winCamp?.toCdef()
        villageBhv.queryUpdate(v) { it.query().setVillageId_Equal(changed.id) }
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
            participants = villagePlayerDataSource.mapSimpleVillageParticipants(village.villagePlayerList),
            spectators = villagePlayerDataSource.mapSimpleVillageSpectators(village.villagePlayerList),
            days = mapVillageDays(village),
            setting = villageSettingsDataSource.mapSimpleSetting(village),
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
            participants = villagePlayerDataSource.mapVillageParticipants(village.villagePlayerList),
            spectators = villagePlayerDataSource.mapVillageSpectators(village.villagePlayerList),
            days = mapVillageDays(village),
            setting = villageSettingsDataSource.mapSetting(village),
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
}