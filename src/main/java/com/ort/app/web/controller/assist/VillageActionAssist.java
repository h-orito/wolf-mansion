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
import com.ort.app.web.form.VillageActionForm;
import com.ort.app.web.model.VillageSayConfirmResultContent;
import com.ort.app.web.model.inner.VillageMessageDto;
import com.ort.dbflute.allcommon.CDef;
import com.ort.dbflute.exbhv.RandomKeywordBhv;
import com.ort.dbflute.exbhv.SkillSayRestrictionBhv;
import com.ort.dbflute.exbhv.VillageBhv;
import com.ort.dbflute.exentity.Chara;
import com.ort.dbflute.exentity.Message;
import com.ort.dbflute.exentity.RandomKeyword;
import com.ort.dbflute.exentity.SkillSayRestriction;
import com.ort.dbflute.exentity.Village;
import com.ort.dbflute.exentity.VillagePlayer;
import com.ort.fw.security.UserInfo;
import com.ort.fw.util.WerewolfMansionDateUtil;
import com.ort.fw.util.WerewolfMansionUserInfoUtil;

@Component
public class VillageActionAssist {

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
    private SkillSayRestrictionBhv skillSayRestrictionBhv;
    @Autowired
    private VillageBhv villageBhv;

    // ===================================================================================
    //                                                                             Execute
    //                                                                             =======
    public VillageSayConfirmResultContent actionConfirm(Integer villageId, VillageActionForm actionForm, BindingResult result,
            Model model) {
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
        if (!isAvailableAction(villageId, village, villagePlayer, userInfo, actionForm)) {
            return null;
        }
        // 発言制限に引っかかったらNG
        if (isRestricted(villageId, village, villagePlayer, actionForm, userInfo)) {
            return null;
        }
        VillageSayConfirmResultContent content = new VillageSayConfirmResultContent();
        VillageMessageDto message = new VillageMessageDto();
        Chara chara = villagePlayer.getChara().get();
        message.setCharacterName(villagePlayer.name());
        message.setCharacterId(chara.getCharaId());
        message.setMessageType(CDef.MessageType.アクション.code());
        message.setMessageNumber(0);
        String targetMessage = actionForm.getTarget() == null ? "" : actionForm.getTarget();
        String messageContent = actionForm.getMyself() + targetMessage + actionForm.getMessage();
        message.setMessageContent(messageContent);
        message.setMessageDatetime(WerewolfMansionDateUtil.currentLocalDateTime());
        message.setIsConvertDisable(actionForm.getIsConvertDisable());
        content.setMessage(message);
        content.setRandomKeywords(String.join(",",
                randomKeywordBhv.selectList(cb -> {}).stream().map(RandomKeyword::getKeyword).collect(Collectors.toList())));
        return content;
    }

    public String action(Integer villageId, VillageActionForm actionForm, BindingResult result, Model model) {
        // ログインしていなかったらNG
        UserInfo userInfo = WerewolfMansionUserInfoUtil.getUserInfo();
        if (result.hasErrors() || userInfo == null) {
            // 最新の日付を表示
            return villageAssist.setIndexModelAndReturnView(villageId, null, actionForm, null, null, model);
        }
        Village village = selectVillage(villageId);
        VillagePlayer villagePlayer = villageService.selectVillagePlayer(villageId, userInfo, true).orElseThrow(() -> {
            return new IllegalArgumentException("セッション切れ？");
        });
        // 発言権利がなかったらNG
        if (!isAvailableAction(villageId, village, villagePlayer, userInfo, actionForm)) {
            // 最新の日付を表示
            return villageAssist.setIndexModelAndReturnView(villageId, null, actionForm, null, null, model);
        }
        // 発言制限に引っかかったらNG
        if (isRestricted(villageId, village, villagePlayer, actionForm, userInfo)) {
            // 最新の日付を表示
            return villageAssist.setIndexModelAndReturnView(villageId, null, actionForm, null, null, model);
        }

        int day = villageService.selectLatestDay(villageId);
        // 登録
        try {
            String targetMessage = actionForm.getTarget() == null ? "" : actionForm.getTarget();
            String messageContent = actionForm.getMyself() + targetMessage + actionForm.getMessage();
            messageLogic.insertMessage(villageId, day, CDef.MessageType.アクション, messageContent, villagePlayer.getVillagePlayerId(), null,
                    BooleanUtils.isTrue(actionForm.getIsConvertDisable()), null);
        } catch (WerewolfMansionBusinessException e) {
            model.addAttribute("actionErrorMessage", e.getMessage());
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
    private boolean isAvailableAction(Integer villageId, Village village, VillagePlayer villagePlayer, UserInfo userInfo,
            VillageActionForm actionForm) {
        // 管理者は発言可能
        if (userInfo.getAuthorities().stream().anyMatch(a -> a.equals(new SimpleGrantedAuthority("ROLE_ADMIN")))) {
            return true;
        }
        return isAvailableAction(village);
    }

    private boolean isAvailableAction(Village village) {
        // アクション不可設定の場合は不可
        return village.getVillageSettingsAsOne().get().isIsAvailableActionTrue();
    }

    private boolean isRestricted(Integer villageId, Village village, VillagePlayer vPlayer, VillageActionForm actionForm,
            UserInfo userInfo) {
        if (!village.isVillageStatusCode進行中()) {
            return false; // 制限するのは進行中のみ
        }
        // 管理者は発言可能
        if (userInfo.getAuthorities().stream().anyMatch(a -> a.equals(new SimpleGrantedAuthority("ROLE_ADMIN")))) {
            return false;
        }
        Integer maxLength = 0;
        Integer maxNum = 0;
        OptionalEntity<SkillSayRestriction> optRestrict = skillSayRestrictionBhv.selectEntity(cb -> {
            cb.query().setVillageId_Equal(villageId);
            cb.query().setMessageTypeCode_Equal_アクション();
        });
        if (!optRestrict.isPresent()) {
            return false; // 制限なし
        }
        maxLength = optRestrict.get().getMessageMaxLength();
        maxNum = optRestrict.get().getMessageMaxNum();
        int myselfLength = actionForm.getMyself().length();
        int targetLength = actionForm.getTarget() == null ? 0 : actionForm.getTarget().length();
        Integer messageLength = myselfLength + targetLength + actionForm.getMessage().length();
        if (messageLength > maxLength) {
            return true; // 文字数オーバー
        }
        // 最新の日付
        int latestDay = villageService.selectLatestDay(villageId);
        List<Message> messageList = messageLogic.selectDayPersonMessage(villageId, latestDay, vPlayer.getVillagePlayerId());
        int count = (int) messageList.stream().filter(m -> m.isMessageTypeCodeアクション()).count();
        if (maxNum <= count) {
            return true; // 制限回数オーバー
        }
        return false;
    }
}
