package com.ort.app.api.request.validator

import com.ort.app.api.request.NewVillageForm
import com.ort.app.domain.model.skill.Skill
import com.ort.app.domain.model.skill.Skills
import com.ort.app.domain.model.skill.toModel
import com.ort.dbflute.allcommon.CDef
import org.springframework.stereotype.Component
import org.springframework.validation.Errors
import org.springframework.validation.Validator
import java.time.DateTimeException
import java.time.LocalDateTime

@Component
class NewVillageFormValidator : Validator {

    override fun supports(clazz: Class<*>): Boolean {
        return Unit::class.java.isAssignableFrom(clazz) || NewVillageForm::class.java.isAssignableFrom(clazz)
    }

    override fun validate(target: Any, errors: Errors) {
        if (errors.hasErrors()) return

        val form = target as NewVillageForm

        // 定員＜最低開始人数
        if (form.personMaxNum!! < form.startPersonMinNum!!) {
            errors.rejectValue("personMaxNum", "NewVillageForm.validator.personMaxNum")
        }

        // 1分 <= 更新間隔 <= 48時間　に収まらない
        val intervalSeconds = form.dayChangeIntervalHours!! * 60 * 60 +
                form.dayChangeIntervalMinutes!! * 60 +
                form.dayChangeIntervalSeconds!!
        if (intervalSeconds !in 60..72 * 60 * 60) {
            errors.rejectValue("dayChangeIntervalHours", "NewVillageForm.validator.dayChangeIntervalHours")
        }

        // 開始日時が過去や存在しない日付
        try {
            val startDateTime = LocalDateTime.of(
                form.startYear!!,
                form.startMonth!!,
                form.startDay!!,
                form.startHour!!,
                form.startMinute!!
            )
            if (startDateTime.isBefore(LocalDateTime.now())) {
                errors.rejectValue("startYear", "NewVillageForm.validator.startYear")
            }
            // 二週間先までしか指定できない
            if (startDateTime.isAfter(LocalDateTime.now().plusDays(14L))) {
                errors.rejectValue("startYear", "NewVillageForm.validator.startYear")
            }
        } catch (e: DateTimeException) {
            errors.rejectValue("startYear", "NewVillageForm.validator.startYear")
        }

        // ダミーキャラ発言
        validateMessage(errors, form.dummyJoinMessage!!.trim(), "dummyJoinMessage")
        if (!form.dummyDay1Message.isNullOrEmpty()) {
            validateMessage(errors, form.dummyDay1Message!!.trim(), "dummyDay1Message")
        }

        // オリジナル画像
        validateOriginalImage(errors, form)

        // 入村パスワードが入力されているが3文字以上12文字以下でない
        val joinPassword = form.joinPassword
        if (!joinPassword.isNullOrEmpty()) {
            if (joinPassword.length !in 3..12) {
                errors.rejectValue("joinPassword", "NewVillageForm.validator.joinPassword.length")
            }
        }
        if (form.shouldOriginalImage == true && joinPassword.isNullOrEmpty()) {
            errors.rejectValue("joinPassword", "NewVillageForm.validator.joinPassword.original")
        }

        // 構成
        validateOrganization(errors, form)

        // 発言制限
        validateSayRestrict(errors, form)
        validateSkillSayRestrict(errors, form)
        validateRpSayRestrict(errors, form)
    }

    private fun validateMessage(errors: Errors, message: String, field: String) {
        // 改行数＋それ以外の文字が400文字以上
        val length = message.length
        val lineSeparatorNum = message.split("\r\n").size - 1
        val messageLength = length - lineSeparatorNum
        if (messageLength !in 1..400) {
            errors.rejectValue(field, "VillageSayForm.validator.message.length")
        }
        // 行数が21以上
        if (message.split("\r\n").size > 20) {
            errors.rejectValue(field, "VillageSayForm.validator.message.line")
        }
    }

    // オリジナル画像
    private fun validateOriginalImage(errors: Errors, form: NewVillageForm) {
        val shouldOriginalImage = form.shouldOriginalImage ?: false
        if (!shouldOriginalImage) return
        val file = form.dummyCharaImageFile ?: return
        if (file.size == 0L || 100000L < file.size) {
            errors.rejectValue("dummyCharaName", "NewVillageForm.validator.dummyCharaImageFile.size")
        }
    }

    private fun validateOrganization(errors: Errors, form: NewVillageForm) {
        if (form.randomOrganization!!) {
            validateRandomOrganization(errors, form)
        } else {
            validateFixOrganization(errors, form)
        }
    }

