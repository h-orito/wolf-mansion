package com.ort.app.application.service

import com.ort.app.domain.model.message.Message
import com.ort.app.domain.model.message.MessageQuery
import com.ort.app.domain.model.message.MessageRepository
import com.ort.app.domain.model.message.MessageType
import com.ort.app.domain.model.message.Messages
import com.ort.app.domain.model.message.toModel
import com.ort.app.domain.model.player.Authority
import com.ort.app.domain.model.player.Player
import com.ort.app.domain.model.skill.Skill
import com.ort.app.domain.model.skill.SkillHistories
import com.ort.app.domain.model.translate.TranslateRepository
import com.ort.app.domain.model.village.Village
import com.ort.app.domain.model.village.createDay1Village
import com.ort.app.domain.model.village.createVillageParticipant
import com.ort.app.domain.model.village.participant.VillageParticipant
import com.ort.app.domain.model.village.participant.VillageParticipants
import com.ort.app.domain.model.village.participant.dead.Dead
import com.ort.app.domain.model.village.participant.dead.DeadHistories
import com.ort.app.domain.model.village.participant.dead.DeadReason
import com.ort.app.domain.model.village.room.Room
import com.ort.app.domain.model.village.toModel
import com.ort.app.domain.service.MessageDomainService
import com.ort.app.domain.service.SayDomainService
import com.ort.app.domain.service.message.say.ActionSayDomainService
import com.ort.app.domain.service.message.say.GraveSayDomainService
import com.ort.app.domain.service.message.say.LoversSayDomainService
import com.ort.app.domain.service.message.say.MonologueSayDomainService
import com.ort.app.domain.service.message.say.NormalSayDomainService
import com.ort.app.domain.service.message.say.SecretSayDomainService
import com.ort.app.domain.service.message.say.SpectateSayDomainService
import com.ort.app.domain.service.message.say.SympathizeSayDomainService
import com.ort.app.domain.service.message.say.TelepathyDomainService
import com.ort.app.domain.service.message.say.WerewolfSayDomainService
import com.ort.app.domain.service.message.system.AttackMessageDomainService
import com.ort.app.domain.service.message.system.CoronerMessageDomainService
import com.ort.app.domain.service.message.system.DivineMessageDomainService
import com.ort.app.domain.service.message.system.FoxMessageDomainService
import com.ort.app.domain.service.message.system.GuruMessageDomainService
import com.ort.app.domain.service.message.system.InvestigateMessageDomainService
import com.ort.app.domain.service.message.system.LoversMessageDomainService
import com.ort.app.domain.service.message.system.PrivateAbilityMessageDomainService
import com.ort.app.domain.service.message.system.PrivateSystemMessageDomainService
import com.ort.app.domain.service.message.system.PsychicMessageDomainService
import com.ort.app.domain.service.message.system.WiseMessageDomainService
import com.ort.dbflute.allcommon.CDef
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import java.time.LocalDateTime

/**
 * 新着検知 (latest-datetime) が「見られない発言の時刻を返さない」ことを、
 * 閲覧者・村の状態ごとに DB へ渡す検索条件 (種別と自分宛て/自分発の取り込み) で固定する。
 *
 * 新着検知は簡易な村 (参加者は部屋・ステータス・履歴なし) と履歴を読まない閲覧者で判定するため、
 * 全情報を読んだ村・閲覧者と判定結果が一致することもあわせて確認する。
 */
internal class LatestMessageDatetimeVisibilityTest {
    private val messageDomainService =
        MessageDomainService(
            SayDomainService(
                NormalSayDomainService(),
                GraveSayDomainService(),
                MonologueSayDomainService(),
                SpectateSayDomainService(),
                WerewolfSayDomainService(),
                SympathizeSayDomainService(),
                TelepathyDomainService(),
                LoversSayDomainService(),
                SecretSayDomainService(),
                ActionSayDomainService(),
            ),
            ActionSayDomainService(),
            PsychicMessageDomainService(),
            GuruMessageDomainService(),
            AttackMessageDomainService(),
            CoronerMessageDomainService(),
            DivineMessageDomainService(),
            WiseMessageDomainService(),
            InvestigateMessageDomainService(),
            LoversMessageDomainService(),
            FoxMessageDomainService(),
            PrivateAbilityMessageDomainService(),
            PrivateSystemMessageDomainService(),
            mock(TranslateRepository::class.java),
        )

    /** DB に渡された検索条件を記録するだけのリポジトリ */
    private class CapturingMessageRepository : MessageRepository {
        var captured: MessageQuery? = null

