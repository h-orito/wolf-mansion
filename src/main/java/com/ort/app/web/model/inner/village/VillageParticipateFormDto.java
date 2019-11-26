package com.ort.app.web.model.inner.village;

import java.util.List;

import com.ort.app.web.model.inner.VillageCharaDto;
import com.ort.app.web.model.inner.VillageSkillDto;

public class VillageParticipateFormDto {

    /** 参戦フォームを表示するか */
    private Boolean isDispParticipateForm;

    /** 希望役職変更フォームを表示するか */
    private Boolean isDispChangeRequestSkillForm;

    /** 役職希望無効のメッセージを表示するか */
    private Boolean isDispChangeRequestNgMessage;

    /** 退村フォームを表示するか */
    private Boolean isDispLeaveVillageForm;

    /** 参戦フォームで選択するキャラクターリスト */
    private List<VillageCharaDto> selectableCharaList;

    /** 参戦フォームで選択する希望役職リスト */
    private List<VillageSkillDto> selectableSkillList;

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

    public Boolean getIsDispChangeRequestNgMessage() {
        return isDispChangeRequestNgMessage;
    }

    public void setIsDispChangeRequestNgMessage(Boolean isDispChangeRequestNgMessage) {
        this.isDispChangeRequestNgMessage = isDispChangeRequestNgMessage;
    }
}
