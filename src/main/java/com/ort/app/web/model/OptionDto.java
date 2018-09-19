package com.ort.app.web.model;

import com.ort.app.web.util.CharaUtil;
import com.ort.dbflute.exentity.VillagePlayer;

public class OptionDto {
    private String name;

    private String value;

    public OptionDto() {
    }

    public OptionDto(VillagePlayer vp) {
        this.name = CharaUtil.makeCharaName(vp);
        this.value = vp.getChara().get().getCharaId().toString();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
