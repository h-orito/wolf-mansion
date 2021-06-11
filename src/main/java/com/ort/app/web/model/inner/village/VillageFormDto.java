package com.ort.app.web.model.inner.village;

public class VillageFormDto {

    /** 参戦、役職変更、退村 */
    private VillageParticipateFormDto participate;

    /** コミット */
    private VillageCommitFormDto commit;

    /** 発言 */
    private VillageSayFormDto say;

    /** アクション */
    private VillageActionFormDto action;

    /** 能力行使 */
    private VillageAbilityFormDto ability;

    /** 投票 */
    private VillageVoteFormDto vote;

    public VillageParticipateFormDto getParticipate() {
        return participate;
    }

    public void setParticipate(VillageParticipateFormDto participate) {
        this.participate = participate;
    }

    public VillageCommitFormDto getCommit() {
        return commit;
    }

    public void setCommit(VillageCommitFormDto commit) {
        this.commit = commit;
    }

    public VillageSayFormDto getSay() {
        return say;
    }

    public void setSay(VillageSayFormDto say) {
        this.say = say;
    }

    public VillageAbilityFormDto getAbility() {
        return ability;
    }

    public void setAbility(VillageAbilityFormDto ability) {
        this.ability = ability;
    }

    public VillageVoteFormDto getVote() {
        return vote;
    }

    public void setVote(VillageVoteFormDto vote) {
        this.vote = vote;
    }

    public VillageActionFormDto getAction() {
        return action;
    }

    public void setAction(VillageActionFormDto action) {
        this.action = action;
    }
}
