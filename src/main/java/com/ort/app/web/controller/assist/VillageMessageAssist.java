package com.ort.app.web.controller.assist;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import org.dbflute.cbean.result.ListResultBean;
import org.dbflute.optional.OptionalEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import com.ort.app.web.form.VillageGetAnchorMessageForm;
import com.ort.app.web.form.VillageGetMessageListForm;
import com.ort.app.web.model.VillageAnchorMessageResultContent;
import com.ort.app.web.model.VillageMessageListResultContent;
import com.ort.app.web.model.inner.VillageMessageDto;
import com.ort.app.web.util.SkillUtil;
import com.ort.dbflute.allcommon.CDef;
import com.ort.dbflute.allcommon.CDef.MessageType;
import com.ort.dbflute.exbhv.MessageBhv;
import com.ort.dbflute.exbhv.VillageBhv;
import com.ort.dbflute.exbhv.VillageDayBhv;
import com.ort.dbflute.exbhv.VillagePlayerBhv;
import com.ort.dbflute.exentity.Message;
import com.ort.dbflute.exentity.Village;
import com.ort.dbflute.exentity.VillageDay;
import com.ort.dbflute.exentity.VillagePlayer;
import com.ort.dbflute.exentity.VillageSettings;
import com.ort.fw.security.UserInfo;
import com.ort.fw.util.WerewolfMansionDateUtil;
import com.ort.fw.util.WerewolfMansionUserInfoUtil;

@Component
public class VillageMessageAssist {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    @Autowired
    private VillageBhv villageBhv;

    @Autowired
    private VillageDayBhv villageDayBhv;

    @Autowired
    private VillagePlayerBhv villagePlayerBhv;

    @Autowired
    private MessageBhv messageBhv;

    @Autowired
    private MessageSource messageSource;

    // ===================================================================================
    //                                                                             Execute
    //                                                                             =======
    public VillageMessageListResultContent getMessageList(VillageGetMessageListForm form, UserInfo userInfo) {
        Integer villageId = form.getVillageId();
        int latestDay = selectLatestDay(villageId);
        int day = form.getDay() != null ? form.getDay() : latestDay;
        OptionalEntity<VillagePlayer> optVillagePlayer = selectVillagePlayer(villageId, userInfo);
        Village village = selectVillage(villageId);
        List<CDef.MessageType> messageTypeList = makeMessageTypeList(optVillagePlayer, village, day);
        ListResultBean<Message> messageList = selectMessageList(form.getVillageId(), day, messageTypeList, optVillagePlayer);
        String suddonlyDeathMessage = makeSuddonlyDeathMessage(village, isLatestDay(villageId, day), day);
        String villageStatusMessage = makeVillageStatusMessage(village, isLatestDay(villageId, day), optVillagePlayer, day);
        VillageMessageListResultContent content =
                mappingToMessageListContent(village, messageList, villageStatusMessage, suddonlyDeathMessage, latestDay);
        return content;
    }

    public VillageAnchorMessageResultContent getAnchorMessage(VillageGetAnchorMessageForm form, BindingResult result) {
        if (result.hasErrors()) {
            return null;
        }
        UserInfo userInfo = WerewolfMansionUserInfoUtil.getUserInfo();
        Village village = selectVillage(form.getVillageId());
        if (!isViewAllowedMessage(form, village, userInfo)) {
            return null;
        }
        Message message = selectAnchorMessage(form);
        VillageAnchorMessageResultContent content = new VillageAnchorMessageResultContent();
        if (message == null) {
            return content;
        }
        content.setMessage(convertToMessage(village, message));
        return content;
    }

    public void updateLastAccessDatetime(Integer villageId) {
        UserInfo userInfo = WerewolfMansionUserInfoUtil.getUserInfo();
        OptionalEntity<VillagePlayer> optVillagePlayer = selectVillagePlayer(villageId, userInfo);
        if (!optVillagePlayer.isPresent()) {
            return;
        }
        VillagePlayer vp = new VillagePlayer();
        vp.setLastAccessDatetime(WerewolfMansionDateUtil.currentLocalDateTime());
        vp.setVillagePlayerId(optVillagePlayer.get().getVillagePlayerId());
        villagePlayerBhv.update(vp);
    }

