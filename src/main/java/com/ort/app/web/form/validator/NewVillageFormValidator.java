package com.ort.app.web.form.validator;

import java.time.DateTimeException;
import java.time.LocalDateTime;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.ort.app.web.form.NewVillageForm;
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

    }

}
