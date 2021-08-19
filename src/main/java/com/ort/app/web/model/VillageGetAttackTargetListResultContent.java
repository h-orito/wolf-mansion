package com.ort.app.web.model;

import java.io.Serializable;
import java.util.List;

public class VillageGetAttackTargetListResultContent implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 対象リスト
     */
    private List<OptionDto> attackTargetList;

    public List<OptionDto> getAttackTargetList() {
        return attackTargetList;
    }

    public void setAttackTargetList(List<OptionDto> attackTargetList) {
        this.attackTargetList = attackTargetList;
    }
}