    // ===================================================================================
    //                                                                              Select
    //                                                                              ======
    private ListResultBean<Message> selectMessageList(Integer villageId, Integer day, List<CDef.MessageType> messageTypeList,
            OptionalEntity<VillagePlayer> optVillagePlayer) {
        return messageBhv.selectList(cb -> {
            cb.setupSelect_VillagePlayer().withPlayer();
            cb.setupSelect_VillagePlayer().withChara();
            cb.query().setVillageId_Equal(villageId);
            cb.query().setDay_Equal(day);
            if (optVillagePlayer.isPresent()) {
                cb.orScopeQuery(orCB -> {
                    orCB.query().setMessageTypeCode_InScope_AsMessageType(messageTypeList);
                    orCB.orScopeQueryAndPart(andCB -> {
                        andCB.query().setMessageTypeCode_Equal_独り言();
                        andCB.query().setVillagePlayerId_Equal(optVillagePlayer.get().getVillagePlayerId());
                    });
                });
            } else {
                cb.query().setMessageTypeCode_InScope_AsMessageType(messageTypeList);
            }
            cb.query().addOrderBy_MessageDatetime_Asc();
            cb.query().addOrderBy_MessageId_Asc();
        });
    }

    private Village selectVillage(Integer villageId) {
        return villageBhv.selectEntityWithDeletedCheck(cb -> {
            cb.setupSelect_VillageSettingsAsOne();
            cb.query().setVillageId_Equal(villageId);
        });
    }

    private OptionalEntity<VillagePlayer> selectVillagePlayer(Integer villageId, UserInfo userInfo) {
        if (userInfo == null) {
            return OptionalEntity.empty();
        }
        return villagePlayerBhv.selectEntity(cb -> {
            cb.setupSelect_Player();
            cb.setupSelect_SkillBySkillCode();
            cb.query().setVillageId_Equal(villageId);
            cb.query().setIsGone_Equal_False();
            cb.query().queryPlayer().setPlayerName_Equal(userInfo.getUsername());
        });
    }

    private int selectLatestDay(Integer villageId) {
        return villageDayBhv.selectScalar(Integer.class).max(cb -> {
            cb.specify().columnDay();
            cb.query().setVillageId_Equal(villageId);
        }).get();
    }

    private Message selectAnchorMessage(VillageGetAnchorMessageForm form) {
        Message message = messageBhv.selectEntity(cb -> {
            cb.setupSelect_VillagePlayer().withPlayer();
            cb.setupSelect_VillagePlayer().withChara();
            cb.query().setVillageId_Equal(form.getVillageId());
            cb.query().setMessageNumber_Equal(form.getMessageNumber());
            cb.query().setMessageTypeCode_Equal_AsMessageType(CDef.MessageType.codeOf(form.getMessageType()));
        }).orElse(null);
        return message;
    }

    // ===================================================================================
    //                                                                             Mapping
    //                                                                             =======
    private VillageMessageListResultContent mappingToMessageListContent(Village village, ListResultBean<Message> messageList,
            String villageStatusMessage, String suddonlyDeathMessage, int latestDay) {
        VillageMessageListResultContent content = new VillageMessageListResultContent();
        content.setMessageList(convertToMessageList(village, messageList));
        content.setVillageStatusMessage(villageStatusMessage);
        content.setSuddonlyDeathMessage(suddonlyDeathMessage);
        content.setLatestDay(latestDay);
        return content;
    }

