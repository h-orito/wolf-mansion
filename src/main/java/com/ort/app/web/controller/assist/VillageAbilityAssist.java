package com.ort.app.web.controller.assist;

import java.util.List;
import java.util.Optional;

import org.dbflute.cbean.result.ListResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import com.ort.app.datasource.VillageService;
import com.ort.app.logic.AbilityLogic;
import com.ort.app.logic.FootstepLogic;
import com.ort.app.util.SkillUtil;
import com.ort.app.web.form.VillageAbilityForm;
import com.ort.app.web.form.VillageGetFootstepListForm;
import com.ort.app.web.form.VillageVoteForm;
import com.ort.app.web.model.VillageGetFootstepListResultContent;
import com.ort.dbflute.allcommon.CDef;
import com.ort.dbflute.exbhv.VillagePlayerBhv;
import com.ort.dbflute.exbhv.VoteBhv;
import com.ort.dbflute.exentity.Village;
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
    private VillageAssist villageAssist;
    @Autowired
    private AbilityLogic abilityLogic;
    @Autowired
    private FootstepLogic footstepLogic;
    @Autowired
    private VillageService villageService;
    @Autowired
    private VillagePlayerBhv villagePlayerBhv;
    @Autowired
    private VoteBhv voteBhv;

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
        VillagePlayer villagePlayer = villageService.selectVillagePlayer(villageId, userInfo, false).orElseThrow(() -> {
            return new IllegalArgumentException("セッション切れ？");
        });
        if (villagePlayer.isIsDeadTrue()) {
            // 最新の日付を表示
            return villageAssist.setIndexModelAndReturnView(villageId, null, null, null, model);
        }
        if (isInvalidAbility(villagePlayer, abilityForm)) {
            // 最新の日付を表示
            return villageAssist.setIndexModelAndReturnView(villageId, null, null, null, model);
        }
        int day = villageService.selectLatestDay(villageId);
        Village village = villageService.selectVillage(villageId, false, false);
        abilityLogic.setAbility(village, villagePlayer, day, abilityForm.getCharaId(), abilityForm.getTargetCharaId(),
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
        VillagePlayer villagePlayer = villageService.selectVillagePlayer(villageId, userInfo, false).orElseThrow(() -> {
            return new IllegalArgumentException("セッション切れ？");
        });
        if (villagePlayer.isIsDeadTrue()) {
            // 最新の日付を表示
            return villageAssist.setIndexModelAndReturnView(villageId, null, null, null, model);
        }
        if (isInvalidVote(villageId, villagePlayer, voteForm)) {
            // 最新の日付を表示
            return villageAssist.setIndexModelAndReturnView(villageId, null, null, null, model);
        }
        int day = villageService.selectLatestDay(villageId);
        setVote(villageId, villagePlayer, day, villagePlayer.getCharaId(), voteForm.getTargetCharaId());
        return "redirect:/village/" + villageId + "#bottom";
    }

    public VillageGetFootstepListResultContent getFootstepList(VillageGetFootstepListForm form, BindingResult result) {
        // ログインしていなかったらNG
        UserInfo userInfo = WerewolfMansionUserInfoUtil.getUserInfo();
        if (result.hasErrors() || userInfo == null) {
            return null;
        }
        Optional<VillagePlayer> optVillagePlayer = villageService.selectVillagePlayer(form.getVillageId(), userInfo, false);
        if (!optVillagePlayer.isPresent()) {
            return null;
        }
        VillagePlayer vPlayer = optVillagePlayer.get();
        if (isInvalidFootstep(vPlayer, form)) {
            return null;
        }
        int day = villageService.selectLatestDay(form.getVillageId());
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
        if (!SkillUtil.SET_AVAILABLE_SKILL_LIST.contains(skill)) {
            return true;
        }
        Integer charaId = abilityForm.getCharaId();
        Integer targetCharaId = abilityForm.getTargetCharaId();
        String footstep = abilityForm.getFootstep();
        if (skill.isHasAttackAbility() && targetCharaId != null && (charaId == null || footstep == null)) {
            return true;
        }
        if (skill.isHasDivineAbility() && (targetCharaId == null || footstep == null)) {
            return true;
        }
        if (skill == CDef.Skill.狩人 && targetCharaId == null) {
            return true;
        }
        if ((skill == CDef.Skill.妖狐 || skill.isHasMadmanAbility()) && footstep == null) {
            return true;
        }
        if (skill == CDef.Skill.探偵 && footstep == null) {
            return true;
        }
        if (skill == CDef.Skill.同棲者 && targetCharaId == null) {
            return true;
        }
        if (villagePlayer.isIsDeadTrue()) {
            return true;
        }
        return false;
    }

    private boolean isInvalidVote(Integer villageId, VillagePlayer villagePlayer, VillageVoteForm voteForm) {
        List<VillagePlayer> villagePlayerList = selectVillagePlayerList(villageId);
        return !villagePlayerList.stream().anyMatch(vp -> vp.isIsDeadFalse() && vp.getCharaId().equals(voteForm.getTargetCharaId()));
    }

    private boolean isInvalidFootstep(VillagePlayer villagePlayer, VillageGetFootstepListForm form) {
        CDef.Skill skill = villagePlayer.getSkillCodeAsSkill();
        if (!skill.isHasAttackAbility() && !skill.isHasDivineAbility() && skill != CDef.Skill.狩人) {
            return true;
        }
        if (skill.isHasAttackAbility() && form.getCharaId() == null) {
            return true;
        }
        if (skill.isHasDivineAbility() && form.getTargetCharaId() == null) {
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
        voteBhv.selectEntity(cb -> {
            cb.query().setVillageId_Equal(villageId);
            cb.query().setDay_Equal(day);
            cb.query().setCharaId_Equal(charaId);
        }).ifPresent(vote -> {
            vote.setVoteCharaId(targetCharaId);
            voteBhv.update(vote);
        }).orElse(() -> {
            Vote vote = new Vote();
            vote.setVillageId(villageId);
            vote.setDay(day);
            vote.setCharaId(charaId);
            vote.setVoteCharaId(targetCharaId);
            voteBhv.insert(vote);
        });
    }
}
