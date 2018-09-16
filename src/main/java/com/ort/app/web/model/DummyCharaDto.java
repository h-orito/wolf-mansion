package com.ort.app.web.model;

public class DummyCharaDto {

    private String charaImgUrl;

    private Integer charaImgWidth;

    private Integer charaImgHeight;

    private String joinMessage;

    public String getCharaImgUrl() {
        return charaImgUrl;
    }

    public void setCharaImgUrl(String charaImgUrl) {
        this.charaImgUrl = charaImgUrl;
    }

    public String getJoinMessage() {
        return joinMessage;
    }

    public void setJoinMessage(String joinMessage) {
        this.joinMessage = joinMessage;
    }

    public Integer getCharaImgWidth() {
        return charaImgWidth;
    }

    public void setCharaImgWidth(Integer charaImgWidth) {
        this.charaImgWidth = charaImgWidth;
    }

    public Integer getCharaImgHeight() {
        return charaImgHeight;
    }

    public void setCharaImgHeight(Integer charaImgHeight) {
        this.charaImgHeight = charaImgHeight;
    }
}
