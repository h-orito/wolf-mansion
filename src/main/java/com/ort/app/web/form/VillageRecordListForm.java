package com.ort.app.web.form;

import java.io.Serializable;
import java.util.List;

public class VillageRecordListForm implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<Integer> vid;

    public List<Integer> getVid() {
        return vid;
    }

    public void setVid(List<Integer> vid) {
        this.vid = vid;
    }
}
