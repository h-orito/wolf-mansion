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
import com.ort.app.web.controller.assist.impl.VillageForms;
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
    // ???????????????
    public String setConfirmModel(Integer villageId, VillageParticipateForm participateForm, BindingResult result, Model model) {
        // ????????????????????????????????????NG
        UserInfo userInfo = WerewolfMansionUserInfoUtil.getUserInfo();
        if (result.hasErrors() || userInfo == null) {
            // ????????????????????????
            return villageAssist.setIndexModelAndReturnView(villageId, new VillageForms.Builder().participateForm(participateForm).build(),
                    model);
        }

        Chara chara = charaBhv.selectByPK(participateForm.getCharaId()).orElseThrow(() -> {
            return new IllegalArgumentException("????????????");
        });
        charaBhv.loadCharaImage(chara, charaImageCB -> charaImageCB.query().setFaceTypeCode_Equal_??????());
        model.addAttribute("characterImgUrl", chara.getNormalCharaImgUrl());
        model.addAttribute("characterImgWidth", chara.getDisplayWidth());
        model.addAttribute("characterImgHeight", chara.getDisplayHeight());
        model.addAttribute("villageId", villageId);
        Village village = selectVillage(villageId);
        model.addAttribute("villageName", village.getVillageDisplayName());
        // ?????????????????????
        return "participate-confirm";
    }

    // ????????????
    public String participate(Integer villageId, VillageParticipateForm participateForm, BindingResult result, Model model) {
        UserInfo userInfo = WerewolfMansionUserInfoUtil.getUserInfo();
        if (isInvalidForParticipate(villageId, participateForm, result, userInfo, model)) {
            return villageAssist.setIndexModelAndReturnView(villageId, new VillageForms.Builder().participateForm(participateForm).build(),
                    model);
        }
        VillageSettings settings = villageSettingsBhv.selectByPK(villageId).get();
        if (StringUtils.isNotEmpty(settings.getJoinPassword())
                && !StringUtils.equals(settings.getJoinPassword(), participateForm.getJoinPassword())) {
            model.addAttribute("participateErrorMessage", "??????????????????????????????????????????");
            return villageAssist.setIndexModelAndReturnView(villageId, new VillageForms.Builder().participateForm(participateForm).build(),
                    model);
        }

        Integer playerId =
                playerBhv.selectEntityWithDeletedCheck(cb -> cb.query().setPlayerName_Equal(userInfo.getUsername())).getPlayerId();
        // ??????
        villageParticipateLogic.participate(villageId, playerId, participateForm.getCharaId(),
                CDef.Skill.codeOf(participateForm.getRequestedSkill()), CDef.Skill.codeOf(participateForm.getSecondRequestedSkill()),
                participateForm.getJoinMessage(), BooleanUtils.isTrue(participateForm.getIsSpectator()), false);
        // ???????????????
        return "redirect:/village/" + villageId + "#bottom";
    }

    // ????????????
    public String leave(Integer villageId, Model model) {
        // ????????????????????????????????????NG
        UserInfo userInfo = WerewolfMansionUserInfoUtil.getUserInfo();
        if (userInfo == null) {
            // ????????????????????????
            return villageAssist.setIndexModelAndReturnView(villageId, VillageForms.empty(), model);
        }
        VillagePlayer vPlayer = villageService.selectVillagePlayer(villageId, userInfo, true).orElseThrow(() -> {
            return new IllegalArgumentException("????????????????????????");
        });
        Village village = villageBhv.selectEntityWithDeletedCheck(cb -> cb.query().setVillageId_Equal(vPlayer.getVillageId()));

        if (!village.isVillageStatusCode?????????() && !village.isVillageStatusCode????????????()) {
            return "redirect:/village/" + villageId + "#bottom";
        }
        // ??????
        villageParticipateLogic.leave(vPlayer);
        // ???????????????
        return "redirect:/village/" + villageId + "#bottom";
    }

    // ??????????????????
    public String changeRequestSkill(Integer villageId, VillageChangeRequestSkillForm changeRequestSkillForm, BindingResult result,
            Model model) {
        UserInfo userInfo = WerewolfMansionUserInfoUtil.getUserInfo();
        if (isInvalidForChangeRequestSkill(villageId, changeRequestSkillForm, result, userInfo, model)) {
            return villageAssist.setIndexModelAndReturnView(villageId,
                    new VillageForms.Builder().changeRequestSkillForm(changeRequestSkillForm).build(), model);
        }
        VillagePlayer vPlayer = villageService.selectVillagePlayer(villageId, userInfo, false).orElseThrow(() -> {
            return new IllegalArgumentException("????????????????????????");
        });

        Village village = villageBhv.selectEntityWithDeletedCheck(cb -> cb.query().setVillageId_Equal(vPlayer.getVillageId()));
        if (!village.isVillageStatusCode?????????() && !village.isVillageStatusCode????????????()) {
            return "redirect:/village/" + villageId + "#bottom";
        }
        // ??????????????????
        villageParticipateLogic.changeRequestSkill(vPlayer, changeRequestSkillForm.getRequestedSkill(),
                changeRequestSkillForm.getSecondRequestedSkill());
        // ???????????????
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
            throw new WerewolfMansionBusinessException("????????????????????????????????????????????????????????????????????????????????????????????????????????????");
        }
        if (BooleanUtils.isTrue(participateForm.getIsSpectator())) {
            // ??????
            // [??????????????????????????? - ??????]????????????????????????????????????
            int charaNum = charaBhv
                    .selectCount(cb -> cb.query().setCharaGroupId_Equal(village.getVillageSettingsAsOne().get().getCharacterGroupId()));
            int capacity = village.getVillageSettingsAsOne().get().getPersonMaxNum();
            long spectatorNum = vPlayerList.stream().filter(vp -> vp.isIsSpectatorTrue()).count();
            if (charaNum - capacity <= spectatorNum) {
                throw new WerewolfMansionBusinessException("??????????????????????????????????????????????????????????????????????????????????????????");
            }
        } else {
            long participateNum = vPlayerList.stream().filter(vp -> vp.isIsSpectatorFalse()).count();
            if (village.getVillageSettingsAsOne().get().getPersonMaxNum() <= participateNum) {
                throw new WerewolfMansionBusinessException("????????????????????????????????????????????????????????????????????????????????????????????????");
            }
            // ?????????????????????????????????????????????
            if ((!CDef.Skill.????????????.code().equals(participateForm.getRequestedSkill())
                    || !CDef.Skill.????????????.code().equals(participateForm.getSecondRequestedSkill()))
                    && BooleanUtils.isFalse(village.getVillageSettingsAsOne().get().getIsPossibleSkillRequest())) {
                throw new WerewolfMansionBusinessException("??????????????????????????????");
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
