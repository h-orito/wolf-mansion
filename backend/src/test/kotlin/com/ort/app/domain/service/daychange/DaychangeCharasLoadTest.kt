package com.ort.app.domain.service.daychange

import com.ort.app.domain.model.ability.Abilities
import com.ort.app.domain.model.chara.Charas
import com.ort.app.domain.model.commit.Commits
import com.ort.app.domain.model.daychange.Daychange
import com.ort.app.domain.model.footstep.Footsteps
import com.ort.app.domain.model.player.Players
import com.ort.app.domain.model.village.Village
import com.ort.app.domain.model.village.createDay1Village
import com.ort.app.domain.model.village.createPrologueVillage
import com.ort.app.domain.model.vote.Votes
import org.junit.jupiter.api.Assertions.assertSame
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock

/**
 * 日付更新の時刻前（ポーリングで毎回通る経路）では、キャラチップを読まないこと。
 */
class DaychangeCharasLoadTest {
    private val failingProvider: () -> Charas = { throw AssertionError("キャラチップを読んではいけない") }

    private fun daychange(village: Village) =
        Daychange(village, Abilities(emptyList()), Votes(emptyList()), Footsteps(emptyList()), Players(emptyList()))

    // 依存はすべてモックにする（日付更新しない経路ではどれも呼ばれない）
    private inline fun <reified T : Any> newWithMocks(): T {
        val constructor = T::class.java.constructors.single()
        val args = constructor.parameterTypes.map { mock(it) }.toTypedArray()
        @Suppress("UNCHECKED_CAST")
        return constructor.newInstance(*args) as T
    }

    @Test
    @DisplayName("プロローグで開始時刻前ならキャラチップを読まない")
    fun prologue() {
        val current = daychange(createPrologueVillage())
        val result = newWithMocks<PrologueDomainService>().changeDayIfNeeded(current, failingProvider)
        assertSame(current, result)
    }

    @Test
    @DisplayName("進行中で更新時刻前ならキャラチップを読まない")
    fun progress() {
        // コミットによる早送りは判定に参加者のダミーキャラが要るので、この確認では無効にする
        val village = createDay1Village().let { it.copy(setting = it.setting.copy(rule = it.setting.rule.copy(isAvailableCommit = false))) }
        val current = daychange(village)
        val result = newWithMocks<ProgressDomainService>().changeDayIfNeeded(current, Commits(emptyList()), failingProvider)
        assertSame(current, result)
    }
}