    private String makeVillageStatusMessage(Village village, boolean isLatestDay, OptionalEntity<VillagePlayer> optVillagePlayer, int day) {
        if (!isLatestDay) {
            return null;
        }
        VillageSettings vSettings = village.getVillageSettingsAsOne().get();
        VillageDay maxVillageDay = selectMaxVillageDay(village);
        String startDateTime = maxVillageDay.getDaychangeDatetime().format(DateTimeFormatter.ofPattern("MM/dd HH:mm"));
        if (village.isVillageStatusCode募集中()) {
            Integer minPersonNum = vSettings.getStartPersonMinNum();
            Integer maxPersonNum = vSettings.getPersonMaxNum();
            if (!optVillagePlayer.isPresent()) {
                return messageSource.getMessage("village.status.preparation",
                        new String[] { startDateTime, minPersonNum.toString(), maxPersonNum.toString() }, Locale.JAPAN);
            } else {
                return messageSource.getMessage("village.status.preparation.participate",
                        new String[] { startDateTime, minPersonNum.toString(), maxPersonNum.toString() }, Locale.JAPAN);
            }
        } else if (village.isVillageStatusCode開始待ち()) {
            return messageSource.getMessage("village.status.wating", new String[] { startDateTime }, Locale.JAPAN);
        } else if (village.isVillageStatusCode進行中()) {
            if (day == 1) {
                return messageSource.getMessage("village.status.progress.firstday", new String[] { startDateTime }, Locale.JAPAN);
            } else {
                return messageSource.getMessage("village.status.progress", new String[] { startDateTime }, Locale.JAPAN);
            }
        } else if (village.isVillageStatusCodeエピローグ()) {
            String campName = village.getWinCampCodeAsCamp().alias();
            return messageSource.getMessage("village.status.epilogue", new String[] { campName, startDateTime }, Locale.JAPAN);
        } else if (village.isVillageStatusCode終了()) {
            return messageSource.getMessage("village.status.completed", new String[] {}, Locale.JAPAN);
        } else if (village.isVillageStatusCode廃村()) {
            return messageSource.getMessage("village.status.cancel", new String[] {}, Locale.JAPAN);
        }
        return null;
    }

    private VillageDay selectMaxVillageDay(Village village) {
        VillageDay maxVillageDay = villageDayBhv.selectEntity(cb -> {
            cb.setupSelect_Village().withVillageSettingsAsOne();
            cb.query().setVillageId_Equal(village.getVillageId());
            cb.query().addOrderBy_Day_Desc();
            cb.fetchFirst(1);
        }).get();
        return maxVillageDay;
    }

    private List<VillageMessageDto> convertToMessageList(Village village, List<Message> messageList) {
        return messageList.stream().map(message -> convertToMessage(village, message)).collect(Collectors.toList());
    }

    private VillageMessageDto convertToMessage(Village village, Message message) {
        VillageMessageDto messageDto = new VillageMessageDto();
        messageDto.setCharacterName(
                message.getVillagePlayer().map(villagePlayer -> villagePlayer.getChara().get().getCharaName()).orElse(null));
        messageDto.setCharacterId(message.getVillagePlayer().map(villagePlayer -> villagePlayer.getCharaId()).orElse(null));
        messageDto.setCharacterImageUrl(
                message.getVillagePlayer().map(villagePlayer -> villagePlayer.getChara().get().getCharaImgUrl()).orElse(null));
        if (village.isVillageStatusCodeエピローグ() || village.isVillageStatusCode終了()) {
            messageDto.setPlayerName(
                    message.getVillagePlayer().map(villagePlayer -> villagePlayer.getPlayer().get().getPlayerName()).orElse(null));
        }
        messageDto.setMessageContent(message.getMessageContent());
        messageDto.setMessageDatetime(message.getMessageDatetime());
        if (!message.isMessageTypeCode独り言() || (village.isVillageStatusCodeエピローグ() || village.isVillageStatusCode終了())) {
            messageDto.setMessageNumber(message.getMessageNumber());
        }
        messageDto.setMessageType(message.getMessageTypeCodeAsMessageType().code());
        return messageDto;
    }

    // ===================================================================================
    //                                                                        Assist Logic
    //                                                                        ============
    private boolean isViewAllowedMessage(VillageGetAnchorMessageForm form, Village village, UserInfo userInfo) {
        Integer villageId = form.getVillageId();
        CDef.MessageType messageType = CDef.MessageType.codeOf(form.getMessageType());
        OptionalEntity<VillagePlayer> optVillagePlayer = selectVillagePlayer(villageId, userInfo);
        if (messageType == CDef.MessageType.人狼の囁き) {
            return isViewAllowedWerewolfSay(village, optVillagePlayer);
        } else if (messageType == CDef.MessageType.通常発言) {
            return true;
        } else if (messageType == CDef.MessageType.共鳴発言) {
            return isViewAllowedMasonSay(village, optVillagePlayer);
        } else if (messageType == CDef.MessageType.死者の呻き) {
            return isViewAllowedGraveSay(village, optVillagePlayer);
        } else if (messageType == CDef.MessageType.見学発言) {
            return isViewAllowedSpectateSay(village, optVillagePlayer, 1);
        } else if (messageType == CDef.MessageType.独り言) {
            return isViewAllowedMonologueSay(village, optVillagePlayer);
        }
        return false;
    }

