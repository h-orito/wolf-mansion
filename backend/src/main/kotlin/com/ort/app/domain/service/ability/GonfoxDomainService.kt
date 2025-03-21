package com.ort.app.domain.service.ability

import com.ort.app.domain.model.daychange.Daychange
import com.ort.app.domain.model.message.Message
import com.ort.app.domain.model.skill.toModel
import com.ort.dbflute.allcommon.CDef
import org.springframework.stereotype.Service

@Service
class GonfoxDomainService {

    fun addGonfoxMessage(daychange: Daychange): Daychange {
        val village = daychange.village
        var messages = daychange.messages.copy()

        // ごんが生存していたら木の実を置く
        val aliveGonCount = village.participants.filterAlive().filterBySkill(CDef.Skill.ごん.toModel()).list.size
        if (aliveGonCount > 0) {
            messages = messages.add(
                Message.ofSystemMessage(
                    day = village.latestDay(),
                    message = "みんなの部屋の前に、栗が${aliveGonCount}個ずつ置かれていた。"
                )
            )
        }

        // ごんが一人でも死亡していたらメッセージ表示
        if (village.participants.filterDeadDay(village.latestDay())
                .filterBySkill(CDef.Skill.ごん.toModel()).list.isNotEmpty()
        ) {
            messages = messages.add(
                Message.ofSystemMessage(
                    day = village.latestDay(),
                    message = "ごん、お前だったのか。いつも栗をくれたのは。"
                )
            )
        }
        return daychange.copy(messages = messages)
    }
}
