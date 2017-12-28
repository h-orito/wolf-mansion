package com.ort.app.web.form;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

public class VillageSayForm implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 発言 */
    @NotNull
    @Length(max = 200)
    private String message;

    /** 発言種別 */
    @NotNull
    private String messageType;

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
}
