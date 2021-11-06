package com.ort.app.api.helper

import com.ort.app.api.request.VillageAbilityForm
import com.ort.app.api.request.VillageActionForm
import com.ort.app.api.request.VillageChangeNameForm
import com.ort.app.api.request.VillageChangeRequestSkillForm
import com.ort.app.api.request.VillageCommitForm
import com.ort.app.api.request.VillageForms
import com.ort.app.api.request.VillageKickForm
import com.ort.app.api.request.VillageLeaveForm
import com.ort.app.api.request.VillageMemoForm
import com.ort.app.api.request.VillageParticipateForm
import com.ort.app.api.request.VillageSayForm
import com.ort.app.api.request.VillageVoteForm
import com.ort.app.api.view.OptionContent
import com.ort.app.api.view.VillageContent
import com.ort.app.application.coordinator.VillageCoordinator
import com.ort.app.application.service.AbilityService
import com.ort.app.application.service.CharaService
import com.ort.app.application.service.FootstepApplicationService
import com.ort.app.application.service.PlayerService
import com.ort.app.application.service.RandomKeywordService
import com.ort.app.application.service.VillageService
import com.ort.app.application.service.VoteApplicationService
import com.ort.app.domain.model.chara.Charachip
import com.ort.app.domain.model.message.MessageType
import com.ort.app.domain.model.situation.ParticipantSituation
import com.ort.app.domain.model.village.Village
import com.ort.app.domain.model.village.participant.VillageParticipant
import com.ort.app.domain.service.SpoilerDomainService
import com.ort.app.fw.exception.WolfMansionBusinessException
import com.ort.app.fw.util.WolfMansionUserInfoUtil
import com.ort.dbflute.allcommon.CDef
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.ui.Model

