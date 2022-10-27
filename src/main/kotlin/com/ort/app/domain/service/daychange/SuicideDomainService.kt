package com.ort.app.domain.service.daychange

import com.ort.app.domain.model.daychange.Daychange
import com.ort.app.domain.model.message.Message
import com.ort.app.domain.model.skill.toModel
import com.ort.app.domain.model.village.Village
import com.ort.app.domain.model.village.participant.VillageParticipant
import com.ort.app.domain.service.room.RoomDomainService
import com.ort.dbflute.allcommon.CDef
import org.springframework.stereotype.Service

@Service
class SuicideDomainService(
    private val roomDomainService: RoomDomainService
) {

    fun suicide(daychange: Daychange): Daychange {
        var village = daychange.village.copy()
        var messages = daychange.messages.copy()

        while (existsSuicideTarget(village)) {
            val target = findSuicideTarget(village)!!

            val loverSuicideTarget = findLoverSuicideTarget(village)
            messages = if (loverSuicideTarget != null) {
                val lover = target.getTargetLovers(village).filterDead().list.shuffled().first()
                messages.add(createLoverSuicideMessage(village, target, lover))
            } else if (findWallPunchSuicideTarget(village) != null) {
                messages.add(createWallPuncherSuicideMessage(village, target))
            } else if (findAnpanmanSuicideTarget(village) != null) {
                messages.add(createAnpanmanSuicideMessage(village, target))
            } else {
                messages.add(createImmoralSuicideMessage(village, target))
            }
            village = village.suicideParticipant(target.id)
        }

        return daychange.copy(village = village, messages = messages)
    }

    private fun findSuicideTarget(village: Village): VillageParticipant? {
        // 恋絆
        findLoverSuicideTarget(village)?.let { return it }
        // 壁殴り代行
        findWallPunchSuicideTarget(village)?.let { return it }
        // 餡麺麭者
        findAnpanmanSuicideTarget(village)?.let { return it }
        // 背徳者
        return findImmoralSuicideTarget(village)
    }

    private fun existsSuicideTarget(village: Village): Boolean = findSuicideTarget(village) != null

    private fun findLoverSuicideTarget(village: Village): VillageParticipant? {
        return village.participants.filterAlive().list // 自分は生きていて
            .filter { it.status.hasLover() } // 恋人がいて
            .firstOrNull { participant ->
                // 恋人のいずれかが死亡している
                participant.getTargetLovers(village).list.any { it.isDead() }
            }
    }

    private fun findWallPunchSuicideTarget(village: Village): VillageParticipant? {
        return village.participants
            .filterAlive() // 自分は生きていて
            .filterBySkill(CDef.Skill.壁殴り代行.toModel()).list // 壁殴り代行で
            .firstOrNull { participant ->
                // 四方の部屋の人が全員死亡していたら後追い対象
                val wasdRoomNumbers = roomDomainService.detectWasdRoomNumbers(participant.room!!, village.roomSize!!)
                village.participants.list.filter { wasdRoomNumbers.contains(it.room!!.number) }.none { it.isAlive() }
            }
    }

    private fun findAnpanmanSuicideTarget(village: Village): VillageParticipant? {
        // パン屋が全員死亡していたら後追い対象
        if (village.participants.filterAlive().filterBySkill(CDef.Skill.パン屋.toModel()).list.isNotEmpty()) return null

        return village.participants
            .filterAlive() // 自分は生きていて
            .filterBySkill(CDef.Skill.餡麺麭者.toModel()).list // 餡麺麭者
            .firstOrNull()
    }

    private fun findImmoralSuicideTarget(village: Village): VillageParticipant? {
        // 妖狐系が生存していたら後追いしない
        if (village.participants.filterAlive().list.any { it.skill!!.isFoxCount() }) {
            return null
        }
        // 生存している妖狐陣営(＝恋絆のついていない背徳者、狐憑き)が後追い対象
        return village.participants
            .filterAlive().list
            .firstOrNull { it.camp!!.isFoxs() }
    }

    private fun createLoverSuicideMessage(
        village: Village,
        target: VillageParticipant,
        lover: VillageParticipant
    ): Message {
        return Message.ofSystemMessage(
            day = village.latestDay(),
            message = "${target.name()}は、絆に引きずられるように${lover.name()}の後を追った。"
        )
    }

    private fun createWallPuncherSuicideMessage(village: Village, target: VillageParticipant): Message {
        return Message.ofSystemMessage(
            day = village.latestDay(),
            message = "${target.name()}は、壁殴りを代行する部屋がなくなってしまい、孤独死した。"
        )
    }

    private fun createAnpanmanSuicideMessage(village: Village, target: VillageParticipant): Message {
        return Message.ofSystemMessage(
            day = village.latestDay(),
            message = "${target.name()}は、新しい顔がもらえなくなってしまい、顔がふやけて衰弱死した。"
        )
    }

    private fun createImmoralSuicideMessage(village: Village, target: VillageParticipant): Message {
        return Message.ofSystemMessage(
            day = village.latestDay(),
            message = "${target.name()}は、妖狐の後を追い、いなくなってしまった。"
        )
    }
}