        override fun findLatestMessageDatetime(
            myself: VillageParticipant?,
            query: MessageQuery,
        ): LocalDateTime? {
            captured = query
            return null
        }

        override fun findMessages(
            village: Village,
            myself: VillageParticipant?,
            query: MessageQuery,
        ): Messages = throw UnsupportedOperationException()

        override fun findMessage(
            village: Village,
            messageType: CDef.MessageType,
            messageNumber: Int,
        ): Message? = throw UnsupportedOperationException()

        override fun findParticipantDayMessageCount(
            village: Village,
            day: Int,
            participant: VillageParticipant,
        ): Map<CDef.MessageType, Int> = throw UnsupportedOperationException()

        override fun registerMessage(
            village: Village,
            message: Message,
        ): Message = throw UnsupportedOperationException()
    }

    /** 判定結果。DB に渡る検索条件のうち公開範囲に関わる部分 */
    private data class Visibility(
        val types: Set<CDef.MessageType>,
        val includeMonologue: Boolean,
        val includeSecret: Boolean,
        val includePrivateAbility: Boolean,
    )

    private fun visibility(
        village: Village,
        myself: VillageParticipant?,
        player: Player?,
        day: Int = 1,
        fromParticipantIds: List<Int> = emptyList(),
        requestTypes: List<CDef.MessageType> = emptyList(),
    ): Visibility {
        val repository = CapturingMessageRepository()
        val query =
            MessageQuery(
                village = village,
                day = day,
                pageSize = null,
                pageNum = null,
                fromParticipantIds = fromParticipantIds,
                toParticipantIds = emptyList(),
                requestTypes = requestTypes.map { it.toModel() },
                keywords = null,
                isPaging = false,
                isDispLatest = false,
            )
        MessageService(messageDomainService, repository).findLatestMessageDatetime(village, myself, player, query)
        val captured = requireNotNull(repository.captured)
        return Visibility(
            types = captured.messageTypeList.map { it.toCdef() }.toSet(),
            includeMonologue = captured.includeMonologue,
            includeSecret = captured.includeSecret,
            includePrivateAbility = captured.includePrivateAbility,
        )
    }

    // ------------------------------------------------------------------
    // fixtures

    private val creatorName = "creator name"

    private fun village(
        status: CDef.VillageStatus = CDef.VillageStatus.進行中,
        isVisibleGraveSpectateMessage: Boolean = false,
        isCreatorIsProducer: Boolean = false,
    ): Village {
        val base = createDay1Village()
        return base.copy(
            createPlayerName = creatorName,
            status = status.toModel(),
            setting =
                base.setting.copy(
                    rule =
                        base.setting.rule.copy(
                            isVisibleGraveSpectateMessage = isVisibleGraveSpectateMessage,
                            isCreatorIsProducer = isCreatorIsProducer,
                        ),
                ),
        )
    }

    /** 新着検知で読む村: 参加者・見学者は簡易版 (部屋・ステータス・履歴を持たない) */
    private fun Village.light(): Village =
        copy(
            participants = participants.simple(),
            spectators = spectators.simple(),
        )

    /** 村一覧と同じ簡易な参加者 (VillagePlayerDataSource.mapSimpleVillageParticipant と同じ欠け方) */
    private fun VillageParticipants.simple(): VillageParticipants =
        copy(
            list =
                list.map {
                    it.copy(
                        skill = it.skill?.copy(histories = SkillHistories(list = emptyList())),
                        room = null,
                        status =
                            it.status.copy(
                                loverIdList = emptyList(),
                                foxPossessionedIdList = emptyList(),
                                insanedIdList = emptyList(),
                                persuadedIdList = emptyList(),
                                insuranceIdList = emptyList(),
                                disrespectfulList = emptyList(),
                                hasCurseMark = false,
                                hasCounterCurseMark = false,
                                hasTelekinesis = false,
                            ),
                        dead = it.dead.copy(histories = DeadHistories(list = emptyList())),
                        ipAddresses = emptyList(),
                        notification = null,
                    )
                },
        )

    /** 新着検知で読む閲覧者: 死亡・部屋・役職の履歴、IP、通知設定を読まない */
    private fun VillageParticipant.light(): VillageParticipant =
        copy(
            skill = skill?.copy(histories = SkillHistories(list = emptyList())),
            room = room?.let { Room(number = it.number, histories = it.histories.copy(list = emptyList())) },
            dead = dead.copy(histories = DeadHistories(list = emptyList())),
            ipAddresses = emptyList(),
            notification = null,
        )

