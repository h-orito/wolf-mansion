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
}
