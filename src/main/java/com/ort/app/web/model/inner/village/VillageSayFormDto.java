package com.ort.app.web.model.inner.village;

import java.util.List;

import com.ort.app.web.model.OptionDto;
import com.ort.app.web.model.inner.SayRestrictDto;
import com.ort.dbflute.exentity.Chara;

public class VillageSayFormDto {

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

    /** 秘話相手 */
    private List<Chara> secretSayTargetList;

    public void setAllSayTypeAvailable(boolean isAvailable) {
        this.isAvailableNormalSay = isAvailable;
        this.isAvailableWerewolfSay = isAvailable;
        this.isAvailableMasonSay = isAvailable;
        this.isAvailableGraveSay = isAvailable;
        this.isAvailableSpectateSay = isAvailable;
        this.isAvailableMonologueSay = isAvailable;
        this.isAvailableSecretSay = isAvailable;
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

    public List<Chara> getSecretSayTargetList() {
        return secretSayTargetList;
    }

    public void setSecretSayTargetList(List<Chara> secretSayTargetList) {
        this.secretSayTargetList = secretSayTargetList;
    }
}
