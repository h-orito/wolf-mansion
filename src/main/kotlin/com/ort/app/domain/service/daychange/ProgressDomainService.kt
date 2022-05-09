package com.ort.app.domain.service.daychange

import com.ort.app.domain.model.chara.Charas
import com.ort.app.domain.model.commit.Commits
import com.ort.app.domain.model.daychange.Daychange
import com.ort.app.domain.model.message.Message
import com.ort.app.domain.model.village.Village
import com.ort.app.domain.service.FootstepDomainService
import com.ort.app.domain.service.VoteDomainService
import com.ort.app.domain.service.ability.*
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class ProgressDomainService(
    private val attackDomainService: AttackDomainService,
    private val bombDomainService: BombDomainService,
    private val pushpinDomainService: PushpinDomainService,
    private val drawerDomainService: DrawerDomainService,
    private val cheatDomainService: CheatDomainService,
    private val insaneDomainService: InsaneDomainService,
    private val persuadeDomainService: PersuadeDomainService,
    private val insuranceDomainService: InsuranceDomainService,
    private val cohabitDomainService: CohabitDomainService,
    private val courtDomainService: CourtDomainService,
    private val divineDomainService: DivineDomainService,
    private val badgerGameDomainService: BadgerGameDomainService,
    private val fruitsBasketDomainService: FruitsBasketDomainService,
    private val guardDomainService: GuardDomainService,
    private val wandererDomainService: WandererDomainService,
    private val investigateDomainService: InvestigateDomainService,
    private val loneAttackDomainService: LoneAttackDomainService,
    private val beatDomainService: BeatDomainService,
    private val huntingDomainService: HuntingDomainService,
    private val seduceDomainService: SeduceDomainService,
    private val stalkingDomainService: StalkingDomainService,
    private val trapDomainService: TrapDomainService,
    private val wallPunchDomainService: WallPunchDomainService,
    private val psychicDomainService: PsychicDomainService,
    private val autopsyDomainService: AutopsyDomainService,
    private val bakeryDomainService: BakeryDomainService,
    private val gonfoxDomainService: GonfoxDomainService,
    private val falseChargesDomainService: FalseChargesDomainService,
    private val rainbowDomainService: RainbowDomainService,
    private val loudSpeakDomainService: LoudSpeakDomainService,
    private val shoutDomainService: ShoutDomainService,
    private val translateDomainService: TranslateDomainService,
    private val forceReincarnationDomainService: ForceReincarnationDomainService,
    private val giveBabaDomainService: GiveBabaDomainService,
    private val giveWinDomainService: GiveWinDomainService,
    private val resuscitateDomainService: ResuscitateDomainService,
    private val necromanceDomainService: NecromanceDomainService,
    private val yubisashiDomainService: YubisashiDomainService,
    private val loveStealDomainService: LoveStealDomainService,
    private val breakupDomainService: BreakupDomainService,
    private val revivalDomainService: RevivalDomainService,
    private val suicideDomainService: SuicideDomainService,
    private val epilogueDomainService: EpilogueDomainService,
    private val abilityDomainService: AbilityDomainService,
    private val voteDomainService: VoteDomainService,
    private val suddenlyDeathDomainService: SuddenlyDeathDomainService,
    private val miserableDomainService: MiserableDomainService,
    private val executeDomainService: ExecuteDomainService,
    private val resentDomainService: ResentDomainService,
    private val footstepDomainService: FootstepDomainService
) {

    fun changeDayIfNeeded(daychange: Daychange, commits: Commits, charas: Charas): Daychange {
        if (!shouldChangeDay(daychange.village, commits)) return daychange
        return changeDay(daychange.copy(village = daychange.village.addNewDay()), charas)
    }

    fun changeDay(beforeDaychange: Daychange, charas: Charas): Daychange {
        // 虹塗り
        var daychange = rainbowDomainService.rainbow(beforeDaychange)
        // 拡声
        daychange = loudSpeakDomainService.loudSpeak(daychange)
        // 叫び
        daychange = shoutDomainService.shout(daychange)
        // 翻訳
        daychange = translateDomainService.translate(daychange)
        // 突然死
        daychange = suddenlyDeathDomainService.deadIfNeeded(daychange)
        // 破局
        daychange = breakupDomainService.breakup(daychange)
        // 恋泥棒
        daychange = loveStealDomainService.stealLove(daychange)
        // 求愛
        daychange = courtDomainService.court(daychange)
        // ストーキング
        daychange = stalkingDomainService.stalking(daychange)
        // 誘惑
        daychange = seduceDomainService.seduce(daychange)
        // 美人局
        daychange = badgerGameDomainService.badgerGame(daychange)
        // 誑かす
        daychange = cheatDomainService.cheat(daychange)
        // 唆す
        daychange = insaneDomainService.insane(daychange)
        // 説得する
        daychange = persuadeDomainService.persuade(daychange)
        // 保険屋
        daychange = insuranceDomainService.insurance(daychange)
        // 罠、爆弾メッセージ
        daychange = trapDomainService.addTrapMessages(daychange)
        daychange = bombDomainService.addBombMessages(daychange)
        // 処刑
        daychange = executeDomainService.execute(daychange)
        // 怨恨
        daychange = resentDomainService.resent(daychange)
        // 護衛
        daychange = guardDomainService.guard(daychange)
        daychange = wandererDomainService.wandererGuard(daychange)
        // 壁殴り
        daychange = wallPunchDomainService.wallPunch(daychange)
        // 占い、呪殺、逆呪殺
        daychange = divineDomainService.divine(daychange)
        // 捜査
        daychange = investigateDomainService.investigate(daychange)
        // 霊能
        daychange = psychicDomainService.psychic(daychange)
        // 同棲メッセージ
        daychange = cohabitDomainService.cohabit(daychange)
        // 襲撃
        daychange = attackDomainService.attack(daychange, charas)
        // 美人局襲撃
        daychange = badgerGameDomainService.badgerGameAttack(daychange)
        // 単独襲撃
        daychange = loneAttackDomainService.loneAttack(daychange, charas)
        // 狩猟
        daychange = huntingDomainService.hunting(daychange)
        // 殴打
        daychange = beatDomainService.beat(daychange, charas)
        // 罠発動
        daychange = trapDomainService.trap(daychange)
        // 爆弾発動
        daychange = bombDomainService.bomb(daychange)
        // 画鋲
        daychange = pushpinDomainService.pushpin(daychange)
        // 箪笥
        daychange = drawerDomainService.littleFinger(daychange)
        // 無惨メッセージ
        daychange = miserableDomainService.addMiserableMessages(daychange)
        // 検死
        daychange = autopsyDomainService.autopsy(daychange)
        // 強制転生
        daychange = forceReincarnationDomainService.forceReincarnation(daychange)
        // ババを渡す
        daychange = giveBabaDomainService.giveBaba(daychange)
        // 当選権利を譲る
        daychange = giveWinDomainService.giveWin(daychange)
        // 蘇生
        daychange = resuscitateDomainService.resuscitate(daychange)
        // 死霊蘇生
        daychange = necromanceDomainService.necromance(daychange)
        // 復活
        daychange = revivalDomainService.revival(daychange)
        // 後追い
        daychange = suicideDomainService.suicide(daychange)
        // 2日目専用メッセージ
        daychange = addDay2MessageIfNeeded(daychange)
        // 勝敗判定
        daychange = epilogueDomainService.transitionToEpilogueIfNeeded(daychange)
        if (daychange.village.status.isEpilogue()) return daychange
        // パン屋
        daychange = bakeryDomainService.addBakeryMessage(daychange)
        // ごん
        daychange = gonfoxDomainService.addGonfoxMessage(daychange)
        // 指差死
        daychange = yubisashiDomainService.yubisashi(daychange)
        // 冤罪者の足音発生
        daychange = falseChargesDomainService.falseCharges(daychange)
        // 生存者と足音メッセージ
        daychange = addAlivePlayersMessage(daychange)
        daychange = addFootstepsMessage(daychange)
        // フルーツバスケット
        daychange = fruitsBasketDomainService.fruitBasket(daychange)
        // デフォルト能力
        daychange = abilityDomainService.addDefaultAbilities(daychange)
        // デフォルト投票
        daychange = voteDomainService.addDefaultVotes(daychange)

        return daychange
    }

    private fun shouldChangeDay(village: Village, commits: Commits): Boolean =
        !LocalDateTime.now().isBefore(village.days.latestDay().dayChangeDatetime)
                || isAllLivingPlayerCommitted(village, commits)

    // 全員コミットしている
    private fun isAllLivingPlayerCommitted(village: Village, commits: Commits): Boolean {
        if (!village.setting.rule.isAvailableCommit) return false
        // 全員コミットしているか
        val commitNum: Int = commits.filterByDay(village.latestDay()).list.size
        val livingPersonNum = village.participants
            .filterAlive()
            .filterNotDummy(village.dummyParticipant()).list.size
        return commitNum >= livingPersonNum
    }

    private fun addDay2MessageIfNeeded(daychange: Daychange): Daychange {
        if (daychange.village.latestDay() != 2) return daychange
        val text = daychange.village.getDay2Message()
        return daychange.copy(
            messages = daychange.messages.add(
                Message.ofSystemMessage(
                    day = daychange.village.latestDay(),
                    message = text
                )
            )
        )
    }

    private fun addAlivePlayersMessage(daychange: Daychange): Daychange {
        val text = daychange.village.getAliveParticipantsMessage()
        return daychange.copy(
            messages = daychange.messages.add(
                Message.ofSystemMessage(
                    day = daychange.village.latestDay(),
                    message = text
                )
            )
        )
    }

    private fun addFootstepsMessage(daychange: Daychange): Daychange {
        val text = footstepDomainService.getDisplayFootstepString(
            daychange.village,
            daychange.footsteps,
            daychange.village.latestDay()
        )
        return daychange.copy(
            messages = daychange.messages.add(
                Message.ofSystemMessage(
                    day = daychange.village.latestDay(),
                    message = text
                )
            )
        )
    }
}