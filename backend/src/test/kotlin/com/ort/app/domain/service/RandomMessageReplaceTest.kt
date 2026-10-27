package com.ort.app.domain.service

import com.ort.app.domain.model.message.Message
import com.ort.app.domain.model.message.MessageContent
import com.ort.app.domain.model.message.MessageTime
import com.ort.app.domain.model.message.toModel
import com.ort.app.domain.model.randomkeyword.RandomContent
import com.ort.app.domain.model.randomkeyword.RandomKeyword
import com.ort.app.domain.model.randomkeyword.RandomKeywords
import com.ort.app.domain.model.skill.Skill
import com.ort.app.domain.model.village.createVillageParticipant
import com.ort.app.domain.model.village.participant.VillageParticipantName
import com.ort.app.domain.model.village.participant.VillageParticipants
import com.ort.dbflute.allcommon.CDef
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import java.time.LocalDateTime

internal class RandomMessageReplaceTest {
    private val service =
        MessageDomainService(
            mock(SayDomainService::class.java),
            mock(com.ort.app.domain.service.message.system.PsychicMessageDomainService::class.java),
            mock(com.ort.app.domain.service.message.system.GuruMessageDomainService::class.java),
            mock(com.ort.app.domain.service.message.system.AttackMessageDomainService::class.java),
            mock(com.ort.app.domain.service.message.system.CoronerMessageDomainService::class.java),
            mock(com.ort.app.domain.service.message.system.DivineMessageDomainService::class.java),
            mock(com.ort.app.domain.service.message.system.WiseMessageDomainService::class.java),
            mock(com.ort.app.domain.service.message.system.InvestigateMessageDomainService::class.java),
            mock(com.ort.app.domain.service.message.system.LoversMessageDomainService::class.java),
            mock(com.ort.app.domain.service.message.system.FoxMessageDomainService::class.java),
            mock(com.ort.app.domain.service.message.system.PrivateAbilityMessageDomainService::class.java),
            mock(com.ort.app.domain.service.message.system.PrivateSystemMessageDomainService::class.java),
            mock(com.ort.app.domain.model.translate.TranslateRepository::class.java),
        )

    private val participant =
        createVillageParticipant(Skill(CDef.Skill.村人), 1)
            .copy(charaName = VillageParticipantName(name = "A\$1\\", shortName = "A"))

    private fun replace(
        text: String,
        keywords: List<RandomKeyword> = emptyList(),
    ): String {
        val message =
            Message(
                fromParticipantId = 1,
                fromCharacterName = null,
                toParticipantId = null,
                toCharacterName = null,
                time = MessageTime(day = 1, datetime = LocalDateTime.now()),
                content =
                    MessageContent(
                        type = CDef.MessageType.通常発言.toModel(),
                        num = null,
                        text = text,
                        faceTypeCode = null,
                        isConvertDisable = false,
                    ),
            )
        return service
            .replaceRandomMessageIfNeeded(message, VillageParticipants(count = 1, list = listOf(participant)), RandomKeywords(keywords))
            .content.text
    }

    @Test
    @DisplayName("ユーザー定義キーワードの置換文字列に \$ や \\ があっても、そのまま置き換える")
    fun userDefinedKeywordWithSpecialCharacters() {
        listOf("\$9", "\\", "\$0\\1").forEach { content ->
            val keyword = RandomKeyword(id = 1, keyword = "kw", contents = listOf(RandomContent(content)))
            assertTrue(replace("[[kw]]", listOf(keyword)).startsWith(content), content)
        }
    }

    @Test
    @DisplayName("キーワードの正規表現の記号は文字として扱う")
    fun keywordIsNotRegex() {
        val keyword = RandomKeyword(id = 1, keyword = "a.b", contents = listOf(RandomContent("x")))
        assertEquals("[[azb]]", replace("[[azb]]", listOf(keyword)))
        assertTrue(replace("[[a.b]]", listOf(keyword)).startsWith("x"))
    }

    @Test
    @DisplayName("キャラ名や or の選択肢に \$ や \\ があっても、そのまま置き換える")
    fun charaNameAndOrWithSpecialCharacters() {
        assertTrue(replace("[[who]]").startsWith("A\$1\\"))
        assertTrue(replace("[[allwho]]").startsWith("A\$1\\"))
        assertTrue(replace("[[\$1or\$1]]").startsWith("\$1"))
    }
}
