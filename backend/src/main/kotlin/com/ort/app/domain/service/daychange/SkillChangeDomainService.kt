package com.ort.app.domain.service.daychange

import com.ort.app.domain.model.daychange.Daychange
import com.ort.app.domain.model.message.Message
import com.ort.app.domain.model.skill.Skills
import com.ort.app.domain.model.skill.toModel
import com.ort.dbflute.allcommon.CDef
import org.springframework.stereotype.Service

@Service
class SkillChangeDomainService {

    fun notify(beforeDaychange: Daychange, currentDaychange: Daychange): Daychange {
        var messages = currentDaychange.messages.copy()
        val village = currentDaychange.village
        // 絶対人狼や勇者を知らせる
        Skills.openSkills.forEach { cdefSkill ->
            village.participants.filterBySkill(cdefSkill.toModel()).list.forEach {
                val yesterdaySkill = it.skillWhen(village.latestDay() - 1)
                if (yesterdaySkill?.toCdef() != cdefSkill) {
                    messages = messages.add(
                        Message.ofSystemMessage(
                            day = village.latestDay(),
                            message = "${it.name()}は${cdefSkill.toModel().name}のようだ。"
                        )
                    )
                }
            }
        }

        // 梟を知らせる
        val existOwlYesterday =
            beforeDaychange.village.participants.filterBySkill(CDef.Skill.梟.toModel()).list.isNotEmpty()
        val existOwlToday =
            currentDaychange.village.participants.filterBySkill(CDef.Skill.梟.toModel()).list.isNotEmpty()
        if (!existOwlYesterday && existOwlToday) {
            messages = messages.add(
                Message.ofSystemMessage(
                    day = village.latestDay(),
                    message = "この村には強力な聴力を持つ者がいるようだ。"
                )
            )
        }

        return currentDaychange.copy(messages = messages)
    }
}
