package com.ort.app.domain.service.ability

import com.ort.app.domain.model.ability.Abilities
import com.ort.app.domain.model.ability.toModel
import com.ort.app.domain.model.daychange.Daychange
import com.ort.app.domain.model.message.Message
import com.ort.app.domain.model.skill.toModel
import com.ort.app.domain.model.village.Village
import com.ort.app.domain.model.village.participant.VillageParticipant
import com.ort.app.domain.model.vote.Votes
import com.ort.dbflute.allcommon.CDef
import org.springframework.stereotype.Service

@Service
class YubisashiDomainService : AbilityTypeDomainService {

    override val abilityType = CDef.AbilityType.指差死.toModel()

    override fun getSelectableTargetList(
        village: Village,
        myself: VillageParticipant,
        abilities: Abilities,
        votes: Votes
    ): List<VillageParticipant> = getAliveTargetsWithoutMyself(village, myself)

    override fun isAvailableNoTarget(village: Village, myself: VillageParticipant, abilities: Abilities): Boolean = true
    override fun getTargetPrefix(): String = "死に際に指差す対象"
    override fun getTargetSuffix(): String? = "を死に際に指差す"
    override fun canUseDay(day: Int): Boolean = day > 1

    fun yubisashi(daychange: Daychange): Daychange {
        val village = daychange.village
        var messages = daychange.messages.copy()
        village.participants
            .filterBySkill(CDef.Skill.不止者.toModel())
            .filterDeadDay(village.latestDay()).list.forEach {
                val ability = daychange.abilities.findYesterday(village, it, abilityType) ?: return@forEach
                val target = village.participants.chara(ability.targetCharaId!!)
                messages = messages.add(createYubisashiMessage(village, it, target))
            }

        return daychange.copy(messages = messages)
    }

    private fun createYubisashiMessage(
        village: Village,
        myself: VillageParticipant,
        target: VillageParticipant
    ): Message {
        return Message.ofSystemMessage(
            day = village.latestDay(),
            message = """
                止まるんじゃねぇぞ...

                　　rm1
                 　 〉　ｸ
                 　j￣l　　　　　,ｬvｧ,
                　j::::::l　　　　 ﾐt　　戔
                 j:::::::ﾄ、＿ _　幺wv､｢
                 t:::::::::::::::::｀::⌒ヾニニﾄ､_
                　￣｀ヾ:::::::::::;;;;;;;:::::;|;;;;;;;;::｀ｰ､
                　 　 　 ﾞY::::;;;;;;;;;;;;;;;|;;;;;;;;;;|::::::!
                　　　 　 ヾ::;;;;;;;;;;;;;;;|;;;;;;;;;;:!:::::|
                　　　　 　/;;;;;;;;;;;;;;;;|;;;;;;;;;;;l::::::l
                 　 　 　 /::::;;;;;;;;;;;;;;|;;;;;;;;;;;jl:::::ﾞi
                　　 　 /:::::;;;;;;;;;;;;;;;ﾊ;;;;;;;;;;tl:::::::l
                　　　〈::::;;;;;;;;;;;;;;;;/;|;;ﾞ､;;;;;;;ﾞl:::::::l
                　　 　 ヾ､＿＿/;;;|;;;;;ヾ＿｣::::::〉
                　 　 　 　 |;;;;;;;;;;;;;;|;;;;;;;;;;;;;;| l/え、
                　　　　 　 |;;;;;;;;;;;;;ﾊ;;;;;;;;;;;;;ﾞ〈t!杉!
                　　　　　　|;;;;;;;;;;;j　ヾ;;;;;;;;;;;;ﾞ､ ぐ
                　　　　　　|;;;;;;;;;;;!　　ヾ;;;;;;;;;l ＼>
                　　　　　　|;;;;;;;;;;|　　　ヾ;;;;;;;;!
                　　　　　　|;;;;;;;;;;|　　　　〉;;;;;;|
                
                ${myself.name()}は、${target.name()}を指差した。
            """.trimIndent()
        )
    }
}