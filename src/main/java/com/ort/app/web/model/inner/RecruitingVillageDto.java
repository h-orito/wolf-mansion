package com.ort.app.web.model.inner;

public class RecruitingVillageDto {

    /** 村ID */
    private Integer villageId;

    /** 村表示用番号 */
    private String villageNumber;

    /** 村名 */
    private String villageName;

    /** 人数 */
    private String participateNum;

    /** 見学人数 */
    private String spectateNum;

    /** 更新時間 */
    private String daychangeDatetime;

    /** 更新間隔 */
    private String daychangeInterval;

    /** キャラチップ */
    private String charaset;

    /** 発言制限 */
    private String restrict;

    /** 状態 */
    private String status;

    /** URL */
    private String url;

    public Integer getVillageId() {
        return villageId;
    }

    public void setVillageId(Integer villageId) {
        this.villageId = villageId;
    }

    public String getVillageNumber() {
        return villageNumber;
    }

    public void setVillageNumber(String villageNumber) {
        this.villageNumber = villageNumber;
    }

    public String getVillageName() {
        return villageName;
    }

    public void setVillageName(String villageName) {
        this.villageName = villageName;
    }

    public String getParticipateNum() {
        return participateNum;
    }

    public void setParticipateNum(String participateNum) {
        this.participateNum = participateNum;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSpectateNum() {
        return spectateNum;
    }

    public void setSpectateNum(String spectateNum) {
        this.spectateNum = spectateNum;
    }

    public String getDaychangeDatetime() {
        return daychangeDatetime;
    }

    public void setDaychangeDatetime(String daychangeDatetime) {
        this.daychangeDatetime = daychangeDatetime;
    }

    public String getDaychangeInterval() {
        return daychangeInterval;
    }

    public void setDaychangeInterval(String daychangeInterval) {
        this.daychangeInterval = daychangeInterval;
    }

    public String getCharaset() {
        return charaset;
    }

    public void setCharaset(String charaset) {
        this.charaset = charaset;
    }

    public String getRestrict() {
        return restrict;
    }

    public void setRestrict(String restrict) {
        this.restrict = restrict;
    }

}
