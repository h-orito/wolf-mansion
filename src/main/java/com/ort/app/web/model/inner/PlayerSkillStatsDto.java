package com.ort.app.web.model.inner;

public class PlayerSkillStatsDto extends PlayerStatsDto {

    /** 役職名 */
    private String skillName;

    public String getSkillName() {
        return skillName;
    }

    public void setSkillName(String skillName) {
        this.skillName = skillName;
    }
}
