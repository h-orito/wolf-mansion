package com.ort.app.domain.service.skill

import com.ort.app.domain.model.daychange.Daychange
import com.ort.app.domain.model.message.Message
import com.ort.app.domain.model.message.Messages
import com.ort.app.domain.model.skill.Skill
import com.ort.app.domain.model.skill.Skills
import com.ort.app.domain.model.skill.toModel
import com.ort.app.domain.model.village.Village
import com.ort.app.domain.model.village.participant.VillageParticipant
import com.ort.app.domain.service.MessageDomainService
import com.ort.dbflute.allcommon.CDef
import org.springframework.stereotype.Service

@Service
class SkillAssignDomainService(
    private val messageDomainService: MessageDomainService
) {

    fun assign(
        daychange: Daychange
    ): Daychange {
        val skillPersonCountMap = daychange.village.mapToSkillCount()

        // 人数により役職構成が切り替わった場合に役職希望を自動で変更する
        var (village, messages) = updateSkillRequestByOrganization(
            daychange.village,
            daychange.messages,
            skillPersonCountMap
        )

        // ダミー配役
        village = village.assignParticipantSkill(village.dummyParticipant().id, Skill(CDef.Skill.村人))
        // 第1希望で役職希望した人を割り当て
        village = assignFirstSpecifyRequest(village, skillPersonCountMap)
        // 第1希望で範囲指定希望した人を割り当て
        village = assignFirstRangeRequest(village, skillPersonCountMap)
        // 第2希望で役職希望した人を割り当て
        village = assignSecondSpecifyRequest(village, skillPersonCountMap)
        // 第2希望で範囲指定希望した人を割り当て
        village = assignSecondRangeRequest(village, skillPersonCountMap)
        // ここまでで割当たらなかった人に割り当て
        village = assignOther(village, skillPersonCountMap)
        // 恋人、同棲者に状態付与
        village = appendLoversStatus(village)
        // 編成メッセージ
        messages = messages.add(addOrganizationMessage(village, skillPersonCountMap))
        messages = messages.add(messageDomainService.createSkillAssignedMessage(village))

        return daychange.copy(
            village = village,
            messages = messages
        )
    }

    private fun updateSkillRequestByOrganization(
        village: Village,
        messages: Messages,
        countMap: Map<CDef.Skill, Int>
    ): Pair<Village, Messages> {
        var msgs = messages.copy()
        val vil = village.copy(
            participants = village.participants.copy(
                list = village.participants.list.map { participant ->
                    var par = participant.copy()
                    if (participant.id == village.dummyParticipant().id) return@map participant
                    if (!CDef.Skill.村人.existsIn(countMap) && CDef.Skill.霊能者.existsIn(countMap)) {
                        par = par.changeRequestSkillIfNeeded(CDef.Skill.村人, CDef.Skill.霊能者)
                    } else if (CDef.Skill.村人.existsIn(countMap) && !CDef.Skill.霊能者.existsIn(countMap)) {
                        par = par.changeRequestSkillIfNeeded(CDef.Skill.村人, CDef.Skill.霊能者)
                    }
                    par = par.changeRequestSkillByPriority(Skill.madmanPriorityList, countMap)
                    par = par.changeRequestSkillByPriority(Skill.wolfPriorityList, countMap)
                    par = par.changeRequestSkillByPriority(Skill.seerPriorityList, countMap)
                    par = par.upgradeToFirstRequest(countMap)
                    msgs = msgs.addChangeRequestSkillMessageIfNeeded(participant, par)
                    par
                }
            )
        )

        return vil to msgs
    }

    private fun assignFirstSpecifyRequest(
        village: Village,
        skillPersonCountMap: Map<CDef.Skill, Int>
    ): Village {
        var participants = village.participants.copy()
        for ((cdefSkill, capacity) in skillPersonCountMap.entries) {
            // この役職を希望していてまだ割り当たってない人
            val requestPlayerList = participants
                .filterNotAssignSkill()
                .filterByFirstRequestSkill(Skill(cdefSkill)).list
            // 希望している人がいない
            if (requestPlayerList.isEmpty()) continue
            // 空いている枠数
            val left = capacity - participants.filterBySkill(Skill(cdefSkill)).list.size
            // もう空きがない
            if (left <= 0) continue
            // 空いている人数まで割り当てる
            var count = 0
            for (requestPlayer in requestPlayerList.shuffled()) {
                if (count >= left) break
                participants = participants.assignSkill(requestPlayer.id, Skill(cdefSkill), village.latestDay())
                count++
            }
        }
        return village.copy(participants = participants)
    }

    private fun assignFirstRangeRequest(
        village: Village,
        skillPersonCountMap: Map<CDef.Skill, Int>
    ): Village {
        var participants = village.participants.copy()
        // 範囲指定している人
        participants
            .filterNotAssignSkill()
            .list.filter {
                CDef.Skill.listOfSomeoneSkill().contains(it.requestSkill?.first?.toCdef())
                        && it.requestSkill?.first?.toCdef() != CDef.Skill.おまかせ
            }.shuffled()
            .forEach {
                // 役職候補
                val candidateSkillList = Skill.getSomeoneCandidateList(it.requestSkill!!.first)
                for (skill in candidateSkillList) {
                    val cdef = skill.toCdef()
                    val capacity = skillPersonCountMap[cdef] ?: 0
                    val left = capacity - participants.filterBySkill(Skill(cdef)).list.size
                    if (left <= 0) continue
                    participants = participants.assignSkill(it.id, Skill(cdef), village.latestDay())
                    break
                }
            }
        return village.copy(participants = participants)
    }

    private fun assignSecondSpecifyRequest(
        village: Village,
        skillPersonCountMap: Map<CDef.Skill, Int>
    ): Village {
        var participants = village.participants.copy()
        for ((cdefSkill, capacity) in skillPersonCountMap.entries) {
            // この役職を希望していてまだ割り当たってない人
            val requestPlayerList = participants
                .filterNotAssignSkill()
                .filterBySecondRequestSkill(Skill(cdefSkill)).list
            // 希望している人がいない
            if (requestPlayerList.isEmpty()) continue
            // 空いている枠数
            val left = capacity - participants.filterBySkill(Skill(cdefSkill)).list.size
            // もう空きがない
            if (left <= 0) continue
            // 空いている人数まで割り当てる
            var count = 0
            for (requestPlayer in requestPlayerList.shuffled()) {
                if (count >= left) break
                participants = participants.assignSkill(requestPlayer.id, Skill(cdefSkill), village.latestDay())
                count++
            }
        }
        return village.copy(participants = participants)
    }

    private fun assignSecondRangeRequest(
        village: Village,
        skillPersonCountMap: Map<CDef.Skill, Int>
    ): Village {
        var participants = village.participants.copy()
        // 範囲指定している人
        participants
            .filterNotAssignSkill()
            .list.filter {
                CDef.Skill.listOfSomeoneSkill().contains(it.requestSkill?.second?.toCdef())
                        && it.requestSkill?.second?.toCdef() != CDef.Skill.おまかせ
            }.shuffled()
            .forEach {
                // 役職候補
                val candidateSkillList = Skill.getSomeoneCandidateList(it.requestSkill!!.second)
                for (skill in candidateSkillList) {
                    val cdef = skill.toCdef()
                    val capacity = skillPersonCountMap[cdef] ?: 0
                    val left = capacity - participants.filterBySkill(Skill(cdef)).list.size
                    if (left <= 0) continue
                    participants = participants.assignSkill(it.id, Skill(cdef), village.latestDay())
                    break
                }
            }
        return village.copy(participants = participants)
    }

    private fun assignOther(village: Village, skillPersonCountMap: Map<CDef.Skill, Int>): Village {
        var participants = village.participants.copy()

        // 役職が決まっていない参加者に
        participants.filterNotAssignSkill().list.shuffled().forEach { member ->
            // 枠が余っている役職を割り当てる
            for ((cdefSkill, capacity) in skillPersonCountMap.entries) {
                // 空いている枠数
                val left = capacity - participants.filterBySkill(Skill(cdefSkill)).list.size
                if (left <= 0) continue
                participants = participants.assignSkill(member.id, Skill(cdefSkill), village.latestDay())
                break
            }
        }
        return village.copy(participants = participants)
    }

    private fun appendLoversStatus(village: Village): Village {
        var participants = village.participants.copy()
        val lovers = participants.filterBySkill(Skill(CDef.Skill.恋人)).list.shuffled()
        var i = 0
        while (i < lovers.size - 1) {
            participants = participants.addLover(lovers[i], lovers[i + 1])
            participants = participants.addLover(lovers[i + 1], lovers[i])
            i += 2
        }
        val cohabitors = participants.filterBySkill(Skill(CDef.Skill.同棲者)).list.shuffled()
        i = 0
        while (i < cohabitors.size - 1) {
            participants = participants.addLover(cohabitors[i], cohabitors[i + 1])
            participants = participants.addLover(cohabitors[i + 1], cohabitors[i])
            i += 2
        }
        return village.copy(participants = participants)
    }

    private fun addOrganizationMessage(village: Village, countMap: Map<CDef.Skill, Int>): Message {
        val text = Skills.all().filterNotSomeone().list
            .filter { countMap.getOrDefault(it.toCdef(), 0) > 0 }
            .joinToString(
                separator = "、",
                prefix = "この館には、",
                postfix = "いるようだ。"
            ) { "${it.name}が${countMap[it.toCdef()]}名" }
        return messageDomainService.createOrganizationMessage(village, text)
    }

    private fun CDef.Skill.existsIn(countMap: Map<CDef.Skill, Int>) =
        when {
            isSomeoneSkill -> true
            this == CDef.Skill.村人 -> countMap.getOrDefault(this, 0) > 1
            else -> countMap.getOrDefault(this, 0) > 0
        }

    private fun VillageParticipant.changeRequestSkillIfNeeded(from: CDef.Skill, to: CDef.Skill): VillageParticipant {
        val req = requestSkill!!
        return changeRequestSkill(
            req.copy(
                first = if (req.first.toCdef() == from) Skill(to) else req.first,
                second = if (req.second.toCdef() == from) Skill(to) else req.second
            )
        )
    }

    private fun VillageParticipant.changeRequestSkillByPriority(
        priorityList: List<CDef.Skill>,
        countMap: Map<CDef.Skill, Int>
    ): VillageParticipant {
        val to = priorityList.find { it.existsIn(countMap) } ?: return this
        var par = this.copy()
        val first = requestSkill!!.first.toCdef()
        if (!first.existsIn(countMap) && priorityList.contains(first)) {
            par = par.changeRequestSkill(requestSkill.copy(first = to.toModel()))
        }
        val second = requestSkill.second.toCdef()
        if (!second.existsIn(countMap) && priorityList.contains(second)) {
            par = par.changeRequestSkill(requestSkill.copy(second = to.toModel()))
        }
        return par
    }

    private fun VillageParticipant.upgradeToFirstRequest(countMap: Map<CDef.Skill, Int>): VillageParticipant {
        val req = requestSkill!!
        val existsFirst = req.first.toCdef().existsIn(countMap)
        val existsSecond = req.second.toCdef().existsIn(countMap)
        return if (!existsFirst && !existsSecond) {
            changeRequestSkill(req.copy(first = Skill(CDef.Skill.おまかせ), second = Skill(CDef.Skill.おまかせ)))
        } else if (!existsFirst && existsSecond) {
            changeRequestSkill(req.copy(first = req.second, second = Skill(CDef.Skill.おまかせ)))
        } else if (existsFirst && !existsSecond) {
            changeRequestSkill(req.copy(first = req.first, second = Skill(CDef.Skill.おまかせ)))
        } else this
    }

    private fun Messages.addChangeRequestSkillMessageIfNeeded(
        before: VillageParticipant,
        after: VillageParticipant
    ): Messages {
        val beforeReq = before.requestSkill!!
        val afterReq = after.requestSkill!!
        if (beforeReq.first.toCdef() == afterReq.first.toCdef()
            && beforeReq.second.toCdef() == afterReq.second.toCdef()
        ) return this
        return copy().add(messageDomainService.createAutoChangeRequestSkillMessage(before, after))
    }
}