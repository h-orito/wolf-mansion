package com.ort.app.web.model.inner.village;

public class VillageParticipateSkillDto {

    /** 役職コード */
    private String code;

    /** 襲撃能力を持っているか */
    private Boolean hasAttackAbility;

    /** 占い能力を持っているか */
    private Boolean hasDivineAbility;

    /** 護衛能力を持っているか */
    private Boolean hasGuardAbility;

    /** 徘徊能力を持っているか */
    private Boolean hasDisturbAbility;

    /** 足音を残す能力を持っているか */
    private Boolean hasFootstepAbility;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Boolean getHasAttackAbility() {
        return hasAttackAbility;
    }

    public void setHasAttackAbility(Boolean hasAttackAbility) {
        this.hasAttackAbility = hasAttackAbility;
    }

    public Boolean getHasDivineAbility() {
        return hasDivineAbility;
    }

    public void setHasDivineAbility(Boolean hasDivineAbility) {
        this.hasDivineAbility = hasDivineAbility;
    }

    public Boolean getHasGuardAbility() {
        return hasGuardAbility;
    }

    public void setHasGuardAbility(Boolean hasGuardAbility) {
        this.hasGuardAbility = hasGuardAbility;
    }

    public Boolean getHasDisturbAbility() {
        return hasDisturbAbility;
    }

    public void setHasDisturbAbility(Boolean hasDisturbAbility) {
        this.hasDisturbAbility = hasDisturbAbility;
    }

    public Boolean getHasFootstepAbility() {
        return hasFootstepAbility;
    }

    public void setHasFootstepAbility(Boolean hasFootstepAbility) {
        this.hasFootstepAbility = hasFootstepAbility;
    }
}
