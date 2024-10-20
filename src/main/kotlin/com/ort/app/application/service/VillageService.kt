package com.ort.app.application.service

import com.ort.app.domain.model.chara.Chara
import com.ort.app.domain.model.skill.Skill
import com.ort.app.domain.model.village.Village
import com.ort.app.domain.model.village.VillageQuery
import com.ort.app.domain.model.village.VillageRepository
import com.ort.app.domain.model.village.VillageStatus
import com.ort.app.domain.model.village.Villages
import com.ort.app.domain.model.village.participant.VillageParticipant
import com.ort.app.fw.exception.WolfMansionBusinessException
import com.ort.dbflute.allcommon.CDef
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class VillageService(
    private val villageRepository: VillageRepository
) {
    // ------------------------
    // village
    fun findLatestVillageId(
        statusList: List<VillageStatus>
    ): Int = villageRepository.findLatestVillageId(statusList)

    fun findVillages(
        query: VillageQuery
    ): Villages = villageRepository.findVillages(query)

    fun findVillage(
        villageId: Int,
        excludeGone: Boolean = true
    ): Village? = villageRepository.findVillage(villageId, excludeGone)

    fun registerVillage(paramVillage: Village): Village = villageRepository.registerVillage(paramVillage)

    fun cancel(villageId: Int) = villageRepository.updateStatus(villageId, VillageStatus(CDef.VillageStatus.廃村))

    fun extendDay(villageId: Int, day: Int, datetime: LocalDateTime) =
        villageRepository.extendDay(villageId, day, datetime)

    fun shortenDay(villageId: Int, day: Int, datetime: LocalDateTime) =
        villageRepository.shortenDay(villageId, day, datetime)

    fun updateSetting(village: Village) = villageRepository.updateSetting(village)

    fun updateDummyCharaId(id: Int, charaId: Int) = villageRepository.updateDummyCharaId(id, charaId)

    // ------------------------
    // participant
    fun findVillageParticipant(
        villageId: Int,
        username: String,
        excludeGone: Boolean = true
    ): VillageParticipant? = villageRepository.findVillageParticipant(
        villageId = villageId,
        userName = username,
        excludeGone = excludeGone
    )

    fun findVillageParticipant(
        id: Int,
        excludeGone: Boolean = true
    ): VillageParticipant? = villageRepository.findVillageParticipant(id, excludeGone)

    fun participate(
        villageId: Int,
        playerId: Int,
        chara: Chara,
        charaName: String,
        charaShortName: String,
        firstRequestSkill: Skill,
        secondRequestSkill: Skill,
        isSpectator: Boolean
    ): VillageParticipant =
        villageRepository.participate(
            villageId = villageId,
            playerId = playerId,
            chara = chara,
            charaName = charaName,
            charaShortName = charaShortName,
            firstRequestSkill = firstRequestSkill,
            secondRequestSkill = secondRequestSkill,
            spectator = isSpectator
        )

    fun switchParticipate(villageId: Int, participantId: Int, isSpectator: Boolean) =
        villageRepository.switchParticipate(villageId, participantId, isSpectator)

    fun leave(participant: VillageParticipant) = villageRepository.leave(participant)

    fun changeParticipantName(participant: VillageParticipant, name: String, shortName: String) =
        villageRepository.changeParticipantName(participant, name, shortName)

    fun changeRequestSkill(myself: VillageParticipant, first: Skill, second: Skill) {
        villageRepository.changeRequestSkill(myself, first, second)
    }

    fun changeMemo(
        participant: VillageParticipant?,
        memo: String
    ) {
        participant ?: throw WolfMansionBusinessException("ログインしてください")
        villageRepository.changeMemo(participant, memo)
    }

    fun updateLastAccessDatetime(participant: VillageParticipant) =
        villageRepository.updateLastAccessDatetime(participant)

    fun updateDaychangeDifference(current: Village, changed: Village) {
        if (current.isSame(changed)) return
        villageRepository.updateDaychangeDifference(current, changed)
    }

    fun addIpAddress(participant: VillageParticipant, ipAddress: String) =
        villageRepository.addIpAddress(participant, ipAddress)

    fun registerNotification(participant: VillageParticipant) {

        villageRepository.registerNotificationSetting(participant)
    }
}