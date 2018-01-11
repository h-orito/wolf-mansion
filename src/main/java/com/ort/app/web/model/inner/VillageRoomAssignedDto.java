package com.ort.app.web.model.inner;

public class VillageRoomAssignedDto {

    /** 部屋番号 0埋めの2桁 */
    private String roomNumber;

    /** キャラ名略称 */
    private String charaName;

    /** キャラ画像 */
    private String charaImgUrl;

    /** 死亡しているか */
    private Boolean isDead;

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
}
