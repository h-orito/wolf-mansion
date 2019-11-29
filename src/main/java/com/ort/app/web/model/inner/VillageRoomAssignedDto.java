package com.ort.app.web.model.inner;

public class VillageRoomAssignedDto {

    /** 部屋番号 0埋めの2桁 */
    private String roomNumber;

    /** キャラ名 */
    private String charaName;

    /** キャラ名略称 */
    private String charaShortName;

    /** キャラ画像 */
    private String charaImgUrl;

    /** 死亡しているか */
    private Boolean isDead;

    /** 死亡日時 */
    private Integer deadDay;

    /** 死亡理由 */
    private String deadReason;

    /** 役職名 */
    private String skillName;

    /** キャラ画像横幅 */
    private Integer charaImgWidth;

    /** キャラ画像縦幅 */
    private Integer charaImgHeight;

    /** ダミーか */
    private Boolean isDummy;

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getCharaName() {
        return charaName;
    }

    public void setCharaName(String charaName) {
        this.charaName = charaName;
    }

    public String getCharaImgUrl() {
        return charaImgUrl;
    }

    public void setCharaImgUrl(String charaImgUrl) {
        this.charaImgUrl = charaImgUrl;
    }

    public Boolean getIsDead() {
        return isDead;
    }

    public void setIsDead(Boolean isDead) {
        this.isDead = isDead;
    }

    public String getSkillName() {
        return skillName;
    }

    public void setSkillName(String skillName) {
        this.skillName = skillName;
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

    public Integer getDeadDay() {
        return deadDay;
    }

    public void setDeadDay(Integer deadDay) {
        this.deadDay = deadDay;
    }

    public String getDeadReason() {
        return deadReason;
    }

    public void setDeadReason(String deadReson) {
        this.deadReason = deadReson;
    }

    public Boolean getIsDummy() {
        return isDummy;
    }

    public void setIsDummy(Boolean isDummy) {
        this.isDummy = isDummy;
    }

    public String getCharaShortName() {
        return charaShortName;
    }

    public void setCharaShortName(String charaShortName) {
        this.charaShortName = charaShortName;
    }
}
