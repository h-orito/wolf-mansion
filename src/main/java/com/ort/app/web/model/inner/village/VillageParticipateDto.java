package com.ort.app.web.model.inner.village;

public class VillageParticipateDto {

    /** 参戦しているキャラの画像 */
    private String charaImageUrl;

    /** 参戦しているキャラの画像の横幅 */
    private Integer charaImageWidth;

    /** 参戦しているキャラの画像の縦幅 */
    private Integer charaImageHeight;

    /** 役職 */
    private String skillName;

    /** 死んでいるか */
    private Boolean isDead;

    public String getCharaImageUrl() {
        return charaImageUrl;
    }

    public void setCharaImageUrl(String charaImageUrl) {
        this.charaImageUrl = charaImageUrl;
    }

    public String getSkillName() {
        return skillName;
    }

    public void setSkillName(String skillName) {
        this.skillName = skillName;
    }

    public Boolean getIsDead() {
        return isDead;
    }

    public void setIsDead(Boolean isDead) {
        this.isDead = isDead;
    }

    public Integer getCharaImageWidth() {
        return charaImageWidth;
    }

    public void setCharaImageWidth(Integer charaImageWidth) {
        this.charaImageWidth = charaImageWidth;
    }

    public Integer getCharaImageHeight() {
        return charaImageHeight;
    }

    public void setCharaImageHeight(Integer charaImageHeight) {
        this.charaImageHeight = charaImageHeight;
    }
}
