package com.ort.app.web.form;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class VillageGetAttackTargetListForm implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 村ID
     */
    @NotNull
    private Integer villageId;

    /**
     * 実行者キャラID
     */
    private Integer charaId;

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
}
