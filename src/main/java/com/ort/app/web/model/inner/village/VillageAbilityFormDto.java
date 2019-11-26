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
}
