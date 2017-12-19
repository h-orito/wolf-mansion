package com.ort.app.web.model.inner;

public class IndexVillageDto {

    /** 村ID */
    private Integer villageId;

    /** 村名 */
    private String villageName;

    /** 状態 */
    private String status;

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
