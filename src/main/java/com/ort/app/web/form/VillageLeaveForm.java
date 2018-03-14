package com.ort.app.web.form;

import java.io.Serializable;

public class VillageLeaveForm implements Serializable {

    private static final long serialVersionUID = 1L;

    /** village_player_id */
    private Integer villagePlayerId;

    public Integer getVillagePlayerId() {
        return villagePlayerId;
    }

    public void setVillagePlayerId(Integer villagePlayerId) {
        this.villagePlayerId = villagePlayerId;
    }
}
