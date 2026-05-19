package com.ort.app.api.v1.villages

import com.ort.app.api.request.VillageSettingForm
import com.ort.app.api.request.validator.SettingFormValidator
import com.ort.app.api.request.village.VillageSettingsUpdateBody
import com.ort.app.domain.model.camp.Camp
import com.ort.app.domain.model.message.MessageType
import com.ort.app.domain.model.skill.Skill
import com.ort.app.domain.model.village.Village
import com.ort.app.domain.model.village.setting.SayRestriction
import com.ort.app.domain.model.village.setting.SecretSayRange
import com.ort.app.domain.model.village.setting.VillageOrganize
import com.ort.app.domain.model.village.setting.VillageRandomOrganize
import com.ort.app.domain.model.village.setting.VillageTags
import com.ort.app.domain.model.village.setting.toModel
import com.ort.app.fw.exception.WolfMansionBusinessException
import com.ort.dbflute.allcommon.CDef
import org.springframework.context.MessageSource
import org.springframework.stereotype.Component
import org.springframework.validation.BeanPropertyBindingResult
import java.time.LocalDateTime
import java.util.Locale

/**
 * `VillageSettingsUpdateBody` を Village に適用するヘルパー。
 *
 * 旧 `CreatorController.merge` の置き換え。cross-field バリデーションは既存の
 * `SettingFormValidator` を共有 (body → form に変換してから流し込む) し、メッセージ
 * 解決は `MessageSource` 経由で行う。
 *
 * オリジナルキャラチップ村でのパスワード必須チェックは旧 Thymeleaf 実装と同じく
 * controller 側で別途呼び出す (本クラスは「form 由来の汎用チェック + Village への反映」)。
 */
@Component
class VillageSettingsUpdateApplier(
    private val settingFormValidator: SettingFormValidator,
    private val messageSource: MessageSource,
) {

    /**
     * バリデーション + 反映を一度に行う。違反があれば最初のエラーを
     * `WolfMansionBusinessException` (= 400) にして送出する。
     */
    fun apply(village: Village, body: VillageSettingsUpdateBody): Village {
        val form = body.toForm()
        validate(form)
        return merge(village, form)
    }

    private fun validate(form: VillageSettingForm) {
        val errors = BeanPropertyBindingResult(form, "settingsForm")
        settingFormValidator.validate(form, errors)
        if (!errors.hasErrors()) return
        val first = errors.allErrors.first()
        val message = messageSource.getMessage(first, Locale.JAPANESE)
        throw WolfMansionBusinessException(message)
    }

    /**
     * 旧 `CreatorController.merge` と同じロジック。
     * Thymeleaf 撤去 (Step 9) 後に旧 controller 側もこのヘルパーに合流させる想定。
     */
    private fun merge(village: Village, form: VillageSettingForm): Village {
        val startDatetime = LocalDateTime.of(
            form.startYear!!,
            form.startMonth!!,
            form.startDay!!,
            form.startHour!!,
            form.startMinute!!,
        )
        val welcome =
            if (form.welcomeRange.isNullOrBlank()) emptyList()
            else listOf(CDef.VillageTagItem.codeOf(form.welcomeRange).toModel())
        val age =
            if (form.ageLimit.isNullOrBlank()) emptyList()
            else listOf(CDef.VillageTagItem.codeOf(form.ageLimit).toModel())
        return village.copy(
            name = form.villageName!!,
            days = village.days.copy(
                list = village.days.list.map {
                    if (it.day == 0) it.copy(dayChangeDatetime = startDatetime)
                    else it.copy()
                },
            ),
            setting = village.setting.copy(
                chara = village.setting.chara.copy(
                    dummyDay1Message = form.dummyDay1Message,
                ),
                personMin = form.startPersonMinNum!!,
                personMax = form.personMaxNum!!,
                dayChangeIntervalSeconds = form.dayChangeIntervalHours!! * 3600 +
                        form.dayChangeIntervalMinutes!! * 60 +
                        form.dayChangeIntervalSeconds!!,
                startDatetime = startDatetime,
                rule = village.setting.rule.copy(
                    isOpenVote = form.openVote!!,
                    isAvailableSameWolfAttack = form.availableSameWolfAttack!!,
                    isOpenSkillInGrave = form.openSkillInGrave!!,
                    isVisibleGraveSpectateMessage = form.visibleGraveSpectateMessage!!,
                    isAvailableSpectate = form.availableSpectate!!,
                    isAvailableSuddenlyDeath = form.availableSuddonlyDeath!!,
                    isAvailableCommit = form.availableCommit!!,
                    isAvailableGuardSameTarget = form.availableGuardSameTarget!!,
                    isAvailableAction = form.availableAction!!,
                    isRandomOrganization = form.randomOrganization!!,
                    isReincarnationSkillAll = form.reincarnationSkillAll!!,
                    secretSayRange = SecretSayRange(CDef.AllowedSecretSay.codeOf(form.allowedSecretSayCode!!)),
                ),
                organize = VillageOrganize(
                    fixedOrganization = form.organization.orEmpty(),
                    randomOrganization = VillageRandomOrganize(
                        campAllocation = form.campAllocationList?.map {
                            VillageRandomOrganize.CampAllocation(
                                camp = Camp(CDef.Camp.codeOf(it.campCode)),
                                min = it.minNum!!,
                                max = it.maxNum,
                                initAllocation = it.allocation!!,
                                reincarnationAllocation = it.reincarnationAllocation!!,
                            )
                        } ?: emptyList(),
                        skillAllocation = form.campAllocationList?.flatMap { it.skillAllocation!! }?.map {
                            VillageRandomOrganize.SkillAllocation(
                                skill = Skill(CDef.Skill.codeOf(it.skillCode)),
                                min = it.minNum!!,
                                max = it.maxNum,
                                initAllocation = it.allocation!!,
                                reincarnationAllocation = it.reincarnationAllocation!!,
                            )
                        } ?: emptyList(),
                        wolfAllocation = form.wolfAllocation?.let {
                            VillageRandomOrganize.WolfAllocation(
                                min = it.minNum!!,
                                max = it.maxNum,
                            )
                        },
                    ),
                ),
                joinPassword = form.joinPassword,
                sayRestriction = SayRestriction(
                    normalSayRestriction = form.sayRestrictList!!.filter { it.restrict!! }.map {
                        SayRestriction.NormalSayRestriction(
                            skill = Skill(CDef.Skill.codeOf(it.skillCode)),
                            messageType = MessageType(CDef.MessageType.通常発言),
                            count = it.count!!,
                            length = it.length!!,
                        )
                    },
                    skillSayRestriction = (form.skillSayRestrictList!! + form.rpSayRestrictList!!)
                        .filter { it.restrict!! }
                        .map {
                            SayRestriction.SkillSayRestriction(
                                messageType = MessageType(CDef.MessageType.codeOf(it.messageTypeCode)),
                                count = it.count!!,
                                length = it.length!!,
                            )
                        },
                ),
                tags = VillageTags(list = welcome + age),
            ),
        )
    }
}
