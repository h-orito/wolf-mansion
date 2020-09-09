package com.ort.app.web.model.inner;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class VillageRecordDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 村ID */
    private Integer id;

    /** 村名 */
    private String name;

    /** ステータス */
    private String status;

    /** 編成 */
    private String organization;

    /** 更新間隔（秒） */
    @JsonProperty("interval_seconds")
    private Integer intervalSeconds;

    /** 開始日時 */
    @JsonProperty("start_datetime")
    private String startDatetime;

    /** プロローグ開始日時 */
    @JsonProperty("prologue_datetime")
    private String prologueDatetime;

    /** エピローグ日時 */
    @JsonProperty("epilogue_datetime")
    private String epilogueDatetime;

    /** エピローグが何日目か */
    @JsonProperty("epilogue_day")
    private Integer epilogueDay;

    /** URL */
    private String url;

    /** 勝利陣営名 */
    @JsonProperty("win_camp_name")
    private String winCampName;

    /** 参加者リスト */
    @JsonProperty("participant_list")
    private List<VillageParticipantRecordDto> participantList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getStartDatetime() {
        return startDatetime;
    }

    public void setStartDatetime(String startDatetime) {
        this.startDatetime = startDatetime;
    }

    public String getEpilogueDatetime() {
        return epilogueDatetime;
    }

    public void setEpilogueDatetime(String epilogueDatetime) {
        this.epilogueDatetime = epilogueDatetime;
    }

    public Integer getEpilogueDay() {
        return epilogueDay;
    }

    public void setEpilogueDay(Integer epilogueDay) {
        this.epilogueDay = epilogueDay;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getWinCampName() {
        return winCampName;
    }

    public void setWinCampName(String winCampName) {
        this.winCampName = winCampName;
    }

    public List<VillageParticipantRecordDto> getParticipantList() {
        return participantList;
    }

    public void setParticipantList(List<VillageParticipantRecordDto> participantList) {
        this.participantList = participantList;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPrologueDatetime() {
        return prologueDatetime;
    }

    public void setPrologueDatetime(String prologueDatetime) {
        this.prologueDatetime = prologueDatetime;
    }

    public Integer getIntervalSeconds() {
        return intervalSeconds;
    }

    public void setIntervalSeconds(Integer intervalSeconds) {
        this.intervalSeconds = intervalSeconds;
    }
}
