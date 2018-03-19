package com.ort.app.web.model;

import java.io.Serializable;
import java.util.List;

import com.ort.app.web.model.inner.VillageCharaDto;
import com.ort.app.web.model.inner.VillageMemberDto;
import com.ort.app.web.model.inner.VillageMessageDto;
import com.ort.app.web.model.inner.VillageRoomAssignedRowDto;
import com.ort.app.web.model.inner.VillageSettingsDto;
import com.ort.app.web.model.inner.VillageSkillDto;
import com.ort.dbflute.exentity.Chara;

public class VillageResultContent implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 村ID */
    private Integer villageId;

    /** 村名 */
    private String villageName;

    /** 村設定 */
    private VillageSettingsDto villageSettings;

    /** 何日目か */
    private Integer day;

    /** 役職希望有効か */
    private Boolean isSkillRequestAvailable;

    /** 日付リスト */
    private List<Integer> dayList;

    /** エピローグ日時 */
    private Integer epilogueDay;

    /** 参加者一覧 */
    private List<VillageMemberDto> memberList;

    /** 参加者部屋割り当て */
    private List<VillageRoomAssignedRowDto> roomAssignedRowList;

    /** 部屋の横サイズ */
    private Integer roomWidth;

    /** メッセージリスト */
    private List<VillageMessageDto> messageList;

    /** 参戦フォームを表示するか */
    private Boolean isDispParticipateForm;

    /** 希望役職変更フォームを表示するか */
    private Boolean isDispChangeRequestSkillForm;

    /** 退村フォームを表示するか */
    private Boolean isDispLeaveVillageForm;

    /** 参戦フォームで選択するキャラクターリスト */
    private List<VillageCharaDto> selectableCharaList;

    /** 参戦フォームで選択する希望役職リスト */
    private List<VillageSkillDto> selectableSkillList;

    /** 発言フォームを表示するか */
    private Boolean isDispSayForm;

    /** 通常発言可能か */
    private Boolean isAvailableNormalSay;

    /** 囁き発言可能か */
    private Boolean isAvailableWerewolfSay;

    /** 共有発言可能か */
    private Boolean isAvailableMasonSay;

    /** 墓下発言可能か */
    private Boolean isAvailableGraveSay;

    /** 独り言可能か */
    private Boolean isAvailableMonologueSay;

    /** 参戦しているキャラの画像 */
    private String charaImageUrl;

    /** 能力行使対象リスト */
    private List<Chara> abilityTargetList;

    /** 襲撃担当者リスト */
    private List<Chara> attackerList;

    /** 投票対象リスト */
    private List<Chara> voteTargetList;

    /** 役職 */
    private String skillName;

    /** 死んでいるか */
    private Boolean isDead;

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

    public VillageSettingsDto getVillageSettings() {
        return villageSettings;
    }

    public void setVillageSettings(VillageSettingsDto villageSettings) {
        this.villageSettings = villageSettings;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public Boolean getIsSkillRequestAvailable() {
        return isSkillRequestAvailable;
    }

    public void setIsSkillRequestAvailable(Boolean isSkillRequestAvailable) {
        this.isSkillRequestAvailable = isSkillRequestAvailable;
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

    public List<VillageMessageDto> getMessageList() {
        return messageList;
    }

    public void setMessageList(List<VillageMessageDto> messageList) {
        this.messageList = messageList;
    }

    public Boolean getIsDispParticipateForm() {
        return isDispParticipateForm;
    }

    public void setIsDispParticipateForm(Boolean isDispParticipateForm) {
        this.isDispParticipateForm = isDispParticipateForm;
    }

    public Boolean getIsDispChangeRequestSkillForm() {
        return isDispChangeRequestSkillForm;
    }

    public void setIsDispChangeRequestSkillForm(Boolean isDispChangeRequestSkillForm) {
        this.isDispChangeRequestSkillForm = isDispChangeRequestSkillForm;
    }

    public Boolean getIsDispLeaveVillageForm() {
        return isDispLeaveVillageForm;
    }

    public void setIsDispLeaveVillageForm(Boolean isDispLeaveVillageForm) {
        this.isDispLeaveVillageForm = isDispLeaveVillageForm;
    }

    public List<VillageCharaDto> getSelectableCharaList() {
        return selectableCharaList;
    }

    public void setSelectableCharaList(List<VillageCharaDto> selectableCharaList) {
        this.selectableCharaList = selectableCharaList;
    }

    public List<VillageSkillDto> getSelectableSkillList() {
        return selectableSkillList;
    }

    public void setSelectableSkillList(List<VillageSkillDto> selectableSkillList) {
        this.selectableSkillList = selectableSkillList;
    }

    public Boolean getIsDispSayForm() {
        return isDispSayForm;
    }

    public void setIsDispSayForm(Boolean isDispSayForm) {
        this.isDispSayForm = isDispSayForm;
    }

    public Boolean getIsAvailableNormalSay() {
        return isAvailableNormalSay;
    }

    public void setIsAvailableNormalSay(Boolean isAvailableNormalSay) {
        this.isAvailableNormalSay = isAvailableNormalSay;
    }

    public Boolean getIsAvailableWerewolfSay() {
        return isAvailableWerewolfSay;
    }

    public void setIsAvailableWerewolfSay(Boolean isAvailableWerewolfSay) {
        this.isAvailableWerewolfSay = isAvailableWerewolfSay;
    }

    public Boolean getIsAvailableMasonSay() {
        return isAvailableMasonSay;
    }

    public void setIsAvailableMasonSay(Boolean isAvailableMasonSay) {
        this.isAvailableMasonSay = isAvailableMasonSay;
    }

    public Boolean getIsAvailableGraveSay() {
        return isAvailableGraveSay;
    }

    public void setIsAvailableGraveSay(Boolean isAvailableGraveSay) {
        this.isAvailableGraveSay = isAvailableGraveSay;
    }

    public Boolean getIsAvailableMonologueSay() {
        return isAvailableMonologueSay;
    }

    public void setIsAvailableMonologueSay(Boolean isAvailableMonologueSay) {
        this.isAvailableMonologueSay = isAvailableMonologueSay;
    }

    public String getCharaImageUrl() {
        return charaImageUrl;
    }

    public void setCharaImageUrl(String charaImageUrl) {
        this.charaImageUrl = charaImageUrl;
    }

    public List<Chara> getAbilityTargetList() {
        return abilityTargetList;
    }

    public void setAbilityTargetList(List<Chara> abilityTargetList) {
        this.abilityTargetList = abilityTargetList;
    }

    public List<Chara> getAttackerList() {
        return attackerList;
    }

    public void setAttackerList(List<Chara> attackerList) {
        this.attackerList = attackerList;
    }

    public List<Chara> getVoteTargetList() {
        return voteTargetList;
    }

    public void setVoteTargetList(List<Chara> voteTargetList) {
        this.voteTargetList = voteTargetList;
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

}
