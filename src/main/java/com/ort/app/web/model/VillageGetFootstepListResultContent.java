package com.ort.app.web.model;

import java.io.Serializable;
import java.util.List;

public class VillageGetFootstepListResultContent implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 足音リスト */
    private List<String> footstepList;

    public List<String> getFootstepList() {
        return footstepList;
    }

    public void setFootstepList(List<String> footstepList) {
        this.footstepList = footstepList;
    }
}
