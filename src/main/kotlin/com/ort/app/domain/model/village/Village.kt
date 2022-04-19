package com.ort.app.domain.model.village

import com.ort.app.domain.model.camp.Camp
import com.ort.app.domain.model.camp.toModel
import com.ort.app.domain.model.message.MessageType
import com.ort.app.domain.model.skill.Skill
import com.ort.app.domain.model.skill.Skills
import com.ort.app.domain.model.skill.toModel
import com.ort.app.domain.model.village.participant.VillageParticipant
import com.ort.app.domain.model.village.participant.VillageParticipants
import com.ort.app.domain.model.village.room.RoomSize
import com.ort.app.domain.model.village.setting.SayRestriction
import com.ort.app.fw.exception.WolfMansionBusinessException
import com.ort.dbflute.allcommon.CDef
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class Village(
    val id: Int,
    val name: String,
    val createPlayerName: String,
    val createDatetime: LocalDateTime,
    val status: VillageStatus,
    val roomSize: RoomSize?,
    val participants: VillageParticipants,
    val spectators: VillageParticipants,
    val days: VillageDays,
    val setting: VillageSetting,
    val epilogueDay: Int?,
    val winCamp: Camp?
) {
    fun allParticipants(excludeDummy: Boolean = false): VillageParticipants {
        val list = (participants.list + spectators.list)
        val participants = VillageParticipants(
            count = list.size,
            list = list
        )
        return if (excludeDummy) participants.filterNotDummy(dummyParticipant())
        else participants
    }

    fun allRequestableSkillList(): List<Skill> = setting.allRequestableSkillList()

    fun dummyParticipant(): VillageParticipant = participants.chara(setting.chara.dummyCharaId)

    fun canCancel(): Boolean = status.isPrologue()
    fun canKick(): Boolean = status.isPrologue()
    fun canModifySetting(): Boolean = status.isPrologue()
    fun canExtendEpilogue(): Boolean = status.isEpilogue()
    fun canShortenEpilogue(): Boolean {
        val tomorrow = LocalDateTime.now().plusDays(1L)
        // まだ1日以上エピローグがある
        return status.isEpilogue() && days.latestDay().dayChangeDatetime.isAfter(tomorrow)
    }

    fun isSettled(): Boolean = getAliveWolfCount() <= 0 || getAliveVillagerCount() <= getAliveWolfCount()

    fun canParticipate(): Boolean = status.isPrologue() && participants.count < setting.personMax
    fun assertParticipate(charaId: Int?, joinPassword: String?) {
        if (!canParticipate()) {
            throw WolfMansionBusinessException("参加できません")
        }
        if (allParticipants().list.any { it.charaId == charaId }) {
            throw WolfMansionBusinessException("既に参加しているキャラクターです。")
        }
        if (!setting.joinPassword.isNullOrEmpty() && setting.joinPassword != joinPassword) {
            throw WolfMansionBusinessException("入村パスワードが誤っています。")
        }
    }

    fun canSpectate(charaNum: Int): Boolean =
        setting.rule.isAvailableSpectate
                && status.isPrologue()
                && (setting.chara.isOriginalCharachip || spectators.count < charaNum - setting.personMax)

    fun assertSpectate(charaId: Int?, joinPassword: String?, charaNum: Int) {
        if (!canSpectate(charaNum)) {
            throw WolfMansionBusinessException("参加できません")
        }
        if (allParticipants().list.any { it.charaId == charaId }) {
            throw WolfMansionBusinessException("既に参加しているキャラクターです。")
        }
        if (!setting.joinPassword.isNullOrEmpty() && setting.joinPassword != joinPassword) {
            throw WolfMansionBusinessException("入村パスワードが誤っています。")
        }
    }

    fun canLeave(): Boolean = status.isPrologue()
    fun assertLeave() {
        if (!canLeave()) throw WolfMansionBusinessException("退村できません")
    }

    fun canRequestSkill(): Boolean = status.isPrologue() && setting.rule.isPossibleSkillRequest

    fun canCommit(): Boolean = setting.rule.isAvailableCommit && status.isProgress()

    fun canSay(day: Int): Boolean = status.isNotFinished() && isLatestDay(day)

    fun isSayableNormalSay(): Boolean = status.isNotFinished()

    fun isViewableGraveSay(): Boolean = status.isSettled() || setting.rule.isVisibleGraveSpectateMessage
    fun isSayableGraveSay(): Boolean = status.isProgress()

    fun isViewableMonologueSay(): Boolean = status.isSettled()
    fun isSayableMonologueSay(): Boolean = true

    fun isViewableSpectateSay(): Boolean = !status.isProgress() || setting.rule.isVisibleGraveSpectateMessage
    fun isSayableSpectateSay(): Boolean = true

    fun isViewableWerewolfSay(): Boolean = status.isSettled()
    fun isSayableWerewolfSay(): Boolean = status.isProgress()

    fun isViewableSympathizeSay(): Boolean = status.isSettled()
    fun isSayableSympathizeSay(): Boolean = status.isProgress()

    fun isViewableTelepathy(): Boolean = status.isSettled()
    fun isSayableTelepathy(): Boolean = status.isProgress()

    fun isViewableLoversSay(): Boolean = status.isSettled()
    fun isSayableLoversSay(): Boolean = status.isProgress()

    fun isSayableCreatorSay(): Boolean = status.isNotFinished()

    fun isViewableSecretSay(): Boolean = status.isSettled()

    fun isSayableSecretSay(): Boolean = status.isNotFinished() && setting.rule.isAvailableSecretSay

    fun isSayableActionSay(): Boolean = status.isNotFinished() && setting.rule.isAvailableAction

    fun isViewablePsychicMessage(): Boolean = status.isSettled()
    fun isViewableGuruMessage(): Boolean = status.isSettled()
    fun isViewableAttackMessage(): Boolean = status.isSettled()
    fun isViewableCoronerMessage(): Boolean = status.isSettled()
    fun isViewableDivineMessage(): Boolean = status.isSettled()
    fun isViewableWiseMessage(): Boolean = status.isSettled()
    fun isViewableLoversMessage(): Boolean = status.isSettled()
    fun isViewableFoxMessage(): Boolean = status.isSettled()
    fun isViewableInvestigateMessage(): Boolean = status.isSettled()
    fun isViewablePrivateSystemMessage(): Boolean = status.isSettled()
    fun isViewablePrivateAbilityMessage(): Boolean = status.isSettled()

    fun canUseAbility(day: Int): Boolean = status.isProgress() && isLatestDay(day)

    fun canVote(): Boolean = status.isProgress() && days.latestDay().day > 1

    fun canChangeName(day: Int): Boolean = status.isNotFinished() && isLatestDay(day)
    fun canAddImage(day: Int): Boolean = setting.chara.isOriginalCharachip && status.isNotFinished() && isLatestDay(day)

    fun isLatestDay(day: Int): Boolean = latestDay() == day
    fun latestDay(): Int = days.latestDay().day

    fun isViewableSpoilerContent(): Boolean = status.isSettled()

    fun findRestrict(myself: VillageParticipant, type: MessageType): SayRestriction.Restriction? =
        setting.findRestrict(myself, type)

    fun assertModifySetting() {
        if (latestDay() > 0) throw WolfMansionBusinessException("既にプロローグが終了しているため変更できません")
        if (setting.startDatetime.isBefore(LocalDateTime.now())) throw WolfMansionBusinessException("開始日時を現在より過去にすることはできません")
        if (setting.startDatetime.isAfter(createDatetime.plusDays(14L))) throw WolfMansionBusinessException("開始日時は最初に建てた日時の14日後以降にはできません")
        if (setting.personMax < participants.count) throw WolfMansionBusinessException("定員は既に入村済みの人数未満にすることはできません")
        if (!setting.rule.isAvailableSpectate && spectators.count > 0) throw WolfMansionBusinessException("見学者が既にいるため、見学入村を不可にすることはできません")
    }

    fun statusMessage(isLogin: Boolean, isParticipating: Boolean): String {
        val daychangeDatetime = days.latestDay().dayChangeDatetime.format(DateTimeFormatter.ofPattern("MM/dd HH:mm"))
        return when {
            status.isPrologue() -> {
                val base =
                    "$daychangeDatetime に${setting.personMin}名以上がエントリーしていれば進行します。\n最大${setting.personMax}名まで参加可能です。"
                if (!isLogin) {
                    "ゲーム参加者希望者はトップページよりID登録し、ログインしてください。\n$base"
                } else if (!isParticipating) {
                    "演じたいキャラクターを選び、発言してください。\n$base"
                } else {
                    "$base\n\nプロローグ中は24時間アクセスなしで自動退村となるため、定期的にアクセスしてください。"
                }
            }
            status.isProgress() -> {
                if (latestDay() == 1) {
                    "特殊な能力を持つ人は、$daychangeDatetime までに行動を確定して下さい。"
                } else {
                    "$daychangeDatetime までに、誰を処刑するべきかの投票先を決定して下さい。\n一番票を集めた人物が処刑されます。同数だった場合はランダムで決定されます。\n\n特殊な能力を持つ人は、$daychangeDatetime までに行動を確定して下さい。"
                }
            }
            status.isEpilogue() -> {
                "${winCamp!!.name}の勝利です！\n全てのログとユーザー名を公開します。\n\n$daychangeDatetime まで自由に書き込めますので、今回の感想などをどうぞ。"
            }
            status.toCdef() == CDef.VillageStatus.終了 -> "終了しました。"
            status.isCanceled() -> "この村は廃村となりました。"
            else -> ""
        }
    }

    fun getDay2Message(): String = """
            ついに犠牲者が出た。
            
            村人達は、この中にいる人狼を排除するため、投票を行う事にした。
            無実の犠牲者が出るのもやむをえない。館の外に被害を広げるわけにはいかないのだ。
            
            最後まで残るのは村人か、それとも人狼か。
        """.trimIndent()

    fun getEpilogueTransitionMessage(): String {
        return when {
            winCamp!!.isLovers() -> "どんな古びた伝承も、今を生きる者たちの前では無力だった。\n愛こそ最も尊いのだ。"
            winCamp.isFoxs() -> "全ては終わったかのように見えた。\nだが、奴が生き残っていた。"
            winCamp.isVillagers() -> "全ての人狼を退治した。人狼に怯える日々は去ったのだ！"
            winCamp.isWolfs() -> "もう人狼に抵抗できるほど村人は残っていない。\n人狼は残った村人を全て食らい、別の獲物を求めて館を去っていった。"
            else -> throw IllegalStateException("unknown wincamp. $winCamp")
        }
    }

    fun getAliveParticipantsMessage(): String {
        val aliveParticipants = participants.filterAlive().sortedByRoomNumber().list
        return aliveParticipants.joinToString(
            separator = "、",
            prefix = "現在の生存者は、以下の${aliveParticipants.size}名。\n"
        ) { it.name() }
    }

    // 日付更新用
    fun isSame(other: Village): Boolean {
        return status.code == other.status.code
                && winCamp?.code == other.winCamp?.code
                && roomSize?.width == other.roomSize?.width
                && participants.isSame(other.participants)
                && spectators.isSame(other.spectators)
                && days.isSame(other.days)
                && setting.isSame(other.setting)
    }

    fun leaveParticipant(participantId: Int): Village {
        return if (participants.list.any { it.id == participantId }) {
            this.copy(participants = participants.leave(participantId))
        } else this.copy(spectators = spectators.leave(participantId))
    }

    fun suddenlyDeathParticipant(participantId: Int): Village {
        return copy(participants = participants.suddenlyDeadh(participantId, latestDay()))
    }

    fun executeParticipant(participantId: Int): Village {
        return copy(participants = participants.execute(participantId, latestDay()))
    }

    fun divineKillParticipant(participantId: Int): Village {
        return copy(participants = participants.divineKill(participantId, latestDay()))
    }

    fun attackedParticipant(participantId: Int): Village {
        return copy(participants = participants.attacked(participantId, latestDay()))
    }

    fun trapKillParticipant(participantId: Int): Village {
        return copy(participants = participants.trapKill(participantId, latestDay()))
    }

    fun bombKillParticipant(participantId: Int): Village {
        return copy(participants = participants.bombKill(participantId, latestDay()))
    }

    fun suicideParticipant(participantId: Int): Village {
        return copy(participants = participants.suicide(participantId, latestDay()))
    }

    fun forceReincarnation(participantId: Int, skill: Skill): Village {
        return copy(participants = participants.forceReincarnation(participantId, latestDay(), skill))
    }

    fun reviveParticipant(participantId: Int): Village {
        return copy(participants = participants.revive(participantId, latestDay()))
    }

    fun foxPossessionParticipant(fromParticipantId: Int, toParticipantId: Int): Village {
        return copy(participants = participants.foxPossession(this, fromParticipantId, toParticipantId))
    }

    fun insaneParticipant(fromParticipantId: Int, toParticipantId: Int): Village {
        return copy(participants = participants.insane(this, fromParticipantId, toParticipantId))
    }

    fun persuadeParticipant(fromParticipantId: Int, toParticipantId: Int): Village {
        return copy(participants = participants.persuade(this, fromParticipantId, toParticipantId))
    }

    fun courtParticipant(fromParticipantId: Int, toParticipantId: Int): Village {
        return copy(participants = participants.court(fromParticipantId, toParticipantId))
    }

    fun stalkingParticipant(fromParticipantId: Int, toParticipantId: Int): Village {
        return copy(participants = participants.stalking(fromParticipantId, toParticipantId))
    }

    fun seduceParticipant(fromParticipantId: Int, toParticipantId: Int): Village {
        return copy(participants = participants.seduce(fromParticipantId, toParticipantId))
    }

    fun insuranceParticipant(fromParticipantId: Int, toParticipantId: Int): Village {
        return copy(participants = participants.insurance(fromParticipantId, toParticipantId))
    }

    fun breakupParticipant(participantId: Int): Village {
        return copy(participants = participants.breakup(participantId, this))
    }

    fun stealLoveParticipant(participantId: Int, stealerId: Int): Village {
        return copy(participants = participants.stealLove(participantId, stealerId, this))
    }

    fun useInsurance(participantId: Int): Village {
        return copy(participants = participants.useInsurance(participantId))
    }

    fun assignParticipantSkill(participantId: Int, skill: Skill): Village {
        return this.copy(participants = participants.assignSkill(participantId, skill, latestDay()))
    }

    fun extendPrologue(): Village = copy(
        setting = setting.extendPrologue(),
        days = days.extenrPrologue()
    )

    fun cancel(): Village = copy(status = CDef.VillageStatus.廃村.toModel())
    fun toProgress(): Village = copy(status = CDef.VillageStatus.進行中.toModel())
    fun toEpilogue(): Village {
        val winCamp = when {
            getAliveLoversCount() > 0 -> CDef.Camp.恋人陣営.toModel()
            getAliveFoxCount() > 0 -> CDef.Camp.狐陣営.toModel()
            getAliveWolfCount() >= getAliveVillagerCount() -> CDef.Camp.人狼陣営.toModel()
            else -> CDef.Camp.村人陣営.toModel()
        }
        return copy(
            status = CDef.VillageStatus.エピローグ.toModel(),
            days = days.toEpilogue(),
            epilogueDay = latestDay(),
            winCamp = winCamp
        )
    }

    fun toFinished(): Village = copy(status = CDef.VillageStatus.終了.toModel())

    fun addNewDay(): Village = copy(days = days.addNewDay(setting.dayChangeIntervalSeconds))
    fun mapToSkillCount(): Map<CDef.Skill, Int> = setting.mapToSkillCount(participants.list.size)

    private fun getAliveVillagerCount(): Int =
        participants.filterAlive().list.count { !it.skill!!.isWolfCount() && !it.skill.isNoCount() }

    private fun getAliveWolfCount(): Int = participants.filterAlive().list.count { it.skill!!.isWolfCount() }
    private fun getAliveFoxCount(): Int = participants.filterAlive().list.count { it.skill!!.isFoxCount() }
    private fun getAliveLoversCount(): Int = participants.filterAlive().list.count {
        it.status.hasLover() || it.skill!!.camp().isLovers()
    }

    fun judgeParticipantsWin(): Village = copy(participants = participants.judgeWin(winCamp!!))

    fun getRevivableSkills(): List<Skill> {
        val revivableSkills = Skills.revivables().list
        return when {
            // 全役職可
            setting.rule.isReincarnationSkillAll -> revivableSkills
            // 闇鍋 編成にいる役職のみ
            setting.rule.isRandomOrganization -> {
                // 配分が0でない役職
                val allocatableSkills = setting.organize.randomOrganization.skillAllocation
                    .filterNot { it.allocation <= 0 }
                    .map { it.skill }
                val list = revivableSkills.filter { r -> allocatableSkills.any { r.code == it.code } }
                if (list.isNotEmpty()) list else listOf(CDef.Skill.村人.toModel())
            }
            else -> {
                // 1日目時点でこの村に含まれる役職
                val allocatableSkills = participants.list.map { it.skillWhen(1)!! }.distinctBy { it.code }
                val list = revivableSkills.filter { r -> allocatableSkills.any { r.code == it.code } }
                if (list.isNotEmpty()) list else listOf(CDef.Skill.村人.toModel())
            }
        }

    }
}