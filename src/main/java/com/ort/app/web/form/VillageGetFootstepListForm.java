package com.ort.app.web.form;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

public class VillageGetFootstepListForm implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 村ID */
    @NotNull
    private Integer villageId;

    /** 実行者キャラID(狼のみ) */
    private Integer charaId;

    /** 対象キャラID */
    private Integer targetCharaId;

    public Integer getVillageId() {
        return villageId;
    }

    public void setVillageId(Integer villageId) {
        this.villageId = villageId;
    }

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
}