    private fun player(name: String = "viewer"): Player =
        Player(
            id = 50,
            name = name,
            twitterUserName = null,
            introduction = null,
            authority = Authority(CDef.Authority.プレイヤー.code(), CDef.Authority.プレイヤー.alias()),
            isRestrictedParticipation = false,
            shouldCheckAccessInfo = false,
        )

    private fun participant(
        skill: CDef.Skill = CDef.Skill.村人,
        id: Int = 30,
    ): VillageParticipant = createVillageParticipant(skill = Skill(skill), id = id)

    private fun VillageParticipant.deadBy(reason: CDef.DeadReason): VillageParticipant =
        copy(dead = Dead(isDead = true, deadDay = 1, reason = DeadReason(reason), histories = DeadHistories(emptyList())))

    private fun VillageParticipant.withLover(): VillageParticipant = copy(status = status.copy(loverIdList = listOf(99)))

    private fun VillageParticipant.foxPossessioned(): VillageParticipant = copy(status = status.copy(foxPossessionedIdList = listOf(99)))

    /** 全員から見える種別 */
    private val publicTypes =
        setOf(
            CDef.MessageType.通常発言,
            CDef.MessageType.村建て発言,
            CDef.MessageType.公開システムメッセージ,
            CDef.MessageType.アクション,
        )
    private val allTypes = CDef.MessageType.listAll().toSet()

    private val anonymous = Visibility(publicTypes, includeMonologue = false, includeSecret = false, includePrivateAbility = false)

    private fun participantSees(types: Set<CDef.MessageType>) =
        Visibility(types, includeMonologue = true, includeSecret = true, includePrivateAbility = true)

    /** 全情報の村・閲覧者と軽量な村・閲覧者で判定が一致することを確認して、判定結果を返す */
    private fun assertSameWhenLight(
        village: Village,
        myself: VillageParticipant?,
        player: Player?,
        day: Int = 1,
    ): Visibility {
        val full = visibility(village, myself, player, day)
        val light = visibility(village.light(), myself?.light(), player, day)
        assertEquals(full, light)
        return full
    }

    // ------------------------------------------------------------------
    // 未ログイン・非参加者

    @Test
    @DisplayName("進行中の村を未ログインで見ると、公開されている種別だけ")
    fun anonymousInProgress() {
        assertEquals(anonymous, assertSameWhenLight(village(), null, null))
    }

    @Test
    @DisplayName("進行中でも 0 日目 (プロローグ) なら見学発言が見える")
    fun anonymousInProgressDay0() {
        assertEquals(
            anonymous.copy(types = publicTypes + CDef.MessageType.見学発言),
            assertSameWhenLight(village(), null, null, day = 0),
        )
    }

    @Test
    @DisplayName("墓下・見学発言を公開する村では、未ログインでも死者の呻きと見学発言が見える")
    fun anonymousWithVisibleGraveSpectate() {
        assertEquals(
            anonymous.copy(types = publicTypes + CDef.MessageType.死者の呻き + CDef.MessageType.見学発言),
            assertSameWhenLight(village(isVisibleGraveSpectateMessage = true), null, null),
        )
    }

    @Test
    @DisplayName("募集中の村は、未ログインでも見学発言が見える")
    fun anonymousInPrologue() {
        assertEquals(
            anonymous.copy(types = publicTypes + CDef.MessageType.見学発言),
            assertSameWhenLight(village(status = CDef.VillageStatus.募集中), null, null, day = 0),
        )
    }

    @Test
    @DisplayName("エピローグ・終了・廃村の村は、未ログインでも全種別が見える")
    fun anonymousAfterSettled() {
        listOf(CDef.VillageStatus.エピローグ, CDef.VillageStatus.終了, CDef.VillageStatus.廃村).forEach {
            assertEquals(anonymous.copy(types = allTypes), assertSameWhenLight(village(status = it), null, null), it.name)
        }
    }

    @Test
    @DisplayName("ログインしていても参加していなければ、未ログインと同じ")
    fun loggedInNonParticipant() {
        assertEquals(anonymous, assertSameWhenLight(village(), null, player()))
    }

    @Test
    @DisplayName("村建てでも、村建てが見物人になる設定でなければ未ログインと同じ")
    fun creatorWithoutProducerSetting() {
        assertEquals(anonymous, assertSameWhenLight(village(), null, player(name = creatorName)))
    }

