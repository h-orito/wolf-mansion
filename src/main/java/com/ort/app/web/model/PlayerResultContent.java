package com.ort.app.web.model;

import java.io.Serializable;
import java.util.List;

import com.ort.app.web.model.inner.PlayerCampStatsDto;
import com.ort.app.web.model.inner.PlayerParticipateVillageDto;
import com.ort.app.web.model.inner.PlayerSkillStatsDto;
import com.ort.app.web.model.inner.PlayerStatsDto;

public class PlayerResultContent implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 総合戦績 */
    private PlayerStatsDto wholeStats;

    /** 陣営戦績 */
    private List<PlayerCampStatsDto> campStatsList;

    /** 役職戦績 */
    private List<PlayerSkillStatsDto> skillStatsList;

    /** 参加した村 */
    private List<PlayerParticipateVillageDto> participateVillageList;

    /** 見学した村 */
    private List<PlayerParticipateVillageDto> spectateVillageList;

    public PlayerStatsDto getWholeStats() {
        return wholeStats;
    }

    public void setWholeStats(PlayerStatsDto wholeStats) {
        this.wholeStats = wholeStats;
    }

    public List<PlayerCampStatsDto> getCampStatsList() {
        return campStatsList;
    }

    public void setCampStatsList(List<PlayerCampStatsDto> campStatsList) {
        this.campStatsList = campStatsList;
    }

    public List<PlayerSkillStatsDto> getSkillStatsList() {
        return skillStatsList;
    }

    public void setSkillStatsList(List<PlayerSkillStatsDto> skillStatsList) {
        this.skillStatsList = skillStatsList;
    }

    public List<PlayerParticipateVillageDto> getParticipateVillageList() {
        return participateVillageList;
    }

    public void setParticipateVillageList(List<PlayerParticipateVillageDto> participateVillageList) {
        this.participateVillageList = participateVillageList;
    }

    public List<PlayerParticipateVillageDto> getSpectateVillageList() {
        return spectateVillageList;
    }

    public void setSpectateVillageList(List<PlayerParticipateVillageDto> spectateVillageList) {
        this.spectateVillageList = spectateVillageList;
    }

}
