package com.ort.app.web.form;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class VillageSettingsForm implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 最低開始人数 */
    @NotNull
    @Min(8)
    private Integer startPersonMinNum;

    /** 定員 */
    @NotNull
    @Max(99)
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

    /** 記名投票か */
    @NotNull
    private Boolean isOpenVote;

    /** 連続襲撃ありか */
    @NotNull
    private Boolean isAvailableSameWolfAttack;

    /** 墓下役職公開ありか */
    @NotNull
    private Boolean isOpenSkillInGrave;

    /** 墓下見学発言を地上から見られるか */
    @NotNull
    private Boolean isVisibleGraveSpectateMessage;

    /** 秘話可能範囲 */
    @NotNull
    private String allowedSecretSayCode;

    /** 観戦を可能にする */
    @NotNull
    private Boolean isAvailableSpectate;

    /** 突然死ありか */
    @NotNull
    private Boolean isAvailableSuddonlyDeath;

    /** コミット可能か */
    @NotNull
    private Boolean isAvailableCommit;

    /** 連続ガードありか */
    @NotNull
    private Boolean isAvailableGuardSameTarget;

    /** 構成 */
    @NotNull
    private String organization;

    /** 入村パスワード */
    private String joinPassword;

    /** 発言制限 */
    @NotNull
    private List<NewVillageSayRestrictDto> sayRestrictList;

    /** 役職発言制限 */
    @NotNull
    private List<NewVillageSkillSayRestrictDto> skillSayRestrictList;

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

    public Boolean getIsOpenVote() {
        return isOpenVote;
    }

    public void setIsOpenVote(Boolean isOpenVote) {
        this.isOpenVote = isOpenVote;
    }

    public Boolean getIsAvailableSameWolfAttack() {
        return isAvailableSameWolfAttack;
    }

    public void setIsAvailableSameWolfAttack(Boolean isAvailableSameWolfAttack) {
        this.isAvailableSameWolfAttack = isAvailableSameWolfAttack;
    }

    public Boolean getIsOpenSkillInGrave() {
        return isOpenSkillInGrave;
    }

    public void setIsOpenSkillInGrave(Boolean isOpenSkillInGrave) {
        this.isOpenSkillInGrave = isOpenSkillInGrave;
    }

    public Boolean getIsVisibleGraveSpectateMessage() {
        return isVisibleGraveSpectateMessage;
    }

    public void setIsVisibleGraveSpectateMessage(Boolean isVisibleGraveSpectateMessage) {
        this.isVisibleGraveSpectateMessage = isVisibleGraveSpectateMessage;
    }

    public Boolean getIsAvailableSuddonlyDeath() {
        return isAvailableSuddonlyDeath;
    }

    public void setIsAvailableSuddonlyDeath(Boolean isAvailableSuddonlyDeath) {
        this.isAvailableSuddonlyDeath = isAvailableSuddonlyDeath;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getJoinPassword() {
        return joinPassword;
    }

    public void setJoinPassword(String joinPassword) {
        this.joinPassword = joinPassword;
    }

    public Boolean getIsAvailableSpectate() {
        return isAvailableSpectate;
    }

    public void setIsAvailableSpectate(Boolean isAvailableSpectate) {
        this.isAvailableSpectate = isAvailableSpectate;
    }

    public Boolean getIsAvailableCommit() {
        return isAvailableCommit;
    }

    public void setIsAvailableCommit(Boolean isAvailableCommit) {
        this.isAvailableCommit = isAvailableCommit;
    }

    public Boolean getIsAvailableGuardSameTarget() {
        return isAvailableGuardSameTarget;
    }

    public void setIsAvailableGuardSameTarget(Boolean isAvailableGuardSameTarget) {
        this.isAvailableGuardSameTarget = isAvailableGuardSameTarget;
    }

    public String getAllowedSecretSayCode() {
        return allowedSecretSayCode;
    }

    public void setAllowedSecretSayCode(String allowedSecretSayCode) {
        this.allowedSecretSayCode = allowedSecretSayCode;
    }

    public List<NewVillageSayRestrictDto> getSayRestrictList() {
        return sayRestrictList;
    }

    public void setSayRestrictList(List<NewVillageSayRestrictDto> sayRestrictList) {
        this.sayRestrictList = sayRestrictList;
    }

    public List<NewVillageSkillSayRestrictDto> getSkillSayRestrictList() {
        return skillSayRestrictList;
    }

    public void setSkillSayRestrictList(List<NewVillageSkillSayRestrictDto> skillSayRestrictList) {
        this.skillSayRestrictList = skillSayRestrictList;
    }
}
