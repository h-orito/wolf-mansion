package com.ort.app.web.model;

import java.io.Serializable;
import java.util.List;

import com.ort.app.web.model.inner.VillageMessageDto;

public class VillageMessageListResultContent implements Serializable {

    private static final long serialVersionUID = 1L;

    /** メッセージリスト */
    private List<VillageMessageDto> messageList;

    /** 村状態メッセージ */
    private String villageStatusMessage;

    /** 突然死候補メッセージ */
    private String suddonlyDeathMessage;

    /** 最新日付 */
    private Integer latestDay;

    public List<VillageMessageDto> getMessageList() {
        return messageList;
    }

    public void setMessageList(List<VillageMessageDto> messageList) {
        this.messageList = messageList;
    }

    public String getVillageStatusMessage() {
        return villageStatusMessage;
    }

    public void setVillageStatusMessage(String villageStatusMessage) {
        this.villageStatusMessage = villageStatusMessage;
    }

    public String getSuddonlyDeathMessage() {
        return suddonlyDeathMessage;
    }

    public void setSuddonlyDeathMessage(String suddonlyDeathMessage) {
        this.suddonlyDeathMessage = suddonlyDeathMessage;
    }

    public Integer getLatestDay() {
        return latestDay;
    }

    public void setLatestDay(Integer latestDay) {
        this.latestDay = latestDay;
    }
}
