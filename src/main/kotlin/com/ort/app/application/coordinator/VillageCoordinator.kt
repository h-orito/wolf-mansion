package com.ort.app.application.coordinator

import com.ort.app.application.service.*
import com.ort.app.domain.model.ability.Abilities
import com.ort.app.domain.model.chara.Charachip
import com.ort.app.domain.model.chara.Charachips
import com.ort.app.domain.model.commit.Commit
import com.ort.app.domain.model.footstep.Footsteps
import com.ort.app.domain.model.message.Message
import com.ort.app.domain.model.player.Player
import com.ort.app.domain.model.situation.ParticipantSituation
import com.ort.app.domain.model.situation.VillageSituation
import com.ort.app.domain.model.situation.village.VillageParticipantLiveSituation
import com.ort.app.domain.model.skill.Skill
import com.ort.app.domain.model.village.Village
import com.ort.app.domain.model.village.participant.VillageParticipant
import com.ort.app.domain.model.village.participant.VillageParticipants
import com.ort.app.domain.model.village.setting.VillageCharaSetting
import com.ort.app.domain.model.vote.Vote
import com.ort.app.domain.model.vote.Votes
import com.ort.app.domain.service.*
import com.ort.app.domain.service.ability.AbilityDomainService
import com.ort.app.domain.service.ability.AttackDomainService
import com.ort.app.domain.service.room.RoomDomainService
import com.ort.app.fw.exception.WolfMansionBusinessException
import com.ort.dbflute.allcommon.CDef
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile

