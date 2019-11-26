package com.ort.app.web.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import com.ort.app.web.model.inner.VillageMemberDto;
import com.ort.app.web.model.inner.VillageRoomAssignedRowDto;
import com.ort.app.web.model.inner.village.VillageFormDto;
import com.ort.app.web.model.inner.village.VillageParticipateDto;
import com.ort.app.web.model.inner.village.VillageSettingsDto;

public class VillageResultContent implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 村ID */
    private Integer villageId;

    /** 村表示用番号 */
    private String villageNumber;

    /** 村名 */
    private String villageName;

    /** 村設定 */
    private VillageSettingsDto settings;

    /** 何日目か */
    private Integer day;

    /** 日付リスト */
    private List<Integer> dayList;

    /** エピローグ日時 */
    private Integer epilogueDay;

    /** 参加者一覧 */
    private List<VillageMemberDto> memberList;

    /** キャラクター一覧 */
    private List<OptionDto> characterList;

    /** 参加者部屋割り当て */
    private List<VillageRoomAssignedRowDto> roomAssignedRowList;

    /** 部屋の横サイズ */
    private Integer roomWidth;

    /** 参加している場合の各種フォーム表示 */
    private VillageFormDto form;

    /** 参加している場合の自分自身の情報 */
    private VillageParticipateDto myself;

    /** 村建てしたプレーヤー名 */
    private String createPlayerName;

    /** 村設定変更可能か */
    private Boolean isAvailableSettingsUpdate;

    /** 投票一覧 */
    private VillageVoteDto vote;

    /** 足音一覧 */
    private List<VillageFootstepDto> villageFootstepList;

    /** 次回更新日時 */
    private LocalDateTime dayChangeDatetime;

    /** ネタバレ防止切り替えを表示するか */
    private Boolean isDispUnspoiler;

    /** ランダムキーワード（カンマ区切り） */
    private String randomKeywords;

    /** 全体状況リスト */
    private List<VillageSituationDto> situationList;

    /** ネタバレ表示（エピと墓下公開用） */
    private Boolean isDispSpoilerContent;

    /** 村建てした人か */
    private Boolean isCreatePlayer;

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

    public VillageSettingsDto getSettings() {
        return settings;
    }

    public void setSettings(VillageSettingsDto settings) {
        this.settings = settings;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public List<Integer> getDayList() {
        return dayList;
    }

    public void setDayList(List<Integer> dayList) {
        this.dayList = dayList;
    }

    public Integer getEpilogueDay() {
        return epilogueDay;
    }

    public void setEpilogueDay(Integer epilogueDay) {
        this.epilogueDay = epilogueDay;
    }

    public List<VillageMemberDto> getMemberList() {
        return memberList;
    }

    public void setMemberList(List<VillageMemberDto> memberList) {
        this.memberList = memberList;
    }

    public List<OptionDto> getCharacterList() {
        return characterList;
    }

    public void setCharacterList(List<OptionDto> characterList) {
        this.characterList = characterList;
    }

    public List<VillageRoomAssignedRowDto> getRoomAssignedRowList() {
        return roomAssignedRowList;
    }

    public void setRoomAssignedRowList(List<VillageRoomAssignedRowDto> roomAssignedRowList) {
        this.roomAssignedRowList = roomAssignedRowList;
    }

    public Integer getRoomWidth() {
        return roomWidth;
    }

    public void setRoomWidth(Integer roomWidth) {
        this.roomWidth = roomWidth;
    }

    public String getCreatePlayerName() {
        return createPlayerName;
    }

    public void setCreatePlayerName(String createPlayerName) {
        this.createPlayerName = createPlayerName;
    }

    public Boolean getIsAvailableSettingsUpdate() {
        return isAvailableSettingsUpdate;
    }

    public void setIsAvailableSettingsUpdate(Boolean isAvailableSettingsUpdate) {
        this.isAvailableSettingsUpdate = isAvailableSettingsUpdate;
    }

    public VillageVoteDto getVote() {
        return vote;
    }

    public void setVote(VillageVoteDto vote) {
        this.vote = vote;
    }

    public List<VillageFootstepDto> getVillageFootstepList() {
        return villageFootstepList;
    }

    public void setVillageFootstepList(List<VillageFootstepDto> villageFootstepList) {
        this.villageFootstepList = villageFootstepList;
    }

    public LocalDateTime getDayChangeDatetime() {
        return dayChangeDatetime;
    }

    public void setDayChangeDatetime(LocalDateTime dayChangeDatetime) {
        this.dayChangeDatetime = dayChangeDatetime;
    }

    public Boolean getIsDispUnspoiler() {
        return isDispUnspoiler;
    }

    public void setIsDispUnspoiler(Boolean isDispUnspoiler) {
        this.isDispUnspoiler = isDispUnspoiler;
    }

    public String getRandomKeywords() {
        return randomKeywords;
    }

    public void setRandomKeywords(String randomKeywords) {
        this.randomKeywords = randomKeywords;
    }

    public List<VillageSituationDto> getSituationList() {
        return situationList;
    }

    public void setSituationList(List<VillageSituationDto> situationList) {
        this.situationList = situationList;
    }

    public Boolean getIsDispSpoilerContent() {
        return isDispSpoilerContent;
    }

    public void setIsDispSpoilerContent(Boolean isDispSpoilerContent) {
        this.isDispSpoilerContent = isDispSpoilerContent;
    }

    public VillageFormDto getForm() {
        return form;
    }

    public void setForm(VillageFormDto form) {
        this.form = form;
    }

    public VillageParticipateDto getMyself() {
        return myself;
    }

    public void setMyself(VillageParticipateDto myself) {
        this.myself = myself;
    }

    public Boolean getIsCreatePlayer() {
        return isCreatePlayer;
    }

    public void setIsCreatePlayer(Boolean isCreatePlayer) {
        this.isCreatePlayer = isCreatePlayer;
    }
}
