package com.ort.app.web.form.validator;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.ort.app.util.SkillUtil;
import com.ort.app.web.form.NewVillageForm;
import com.ort.app.web.form.NewVillageRandomOrgCampDto;
import com.ort.app.web.form.NewVillageRandomOrgSkillDto;
import com.ort.dbflute.allcommon.CDef;
import com.ort.dbflute.allcommon.CDef.Camp;
import com.ort.fw.util.WolfMansionDateUtil;

@Component
public class NewVillageFormValidator implements Validator {

    @Override
    public boolean supports(Class<?> paramClass) {
        return NewVillageForm.class.isAssignableFrom(paramClass);
    }

    @Override
    public void validate(Object paramObject, Errors errors) {
        if (errors.hasErrors()) {
            // 既にエラーがある場合はチェックしない
            return;
        }

        NewVillageForm form = (NewVillageForm) paramObject;

        // 定員＜最低開始人数
        if (form.getPersonMaxNum() < form.getStartPersonMinNum()) {
            errors.rejectValue("personMaxNum", "NewVillageForm.validator.personMaxNum");
        }

        // 1分 <= 更新間隔 <= 48時間　に収まらない
        Integer dayChangeIntervalHours = form.getDayChangeIntervalHours();
        Integer dayChangeIntervalMinutes = form.getDayChangeIntervalMinutes();
        Integer dayChangeIntervalSeconds = form.getDayChangeIntervalSeconds();
        int intervalSeconds = dayChangeIntervalHours * 60 * 60 + dayChangeIntervalMinutes * 60 + dayChangeIntervalSeconds;
        if (intervalSeconds < 60 || 48 * 60 * 60 < intervalSeconds) {
            errors.rejectValue("dayChangeIntervalHours", "NewVillageForm.validator.dayChangeIntervalHours");
        }

        // 開始日時が過去や存在しない日付
        Integer startYear = form.getStartYear();
        Integer startMonth = form.getStartMonth();
        Integer startDay = form.getStartDay();
        Integer startHour = form.getStartHour();
        Integer startMinute = form.getStartMinute();
        LocalDateTime startDateTime = null;
        try {
            startDateTime = LocalDateTime.of(startYear, startMonth, startDay, startHour, startMinute);
            if (startDateTime.isBefore(WolfMansionDateUtil.currentLocalDateTime())) {
                errors.rejectValue("startYear", "NewVillageForm.validator.startYear");
            }
            // 二週間先までしか指定できない
            if (startDateTime.isAfter(WolfMansionDateUtil.currentLocalDateTime().plusDays(14L))) {
                errors.rejectValue("startYear", "NewVillageForm.validator.startYear");
            }
        } catch (DateTimeException e) {
            errors.rejectValue("startYear", "NewVillageForm.validator.startYear");
        }

        // ダミーキャラ発言
        validateMessage(errors, form);

        // 入村パスワードが入力されているが3文字以上12文字以下でない
        String joinPassword = form.getJoinPassword();
        if (StringUtils.isNotEmpty(joinPassword)) {
            if (joinPassword.length() < 3 || 12 < joinPassword.length()) {
                errors.rejectValue("joinPassword", "VillageSayForm.validator.joinPassword.length");
            }
        }

        // 構成
        validateOrganization(errors, form);

        // 発言制限
        validateSayRestrict(errors, form);
        validateSkillSayRestrict(errors, form);
        validateRpSayRestrict(errors, form);
    }

    private void validateMessage(Errors errors, NewVillageForm form) {
        String message = form.getDummyJoinMessage();
        if (message == null) {
            return;
        }
        // 末尾に改行文字列が含まれているとsplit時に削られるので削除してチェック
        String trimedMessage = message.trim();
        // 改行数＋それ以外の文字が400文字以上
        int length = trimedMessage.length();
        int lineSeparatorNum = trimedMessage.split("\r\n").length - 1;
        int messageLength = length - lineSeparatorNum;
        if (messageLength <= 0 || 400 < messageLength) {
            errors.rejectValue("dummyJoinMessage", "VillageSayForm.validator.message.length");
        }
        // 行数が21以上
        if (trimedMessage.split("\r\n").length > 20) {
            errors.rejectValue("dummyJoinMessage", "VillageSayForm.validator.message.line");
        }
    }

    private void validateOrganization(Errors errors, NewVillageForm form) {
        Boolean isRandomOrganization = form.getIsRandomOrganization();
        if (isRandomOrganization == null) {
            errors.rejectValue("organization", "VillageSayForm.validator.organization.required");
            return;
        }

        if (isRandomOrganization) {
            validateRandomOrganization(errors, form);
        } else {
            validateFixOrganization(errors, form);
        }
    }

