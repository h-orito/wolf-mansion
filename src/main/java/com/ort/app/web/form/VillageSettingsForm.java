package com.ort.app.web.form;

import java.io.Serializable;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class VillageSettingsForm implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 最低開始人数 */
    @NotNull
    @Min(5)
    private Integer startPersonMinNum;

    /** 定員 */
    @NotNull
    @Max(20)
    private Integer personMaxNum;

    /** 更新間隔時間 */
    @Min(0)
    @Max(48)
    private Integer dayChangeIntervalHours;

    /** 更新間隔分 */
    @Min(0)
    @Max(59)
    private Integer dayChangeIntervalMinutes;

    /** 更新間隔秒 */
    @Min(0)
    @Max(59)
    private Integer dayChangeIntervalSeconds;

    /** 開始年 */
    @Min(0)
    private Integer startYear;
    /** 開始月 */
    @Min(1)
    @Max(12)
    private Integer startMonth;
    /** 開始日 */
    @Min(1)
    @Max(31)
    private Integer startDay;
    /** 開始時間 */
    @Min(0)
    @Max(23)
    private Integer startHour;
    /** 開始分 */
    @Min(0)
    @Max(59)
    private Integer startMinute;

    public Integer getStartPersonMinNum() {
        return startPersonMinNum;
    }

    public void setStartPersonMinNum(Integer startPersonMinNum) {
        this.startPersonMinNum = startPersonMinNum;
    }

    public Integer getPersonMaxNum() {
        return personMaxNum;
    }

    public void setPersonMaxNum(Integer personMaxNum) {
        this.personMaxNum = personMaxNum;
    }

    public Integer getDayChangeIntervalHours() {
        return dayChangeIntervalHours;
    }

    public void setDayChangeIntervalHours(Integer dayChangeIntervalHours) {
        this.dayChangeIntervalHours = dayChangeIntervalHours;
    }

    public Integer getDayChangeIntervalMinutes() {
        return dayChangeIntervalMinutes;
    }

    public void setDayChangeIntervalMinutes(Integer dayChangeIntervalMinutes) {
        this.dayChangeIntervalMinutes = dayChangeIntervalMinutes;
    }

    public Integer getDayChangeIntervalSeconds() {
        return dayChangeIntervalSeconds;
    }

    public void setDayChangeIntervalSeconds(Integer dayChangeIntervalSeconds) {
        this.dayChangeIntervalSeconds = dayChangeIntervalSeconds;
    }

    public Integer getStartYear() {
        return startYear;
    }

    public void setStartYear(Integer startYear) {
        this.startYear = startYear;
    }

    public Integer getStartMonth() {
        return startMonth;
    }

    public void setStartMonth(Integer startMonth) {
        this.startMonth = startMonth;
    }

    public Integer getStartDay() {
        return startDay;
    }

    public void setStartDay(Integer startDay) {
        this.startDay = startDay;
    }

    public Integer getStartHour() {
        return startHour;
    }

    public void setStartHour(Integer startHour) {
        this.startHour = startHour;
    }

    public Integer getStartMinute() {
        return startMinute;
    }

    public void setStartMinute(Integer startMinute) {
        this.startMinute = startMinute;
    }

}
