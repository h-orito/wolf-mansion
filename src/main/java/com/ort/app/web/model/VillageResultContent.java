package com.ort.app.web.model;

import java.io.Serializable;
import java.util.List;

import com.ort.app.web.model.inner.VillageCharaDto;
import com.ort.app.web.model.inner.VillageMemberDto;
import com.ort.app.web.model.inner.VillageMessageDto;
import com.ort.app.web.model.inner.VillageSkillDto;

public class VillageResultContent implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 村ID */
    private Integer villageId;

    /** 村名 */
    private String villageName;

    /** 参加者一覧 */
    private List<VillageMemberDto> memberList;

    /** メッセージリスト */
    private List<VillageMessageDto> messageList;

    /** 参戦フォームを表示するか */
    private boolean isDispParticipateForm;

    /** 参戦フォームで選択するキャラクターリスト */
    private List<VillageCharaDto> selectableCharaList;

    /** 参戦フォームで選択する希望役職リスト */
    private List<VillageSkillDto> selectableSkillList;

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

    public List<VillageMemberDto> getMemberList() {
        return memberList;
    }

    public void setMemberList(List<VillageMemberDto> memberList) {
        this.memberList = memberList;
    }

    public List<VillageMessageDto> getMessageList() {
        return messageList;
    }

    public void setMessageList(List<VillageMessageDto> messageList) {
        this.messageList = messageList;
    }

    public boolean getIsDispParticipateForm() {
        return isDispParticipateForm;
    }

    public void setIsDispParticipateForm(boolean isDispParticipateForm) {
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
}
