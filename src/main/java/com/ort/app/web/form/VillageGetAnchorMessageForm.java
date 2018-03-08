package com.ort.app.web.form;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

public class VillageGetAnchorMessageForm implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 村ID */
    @NotNull
    private Integer villageId;

    /** 発言番号 */
    @NotNull
    private Integer messageNumber;

    /** 発言種別 */
    @NotNull
    private String messageType;

    public Integer getVillageId() {
        return villageId;
    }

    public void setVillageId(Integer villageId) {
        this.villageId = villageId;
    }

    public Integer getMessageNumber() {
        return messageNumber;
    }

    public void setMessageNumber(Integer messageNumber) {
        this.messageNumber = messageNumber;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

}