    private List<CDef.MessageType> makeMessageTypeList(OptionalEntity<VillagePlayer> optVillagePlayer, Village village, int day) {
        if (optVillagePlayer.isPresent() && "master".equals(optVillagePlayer.get().getPlayer().get().getPlayerName())) {
            // masterは全て見られる
            return CDef.MessageType.listAll();
        }
        List<CDef.MessageType> dispAllowedMessageTypeList =
                new ArrayList<>(Arrays.asList(CDef.MessageType.公開システムメッセージ, CDef.MessageType.通常発言));
        addGraveSayIfAllowed(dispAllowedMessageTypeList, village, optVillagePlayer);
        addSpectateSayIfAllowed(dispAllowedMessageTypeList, village, optVillagePlayer, day);
        addMasonSayIfAllowed(dispAllowedMessageTypeList, village, optVillagePlayer);
        addWerewolfSayIfAllowed(dispAllowedMessageTypeList, village, optVillagePlayer);
        addMonologueSayIfAllowed(dispAllowedMessageTypeList, village, optVillagePlayer);
        addSeerMessageIfAllowed(dispAllowedMessageTypeList, village, optVillagePlayer);
        addPsychicMessageIfAllowed(dispAllowedMessageTypeList, village, optVillagePlayer);
        addSystemMessageIfAllowed(dispAllowedMessageTypeList, village, optVillagePlayer);
        return dispAllowedMessageTypeList;
    }

    // 墓下発言
    private void addGraveSayIfAllowed(List<MessageType> dispAllowedMessageTypeList, Village village,
            OptionalEntity<VillagePlayer> optVillagePlayer) {
        if (isViewAllowedGraveSay(village, optVillagePlayer)) {
            dispAllowedMessageTypeList.add(CDef.MessageType.死者の呻き);
        }
    }

    private boolean isViewAllowedGraveSay(Village village, OptionalEntity<VillagePlayer> optVillagePlayer) {
        // 終了していたら全開放
        if (village.isVillageStatusCodeエピローグ() || village.isVillageStatusCode廃村() || village.isVillageStatusCode終了()) {
            return true;
        }
        // 墓下との会話ができる設定なら開放
        if (village.getVillageSettingsAsOne().get().isIsVisibleGraveSpectateMessageTrue()) {
            return true;
        }
        // 終了していなかったら参加していて突然死以外で死亡している場合のみ開放
        if (!optVillagePlayer.isPresent()) {
            return false;
        }
        VillagePlayer vPlayer = optVillagePlayer.get();
        if ((vPlayer.isIsDeadTrue() && !vPlayer.isDeadReasonCode突然()) || vPlayer.isIsSpectatorTrue()) {
            return true;
        }
        return false;
    }

    // 見学発言
    private void addSpectateSayIfAllowed(List<MessageType> dispAllowedMessageTypeList, Village village,
            OptionalEntity<VillagePlayer> optVillagePlayer, int day) {
        if (isViewAllowedSpectateSay(village, optVillagePlayer, day)) {
            dispAllowedMessageTypeList.add(CDef.MessageType.見学発言);
        }
    }

    private boolean isViewAllowedSpectateSay(Village village, OptionalEntity<VillagePlayer> optVillagePlayer, int day) {
        // 進行中以外は全開放
        if (village.isVillageStatusCode募集中() || village.isVillageStatusCode開始待ち() || village.isVillageStatusCodeエピローグ()
                || village.isVillageStatusCode廃村() || village.isVillageStatusCode終了()) {
            return true;
        }
        // 見学との会話ができる設定なら開放
        if (village.getVillageSettingsAsOne().get().isIsVisibleGraveSpectateMessageTrue()) {
            return true;
        }
        // 進行中は0日目の発言は開放
        if (village.isVillageStatusCode進行中() && day == 0) {
            return true;
        }
        // 終了していなかったら参加していて突然死以外で死亡している場合、もしくは見学の場合のみ開放
        if (!optVillagePlayer.isPresent()) {
            return false;
        }
        VillagePlayer vPlayer = optVillagePlayer.get();
        if ((vPlayer.isIsDeadTrue() && !vPlayer.isDeadReasonCode突然()) || vPlayer.isIsSpectatorTrue()) {
            return true;
        }
        return false;
    }

