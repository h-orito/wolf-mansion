package com.ort.app.web.model.inner.village;

import java.time.LocalDateTime;
import java.util.List;

import com.ort.app.web.form.NewVillageRpSayRestrictDto;
import com.ort.app.web.form.NewVillageSayRestrictDto;
import com.ort.app.web.form.NewVillageSkillSayRestrictDto;

public class VillageSettingsDto {

    /** 最低開始人数 */
    private Integer startPersonMinNum;

    /** 定員 */
    private Integer personMaxNum;

    /** 開始日時 */
    private LocalDateTime startDatetime;

    /** 更新間隔 */
    private String dayChangeInterval;

    /** 投票形式 */
    private String voteType;

    /** 役職希望 */
    private String skillRequestType;

    /** キャラセット名 */
    private String charaGroupName;

    /** ダミーキャラ名 */
    private String dummyCharaName;

    /** キャラクターグループID */
    private Integer charaGroupId;

    /** 役職希望有効か */
    private Boolean isSkillRequestAvailable;

    /** 入村パスワードを必要とするか */
    private Boolean isRequiredJoinPassword;

    /** 見学が可能か */
    private Boolean isAvailableSpectate;

    /** 連続襲撃ありか */
    private Boolean isAvailableSameWolfAttack;

    /** 連続護衛可能か */
    private Boolean isAvailableGuardSameTarget;

    /** 墓下役職公開ありか */
    private Boolean isOpenSkillInGrave;

    /** 墓下見学発言を地上から見られるか */
    private Boolean isVisibleGraveSpectateMessage;

    /** 秘話可能範囲 */
    private String allowedSecretSayCode;

    /** 突然死ありか */
    private Boolean isAvailableSuddonlyDeath;

    /** コミットありか */
    private Boolean isAvailableCommit;

    /** アクションありか */
    private Boolean isAvailableAction;

    /** 構成 */
    private String organization;

    /** 村設定の発言制限 */
    private List<NewVillageSayRestrictDto> sayRestrictList;

    /** 村設定の役職発言制限 */
    private List<NewVillageSkillSayRestrictDto> skillSayRestrictList;

    /** 村設定のRP発言制限 */
    private List<NewVillageRpSayRestrictDto> rpSayRestrictList;

    /** 村建てしたプレーヤー名 */
    private String createPlayerName;

    public Integer getStartPersonMinNum() {
        return startPersonMinNum;
    }

    public void setStartPersonMinNum(Integer startPersonMinNum) {
        this.startPersonMinNum = startPersonMinNum;
    }

    public Integer getPersonMaxNum() {
        return personMaxNum;
    }

    public void setPersonMaxNum(Integer personMaxNum) {
        this.personMaxNum = personMaxNum;
    }

    public LocalDateTime getStartDatetime() {
        return startDatetime;
    }

    public void setStartDatetime(LocalDateTime startDatetime) {
        this.startDatetime = startDatetime;
    }

    public String getDayChangeInterval() {
        return dayChangeInterval;
    }

    public void setDayChangeInterval(String dayChangeInterval) {
        this.dayChangeInterval = dayChangeInterval;
    }

    public String getVoteType() {
        return voteType;
    }

    public void setVoteType(String voteType) {
        this.voteType = voteType;
    }

    public String getSkillRequestType() {
        return skillRequestType;
    }

    public void setSkillRequestType(String skillRequestType) {
        this.skillRequestType = skillRequestType;
    }

    public String getCharaGroupName() {
        return charaGroupName;
    }

    public void setCharaGroupName(String charaGroupName) {
        this.charaGroupName = charaGroupName;
    }

    public Integer getCharaGroupId() {
        return charaGroupId;
    }

    public void setCharaGroupId(Integer charaGroupId) {
        this.charaGroupId = charaGroupId;
    }

    public Boolean getIsRequiredJoinPassword() {
        return isRequiredJoinPassword;
    }

    public void setIsRequiredJoinPassword(Boolean isRequiredJoinPassword) {
        this.isRequiredJoinPassword = isRequiredJoinPassword;
    }

