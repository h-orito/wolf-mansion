package com.ort.app.domain.service.daychange

import com.ort.app.domain.model.daychange.Daychange
import com.ort.app.domain.model.message.Message
import com.ort.app.domain.model.message.toModel
import com.ort.app.domain.model.skill.toModel
import com.ort.app.domain.model.village.Village
import com.ort.app.domain.model.village.participant.VillageParticipant
import com.ort.app.domain.service.FootstepDomainService
import com.ort.app.domain.service.MessageDomainService
import com.ort.dbflute.allcommon.CDef
import org.springframework.stereotype.Service

@Service
class PushpinDomainService(
    private val messageDomainService: MessageDomainService,
    private val footstepDomainService: FootstepDomainService
) {
    fun pushpin(daychange: Daychange): Daychange {
        var village = daychange.village.copy()
        var messages = daychange.messages.copy()
        village.participants.filterAlive().filterBySkill(CDef.Skill.画鋲.toModel()).list.forEach { pushpin ->
            // 通過すると死亡
            val passedParticipants = footstepDomainService.findPassedParticipants(
                village = village,
                footsteps = daychange.footsteps,
                day = village.latestDay() - 1,
                roomNumber = pushpin.room!!.number
            )
            passedParticipants.filter { it.isAlive() }.forEach { passed ->
                village = village.zakoKilledParticipant(passed.id)
                messages = messages.add(createPinnedMessage(village, pushpin, passed))
            }
        }

        return daychange.copy(village = village, messages = messages)
    }

    private fun createPinnedMessage(
        village: Village,
        pushpin: VillageParticipant,
        passed: VillageParticipant
    ): Message {
        return messageDomainService.createPrivateAbilityMessage(
            village = village,
            myself = pushpin,
            text = "${passed.name()}は、画鋲を踏んでしまい、即死した。",
            messageType = CDef.MessageType.能力行使メッセージ.toModel()
        )
    }
}