    private boolean isViewAllowedMonologueSay(Village village, OptionalEntity<VillagePlayer> optVillagePlayer) {
        // 進行中以外は全開放
        if (village.isVillageStatusCode募集中() || village.isVillageStatusCode開始待ち() || village.isVillageStatusCodeエピローグ()
                || village.isVillageStatusCode廃村() || village.isVillageStatusCode終了()) {
            return true;
        }
        return false;
    }

    // 共鳴発言
    private void addMasonSayIfAllowed(List<MessageType> dispAllowedMessageTypeList, Village village,
            OptionalEntity<VillagePlayer> optVillagePlayer) {
        if (isViewAllowedMasonSay(village, optVillagePlayer)) {
            dispAllowedMessageTypeList.add(CDef.MessageType.共鳴発言);
        }
    }

    private boolean isViewAllowedMasonSay(Village village, OptionalEntity<VillagePlayer> optVillagePlayer) {
        // 終了していたら全開放
        if (village.isVillageStatusCodeエピローグ() || village.isVillageStatusCode廃村() || village.isVillageStatusCode終了()) {
            return true;
        }
        // 終了していなかったら参加していて死亡しておらず、共鳴者だったら開放
        if (!optVillagePlayer.isPresent()) {
            return false;
        }
        VillagePlayer vPlayer = optVillagePlayer.get();
        if (vPlayer.isIsDeadFalse() && vPlayer.getSkillCodeAsSkill() == CDef.Skill.共鳴者) {
            return true;
        }
        return false;
    }

    // 人狼の囁き
    private void addWerewolfSayIfAllowed(List<MessageType> dispAllowedMessageTypeList, Village village,
            OptionalEntity<VillagePlayer> optVillagePlayer) {
        if (isViewAllowedWerewolfSay(village, optVillagePlayer)) {
            dispAllowedMessageTypeList.add(CDef.MessageType.人狼の囁き);
        }
    }

    private boolean isViewAllowedWerewolfSay(Village village, OptionalEntity<VillagePlayer> optVillagePlayer) {
        // 終了していたら全開放
        if (village.isVillageStatusCodeエピローグ() || village.isVillageStatusCode廃村() || village.isVillageStatusCode終了()) {
            return true;
        }
        // 終了していなかったら参加していて死亡しておらず、人狼だったら開放
        if (!optVillagePlayer.isPresent()) {
            return false;
        }
        VillagePlayer vPlayer = optVillagePlayer.get();
        if (vPlayer.isIsDeadFalse()
                && (vPlayer.getSkillCodeAsSkill() == CDef.Skill.人狼 || vPlayer.getSkillCodeAsSkill() == CDef.Skill.C国狂人)) {
            return true;
        }
        return false;
    }

    // 占い結果
    private void addSeerMessageIfAllowed(List<MessageType> dispAllowedMessageTypeList, Village village,
            OptionalEntity<VillagePlayer> optVillagePlayer) {
        // 終了していたら全開放
        if (village.isVillageStatusCodeエピローグ() || village.isVillageStatusCode廃村() || village.isVillageStatusCode終了()) {
            dispAllowedMessageTypeList.add(CDef.MessageType.白黒占い結果);
            dispAllowedMessageTypeList.add(CDef.MessageType.役職占い結果);
            return;
        }
        // 終了していなかったら参加していて死亡しておらず、占い師だったら開放
        if (!optVillagePlayer.isPresent()) {
            return;
        }
        VillagePlayer vPlayer = optVillagePlayer.get();
        if (vPlayer.isIsDeadFalse()) {
            if (vPlayer.getSkillCodeAsSkill() == CDef.Skill.占い師) {
                dispAllowedMessageTypeList.add(CDef.MessageType.白黒占い結果);
                return;
            } else if (vPlayer.getSkillCodeAsSkill() == CDef.Skill.賢者) {
                dispAllowedMessageTypeList.add(CDef.MessageType.役職占い結果);
                return;
            }
        }
    }

