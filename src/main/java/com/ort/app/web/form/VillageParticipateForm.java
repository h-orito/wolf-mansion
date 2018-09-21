package com.ort.app.web.form;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

public class VillageParticipateForm implements Serializable {

    private static final long serialVersionUID = 1L;

    /** キャラクター */
    @NotNull
    private Integer charaId;

    /** 希望役職 */
    private String requestedSkill;

    /** 第二希望役職 */
    private String secondRequestedSkill;

    /** 入村発言 */
    @NotNull
    private String joinMessage;

    /** 入村人数(管理者用) */
    private Integer personNumber;

    /** 入村パスワード */
    private String joinPassword;

    /** 見学か */
    private Boolean isSpectator;

    public Integer getCharaId() {
        return charaId;
    }

    public void setCharaId(Integer charaId) {
        this.charaId = charaId;
    }

    public String getRequestedSkill() {
        return requestedSkill;
    }

    public void setRequestedSkill(String requestedSkill) {
        this.requestedSkill = requestedSkill;
    }

    public String getJoinMessage() {
        return joinMessage;
    }

    public void setJoinMessage(String joinMessage) {
        this.joinMessage = joinMessage;
    }

    public Integer getPersonNumber() {
        return personNumber;
    }

    public void setPersonNumber(Integer personNumber) {
        this.personNumber = personNumber;
    }

    public String getJoinPassword() {
        return joinPassword;
    }

    public void setJoinPassword(String joinPassword) {
        this.joinPassword = joinPassword;
    }

    public Boolean getIsSpectator() {
        return isSpectator;
    }

    public void setIsSpectator(Boolean isSpectator) {
        this.isSpectator = isSpectator;
    }

    public String getSecondRequestedSkill() {
        return secondRequestedSkill;
    }

    public void setSecondRequestedSkill(String secondRequestedSkill) {
        this.secondRequestedSkill = secondRequestedSkill;
    }
}
