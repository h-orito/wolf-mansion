package com.ort.app.web.model.inner;

public class VillageAbilityTargetDto {

    private Integer charaId;

    private String charaName;

    /** 足音（カンマ区切り） */
    private String footstep;

    public Integer getCharaId() {
        return charaId;
    }

    public void setCharaId(Integer charaId) {
        this.charaId = charaId;
    }

    public String getCharaName() {
        return charaName;
    }

    public void setCharaName(String charaName) {
        this.charaName = charaName;
    }

    public String getFootstep() {
        return footstep;
    }

    public void setFootstep(String footstep) {
        this.footstep = footstep;
    }
}
