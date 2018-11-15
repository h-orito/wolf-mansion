package com.ort.app.web.model;

import java.io.Serializable;
import java.util.List;

import com.ort.app.web.model.inner.RecruitingVillageDto;

public class RecruitingResultContent implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 村リスト */
    private List<RecruitingVillageDto> villageList;

    public List<RecruitingVillageDto> getVillageList() {
        return villageList;
    }

    public void setVillageList(List<RecruitingVillageDto> villageList) {
        this.villageList = villageList;
    }
}
