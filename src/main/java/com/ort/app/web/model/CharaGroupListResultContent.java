package com.ort.app.web.model;

import java.io.Serializable;
import java.util.List;

import com.ort.app.web.model.inner.CharaGroupListCharaGroupDto;

public class CharaGroupListResultContent implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<CharaGroupListCharaGroupDto> charaGroupList;

    public List<CharaGroupListCharaGroupDto> getCharaGroupList() {
        return charaGroupList;
    }

    public void setCharaGroupList(List<CharaGroupListCharaGroupDto> charaGroupList) {
        this.charaGroupList = charaGroupList;
    }
}
