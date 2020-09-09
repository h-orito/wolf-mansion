package com.ort.app.web.model;

import java.util.List;

import com.ort.app.web.model.inner.CampSkillDto;

public class SkillListResultContent {

    private List<CampSkillDto> list;

    public List<CampSkillDto> getList() {
        return list;
    }

    public void setList(List<CampSkillDto> list) {
        this.list = list;
    }
}
