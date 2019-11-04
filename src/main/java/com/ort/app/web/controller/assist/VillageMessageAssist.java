package com.ort.app.web.controller.assist;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import org.dbflute.cbean.result.ListResultBean;
import org.dbflute.cbean.result.PagingResultBean;
import org.dbflute.optional.OptionalEntity;
import org.dbflute.optional.OptionalScalar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import com.ort.app.web.form.VillageGetAnchorMessageForm;
import com.ort.app.web.form.VillageGetMessageListForm;
import com.ort.app.web.model.VillageAnchorMessageResultContent;
import com.ort.app.web.model.VillageLatestMessageDatetimeResultContent;
import com.ort.app.web.model.VillageMessageListResultContent;
import com.ort.app.web.model.inner.VillageMessageDto;
import com.ort.app.web.util.CharaUtil;
import com.ort.dbflute.allcommon.CDef;
import com.ort.dbflute.allcommon.CDef.MessageType;
import com.ort.dbflute.exbhv.CommitBhv;
import com.ort.dbflute.exbhv.MessageBhv;
import com.ort.dbflute.exbhv.VillageBhv;
import com.ort.dbflute.exbhv.VillageDayBhv;
import com.ort.dbflute.exbhv.VillagePlayerBhv;
import com.ort.dbflute.exbhv.VoteBhv;
import com.ort.dbflute.exentity.Chara;
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
    private CommitBhv commitBhv;

    @Autowired
    private VoteBhv voteBhv;

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
        PagingResultBean<Message> messageList =
                selectMessageList(form.getVillageId(), day, messageTypeList, optVillagePlayer, form.getPageNum(), form.getPageSize());
        boolean isLatestDay = isLatestDay(villageId, day);
        String suddonlyDeathMessage = makeSuddonlyDeathMessage(village, isLatestDay, day);
        String villageStatusMessage = makeVillageStatusMessage(village, isLatestDay, optVillagePlayer, day);
        String commitStatusMessage = makeCommitStatusMessage(village, isLatestDay, day);
        VillageMessageListResultContent content = mappingToMessageListContent(village, messageList, villageStatusMessage,
                suddonlyDeathMessage, commitStatusMessage, latestDay);
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

    public VillageLatestMessageDatetimeResultContent getLatestMessageDatetime(VillageGetMessageListForm form, UserInfo userInfo) {
        Integer villageId = form.getVillageId();
        int latestDay = selectLatestDay(villageId);
        int day = form.getDay() != null ? form.getDay() : latestDay;
        OptionalEntity<VillagePlayer> optVillagePlayer = selectVillagePlayer(villageId, userInfo);
        Village village = selectVillage(villageId);
        List<CDef.MessageType> messageTypeList = makeMessageTypeList(optVillagePlayer, village, day);
        OptionalScalar<LocalDateTime> optLatestMessageDatetime =
                selectLatestMessageDatetime(form.getVillageId(), latestDay, messageTypeList, optVillagePlayer);
        VillageLatestMessageDatetimeResultContent content = new VillageLatestMessageDatetimeResultContent();
        String latestMessageDatetime =
                optLatestMessageDatetime.map(ldt -> ldt.format(DateTimeFormatter.ofPattern("uuuuMMddHHmmss"))).orElse("0");
        content.setLatestMessageDatetime(latestMessageDatetime);
        return content;
    }

    // ===================================================================================
    //                                                                              Select
    //                                                                              ======
    private PagingResultBean<Message> selectMessageList(Integer villageId, Integer day, List<CDef.MessageType> messageTypeList,
            OptionalEntity<VillagePlayer> optVillagePlayer, Integer pageNum, Integer pageSize) {
        PagingResultBean<Message> messagePage = messageBhv.selectPage(cb -> {
            if (pageNum != null && pageSize != null) {
                cb.paging(pageSize, pageNum);
            } else if (pageSize != null) {
                cb.paging(pageSize, 10000); // 存在しないページを検索して最新を取得させる
            } else {
                cb.paging(100000, 1);
            }
            cb.setupSelect_VillagePlayerByVillagePlayerId().withPlayer();
            cb.setupSelect_VillagePlayerByVillagePlayerId().withChara();
            cb.setupSelect_VillagePlayerByToVillagePlayerId().withChara();
            cb.query().setVillageId_Equal(villageId);
            cb.query().setDay_Equal(day);
            if (optVillagePlayer.isPresent()) {
                VillagePlayer vPlayer = optVillagePlayer.get();
                Integer villagePlayerId = vPlayer.getVillagePlayerId();
                cb.orScopeQuery(orCB -> {
                    orCB.query().setMessageTypeCode_InScope_AsMessageType(messageTypeList);
                    orCB.orScopeQueryAndPart(andCB -> {
                        andCB.query().setMessageTypeCode_Equal_独り言();
                        andCB.query().setVillagePlayerId_Equal(villagePlayerId);
                    });
                    orCB.orScopeQueryAndPart(andCB -> {
                        andCB.query().setMessageTypeCode_Equal_秘話();
                        andCB.query().setVillagePlayerId_Equal(villagePlayerId);
                    });
                    orCB.orScopeQueryAndPart(andCB -> {
                        andCB.query().setMessageTypeCode_Equal_秘話();
                        andCB.query().setToVillagePlayerId_Equal(villagePlayerId);
                    });
                    if (isViewAllowedSeerMessage(vPlayer)) {
                        orCB.orScopeQueryAndPart(andCB -> {
                            andCB.query().setMessageTypeCode_Equal_白黒占い結果();
                            andCB.query().setVillagePlayerId_Equal(villagePlayerId);
                        });
                    } else if (isViewAllowedWiseMessage(vPlayer)) {
                        orCB.orScopeQueryAndPart(andCB -> {
                            andCB.query().setMessageTypeCode_Equal_役職占い結果();
                            andCB.query().setVillagePlayerId_Equal(villagePlayerId);
                        });
                    } else if (isViewAllowedInvestigateMessage(vPlayer)) {
                        orCB.orScopeQueryAndPart(andCB -> {
                            andCB.query().setMessageTypeCode_Equal_足音調査結果();
                            andCB.query().setVillagePlayerId_Equal(villagePlayerId);
                        });
                    }
                });
            } else {
                cb.query().setMessageTypeCode_InScope_AsMessageType(messageTypeList);
            }
            cb.query().addOrderBy_MessageDatetime_Asc();
            cb.query().addOrderBy_MessageId_Asc();
        });
        messageBhv.load(messagePage, loader -> {
            loader.pulloutVillagePlayerByVillagePlayerId().pulloutChara().loadCharaImage(charaImageCB -> {});
        });
        return messagePage;
    }

    private boolean isViewAllowedSeerMessage(VillagePlayer villagePlayer) {
        return villagePlayer.isIsDeadFalse() && villagePlayer.getSkillCodeAsSkill() == CDef.Skill.占い師;
    }

    private boolean isViewAllowedWiseMessage(VillagePlayer villagePlayer) {
        return villagePlayer.isIsDeadFalse() && villagePlayer.getSkillCodeAsSkill() == CDef.Skill.賢者;
    }

    private boolean isViewAllowedInvestigateMessage(VillagePlayer villagePlayer) {
        return villagePlayer.isIsDeadFalse() && villagePlayer.getSkillCodeAsSkill() == CDef.Skill.探偵;
    }

    private OptionalScalar<LocalDateTime> selectLatestMessageDatetime(Integer villageId, Integer latestDay,
            List<CDef.MessageType> messageTypeList, OptionalEntity<VillagePlayer> optVillagePlayer) {
        return messageBhv.selectScalar(LocalDateTime.class).max(cb -> {
            cb.specify().columnMessageDatetime();
            cb.query().setVillageId_Equal(villageId);
            cb.query().setDay_Equal(latestDay);
            if (optVillagePlayer.isPresent()) {
                Integer villagePlayerId = optVillagePlayer.get().getVillagePlayerId();
                cb.orScopeQuery(orCB -> {
                    orCB.query().setMessageTypeCode_InScope_AsMessageType(messageTypeList);
                    orCB.orScopeQueryAndPart(andCB -> {
                        andCB.query().setMessageTypeCode_Equal_独り言();
                        andCB.query().setVillagePlayerId_Equal(villagePlayerId);
                    });
                    orCB.orScopeQueryAndPart(andCB -> {
                        andCB.query().setMessageTypeCode_Equal_秘話();
                        andCB.query().setVillagePlayerId_Equal(villagePlayerId);
                    });
                    orCB.orScopeQueryAndPart(andCB -> {
                        andCB.query().setMessageTypeCode_Equal_秘話();
                        andCB.query().setToVillagePlayerId_Equal(villagePlayerId);
                    });
                });
            } else {
                cb.query().setMessageTypeCode_InScope_AsMessageType(messageTypeList);
            }
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
            cb.setupSelect_VillagePlayerByVillagePlayerId().withPlayer();
            cb.setupSelect_VillagePlayerByVillagePlayerId().withChara();
            cb.query().setVillageId_Equal(form.getVillageId());
            cb.query().setMessageNumber_Equal(form.getMessageNumber());
            cb.query().setMessageTypeCode_Equal_AsMessageType(CDef.MessageType.codeOf(form.getMessageType()));
        }).orElse(null);
        if (message != null) {
            messageBhv.load(message, loader -> {
                loader.pulloutVillagePlayerByVillagePlayerId().pulloutChara().loadCharaImage(charaImageCB -> {});
            });
        }
        return message;
    }

    // ===================================================================================
    //                                                                             Mapping
    //                                                                             =======
    private VillageMessageListResultContent mappingToMessageListContent(Village village, PagingResultBean<Message> messageList,
            String villageStatusMessage, String suddonlyDeathMessage, String commitStatusMessage, int latestDay) {
        VillageMessageListResultContent content = new VillageMessageListResultContent();
        content.setMessageList(convertToMessageList(village, messageList));
        content.setVillageStatusMessage(villageStatusMessage);
        content.setSuddonlyDeathMessage(suddonlyDeathMessage);
        content.setCommitStatusMessage(commitStatusMessage);
        content.setLatestDay(latestDay);
        content.setAllPageCount(messageList.getAllPageCount());
        content.setIsExistPrePage(messageList.existsPreviousPage());
        content.setIsExistNextPage(messageList.existsNextPage());
        content.setCurrentPageNum(messageList.getCurrentPageNumber());
        content.setPageNumList(createPageNumList(messageList));
        String latestMessageDatetime = "0";
        if (messageList.size() != 0) {
            latestMessageDatetime =
                    messageList.get(messageList.size() - 1).getMessageDatetime().format(DateTimeFormatter.ofPattern("uuuuMMddHHmmss"));
        }
        content.setLatestMessageDatetime(latestMessageDatetime);
        return content;
    }

    private List<Integer> createPageNumList(PagingResultBean<Message> messageList) {
        int allPageCount = messageList.getAllPageCount();
        int currentPageNumber = messageList.getCurrentPageNumber();
        int startPage = currentPageNumber - 2;
        int endPage = currentPageNumber + 2;
        if (startPage < 1) {
            startPage = 1;
            endPage = 5;
        }
        if (endPage > allPageCount) {
            endPage = allPageCount;
            startPage = allPageCount - 4;
            if (startPage < 1) {
                startPage = 1;
            }
        }
        List<Integer> pageNumList = new ArrayList<>();
        for (int i = startPage; i <= endPage; i++) {
            pageNumList.add(i);
        }
        return pageNumList;
    }

    private String makeVillageStatusMessage(Village village, boolean isLatestDay, OptionalEntity<VillagePlayer> optVillagePlayer, int day) {
        if (!isLatestDay) {
            return null;
        }
        VillageSettings vSettings = village.getVillageSettingsAsOne().get();
        VillageDay maxVillageDay = selectMaxVillageDay(village);
        String startDateTime = maxVillageDay.getDaychangeDatetime().format(DateTimeFormatter.ofPattern("MM/dd HH:mm:ss"));
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
        message.getVillagePlayerByVillagePlayerId().ifPresent(vp -> {
            Chara chara = vp.getChara().get();
            messageDto.setCharacterName(chara.getCharaName());
            messageDto.setCharacterShortName(chara.getCharaShortName());
            messageDto.setCharacterId(chara.getCharaId());
            if (message.getFaceTypeCodeAsFaceType() != null) {
                messageDto.setCharacterImageUrl(CharaUtil.getCharaImgUrlByFaceType(chara, message.getFaceTypeCodeAsFaceType()));
            }
            messageDto.setWidth(chara.getDisplayWidth());
            messageDto.setHeight(chara.getDisplayHeight());
        });
        message.getVillagePlayerByToVillagePlayerId().ifPresent(vp -> {
            messageDto.setTargetCharacterName(vp.getChara().get().getCharaName());
        });
        if (village.isVillageStatusCodeエピローグ() || village.isVillageStatusCode終了() || village.isVillageStatusCode廃村()) {
            messageDto.setPlayerName(message.getVillagePlayerByVillagePlayerId()
                    .map(villagePlayer -> villagePlayer.getPlayer().get().getPlayerName())
                    .orElse(null));
        }
        messageDto.setMessageContent(message.getMessageContent());
        messageDto.setMessageDatetime(message.getMessageDatetime());
        messageDto.setIsConvertDisable(message.getIsConvertDisable());
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
        } else if (messageType == CDef.MessageType.通常発言 || messageType == CDef.MessageType.村建て発言) {
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
                new ArrayList<>(Arrays.asList(CDef.MessageType.公開システムメッセージ, CDef.MessageType.通常発言, CDef.MessageType.村建て発言));
        addGraveSayIfAllowed(dispAllowedMessageTypeList, village, optVillagePlayer);
        addSpectateSayIfAllowed(dispAllowedMessageTypeList, village, optVillagePlayer, day);
        addMasonSayIfAllowed(dispAllowedMessageTypeList, village, optVillagePlayer);
        addWerewolfSayIfAllowed(dispAllowedMessageTypeList, village, optVillagePlayer);
        addMonologueSayIfAllowed(dispAllowedMessageTypeList, village, optVillagePlayer);
        addSecretSayIfAllowed(dispAllowedMessageTypeList, village, optVillagePlayer);
        addSeerMessageIfAllowed(dispAllowedMessageTypeList, village, optVillagePlayer);
        addPsychicMessageIfAllowed(dispAllowedMessageTypeList, village, optVillagePlayer);
        addInvestigateMessageIfAllowed(dispAllowedMessageTypeList, village, optVillagePlayer);
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
        if (village.isVillageStatusCodeエピローグ() || village.isVillageStatusCode廃村() || village.isVillageStatusCode終了()) {
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
        // 終了していなかったら参加していて共鳴者だったら開放
        if (!optVillagePlayer.isPresent()) {
            return false;
        }
        VillagePlayer vPlayer = optVillagePlayer.get();
        if (vPlayer.getSkillCodeAsSkill() == CDef.Skill.共鳴者) {
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
        // 終了していなかったら参加していて人狼だったら開放
        if (!optVillagePlayer.isPresent()) {
            return false;
        }
        VillagePlayer vPlayer = optVillagePlayer.get();
        if (vPlayer.getSkillCodeAsSkill() == CDef.Skill.人狼 || vPlayer.getSkillCodeAsSkill() == CDef.Skill.C国狂人) {
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
        CDef.Skill skill = vPlayer.getSkillCodeAsSkill();
        if (vPlayer.isIsDeadFalse() && skill != null) {
            if (vPlayer.getSkillCodeAsSkill() == CDef.Skill.霊能者) {
                dispAllowedMessageTypeList.add(CDef.MessageType.白黒霊視結果);
                return;
            } else if (vPlayer.getSkillCodeAsSkill().isHasSkillPsychicAbility()) {
                dispAllowedMessageTypeList.add(CDef.MessageType.役職霊視結果);
                return;
            }
        }
    }

    // 調査結果
    private void addInvestigateMessageIfAllowed(List<MessageType> dispAllowedMessageTypeList, Village village,
            OptionalEntity<VillagePlayer> optVillagePlayer) {
        // 終了していたら全開放
        if (village.isVillageStatusCodeエピローグ() || village.isVillageStatusCode廃村() || village.isVillageStatusCode終了()) {
            dispAllowedMessageTypeList.add(CDef.MessageType.足音調査結果);
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

    // 秘話
    private void addSecretSayIfAllowed(List<MessageType> dispAllowedMessageTypeList, Village village,
            OptionalEntity<VillagePlayer> optVillagePlayer) {
        // 終了していたら全開放
        if (village.isVillageStatusCodeエピローグ() || village.isVillageStatusCode廃村() || village.isVillageStatusCode終了()) {
            dispAllowedMessageTypeList.add(CDef.MessageType.秘話);
            return;
        }
        // 終了していなかったら自分発信と自分向けのみ表示なので、ここでは追加しない
    }

    // 最新日か
    private boolean isLatestDay(Integer villageId, int day) {
        ListResultBean<VillageDay> dayList = villageDayBhv.selectList(cb -> cb.query().setVillageId_Equal(villageId));
        return dayList.get(dayList.size() - 1).getDay().equals(day);
    }

    // 突然死ありの場合に投票していない人を表示する
    private String makeSuddonlyDeathMessage(Village village, boolean isLatestDay, int day) {
        if (!isLatestDay || village.getVillageSettingsAsOne().get().isIsAvailableSuddonlyDeathFalse() || !village.isVillageStatusCode進行中()
                || day < 2) {
            return null;
        }
        Integer villageId = village.getVillageId();
        // 対象候補
        ListResultBean<VillagePlayer> villagePlayerList = villagePlayerBhv.selectList(cb -> {
            cb.setupSelect_Chara();
            cb.query().setVillageId_Equal(villageId);
            cb.query().setIsDead_Equal_False();
            cb.query().setIsSpectator_Equal_False();
            cb.query().setIsGone_Equal_False();
        });
        // 投票した人
        List<Integer> voteCharaIdList = voteBhv.selectList(cb -> {
            cb.query().setVillageId_Equal(villageId);
            cb.query().setDay_Equal(day);
        }).stream().map(vote -> vote.getCharaId()).collect(Collectors.toList());
        // 投票していない人
        List<String> noVoteCharaNameList = villagePlayerList.stream()
                .filter(vp -> !voteCharaIdList.contains(vp.getCharaId()))
                .map(vp -> CharaUtil.makeCharaName(vp))
                .collect(Collectors.toList());
        if (noVoteCharaNameList.size() == 0) {
            return null;
        }
        return String.format("本日まだ投票していない者は、%s。\n\n※未投票で更新時刻を迎えると突然死します。", String.join("、", noVoteCharaNameList));
    }

    // コミット状況のメッセージ
    private String makeCommitStatusMessage(Village village, boolean isLatestDay, int day) {
        if (village.getVillageSettingsAsOne().get().isIsAvailableCommitFalse() || !village.isVillageStatusCode進行中() || !isLatestDay) {
            return null;
        }
        int commitCount = commitBhv.selectCount(cb -> {
            cb.query().setVillageId_Equal(village.getVillageId());
            cb.query().setDay_Equal(day);
        });
        Integer dummyCharaId = village.getVillageSettingsAsOne().get().getDummyCharaId();
        int livingPersonNum = villagePlayerBhv.selectCount(cb -> {
            cb.query().setVillageId_Equal(village.getVillageId());
            cb.query().setIsGone_Equal_False();
            cb.query().setIsSpectator_Equal_False();
            cb.query().setIsDead_Equal_False();
            cb.query().queryChara().setCharaId_NotEqual(dummyCharaId);
        });
        return String.format("生存者全員がコミットすると日付が更新されます。\n\n現在 %d/%d人 がコミットしています。", commitCount, livingPersonNum);
    }
}
