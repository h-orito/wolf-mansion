package com.ort.app.api.village.request

import com.ort.app.api.request.validator.NewVillageFormValidator
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.validation.BeanPropertyBindingResult
import java.time.LocalDateTime

internal class VillageCreateRequestTest {
    @Test
    fun `toForm がフォームへ全項目を引き継ぐ`() {
        val request = fixture()

        val form = request.toForm(null)

        assertEquals("テスト村あいうえお", form.villageName)
        assertEquals(8, form.startPersonMinNum)
        assertEquals(8, form.personMaxNum)
        assertEquals(false, form.shouldOriginalImage)
        assertEquals(listOf(1), form.characterSetId)
        assertEquals(1, form.dummyCharaId)
        assertEquals("楽天家 ゲルト", form.dummyCharaName)
        assertEquals("人狼の囁き", form.skillSayRestrictList!!.first().messageTypeName)
        assertEquals("村人", form.sayRestrictList!!.first().skillName)
    }

    @Test
    fun `toForm はオリジナル画像時にダミーキャラ未選択を許容する`() {
        val form = fixture(shouldOriginalImage = true, dummyCharaId = null).toForm(null)

        assertEquals(1, form.dummyCharaId)
    }

    @Test
    fun `validateCodes は不正なコードを検出する`() {
        val request =
            fixture().copy(
                sayRestrictList = listOf(VillageCreateRequest.SkillSayRestrict("INVALID", false, 400, 20)),
                skillSayRestrictList =
                    listOf(VillageCreateRequest.MessageTypeSayRestrict("INVALID", false, 400, 20)),
            )

        val errors = request.validateCodes()

        assertEquals(
            listOf("sayRestrictList[0].skillCode", "skillSayRestrictList[0].messageTypeCode"),
            errors.map { it.field },
        )
    }

    @Test
    fun `validateCodes は正しいコードなら空を返す`() {
        assertTrue(fixture().validateCodes().isEmpty())
    }

    @Test
    fun `validateCodes は闇鍋で村人陣営が欠けたら検出する`() {
        val request =
            fixture(randomOrganization = true).copy(
                campAllocationList = listOf(wolfCampOnly()),
            )

        val errors = request.validateCodes()

        assertEquals(listOf("campAllocationList"), errors.map { it.field })
    }

    @Test
    fun `validateCodes は固定編成では闇鍋構造を検査しない`() {
        // 固定編成 (randomOrganization=false) なら村人陣営が無くてもここでは弾かない
        val request = fixture().copy(campAllocationList = listOf(wolfCampOnly()))

        assertTrue(request.validateCodes().isEmpty())
    }

    @Test
    fun `NewVillageFormValidator と組み合わせて固定編成の村を検証できる`() {
        val errors = BeanPropertyBindingResult(fixture().toForm(null), "villageForm")

        NewVillageFormValidator().validate(fixture().toForm(null), errors)

        assertFalse(errors.hasErrors(), errors.allErrors.toString())
    }

    @Test
    fun `NewVillageFormValidator と組み合わせて編成エラーを検出できる`() {
        // 定員 9 人に対して 9 人の編成行が無い
        val form = fixture(personMaxNum = 9).toForm(null)
        val errors = BeanPropertyBindingResult(form, "villageForm")

        NewVillageFormValidator().validate(form, errors)

        assertTrue(errors.fieldErrors.any { it.field == "organization" })
    }

    /** 村人陣営を含まない人狼陣営のみの配分 (闇鍋構造チェック用)。 */
    private fun wolfCampOnly(): VillageCreateRequest.CampAllocation =
        VillageCreateRequest.CampAllocation(
            campCode = "WEREWOLF",
            minNum = 0,
            maxNum = null,
            allocation = 50,
            reincarnationAllocation = 50,
            skillAllocation =
                listOf(
                    VillageCreateRequest.SkillAllocation(
                        skillCode = "WEREWOLF",
                        minNum = 0,
                        maxNum = null,
                        allocation = 50,
                        reincarnationAllocation = 0,
                    ),
                ),
        )

    private fun fixture(
        shouldOriginalImage: Boolean = false,
        dummyCharaId: Int? = 1,
        personMaxNum: Int = 8,
        randomOrganization: Boolean = false,
    ): VillageCreateRequest {
        val start = LocalDateTime.now().plusDays(7)
        return VillageCreateRequest(
            villageName = "テスト村あいうえお",
            welcomeRange = null,
            startPersonMinNum = 8,
            personMaxNum = personMaxNum,
            dayChangeIntervalHours = 24,
            dayChangeIntervalMinutes = 0,
            dayChangeIntervalSeconds = 0,
            startYear = start.year,
            startMonth = start.monthValue,
            startDay = start.dayOfMonth,
            startHour = 0,
            startMinute = 0,
            shouldOriginalImage = shouldOriginalImage,
            characterSetId = listOf(1),
            dummyCharaId = dummyCharaId,
            dummyCharaName = "楽天家 ゲルト",
            dummyCharaShortName = "楽",
            dummyJoinMessage = "人狼なんているわけないじゃん。",
            dummyDay1Message = null,
            joinPassword = if (shouldOriginalImage) "pass123" else null,
            openVote = true,
            possibleSkillRequest = true,
            availableSameWolfAttack = true,
            availableGuardSameTarget = true,
            reincarnationSkillAll = false,
            availableSuddonlyDeath = false,
            availableCommit = false,
            availableSpectate = false,
            creatorIsProducer = false,
            openSkillInGrave = false,
            visibleGraveSpectateMessage = false,
            availableAction = false,
            randomOrganization = randomOrganization,
            organization = "村狼狼賢導村村村",
            campAllocationList = null,
            wolfAllocation = null,
            allowedSecretSayCode = "NOTHING",
            sayRestrictList = listOf(VillageCreateRequest.SkillSayRestrict("VILLAGER", false, 400, 20)),
            skillSayRestrictList =
                listOf(VillageCreateRequest.MessageTypeSayRestrict("WEREWOLF_SAY", false, 400, 20)),
            rpSayRestrictList = listOf(VillageCreateRequest.MessageTypeSayRestrict("ACTION", false, 400, 20)),
            ageLimit = null,
        )
    }
}
