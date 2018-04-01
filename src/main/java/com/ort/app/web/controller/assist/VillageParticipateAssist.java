package com.ort.app.web.controller.assist;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import com.ort.app.web.controller.logic.VillageParticipateLogic;
import com.ort.app.web.exception.WerewolfMansionBusinessException;
import com.ort.app.web.form.VillageChangeRequestSkillForm;
import com.ort.app.web.form.VillageParticipateForm;
import com.ort.dbflute.allcommon.CDef;
import com.ort.dbflute.exbhv.PlayerBhv;
import com.ort.dbflute.exbhv.VillageBhv;
import com.ort.dbflute.exbhv.VillagePlayerBhv;
import com.ort.dbflute.exbhv.VillageSettingsBhv;
import com.ort.dbflute.exentity.Village;
import com.ort.dbflute.exentity.VillagePlayer;
import com.ort.dbflute.exentity.VillageSettings;
import com.ort.fw.security.UserInfo;
import com.ort.fw.util.WerewolfMansionUserInfoUtil;

@Component
public class VillageParticipateAssist {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    @Autowired
    private VillageBhv villageBhv;

    @Autowired
    private VillageSettingsBhv villageSettingsBhv;

    @Autowired
    private VillagePlayerBhv villagePlayerBhv;

    @Autowired
    private PlayerBhv playerBhv;

    @Autowired
    private VillageAssist villageAssist;

    @Autowired
    private VillageParticipateLogic villageParticipateLogic;

    // ===================================================================================
    //                                                                             Execute
    //                                                                             =======
    // 参戦する
    public String participate(Integer villageId, VillageParticipateForm participateForm, BindingResult result, Model model) {
        UserInfo userInfo = WerewolfMansionUserInfoUtil.getUserInfo();
        if (isInvalidForParticipate(villageId, participateForm, result, userInfo, model)) {
            return villageAssist.setIndexModelAndReturnView(villageId, null, participateForm, null, model);
        }
        VillageSettings settings = villageSettingsBhv.selectByPK(villageId).get();
        if (StringUtils.isNotEmpty(settings.getJoinPassword())
                && !StringUtils.equals(settings.getJoinPassword(), participateForm.getJoinPassword())) {
            model.addAttribute("participateErrorMessage", "入村パスワードが誤っています");
            return villageAssist.setIndexModelAndReturnView(villageId, null, participateForm, null, model);
        }

        Integer playerId =
                playerBhv.selectEntityWithDeletedCheck(cb -> cb.query().setPlayerName_Equal(userInfo.getUsername())).getPlayerId();
        // 参戦
        villageParticipateLogic.participate(villageId, playerId, participateForm.getCharaId(),
                CDef.Skill.codeOf(participateForm.getRequestedSkill()), participateForm.getJoinMessage());
        // 最新の日へ
        return "redirect:/village/" + villageId + "#bottom";
    }

    // 退村する
    public String leave(Integer villageId, Model model) {
        // ログインしていなかったらNG
        UserInfo userInfo = WerewolfMansionUserInfoUtil.getUserInfo();
        if (userInfo == null) {
            // 最新の日付を表示
            return villageAssist.setIndexModelAndReturnView(villageId, null, null, null, model);
        }
        VillagePlayer vPlayer = villageAssist.selectVillagePlayer(villageId, userInfo).orElseThrow(() -> {
            return new IllegalArgumentException("セッション切れ？");
        });
        Village village = villageBhv.selectEntityWithDeletedCheck(cb -> cb.query().setVillageId_Equal(vPlayer.getVillageId()));

        if (!village.isVillageStatusCode募集中() && !village.isVillageStatusCode開始待ち()) {
            return "redirect:/village/" + villageId + "#bottom";
        }
        // 退村
        villageParticipateLogic.leave(vPlayer);
        // 最新の日へ
        return "redirect:/village/" + villageId + "#bottom";
    }

    // 役職希望変更
    public String changeRequestSkill(Integer villageId, VillageChangeRequestSkillForm changeRequestSkillForm, BindingResult result,
            Model model) {
        UserInfo userInfo = WerewolfMansionUserInfoUtil.getUserInfo();
        if (isInvalidForChangeRequestSkill(villageId, changeRequestSkillForm, result, userInfo, model)) {
            return villageAssist.setIndexModelAndReturnView(villageId, null, null, changeRequestSkillForm, model);
        }
        VillagePlayer vPlayer = villageAssist.selectVillagePlayer(villageId, userInfo).orElseThrow(() -> {
            return new IllegalArgumentException("セッション切れ？");
        });

        Village village = villageBhv.selectEntityWithDeletedCheck(cb -> cb.query().setVillageId_Equal(vPlayer.getVillageId()));
        if (!village.isVillageStatusCode募集中() && !village.isVillageStatusCode開始待ち()) {
            return "redirect:/village/" + villageId + "#bottom";
        }
        // 役職希望変更
        villageParticipateLogic.changeRequestSkill(vPlayer, changeRequestSkillForm.getRequestedSkill());
        // 最新の日へ
        return "redirect:/village/" + villageId + "#bottom";
    }

    // ===================================================================================
    //                                                                          Validation
    //                                                                          ==========
    private boolean isInvalidForParticipate(Integer villageId, VillageParticipateForm participateForm, BindingResult result,
            UserInfo userInfo, Model model) {
        if (result.hasErrors() || userInfo == null) {
            return true;
        }
        try {
            assertParticipate(villageId, participateForm);
        } catch (WerewolfMansionBusinessException e) {
            model.addAttribute("participateErrorMessage", e.getMessage());
            return true;
        }
        return false;
    }

    private void assertParticipate(Integer villageId, VillageParticipateForm participateForm) throws WerewolfMansionBusinessException {
        Village village = villageBhv.selectEntityWithDeletedCheck(cb -> {
            cb.setupSelect_VillageSettingsAsOne();
            cb.query().setVillageId_Equal(villageId);
        });
        int participateCount = villagePlayerBhv.selectCount(cb -> {
            cb.query().setVillageId_Equal(villageId);
            cb.query().setCharaId_Equal(participateForm.getCharaId());
            cb.query().setIsGone_Equal_False();
        });
        if (participateCount > 0) {
            throw new WerewolfMansionBusinessException("既に参加されているキャラクターです。別のキャラクターを選択してください。");
        } else if (participateCount >= village.getVillageSettingsAsOne().get().getPersonMaxNum()) {
            throw new WerewolfMansionBusinessException("既に上限人数まで参加しているプレイヤーがいるため参加できません。");
        }
        // 役職希望無効なのにおまかせ以外
        if (!CDef.Skill.おまかせ.code().equals(participateForm.getRequestedSkill())
                && BooleanUtils.isFalse(village.getVillageSettingsAsOne().get().getIsPossibleSkillRequest())) {
            throw new WerewolfMansionBusinessException("希望役職が不正です。");
        }
    }

    private boolean isInvalidForChangeRequestSkill(Integer villageId, VillageChangeRequestSkillForm changeRequestSkillForm,
            BindingResult result, UserInfo userInfo, Model model) {
        if (result.hasErrors() || userInfo == null) {
            return true;
        }
        return false;
    }
}
