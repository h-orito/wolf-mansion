package com.ort.app.infrastructure.datasource.village

import com.ort.app.domain.model.camp.Camp
import com.ort.app.domain.model.chara.Chara
import com.ort.app.domain.model.skill.*
import com.ort.app.domain.model.skill.Skill
import com.ort.app.domain.model.village.participant.*
import com.ort.app.domain.model.village.participant.dead.Dead
import com.ort.app.domain.model.village.participant.dead.DeadHistories
import com.ort.app.domain.model.village.participant.dead.DeadHistory
import com.ort.app.domain.model.village.participant.dead.DeadReason
import com.ort.app.domain.model.village.room.Room
import com.ort.app.domain.model.village.room.RoomHistories
import com.ort.app.domain.model.village.room.RoomHistory
import com.ort.dbflute.allcommon.CDef
import com.ort.dbflute.bsbhv.loader.LoaderOfVillagePlayer
import com.ort.dbflute.exbhv.*
import com.ort.dbflute.exentity.*
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
class VillagePlayerDataSource(
    private val villagePlayerBhv: VillagePlayerBhv,
    private val villagePlayerStatusBhv: VillagePlayerStatusBhv,
    private val villagePlayerRoomHistoryBhv: VillagePlayerRoomHistoryBhv,
    private val villagePlayerDeadHistoryBhv: VillagePlayerDeadHistoryBhv,
    private val villagePlayerSkillHistoryBhv: VillagePlayerSkillHistoryBhv,
    private val villagePlayerAccessInfoBhv: VillagePlayerAccessInfoBhv,
    private val villagePlayerNotificationBhv: VillagePlayerNotificationBhv
) {
    fun findVillageParticipant(
        id: Int,
        excludeGone: Boolean
    ): VillageParticipant? {
        val optVillagePlayer = villagePlayerBhv.selectEntity {
            it.setupSelect_Player()
            it.setupSelect_VillagePlayerNotificationAsOne()
            it.query().setVillagePlayerId_Equal(id)
            if (excludeGone) it.query().setIsGone_Equal_False()
        }
        if (!optVillagePlayer.isPresent) return null
        val villagePlayer = optVillagePlayer.get()
        villagePlayerBhv.load(villagePlayer) { withNestedVillagePlayer(it) }
        return mapVillageParticipant(villagePlayer)
    }

    fun findVillageParticipant(
        villageId: Int,
        userName: String,
        excludeGone: Boolean
    ): VillageParticipant? {
        val optVillagePlayer = villagePlayerBhv.selectEntity {
            it.setupSelect_Player()
            it.setupSelect_VillagePlayerNotificationAsOne()
            it.query().setVillageId_Equal(villageId)
            it.query().queryPlayer().setPlayerName_Equal(userName)
            if (excludeGone) it.query().setIsGone_Equal_False()
        }
        if (!optVillagePlayer.isPresent) return null
        val villagePlayer = optVillagePlayer.get()
        villagePlayerBhv.load(villagePlayer) { withNestedVillagePlayer(it) }
        return mapVillageParticipant(villagePlayer)
    }

    fun withNestedVillagePlayer(loader: LoaderOfVillagePlayer) {
        loader.loadVillagePlayerDeadHistory {
            it.query().addOrderBy_Day_Asc()
            it.query().addOrderBy_VillagePlayerDeadHistoryId_Asc()
        }
        loader.loadVillagePlayerRoomHistory {
            it.query().addOrderBy_Day_Asc()
            it.query().addOrderBy_VillagePlayerRoomHistoryId_Asc()
        }
        loader.loadVillagePlayerSkillHistory {
            it.query().addOrderBy_Day_Asc()
            it.query().addOrderBy_VillagePlayerSkillHistoryId_Asc()
        }
        loader.loadVillagePlayerStatusByVillagePlayerId { }
        loader.loadVillagePlayerStatusByToVillagePlayerId { }
        loader.loadVillagePlayerAccessInfo { }
    }

    fun insertVillagePlayer(
        villageId: Int,
        playerId: Int,
        chara: Chara,
        spectator: Boolean,
        firstRequestSkill: Skill,
        secondRequestSkill: Skill
    ): VillagePlayer {
        val vPlayer = VillagePlayer()
        vPlayer.villageId = villageId
        vPlayer.playerId = playerId
        vPlayer.charaId = chara.id
        vPlayer.setIsDead_False()
        vPlayer.isSpectator = spectator
        vPlayer.setIsGone_False()
        vPlayer.requestSkillCodeAsSkill = firstRequestSkill.toCdef()
        vPlayer.secondRequestSkillCodeAsSkill = secondRequestSkill.toCdef()
        vPlayer.lastAccessDatetime = LocalDateTime.now()
        vPlayer.charaName = chara.name
        vPlayer.charaShortName = chara.shortName
        villagePlayerBhv.insert(vPlayer)
        return vPlayer
    }

    fun insertVillagePlayerAccessInfo(villagePlayerId: Int, ipAddress: String) {
        if (villagePlayerAccessInfoBhv.selectByUniqueOf(villagePlayerId, ipAddress).isPresent) return
        val access = VillagePlayerAccessInfo()
        access.villagePlayerId = villagePlayerId
        access.ipAddress = ipAddress
        villagePlayerAccessInfoBhv.insert(access)
    }

    fun leave(participant: VillageParticipant) {
        val vPlayer = VillagePlayer()
        vPlayer.isGone = true
        update(participant.id, vPlayer)
    }

    fun changeParticipantName(
        participant: VillageParticipant,
        name: String,
        shortName: String
    ) {
        val vPlayer = VillagePlayer()
        vPlayer.charaName = name
        vPlayer.charaShortName = shortName
        update(participant.id, vPlayer)
    }

    fun changeRequestSkill(participant: VillageParticipant, first: Skill, second: Skill) {
        val vPlayer = VillagePlayer()
        vPlayer.requestSkillCodeAsSkill = first.toCdef()
        vPlayer.secondRequestSkillCodeAsSkill = second.toCdef()
        update(participant.id, vPlayer)
    }

    fun changeMemo(participant: VillageParticipant, memo: String) {
        val vPlayer = VillagePlayer()
        vPlayer.memo = memo
        update(participant.id, vPlayer)
    }

    fun updateLastAccessDatetime(participant: VillageParticipant) {
        val vPlayer = VillagePlayer()
        vPlayer.lastAccessDatetime = LocalDateTime.now()
        update(participant.id, vPlayer)
    }

    fun updateDaychangeDifference(current: VillageParticipants, changed: VillageParticipants) {
        if (current.isSame(changed)) return
        // 基本的に追加削除はないはずなので更新のみ行う
        changed.list.forEach { changedParticipant ->
            val currentParticipant = current.member(changedParticipant.id)
            if (!currentParticipant.isSame(changedParticipant)) {
                updateVillagePlayerDaychangeDifference(currentParticipant, changedParticipant)
                updateVillagePlayerStatusDaychangeDifference(
                    changedParticipant.id,
                    currentParticipant.status,
                    changedParticipant.status
                )
                updateRoomHistoryDaychangeDifference(
                    changedParticipant.id,
                    currentParticipant.room?.histories,
                    changedParticipant.room?.histories
                )
                updateDeadHistoryDaychangeDifference(
                    changedParticipant.id,
                    currentParticipant.dead.histories,
                    changedParticipant.dead.histories
                )
                updateSkillHistoryDaychangeDifference(
                    changedParticipant.id,
                    currentParticipant.skill?.histories,
                    changedParticipant.skill?.histories
                )
            }
        }
    }

    private fun updateVillagePlayerDaychangeDifference(
        current: VillageParticipant,
        changed: VillageParticipant
    ) {
        if (current.skill?.code == changed.skill?.code
            && current.requestSkill?.first?.code == changed.requestSkill?.first?.code
            && current.requestSkill?.second?.code == changed.requestSkill?.second?.code
            && current.room?.number == changed.room?.number
            && current.dead.isDead == changed.dead.isDead
            && current.dead.deadDay == changed.dead.deadDay
            && current.isGone == changed.isGone
            && current.isWin == changed.isWin
            && current.camp?.code == changed.camp?.code
        ) return
        val vPlayer = VillagePlayer()
        vPlayer.skillCodeAsSkill = changed.skill?.toCdef()
        vPlayer.requestSkillCodeAsSkill = changed.requestSkill?.first?.toCdef()
        vPlayer.secondRequestSkillCodeAsSkill = changed.requestSkill?.second?.toCdef()
        vPlayer.roomNumber = changed.room?.number
        vPlayer.isDead = changed.isDead()
        vPlayer.deadReasonCodeAsDeadReason = changed.dead.reason?.toCdef()
        vPlayer.deadDay = changed.dead.deadDay
        vPlayer.isGone = changed.isGone
        vPlayer.isWin = changed.isWin
        vPlayer.campCode = changed.camp?.code
        update(changed.id, vPlayer)
    }

    private fun update(id: Int, villagePlayer: VillagePlayer) =
        villagePlayerBhv.queryUpdate(villagePlayer) { it.query().setVillagePlayerId_Equal(id) }

    private fun updateVillagePlayerStatusDaychangeDifference(
        participantId: Int,
        current: VillageParticipantStatus,
        changed: VillageParticipantStatus
    ) {
        // 削除
        current.loverIdList.filterNot { changed.loverIdList.contains(it) }
            .forEach { deleteVillagePlayerStatus(participantId, it, CDef.VillagePlayerStatusType.後追い) }
        current.foxPossessionedIdList.filterNot { changed.foxPossessionedIdList.contains(it) }
            .forEach { deleteVillagePlayerStatus(it, participantId, CDef.VillagePlayerStatusType.狐憑き) }
        current.insanedIdList.filterNot { changed.insanedIdList.contains(it) }
            .forEach { deleteVillagePlayerStatus(it, participantId, CDef.VillagePlayerStatusType.狂気) }
        current.persuadedIdList.filterNot { changed.persuadedIdList.contains(it) }
            .forEach { deleteVillagePlayerStatus(it, participantId, CDef.VillagePlayerStatusType.信念) }
        current.insuranceIdList.filterNot { changed.insuranceIdList.contains(it) }
            .forEach { deleteVillagePlayerStatus(it, participantId, CDef.VillagePlayerStatusType.保険) }
        current.disrespectfulList.filterNot { changed.disrespectfulList.contains(it) }
            .forEach { deleteVillagePlayerStatus(it, participantId, CDef.VillagePlayerStatusType.不敬) }

        // 追加
        changed.loverIdList.filterNot { current.loverIdList.contains(it) }
            .forEach { insertVillagePlayerStatus(participantId, it, CDef.VillagePlayerStatusType.後追い) }
        changed.foxPossessionedIdList.filterNot { current.foxPossessionedIdList.contains(it) }
            .forEach { insertVillagePlayerStatus(it, participantId, CDef.VillagePlayerStatusType.狐憑き) }
        changed.insanedIdList.filterNot { current.insanedIdList.contains(it) }
            .forEach { insertVillagePlayerStatus(it, participantId, CDef.VillagePlayerStatusType.狂気) }
        changed.persuadedIdList.filterNot { current.persuadedIdList.contains(it) }
            .forEach { insertVillagePlayerStatus(it, participantId, CDef.VillagePlayerStatusType.信念) }
        changed.insuranceIdList.filterNot { current.insuranceIdList.contains(it) }
            .forEach { insertVillagePlayerStatus(it, participantId, CDef.VillagePlayerStatusType.保険) }
        changed.disrespectfulList.filterNot { current.disrespectfulList.contains(it) }
            .forEach { insertVillagePlayerStatus(it, participantId, CDef.VillagePlayerStatusType.不敬) }
    }

    private fun insertVillagePlayerStatus(from: Int, to: Int, type: CDef.VillagePlayerStatusType) {
        val status = VillagePlayerStatus()
        status.villagePlayerId = from
        status.toVillagePlayerId = to
        status.villagePlayerStatusCodeAsVillagePlayerStatusType = type
        villagePlayerStatusBhv.insert(status)
    }

    private fun deleteVillagePlayerStatus(from: Int, to: Int, type: CDef.VillagePlayerStatusType) {
        villagePlayerStatusBhv.queryDelete {
            it.query().setVillagePlayerId_Equal(from)
            it.query().setToVillagePlayerId_Equal(to)
            it.query().setVillagePlayerStatusCode_Equal_AsVillagePlayerStatusType(type)
        }
    }

    private fun updateRoomHistoryDaychangeDifference(
        participantId: Int,
        current: RoomHistories?,
        changed: RoomHistories?
    ) {
        val currentList = current?.list ?: emptyList()
        val changedList = changed?.list ?: emptyList()
        changedList.drop(currentList.size).forEach { insertRoomHistory(participantId, it) }
    }

    private fun insertRoomHistory(participantId: Int, history: RoomHistory) {
        val h = VillagePlayerRoomHistory()
        h.villagePlayerId = participantId
        h.day = history.day
        h.roomNumber = history.number
        villagePlayerRoomHistoryBhv.insert(h)
    }

    private fun updateDeadHistoryDaychangeDifference(
        participantId: Int,
        current: DeadHistories,
        changed: DeadHistories
    ) {
        changed.list.drop(current.list.size).forEach { insertDeadHistory(participantId, it) }
    }

    private fun insertDeadHistory(participantId: Int, history: DeadHistory) {
        val h = VillagePlayerDeadHistory()
        h.villagePlayerId = participantId
        h.day = history.day
        h.isDead = history.isDead
        h.deadReasonCodeAsDeadReason = history.reason?.toCdef()
        villagePlayerDeadHistoryBhv.insert(h)
    }

    private fun updateSkillHistoryDaychangeDifference(
        participantId: Int,
        current: SkillHistories?,
        changed: SkillHistories?
    ) {
        val currentList = current?.list ?: emptyList()
        val changedList = changed?.list ?: emptyList()
        changedList.drop(currentList.size).forEach { insertSkillHistory(participantId, it) }
    }

    private fun insertSkillHistory(id: Int, history: SkillHistory) {
        val h = VillagePlayerSkillHistory()
        h.villagePlayerId = id
        h.day = history.day
        h.skillCodeAsSkill = history.skill.toCdef()
        villagePlayerSkillHistoryBhv.insert(h)
    }

    fun updateVillagePlayerNotification(
        participantId: Int,
        notification: VillageParticipantNotificationCondition
    ) {
        villagePlayerNotificationBhv.queryDelete { it.query().setVillagePlayerId_Equal(participantId) }
        val n = VillagePlayerNotification()
        n.villagePlayerId = participantId
        n.discordWebhookUrl = notification.discordWebhookUrl
        n.villageStart = notification.village.start
        n.villageDaychange = notification.village.dayChange
        n.villageEpilogue = notification.village.epilogue
        n.receiveSecretSay = notification.message.secretSay
        n.receiveAbilitySay = notification.message.abilitySay
        n.receiveAnchorSay = notification.message.anchor
        n.keyword = notification.message.keywords.let {
            if (it.isEmpty()) null
            else it.joinToString(separator = " ")
        }
        villagePlayerNotificationBhv.insert(n)
    }

    fun mapSimpleVillageParticipants(villagePlayerList: List<VillagePlayer>): VillageParticipants {
        val participantList = villagePlayerList.filterNot { it.isSpectator }
        return VillageParticipants(
            count = participantList.size,
            list = participantList.map { mapSimpleVillageParticipant(it) }
        )
    }

    fun mapVillageParticipants(villagePlayerList: List<VillagePlayer>): VillageParticipants {
        val participantList = villagePlayerList.filterNot { it.isSpectator }
        return VillageParticipants(
            count = participantList.size,
            list = participantList.map { mapVillageParticipant(it) }
        )
    }

    fun mapSimpleVillageSpectators(villagePlayerList: List<VillagePlayer>): VillageParticipants {
        val spectatorList = villagePlayerList.filter { it.isSpectator }
        return VillageParticipants(
            count = spectatorList.size,
            list = spectatorList.map { mapSimpleVillageParticipant(it) }
        )
    }

    fun mapVillageSpectators(villagePlayerList: List<VillagePlayer>): VillageParticipants {
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
                foxPossessionedIdList = emptyList(),
                insanedIdList = emptyList(),
                persuadedIdList = emptyList(),
                insuranceIdList = emptyList(),
                disrespectfulList = emptyList()
            ),
            dead = mapSimpleDead(villagePlayer),
            isSpectator = villagePlayer.isSpectator,
            isGone = villagePlayer.isGone,
            isWin = villagePlayer.isWin,
            camp = if (villagePlayer.campCode.isNullOrBlank()) null else Camp(CDef.Camp.codeOf(villagePlayer.campCode)),
            lastAccessDatetime = villagePlayer.lastAccessDatetime,
            memo = villagePlayer.memo,
            ipAddresses = villagePlayer.villagePlayerAccessInfoList.map { it.ipAddress },
            notification = null
        )
    }

    fun mapVillageParticipant(villagePlayer: VillagePlayer): VillageParticipant {
        return VillageParticipant(
            id = villagePlayer.villagePlayerId,
            charaName = VillageParticipantName(
                name = villagePlayer.charaName,
                shortName = villagePlayer.charaShortName
            ),
            playerId = villagePlayer.playerId,
            charaId = villagePlayer.charaId,
            skill = if (villagePlayer.skillCode.isNullOrBlank()) null else mapSkill(villagePlayer),
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
            memo = villagePlayer.memo,
            ipAddresses = villagePlayer.villagePlayerAccessInfoList.map { it.ipAddress },
            notification = mapNotification(villagePlayer)
        )
    }

    private fun mapNotification(villagePlayer: VillagePlayer): VillageParticipantNotificationCondition? {
        return villagePlayer.villagePlayerNotificationAsOne.map {
            VillageParticipantNotificationCondition(
                discordWebhookUrl = it.discordWebhookUrl,
                village = VillageParticipantNotificationCondition.VillageCondition(
                    start = it.villageStart,
                    dayChange = it.villageDaychange,
                    epilogue = it.villageEpilogue
                ),
                message = VillageParticipantNotificationCondition.MessageCondition(
                    secretSay = it.receiveSecretSay,
                    abilitySay = it.receiveAbilitySay,
                    anchor = it.receiveAnchorSay,
                    keywords = it.keyword?.split(" ") ?: emptyList()
                )
            )
        }.orElse(null)
    }

    private fun mapSkill(villagePlayer: VillagePlayer): Skill {
        return villagePlayer.skillCodeAsSkill.toModel().copy(
            histories = SkillHistories(
                list = villagePlayer.villagePlayerSkillHistoryList.map {
                    SkillHistory(
                        skill = it.skillCodeAsSkill.toModel(),
                        day = it.day
                    )
                }
            )
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
            foxPossessionedIdList = toStatusList.filter { it.isVillagePlayerStatusCode狐憑き }
                .map { it.villagePlayerId },
            insanedIdList = toStatusList.filter { it.isVillagePlayerStatusCode狂気 }.map { it.villagePlayerId },
            persuadedIdList = toStatusList.filter { it.isVillagePlayerStatusCode信念 }.map { it.villagePlayerId },
            insuranceIdList = toStatusList.filter { it.isVillagePlayerStatusCode保険 }.map { it.villagePlayerId },
            disrespectfulList = toStatusList.filter { it.isVillagePlayerStatusCode不敬 }.map { it.villagePlayerId },
        )
    }
}