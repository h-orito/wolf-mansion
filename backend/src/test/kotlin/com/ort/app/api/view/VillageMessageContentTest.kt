package com.ort.app.api.view

import com.ort.app.domain.model.chara.Chara
import com.ort.app.domain.model.chara.CharaImages
import com.ort.app.domain.model.chara.CharaSize
import com.ort.app.domain.model.chara.Charas
import com.ort.app.domain.model.message.Message
import com.ort.app.domain.model.message.MessageContent
import com.ort.app.domain.model.message.MessageTime
import com.ort.app.domain.model.message.MessageType
import com.ort.app.domain.model.skill.Skill
import com.ort.app.domain.model.village.createDay1Village
import com.ort.app.domain.model.village.createVillageParticipant
import com.ort.dbflute.allcommon.CDef
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

internal class VillageMessageContentTest {
    private val wolfCharaId = 3

    private fun whisper(): Message =
        Message(
            fromParticipantId = wolfCharaId,
            fromCharacterName = "少年 ペーター",
            toParticipantId = null,
            toCharacterName = "老人 モーリッツ",
            time = MessageTime(day = 1, datetime = LocalDateTime.now()),
            content =
                MessageContent(
                    type = MessageType(CDef.MessageType.人狼の囁き),
                    num = 5,
                    text = "今夜は村長を襲撃しよう",
                    faceTypeCode = null,
                    isConvertDisable = true,
                ),
        )

    private fun charasWith(charaId: Int): Charas =
        Charas(
            list =
                listOf(
                    Chara(
                        id = charaId,
                        name = "少年 ペーター",
                        shortName = "年",
                        defaultJoinMessage = null,
                        defaultFirstdayMessage = null,
                        size = CharaSize(width = 50, height = 77),
                        images = CharaImages(list = emptyList()),
                    ),
                ),
        )

    @Test
    fun `梟の地獄耳で見えている囁きは発言者を特定できる情報をレスポンスから除去する`() {
        val village = createDay1Village()
        val owl = createVillageParticipant(skill = Skill.byShortName("梟")!!, id = 99)
        val wolf = createVillageParticipant(skill = Skill.byShortName("狼")!!, id = wolfCharaId)

        val content =
            VillageMessageContent.of(
                village = village,
                myself = owl,
                myselfPlayer = null,
                message = whisper(),
                fromParticipant = wolf,
                player = null,
                charas = Charas(list = emptyList()),
                hasBigEar = true,
                isRainbow = true,
                isLoud = true,
                isLatestDay = true,
            )

        assertTrue(content.isBigEars)
        // 種別も伏せる (囁き/共鳴/恋人/念話 の判別で共有・恋人・妖狐の活動が漏れるため)
        assertEquals(CDef.MessageType.通常発言.code(), content.messageType)
        // 発言者の identity は一切出さない (生 JSON からの囁き主特定を防ぐ)
        assertNull(content.characterName)
        assertNull(content.characterId)
        assertNull(content.characterImageUrl)
        assertNull(content.width)
        assertNull(content.height)
        assertNull(content.playerName)
        assertNull(content.targetCharacterName)
        // 発言番号を出すとアンカー参照や種別特定に使われるため伏せる
        assertNull(content.messageNumber)
        assertFalse(content.canReply)
        // 地獄耳発言は虹色・大声にならない (発言者の被虹塗り・被拡声の間接漏れを防ぐ)
        assertFalse(content.isRainbow)
        assertFalse(content.isLoud)
        // 本文自体は梟が読める情報なので残す
        assertEquals("今夜は村長を襲撃しよう", content.messageContent)
    }

    @Test
    fun `地獄耳でない通常の閲覧では発言者情報を保持する`() {
        val village = createDay1Village()
        val wolf = createVillageParticipant(skill = Skill.byShortName("狼")!!, id = wolfCharaId)

        val content =
            VillageMessageContent.of(
                village = village,
                myself = wolf,
                myselfPlayer = null,
                message = whisper(),
                fromParticipant = wolf,
                player = null,
                charas = charasWith(wolfCharaId),
                hasBigEar = false,
                isRainbow = false,
                isLoud = false,
                isLatestDay = true,
            )

        assertFalse(content.isBigEars)
        assertEquals(CDef.MessageType.人狼の囁き.code(), content.messageType)
        assertEquals("少年 ペーター", content.characterName)
        assertEquals(wolfCharaId, content.characterId)
        assertEquals(5, content.messageNumber)
        assertEquals(50, content.width)
        assertEquals(77, content.height)
        assertTrue(content.canReply)
    }
}