    private fun validateRandomOrganization(errors: Errors, form: NewVillageForm) {
        // 未入力があったらチェックしない
        val campAllocationList = form.campAllocationList ?: return
        val wolfAllocation = form.wolfAllocation ?: return
        val startPersonMin = form.startPersonMinNum!!

        // 村人は最低一人必要
        val villagerSkillOrg = campAllocationList
            .first { CDef.Camp.codeOf(it.campCode) == CDef.Camp.村人陣営 }
            .skillAllocation!!
            .first { CDef.Skill.codeOf(it.skillCode) == CDef.Skill.村人 }
        if (villagerSkillOrg.minNum!! <= 0) {
            errors.rejectValue("campAllocationList", "NewVillageForm.validator.campAllocationList.novillager")
            return
        }

        // 初期配役に含められない役職の最少最多が0より多い
        val existsNotOrganizableSkillNotMinMaxZero = campAllocationList.any {
            it.skillAllocation!!.any {
                !CDef.Skill.codeOf(it.skillCode).toModel().isRequestable()
                        && (it.minNum != 0 || it.maxNum != 0)
            }
        }
        if (existsNotOrganizableSkillNotMinMaxZero) {
            errors.rejectValue("campAllocationList", "NewVillageForm.validator.campAllocationList.cantorganize")
            return
        }

        // 陣営の最低人数の合計が村の最低人数よりも多かったらNG
        val campMinSum = campAllocationList.sumOf { it.minNum!! }
        if (startPersonMin < campMinSum) {
            errors.rejectValue("campAllocationList", "NewVillageForm.validator.campAllocationList.campmin")
            return
        }

        // 役職の最低人数の合計が村の最低人数よりも多かったらNG
        val skillMinSum = campAllocationList
            .flatMap { it.skillAllocation!!.map { skill -> skill.minNum!! } }
            .sum()
        if (startPersonMin < skillMinSum) {
            errors.rejectValue("campAllocationList", "NewVillageForm.validator.campAllocationList.campmin")
            return
        }

        // 狼系の最低人数が村の最低人数の半数を超えたらNG
        val wolfMinSum = campAllocationList.filter {
            CDef.Camp.codeOf(it.campCode) == CDef.Camp.人狼陣営
        }.flatMap {
            it.skillAllocation!!.filter { skill ->
                CDef.Skill.codeOf(skill.skillCode).isWolfCount
            }.map { skill -> skill.minNum!! }
        }.sum()
        if (startPersonMin <= wolfMinSum * 2 || startPersonMin <= wolfAllocation.minNum!!) {
            errors.rejectValue("campAllocationList", "NewVillageForm.validator.campAllocationList.wolfmin")
            return
        }

        // 人狼カウントの最多人数が狼系の最低人数を下回っていたらNG
        if (wolfAllocation.maxNum != null && wolfAllocation.maxNum!! < wolfMinSum) {
            errors.rejectValue("campAllocationList", "NewVillageForm.validator.campAllocationList.wolfmaxgtmin")
            return
        }

        // 最少>最多だったらNG
        if (wolfAllocation.maxNum != null && wolfAllocation.maxNum!! < wolfAllocation.minNum!!) {
            errors.rejectValue("campAllocationList", "NewVillageForm.validator.campAllocationList.campmingtmax")
            return
        }
        if (campAllocationList.any {
                val min = it.minNum!!
                val max = it.maxNum
                max != null && max < min
            }) {
            errors.rejectValue("campAllocationList", "NewVillageForm.validator.campAllocationList.campmingtmax")
            return
        }
        if (campAllocationList.any {
                it.skillAllocation!!.any { skill ->
                    val min = skill.minNum!!
                    val max = skill.maxNum
                    max != null && max < min
                }
            }) {
            errors.rejectValue("campAllocationList", "NewVillageForm.validator.campAllocationList.skillmingtmax")
            return
        }

        // 陣営の最多人数 < 陣営の役職の最低人数の合計だったらNG
        if (campAllocationList.any {
                val maxNum = it.maxNum
                val minSum = it.skillAllocation!!.map { skill -> skill.minNum!! }.sum()
                maxNum != null && maxNum < minSum
            }) {
            errors.rejectValue("campAllocationList", "NewVillageForm.validator.campAllocationList.campmaxltskillminsum")
            return
        }

        // 転生 --------------------------------------------
        // 一つも割り当てられる陣営がない
        if (campAllocationList.none { it.reincarnationAllocation!! > 0 }) {
            errors.rejectValue(
                "campAllocationList",
                "NewVillageForm.validator.campAllocationList.reincarnationAllocationAllZero"
            )
            return
        }

        // 陣営配分があるのに役職配分がすべて0
        if (campAllocationList.any { camp ->
                camp.reincarnationAllocation!! > 0 && camp.skillAllocation!!.all { it.reincarnationAllocation == 0 }
            }) {
            errors.rejectValue(
                "campAllocationList",
                "NewVillageForm.validator.campAllocationList.reincarnationAllocationButSkillAllocationAllZero"
            )
            return
        }
    }

