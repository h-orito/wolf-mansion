package com.ort.app.web.model.inner;

public class NewVillageDivertVillageDto {

    public NewVillageDivertVillageDto(Integer id, String name) {
        this.villageId = id;
        this.villageNo = String.format("%04d", id);
        this.villageName = name;
    }

    private Integer villageId;
    private String villageNo;
    private String villageName;

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

    public String getVillageNo() {
        return villageNo;
    }

    public void setVillageNo(String villageNo) {
        this.villageNo = villageNo;
    }
}
