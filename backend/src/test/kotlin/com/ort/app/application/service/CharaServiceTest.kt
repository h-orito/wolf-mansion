package com.ort.app.application.service

import com.ort.app.domain.model.chara.CharaRepository
import com.ort.app.fw.exception.WolfMansionBusinessException
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.mockito.kotlin.eq
import org.mockito.kotlin.mock
import org.mockito.kotlin.never
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class CharaServiceTest {

    @Test
    fun `updateOriginalCharaImage 自キャラの表情なら更新が委譲される`() {
        val charaRepository = mock<CharaRepository>()
        whenever(charaRepository.findOriginalCharaIdByCharaImageId(eq(100))).thenReturn(42)
        val service = CharaService(charaRepository)

        service.updateOriginalCharaImage(
            ownerCharaId = 42,
            faceTypeCode = "100",
            faceTypeName = "笑顔",
            isDisplay = true,
        )

        verify(charaRepository).updateOriginalCharaImage(eq(100), eq("笑顔"), eq(true))
    }

    @Test
    fun `updateOriginalCharaImage 他人キャラの表情を指定すると例外`() {
        val charaRepository = mock<CharaRepository>()
        whenever(charaRepository.findOriginalCharaIdByCharaImageId(eq(100))).thenReturn(43)
        val service = CharaService(charaRepository)

        assertThatThrownBy {
            service.updateOriginalCharaImage(
                ownerCharaId = 42,
                faceTypeCode = "100",
                faceTypeName = "笑顔",
                isDisplay = true,
            )
        }.isInstanceOf(WolfMansionBusinessException::class.java)
            .extracting { (it as WolfMansionBusinessException).message }
            .satisfies({ assertThat(it as String).contains("他のキャラ") })

        verify(charaRepository, never()).updateOriginalCharaImage(eq(100), eq("笑顔"), eq(true))
    }

    @Test
    fun `updateOriginalCharaImage 数値でない faceTypeCode で例外 (400 相当)`() {
        val charaRepository = mock<CharaRepository>()
        val service = CharaService(charaRepository)

        assertThatThrownBy {
            service.updateOriginalCharaImage(
                ownerCharaId = 42,
                faceTypeCode = "abc",
                faceTypeName = "笑顔",
                isDisplay = true,
            )
        }.isInstanceOf(WolfMansionBusinessException::class.java)
            .extracting { (it as WolfMansionBusinessException).message }
            .satisfies({ assertThat(it as String).contains("不正") })
    }

    @Test
    fun `updateOriginalCharaImage 存在しない faceTypeCode で例外`() {
        val charaRepository = mock<CharaRepository>()
        whenever(charaRepository.findOriginalCharaIdByCharaImageId(eq(999))).thenReturn(null)
        val service = CharaService(charaRepository)

        assertThatThrownBy {
            service.updateOriginalCharaImage(
                ownerCharaId = 42,
                faceTypeCode = "999",
                faceTypeName = "笑顔",
                isDisplay = true,
            )
        }.isInstanceOf(WolfMansionBusinessException::class.java)
            .extracting { (it as WolfMansionBusinessException).message }
            .satisfies({ assertThat(it as String).contains("見つかりません") })

        verify(charaRepository, never()).updateOriginalCharaImage(eq(999), eq("笑顔"), eq(true))
    }
}