    private fun validateFixOrganization(errors: Errors, form: NewVillageForm) {
        val organization = form.organization
        // 未入力
        if (organization.isNullOrBlank()) {
            errors.rejectValue("organization", "VillageSayForm.validator.organization.required")
            return
        }

        val organizationList = organization.replace("\r\n", "\n").split("\n")

        // 最低人数〜最多人数までの構成が存在するか
        val minNum: Int = form.startPersonMinNum!!
        val maxNum: Int = form.personMaxNum!!
        for (num in minNum..maxNum) {
            if (organizationList.none { orgStr: String -> orgStr.length == num }) {
                errors.rejectValue("organization", "NewVillageForm.validator.organization.lines")
                return
            }
            if (organizationList.count { orgStr: String -> orgStr.length == num } > 1) {
                errors.rejectValue(
                    "organization", "NewVillageForm.validator.organization.duplicate",
                    arrayOf<Any>(num), null
                )
                return
            }
        }

        // 行ごとにチェック
        for (org in organizationList) {
            val personNum = org.length
            // 存在しない役職
            for (index in org.indices) {
                val shortName = org[index].toString()
                val skill = Skill.byShortName(shortName)
                if (skill == null || Skills.organizables().list.none { it.toCdef() == skill.toCdef() }) {
                    errors.rejectValue(
                        "organization",
                        "NewVillageForm.validator.organization.noexistskill",
                        arrayOf(personNum, shortName),
                        null
                    )
                    return
                }
            }
            // 役職人数制限
            if (isInvalidOrganizationSkillPersonNum(errors, org, personNum)) {
                return
            }
        }
    }

    private fun isInvalidOrganizationSkillPersonNum(errors: Errors, org: String, personNum: Int): Boolean {
        val skillPersonNumMap = Skill.convertToSkillPersonNumMap(org)
        // 村人がいない
        if (skillPersonNumMap[CDef.Skill.村人]!! < 1) {
            errors.rejectValue(
                "organization",
                "NewVillageForm.validator.organization.noexistvillager",
                arrayOf<Any>(personNum),
                null
            )
            return true
        }
        // 人狼がいない
        val wolfsNum = CDef.Skill.listOfHasAttackAbility().sumBy { skillPersonNumMap[it]!! }
        if (wolfsNum < 1) {
            errors.rejectValue(
                "organization",
                "NewVillageForm.validator.organization.noexistwerewolf",
                arrayOf<Any>(personNum),
                null
            )
            return true
        }
        // 人狼の人数が過半数を超えている
        if (wolfsNum > org.length / 2) {
            errors.rejectValue(
                "organization",
                "NewVillageForm.validator.organization.werewolfwin",
                arrayOf<Any>(personNum),
                null
            )
            return true
        }
        // 恋人や同棲者の人数が偶数でない
        val loversNum = skillPersonNumMap[CDef.Skill.恋人]!!
        val cohabiterNum = skillPersonNumMap[CDef.Skill.同棲者]!!
        if (loversNum % 2 == 1) {
            errors.rejectValue(
                "organization",
                "NewVillageForm.validator.organization.loversodd",
                arrayOf<Any>(personNum),
                null
            )
            return true
        }
        if (cohabiterNum % 2 == 1) {
            errors.rejectValue(
                "organization",
                "NewVillageForm.validator.organization.cohabiterodd",
                arrayOf<Any>(personNum),
                null
            )
            return true
        }

        return false
    }

    private fun validateSayRestrict(errors: Errors, form: NewVillageForm) {
        val isCountInvalid: Boolean = form.sayRestrictList!!.any {
            it.restrict == true && (it.count == null || it.count!! !in 0..100)
        }
        val isLengthInvalid: Boolean = form.sayRestrictList!!.any {
            it.restrict == true && (it.length == null || it.length!! !in 0..400)
        }
        if (isCountInvalid || isLengthInvalid) {
            errors.rejectValue("sayRestrictList", "NewVillageForm.validator.sayRestrictList", arrayOf(), null)
        }
    }

    private fun validateSkillSayRestrict(errors: Errors, form: NewVillageForm) {
        val isCountInvalid: Boolean = form.skillSayRestrictList!!.any {
            it.restrict == true && (it.count == null || it.count!! !in 0..100)
        }
        val isLengthInvalid: Boolean = form.skillSayRestrictList!!.any {
            it.restrict == true && (it.length == null || it.length!! !in 0..400)
        }
        if (isCountInvalid || isLengthInvalid) {
            errors.rejectValue("skillSayRestrictList", "NewVillageForm.validator.sayRestrictList", arrayOf(), null)
        }
    }

    private fun validateRpSayRestrict(errors: Errors, form: NewVillageForm) {
        val isCountInvalid: Boolean = form.rpSayRestrictList!!.any {
            it.restrict == true && (it.count == null || it.count!! !in 0..100)
        }
        val isLengthInvalid: Boolean = form.rpSayRestrictList!!.any {
            it.restrict == true && (it.length == null || it.length!! !in 0..400)
        }
        if (isCountInvalid || isLengthInvalid) {
            errors.rejectValue("rpSayRestrictList", "NewVillageForm.validator.sayRestrictList", arrayOf(), null)
        }
    }
}