package com.ort.app.domain.service.ability

import com.ort.app.domain.model.daychange.Daychange
import com.ort.app.domain.model.message.Message
import com.ort.app.domain.model.village.Village
import com.ort.dbflute.allcommon.CDef
import org.springframework.stereotype.Service

@Service
class BakeryDomainService {

    fun addBakeryMessage(daychange: Daychange): Daychange {
        val village = daychange.village
        var messages = daychange.messages.copy()

        // 生存しているパン屋がいる
        val text = when {
            existsAliveBakery(village) -> "パン屋がおいしいパンを焼いてくれたそうです。"
            isEradicatedBakeryToday(village) -> "今日からはもうおいしいパンが食べられません。"
            else -> return daychange
        }
        val message = Message.ofSystemMessage(
            day = village.latestDay(),
            message = text
        )
        return daychange.copy(messages = messages.add(message))
    }

    private fun existsAliveBakery(village: Village): Boolean =
        village.participants.filterAlive().list.any {
            val skill = it.skill!!.toCdef()
            skill == CDef.Skill.パン屋 || skill == CDef.Skill.闇パン屋
        }

    private fun isEradicatedBakeryToday(village: Village): Boolean =
        !existsAliveBakery(village) && village.participants
            .filterDeadDay(village.latestDay()).list.any {
                val skill = it.skill!!.toCdef()
                skill == CDef.Skill.パン屋 || skill == CDef.Skill.闇パン屋
            }
}
