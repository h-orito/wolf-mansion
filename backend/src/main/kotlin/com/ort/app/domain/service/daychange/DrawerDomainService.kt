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
class DrawerDomainService(
    private val messageDomainService: MessageDomainService,
    private val footstepDomainService: FootstepDomainService
) {
    fun littleFinger(daychange: Daychange): Daychange {
        var village = daychange.village.copy()
        var messages = daychange.messages.copy()
        village.participants.filterAlive().filterBySkill(CDef.Skill.箪笥.toModel()).list.forEach { drawer ->
            // 通過すると死亡
            val passedParticipants = footstepDomainService.findPassedParticipants(
                village = village,
                footsteps = daychange.footsteps,
                day = village.latestDay() - 1,
                roomNumber = drawer.room!!.number
            )
            passedParticipants.filter { it.isAlive() }.forEach { passed ->
                village = village.zakoKilledParticipant(passed.id)
                messages = messages.add(createHitMessage(village, passed))
            }
        }

        return daychange.copy(village = village, messages = messages)
    }

    private fun createHitMessage(
        village: Village,
        passed: VillageParticipant
    ): Message {
        return messageDomainService.createPrivateAbilityMessage(
            village = village,
            myself = passed,
            text = "${passed.name()}は、箪笥に小指をぶつけ、即死した。",
            messageType = CDef.MessageType.能力行使メッセージ.toModel()
        )
    }
}
