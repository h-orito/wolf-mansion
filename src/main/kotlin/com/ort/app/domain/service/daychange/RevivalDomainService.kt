package com.ort.app.domain.service.daychange

import com.ort.app.domain.model.ability.AbilityType
import com.ort.app.domain.model.ability.toModel
import com.ort.app.domain.model.daychange.Daychange
import com.ort.app.domain.model.message.Message
import com.ort.app.domain.model.message.toModel
import com.ort.app.domain.model.skill.toModel
import com.ort.app.domain.model.village.Village
import com.ort.app.domain.model.village.participant.VillageParticipant
import com.ort.app.domain.service.MessageDomainService
import com.ort.app.domain.service.ability.hasAlreadyUseAbility
import com.ort.dbflute.allcommon.CDef
import org.springframework.stereotype.Service

@Service
class RevivalDomainService(
    private val messageDomainService: MessageDomainService
) {

    fun revival(orgDaychange: Daychange): Daychange {
        // 蘇生者、死霊術師、陰陽師
        var daychange = revivalByResuscitate(orgDaychange)
        // 絶対人狼
        daychange = revivalAbsoluteWolf(daychange)
        // 勇者
        daychange = revivalHero(daychange)
        // 転生者/申し子
        daychange = revivalReincarnation(daychange)
        // 餡麺麭者
        daychange = revivalAnpanman(daychange)
        // 保険による復活
        daychange = revivalByInsurance(daychange)

        return daychange
    }

    // 蘇生者、死霊術師、陰陽師
    fun revivalByResuscitate(daychange: Daychange): Daychange {
        var village = daychange.village.copy()
        var messages = daychange.messages.copy()
        village.participants.filterAlive().list.filter {
            listOf(CDef.Skill.蘇生者, CDef.Skill.死霊術師, CDef.Skill.陰陽師).contains(it.skill!!.toCdef())
        }.shuffled().forEach {
            val abilityType = when (it.skill!!.toCdef()) {
                CDef.Skill.蘇生者 -> CDef.AbilityType.蘇生.toModel()
                CDef.Skill.死霊術師 -> CDef.AbilityType.死霊蘇生.toModel()
                CDef.Skill.陰陽師 -> CDef.AbilityType.降霊.toModel()
                else -> throw IllegalStateException("想定外の役職です。")
            }
            val ability = daychange.abilities.findYesterday(village, it, abilityType) ?: return@forEach
            val target = village.participants.chara(ability.targetCharaId!!)
            // 蘇生済み/同棲者の場合は失敗
            if (target.isAlive() || target.skill!!.toCdef() == CDef.Skill.同棲者) return@forEach
            village = village.reviveParticipant(target.id)
            // 死霊蘇生、降霊の場合は役職変化
            if (abilityType.toCdef() == CDef.AbilityType.死霊蘇生) {
                village = village.assignParticipantSkill(target.id, CDef.Skill.黙狼.toModel())
            } else if (abilityType.toCdef() == CDef.AbilityType.降霊) {
                village = village.assignParticipantSkill(target.id, CDef.Skill.妖狐.toModel())
            }
            messages = messages.add(createRevivalMessage(village, target))
        }

        return daychange.copy(village = village, messages = messages)
    }

    private fun revivalAbsoluteWolf(daychange: Daychange): Daychange {
        var village = daychange.village.copy()
        // 絶対人狼以外の人狼系役職が生存しているか
        val existsNonAbsoluteAliveWolf = village.participants
            .filterAlive()
            .list.any { it.skill!!.hasAttackAbility() && it.skill.toCdef() != CDef.Skill.絶対人狼 }
        if (!existsNonAbsoluteAliveWolf) return daychange

        var messages = daychange.messages.copy()
        village.participants.filterDead().filterBySkill(CDef.Skill.絶対人狼.toModel()).list.forEach {
            village = village.reviveParticipant(it.id)
            messages = messages.add(createRevivalMessage(village, it))
        }
        return daychange.copy(village = village, messages = messages)
    }

    private fun revivalHero(daychange: Daychange): Daychange {
        var village = daychange.village.copy()
        var messages = daychange.messages.copy()
        village.participants.filterDead().filterBySkill(CDef.Skill.勇者.toModel()).list.forEach {
            // 能力行使済みか
            val hasAlreadyUse = hasAlreadyUseAbility(
                village = village,
                myself = it,
                abilities = daychange.abilities,
                abilityType = AbilityType(CDef.AbilityType.世界を救う)
            )
            if (hasAlreadyUse || it.camp!!.toCdef() != CDef.Camp.村人陣営) {
                return@forEach
            }
            village = village.reviveParticipant(it.id)
            messages = messages.add(createRevivalMessage(village, it))
        }
        return daychange.copy(village = village, messages = messages)
    }

    private fun revivalReincarnation(daychange: Daychange): Daychange {
        var village = daychange.village.copy()
        var messages = daychange.messages.copy()
        village.participants.filterDead().list
            .filter { it.skill?.toCdef() == CDef.Skill.申し子 || it.skill?.toCdef() == CDef.Skill.転生者 }
            .shuffled()
            .forEach {
                village = village.reviveParticipant(it.id)
                val skill = if (it.skill?.toCdef() == CDef.Skill.申し子) {
                    // 村陣営のランダム役職で転生
                    village.getRevivableSkills().filter { it.camp().toCdef() == CDef.Camp.村人陣営 }.shuffled().first()
                } else {
                    // 転生者はランダム役職で転生
                    village.getRevivableSkills().shuffled().first()
                }
                village = village.assignParticipantSkill(it.id, skill)
                messages = messages.add(createRevivalMessage(village, it))
            }
        return daychange.copy(village = village, messages = messages)
    }

    private fun revivalAnpanman(daychange: Daychange): Daychange {
        var village = daychange.village.copy()

        // 生存しているパン屋
        val bakeries = village.participants
            .filterAlive()
            .list.filter { it.skill!!.toCdef() == CDef.Skill.パン屋 }
        val bakeryCount = bakeries.size
        val evilBakeries = village.participants
            .filterAlive()
            .list.filter { it.skill!!.toCdef() == CDef.Skill.闇パン屋 }
        val evilBakeryCount = evilBakeries.size

        if (bakeries.isEmpty() && evilBakeries.isEmpty()) return daychange

        var messages = daychange.messages.copy()
        village.participants.filterDead().filterBySkill(CDef.Skill.餡麺麭者.toModel()).list.forEach {
            village = village.reviveParticipant(it.id)
            messages = messages.add(createAnpanmanRevivalMessage(village, it))
            // パン屋なし、闇パン屋生存の場合闇堕ち
            if (bakeries.isEmpty() && evilBakeries.isNotEmpty()) {
                village = village.insaneParticipant(evilBakeries.first().id, it.id)
                messages = messages.add(createEvilAnpanmanMessage(village, it))
            } else if (bakeries.isNotEmpty() && evilBakeries.isEmpty()) {
                village = village.persuadeParticipant(bakeries.first().id, it.id)
                messages = messages.add(createRightAnpanmanMessage(village, it))
            }
        }
        return daychange.copy(village = village, messages = messages)
    }

    private fun revivalByInsurance(daychange: Daychange): Daychange {
        var village = daychange.village.copy()
        var messages = daychange.messages.copy()
        village.participants.filterDead().list.filter { it.status.hasInsurance() }.forEach {
            village = village.reviveParticipant(it.id)
            village = village.useInsurance(it.id)
            messages = messages.add(createInsuranceReviveMessage(village, it))
        }
        return daychange.copy(village = village, messages = messages)
    }

    private fun createRevivalMessage(village: Village, participant: VillageParticipant): Message {
        return Message.ofSystemMessage(
            day = village.latestDay(),
            message = "不思議なことに、${participant.name()}が生き返った。"
        )
    }

    private fun createAnpanmanRevivalMessage(village: Village, participant: VillageParticipant): Message {
        return Message.ofSystemMessage(
            day = village.latestDay(),
            message = "餡麺麭！新しい顔よ！それーっ！\n不思議なことに、${participant.name()}が生き返った。"
        )
    }

    private fun createEvilAnpanmanMessage(village: Village, anpanman: VillageParticipant): Message {
        return messageDomainService.createPrivateAbilityMessage(
            village = village,
            myself = anpanman,
            text = "${anpanman.name()}は、闇堕ちした。",
            messageType = CDef.MessageType.能力行使メッセージ.toModel()
        )
    }

    private fun createRightAnpanmanMessage(village: Village, anpanman: VillageParticipant): Message {
        return messageDomainService.createPrivateAbilityMessage(
            village = village,
            myself = anpanman,
            text = "${anpanman.name()}は、正義の心を取り戻した。",
            messageType = CDef.MessageType.能力行使メッセージ.toModel()
        )
    }

    private fun createInsuranceReviveMessage(village: Village, participant: VillageParticipant): Message {
        return Message.ofSystemMessage(
            day = village.latestDay(),
            message = "今からでも入れる保険があるんですか！？\n不思議なことに、${participant.name()}が生き返った。"
        )
    }
}
