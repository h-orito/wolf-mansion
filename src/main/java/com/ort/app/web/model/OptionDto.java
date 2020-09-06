package com.ort.app.web.model;

import com.ort.dbflute.exentity.VillagePlayer;

public class OptionDto {
    private String name;

    private String value;

    public OptionDto() {
    }

    public OptionDto(VillagePlayer vp) {
        this.name = vp.name();
        this.value = vp.getChara().get().getCharaId().toString();
    }

    public OptionDto(String name, String value) {
        this.name = name;
        this.value = value;
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
