package com.ort.app.web.model.inner;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class VillageParticipantRecordDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /** ユーザID */
    @JsonProperty("user_id")
    private String userId;

    /** キャラ名 */
    @JsonProperty("character_name")
    private String characterName;

    /** 役職名 */
    @JsonProperty("skill_name")
    private String skillName;

    /** 見学か */
    @JsonProperty("spectator")
    private Boolean isSpectator;

    /** 勝利したか */
    @JsonProperty("win")
    private Boolean isWin;

    /** 死亡したか */
    @JsonProperty("dead")
    private Boolean isDead;

    /** 死亡日 */
    @JsonProperty("dead_day")
    private Integer deadDay;

    /** 死亡理由 */
    @JsonProperty("dead_reason")
    private String deadReason;

    /** 勝敗判定陣営 */
    @JsonProperty("camp_name")
    private String campName;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCharacterName() {
        return characterName;
    }

    public void setCharacterName(String characterName) {
        this.characterName = characterName;
    }

    public String getSkillName() {
        return skillName;
    }

    public void setSkillName(String skillName) {
        this.skillName = skillName;
    }

    public Boolean getIsWin() {
        return isWin;
    }

    public void setIsWin(Boolean isWin) {
        this.isWin = isWin;
    }

    public Boolean getIsDead() {
        return isDead;
    }

    public void setIsDead(Boolean isDead) {
        this.isDead = isDead;
    }

    public Integer getDeadDay() {
        return deadDay;
    }

    public void setDeadDay(Integer deadDay) {
        this.deadDay = deadDay;
    }

    public String getDeadReason() {
        return deadReason;
    }

    public void setDeadReason(String deadReason) {
        this.deadReason = deadReason;
    }

    public Boolean getIsSpectator() {
        return isSpectator;
    }

    public void setIsSpectator(Boolean isSpectator) {
        this.isSpectator = isSpectator;
    }

    public String getCampName() {
        return campName;
    }

    public void setCampName(String campName) {
        this.campName = campName;
    }
}
