package com.ort.app.domain.service.daychange

import com.ort.app.domain.model.daychange.Daychange
import com.ort.app.domain.model.message.Message
import com.ort.app.domain.model.message.toModel
import com.ort.app.domain.model.skill.toModel
import com.ort.app.domain.model.village.Village
import com.ort.app.domain.model.village.participant.VillageParticipant
import com.ort.app.domain.service.ability.CohabitDomainService
import com.ort.app.domain.service.room.RoomDomainService
import com.ort.dbflute.allcommon.CDef
import org.springframework.stereotype.Service

@Service
class NormieDomainService(
    private val roomDomainService: RoomDomainService,
    private val cohabitDomainService: CohabitDomainService
) {
    fun normieBomb(daychange: Daychange): Daychange {
        var village = daychange.village.copy()
        var messages = daychange.messages.copy()
        village.participants
            .filterDeadDay(village.latestDay())
            .filterMiserable()
            .filterBySkill(CDef.Skill.リア充.toModel())
            .list.shuffled().forEach { normie ->
                messages = messages.add(createNormieBombMessage(village, normie))

                // 3*3の部屋の人が爆死する
                val targetRoomNumberList = roomDomainService.detectAroundRoomNumbers(normie.room!!, village.roomSize!!)
                val deadParticipants = mutableListOf<VillageParticipant>()
                village.participants
                    .filterAlive()
                    .list.filter { targetRoomNumberList.contains(it.room!!.number) }
                    .forEach { target ->
                        // 同棲で不在でなければ死亡
                        if (!cohabitDomainService.isAbsence(daychange, target)) {
                            village = village.bombKillParticipant(target.id)
                            deadParticipants.add(target)
                        }
                        // 同棲で部屋に同棲者が来ていたら同棲者も死亡
                        if (cohabitDomainService.isCohabiting(daychange, target)) {
                            val cohabitor = target.getTargetCohabitor(village)!!
                            village = village.bombKillParticipant(cohabitor.id)
                            deadParticipants.add(cohabitor)
                        }
                    }
                if (deadParticipants.isNotEmpty()) {
                    messages = messages.add(createBombedMessage(village, deadParticipants))
                }
            }
        return daychange.copy(village = village, messages = messages)
    }

    private fun createNormieBombMessage(village: Village, normie: VillageParticipant): Message {
        return Message.ofSystemMessage(
            day = village.latestDay(),
            message = "リア充の${normie.name()}は、爆発した。"
        )
    }

    private fun createBombedMessage(
        village: Village,
        deadParticipants: MutableList<VillageParticipant>
    ): Message {
        val message = deadParticipants.joinToString(
            prefix = "リア充の爆発により、",
            separator = "と",
            postfix = "が爆死した。"
        ) { it.name() }
        return Message.ofSystemMessage(
            day = village.latestDay(),
            message = message,
            messageType = CDef.MessageType.非公開システムメッセージ.toModel()
        )
    }
}