    @Test
    @DisplayName("村建てが見物人になる設定の村建ては、進行中でも全種別が見える")
    fun producer() {
        assertEquals(
            anonymous.copy(types = allTypes),
            assertSameWhenLight(village(isCreatorIsProducer = true), null, player(name = creatorName)),
        )
    }

    @Test
    @DisplayName("村建てが見物人になる設定でも、村建て以外のプレイヤーには公開分だけ")
    fun producerSettingOtherPlayer() {
        assertEquals(anonymous, assertSameWhenLight(village(isCreatorIsProducer = true), null, player()))
    }

    // ------------------------------------------------------------------
    // 参加者

    @Test
    @DisplayName("村人は公開分に加えて、自分の独り言・自分が関わる秘話・自分の能力結果だけを取り込む")
    fun villager() {
        assertEquals(participantSees(publicTypes), assertSameWhenLight(village(), participant(), player()))
    }

    @Test
    @DisplayName("人狼は囁きと襲撃結果が見え、共鳴・念話・恋人・死者の呻きは見えない")
    fun werewolf() {
        val result = assertSameWhenLight(village(), participant(CDef.Skill.人狼), player())
        assertTrue(result.types.containsAll(publicTypes + CDef.MessageType.人狼の囁き + CDef.MessageType.襲撃結果))
        listOf(
            CDef.MessageType.共鳴発言,
            CDef.MessageType.念話,
            CDef.MessageType.恋人発言,
            CDef.MessageType.死者の呻き,
            CDef.MessageType.見学発言,
            CDef.MessageType.独り言,
            CDef.MessageType.秘話,
        ).forEach { assertFalse(result.types.contains(it), it.name) }
    }

    @Test
    @DisplayName("梟は囁き・共鳴・恋人・念話が見える")
    fun owl() {
        val result = assertSameWhenLight(village(), participant(CDef.Skill.梟), player())
        assertTrue(result.types.containsAll(publicTypes + MessageType.owlViewableSayTypeList))
        assertFalse(result.types.contains(CDef.MessageType.死者の呻き))
    }

    @Test
    @DisplayName("梟が人で絞り込むと、誰が囁いたか分かってしまうため梟が見られる種別は一切見えない")
    fun owlFilteredByParticipant() {
        val owl = participant(CDef.Skill.梟)
        val result = visibility(village(), owl, player(), fromParticipantIds = listOf(2))
        MessageType.owlViewableSayTypeList.forEach { assertFalse(result.types.contains(it), it.name) }
    }

    @Test
    @DisplayName("死亡者 (突然死以外) は死者の呻きと見学発言が見える")
    fun dead() {
        val result = assertSameWhenLight(village(), participant().deadBy(CDef.DeadReason.処刑), player())
        assertEquals(participantSees(publicTypes + CDef.MessageType.死者の呻き + CDef.MessageType.見学発言), result)
    }

    @Test
    @DisplayName("突然死した参加者は死者の呻きも見学発言も見えない")
    fun suddenlyDead() {
        val result = assertSameWhenLight(village(), participant().deadBy(CDef.DeadReason.突然), player())
        assertEquals(participantSees(publicTypes), result)
    }

    @Test
    @DisplayName("見学者は死者の呻きと見学発言が見える")
    fun spectator() {
        val result = assertSameWhenLight(village(), participant().copy(isSpectator = true), player())
        assertEquals(participantSees(publicTypes + CDef.MessageType.死者の呻き + CDef.MessageType.見学発言), result)
    }

    @Test
    @DisplayName("恋人がいる参加者は恋人発言が見える (恋人メッセージは自分宛ての能力結果として取り込む)")
    fun lover() {
        val result = assertSameWhenLight(village(), participant().withLover(), player())
        assertEquals(participantSees(publicTypes + CDef.MessageType.恋人発言), result)
    }

    @Test
    @DisplayName("狐憑きの参加者は念話が見える (妖狐メッセージは自分宛ての能力結果として取り込む)")
    fun foxPossessioned() {
        val result = assertSameWhenLight(village(), participant().foxPossessioned(), player())
        assertEquals(participantSees(publicTypes + CDef.MessageType.念話), result)
    }

    @Test
    @DisplayName("管理者 (player_id=1) は進行中でも全種別が見える")
    fun admin() {
        val admin = participant(id = 30).copy(playerId = 1)
        val result = assertSameWhenLight(village(), admin, player())
        assertEquals(allTypes, result.types)
    }
}
