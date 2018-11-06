package com.ort.app.web.controller.assist;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.BooleanUtils;
import org.dbflute.optional.OptionalEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import com.ort.app.web.controller.logic.MessageLogic;
import com.ort.app.web.exception.WerewolfMansionBusinessException;
import com.ort.app.web.form.VillageSayForm;
import com.ort.app.web.util.CharaUtil;
import com.ort.dbflute.allcommon.CDef;
import com.ort.dbflute.allcommon.CDef.MessageType;
import com.ort.dbflute.exbhv.MessageRestrictionBhv;
import com.ort.dbflute.exbhv.RandomKeywordBhv;
import com.ort.dbflute.exbhv.VillageBhv;
import com.ort.dbflute.exbhv.VillagePlayerBhv;
import com.ort.dbflute.exentity.Message;
import com.ort.dbflute.exentity.MessageRestriction;
import com.ort.dbflute.exentity.RandomKeyword;
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
    private VillagePlayerBhv villagePlayerBhv;
    @Autowired
    private VillageAssist villageAssist;
    @Autowired
    private RandomKeywordBhv randomKeywordBhv;
    @Autowired
    private MessageRestrictionBhv messageRestrictionBhv;
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
        Village village = selectVillage(villageId);
        VillagePlayer villagePlayer = villageAssist.selectVillagePlayer(villageId, userInfo, true).orElseThrow(() -> {
            return new IllegalArgumentException("セッション切れ？");
        });
        // 発言権利がなかったらNG
        if (!isAvailableSay(villageId, village, villagePlayer, userInfo, sayForm)) {
            // 最新の日付を表示
            return villageAssist.setIndexModelAndReturnView(villageId, sayForm, null, null, model);
        }
        // 発言制限に引っかかったらNG
        if (isRestricted(villageId, village, villagePlayer, sayForm)) {
            // 最新の日付を表示
            return villageAssist.setIndexModelAndReturnView(villageId, sayForm, null, null, model);
        }
        CDef.FaceType faceType = CDef.FaceType.codeOf(sayForm.getFaceType());
        if (faceType == null) {
            throw new IllegalArgumentException("表情種別が改ざんされている");
        }
        model.addAttribute("characterImgUrl", CharaUtil.getCharaImgUrlByFaceType(villagePlayer.getChara().get(), faceType));
        model.addAttribute("characterImgWidth", villagePlayer.getChara().get().getDisplayWidth());
        model.addAttribute("characterImgHeight", villagePlayer.getChara().get().getDisplayHeight());
        model.addAttribute("randomKeywords", String.join(",",
                randomKeywordBhv.selectList(cb -> {}).stream().map(RandomKeyword::getKeyword).collect(Collectors.toList())));
        model.addAttribute("secretSayTargetCharaName", createSecretSayTargetCharaName(villageId, sayForm));

        model.addAttribute("villageId", villageId);
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
        Village village = selectVillage(villageId);
        VillagePlayer villagePlayer = villageAssist.selectVillagePlayer(villageId, userInfo, true).orElseThrow(() -> {
            return new IllegalArgumentException("セッション切れ？");
        });
        // 発言権利がなかったらNG
        if (!isAvailableSay(villageId, village, villagePlayer, userInfo, sayForm)) {
            // 最新の日付を表示
            return villageAssist.setIndexModelAndReturnView(villageId, sayForm, null, null, model);
        }
        // 発言制限に引っかかったらNG
        if (isRestricted(villageId, village, villagePlayer, sayForm)) {
            // 最新の日付を表示
            return villageAssist.setIndexModelAndReturnView(villageId, sayForm, null, null, model);
        }

        int day = villageAssist.selectLatestDay(villageId);
        MessageType type = CDef.MessageType.codeOf(sayForm.getMessageType());
        CDef.FaceType faceType = CDef.FaceType.codeOf(sayForm.getFaceType());
        if (type == null || faceType == null) {
            throw new IllegalArgumentException("発言種別改ざん");
        }
        Integer targetVillagePlayerId = sayForm.getMessageType().equals(CDef.MessageType.秘話.code())
                ? selectTargetVillagePlayer(villageId, sayForm).getVillagePlayerId()
                : null;
        // 登録
        try {
            messageLogic.insertMessage(villageId, day, type, sayForm.getMessage(), villagePlayer.getVillagePlayerId(),
                    targetVillagePlayerId, BooleanUtils.isTrue(sayForm.getIsConvertDisable()), faceType);
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

    private VillagePlayer selectTargetVillagePlayer(Integer villageId, VillageSayForm sayForm) {
        return villagePlayerBhv.selectEntityWithDeletedCheck(cb -> {
            cb.setupSelect_Chara();
            cb.query().setVillageId_Equal(villageId);
            cb.query().setIsGone_Equal_False();
            cb.query().setCharaId_Equal(sayForm.getSecretSayTargetCharaId());
        });
    }

    // ===================================================================================
    //                                                                        Assist Logic
    //                                                                        ============
    // 発言可能かチェック
    private boolean isAvailableSay(Integer villageId, Village village, VillagePlayer villagePlayer, UserInfo userInfo,
            VillageSayForm sayForm) {
        CDef.MessageType type = CDef.MessageType.codeOf(sayForm.getMessageType());
        if (type == null) {
            throw new IllegalArgumentException("発言種別が改ざんされている");
        }
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
        case 秘話:
            return isAvailableSecretSay(village, villagePlayer, sayForm.getSecretSayTargetCharaId());
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

    private boolean isAvailableSecretSay(Village village, VillagePlayer villagePlayer, Integer targetCharaId) {
        // 進行中以外は不可
        if (!village.isVillageStatusCode進行中()) {
            return false;
        }
        // 秘話不可設定の場合は不可
        if (village.getVillageSettingsAsOne().get().getAllowedSecretSayCodeAsAllowedSecretSay() == CDef.AllowedSecretSay.なし) {
            return false;
        }
        // 相手がこの村に参加していなかったら不可
        if (targetCharaId == null) {
            return false;
        }
        OptionalEntity<VillagePlayer> optTarget = villagePlayerBhv.selectEntity(cb -> {
            cb.query().setVillageId_Equal(village.getVillageId());
            cb.query().setIsGone_Equal_False();
            cb.query().setCharaId_Equal(targetCharaId);
        });
        if (!optTarget.isPresent()) {
            return false;
        }
        return true;
    }

    private String createSecretSayTargetCharaName(Integer villageId, VillageSayForm sayForm) {
        if (!sayForm.getMessageType().equals(CDef.MessageType.秘話.code())) {
            return null;
        }
        return selectTargetVillagePlayer(villageId, sayForm).getChara().get().getCharaName();
    }

    private boolean isRestricted(Integer villageId, Village village, VillagePlayer vPlayer, VillageSayForm sayForm) {
        if (!village.isVillageStatusCode進行中()) {
            return false; // 制限するのは進行中のみ
        }
        CDef.MessageType type = CDef.MessageType.codeOf(sayForm.getMessageType());
        if (type != CDef.MessageType.通常発言 && type != CDef.MessageType.人狼の囁き && type != CDef.MessageType.共鳴発言) {
            return false;
        }
        OptionalEntity<MessageRestriction> optRestrict = messageRestrictionBhv.selectEntity(cb -> {
            cb.query().setVillageId_Equal(villageId);
            cb.query().setMessageTypeCode_Equal_AsMessageType(type);
            cb.query().setSkillCode_Equal_AsSkill(vPlayer.getSkillCodeAsSkill());
        });
        if (!optRestrict.isPresent()) {
            return false; // 制限なし
        }
        MessageRestriction restrict = optRestrict.get();
        Integer maxLength = restrict.getMessageMaxLength();
        if (sayForm.getMessage().length() > maxLength) {
            return true; // 文字数オーバー
        }
        // 最新の日付
        int latestDay = villageAssist.selectLatestDay(villageId);
        List<Message> messageList = messageLogic.selectDayPersonMessage(villageId, latestDay, vPlayer.getVillagePlayerId());
        int count = (int) messageList.stream().filter(m -> m.getMessageTypeCodeAsMessageType() == type).count();
        Integer maxNum = restrict.getMessageMaxNum();
        if (maxNum <= count) {
            return true; // 制限回数オーバー
        }
        return false;
    }
}
