package com.ort.app.web.controller.assist;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.BooleanUtils;
import org.dbflute.optional.OptionalEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import com.ort.app.datasource.VillageService;
import com.ort.app.logic.MessageLogic;
import com.ort.app.web.exception.WerewolfMansionBusinessException;
import com.ort.app.web.form.VillageSayForm;
import com.ort.app.web.model.VillageSayConfirmResultContent;
import com.ort.app.web.model.inner.VillageMessageDto;
import com.ort.dbflute.allcommon.CDef;
import com.ort.dbflute.allcommon.CDef.MessageType;
import com.ort.dbflute.exbhv.NormalSayRestrictionBhv;
import com.ort.dbflute.exbhv.RandomKeywordBhv;
import com.ort.dbflute.exbhv.SkillSayRestrictionBhv;
import com.ort.dbflute.exbhv.VillageBhv;
import com.ort.dbflute.exbhv.VillagePlayerBhv;
import com.ort.dbflute.exentity.Chara;
import com.ort.dbflute.exentity.Message;
import com.ort.dbflute.exentity.NormalSayRestriction;
import com.ort.dbflute.exentity.RandomKeyword;
import com.ort.dbflute.exentity.SkillSayRestriction;
import com.ort.dbflute.exentity.Village;
import com.ort.dbflute.exentity.VillagePlayer;
import com.ort.fw.security.UserInfo;
import com.ort.fw.util.WerewolfMansionDateUtil;
import com.ort.fw.util.WerewolfMansionUserInfoUtil;

@Component
public class VillageSayAssist {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    @Autowired
    private VillageAssist villageAssist;
    @Autowired
    private MessageLogic messageLogic;
    @Autowired
    private VillageService villageService;
    @Autowired
    private RandomKeywordBhv randomKeywordBhv;
    @Autowired
    private NormalSayRestrictionBhv normalSayRestrictionBhv;
    @Autowired
    private SkillSayRestrictionBhv skillSayRestrictionBhv;
    @Autowired
    private VillageBhv villageBhv;
    @Autowired
    private VillagePlayerBhv villagePlayerBhv;

    // ===================================================================================
    //                                                                             Execute
    //                                                                             =======
    public VillageSayConfirmResultContent sayConfirm(Integer villageId, VillageSayForm sayForm, BindingResult result, Model model) {
        // ログインしていなかったらNG
        UserInfo userInfo = WerewolfMansionUserInfoUtil.getUserInfo();
        if (result.hasErrors() || userInfo == null) {
            return null;
        }
        Optional<VillagePlayer> optVp = villageService.selectVillagePlayer(villageId, userInfo, true);
        if (!optVp.isPresent()) {
            return null;
        }
        VillagePlayer villagePlayer = optVp.get();

        Village village = selectVillage(villageId);
        // 発言権利がなかったらNG
        if (!isAvailableSay(villageId, village, villagePlayer, userInfo, sayForm)) {
            return null;
        }
        // 発言制限に引っかかったらNG
        if (isRestricted(villageId, village, villagePlayer, sayForm, userInfo)) {
            return null;
        }
        CDef.FaceType faceType = CDef.FaceType.codeOf(sayForm.getFaceType());
        if (faceType == null) {
            return null;
        }
        VillageSayConfirmResultContent content = new VillageSayConfirmResultContent();
        VillageMessageDto message = new VillageMessageDto();
        Chara chara = villagePlayer.getChara().get();
        message.setCharacterName(villagePlayer.name());
        message.setCharacterId(chara.getCharaId());
        message.setCharacterImageUrl(chara.getCharaImgUrlByFaceType(faceType));
        message.setMessageType(sayForm.getMessageType());
        message.setMessageNumber(0);
        message.setMessageContent(sayForm.getMessage());
        message.setMessageDatetime(WerewolfMansionDateUtil.currentLocalDateTime());
        message.setWidth(chara.getDisplayWidth());
        message.setHeight(chara.getDisplayHeight());
        message.setIsConvertDisable(sayForm.getIsConvertDisable());
        message.setTargetCharacterName(createSecretSayTargetCharaName(villageId, sayForm));
        content.setMessage(message);
        content.setRandomKeywords(String.join(",",
                randomKeywordBhv.selectList(cb -> {}).stream().map(RandomKeyword::getKeyword).collect(Collectors.toList())));
        return content;
    }

