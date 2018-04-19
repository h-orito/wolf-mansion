package com.ort.app.web.form.validator;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.ort.app.web.form.NewVillageForm;
import com.ort.app.web.util.SkillUtil;
import com.ort.dbflute.allcommon.CDef;
import com.ort.dbflute.allcommon.CDef.Skill;
import com.ort.fw.util.WerewolfMansionDateUtil;

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
            if (startDateTime.isBefore(WerewolfMansionDateUtil.currentLocalDateTime())) {
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

    }

    private void validateMessage(Errors errors, NewVillageForm form) {
        String message = form.getDummyJoinMessage();
        if (message == null) {
            return;
        }
        // 末尾に改行文字列が含まれているとsplit時に削られるので削除してチェック
        String trimedMessage = message.trim();
        // 改行数＋それ以外の文字が200文字以上
        int length = trimedMessage.length();
        int lineSeparatorNum = trimedMessage.split("\r\n").length - 1;
        int messageLength = length - lineSeparatorNum;
        if (messageLength <= 0 || 200 < messageLength) {
            errors.rejectValue("dummyJoinMessage", "VillageSayForm.validator.message.length");
        }
        // 行数が11以上
        if (trimedMessage.split("\r\n").length > 10) {
            errors.rejectValue("dummyJoinMessage", "VillageSayForm.validator.message.line");
        }
    }

    private void validateOrganization(Errors errors, NewVillageForm form) {
        String organization = form.getOrganization();
        // 未入力
        if (StringUtils.isEmpty(organization)) {
            errors.rejectValue("organization", "VillageSayForm.validator.organization.required");
            return;
        }
        // 行数不足
        String[] organizationArray = organization.replace("\r\n", "\n").split("\n");
        if (organizationArray.length < 13) { // 8~20名
            errors.rejectValue("organization", "VillageSayForm.validator.organization.lines");
            return;
        }
        // 行ごとにチェック
        int personNum = 8;
        for (String org : organizationArray) {
            int lineNum = personNum - 7;
            // 人数過不足
            if (org.length() != personNum) {
                errors.rejectValue("organization", "VillageSayForm.validator.organization.personNum", new Object[] { lineNum, personNum },
                        null);
                return;
            }
            // 存在しない役職
            for (String character : org.split("")) {
                if (!SkillUtil.SKILL_SHORTNAME_MAP.containsKey(character)) {
                    errors.rejectValue("organization", "VillageSayForm.validator.organization.noexistskill",
                            new Object[] { lineNum, character }, null);
                    return;
                }
            }
            // 役職人数制限
            if (isInvalidOrganizationSkillPersonNum(errors, org, lineNum)) {
                return;
            }

            personNum++;
        }
    }

    private boolean isInvalidOrganizationSkillPersonNum(Errors errors, String org, int lineNum) {
        Map<CDef.Skill, Integer> skillPersonNumMap = createSkillPersonNum(org);
        // 占い師と賢者は1名まで
        if (skillPersonNumMap.get(CDef.Skill.占い師) + skillPersonNumMap.get(CDef.Skill.賢者) > 1) {
            errors.rejectValue("organization", "VillageSayForm.validator.organization.maxperson", new Object[] { "占い師と賢者", 1, lineNum },
                    null);
            return true;
        }
        // 狂人と魔神官とC国狂人は最大1名まで
        if (skillPersonNumMap.get(CDef.Skill.狂人) + skillPersonNumMap.get(CDef.Skill.魔神官) + skillPersonNumMap.get(CDef.Skill.C国狂人) > 1) {
            errors.rejectValue("organization", "VillageSayForm.validator.organization.maxperson",
                    new Object[] { "狂人と魔神官とC国狂人", 1, lineNum }, null);
            return true;
        }
        // 狩人は最大1名まで
        if (skillPersonNumMap.get(CDef.Skill.狩人) > 1) {
            errors.rejectValue("organization", "VillageSayForm.validator.organization.maxperson", new Object[] { "狩人", 1, lineNum }, null);
            return true;
        }
        // 妖狐は最大1名まで
        if (skillPersonNumMap.get(CDef.Skill.妖狐) > 1) {
            errors.rejectValue("organization", "VillageSayForm.validator.organization.maxperson", new Object[] { "妖狐", 1, lineNum }, null);
            return true;
        }
        // 村人がいない
        if (skillPersonNumMap.get(CDef.Skill.村人) < 1) {
            errors.rejectValue("organization", "VillageSayForm.validator.organization.noexistvillager", new Object[] { lineNum }, null);
            return true;
        }
        // 人狼がいない
        if (skillPersonNumMap.get(CDef.Skill.人狼) < 1) {
            errors.rejectValue("organization", "VillageSayForm.validator.organization.noexistwerewolf", new Object[] { lineNum }, null);
            return true;
        }
        // 人狼の人数が過半数を超えている
        if (skillPersonNumMap.get(CDef.Skill.人狼) > org.length() / 2) {
            errors.rejectValue("organization", "VillageSayForm.validator.organization.werewolfwin", new Object[] { lineNum }, null);
            return true;
        }

        return false;
    }

    private Map<Skill, Integer> createSkillPersonNum(String org) {
        Map<CDef.Skill, Integer> skillPersonNumMap = new HashMap<>();
        for (CDef.Skill skill : CDef.Skill.values()) {
            skillPersonNumMap.put(skill, 0);
        }
        for (String character : org.split("")) {
            CDef.Skill skill = SkillUtil.SKILL_SHORTNAME_MAP.get(character);
            Integer skillPersonNum = skillPersonNumMap.get(skill);
            skillPersonNum++;
            skillPersonNumMap.put(skill, skillPersonNum);
        }
        return skillPersonNumMap;
    }
}
