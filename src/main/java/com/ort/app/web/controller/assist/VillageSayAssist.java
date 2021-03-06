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
import com.ort.app.logic.message.MessageEntity;
import com.ort.app.web.controller.assist.impl.VillageForms;
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
        // ????????????????????????????????????NG
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
        // ??????????????????????????????NG
        if (!isAvailableSay(villageId, village, villagePlayer, userInfo, sayForm)) {
            return null;
        }
        // ????????????????????????????????????NG
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
        // ????????????????????????????????????NG
        UserInfo userInfo = WerewolfMansionUserInfoUtil.getUserInfo();
        if (result.hasErrors() || userInfo == null) {
            // ????????????????????????
            return villageAssist.setIndexModelAndReturnView(villageId, new VillageForms.Builder().sayForm(sayForm).build(), model);
        }
        Village village = selectVillage(villageId);
        VillagePlayer villagePlayer = villageService.selectVillagePlayer(villageId, userInfo, true).orElseThrow(() -> {
            return new IllegalArgumentException("????????????????????????");
        });
        // ??????????????????????????????NG
        if (!isAvailableSay(villageId, village, villagePlayer, userInfo, sayForm)) {
            // ????????????????????????
            return villageAssist.setIndexModelAndReturnView(villageId, new VillageForms.Builder().sayForm(sayForm).build(), model);
        }
        // ????????????????????????????????????NG
        if (isRestricted(villageId, village, villagePlayer, sayForm, userInfo)) {
            // ????????????????????????
            return villageAssist.setIndexModelAndReturnView(villageId, new VillageForms.Builder().sayForm(sayForm).build(), model);
        }

        int day = villageService.selectLatestDay(villageId);
        MessageType type = CDef.MessageType.codeOf(sayForm.getMessageType());
        CDef.FaceType faceType = CDef.FaceType.codeOf(sayForm.getFaceType());
        if (type != CDef.MessageType.??????????????? && faceType == null) {
            throw new IllegalArgumentException("?????????????????????");
        }
        VillagePlayer targetVillagePlayer =
                sayForm.getMessageType().equals(CDef.MessageType.??????.code()) ? selectTargetVillagePlayer(villageId, sayForm) : null;
        // ??????
        try {
            messageLogic.save(new MessageEntity.Builder(villageId, day) //
                    .messageType(type)
                    .content(sayForm.getMessage())
                    .villagePlayer(villagePlayer)
                    .targetVillagePlayer(targetVillagePlayer)
                    .isConvertDisable(BooleanUtils.isTrue(sayForm.getIsConvertDisable()))
                    .faceType(faceType)
                    .build());
        } catch (WerewolfMansionBusinessException e) {
            model.addAttribute("sayErrorMessage", e.getMessage());
        }

        // ????????????????????????
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
        villageBhv.loadVillagePlayer(village, villagePlayerCB -> { // ??????????????????
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
    // ???????????????????????????
    private boolean isAvailableSay(Integer villageId, Village village, VillagePlayer villagePlayer, UserInfo userInfo,
            VillageSayForm sayForm) {
        CDef.MessageType type = CDef.MessageType.codeOf(sayForm.getMessageType());
        if (type == null) {
            throw new IllegalArgumentException("???????????????????????????????????????");
        }
        // ????????????????????????
        if (userInfo.getAuthorities().stream().anyMatch(a -> a.equals(new SimpleGrantedAuthority("ROLE_ADMIN")))) {
            return true;
        }
        switch (type) {
        case ???????????????:
            return isAvailableWerewolfSay(village, villagePlayer);
        case ????????????:
            return isAvailableNormalSay(village, villagePlayer);
        case ????????????:
            return isAvailableMasonSay(village, villagePlayer);
        case ????????????:
            return isAvailableLoversSay(village, villagePlayer);
        case ???????????????:
            return isAvailableGraveSay(village, villagePlayer);
        case ????????????:
            return isAvailableSpectateSay(village, villagePlayer);
        case ?????????:
            return isAvailableMonologueSay(village);
        case ??????:
            return isAvailableSecretSay(village, villagePlayer, sayForm.getSecretSayTargetCharaId());
        default:
            throw new IllegalArgumentException("??????????????????????????????????????? type: " + type);
        }
    }

    private boolean isAvailableNormalSay(Village village, VillagePlayer villagePlayer) {
        // ?????????????????????????????????????????????????????????
        if (BooleanUtils.isTrue(villagePlayer.getIsDead()) && !village.isVillageStatusCode???????????????()) {
            return false;
        }
        return true;
    }

    private boolean isAvailableWerewolfSay(Village village, VillagePlayer villagePlayer) {
        // ??????C??????????????????
        CDef.Skill skill = villagePlayer.getSkillCodeAsSkill();
        if (skill == null || !skill.isAvailableWerewolfSay()) {
            return false;
        }
        // ???????????????????????????
        if (BooleanUtils.isTrue(villagePlayer.getIsDead())) {
            return false;
        }
        // ????????????????????????
        if (!village.isVillageStatusCode?????????()) {
            return false;
        }
        return true;
    }

    private boolean isAvailableMasonSay(Village village, VillagePlayer villagePlayer) {
        // ?????????????????????
        CDef.Skill skill = villagePlayer.getSkillCodeAsSkill();
        if (skill != CDef.Skill.?????????) {
            return false;
        }
        // ???????????????????????????
        if (BooleanUtils.isTrue(villagePlayer.getIsDead())) {
            return false;
        }
        // ????????????????????????
        if (!village.isVillageStatusCode?????????()) {
            return false;
        }
        return true;
    }

    private boolean isAvailableLoversSay(Village village, VillagePlayer villagePlayer) {
        // ????????????????????????????????????????????????
        if (!villagePlayer.hasLover()) {
            return false;
        }
        // ???????????????????????????
        if (BooleanUtils.isTrue(villagePlayer.getIsDead())) {
            return false;
        }
        // ????????????????????????
        if (!village.isVillageStatusCode?????????()) {
            return false;
        }
        return true;
    }

    private boolean isAvailableGraveSay(Village village, VillagePlayer villagePlayer) {
        // ????????????????????????????????????
        if (!BooleanUtils.isTrue(villagePlayer.getIsDead())) {
            return false;
        }
        // ????????????????????????
        if (!village.isVillageStatusCode?????????()) {
            return false;
        }
        return true;
    }

    private boolean isAvailableSpectateSay(Village village, VillagePlayer villagePlayer) {
        // ????????????????????????????????????
        if (!BooleanUtils.isTrue(villagePlayer.getIsSpectator())) {
            return false;
        }
        return true;
    }

    private boolean isAvailableMonologueSay(Village village) {
        // ??????????????????
        return true;
    }

    private boolean isAvailableSecretSay(Village village, VillagePlayer villagePlayer, Integer targetCharaId) {
        // ????????????????????????
        //        if (!village.isVillageStatusCode?????????()) {
        //            return false;
        //        }
        // ????????????????????????????????????
        if (village.getVillageSettingsAsOne().get().getAllowedSecretSayCodeAsAllowedSecretSay() == CDef.AllowedSecretSay.??????) {
            return false;
        }
        // ?????????????????????????????????????????????????????????
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
        if (!sayForm.getMessageType().equals(CDef.MessageType.??????.code())) {
            return null;
        }
        return selectTargetVillagePlayer(villageId, sayForm).name();
    }

    private boolean isRestricted(Integer villageId, Village village, VillagePlayer vPlayer, VillageSayForm sayForm, UserInfo userInfo) {
        if (!village.isVillageStatusCode?????????()) {
            return false; // ?????????????????????????????????
        }
        // ????????????????????????
        if (userInfo.getAuthorities().stream().anyMatch(a -> a.equals(new SimpleGrantedAuthority("ROLE_ADMIN")))) {
            return false;
        }
        CDef.MessageType type = CDef.MessageType.codeOf(sayForm.getMessageType());
        if (type != CDef.MessageType.???????????? && type != CDef.MessageType.??????????????? && type != CDef.MessageType.????????????
                && type != CDef.MessageType.????????????) {
            return false;
        }
        Integer maxLength = 0;
        Integer maxNum = 0;
        if (type == CDef.MessageType.????????????) {
            OptionalEntity<NormalSayRestriction> optRestrict = normalSayRestrictionBhv.selectEntity(cb -> {
                cb.query().setVillageId_Equal(villageId);
                cb.query().setMessageTypeCode_Equal_AsMessageType(type);
                cb.query().setSkillCode_Equal_AsSkill(vPlayer.getSkillCodeAsSkill());
            });
            if (!optRestrict.isPresent()) {
                return false; // ????????????
            }
            maxLength = optRestrict.get().getMessageMaxLength();
            maxNum = optRestrict.get().getMessageMaxNum();
        } else {
            OptionalEntity<SkillSayRestriction> optRestrict = skillSayRestrictionBhv.selectEntity(cb -> {
                cb.query().setVillageId_Equal(villageId);
                cb.query().setMessageTypeCode_Equal_AsMessageType(type);
            });
            if (!optRestrict.isPresent()) {
                return false; // ????????????
            }
            maxLength = optRestrict.get().getMessageMaxLength();
            maxNum = optRestrict.get().getMessageMaxNum();
        }
        Integer messageLength = sayForm.getMessage().replace("\r\n", "\n").length();
        if (messageLength > maxLength) {
            return true; // ?????????????????????
        }
        // ???????????????
        int latestDay = villageService.selectLatestDay(villageId);
        List<Message> messageList = messageLogic.selectDayPersonMessage(villageId, latestDay, vPlayer.getVillagePlayerId());
        int count = (int) messageList.stream().filter(m -> m.getMessageTypeCodeAsMessageType() == type).count();
        if (maxNum <= count) {
            return true; // ????????????????????????
        }
        return false;
    }
}
