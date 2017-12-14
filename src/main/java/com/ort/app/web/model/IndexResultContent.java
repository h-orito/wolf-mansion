package com.ort.app.web.model;

import java.util.List;

import com.ort.app.web.model.inner.IndexVillageDto;

public class IndexResultContent {

    /** 村リスト */
    private List<IndexVillageDto> villageList;

    public List<IndexVillageDto> getVillageList() {
        return villageList;
    }

    public void setVillageList(List<IndexVillageDto> villageList) {
        this.villageList = villageList;
    }

}
