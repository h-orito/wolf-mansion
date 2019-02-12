package com.ort.app.web.form;

import java.io.Serializable;

public class VillageAbilityForm implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 行使キャラID(人狼のみ) */
    private Integer charaId;

    /** 対象キャラID(人狼、占い、狩人のみ) */
    private Integer targetCharaId;

    /** 足音(人狼、占い、妖狐、狂人、探偵のみ) */
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
