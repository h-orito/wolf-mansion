package com.ort.app.web.model;

public class GroupCharaDto {

    private Integer charaId;

    private String charaName;

    private String charaImgUrl;

    private Integer charaImgWidth;

    private Integer charaImgHeight;

    private String joinMessage;

    private String firstDayMessage;

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

    public Integer getCharaId() {
        return charaId;
    }

    public void setCharaId(Integer charaId) {
        this.charaId = charaId;
    }

    public String getCharaName() {
        return charaName;
    }

    public void setCharaName(String charaName) {
        this.charaName = charaName;
    }

    public String getFirstDayMessage() {
        return firstDayMessage;
    }

    public void setFirstDayMessage(String firstDayMessage) {
        this.firstDayMessage = firstDayMessage;
    }
}
