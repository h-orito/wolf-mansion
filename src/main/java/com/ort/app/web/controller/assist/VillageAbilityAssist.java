package com.ort.app.web.controller.assist;

import java.util.Arrays;
import java.util.List;

import org.dbflute.cbean.result.ListResultBean;
import org.dbflute.optional.OptionalThing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import com.ort.app.web.controller.logic.AbilityLogic;
import com.ort.app.web.controller.logic.FootstepLogic;
import com.ort.app.web.form.VillageAbilityForm;
import com.ort.app.web.form.VillageGetFootstepListForm;
import com.ort.app.web.form.VillageVoteForm;
import com.ort.app.web.model.VillageGetFootstepListResultContent;
import com.ort.app.web.util.SkillUtil;
import com.ort.dbflute.allcommon.CDef;
import com.ort.dbflute.exbhv.VillagePlayerBhv;
import com.ort.dbflute.exbhv.VoteBhv;
import com.ort.dbflute.exentity.VillagePlayer;
import com.ort.dbflute.exentity.Vote;
import com.ort.fw.security.UserInfo;
import com.ort.fw.util.WerewolfMansionUserInfoUtil;

@Component
public class VillageAbilityAssist {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    @Autowired
    private VillagePlayerBhv villagePlayerBhv;

    @Autowired
    private VoteBhv voteBhv;

    @Autowired
    private VillageAssist villageAssist;

    @Autowired
    private AbilityLogic abilityLogic;

    @Autowired
    private FootstepLogic footstepLogic;

    // ===================================================================================
    //                                                                             Execute
    //                                                                             =======
    public String setAbility(Integer villageId, VillageAbilityForm abilityForm, BindingResult result, Model model) {
        // ログインしていなかったらNG
        UserInfo userInfo = WerewolfMansionUserInfoUtil.getUserInfo();
        if (result.hasErrors() || userInfo == null) {
            // 最新の日付を表示
            return villageAssist.setIndexModelAndReturnView(villageId, null, null, null, model);
        }
        VillagePlayer villagePlayer = villageAssist.selectVillagePlayer(villageId, userInfo, false).orElseThrow(() -> {
            return new IllegalArgumentException("セッション切れ？");
        });
        if (isInvalidAbility(villagePlayer, abilityForm)) {
            // 最新の日付を表示
            return villageAssist.setIndexModelAndReturnView(villageId, null, null, null, model);
        }
        int day = villageAssist.selectLatestDay(villageId);
        abilityLogic.setAbility(villageId, villagePlayer, day, abilityForm.getCharaId(), abilityForm.getTargetCharaId(),
                abilityForm.getFootstep());
        return "redirect:/village/" + villageId + "#bottom";
    }

    public String setVote(Integer villageId, VillageVoteForm voteForm, BindingResult result, Model model) {
        // ログインしていなかったらNG
        UserInfo userInfo = WerewolfMansionUserInfoUtil.getUserInfo();
        if (result.hasErrors() || userInfo == null) {
            // 最新の日付を表示
            return villageAssist.setIndexModelAndReturnView(villageId, null, null, null, model);
        }
        VillagePlayer villagePlayer = villageAssist.selectVillagePlayer(villageId, userInfo, false).orElseThrow(() -> {
            return new IllegalArgumentException("セッション切れ？");
        });
        if (isInvalidVote(villageId, villagePlayer, voteForm)) {
            // 最新の日付を表示
            return villageAssist.setIndexModelAndReturnView(villageId, null, null, null, model);
        }
        int day = villageAssist.selectLatestDay(villageId);
        setVote(villageId, villagePlayer, day, villagePlayer.getCharaId(), voteForm.getTargetCharaId());
        return "redirect:/village/" + villageId + "#bottom";
    }