    // 霊視結果
    private void addPsychicMessageIfAllowed(List<MessageType> dispAllowedMessageTypeList, Village village,
            OptionalEntity<VillagePlayer> optVillagePlayer) {
        // 終了していたら全開放
        if (village.isVillageStatusCodeエピローグ() || village.isVillageStatusCode廃村() || village.isVillageStatusCode終了()) {
            dispAllowedMessageTypeList.add(CDef.MessageType.白黒霊視結果);
            dispAllowedMessageTypeList.add(CDef.MessageType.役職霊視結果);
            return;
        }
        // 終了していなかったら参加していて死亡しておらず、霊能者だったら開放
        if (!optVillagePlayer.isPresent()) {
            return;
        }
        VillagePlayer vPlayer = optVillagePlayer.get();
        if (vPlayer.isIsDeadFalse()) {
            if (vPlayer.getSkillCodeAsSkill() == CDef.Skill.霊能者) {
                dispAllowedMessageTypeList.add(CDef.MessageType.白黒霊視結果);
                return;
            } else if (SkillUtil.hasSkillPsychicAbility(vPlayer.getSkillCodeAsSkill())) {
                dispAllowedMessageTypeList.add(CDef.MessageType.役職霊視結果);
                return;
            }
        }
    }

    // 非公開システムメッセージ
    private void addSystemMessageIfAllowed(List<MessageType> dispAllowedMessageTypeList, Village village,
            OptionalEntity<VillagePlayer> optVillagePlayer) {
        // 終了していたら全開放
        if (village.isVillageStatusCodeエピローグ() || village.isVillageStatusCode廃村() || village.isVillageStatusCode終了()) {
            dispAllowedMessageTypeList.add(CDef.MessageType.非公開システムメッセージ);
            return;
        }
    }

    // 独り言
    private void addMonologueSayIfAllowed(List<MessageType> dispAllowedMessageTypeList, Village village,
            OptionalEntity<VillagePlayer> optVillagePlayer) {
        // 終了していたら全開放
        if (village.isVillageStatusCodeエピローグ() || village.isVillageStatusCode廃村() || village.isVillageStatusCode終了()) {
            dispAllowedMessageTypeList.add(CDef.MessageType.独り言);
            return;
        }
        // 終了していなかったら自分自身のだけ表示なので、ここでは追加しない
    }

    // 最新日か
    private boolean isLatestDay(Integer villageId, int day) {
        ListResultBean<VillageDay> dayList = villageDayBhv.selectList(cb -> cb.query().setVillageId_Equal(villageId));
        return dayList.get(dayList.size() - 1).getDay().equals(day);
    }

    // 突然死ありの場合に接続していない人を表示する
    private String makeSuddonlyDeathMessage(Village village, boolean isLatestDay, int day) {
        if (!isLatestDay || village.getVillageSettingsAsOne().get().isIsAvailableSuddonlyDeathFalse()) {
            return null;
        }
        // 前日更新日時
        LocalDateTime lastUpdate = getLastUpdate(village, day);
        // 接続していない人
        List<String> noAccessCharaList = villagePlayerBhv.selectList(cb -> {
            cb.setupSelect_Chara();
            cb.query().setVillageId_Equal(village.getVillageId());
            cb.orScopeQuery(orCB -> {
                orCB.query().setLastAccessDatetime_IsNull();
                orCB.query().setLastAccessDatetime_LessThan(lastUpdate);
            });
            cb.query().queryChara().setIsDummy_Equal_False();
            cb.query().setIsGone_Equal_False();
            cb.query().setIsSpectator_Equal_False();
            cb.query().setIsDead_Equal_False();
        }).stream().map(vp -> vp.getChara().get().getCharaName()).collect(Collectors.toList());
        if (noAccessCharaList.size() == 0) {
            return null;
        }
        return String.format("本日まだ部屋から出てきていない者は、%s。", String.join("、", noAccessCharaList));
    }

    private LocalDateTime getLastUpdate(Village village, int day) {
        ListResultBean<VillageDay> dayList = villageDayBhv.selectList(cb -> {
            cb.query().setVillageId_Equal(village.getVillageId());
            cb.query().addOrderBy_Day_Asc();
        });
        if (day == 0) {
            VillageDay latestDay = dayList.get(0);
            return latestDay.getUpdateDatetime();
        }
        return dayList.get(dayList.size() - 2).getDaychangeDatetime();
    }
}
