package com.ort.app.web.form;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

public class VillageSayForm implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 発言 */
    @NotNull
    private String message;

    /** 発言種別 */
    @NotNull
    private String messageType;

    /** 秘話相手 */
    private Integer secretSayTargetCharaId;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public Integer getSecretSayTargetCharaId() {
        return secretSayTargetCharaId;
    }

    public void setSecretSayTargetCharaId(Integer secretSayTargetCharaId) {
        this.secretSayTargetCharaId = secretSayTargetCharaId;
    }
}
