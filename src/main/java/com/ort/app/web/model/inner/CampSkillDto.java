package com.ort.app.web.model.inner;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CampSkillDto {

    @JsonProperty("camp_name")
    private String campName;

    @JsonProperty("skill_name_list")
    private List<String> skillList;

    public String getCampName() {
        return campName;
    }

    public void setCampName(String campName) {
        this.campName = campName;
    }

    public List<String> getSkillList() {
        return skillList;
    }

    public void setSkillList(List<String> skillList) {
        this.skillList = skillList;
    }
}
