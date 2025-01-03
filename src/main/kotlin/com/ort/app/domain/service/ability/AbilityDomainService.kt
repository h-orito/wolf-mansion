package com.ort.app.domain.service.ability

import com.ort.app.domain.model.ability.Abilities
import com.ort.app.domain.model.ability.AbilityType
import com.ort.app.domain.model.daychange.Daychange
import com.ort.app.domain.model.footstep.Footsteps
import com.ort.app.domain.model.message.Message
import com.ort.app.domain.model.message.MessageType
import com.ort.app.domain.model.situation.participant.ParticipantAbilitySituation
import com.ort.app.domain.model.situation.village.VillageWholeDetail
import com.ort.app.domain.model.situation.village.VillageWholeSituation
import com.ort.app.domain.model.skill.Skill
import com.ort.app.domain.model.skill.Skills
import com.ort.app.domain.model.skill.toModel
import com.ort.app.domain.model.village.Village
import com.ort.app.domain.model.village.participant.VillageParticipant
import com.ort.app.domain.model.village.participant.dead.DeadReason
import com.ort.app.domain.model.vote.Votes
import com.ort.app.domain.service.FootstepDomainService
import com.ort.app.domain.service.MessageDomainService
import com.ort.app.domain.service.SpoilerDomainService
import com.ort.app.fw.exception.WolfMansionBusinessException
import com.ort.dbflute.allcommon.CDef
import org.springframework.stereotype.Service

