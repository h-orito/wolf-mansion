package com.ort.app.web.model;

import java.io.Serializable;
import java.util.List;

import com.ort.app.web.model.inner.VillageMessageDto;

public class VillageResultContent implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 村名 */
    private String villageName;

    /** メッセージリスト */
    private List<VillageMessageDto> messageList;

    public String getVillageName() {
        return villageName;
    }

    public void setVillageName(String villageName) {
        this.villageName = villageName;
    }

    public List<VillageMessageDto> getMessageList() {
        return messageList;
    }

    public void setMessageList(List<VillageMessageDto> messageList) {
        this.messageList = messageList;
    }

}
