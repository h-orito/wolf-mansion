package com.ort.app.web.model;

import java.io.Serializable;

public class VillageLatestMessageDatetimeResultContent implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 最新発言の文字列型日時uuuuMMddHHMiss */
    private String latestMessageDatetime;

    public String getLatestMessageDatetime() {
        return latestMessageDatetime;
    }

    public void setLatestMessageDatetime(String latestMessageDatetime) {
        this.latestMessageDatetime = latestMessageDatetime;
    }
}
