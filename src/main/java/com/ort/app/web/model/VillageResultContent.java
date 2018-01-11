package com.ort.app.web.model;

import java.io.Serializable;
import java.util.List;

import com.ort.app.web.model.inner.VillageCharaDto;
import com.ort.app.web.model.inner.VillageMemberDto;
import com.ort.app.web.model.inner.VillageMessageDto;
import com.ort.app.web.model.inner.VillageRoomAssignedRowDto;
import com.ort.app.web.model.inner.VillageSkillDto;

public class VillageResultContent implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 村ID */
    private Integer villageId;

    /** 村名 */
    private String villageName;

    /** 何日目か */
    private Integer day;

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

}
