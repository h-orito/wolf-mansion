package com.ort.app.web.controller.assist;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.dbflute.cbean.result.ListResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import com.ort.app.datasource.VillageService;
import com.ort.app.logic.VillageParticipateLogic;
import com.ort.app.web.exception.WerewolfMansionBusinessException;
import com.ort.app.web.form.VillageChangeRequestSkillForm;
import com.ort.app.web.form.VillageParticipateForm;
import com.ort.dbflute.allcommon.CDef;
import com.ort.dbflute.exbhv.CharaBhv;
import com.ort.dbflute.exbhv.PlayerBhv;
import com.ort.dbflute.exbhv.VillageBhv;
import com.ort.dbflute.exbhv.VillagePlayerBhv;
import com.ort.dbflute.exbhv.VillageSettingsBhv;
import com.ort.dbflute.exentity.Chara;
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
    private CharaBhv charaBhv;
    @Autowired
    private VillageAssist villageAssist;
    @Autowired
    private VillageService villageService;
    @Autowired
    private VillageParticipateLogic villageParticipateLogic;

    // ===================================================================================
    //                                                                             Execute
    //                                                                             =======
    // 確認画面へ
    public String setConfirmModel(Integer villageId, VillageParticipateForm participateForm, BindingResult result, Model model) {
        // ログインしていなかったらNG
        UserInfo userInfo = WerewolfMansionUserInfoUtil.getUserInfo();
        if (result.hasErrors() || userInfo == null) {
            // 最新の日付を表示
            return villageAssist.setIndexModelAndReturnView(villageId, null, participateForm, null, model);
        }

        Chara chara = charaBhv.selectByPK(participateForm.getCharaId()).orElseThrow(() -> {
            return new IllegalArgumentException("改ざん？");
        });
        charaBhv.loadCharaImage(chara, charaImageCB -> charaImageCB.query().setFaceTypeCode_Equal_通常());
        model.addAttribute("characterImgUrl", chara.getNormalCharaImgUrl());
        model.addAttribute("characterImgWidth", chara.getDisplayWidth());
        model.addAttribute("characterImgHeight", chara.getDisplayHeight());
        model.addAttribute("villageId", villageId);
        Village village = selectVillage(villageId);
        model.addAttribute("villageName", village.getVillageDisplayName());
        // 発言確認画面へ
        return "participate-confirm";
    }

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
                CDef.Skill.codeOf(participateForm.getRequestedSkill()), CDef.Skill.codeOf(participateForm.getSecondRequestedSkill()),
                participateForm.getJoinMessage(), BooleanUtils.isTrue(participateForm.getIsSpectator()), false);
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
        VillagePlayer vPlayer = villageService.selectVillagePlayer(villageId, userInfo, true).orElseThrow(() -> {
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
        VillagePlayer vPlayer = villageService.selectVillagePlayer(villageId, userInfo, false).orElseThrow(() -> {
            return new IllegalArgumentException("セッション切れ？");
        });

        Village village = villageBhv.selectEntityWithDeletedCheck(cb -> cb.query().setVillageId_Equal(vPlayer.getVillageId()));
        if (!village.isVillageStatusCode募集中() && !village.isVillageStatusCode開始待ち()) {
            return "redirect:/village/" + villageId + "#bottom";
        }
        // 役職希望変更
        villageParticipateLogic.changeRequestSkill(vPlayer, changeRequestSkillForm.getRequestedSkill(),
                changeRequestSkillForm.getSecondRequestedSkill());
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
        ListResultBean<VillagePlayer> vPlayerList = villagePlayerBhv.selectList(cb -> {
            cb.query().setVillageId_Equal(villageId);
            cb.query().setIsGone_Equal_False();
        });

        boolean isAlreadyParticipateCharacter = vPlayerList.stream().anyMatch(vp -> vp.getCharaId().equals(participateForm.getCharaId()));
        if (isAlreadyParticipateCharacter) {
            throw new WerewolfMansionBusinessException("既に参加されているキャラクターです。別のキャラクターを選択してください。");
        }
        if (BooleanUtils.isTrue(participateForm.getIsSpectator())) {
            // 見学
            // [キャラチップの人数 - 定員]人を超えて見学はできない
            int charaNum = charaBhv
                    .selectCount(cb -> cb.query().setCharaGroupId_Equal(village.getVillageSettingsAsOne().get().getCharacterGroupId()));
            int capacity = village.getVillageSettingsAsOne().get().getPersonMaxNum();
            long spectatorNum = vPlayerList.stream().filter(vp -> vp.isIsSpectatorTrue()).count();
            if (charaNum - capacity <= spectatorNum) {
                throw new WerewolfMansionBusinessException("既に上限人数まで見学者がいるため見学者として参加できません。");
            }
        } else {
            long participateNum = vPlayerList.stream().filter(vp -> vp.isIsSpectatorFalse()).count();
            if (village.getVillageSettingsAsOne().get().getPersonMaxNum() <= participateNum) {
                throw new WerewolfMansionBusinessException("既に上限人数まで参加しているプレイヤーがいるため参加できません。");
            }
            // 役職希望無効なのにおまかせ以外
            if ((!CDef.Skill.おまかせ.code().equals(participateForm.getRequestedSkill())
                    || !CDef.Skill.おまかせ.code().equals(participateForm.getSecondRequestedSkill()))
                    && BooleanUtils.isFalse(village.getVillageSettingsAsOne().get().getIsPossibleSkillRequest())) {
                throw new WerewolfMansionBusinessException("希望役職が不正です。");
            }
        }
    }

    private boolean isInvalidForChangeRequestSkill(Integer villageId, VillageChangeRequestSkillForm changeRequestSkillForm,
            BindingResult result, UserInfo userInfo, Model model) {
        if (result.hasErrors() || userInfo == null) {
            return true;
        }
        return false;
    }

    // ===================================================================================
    //                                                                              Select
    //                                                                              ======
    private Village selectVillage(Integer villageId) {
        return villageBhv.selectEntityWithDeletedCheck(cb -> {
            cb.query().setVillageId_Equal(villageId);
        });
    }
}
