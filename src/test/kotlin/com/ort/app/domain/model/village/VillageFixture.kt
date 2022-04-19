package com.ort.app.domain.model.village

import com.ort.app.domain.model.skill.RequestSkill
import com.ort.app.domain.model.skill.Skill
import com.ort.app.domain.model.village.participant.VillageParticipant
import com.ort.app.domain.model.village.participant.VillageParticipantName
import com.ort.app.domain.model.village.participant.VillageParticipantStatus
import com.ort.app.domain.model.village.participant.VillageParticipants
import com.ort.app.domain.model.village.participant.dead.Dead
import com.ort.app.domain.model.village.participant.dead.DeadHistories
import com.ort.app.domain.model.village.room.RoomSize
import com.ort.app.domain.model.village.setting.*
import com.ort.dbflute.allcommon.CDef
import java.time.LocalDateTime

fun createPrologueVillage(): Village = Village(
    id = 1,
    name = "village name",
    createPlayerName = "creator name",
    createDatetime = LocalDateTime.now(),
    status = CDef.VillageStatus.募集中.toModel(),
    roomSize = null,
    participants = VillageParticipants(count = 0, list = emptyList()),
    spectators = VillageParticipants(count = 0, list = emptyList()),
    days = VillageDays(list = listOf(createVillageDay(day = 0))),
    setting = createVillageSetting(),
    epilogueDay = null,
    winCamp = null
)

fun createDay1Village(
    organize: String = "村狼狼狼魔狐賢導狩共共霊霊霊霊霊霊"
): Village = Village(
    id = 1,
    name = "village name",
    createPlayerName = "creator name",
    createDatetime = LocalDateTime.now(),
    status = CDef.VillageStatus.進行中.toModel(),
    roomSize = RoomSize.invoke(organize.length),
    participants = VillageParticipants(
        count = organize.length,
        list = organize.toCharArray().mapIndexed { index, shortname ->
            createVillageParticipant(
                skill = Skill.byShortName(shortname.toString())!!,
                id = index + 2
            )
        }
    ),
    spectators = VillageParticipants(count = 0, list = emptyList()),
    days = VillageDays(
        list = listOf(
            createVillageDay(day = 0),
            createVillageDay(day = 1)
        )
    ),
    setting = createVillageSetting(),
    epilogueDay = null,
    winCamp = null
)

fun createVillageDay(day: Int): VillageDay = VillageDay(
    day = day,
    dayChangeDatetime = LocalDateTime.now().plusDays(1L)
)

fun createVillageSetting(
    organize: String = "村狼狼狼魔狐賢導狩共共霊霊霊霊霊霊"
): VillageSetting = VillageSetting(
    chara = VillageCharaSetting(
        isOriginalCharachip = false,
        dummyCharaId = 1,
        charachipIds = listOf(1),
    ),
    personMin = 8,
    personMax = 99,
    startDatetime = LocalDateTime.now(),
    dayChangeIntervalSeconds = 24 * 60 * 60,
    rule = createVillageRule(),
    joinPassword = null,
    organize = createVillageOrganize(organize),
    sayRestriction = SayRestriction(
        normalSayRestriction = emptyList(),
        skillSayRestriction = emptyList()
    ),
    tags = VillageTags(list = emptyList())
)

fun createVillageRule(): VillageRule = VillageRule(
    isOpenVote = false,
    isPossibleSkillRequest = true,
    isAvailableSpectate = true,
    isAvailableSameWolfAttack = false,
    isOpenSkillInGrave = false,
    isVisibleGraveSpectateMessage = false,
    isAvailableSuddenlyDeath = false,
    isAvailableCommit = true,
    isAvailableGuardSameTarget = false,
    isAvailableSecretSay = false,
    isAvailableAction = false,
    isRandomOrganization = false,
    isReincarnationSkillAll = false
)

fun createVillageOrganize(organize: String): VillageOrganize = VillageOrganize(
    fixedOrganization = organize,
    randomOrganization = VillageRandomOrganize(
        skillAllocation = emptyList(),
        campAllocation = emptyList()
    )
)

fun createVillageParticipant(skill: Skill, id: Int): VillageParticipant = VillageParticipant(
    id = id,
    charaName = VillageParticipantName(name = "name${id}", shortName = "shortname${id}"),
    playerId = id,
    charaId = id,
    skill = null,
    requestSkill = RequestSkill(first = skill, second = skill),
    room = null,
    status = VillageParticipantStatus(emptyList(), emptyList(), emptyList(), emptyList(), emptyList()),
    dead = Dead(isDead = false, deadDay = null, reason = null, histories = DeadHistories(emptyList())),
    isSpectator = false,
    isGone = false,
    isWin = null,
    camp = null,
    lastAccessDatetime = LocalDateTime.now(),
    memo = null,
    ipAddresses = emptyList()
).assignSkill(skill, 1).assignRoom(roomNumber = id, day = 1)
