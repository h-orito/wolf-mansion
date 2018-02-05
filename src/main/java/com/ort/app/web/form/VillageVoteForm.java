package com.ort.app.web.form;

import java.io.Serializable;

public class VillageVoteForm implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 対象キャラID */
    private Integer targetCharaId;

    public Integer getTargetCharaId() {
        return targetCharaId;
    }

    public void setTargetCharaId(Integer targetCharaId) {
        this.targetCharaId = targetCharaId;
    }
}
