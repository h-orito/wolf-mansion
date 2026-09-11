package com.ort.app.api.village.request

import com.ort.app.api.request.NewVillageForm
import com.ort.app.api.request.setting.MessageTypeSayRestrictForm
import com.ort.app.api.request.setting.RandomOrganizationCampForm
import com.ort.app.api.request.setting.RandomOrganizationSkillForm
import com.ort.app.api.request.setting.RandomOrganizationWolfForm
import com.ort.app.api.request.setting.SkillSayRestrictForm
import com.ort.app.fw.exception.WolfMansionValidationException.FieldErrorItem
import com.ort.dbflute.allcommon.CDef
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.Valid
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size
import org.springframework.web.multipart.MultipartFile

/**
 * 村作成リクエスト (multipart/form-data の JSON part)。
 *
 * 項目単位の制約は Jakarta アノテーション (spec の単一ソース)、相関チェックは
 * SSR と共通の `NewVillageFormValidator` を [toForm] 変換後に適用する。
 * 表示名 (陣営名・役職名など) はコードから解決できるため受け取らない。
 */
data class VillageCreateRequest(
    @field:NotNull
    @field:Size(min = 5, max = 40)
    val villageName: String? = null,
    @field:Pattern(regexp = "ANYONE_WELCOME|RELATIVES_ONLY")
    val welcomeRange: String? = null,
    @field:NotNull
    @field:Min(8)
    val startPersonMinNum: Int? = null,
    @field:NotNull
    @field:Max(999)
    val personMaxNum: Int? = null,
    @field:NotNull
    @field:Min(0)
    @field:Max(72)
    val dayChangeIntervalHours: Int? = null,
    @field:NotNull
    @field:Min(0)
    @field:Max(59)
    val dayChangeIntervalMinutes: Int? = null,
    @field:NotNull
    @field:Min(0)
    @field:Max(59)
    val dayChangeIntervalSeconds: Int? = null,
    @field:NotNull
    @field:Min(0)
    val startYear: Int? = null,
    @field:NotNull
    @field:Min(1)
    @field:Max(12)
    val startMonth: Int? = null,
    @field:NotNull
    @field:Min(1)
    @field:Max(31)
    val startDay: Int? = null,
    @field:NotNull
    @field:Min(0)
    @field:Max(23)
    val startHour: Int? = null,
    @field:NotNull
    @field:Min(0)
    @field:Max(59)
    val startMinute: Int? = null,
    @field:NotNull
    val shouldOriginalImage: Boolean? = null,
    @field:NotNull
    val characterSetId: List<Int>? = null,
    /** キャラチップ利用時は必須。オリジナル画像時は未使用 (サーバー側でダミー値に置き換わる)。 */
    val dummyCharaId: Int? = null,
    @field:NotNull
    @field:Size(min = 1, max = 40)
    val dummyCharaName: String? = null,
    @field:NotNull
    @field:Size(min = 1, max = 1)
    val dummyCharaShortName: String? = null,
    @field:NotNull
    @field:Size(min = 1, max = 400)
    val dummyJoinMessage: String? = null,
    @field:Size(max = 400)
    val dummyDay1Message: String? = null,
    val joinPassword: String? = null,
    @field:NotNull
    val openVote: Boolean? = null,
    @field:NotNull
    val possibleSkillRequest: Boolean? = null,
    @field:NotNull
    val availableSameWolfAttack: Boolean? = null,
    @field:NotNull
    val availableGuardSameTarget: Boolean? = null,
    @field:NotNull
    val reincarnationSkillAll: Boolean? = null,
    @field:NotNull
    val availableSuddonlyDeath: Boolean? = null,
    @field:NotNull
    val availableCommit: Boolean? = null,
    @field:NotNull
    val availableSpectate: Boolean? = null,
    @field:NotNull
    val creatorIsProducer: Boolean? = null,
    @field:NotNull
    val openSkillInGrave: Boolean? = null,
    @field:NotNull
    val visibleGraveSpectateMessage: Boolean? = null,
    @field:NotNull
    val availableAction: Boolean? = null,
    @field:NotNull
    val randomOrganization: Boolean? = null,
    /** 固定編成 (プレフィックスなしの編成本体)。固定編成時は必須 (相関チェックで検証)。 */
    val organization: String? = null,
    @field:Valid
    val campAllocationList: List<CampAllocation>? = null,
    @field:Valid
    val wolfAllocation: WolfAllocation? = null,
    @field:NotNull
    @field:Pattern(regexp = "NOTHING|ONLY_CREATOR|EVERYTHING")
    val allowedSecretSayCode: String? = null,
    @field:NotNull
    @field:Valid
    val sayRestrictList: List<SkillSayRestrict>? = null,
    @field:NotNull
    @field:Valid
    val skillSayRestrictList: List<MessageTypeSayRestrict>? = null,
    @field:NotNull
    @field:Valid
    val rpSayRestrictList: List<MessageTypeSayRestrict>? = null,
    @field:Pattern(regexp = "R15|R18")
    val ageLimit: String? = null,
) {
    // ドメインの VillageRandomOrganize.CampAllocation 等と単純名が衝突すると
    // SpringDoc が片方のスキーマ定義で上書きしてしまうため、spec 上の名前を明示する
    @Schema(name = "VillageCreateRequestCampAllocation")
    data class CampAllocation(
        @field:NotNull
        val campCode: String? = null,
        @field:NotNull
        @field:Min(0)
        @field:Max(100)
        val minNum: Int? = null,
        @field:Min(0)
        @field:Max(100)
        val maxNum: Int? = null,
        @field:NotNull
        @field:Min(0)
        @field:Max(100)
        val allocation: Int? = null,
        @field:NotNull
        @field:Min(0)
        @field:Max(100)
        val reincarnationAllocation: Int? = null,
        @field:NotNull
        @field:Valid
        val skillAllocation: List<SkillAllocation>? = null,
    )

    @Schema(name = "VillageCreateRequestSkillAllocation")
    data class SkillAllocation(
        @field:NotNull
        val skillCode: String? = null,
        @field:NotNull
        @field:Min(0)
        @field:Max(100)
        val minNum: Int? = null,
        @field:Min(0)
        @field:Max(100)
        val maxNum: Int? = null,
        @field:NotNull
        @field:Min(0)
        @field:Max(100)
        val allocation: Int? = null,
        @field:NotNull
        @field:Min(0)
        @field:Max(100)
        val reincarnationAllocation: Int? = null,
    )

    @Schema(name = "VillageCreateRequestWolfAllocation")
    data class WolfAllocation(
        @field:NotNull
        @field:Min(1)
        @field:Max(100)
        val minNum: Int? = null,
        @field:Min(1)
        @field:Max(100)
        val maxNum: Int? = null,
    )

    data class SkillSayRestrict(
        @field:NotNull
        val skillCode: String? = null,
        @field:NotNull
        val restrict: Boolean? = null,
        val length: Int? = null,
        val count: Int? = null,
    )

    data class MessageTypeSayRestrict(
        @field:NotNull
        val messageTypeCode: String? = null,
        @field:NotNull
        val restrict: Boolean? = null,
        val length: Int? = null,
        val count: Int? = null,
    )

    /**
     * コード値の存在チェック + 闇鍋編成の構造完全性チェック。
     * `NewVillageFormValidator` や `toVillage` は CDef 解決済みかつ村人陣営/村人役職の存在を
     * 前提に `first { }` で参照するため、欠けたリクエストが届くと 500 になる。ここで 400 に倒す
     * (API から取得したコードを正しく組んで送る限り発生しない)。
     */
    fun validateCodes(): List<FieldErrorItem> {
        val errors = mutableListOf<FieldErrorItem>()
        campAllocationList?.forEachIndexed { i, camp ->
            if (CDef.Camp.codeOf(camp.campCode) == null) {
                errors.add(FieldErrorItem("campAllocationList[$i].campCode", "存在しない陣営です"))
            }
            camp.skillAllocation?.forEachIndexed { j, skill ->
                if (CDef.Skill.codeOf(skill.skillCode) == null) {
                    errors.add(FieldErrorItem("campAllocationList[$i].skillAllocation[$j].skillCode", "存在しない役職です"))
                }
            }
        }
        sayRestrictList?.forEachIndexed { i, restrict ->
            if (CDef.Skill.codeOf(restrict.skillCode) == null) {
                errors.add(FieldErrorItem("sayRestrictList[$i].skillCode", "存在しない役職です"))
            }
        }
        (
            skillSayRestrictList.orEmpty().mapIndexed { i, r -> "skillSayRestrictList[$i]" to r } +
                rpSayRestrictList.orEmpty().mapIndexed { i, r -> "rpSayRestrictList[$i]" to r }
        ).forEach { (field, restrict) ->
            if (CDef.MessageType.codeOf(restrict.messageTypeCode) == null) {
                errors.add(FieldErrorItem("$field.messageTypeCode", "存在しない発言種別です"))
            }
        }
        if (randomOrganization == true && errors.isEmpty()) {
            errors.addAll(validateRandomOrganizationStructure())
        }
        return errors
    }

    /** 闇鍋編成で validator が `first { }` 参照する村人陣営・村人役職の存在を保証する。 */
    private fun validateRandomOrganizationStructure(): List<FieldErrorItem> {
        val camps = campAllocationList
        if (camps.isNullOrEmpty()) {
            return listOf(FieldErrorItem("campAllocationList", "闇鍋編成の陣営が指定されていません"))
        }
        val villagerCamp =
            camps.firstOrNull { CDef.Camp.codeOf(it.campCode) == CDef.Camp.村人陣営 }
                ?: return listOf(FieldErrorItem("campAllocationList", "村人陣営の配分が指定されていません"))
        val hasVillagerSkill =
            villagerCamp.skillAllocation?.any { CDef.Skill.codeOf(it.skillCode) == CDef.Skill.村人 } == true
        if (!hasVillagerSkill) {
            return listOf(FieldErrorItem("campAllocationList", "村人役職の配分が指定されていません"))
        }
        return emptyList()
    }

    /** SSR と同じ検証・変換 (`NewVillageFormValidator` / `toVillage`) を流用するためフォームへ変換する。 */
    fun toForm(dummyCharaImage: MultipartFile?): NewVillageForm =
        NewVillageForm(
            villageName = villageName,
            startPersonMinNum = startPersonMinNum,
            personMaxNum = personMaxNum,
            dayChangeIntervalHours = dayChangeIntervalHours,
            dayChangeIntervalMinutes = dayChangeIntervalMinutes,
            dayChangeIntervalSeconds = dayChangeIntervalSeconds,
            startYear = startYear,
            startMonth = startMonth,
            startDay = startDay,
            startHour = startHour,
            startMinute = startMinute,
            welcomeRange = welcomeRange,
            ageLimit = ageLimit,
            openVote = openVote,
            possibleSkillRequest = possibleSkillRequest,
            availableSameWolfAttack = availableSameWolfAttack,
            openSkillInGrave = openSkillInGrave,
            visibleGraveSpectateMessage = visibleGraveSpectateMessage,
            shouldOriginalImage = shouldOriginalImage,
            characterSetId = characterSetId,
            // オリジナル画像時はダミーキャラ未選択でもよい (登録時にオリジナルキャラチップの値へ置き換わる)
            dummyCharaId = if (shouldOriginalImage == true) dummyCharaId ?: 1 else dummyCharaId,
            dummyCharaImageFile = dummyCharaImage,
            dummyCharaName = dummyCharaName,
            dummyCharaShortName = dummyCharaShortName,
            dummyJoinMessage = dummyJoinMessage,
            dummyDay1Message = dummyDay1Message,
            joinPassword = joinPassword,
            availableSpectate = availableSpectate,
            creatorIsProducer = creatorIsProducer,
            availableSuddonlyDeath = availableSuddonlyDeath,
            availableCommit = availableCommit,
            availableGuardSameTarget = availableGuardSameTarget,
            availableAction = availableAction,
            organization = organization,
            randomOrganization = randomOrganization,
            reincarnationSkillAll = reincarnationSkillAll,
            campAllocationList =
                campAllocationList?.map { camp ->
                    RandomOrganizationCampForm(
                        campCode = camp.campCode,
                        campName = CDef.Camp.codeOf(camp.campCode)?.alias(),
                        minNum = camp.minNum,
                        maxNum = camp.maxNum,
                        allocation = camp.allocation,
                        reincarnationAllocation = camp.reincarnationAllocation,
                        skillAllocation =
                            camp.skillAllocation?.map { skill ->
                                RandomOrganizationSkillForm(
                                    skillCode = skill.skillCode,
                                    skillName = CDef.Skill.codeOf(skill.skillCode)?.alias(),
                                    minNum = skill.minNum,
                                    maxNum = skill.maxNum,
                                    allocation = skill.allocation,
                                    reincarnationAllocation = skill.reincarnationAllocation,
                                )
                            },
                    )
                },
            wolfAllocation =
                wolfAllocation?.let {
                    RandomOrganizationWolfForm(minNum = it.minNum, maxNum = it.maxNum)
                },
            allowedSecretSayCode = allowedSecretSayCode,
            sayRestrictList =
                sayRestrictList?.map {
                    SkillSayRestrictForm(
                        skillName = CDef.Skill.codeOf(it.skillCode)?.alias(),
                        skillCode = it.skillCode,
                        restrict = it.restrict,
                        count = it.count,
                        length = it.length,
                    )
                },
            skillSayRestrictList = skillSayRestrictList?.map { it.toForm() },
            rpSayRestrictList = rpSayRestrictList?.map { it.toForm() },
        )

    private fun MessageTypeSayRestrict.toForm(): MessageTypeSayRestrictForm =
        MessageTypeSayRestrictForm(
            messageTypeName = CDef.MessageType.codeOf(messageTypeCode)?.alias(),
            messageTypeCode = messageTypeCode,
            restrict = restrict,
            count = count,
            length = length,
        )
}
