package com.ort.app.web.model;

import java.io.Serializable;

public class VillageRecordLatestVidResultContent implements Serializable {

    private static final long serialVersionUID = 1L;

    public VillageRecordLatestVidResultContent(Integer vid) {
        this.vid = vid;
    }

    private Integer vid;

    public Integer getVid() {
        return vid;
    }

    public void setVid(Integer vid) {
        this.vid = vid;
    }
}
