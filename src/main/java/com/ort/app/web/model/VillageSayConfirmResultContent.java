package com.ort.app.web.model;

import java.io.Serializable;

import com.ort.app.web.model.inner.VillageMessageDto;

public class VillageSayConfirmResultContent implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 発言 */
    private VillageMessageDto message;

    /** ランダムキーワード(カンマ区切り) */
    private String randomKeywords;

    public String getRandomKeywords() {
        return randomKeywords;
    }

    public void setRandomKeywords(String randomKeywords) {
        this.randomKeywords = randomKeywords;
    }

    public VillageMessageDto getMessage() {
        return message;
    }

    public void setMessage(VillageMessageDto message) {
        this.message = message;
    }

}
