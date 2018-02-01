package com.ort.app.web.form;

import java.io.Serializable;

public class VillageAbilityForm implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 行使キャラID */
    private Integer charaId;

    /** 対象キャラID */
    private Integer targetCharaId;

    /** 足音 */
    private String footstep;

    public Integer getCharaId() {
        return charaId;
    }

    public void setCharaId(Integer charaId) {
        this.charaId = charaId;
    }

    public Integer getTargetCharaId() {
        return targetCharaId;
    }

    public void setTargetCharaId(Integer targetCharaId) {
        this.targetCharaId = targetCharaId;
    }

    public String getFootstep() {
        return footstep;
    }

    public void setFootstep(String footstep) {
        this.footstep = footstep;
    }
}
