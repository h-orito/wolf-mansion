package com.ort.app.web.model;

import java.io.Serializable;

import com.ort.app.web.model.inner.VillageSettingsDto;

public class VillageSettingsResultContent implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 村ID */
    private Integer villageId;

    /** 村名 */
    private String villageName;

    /** 村設定 */
    private VillageSettingsDto villageSettings;

    public Integer getVillageId() {
        return villageId;
    }

    public void setVillageId(Integer villageId) {
        this.villageId = villageId;
    }

    public String getVillageName() {
        return villageName;
    }

    public void setVillageName(String villageName) {
        this.villageName = villageName;
    }

    public VillageSettingsDto getVillageSettings() {
        return villageSettings;
    }

    public void setVillageSettings(VillageSettingsDto villageSettings) {
        this.villageSettings = villageSettings;
    }
}