    public String say(Integer villageId, VillageSayForm sayForm, BindingResult result, Model model) {
        // ログインしていなかったらNG
        UserInfo userInfo = WerewolfMansionUserInfoUtil.getUserInfo();
        if (result.hasErrors() || userInfo == null) {
            // 最新の日付を表示
            return villageAssist.setIndexModelAndReturnView(villageId, sayForm, null, null, null, model);
        }
        Village village = selectVillage(villageId);
        VillagePlayer villagePlayer = villageService.selectVillagePlayer(villageId, userInfo, true).orElseThrow(() -> {
            return new IllegalArgumentException("セッション切れ？");
        });
        // 発言権利がなかったらNG
        if (!isAvailableSay(villageId, village, villagePlayer, userInfo, sayForm)) {
            // 最新の日付を表示
            return villageAssist.setIndexModelAndReturnView(villageId, sayForm, null, null, null, model);
        }
        // 発言制限に引っかかったらNG
        if (isRestricted(villageId, village, villagePlayer, sayForm, userInfo)) {
            // 最新の日付を表示
            return villageAssist.setIndexModelAndReturnView(villageId, sayForm, null, null, null, model);
        }

        int day = villageService.selectLatestDay(villageId);
        MessageType type = CDef.MessageType.codeOf(sayForm.getMessageType());
        CDef.FaceType faceType = CDef.FaceType.codeOf(sayForm.getFaceType());
        if (type != CDef.MessageType.アクション && faceType == null) {
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
            if (type == CDef.MessageType.アクション) {
                model.addAttribute("actionErrorMessage", e.getMessage());
            } else {
                model.addAttribute("sayErrorMessage", e.getMessage());
            }
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
        case 恋人発言:
            return isAvailableLoversSay(village, villagePlayer);
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
        if (skill == null || !skill.isAvailableWerewolfSay()) {
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

    private boolean isAvailableLoversSay(Village village, VillagePlayer villagePlayer) {
        // 恋絆が付与されている人以外は不可
        if (!villagePlayer.hasLover()) {
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
        //        if (!village.isVillageStatusCode進行中()) {
        //            return false;
        //        }
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

    private boolean isRestricted(Integer villageId, Village village, VillagePlayer vPlayer, VillageSayForm sayForm, UserInfo userInfo) {
        if (!village.isVillageStatusCode進行中()) {
            return false; // 制限するのは進行中のみ
        }
        // 管理者は発言可能
        if (userInfo.getAuthorities().stream().anyMatch(a -> a.equals(new SimpleGrantedAuthority("ROLE_ADMIN")))) {
            return false;
        }
        CDef.MessageType type = CDef.MessageType.codeOf(sayForm.getMessageType());
        if (type != CDef.MessageType.通常発言 && type != CDef.MessageType.人狼の囁き && type != CDef.MessageType.共鳴発言
                && type != CDef.MessageType.恋人発言) {
            return false;
        }
        Integer maxLength = 0;
        Integer maxNum = 0;
        if (type == CDef.MessageType.通常発言) {
            OptionalEntity<NormalSayRestriction> optRestrict = normalSayRestrictionBhv.selectEntity(cb -> {
                cb.query().setVillageId_Equal(villageId);
                cb.query().setMessageTypeCode_Equal_AsMessageType(type);
                cb.query().setSkillCode_Equal_AsSkill(vPlayer.getSkillCodeAsSkill());
            });
            if (!optRestrict.isPresent()) {
                return false; // 制限なし
            }
            maxLength = optRestrict.get().getMessageMaxLength();
            maxNum = optRestrict.get().getMessageMaxNum();
        } else {
            OptionalEntity<SkillSayRestriction> optRestrict = skillSayRestrictionBhv.selectEntity(cb -> {
                cb.query().setVillageId_Equal(villageId);
                cb.query().setMessageTypeCode_Equal_AsMessageType(type);
            });
            if (!optRestrict.isPresent()) {
                return false; // 制限なし
            }
            maxLength = optRestrict.get().getMessageMaxLength();
            maxNum = optRestrict.get().getMessageMaxNum();
        }
        Integer messageLength = sayForm.getMessage().replace("\r\n", "\n").length();
        if (messageLength > maxLength) {
            return true; // 文字数オーバー
        }
        // 最新の日付
        int latestDay = villageService.selectLatestDay(villageId);
        List<Message> messageList = messageLogic.selectDayPersonMessage(villageId, latestDay, vPlayer.getVillagePlayerId());
        int count = (int) messageList.stream().filter(m -> m.getMessageTypeCodeAsMessageType() == type).count();
        if (maxNum <= count) {
            return true; // 制限回数オーバー
        }
        return false;
    }
}
