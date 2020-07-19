package com.ort.app.web.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.ort.app.web.model.inner.VillageRecordDto;

public class VillageRecordListResultContent implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 村リスト */
    private List<VillageRecordDto> list = new ArrayList<>();

    public List<VillageRecordDto> getList() {
        return list;
    }

    public void setList(List<VillageRecordDto> list) {
        this.list = list;
    }
}