    private void validateRandomOrganization(Errors errors, NewVillageForm form) {
        // 未入力があったらチェックしない
        List<NewVillageRandomOrgCampDto> campAllocationList = form.getCampAllocationList();
        Integer startPersonMin = form.getStartPersonMinNum();
        if (campAllocationList == null || startPersonMin == null || campAllocationList.stream().anyMatch(camp -> {
            return camp.getMinNum() == null || camp.getAllocation() == null
                    || camp.getSkillAllocation().stream().anyMatch(skill -> skill.getMinNum() == null);
        })) {
            return;
        }

        // 村人は最低一人必要
        NewVillageRandomOrgSkillDto villagerSkillOrg = campAllocationList.stream()
                .filter(c -> CDef.Camp.codeOf(c.getCampCode()) == CDef.Camp.村人陣営)
                .findFirst()
                .get()
                .getSkillAllocation()
                .stream()
                .filter(s -> CDef.Skill.codeOf(s.getSkillCode()) == CDef.Skill.村人)
                .findFirst()
                .get();
        if (villagerSkillOrg.getMinNum() <= 0) {
            errors.rejectValue("campAllocationList", "NewVillageForm.validator.campAllocationList.novillager");
            return;
        }

        // 陣営の最低人数の合計が村の最低人数よりも多かったらNG
        int campMinSum = campAllocationList.stream().mapToInt(camp -> camp.getMinNum()).sum();

        if (startPersonMin < campMinSum) {
            errors.rejectValue("campAllocationList", "NewVillageForm.validator.campAllocationList.campmin");
            return;
        }

        // 役職の最低人数の合計が村の最低人数よりも多かったらNG
        int skillMinSum = campAllocationList.stream()
                .flatMapToInt(camp -> camp.getSkillAllocation().stream().mapToInt(skill -> skill.getMinNum()))
                .sum();
        if (startPersonMin < skillMinSum) {
            errors.rejectValue("campAllocationList", "NewVillageForm.validator.campAllocationList.campmin");
            return;
        }

        // 狼系の最低人数が村の最低人数の半数を超えたらNG
        int wolfMinSum =
                campAllocationList.stream().filter(camp -> Camp.codeOf(camp.getCampCode()) == CDef.Camp.人狼陣営).flatMapToInt(camp -> {
                    return camp.getSkillAllocation()
                            .stream()
                            .filter(skill -> CDef.Skill.codeOf(skill.getSkillCode()).isWolfCount())
                            .mapToInt(skill -> skill.getMinNum());
                }).sum();
        if (startPersonMin <= wolfMinSum * 2) {
            errors.rejectValue("campAllocationList", "NewVillageForm.validator.campAllocationList.wolfmin");
            return;
        }

        // 狼系の最低人数が全て0かつ配分が全て0だったらNG
        int wolfAllocationSum =
                campAllocationList.stream().filter(camp -> Camp.codeOf(camp.getCampCode()) == CDef.Camp.人狼陣営).flatMapToInt(camp -> {
                    return camp.getSkillAllocation()
                            .stream()
                            .filter(skill -> CDef.Skill.codeOf(skill.getSkillCode()).isWolfCount())
                            .mapToInt(skill -> skill.getAllocation());
                }).sum();
        if (wolfMinSum <= 0 && wolfAllocationSum <= 0) {
            errors.rejectValue("campAllocationList", "NewVillageForm.validator.campAllocationList.nowolf");
            return;
        }

        // 最低>最大だったらNG
        if (campAllocationList.stream().anyMatch(camp -> {
            int min = camp.getMinNum();
            Integer max = camp.getMaxNum();
            return max != null && max < min;
        })) {
            errors.rejectValue("campAllocationList", "NewVillageForm.validator.campAllocationList.campmingtmax");
            return;
        }
        if (campAllocationList.stream().anyMatch(camp -> {
            return camp.getSkillAllocation().stream().anyMatch(skill -> {
                int min = skill.getMinNum();
                Integer max = skill.getMaxNum();
                return max != null && max < min;
            });
        })) {
            errors.rejectValue("campAllocationList", "NewVillageForm.validator.campAllocationList.skillmingtmax");
            return;
        }

        // 陣営の最大人数 < 陣営の役職の最低人数の合計だったらNG
        if (campAllocationList.stream().anyMatch(camp -> {
            Integer maxNum = camp.getMaxNum();
            if (maxNum == null) {
                return false;
            }
            int minSum = camp.getSkillAllocation().stream().mapToInt(skill -> skill.getMinNum()).sum();
            return maxNum < minSum;
        })) {
            errors.rejectValue("campAllocationList", "NewVillageForm.validator.campAllocationList.campmaxltskillminsum");
            return;
        }
    }

