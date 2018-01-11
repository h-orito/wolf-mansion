package com.ort.app.web.form;

import java.io.Serializable;

public class VillageAbilityForm implements Serializable {

    private static final long serialVersionUID = 1L;

    /** キャラID */
    private Integer charaId;

    public Integer getCharaId() {
        return charaId;
    }

    public void setCharaId(Integer charaId) {
        this.charaId = charaId;
    }

}
