package com.ort.app.web.model.inner;

import java.time.LocalDateTime;

public class VillageSettingsDto {

    /** 最低開始人数 */
    private Integer startPersonMinNum;

    /** 定員 */
    private Integer personMaxNum;

    /** 開始日時 */
    private LocalDateTime startDatetime;

    /** 更新間隔 */
    private String dayChangeInterval;

    /** 投票形式 */
    private String voteType;

    /** 役職希望 */
    private String skillRequestType;

    /** キャラセット名 */
    private String charaGroupName;

    /** キャラクターグループID */
    private Integer charaGroupId;

    /** 入村パスワードを必要とするか */
    private Boolean isRequiredJoinPassword;

    /** 見学が可能か */
    private Boolean isAvailableSpectate;

    /** 連続襲撃ありか */
    private Boolean isAvailableSameWolfAttack;

    /** 墓下役職公開ありか */
    private Boolean isOpenSkillInGrave;

    /** 墓下見学発言を地上から見られるか */
    private Boolean isVisibleGraveSpectateMessage;

    /** 構成 */
    private String organization;

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

    public LocalDateTime getStartDatetime() {
        return startDatetime;
    }

    public void setStartDatetime(LocalDateTime startDatetime) {
        this.startDatetime = startDatetime;
    }

    public String getDayChangeInterval() {
        return dayChangeInterval;
    }

    public void setDayChangeInterval(String dayChangeInterval) {
        this.dayChangeInterval = dayChangeInterval;
    }

    public String getVoteType() {
        return voteType;
    }

    public void setVoteType(String voteType) {
        this.voteType = voteType;
    }

    public String getSkillRequestType() {
        return skillRequestType;
    }

    public void setSkillRequestType(String skillRequestType) {
        this.skillRequestType = skillRequestType;
    }

    public String getCharaGroupName() {
        return charaGroupName;
    }

    public void setCharaGroupName(String charaGroupName) {
        this.charaGroupName = charaGroupName;
    }

    public Integer getCharaGroupId() {
        return charaGroupId;
    }

    public void setCharaGroupId(Integer charaGroupId) {
        this.charaGroupId = charaGroupId;
    }

    public Boolean getIsRequiredJoinPassword() {
        return isRequiredJoinPassword;
    }

    public void setIsRequiredJoinPassword(Boolean isRequiredJoinPassword) {
        this.isRequiredJoinPassword = isRequiredJoinPassword;
    }

    public Boolean getIsAvailableSpectate() {
        return isAvailableSpectate;
    }

    public void setIsAvailableSpectate(Boolean isAvailableSpectate) {
        this.isAvailableSpectate = isAvailableSpectate;
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

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }
}