@Service
class AbilityDomainService(
    private val spoilerDomainService: SpoilerDomainService,
    private val disturbDomainService: DisturbDomainService,
    private val attackDomainService: AttackDomainService,
    private val bombDomainService: BombDomainService,
    private val cheatFoxDomainService: CheatFoxDomainService,
    private val insaneDomainService: InsaneDomainService,
    private val persuadeDomainService: PersuadeDomainService,
    private val insuranceDomainService: InsuranceDomainService,
    private val cohabitDomainService: CohabitDomainService,
    private val commandDomainService: CommandDomainService,
    private val instigateDomainService: InstigateDomainService,
    private val courtDomainService: CourtDomainService,
    private val divineDomainService: DivineDomainService,
    private val deadDivineDomainService: DeadDivineDomainService,
    private val badgerGameDomainService: BadgerGameDomainService,
    private val fruitsBasketDomainService: FruitsBasketDomainService,
    private val freezerDomainService: FreezerDomainService,
    private val guiltyDomainService: GuiltyDomainService,
    private val cloudyDomainService: CloudyDomainService,
    private val hotLimitDomainService: HotLimitDomainService,
    private val hideDomainService: HideDomainService,
    private val guardDomainService: GuardDomainService,
    private val wandererDomainService: WandererDomainService,
    private val investigateDomainService: InvestigateDomainService,
    private val loneAttackDomainService: LoneAttackDomainService,
    private val huntingDomainService: HuntingDomainService,
    private val saveTheWorldDomainService: SaveTheWorldDomainService,
    private val beatDomainService: BeatDomainService,
    private val seduceDomainService: SeduceDomainService,
    private val loveStealDomainService: LoveStealDomainService,
    private val breakupDomainService: BreakupDomainService,
    private val stalkingDomainService: StalkingDomainService,
    private val cheatLoveDomainService: CheatLoveDomainService,
    private val trapDomainService: TrapDomainService,
    private val wallPunchDomainService: WallPunchDomainService,
    private val fantasistDomainService: FantasistDomainService,
    private val sleepwalkDomainService: SleepwalkDomainService,
    private val rainbowDomainService: RainbowDomainService,
    private val loudSpeakDomainService: LoudSpeakDomainService,
    private val shoutDomainService: ShoutDomainService,
    private val clownDomainService: ClownDomainService,
    private val assassinDomainService: AssassinDomainService,
    private val translateDomainService: TranslateDomainService,
    private val resuscitateDomainService: ResuscitateDomainService,
    private val necromanceDomainService: NecromanceDomainService,
    private val onmyoNecromanceDomainService: OnmyoNecromanceDomainService,
    private val poseidonDomainService: PoseidonDomainService,
    private val forceReincarnationDomainService: ForceReincarnationDomainService,
    private val runawayDomainService: RunawayDomainService,
    private val giveBabaDomainService: GiveBabaDomainService,
    private val giveWinDomainService: GiveWinDomainService,
    private val yubisashiDomainService: YubisashiDomainService,
    private val revolutionDomainService: RevolutionDomainService,
    private val omniscienceDomainService: OmniscienceDomainService,
    private val curseMarkDomainService: CurseMarkDomainService,
    private val counterCurseMarkDomainService: CounterCurseMarkDomainService,
    private val telekinesisDomainService: TelekinesisDomainService,
    private val hiyasichukaDomainService: HiyasichukaDomainService,
    private val emotionDomainService: EmotionDomainService,
    private val chikuwaDomainService: ChikuwaDomainService,
    private val messageDomainService: MessageDomainService,
    private val footstepDomainService: FootstepDomainService
) {

    fun convertToVillageSituation(
        village: Village,
        myself: VillageParticipant?,
        abilities: Abilities,
        day: Int
    ): VillageWholeSituation {
        return VillageWholeSituation(
            list = (2..day).map {
                VillageWholeDetail(
                    day = it,
                    suddenlyDeath = village.participants.filterExistsDeadHistory(it, DeadReason(CDef.DeadReason.突然)),
                    executed = village.participants.filterExistsDeadHistory(it, DeadReason(CDef.DeadReason.処刑)),
                    miserable = village.participants.filterExistsMiserableDeadHistory(it),
                    revival = village.participants.filterExistsReviveHistory(it),
                    suicide = village.participants.filterExistsDeadHistory(it, DeadReason(CDef.DeadReason.後追)),
                    ability = mapAbilitySituation(village, myself, abilities, it)
                )
            }
        )
    }

    fun convertToParticipantSituation(
        village: Village,
        myself: VillageParticipant?,
        abilities: Abilities,
        votes: Votes,
        footsteps: Footsteps,
        day: Int
    ): ParticipantAbilitySituation {
        val abilityType = myself?.skill?.getAbility()
        return ParticipantAbilitySituation(
            canUseAbility = canUseAbility(village, myself, day) && canUseDay(myself, day),
            type = abilityType,
            targetList = getSelectableTargetList(village, myself, abilities, votes, day, abilityType),
            targetFootstepList = getSelectableFootstepList(village, myself, footsteps, day, abilityType),
            attacker = getSelectingAttacker(village, myself, abilities, day, abilityType),
            target = getSelectingTarget(village, myself, abilities, day, abilityType),
            targetFootstep = getSelectingTargetFootstep(village, myself, abilities, day, abilityType),
            targetingMessage = getSelectingTargetMessage(village, myself, abilities, day, abilityType),
            footstep = getSelectingFootstep(village, myself, footsteps, day, abilityType),
            isAvailableNoTarget = canNoTarget(village, myself, abilities, abilityType),
            attackerList = getAttackerList(village, myself, abilities, day),
            skillHistoryList = getSkillHistoryList(village, myself, abilities, footsteps, day, abilityType),
            wolfList = getWolfList(village, myself),
            cMadmanList = getCMadmanList(village, myself),
            foxList = getFoxList(village, myself),
            loversList = getLoversList(village, myself),
            masonsList = getMasonsList(village, myself),
            listenMasonsList = getListenMasonsList(village, myself),
            targetPrefix = getTargetPrefix(village, myself, day, abilityType),
            targetSuffix = getTargetSuffix(village, myself, day, abilityType),
            isTargetingAndFootstep = isTargetingAndFootstep(village, myself, day, abilityType)
        )
    }

    fun detectAbilityTypeService(abilityType: AbilityType): AbilityTypeDomainService =
        when (abilityType.toCdef()) {
            CDef.AbilityType.襲撃 -> attackDomainService
            CDef.AbilityType.襲撃希望 -> attackDomainService
            CDef.AbilityType.爆弾設置 -> bombDomainService
            CDef.AbilityType.誑かす -> cheatFoxDomainService
            CDef.AbilityType.教唆 -> insaneDomainService
            CDef.AbilityType.説得 -> persuadeDomainService
            CDef.AbilityType.同棲 -> cohabitDomainService
            CDef.AbilityType.指揮 -> commandDomainService
            CDef.AbilityType.煽動 -> instigateDomainService
            CDef.AbilityType.求愛 -> courtDomainService
            CDef.AbilityType.占い -> divineDomainService
            CDef.AbilityType.死者占い -> deadDivineDomainService
            CDef.AbilityType.美人局 -> badgerGameDomainService
            CDef.AbilityType.フルーツバスケット -> fruitsBasketDomainService
            CDef.AbilityType.護衛 -> guardDomainService
            CDef.AbilityType.風来護衛 -> wandererDomainService
            CDef.AbilityType.捜査 -> investigateDomainService
            CDef.AbilityType.単独襲撃 -> loneAttackDomainService
            CDef.AbilityType.誘惑 -> seduceDomainService
            CDef.AbilityType.ストーキング -> stalkingDomainService
            CDef.AbilityType.浮気 -> cheatLoveDomainService
            CDef.AbilityType.罠設置 -> trapDomainService
            CDef.AbilityType.壁殴り -> wallPunchDomainService
            CDef.AbilityType.虹塗り -> rainbowDomainService
            CDef.AbilityType.拡声 -> loudSpeakDomainService
            CDef.AbilityType.叫び -> shoutDomainService
            CDef.AbilityType.翻訳 -> translateDomainService
            CDef.AbilityType.強制転生 -> forceReincarnationDomainService
            CDef.AbilityType.暴走転生 -> runawayDomainService
            CDef.AbilityType.蘇生 -> resuscitateDomainService
            CDef.AbilityType.死霊蘇生 -> necromanceDomainService
            CDef.AbilityType.人魚化 -> poseidonDomainService
            CDef.AbilityType.ババを渡す -> giveBabaDomainService
            CDef.AbilityType.当選 -> giveWinDomainService
            CDef.AbilityType.指差死 -> yubisashiDomainService
            CDef.AbilityType.保険 -> insuranceDomainService
            CDef.AbilityType.狩猟 -> huntingDomainService
            CDef.AbilityType.世界を救う -> saveTheWorldDomainService
            CDef.AbilityType.隠蔽 -> hideDomainService
            CDef.AbilityType.恋泥棒 -> loveStealDomainService
            CDef.AbilityType.破局 -> breakupDomainService
            CDef.AbilityType.殴打 -> beatDomainService
            CDef.AbilityType.全知 -> omniscienceDomainService
            CDef.AbilityType.戦闘力発揮 -> freezerDomainService
            CDef.AbilityType.道化 -> clownDomainService
            CDef.AbilityType.殺し屋化 -> assassinDomainService
            CDef.AbilityType.濡衣 -> guiltyDomainService
            CDef.AbilityType.曇天 -> cloudyDomainService
            CDef.AbilityType.降霊 -> onmyoNecromanceDomainService
            CDef.AbilityType.革命 -> revolutionDomainService
            CDef.AbilityType.呪縛 -> curseMarkDomainService
            CDef.AbilityType.反呪 -> counterCurseMarkDomainService
            CDef.AbilityType.念力付与 -> telekinesisDomainService
            CDef.AbilityType.冷やし中華 -> hiyasichukaDomainService
            CDef.AbilityType.誰だ今の -> chikuwaDomainService
            CDef.AbilityType.情緒 -> emotionDomainService
            CDef.AbilityType.ナマ足 -> hotLimitDomainService
        }

    fun createSetMessage(
        village: Village,
        myself: VillageParticipant,
        attackerCharaId: Int?,
        targetCharaId: Int?,
        footstep: String?
    ): Message {
        myself.skill ?: throw WolfMansionBusinessException("役職なし")
        if (!canUseAbility(village, myself, village.latestDay())) throw WolfMansionBusinessException("能力を使えない状態です")
        return if (myself.skill.hasDisturbAbility()) {
            val text = disturbDomainService.createSetMessageText(village, myself, footstep!!)
            messageDomainService.createAbilitySetMessage(
                village = village,
                text = text,
                messageType = MessageType(CDef.MessageType.非公開システムメッセージ)
            )
        } else {
            val abilityType = myself.skill.getAbility() ?: throw WolfMansionBusinessException("行使可能な能力がありません")
            if (!canUseDay(myself, village.latestDay())) throw WolfMansionBusinessException("使用できません")
            val text = detectAbilityTypeService(abilityType).createSetMessageText(
                village,
                myself,
                attackerCharaId,
                targetCharaId,
                footstep
            )
            messageDomainService.createAbilitySetMessage(
                village = village,
                text = text,
                messageType = abilityType.getSetMessageType()
            )
        }
    }

    private fun canNoTarget(
        village: Village,
        myself: VillageParticipant?,
        abilities: Abilities,
        abilityType: AbilityType?
    ): Boolean {
        return if (abilityType == null) false
        else detectAbilityTypeService(abilityType).isAvailableNoTarget(village, myself!!, abilities)
    }

    private fun getSelectingTarget(
        village: Village,
        myself: VillageParticipant?,
        abilities: Abilities,
        day: Int,
        abilityType: AbilityType?
    ): VillageParticipant? {
        return if (!canUseAbility(village, myself, day)) null
        else if (abilityType == null) null
        else detectAbilityTypeService(abilityType).getSelectingTarget(village, myself!!, abilities)
    }

    private fun getSelectingAttacker(
        village: Village,
        myself: VillageParticipant?,
        abilities: Abilities,
        day: Int,
        abilityType: AbilityType?
    ): VillageParticipant? {
        return if (!canUseAbility(village, myself, day)) null
        else if (abilityType?.toCdef() != CDef.AbilityType.襲撃希望) null
        else attackDomainService.getSelectingAttacker(village, myself!!, abilities)
    }

    private fun getSelectingTargetFootstep(
        village: Village,
        myself: VillageParticipant?,
        abilities: Abilities,
        day: Int,
        abilityType: AbilityType?
    ): String? {
        return if (!canUseAbility(village, myself, day)) null
        else if (abilityType?.toCdef() != CDef.AbilityType.捜査) null
        else investigateDomainService.getSelectingFootstep(village, myself!!, abilities)
    }

    private fun getSelectingTargetMessage(
        village: Village,
        myself: VillageParticipant?,
        abilities: Abilities,
        day: Int,
        abilityType: AbilityType?
    ): String? {
        return if (!canUseAbility(village, myself, day)) null
        else if (abilityType == null) null
        else detectAbilityTypeService(abilityType).getSelectingTargetMessage(village, myself!!, abilities)
    }

    private fun getSelectingFootstep(
        village: Village,
        myself: VillageParticipant?,
        footsteps: Footsteps,
        day: Int,
        abilityType: AbilityType?
    ): String? {
        return if (!canUseAbility(village, myself, day)) null
        else if (abilityType?.toCdef() == CDef.AbilityType.襲撃希望) {
            attackDomainService.getSelectingFootstep(village, myself!!, footsteps)
        } else footsteps
            .filterByCharaId(myself!!.charaId)
            .filterByDay(village.latestDay())
            .list.firstOrNull()?.roomNumbers
    }

    fun getSelectableTargetList(
        village: Village,
        myself: VillageParticipant?,
        abilities: Abilities,
        votes: Votes,
        day: Int,
        abilityType: AbilityType?
    ): List<VillageParticipant> {
        return if (!canUseAbility(village, myself, day)) listOf()
        else if (abilityType == null || abilityType.toCdef() == CDef.AbilityType.襲撃希望) listOf()
        else detectAbilityTypeService(abilityType).getSelectableTargetList(village, myself!!, abilities, votes)
    }

    fun assertAbility(
        village: Village,
        myself: VillageParticipant?,
        attackerCharaId: Int?,
        targetCharaId: Int?,
        footstep: String?,
        abilities: Abilities,
        votes: Votes,
        footsteps: Footsteps
    ) {
        myself?.skill ?: throw WolfMansionBusinessException("役職なし")
        if (!canUseAbility(village, myself, village.latestDay())) throw WolfMansionBusinessException("能力を使えない状態です")
        if (!canUseDay(myself, village.latestDay())) throw WolfMansionBusinessException("能力を使えない日数です")
        if (myself.skill.hasDisturbAbility()) {
            disturbDomainService.assertAbility(village, footstep)
        } else {
            val abilityType = myself.skill.getAbility() ?: throw WolfMansionBusinessException("行使可能な能力がありません")
            detectAbilityTypeService(abilityType).assertAbility(
                village,
                myself,
                attackerCharaId,
                targetCharaId,
                footstep,
                abilities,
                footsteps,
                votes
            ) {
                footstepDomainService.assertFootstep(
                    village,
                    attackerCharaId ?: myself.charaId,
                    targetCharaId,
                    footstep
                )
            }
        }
    }

    fun assertGetSelectableFootsteps(village: Village, myself: VillageParticipant) {
        if (!isTargetingAndFootstep(village, myself, village.latestDay(), myself.skill?.getAbility())) {
            throw WolfMansionBusinessException("足音候補を取得できる能力ではありません")
        }
    }

    private fun getSelectableFootstepList(
        village: Village,
        myself: VillageParticipant?,
        footsteps: Footsteps,
        day: Int,
        abilityType: AbilityType?
    ): List<String> {
        return if (!canUseAbility(village, myself, day)) emptyList()
        else if (abilityType == null) emptyList()
        else if (abilityType.toCdef() != CDef.AbilityType.捜査) emptyList()
        else investigateDomainService.getSelectableFootstepList(village, myself!!, footsteps)
    }

    private fun canUseAbility(village: Village, myself: VillageParticipant?, day: Int): Boolean =
        village.canUseAbility(day) && myself?.canUseAbility() == true

    fun canUseDay(myself: VillageParticipant?, day: Int): Boolean {
        val skill = myself?.skill ?: return false
        return skill.hasDisturbAbility() || skill.getAbility()?.let {
            detectAbilityTypeService(it).canUseDay(day)
        } ?: false
    }

    private fun getAttackerList(
        village: Village,
        myself: VillageParticipant?,
        abilities: Abilities,
        day: Int
    ): List<VillageParticipant> {
        if (!canUseAbility(village, myself, day)) return emptyList()
        if (myself!!.skill == null || !myself.skill!!.hasAttackAbility()) return emptyList()
        return attackDomainService.getAttackableWolfs(village, day, abilities)
    }

    private fun getSkillHistoryList(
        village: Village,
        myself: VillageParticipant?,
        abilities: Abilities,
        footsteps: Footsteps,
        day: Int,
        abilityType: AbilityType?
    ): List<String> {
        return when {
            myself?.skill == null -> emptyList()
            myself.skill.hasDisturbAbility() -> {
                disturbDomainService.getHistories(village, myself, footsteps, day)
            }

            else -> {
                abilityType ?: return emptyList()
                detectAbilityTypeService(abilityType).getHistories(village, myself, abilities, footsteps, day)
            }
        }
    }

    private fun getWolfList(village: Village, myself: VillageParticipant?): List<VillageParticipant> {
        if (myself?.skill == null || !myself.skill.isViewableWolfCharaName()) return emptyList()
        return village.participants.sortedByRoomNumber().list.filter { it.skill!!.hasAttackAbility() }
    }

    private fun getCMadmanList(village: Village, myself: VillageParticipant?): List<VillageParticipant> {
        if (myself?.skill == null || !myself.skill.isSayableWerewolfSay()) return emptyList()
        return village.participants.sortedByRoomNumber().list.filter { it.skill!!.toCdef() == CDef.Skill.C国狂人 }
    }

    private fun getFoxList(village: Village, myself: VillageParticipant?): List<VillageParticipant> {
        if (myself?.skill == null || !isFoxCamp(myself)) return emptyList()
        return village.participants.sortedByRoomNumber().list.filter {
            it.skill!!.isFoxCount()
        }
    }

    private fun isFoxCamp(myself: VillageParticipant): Boolean {
        return myself.skill!!.camp().isFoxs() || myself.status.isFoxPossessioned()
    }

    private fun getLoversList(village: Village, myself: VillageParticipant?): List<VillageParticipant> {
        if (myself?.skill == null || !myself.status.hasLover()) return emptyList()
        return village.participants.sortedByRoomNumber().list.filter { it.status.hasLover() }
    }

    private fun getMasonsList(village: Village, myself: VillageParticipant?): List<VillageParticipant> {
        if (myself?.skill == null || !myself.skill.isViewableSympathizeSay()) return emptyList()
        return village.participants.sortedByRoomNumber().list.filter { it.skill!!.toCdef() == CDef.Skill.共鳴者 }
    }

    private fun getListenMasonsList(village: Village, myself: VillageParticipant?): List<VillageParticipant> {
        if (myself?.skill == null || !myself.skill.isViewableSympathizeSay()) return emptyList()
        return village.participants.sortedByRoomNumber().list.filter { it.skill!!.toCdef() == CDef.Skill.共有者 }
    }

    private fun getTargetPrefix(
        village: Village,
        myself: VillageParticipant?,
        day: Int,
        abilityType: AbilityType?
    ): String? {
        return if (!canUseAbility(village, myself, day)) null
        else if (abilityType == null) null
        else detectAbilityTypeService(abilityType).getTargetPrefix()
    }

    private fun getTargetSuffix(
        village: Village,
        myself: VillageParticipant?,
        day: Int,
        abilityType: AbilityType?
    ): String? {
        return if (!canUseAbility(village, myself, day)) null
        else if (abilityType == null) null
        else detectAbilityTypeService(abilityType).getTargetSuffix()
    }

    private fun isTargetingAndFootstep(
        village: Village,
        myself: VillageParticipant?,
        day: Int,
        abilityType: AbilityType?
    ): Boolean {
        return if (!canUseAbility(village, myself, day)) false
        else if (abilityType == null) false
        else detectAbilityTypeService(abilityType).isTargetingAndFootstep()
    }

    private fun mapAbilitySituation(
        village: Village,
        myself: VillageParticipant?,
        abilities: Abilities,
        day: Int
    ): List<String> {
        if (!spoilerDomainService.isViewableSpoilerContent(village, myself)) return emptyList()
        return abilities.filterByDay(day - 1).list.mapNotNull {
            if (it.type.toCdef() == CDef.AbilityType.捜査) {
                val from = village.participants.chara(it.charaId).shortNameWhen(day - 1)
                val target = it.targetFootstep!!
                "[${it.type.name}]$from → $target"
            } else if (it.type.toCdef() == CDef.AbilityType.襲撃希望) {
                null
            } else if (it.type.toCdef() == CDef.AbilityType.襲撃) {
                val from = village.participants.chara(it.attackerCharaId!!).shortNameWhen(day - 1)
                val to = village.participants.chara(it.targetCharaId!!).shortNameWhen(day - 1)
                "[${it.type.name}]$from → $to"
            } else {
                val from = village.participants.chara(it.charaId).shortNameWhen(day - 1)
                val to = village.participants.chara(it.targetCharaId!!).shortNameWhen(day - 1)
                "[${it.type.name}]$from → $to"
            }
        }
    }

    fun addOpenSkillMessages(daychange: Daychange): Daychange {
        val village = daychange.village
        var messages = daychange.messages.copy()

        // 勇者、絶対人狼
        Skills.openSkills.forEach { cdefSkill ->
            val targets = village.participants.filterBySkill(cdefSkill.toModel()).list
            if (targets.isNotEmpty()) {
                val text = targets.joinToString(
                    separator = "、",
                    postfix = "は${cdefSkill.toModel().name}のようだ。"
                ) { it.name() }
                messages = messages.add(messageDomainService.createOpenSkillMessage(village, text))
            }
        }

        // 梟
        if (village.participants.filterBySkill(Skill(CDef.Skill.梟)).list.isNotEmpty()) {
            messages =
                messages.add(
                    messageDomainService.createOpenSkillMessage(
                        daychange.village,
                        "この村には強力な聴力を持つ者がいるようだ。"
                    )
                )
        }

        return daychange.copy(messages = messages)
    }

    fun addDefaultAbilities(beforeDaychange: Daychange): Daychange {
        var daychange = beforeDaychange.copy()

        // 浮気
        daychange = cheatLoveDomainService.addDefaultAbilities(daychange)
        // 襲撃
        daychange = attackDomainService.addDefaultAbilities(daychange)
        // 単独襲撃
        daychange = loneAttackDomainService.addDefaultAbilities(daychange)
        // 占い
        daychange = divineDomainService.addDefaultAbilities(daychange)
        daychange = deadDivineDomainService.addDefaultAbilities(daychange)
        // 徘徊
        daychange = disturbDomainService.addDefaultFootsteps(daychange)
        // 同棲
        daychange = cohabitDomainService.addDefaultAbilities(daychange)
        // 妄想癖
        daychange = fantasistDomainService.addDefaultAbilities(daychange)
        // 夢遊病
        daychange = sleepwalkDomainService.addDefaultAbilities(daychange)
        // 護衛
        daychange = guardDomainService.addDefaultAbilities(daychange)
        // 捜査
        daychange = investigateDomainService.addDefaultAbilities(daychange)
        // 当選
        daychange = giveWinDomainService.addDefaultAbilities(daychange)

        return daychange
    }
}