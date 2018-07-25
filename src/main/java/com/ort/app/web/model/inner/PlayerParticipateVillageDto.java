package com.ort.app.web.model.inner;

public class PlayerParticipateVillageDto {

    /** 村ID */
    private Integer villageId;

    /** 村名 */
    private String villageName;

    /** キャラクター */
    private String characterName;

    /** キャラクター画像URL */
    private String characterImgUrl;

    /** キャラクター画像横幅 */
    private Integer characterImgWidth;

    /** キャラクター画像縦幅 */
    private Integer characterImgHeight;

    /** 役職名 */
    private String skillName;

    /** 生死 */
    private String liveStatus;

    /** 勝敗 */
    private String winStatus;

    public Integer getVillageId() {
        return villageId;
    }

    public void setVillageId(Integer villageId) {
        this.villageId = villageId;
    }

    public String getVillageName() {
        return villageName;
    }

    public void setVillageName(String villageName) {
        this.villageName = villageName;
    }

    public String getCharacterName() {
        return characterName;
    }

    public void setCharacterName(String characterName) {
        this.characterName = characterName;
    }

    public String getCharacterImgUrl() {
        return characterImgUrl;
    }

    public void setCharacterImgUrl(String characterImgUrl) {
        this.characterImgUrl = characterImgUrl;
    }

    public Integer getCharacterImgWidth() {
        return characterImgWidth;
    }

    public void setCharacterImgWidth(Integer characterImgWidth) {
        this.characterImgWidth = characterImgWidth;
    }

    public Integer getCharacterImgHeight() {
        return characterImgHeight;
    }

    public void setCharacterImgHeight(Integer characterImgHeight) {
        this.characterImgHeight = characterImgHeight;
    }

    public String getSkillName() {
        return skillName;
    }

    public void setSkillName(String skillName) {
        this.skillName = skillName;
    }

    public String getLiveStatus() {
        return liveStatus;
    }

    public void setLiveStatus(String liveStatus) {
        this.liveStatus = liveStatus;
    }

    public String getWinStatus() {
        return winStatus;
    }

    public void setWinStatus(String winStatus) {
        this.winStatus = winStatus;
    }

}
