package com.ort.app.web.model;

import java.io.Serializable;
import java.util.List;

import com.ort.app.web.model.inner.IndexVillageDto;

public class IndexResultContent implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 村リスト */
    private List<IndexVillageDto> villageList;

    /** どこかの村に参加中か */
    private Boolean isParticipate;

    public List<IndexVillageDto> getVillageList() {
        return villageList;
    }

    public void setVillageList(List<IndexVillageDto> villageList) {
        this.villageList = villageList;
    }

    public Boolean getIsParticipate() {
        return isParticipate;
    }

    public void setIsParticipate(Boolean isParticipate) {
        this.isParticipate = isParticipate;
    }

}