@Service
class VillageCoordinator(
    // application service
    private val villageService: VillageService,
    private val playerService: PlayerService,
    private val commitService: CommitService,
    private val messageService: MessageService,
    private val charaService: CharaService,
    private val tweetService: TweetService,
    private val abilityService: AbilityService,
    private val footstepService: FootstepApplicationService,
    private val voteService: VoteApplicationService,
    private val slackService: SlackService,
    // domain service
    private val participateDomainService: ParticipateDomainService,
    private val skillRequestDomainService: SkillRequestDomainService,
    private val commitDomainService: CommitDomainService,
    private val sayDomainService: SayDomainService,
    private val abilityDomainService: AbilityDomainService,
    private val adminDomainService: AdminDomainService,
    private val creatorDomainService: CreatorDomainService,
    private val voteDomainService: VoteDomainService,
    private val rpDomainService: RpDomainService,
    private val roomDomainService: RoomDomainService,
    private val footstepDomainService: FootstepDomainService,
    private val messageDomainService: MessageDomainService,
    private val attackDomainService: AttackDomainService
) {
    @Transactional(rollbackFor = [Exception::class, WolfMansionBusinessException::class])
    fun participate(
        village: Village,
        player: Player,
        charaId: Int?,
        charaName: String?,
        charaShortName: String?,
        charaImageFile: MultipartFile?,
        firstRequestSkill: Skill,
        secondRequestSkill: Skill,
        joinMessage: String,
        joinPassword: String?,
        isSpectator: Boolean,
        ipAddress: String
    ): VillageParticipant {
        assertParticipate(
            village,
            player,
            charaId,
            charaName,
            charaShortName,
            charaImageFile,
            joinPassword,
            isSpectator
        )
        val chara = if (village.setting.chara.isOriginalCharachip) {
            charaService.registerOriginalChara(
                village.setting.chara.charachipIds.first(),
                charaName!!,
                charaShortName!!,
                charaImageFile!!
            )
        } else charaService.findChara(charaId!!, village.setting.chara.isOriginalCharachip)
            ?: throw IllegalStateException("chara not found.")
        val participant = villageService.participate(
            village.id, player.id, chara, firstRequestSkill, secondRequestSkill, isSpectator
        )
        villageService.addIpAddress(participant, ipAddress)
        val afterVillage = villageService.findVillage(village.id)!!
        // N人目シスメ
        messageService.registerMessage(
            afterVillage,
            messageDomainService.createParticipateSystemMessage(afterVillage, participant, isSpectator)
        )
        // 参加発言
        messageService.registerMessage(
            afterVillage,
            messageDomainService.createJoinMessage(afterVillage, participant, isSpectator, chara, joinMessage)
        )
        if (!isSpectator) {
            // 希望役職シスメ
            messageService.registerMessage(afterVillage, messageDomainService.createSkillRequestMessage(participant))
            // 人数が揃ったらツイート
            tweetService.tweetParticipantEnoughIfNeeded(afterVillage)
        }
        // IPアドレスが重複している人がいたら通知
        if (!playerService.findPlayer(participant.playerId).shouldCheckAccessInfo) return participant
        val isContain = village.allParticipants().filterNotParticipant(participant).list
            .filterNot { it.playerId == 1 }
            .flatMap { it.ipAddresses }.distinct()
            .contains(ipAddress)
        if (isContain) {
            slackService.postTextIfNeeded(village, "IPアドレス重複検出: $ipAddress")
        }
        return participant
    }

    fun assertParticipate(
        village: Village,
        player: Player,
        charaId: Int?,
        charaName: String?,
        charaShortName: String?,
        charaImageFile: MultipartFile?,
        joinPassword: String?,
        isSpectator: Boolean
    ) {
        if (village.setting.chara.isOriginalCharachip) {
            if (charaName.isNullOrBlank()) throw WolfMansionBusinessException("キャラクター名は必須です")
            if (charaShortName.isNullOrBlank()) throw WolfMansionBusinessException("キャラクター名略称は必須です")
            if (charaImageFile?.size == 0L) throw WolfMansionBusinessException("キャラクター画像は必須です。")
        } else {
            charaId ?: throw WolfMansionBusinessException("キャラクターを選択してください")
        }

        if (isSpectator) {
            val charachips =
                village.setting.chara.let { charaService.findCharachips(it.charachipIds, it.isOriginalCharachip) }
            participateDomainService.assertSpectate(village, player, charaId, joinPassword, charachips)
        } else {
            participateDomainService.assertParticipate(village, player, charaId, joinPassword)
        }
    }

    @Transactional(rollbackFor = [Exception::class, WolfMansionBusinessException::class])
    fun changeRequestSkill(
        village: Village,
        myself: VillageParticipant,
        first: Skill,
        second: Skill
    ) {
        skillRequestDomainService.assertChangeRequestSkill(village, myself)
        villageService.changeRequestSkill(myself, first, second)
        val after = villageService.findVillageParticipant(myself.id)!!
        messageService.registerMessage(village, messageDomainService.createSkillRequestMessage(after))
    }

    @Transactional(rollbackFor = [Exception::class, WolfMansionBusinessException::class])
    fun changeName(
        village: Village,
        myself: VillageParticipant?,
        name: String,
        shortName: String
    ) {
        myself ?: throw WolfMansionBusinessException("ログインしてください")
        villageService.changeParticipantName(myself, name, shortName)
        val after = villageService.findVillageParticipant(myself.id)!!
        messageService.registerMessage(
            village, messageDomainService.createChangeNameMessage(village.latestDay(), myself, after)
        )
    }

    @Transactional(rollbackFor = [Exception::class, WolfMansionBusinessException::class])
    fun leave(village: Village, myself: VillageParticipant) {
        participateDomainService.assertLeave(village, myself)
        villageService.leave(myself)
        messageService.registerMessage(village, messageDomainService.createLeaveMessage(myself))
    }

    @Transactional(rollbackFor = [Exception::class, WolfMansionBusinessException::class])
    fun setAbility(
        village: Village,
        myself: VillageParticipant?,
        attackerCharaId: Int?,
        targetCharaId: Int?,
        footstep: String?
    ) {
        myself ?: throw WolfMansionBusinessException("ログインしてください")
        val abilities = abilityService.findAbilities(village.id)
        val votes = voteService.findVotes(village.id)
        val footsteps = footstepService.findFootsteps(village.id)
        abilityDomainService.assertAbility(
            village,
            myself,
            attackerCharaId,
            targetCharaId,
            footstep,
            abilities,
            votes,
            footsteps
        )
        abilityService.updateAbility(village, myself, attackerCharaId, targetCharaId, footstep)
        val footstepParticipant = attackerCharaId?.let { village.participants.chara(it) } ?: myself
        footstepService.updateFootstep(village, myself, footstepParticipant, footstep)
        messageService.registerMessage(
            village,
            abilityDomainService.createSetMessage(village, myself, attackerCharaId, targetCharaId, footstep)
        )
    }

    @Transactional(rollbackFor = [Exception::class, WolfMansionBusinessException::class])
    fun setVote(village: Village, myself: VillageParticipant?, targetCharaId: Int) {
        myself ?: throw WolfMansionBusinessException("ログインしてください")
        voteDomainService.assertVote(village, myself, targetCharaId)
        voteService.updateVote(
            village,
            Vote(day = village.latestDay(), charaId = myself.charaId, targetCharaId = targetCharaId)
        )
    }

    @Transactional(rollbackFor = [Exception::class, WolfMansionBusinessException::class])
    fun setCommit(village: Village, myself: VillageParticipant?, commit: Boolean) {
        myself ?: throw WolfMansionBusinessException("ログインしてください")
        commitDomainService.assertCommit(village, myself)
        commitService.setCommit(village, Commit(day = village.latestDay(), myselfId = myself.id))
    }

    fun assertCreateVillage(player: Player, max: Int, charachips: Charachips, isOriginal: Boolean) {
        if (!player.isAvailableCreateVillage()) {
            throw WolfMansionBusinessException("村建てした村の決着がつくまでは村を建てられません。")
        }
        if (!isOriginal && charachips.list.sumOf { it.charas.list.size } < max) {
            throw WolfMansionBusinessException("定員に対してキャラ数が不足しています。")
        }
    }

    @Transactional(rollbackFor = [Exception::class, WolfMansionBusinessException::class])
    fun registerVillage(
        paramVillage: Village,
        dummyCharaName: String?,
        dummyCharaShortName: String?,
        dummyCharaImage: MultipartFile?,
        joinMessage: String
    ): Village {
        // オリジナル画像を使用する場合はキャラチップ登録
        val param = if (paramVillage.setting.chara.isOriginalCharachip) {
            val charachip = registerOriginalCharachip(paramVillage.name)
            paramVillage.copy(
                setting = paramVillage.setting.copy(
                    chara = VillageCharaSetting(
                        isOriginalCharachip = true,
                        charachipIds = listOf(charachip.id),
                        dummyCharaId = 1 // dummy
                    )
                )
            )
        } else paramVillage
        // 村登録
        val village = villageService.registerVillage(param)
        // シスメ登録
        messageService.registerMessage(village, Message.ofVillageInitialMessage())
        // ダミーキャラ参加
        participateDummyChara(village, dummyCharaName, dummyCharaShortName, dummyCharaImage, joinMessage)
        // 誰歓ならツイート
        tweetService.tweetCreateVillageIfNeeded(village)
        return village
    }

    fun getAttackableTargets(village: Village, myself: VillageParticipant?, charaId: Int): VillageParticipants {
        myself ?: throw WolfMansionBusinessException("ログインしてください")
        val abilities = abilityService.findAbilities(village.id)
        val votes = voteService.findVotes(village.id)
        attackDomainService.assertAttacker(village, myself, charaId, abilities)
        val attacker = village.participants.chara(charaId)
        val list = attackDomainService.getSelectableTargetList(village, attacker, abilities, votes)
        return VillageParticipants(count = list.size, list = list)
    }

    fun getSelectableFootstepList(
        village: Village,
        myself: VillageParticipant?,
        charaId: Int?,
        targetCharaId: Int?
    ): List<String> {
        myself ?: throw WolfMansionBusinessException("ログインしてください")
        abilityDomainService.assertGetSelectableFootsteps(village, myself)
        return footstepDomainService.getCandidateList(village, charaId ?: myself.charaId, targetCharaId)
    }

    fun findParticipantSituation(
        village: Village,
        username: String?,
        myself: VillageParticipant?,
        votes: Votes,
        abilities: Abilities,
        footsteps: Footsteps,
        charachips: Charachips,
        day: Int
    ): ParticipantSituation {
        val player: Player? = username?.let { playerService.findPlayer(it) }
        val commits = commitService.findCommits(village.id)
        val latestDayMessageCountMap = myself?.let {
            messageService.findParticipantDayMessageCount(village, village.latestDay(), it)
        }

        return ParticipantSituation(
            participate = participateDomainService.convertToSituation(village, myself, player, charachips),
            skillRequest = skillRequestDomainService.convertToSituation(village, myself, myself?.requestSkill),
            commit = commitDomainService.convertToSituation(village, myself, commits),
            say = sayDomainService.convertToSituation(village, myself, charachips, day, latestDayMessageCountMap),
            rp = rpDomainService.convertToSituation(village, myself, charachips, day),
            ability = abilityDomainService.convertToParticipantSituation(
                village,
                myself,
                abilities,
                votes,
                footsteps,
                day
            ),
            vote = voteDomainService.convertToParticipantSituation(village, myself, votes),
            admin = adminDomainService.convertToSituation(village, myself),
            creator = creatorDomainService.convertToSituation(village, player)
        )
    }

    fun findVillageSituation(
        village: Village,
        myself: VillageParticipant?,
        votes: Votes,
        abilities: Abilities,
        footsteps: Footsteps,
        day: Int
    ): VillageSituation {
        return VillageSituation(
            roomAssigned = roomDomainService.convertToSituation(village, day),
            live = VillageParticipantLiveSituation(village),
            footstep = footstepDomainService.convertToSituation(village, myself, footsteps, day),
            vote = voteDomainService.convertToVillageSituation(village, votes, abilities, day),
            whole = abilityDomainService.convertToVillageSituation(village, myself, abilities, day)
        )
    }

    private fun participateDummyChara(
        village: Village,
        dummyCharaName: String?,
        dummyCharaShortName: String?,
        dummyCharaImage: MultipartFile?,
        joinMessage: String
    ) {
        val player = playerService.findPlayer(1)
        val participant = participate(
            village = village,
            player = player,
            charaId = village.setting.chara.dummyCharaId,
            charaName = dummyCharaName,
            charaShortName = dummyCharaShortName,
            charaImageFile = dummyCharaImage,
            firstRequestSkill = Skill(CDef.Skill.おまかせ),
            secondRequestSkill = Skill(CDef.Skill.おまかせ),
            joinMessage = joinMessage,
            joinPassword = village.setting.joinPassword,
            isSpectator = false,
            ipAddress = "dummy"
        )
        if (village.setting.chara.isOriginalCharachip) {
            villageService.updateDummyCharaId(village.id, participant.charaId)
        }
    }

    private fun registerOriginalCharachip(
        name: String,
    ): Charachip {
        return charaService.registerOriginalCharachip(name)
    }
}