package com.ort.app.web.model.inner.village;

import java.util.List;

import com.ort.app.web.model.OptionDto;

public class VillageAbilityFormDto {

    /** 能力行使対象リスト */
    private List<OptionDto> abilityTargetList;

    /** 襲撃担当者リスト */
    private List<OptionDto> attackerList;

    /** 能力行使履歴 */
    private List<String> skillHistoryList;

    /** 人狼のキャラ名（狂信者向け） */
    private String werewolfCharaNameList;

    /** C国狂人のキャラ名（人狼向け） */
    private String cMadmanCharaNameList;

    /** 妖狐のキャラ名（背徳者向け） */
    private String foxCharaNameList;

    /** 恋人陣営のキャラ名（恋人陣営向け） */
    private String loversCharaNameList;

    /** 対象選択の前に表示する文言 */
    private String targetPrefixMessage;

    /** 対象選択の後に表示する文言 */
    private String targetSuffixMessage;

    /** 対象選択して足音を残す役職か */
    private Boolean isTargetingAndFootstep;

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

    public List<String> getSkillHistoryList() {
        return skillHistoryList;
    }

    public void setSkillHistoryList(List<String> skillHistoryList) {
        this.skillHistoryList = skillHistoryList;
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

    public String getTargetPrefixMessage() {
        return targetPrefixMessage;
    }

    public void setTargetPrefixMessage(String targetPrefixMessage) {
        this.targetPrefixMessage = targetPrefixMessage;
    }

    public String getTargetSuffixMessage() {
        return targetSuffixMessage;
    }

    public void setTargetSuffixMessage(String targetSuffixMessage) {
        this.targetSuffixMessage = targetSuffixMessage;
    }

    public Boolean getIsTargetingAndFootstep() {
        return isTargetingAndFootstep;
    }

    public void setIsTargetingAndFootstep(Boolean isTargetingAndFootstep) {
        this.isTargetingAndFootstep = isTargetingAndFootstep;
    }

    public String getFoxCharaNameList() {
        return foxCharaNameList;
    }

    public void setFoxCharaNameList(String foxCharaNameList) {
        this.foxCharaNameList = foxCharaNameList;
    }

    public String getLoversCharaNameList() {
        return loversCharaNameList;
    }

    public void setLoversCharaNameList(String loversCharaNameList) {
        this.loversCharaNameList = loversCharaNameList;
    }
}
