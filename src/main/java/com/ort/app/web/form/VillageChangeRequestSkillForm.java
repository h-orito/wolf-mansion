package com.ort.app.web.form;

import java.io.Serializable;

public class VillageChangeRequestSkillForm implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 希望役職 */
    private String requestedSkill;

    public String getRequestedSkill() {
        return requestedSkill;
    }

    public void setRequestedSkill(String requestedSkill) {
        this.requestedSkill = requestedSkill;
    }
}
