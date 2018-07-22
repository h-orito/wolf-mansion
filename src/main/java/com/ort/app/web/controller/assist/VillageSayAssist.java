package com.ort.app.web.controller.assist;

import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import com.ort.app.web.controller.logic.MessageLogic;
import com.ort.app.web.exception.WerewolfMansionBusinessException;
import com.ort.app.web.form.VillageSayForm;
import com.ort.dbflute.allcommon.CDef;
import com.ort.dbflute.allcommon.CDef.MessageType;
import com.ort.dbflute.exbhv.VillageBhv;
import com.ort.dbflute.exentity.Village;
import com.ort.dbflute.exentity.VillagePlayer;
import com.ort.fw.security.UserInfo;
import com.ort.fw.util.WerewolfMansionUserInfoUtil;

@Component
public class VillageSayAssist {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    @Autowired
    private VillageBhv villageBhv;

    @Autowired
    private VillageAssist villageAssist;

    @Autowired
    private MessageLogic messageLogic;

    // ===================================================================================
    //                                                                             Execute
    //                                                                             =======
    public String setConfirmModel(Integer villageId, VillageSayForm sayForm, BindingResult result, Model model) {
        // ログインしていなかったらNG
        UserInfo userInfo = WerewolfMansionUserInfoUtil.getUserInfo();
        if (result.hasErrors() || userInfo == null) {
            // 最新の日付を表示
            return villageAssist.setIndexModelAndReturnView(villageId, sayForm, null, null, model);
        }
        // 発言権利がなかったらNG
        if (!isAvailableSay(villageId, userInfo, sayForm)) {
            // 最新の日付を表示
            return villageAssist.setIndexModelAndReturnView(villageId, sayForm, null, null, model);
        }
        VillagePlayer villagePlayer = villageAssist.selectVillagePlayer(villageId, userInfo, true).orElseThrow(() -> {
            return new IllegalArgumentException("セッション切れ？");
        });
        model.addAttribute("characterImgUrl", villagePlayer.getChara().get().getCharaImgUrl());
        model.addAttribute("characterImgWidth", villagePlayer.getChara().get().getDisplayWidth());
        model.addAttribute("characterImgHeight", villagePlayer.getChara().get().getDisplayHeight());

        model.addAttribute("villageId", villageId);
        Village village = selectVillage(villageId);
        model.addAttribute("villageName", village.getVillageDisplayName());
        // 発言確認画面へ
        return "say-confirm";
    }

    public String say(Integer villageId, VillageSayForm sayForm, BindingResult result, Model model) {
        // ログインしていなかったらNG
        UserInfo userInfo = WerewolfMansionUserInfoUtil.getUserInfo();
        if (result.hasErrors() || userInfo == null) {
            // 最新の日付を表示
            return villageAssist.setIndexModelAndReturnView(villageId, sayForm, null, null, model);
        }
        // 発言権利がなかったらNG
        if (!isAvailableSay(villageId, userInfo, sayForm)) {
            // 最新の日付を表示
            return villageAssist.setIndexModelAndReturnView(villageId, sayForm, null, null, model);
        }

        int day = villageAssist.selectLatestDay(villageId);
        VillagePlayer villagePlayer = villageAssist.selectVillagePlayer(villageId, userInfo, true).orElseThrow(() -> {
            return new IllegalArgumentException("セッション切れ？");
        });
        MessageType type = CDef.MessageType.codeOf(sayForm.getMessageType());
        if (type == null) {
            throw new IllegalArgumentException("発言種別改ざん");
        }
        // 登録
        try {
            messageLogic.insertMessage(villageId, day, type, sayForm.getMessage(), villagePlayer.getVillagePlayerId());
        } catch (WerewolfMansionBusinessException e) {
            model.addAttribute("sayErrorMessage", e.getMessage());
        }

        // 最新の日付を表示
        return "redirect:/village/" + villageId + "#bottom";
    }