    private void validateFixOrganization(Errors errors, NewVillageForm form) {
        String organization = form.getOrganization();
        // 未入力
        if (StringUtils.isEmpty(organization)) {
            errors.rejectValue("organization", "VillageSayForm.validator.organization.required");
            return;
        }

        List<String> organizationList = Stream.of(organization.replace("\r\n", "\n").split("\n")).collect(Collectors.toList());

        // 最低人数〜最大人数までの構成が存在するか
        Integer minNum = form.getStartPersonMinNum();
        Integer maxNum = form.getPersonMaxNum();
        for (int i = minNum; i <= maxNum; i++) {
            int personNum = i;
            if (organizationList.stream().noneMatch(orgStr -> orgStr.length() == personNum)) {
                errors.rejectValue("organization", "NewVillageForm.validator.organization.lines");
                return;
            }
            if (organizationList.stream().filter(orgStr -> orgStr.length() == personNum).count() > 1) {
                errors.rejectValue("organization", "NewVillageForm.validator.organization.duplicate", new Object[] { personNum }, null);
                return;
            }
        }

        // 行ごとにチェック
        for (String org : organizationList) {
            int personNum = org.length();
            // 存在しない役職
            for (String character : org.split("")) {
                if (!SkillUtil.SKILL_SHORTNAME_MAP.containsKey(character)) {
                    errors.rejectValue("organization", "NewVillageForm.validator.organization.noexistskill",
                            new Object[] { personNum, character }, null);
                    return;
                }
            }
            // 役職人数制限
            if (isInvalidOrganizationSkillPersonNum(errors, org, personNum)) {
                return;
            }
        }
    }

    private boolean isInvalidOrganizationSkillPersonNum(Errors errors, String org, int personNum) {
        Map<CDef.Skill, Integer> skillPersonNumMap = SkillUtil.createSkillPersonNum(org);
        // 村人がいない
        if (skillPersonNumMap.get(CDef.Skill.村人) < 1) {
            errors.rejectValue("organization", "NewVillageForm.validator.organization.noexistvillager", new Object[] { personNum }, null);
            return true;
        }
        // 人狼がいない
        int wolfsNum = CDef.Skill.listOfHasAttackAbility().stream().mapToInt(skill -> skillPersonNumMap.get(skill)).sum();
        if (wolfsNum < 1) {
            errors.rejectValue("organization", "NewVillageForm.validator.organization.noexistwerewolf", new Object[] { personNum }, null);
            return true;
        }
        // 人狼の人数が過半数を超えている
        if (wolfsNum > org.length() / 2) {
            errors.rejectValue("organization", "NewVillageForm.validator.organization.werewolfwin", new Object[] { personNum }, null);
            return true;
        }
        // 恋人や同棲者の人数が偶数でない
        int loversNum = skillPersonNumMap.get(CDef.Skill.恋人);
        int cohabiterNum = skillPersonNumMap.get(CDef.Skill.同棲者);
        if (loversNum % 2 == 1) {
            errors.rejectValue("organization", "NewVillageForm.validator.organization.loversodd", new Object[] { personNum }, null);
            return true;
        }
        if (cohabiterNum % 2 == 1) {
            errors.rejectValue("organization", "NewVillageForm.validator.organization.cohabiterodd", new Object[] { personNum }, null);
            return true;
        }

        return false;
    }

    // 発言制限
    private void validateSayRestrict(Errors errors, NewVillageForm form) {
        boolean isCountInvalid = form.getSayRestrictList().stream().anyMatch(r -> {
            return BooleanUtils.isTrue(r.getIsRestrict()) && (r.getCount() == null || r.getCount() < 0 || r.getCount() > 100);
        });
        boolean isLengthInvalid = form.getSayRestrictList().stream().anyMatch(r -> {
            return BooleanUtils.isTrue(r.getIsRestrict()) && (r.getLength() == null || r.getLength() < 0 || r.getLength() > 400);
        });
        if (isCountInvalid || isLengthInvalid) {
            errors.rejectValue("sayRestrictList", "NewVillageForm.validator.sayRestrictList", new Object[] {}, null);
        }
    }

    private void validateSkillSayRestrict(Errors errors, NewVillageForm form) {
        boolean isCountInvalid = form.getSkillSayRestrictList().stream().anyMatch(r -> {
            return BooleanUtils.isTrue(r.getIsRestrict()) && (r.getCount() == null || r.getCount() < 0 || r.getCount() > 100);
        });
        boolean isLengthInvalid = form.getSkillSayRestrictList().stream().anyMatch(r -> {
            return BooleanUtils.isTrue(r.getIsRestrict()) && (r.getLength() == null || r.getLength() < 0 || r.getLength() > 400);
        });
        if (isCountInvalid || isLengthInvalid) {
            errors.rejectValue("skillSayRestrictList", "NewVillageForm.validator.sayRestrictList", new Object[] {}, null);
        }
    }

    private void validateRpSayRestrict(Errors errors, NewVillageForm form) {
        boolean isCountInvalid = form.getRpSayRestrictList().stream().anyMatch(r -> {
            return BooleanUtils.isTrue(r.getIsRestrict()) && (r.getCount() == null || r.getCount() < 0 || r.getCount() > 100);
        });
        boolean isLengthInvalid = form.getRpSayRestrictList().stream().anyMatch(r -> {
            return BooleanUtils.isTrue(r.getIsRestrict()) && (r.getLength() == null || r.getLength() < 0 || r.getLength() > 400);
        });
        if (isCountInvalid || isLengthInvalid) {
            errors.rejectValue("rpSayRestrictList", "NewVillageForm.validator.sayRestrictList", new Object[] {}, null);
        }
    }
}
