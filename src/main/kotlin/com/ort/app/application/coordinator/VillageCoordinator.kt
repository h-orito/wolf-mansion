package com.ort.app.application.coordinator

import com.ort.app.application.service.AbilityApplicationService
import com.ort.app.application.service.CharaService
import com.ort.app.application.service.CommitService
import com.ort.app.application.service.FootstepApplicationService
import com.ort.app.application.service.MessageService
import com.ort.app.application.service.PlayerService
import com.ort.app.application.service.TweetService
import com.ort.app.application.service.VillageApplicationService
import com.ort.app.application.service.VoteApplicationService
import com.ort.app.domain.model.ability.Abilities
import com.ort.app.domain.model.chara.Charachip
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
import com.ort.app.domain.model.vote.Vote
import com.ort.app.domain.model.vote.Votes
import com.ort.app.domain.service.AdminDomainService
import com.ort.app.domain.service.CommitDomainService
import com.ort.app.domain.service.CreatorDomainService
import com.ort.app.domain.service.FootstepDomainService
import com.ort.app.domain.service.MessageDomainService
import com.ort.app.domain.service.ParticipateDomainService
import com.ort.app.domain.service.RpDomainService
import com.ort.app.domain.service.SayDomainService
import com.ort.app.domain.service.SkillRequestDomainService
import com.ort.app.domain.service.VoteDomainService
import com.ort.app.domain.service.ability.AbilityDomainService
import com.ort.app.domain.service.ability.AttackDomainService
import com.ort.app.domain.service.room.RoomDomainService
import com.ort.app.web.exception.WolfMansionBusinessException
import com.ort.dbflute.allcommon.CDef
import com.ort.fw.security.UserInfo
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class VillageCoordinator(
    // application service
    private val villageService: VillageApplicationService,
    private val playerService: PlayerService,
    private val commitService: CommitService,
    private val messageService: MessageService,
    private val charaService: CharaService,
    private val tweetService: TweetService,
    private val abilityService: AbilityApplicationService,
    private val footstepService: FootstepApplicationService,
    private val voteService: VoteApplicationService,
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
        charaId: Int,
        firstRequestSkill: Skill,
        secondRequestSkill: Skill,
        joinMessage: String,
        joinPassword: String?,
        isSpectator: Boolean
    ) {
        assertParticipate(village, player, charaId, joinPassword, isSpectator)
        val chara = charaService.findChara(charaId) ?: throw IllegalStateException("chara not found.")
        val participant = villageService.participate(
            village.id, player.id, chara, firstRequestSkill, secondRequestSkill, isSpectator
        )
        val afterVillage = villageService.findVillage(village.id)!!
        // N人目シスメ
        messageService.registerMessage(
            afterVillage,
            messageDomainService.createParticipateSystemMessage(afterVillage, participant, isSpectator)
        )
        // 参加発言
        messageService.registerMessage(
            afterVillage,
            messageDomainService.createJoinMessage(afterVillage, participant, isSpectator, joinMessage)
        )
        if (!isSpectator) {
            // 希望役職シスメ
            messageService.registerMessage(afterVillage, messageDomainService.createSkillRequestMessage(participant))
            // 人数が揃ったらツイート
            tweetService.tweetParticipantEnoughIfNeeded(afterVillage)
        }
    }

    fun assertParticipate(
        village: Village,
        player: Player,
        charaId: Int,
        joinPassword: String?,
        isSpectator: Boolean
    ) {
        if (isSpectator) {
            val charachip = charaService.findCharachip(village.setting.charachipId)
                ?: throw IllegalStateException("charachip not found.")
            participateDomainService.assertSpectate(village, player, charaId, joinPassword, charachip)
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
        charaId: Int?,
        targetCharaId: Int?,
        footstep: String?
    ) {
        myself ?: throw WolfMansionBusinessException("ログインしてください")
        val abilities = abilityService.findAbilities(village.id)
        val footsteps = footstepService.findFootsteps(village.id)
        abilityDomainService.assertAbility(village, myself, charaId, targetCharaId, footstep, abilities, footsteps)
        abilityService.updateAbility(village, myself, charaId, targetCharaId, footstep)
        footstepService.updateFootstep(village, myself, footstep)
        messageService.registerMessage(
            village,
            abilityDomainService.createSetMessage(village, myself, charaId, targetCharaId, footstep)
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

    fun assertCreateVillage(player: Player, max: Int, charachip: Charachip) {
        if (!player.isAvailableCreateVillage()) {
            throw WolfMansionBusinessException("村建てした村の決着がつくまでは村を建てられません。")
        }
        if (charachip.charas.list.size < max) {
            throw WolfMansionBusinessException("定員に対してキャラ数が不足しています。")
        }
    }

    @Transactional(rollbackFor = [Exception::class, WolfMansionBusinessException::class])
    fun registerVillage(paramVillage: Village, joinMessage: String): Village {
        // 村登録
        val village = villageService.registerVillage(paramVillage)
        // シスメ登録
        messageService.registerMessage(village, Message.ofVillageInitialMessage())
        // ダミーキャラ参加
        participateDummyChara(village, joinMessage)
        // 誰歓ならツイート
        tweetService.tweetCreateVillageIfNeeded(village)
        return village
    }

    fun getAttackableTargets(village: Village, myself: VillageParticipant?, charaId: Int): VillageParticipants {
        myself ?: throw WolfMansionBusinessException("ログインしてください")
        val abilities = abilityService.findAbilities(village.id)
        attackDomainService.assertAttacker(village, myself, charaId, abilities)
        val attacker = village.participants.chara(charaId)
        val list = attackDomainService.getSelectableTargetList(village, attacker, abilities)
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
        userInfo: UserInfo?,
        myself: VillageParticipant?,
        votes: Votes,
        abilities: Abilities,
        footsteps: Footsteps,
        charachip: Charachip,
        day: Int
    ): ParticipantSituation {
        val player: Player? = userInfo?.let { playerService.findPlayer(it.username) }
        val commits = commitService.findCommits(village.id)
        val latestDayMessageCountMap = myself?.let {
            messageService.findParticipantDayMessageCount(village, village.latestDay(), it)
        }

        return ParticipantSituation(
            participate = participateDomainService.convertToSituation(village, myself, player, charachip),
            skillRequest = skillRequestDomainService.convertToSituation(village, myself, myself?.requestSkill),
            commit = commitDomainService.convertToSituation(village, myself, commits),
            say = sayDomainService.convertToSituation(village, myself, charachip, day, latestDayMessageCountMap),
            rp = rpDomainService.convertToSituation(village, myself, charachip, day),
            ability = abilityDomainService.convertToParticipantSituation(village, myself, abilities, footsteps, day),
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
            vote = voteDomainService.convertToVillageSituation(village, votes, day),
            whole = abilityDomainService.convertToVillageSituation(village, myself, abilities, day)
        )
    }

    private fun participateDummyChara(village: Village, joinMessage: String) {
        val player = playerService.findPlayer(1)
        participate(
            village = village,
            player = player,
            charaId = village.setting.dummyCharaId,
            firstRequestSkill = Skill(CDef.Skill.おまかせ),
            secondRequestSkill = Skill(CDef.Skill.おまかせ),
            joinMessage = joinMessage,
            joinPassword = village.setting.joinPassword,
            isSpectator = false
        )
    }
}