package com.ort.app.web.model.inner.village;

import java.util.List;

import com.ort.app.web.model.OptionDto;
import com.ort.app.web.model.inner.SayRestrictDto;
import com.ort.app.web.model.inner.VillageCharaDto;
import com.ort.app.web.model.inner.VillageSkillDto;
import com.ort.dbflute.exentity.Chara;

public class VillageFormDto {

    /** 参戦フォームを表示するか */
    private Boolean isDispParticipateForm;

    /** 希望役職変更フォームを表示するか */
    private Boolean isDispChangeRequestSkillForm;

    /** 役職希望無効のメッセージを表示するか */
    private Boolean isDispChangeRequestNgMessage;

    /** 退村フォームを表示するか */
    private Boolean isDispLeaveVillageForm;

    /** コミットフォームを表示するか */
    private Boolean isDispCommitForm;

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

    /** 見学発言可能か */
    private Boolean isAvailableSpectateSay;

    /** 独り言可能か */
    private Boolean isAvailableMonologueSay;

    /** 秘話可能か */
    private Boolean isAvailableSecretSay;

    /** 発言制限 */
    private SayRestrictDto restrict;

    /** 選択可能な表情区分 */
    private List<OptionDto> faceTypeList;

    /** 能力行使対象リスト */
    private List<OptionDto> abilityTargetList;

    /** 襲撃担当者リスト */
    private List<OptionDto> attackerList;

    /** 能力行使履歴 */
    private List<String> skillHistoryList;

    /** 投票対象リスト */
    private List<OptionDto> voteTargetList;

    /** 秘話相手 */
    private List<Chara> secretSayTargetList;

    /** 人狼のキャラ名（狂信者向け） */
    private String werewolfCharaNameList;

    /** C国狂人のキャラ名（人狼向け） */
    private String cMadmanCharaNameList;

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

    public Boolean getIsAvailableSpectateSay() {
        return isAvailableSpectateSay;
    }

    public void setIsAvailableSpectateSay(Boolean isAvailableSpectateSay) {
        this.isAvailableSpectateSay = isAvailableSpectateSay;
    }

    public Boolean getIsAvailableMonologueSay() {
        return isAvailableMonologueSay;
    }

    public void setIsAvailableMonologueSay(Boolean isAvailableMonologueSay) {
        this.isAvailableMonologueSay = isAvailableMonologueSay;
    }

    public Boolean getIsDispCommitForm() {
        return isDispCommitForm;
    }

    public void setIsDispCommitForm(Boolean isDispCommitForm) {
        this.isDispCommitForm = isDispCommitForm;
    }

    public Boolean getIsDispChangeRequestNgMessage() {
        return isDispChangeRequestNgMessage;
    }

    public void setIsDispChangeRequestNgMessage(Boolean isDispChangeRequestNgMessage) {
        this.isDispChangeRequestNgMessage = isDispChangeRequestNgMessage;
    }

    public Boolean getIsAvailableSecretSay() {
        return isAvailableSecretSay;
    }

    public void setIsAvailableSecretSay(Boolean isAvailableSecretSay) {
        this.isAvailableSecretSay = isAvailableSecretSay;
    }

    public SayRestrictDto getRestrict() {
        return restrict;
    }

    public void setRestrict(SayRestrictDto restrict) {
        this.restrict = restrict;
    }

    public List<OptionDto> getFaceTypeList() {
        return faceTypeList;
    }

    public void setFaceTypeList(List<OptionDto> faceTypeList) {
        this.faceTypeList = faceTypeList;
    }

    public List<OptionDto> getAbilityTargetList() {
        return abilityTargetList;
    }

    public void setAbilityTargetList(List<OptionDto> abilityTargetList) {
        this.abilityTargetList = abilityTargetList;
    }

    public List<OptionDto> getAttackerList() {
        return attackerList;
    }

    public void setAttackerList(List<OptionDto> attackerList) {
        this.attackerList = attackerList;
    }

    public List<OptionDto> getVoteTargetList() {
        return voteTargetList;
    }

    public void setVoteTargetList(List<OptionDto> voteTargetList) {
        this.voteTargetList = voteTargetList;
    }

    public List<String> getSkillHistoryList() {
        return skillHistoryList;
    }

    public void setSkillHistoryList(List<String> skillHistoryList) {
        this.skillHistoryList = skillHistoryList;
    }

    public List<Chara> getSecretSayTargetList() {
        return secretSayTargetList;
    }

    public void setSecretSayTargetList(List<Chara> secretSayTargetList) {
        this.secretSayTargetList = secretSayTargetList;
    }

    public String getWerewolfCharaNameList() {
        return werewolfCharaNameList;
    }

    public void setWerewolfCharaNameList(String werewolfCharaNameList) {
        this.werewolfCharaNameList = werewolfCharaNameList;
    }

    public String getcMadmanCharaNameList() {
        return cMadmanCharaNameList;
    }

    public void setcMadmanCharaNameList(String cMadmanCharaNameList) {
        this.cMadmanCharaNameList = cMadmanCharaNameList;
    }
}