    public Boolean getIsAvailableSpectate() {
        return isAvailableSpectate;
    }

    public void setIsAvailableSpectate(Boolean isAvailableSpectate) {
        this.isAvailableSpectate = isAvailableSpectate;
    }

    public Boolean getIsAvailableSameWolfAttack() {
        return isAvailableSameWolfAttack;
    }

    public void setIsAvailableSameWolfAttack(Boolean isAvailableSameWolfAttack) {
        this.isAvailableSameWolfAttack = isAvailableSameWolfAttack;
    }

    public Boolean getIsOpenSkillInGrave() {
        return isOpenSkillInGrave;
    }

    public void setIsOpenSkillInGrave(Boolean isOpenSkillInGrave) {
        this.isOpenSkillInGrave = isOpenSkillInGrave;
    }

    public Boolean getIsVisibleGraveSpectateMessage() {
        return isVisibleGraveSpectateMessage;
    }

    public void setIsVisibleGraveSpectateMessage(Boolean isVisibleGraveSpectateMessage) {
        this.isVisibleGraveSpectateMessage = isVisibleGraveSpectateMessage;
    }

    public Boolean getIsAvailableSuddonlyDeath() {
        return isAvailableSuddonlyDeath;
    }

    public void setIsAvailableSuddonlyDeath(Boolean isAvailableSuddonlyDeath) {
        this.isAvailableSuddonlyDeath = isAvailableSuddonlyDeath;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public Boolean getIsAvailableGuardSameTarget() {
        return isAvailableGuardSameTarget;
    }

    public void setIsAvailableGuardSameTarget(Boolean isAvailableGuardSameTarget) {
        this.isAvailableGuardSameTarget = isAvailableGuardSameTarget;
    }

    public Boolean getIsAvailableCommit() {
        return isAvailableCommit;
    }

    public void setIsAvailableCommit(Boolean isAvailableCommit) {
        this.isAvailableCommit = isAvailableCommit;
    }

    public String getAllowedSecretSayCode() {
        return allowedSecretSayCode;
    }

    public void setAllowedSecretSayCode(String allowedSecretSayCode) {
        this.allowedSecretSayCode = allowedSecretSayCode;
    }

    public String getDummyCharaName() {
        return dummyCharaName;
    }

    public void setDummyCharaName(String dummyCharaName) {
        this.dummyCharaName = dummyCharaName;
    }

    public List<NewVillageSayRestrictDto> getSayRestrictList() {
        return sayRestrictList;
    }

    public void setSayRestrictList(List<NewVillageSayRestrictDto> sayRestrictList) {
        this.sayRestrictList = sayRestrictList;
    }

    public Boolean getIsSkillRequestAvailable() {
        return isSkillRequestAvailable;
    }

    public void setIsSkillRequestAvailable(Boolean isSkillRequestAvailable) {
        this.isSkillRequestAvailable = isSkillRequestAvailable;
    }

    public String getCreatePlayerName() {
        return createPlayerName;
    }

    public void setCreatePlayerName(String createPlayerName) {
        this.createPlayerName = createPlayerName;
    }

    public List<NewVillageSkillSayRestrictDto> getSkillSayRestrictList() {
        return skillSayRestrictList;
    }

    public void setSkillSayRestrictList(List<NewVillageSkillSayRestrictDto> skillSayRestrictList) {
        this.skillSayRestrictList = skillSayRestrictList;
    }

    public Boolean getIsAvailableAction() {
        return isAvailableAction;
    }

    public void setIsAvailableAction(Boolean isAvailableAction) {
        this.isAvailableAction = isAvailableAction;
    }

    public List<NewVillageRpSayRestrictDto> getRpSayRestrictList() {
        return rpSayRestrictList;
    }

    public void setRpSayRestrictList(List<NewVillageRpSayRestrictDto> rpSayRestrictList) {
        this.rpSayRestrictList = rpSayRestrictList;
    }
}