    // ===================================================================================
    //                                                                              Select
    //                                                                              ======
    private Village selectVillage(Integer villageId) {
        Village village = villageBhv.selectEntityWithDeletedCheck(cb -> {
            cb.setupSelect_VillageSettingsAsOne().withCharaGroup();
            cb.query().setVillageId_Equal(villageId);
        });
        villageBhv.loadVillagePlayer(village, villagePlayerCB -> { // 参加者一覧用
            villagePlayerCB.setupSelect_Chara();
            villagePlayerCB.setupSelect_SkillBySkillCode();
            villagePlayerCB.setupSelect_DeadReason();
            villagePlayerCB.query().setIsGone_Equal_False();
            villagePlayerCB.query().addOrderBy_DeadDay_Asc();
        });
        return village;
    }

    // ===================================================================================
    //                                                                        Assist Logic
    //                                                                        ============
    // 発言可能かチェック
    private boolean isAvailableSay(Integer villageId, UserInfo userInfo, VillageSayForm sayForm) {
        CDef.MessageType type = CDef.MessageType.codeOf(sayForm.getMessageType());
        if (type == null) {
            throw new IllegalArgumentException("発言種別が改ざんされている");
        }
        Village village = selectVillage(villageId);
        VillagePlayer villagePlayer = villageAssist.selectVillagePlayer(villageId, userInfo, true).orElseThrow(() -> {
            return new IllegalArgumentException("セッション切れ？");
        });
        // 管理者は発言可能
        if (userInfo.getAuthorities().stream().anyMatch(a -> a.equals(new SimpleGrantedAuthority("ROLE_ADMIN")))) {
            return true;
        }
        switch (type) {
        case 人狼の囁き:
            return isAvailableWerewolfSay(village, villagePlayer);
        case 通常発言:
            return isAvailableNormalSay(village, villagePlayer);
        case 共鳴発言:
            return isAvailableMasonSay(village, villagePlayer);
        case 死者の呻き:
            return isAvailableGraveSay(village, villagePlayer);
        case 見学発言:
            return isAvailableSpectateSay(village, villagePlayer);
        case 独り言:
            return isAvailableMonologueSay(village);
        default:
            throw new IllegalArgumentException("発言種別が改ざんされている type: " + type);
        }
    }

    private boolean isAvailableNormalSay(Village village, VillagePlayer villagePlayer) {
        // エピローグ以外で死亡している場合は不可
        if (BooleanUtils.isTrue(villagePlayer.getIsDead()) && !village.isVillageStatusCodeエピローグ()) {
            return false;
        }
        return true;
    }

    private boolean isAvailableWerewolfSay(Village village, VillagePlayer villagePlayer) {
        // 狼とC狂以外は不可
        CDef.Skill skill = villagePlayer.getSkillCodeAsSkill();
        if (skill != CDef.Skill.人狼 && skill != CDef.Skill.C国狂人) {
            return false;
        }
        // 死亡していたら不可
        if (BooleanUtils.isTrue(villagePlayer.getIsDead())) {
            return false;
        }
        // 進行中以外は不可
        if (!village.isVillageStatusCode進行中()) {
            return false;
        }
        return true;
    }

    private boolean isAvailableMasonSay(Village village, VillagePlayer villagePlayer) {
        // 共有以外は不可
        CDef.Skill skill = villagePlayer.getSkillCodeAsSkill();
        if (skill != CDef.Skill.共鳴者) {
            return false;
        }
        // 死亡していたら不可
        if (BooleanUtils.isTrue(villagePlayer.getIsDead())) {
            return false;
        }
        // 進行中以外は不可
        if (!village.isVillageStatusCode進行中()) {
            return false;
        }
        return true;
    }

    private boolean isAvailableGraveSay(Village village, VillagePlayer villagePlayer) {
        // 死亡していなかったら不可
        if (!BooleanUtils.isTrue(villagePlayer.getIsDead())) {
            return false;
        }
        // 進行中以外は不可
        if (!village.isVillageStatusCode進行中()) {
            return false;
        }
        return true;
    }

    private boolean isAvailableSpectateSay(Village village, VillagePlayer villagePlayer) {
        // 見学していなかったら不可
        if (!BooleanUtils.isTrue(villagePlayer.getIsSpectator())) {
            return false;
        }
        return true;
    }

    private boolean isAvailableMonologueSay(Village village) {
        // いつでも可能
        return true;
    }

}