@Component
class VillageControllerHelper(
    private val villageCoordinator: VillageCoordinator,
    private val villageService: VillageService,
    private val voteService: VoteApplicationService,
    private val footstepService: FootstepApplicationService,
    private val abilityService: AbilityService,
    private val charaService: CharaService,
    private val randomKeywordService: RandomKeywordService,
    private val spoilerDomainService: SpoilerDomainService,
    private val playerService: PlayerService
) {

    @Value("\${app.debug:}")
    private lateinit var debug: String

    fun setIndexModel(village: Village, day: Int, model: Model, villageForms: VillageForms) {
        val userInfo = WolfMansionUserInfoUtil.getUserInfo()
        val myself = userInfo?.let { villageService.findVillageParticipant(village.id, it.username) }
        val votes = voteService.findVotes(village.id)
        val footsteps = footstepService.findFootsteps(village.id)
        val abilities = abilityService.findAbilities(village.id)
        val charachip = charaService.findCharachip(village.setting.charachipId)
            ?: throw WolfMansionBusinessException("charachip not found")
        val keywords = randomKeywordService.findRandomKeywords()
        val villageSituation = villageCoordinator.findVillageSituation(
            village = village,
            myself = myself,
            votes = votes,
            abilities = abilities,
            footsteps = footsteps,
            day = day
        )
        val participantSituation = villageCoordinator.findParticipantSituation(
            village = village,
            username = userInfo?.username,
            myself = myself,
            votes = votes,
            abilities = abilities,
            footsteps = footsteps,
            charachip = charachip,
            day = day
        )
        val isDispSpoilerContent = spoilerDomainService.isViewableSpoilerContent(village, myself)
        val content = VillageContent(
            village = village,
            day = day,
            myself = myself,
            charachip = charachip,
            keywords = keywords,
            villageSituation = villageSituation,
            participantSituation = participantSituation,
            isDispSpoilerContent = isDispSpoilerContent
        )
        model.addAttribute("content", content)
        setForm(model, village, myself, participantSituation, villageForms, charachip)
        setDebug(village, model)
    }

    private fun setForm(
        model: Model,
        village: Village,
        myself: VillageParticipant?,
        situation: ParticipantSituation,
        forms: VillageForms,
        charachip: Charachip
    ) {
        setParticipateFormIfNeeded(situation, model, forms)
        setChangeRequestSkillFormIfNeeded(situation, myself, model, forms)
        setLeaveFormIfNeeded(situation, model)
        setCommitFormIfNeeded(situation, model)
        setSayFormIfNeeded(situation, myself, model, forms, charachip)
        setActionFormIfNeeded(situation, myself, model, forms)
        setChangeNameFormIfNeeded(situation, myself, model, forms)
        setMemoFormIfNeeded(situation, myself, model, forms)
        setAbilityFormIfNeeded(situation, myself, model)
        setVoteFormIfNeeded(situation, model)
        setCreatorFormIfNeeded(village, situation, model)
    }

    private fun setParticipateFormIfNeeded(
        situation: ParticipantSituation,
        model: Model,
        forms: VillageForms
    ) {
        if (!situation.participate.isAvailableParticipate) return
        model.addAttribute(
            "participateForm",
            forms.participateForm ?: VillageParticipateForm()
        )
    }

    private fun setChangeRequestSkillFormIfNeeded(
        situation: ParticipantSituation,
        myself: VillageParticipant?,
        model: Model,
        forms: VillageForms
    ) {
        if (!situation.skillRequest.isAvailableSkillRequest) return
        val first = myself!!.requestSkill!!.first
        val second = myself.requestSkill!!.second
        model.addAttribute(
            "changeRequestSkillForm",
            forms.changeRequestSkillForm ?: VillageChangeRequestSkillForm(
                requestedSkill = first.code,
                secondRequestedSkill = second.code
            )
        )
        model.addAttribute("requestSkillName", first.name)
        model.addAttribute("secondRequestSkillName", second.name)
    }

    private fun setLeaveFormIfNeeded(
        situation: ParticipantSituation,
        model: Model
    ) {
        if (!situation.participate.isAvailableLeave) return
        model.addAttribute("villageLeaveForm", VillageLeaveForm())
    }

    private fun setCommitFormIfNeeded(
        situation: ParticipantSituation,
        model: Model
    ) {
        if (!situation.commit.isAvailableCommit) return
        model.addAttribute("commitForm", VillageCommitForm(!situation.commit.isCommitting))
    }

    private fun setSayFormIfNeeded(
        situation: ParticipantSituation,
        myself: VillageParticipant?,
        model: Model,
        forms: VillageForms,
        charachip: Charachip
    ) {
        forms.sayForm?.let { model.addAttribute("sayForm", it) }
        if (!situation.say.isAvailableSay) return
        val defaultMessageType = situation.say.defaultMessageType ?: MessageType(CDef.MessageType.通常発言)
        model.addAttribute(
            "sayForm",
            VillageSayForm(
                messageType = defaultMessageType.code,
                faceType = charachip.charas.chara(myself!!.charaId).detectDefaultFaceTypeCode(defaultMessageType)
            )
        )
    }

    private fun setActionFormIfNeeded(
        situation: ParticipantSituation,
        myself: VillageParticipant?,
        model: Model,
        forms: VillageForms
    ) {
        forms.actionForm?.let { model.addAttribute("actionForm", it) }
        if (situation.say.selectableMessageTypeList.none { it.messageType.toCdef() == CDef.MessageType.アクション }) return
        model.addAttribute(
            "actionForm",
            VillageActionForm(
                myself = "${myself!!.name()}は、"
            )
        )
    }

    private fun setChangeNameFormIfNeeded(
        situation: ParticipantSituation,
        myself: VillageParticipant?,
        model: Model,
        forms: VillageForms
    ) {
        forms.changeNameForm?.let { model.addAttribute("changeNameForm", it) }
        if (!situation.rp.isAvailableChangeName) return
        model.addAttribute(
            "changeNameForm",
            VillageChangeNameForm(
                name = myself!!.charaName.name,
                shortName = myself.charaName.shortName
            )
        )
    }

    private fun setMemoFormIfNeeded(
        situation: ParticipantSituation,
        myself: VillageParticipant?,
        model: Model,
        forms: VillageForms
    ) {
        forms.memoForm?.let { model.addAttribute("memoForm", it) }
        if (!situation.rp.isAvailableMemo) return
        model.addAttribute(
            "memoForm",
            VillageMemoForm(memo = myself!!.memo)
        )
    }

    private fun setAbilityFormIfNeeded(
        situation: ParticipantSituation,
        myself: VillageParticipant?,
        model: Model
    ) {
        // 能力セット系役職
        situation.ability.let { ability ->
            if (!ability.canUseAbility) return
            if (ability.type == null && myself?.skill?.hasDisturbAbility() != true) return
            model.addAttribute(
                "abilityForm",
                VillageAbilityForm(
                    charaId = ability.attacker?.charaId ?: myself!!.charaId,
                    targetCharaId = ability.target?.charaId,
                    footstep = ability.targetFootstep ?: ability.footstep
                )
            )
            ability.attacker?.let {
                model.addAttribute("charaName", it.name())
            } ?: model.addAttribute("charaName", myself!!.name())
            ability.target?.let {
                model.addAttribute("targetCharaName", it.name())
                model.addAttribute("targetCharaId", it.charaId)
            }

            ability.targetFootstep?.let {
                model.addAttribute("footstep", it)
            } ?: model.addAttribute("footstep", ability.footstep)
        }
    }

    private fun setVoteFormIfNeeded(
        situation: ParticipantSituation,
        model: Model
    ) {
        if (!situation.vote.canVote) return
        model.addAttribute(
            "voteForm",
            VillageVoteForm(targetCharaId = situation.vote.target?.charaId)
        )
        model.addAttribute("voteTarget", situation.vote.target?.name())
    }

    private fun setCreatorFormIfNeeded(village: Village, situation: ParticipantSituation, model: Model) {
        if (!situation.creator.isCreator) return
        model.addAttribute("canShortenEpilogue", village.canShortenEpilogue())
        model.addAttribute("kickForm", VillageKickForm())
        model.addAttribute("creatorSayForm", VillageSayForm())
    }

    private fun setDebug(village: Village, model: Model) {
        val isDebug = debug.toBoolean()
        model.addAttribute("isDebugMode", isDebug)
        if (!isDebug) return
        val players = playerService.findPlayers(village.id)
        val participantList = village.allParticipants().sortedByRoomNumber().list.map {
            val name = it.name()
            val skill = it.skill?.name?.let { skill -> ": $skill" } ?: ""
            val playerName = players.player(it.playerId).name
            OptionContent(name = "$name$skill", value = playerName)
        }
        model.addAttribute("dummyLoginPlayerList", participantList)
    }
}