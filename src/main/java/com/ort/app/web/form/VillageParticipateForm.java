package com.ort.app.web.form;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

public class VillageParticipateForm implements Serializable {

    private static final long serialVersionUID = 1L;

    /** キャラクター */
    @NotNull
    private Integer charaId;

    /** 希望役職 */
    private String requestedSkill;

    /** 入村発言 */
    @NotNull
    @Length(max = 200)
    private String joinMessage;

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
}