    public VillageGetFootstepListResultContent getFootstepList(VillageGetFootstepListForm form, BindingResult result) {
        // ログインしていなかったらNG
        UserInfo userInfo = WerewolfMansionUserInfoUtil.getUserInfo();
        if (result.hasErrors() || userInfo == null) {
            return null;
        }
        OptionalThing<VillagePlayer> optVillagePlayer = villageAssist.selectVillagePlayer(form.getVillageId(), userInfo, false);
        if (!optVillagePlayer.isPresent()) {
            return null;
        }
        VillagePlayer vPlayer = optVillagePlayer.get();
        if (isInvalidFootstep(vPlayer, form)) {
            return null;
        }
        int day = villageAssist.selectLatestDay(form.getVillageId());
        List<String> footStepList =
                footstepLogic.getFootstepCandidateList(form.getVillageId(), vPlayer, day, form.getCharaId(), form.getTargetCharaId());
        VillageGetFootstepListResultContent response = new VillageGetFootstepListResultContent();
        response.setFootstepList(footStepList);
        return response;
    }

    // ===================================================================================
    //                                                                          Validation
    //                                                                          ==========
    private boolean isInvalidAbility(VillagePlayer villagePlayer, VillageAbilityForm abilityForm) {
        CDef.Skill skill = villagePlayer.getSkillCodeAsSkill();
        if (!isAvailableSetAbilitySkill(skill)) {
            return true;
        }
        Integer charaId = abilityForm.getCharaId();
        Integer targetCharaId = abilityForm.getTargetCharaId();
        String footstep = abilityForm.getFootstep();
        if (skill == CDef.Skill.人狼 && targetCharaId != null && (charaId == null || footstep == null)) {
            return true;
        }
        if (SkillUtil.hasDivineAbility(skill) && (targetCharaId == null || footstep == null)) {
            return true;
        }
        if (skill == CDef.Skill.狩人 && targetCharaId == null) {
            return true;
        }
        if ((skill == CDef.Skill.妖狐 || SkillUtil.hasMadmanAbility(skill)) && footstep == null) {
            return true;
        }
        if (villagePlayer.isIsDeadTrue()) {
            return true;
        }
        return false;
    }

    private boolean isAvailableSetAbilitySkill(CDef.Skill skill) {
        return Arrays
                .asList(CDef.Skill.人狼, CDef.Skill.占い師, CDef.Skill.狩人, CDef.Skill.狂人, CDef.Skill.妖狐, CDef.Skill.C国狂人, CDef.Skill.賢者,
                        CDef.Skill.魔神官)
                .contains(skill);

    }

    private boolean isInvalidVote(Integer villageId, VillagePlayer villagePlayer, VillageVoteForm voteForm) {
        List<VillagePlayer> villagePlayerList = selectVillagePlayerList(villageId);
        return !villagePlayerList.stream().anyMatch(vp -> vp.isIsDeadFalse() && vp.getCharaId().equals(voteForm.getTargetCharaId()));
    }

    private boolean isInvalidFootstep(VillagePlayer villagePlayer, VillageGetFootstepListForm form) {
        CDef.Skill skill = villagePlayer.getSkillCodeAsSkill();
        if (skill != CDef.Skill.人狼 && !SkillUtil.hasDivineAbility(skill) && skill != CDef.Skill.狩人) {
            return true;
        }
        if (skill == CDef.Skill.人狼 && form.getCharaId() == null) {
            return true;
        }
        if (SkillUtil.hasDivineAbility(skill) && form.getTargetCharaId() == null) {
            return true;
        }
        if (skill == CDef.Skill.狩人 && form.getTargetCharaId() == null) {
            return true;
        }

        return false;
    }

    // ===================================================================================
    //                                                                              Select
    //                                                                              ======
    private ListResultBean<VillagePlayer> selectVillagePlayerList(Integer villageId) {
        return villagePlayerBhv.selectList(cb -> {
            cb.setupSelect_Chara();
            cb.setupSelect_SkillBySkillCode();
            cb.query().setVillageId_Equal(villageId);
            cb.query().setIsGone_Equal_False();
        });
    }

    // ===================================================================================
    //                                                                              Update
    //                                                                              ======
    private void setVote(Integer villageId, VillagePlayer villagePlayer, int day, Integer charaId, Integer targetCharaId) {
        Vote vote = new Vote();
        vote.setVillageId(villageId);
        vote.setDay(day);
        vote.setCharaId(charaId);
        vote.setVoteCharaId(targetCharaId);
        voteBhv.update(vote);
    }
}
