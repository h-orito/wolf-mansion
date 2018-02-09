package com.ort.app.web.model;

import java.io.Serializable;
import java.util.List;

import com.ort.app.web.model.inner.VillageListVillageDto;

public class VillageListResultContent implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<VillageListVillageDto> villageList;

    public List<VillageListVillageDto> getVillageList() {
        return villageList;
    }

    public void setVillageList(List<VillageListVillageDto> villageList) {
        this.villageList = villageList;
    }

}
