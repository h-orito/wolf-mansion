package com.ort.app.web.controller.assist;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.BooleanUtils;
import org.dbflute.cbean.result.ListResultBean;
import org.dbflute.cbean.result.PagingResultBean;
import org.dbflute.optional.OptionalScalar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import com.ort.app.datasource.VillageService;
import com.ort.app.web.form.VillageGetAnchorMessageForm;
import com.ort.app.web.form.VillageGetMessageListForm;
import com.ort.app.web.model.VillageAnchorMessageResultContent;
import com.ort.app.web.model.VillageLatestMessageDatetimeResultContent;
import com.ort.app.web.model.VillageMessageListResultContent;
import com.ort.app.web.model.inner.VillageMessageDto;
import com.ort.dbflute.allcommon.CDef;
import com.ort.dbflute.allcommon.CDef.MessageType;
import com.ort.dbflute.exbhv.CommitBhv;
import com.ort.dbflute.exbhv.MessageBhv;
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
    @Autowired
    private VillageService villageService;

    // ===================================================================================
    //                                                                             Execute
    //                                                                             =======
    public VillageMessageListResultContent getMessageList(VillageGetMessageListForm form, UserInfo userInfo) {
        Integer villageId = form.getVillageId();
        int latestDay = selectLatestDay(villageId);
        int day = form.getDay() != null ? form.getDay() : latestDay;
        Optional<VillagePlayer> optVillagePlayer = villageService.selectVillagePlayer(villageId, userInfo, true);
        Village village = villageService.selectVillage(villageId, true, true);
        List<CDef.MessageType> messageTypeList = makeMessageTypeList(optVillagePlayer, village, day);
        PagingResultBean<Message> messageList = selectMessageList(form.getVillageId(), day, messageTypeList, optVillagePlayer,
                form.getPageNum(), form.getPageSize(), form.getOnlyToMe());
        boolean isLatestDay = isLatestDay(villageId, day);
        String suddonlyDeathMessage = makeSuddonlyDeathMessage(village, isLatestDay, day);
        String villageStatusMessage = makeVillageStatusMessage(village, isLatestDay, optVillagePlayer, day);
        String commitStatusMessage = makeCommitStatusMessage(village, isLatestDay, day);
        boolean isBigEars = optVillagePlayer.isPresent() && optVillagePlayer.get().isSkillCode???() && village.isVillageStatusCode?????????();
        VillageMessageListResultContent content = mappingToMessageListContent(village, messageList, villageStatusMessage,
                suddonlyDeathMessage, commitStatusMessage, latestDay, isBigEars);
        return content;
    }

    public VillageAnchorMessageResultContent getAnchorMessage(VillageGetAnchorMessageForm form, BindingResult result) {
        if (result.hasErrors()) {
            return null;
        }
        UserInfo userInfo = WerewolfMansionUserInfoUtil.getUserInfo();
        Village village = villageService.selectVillage(form.getVillageId(), true, true);
        if (!isViewAllowedMessage(form, village, userInfo)) {
            return null;
        }
        Message message = selectAnchorMessage(form);
        VillageAnchorMessageResultContent content = new VillageAnchorMessageResultContent();
        if (message == null) {
            return content;
        }
        content.setMessage(convertToMessage(village, message, false));
        return content;
    }

    public void updateLastAccessDatetime(Integer villageId) {
        UserInfo userInfo = WerewolfMansionUserInfoUtil.getUserInfo();
        Optional<VillagePlayer> optVillagePlayer = villageService.selectVillagePlayer(villageId, userInfo, true);
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
        Optional<VillagePlayer> optVillagePlayer = villageService.selectVillagePlayer(villageId, userInfo, true);
        Village village = villageService.selectVillage(villageId, true, true);
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
            Optional<VillagePlayer> optVillagePlayer, Integer pageNum, Integer pageSize, Boolean isOnlyToMe) {
        PagingResultBean<Message> messagePage = messageBhv.selectPage(cb -> {
            if (pageNum != null && pageSize != null) {
                cb.paging(pageSize, pageNum);
            } else if (pageSize != null) {
                cb.paging(pageSize, 10000); // ???????????????????????????????????????????????????????????????
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
                        andCB.query().setMessageTypeCode_Equal_?????????();
                        andCB.query().setVillagePlayerId_Equal(villagePlayerId);
                    });
                    orCB.orScopeQueryAndPart(andCB -> {
                        andCB.query().setMessageTypeCode_Equal_??????();
                        andCB.query().setVillagePlayerId_Equal(villagePlayerId);
                    });
                    orCB.orScopeQueryAndPart(andCB -> {
                        andCB.query().setMessageTypeCode_Equal_??????();
                        andCB.query().setToVillagePlayerId_Equal(villagePlayerId);
                    });
                    if (isViewAllowedSeerMessage(vPlayer)) {
                        orCB.orScopeQueryAndPart(andCB -> {
                            andCB.query().setMessageTypeCode_Equal_??????????????????();
                            andCB.query().setVillagePlayerId_Equal(villagePlayerId);
                        });
                    } else if (isViewAllowedWiseMessage(vPlayer)) {
                        orCB.orScopeQueryAndPart(andCB -> {
                            andCB.query().setMessageTypeCode_Equal_??????????????????();
                            andCB.query().setVillagePlayerId_Equal(villagePlayerId);
                        });
                    } else if (isViewAllowedInvestigateMessage(vPlayer)) {
                        orCB.orScopeQueryAndPart(andCB -> {
                            andCB.query().setMessageTypeCode_Equal_??????????????????();
                            andCB.query().setVillagePlayerId_Equal(villagePlayerId);
                        });
                    }
                    if (isViewAllowedLoverMessage(vPlayer)) {
                        orCB.orScopeQueryAndPart(andCB -> {
                            andCB.query().setMessageTypeCode_Equal_?????????????????????();
                            andCB.query().setVillagePlayerId_Equal(villagePlayerId);
                        });
                    }
                });
                if (BooleanUtils.isTrue(isOnlyToMe)) {
                    cb.orScopeQuery(orCB -> {
                        orCB.query().existsMessageSendto(sendToCB -> {
                            sendToCB.query().setVillagePlayerId_Equal(villagePlayerId);
                        });
                        orCB.query().setToVillagePlayerId_Equal(villagePlayerId);
                    });
                }
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
        return villagePlayer.isIsDeadFalse()
                && (villagePlayer.getSkillCodeAsSkill() == CDef.Skill.????????? || villagePlayer.getSkillCodeAsSkill() == CDef.Skill.????????????);
    }

    private boolean isViewAllowedWiseMessage(VillagePlayer villagePlayer) {
        return villagePlayer.isIsDeadFalse() && villagePlayer.getSkillCodeAsSkill() == CDef.Skill.??????;
    }

    private boolean isViewAllowedLoverMessage(VillagePlayer villagePlayer) {
        return villagePlayer.isIsDeadFalse() && villagePlayer.hasLover();
    }

    private boolean isViewAllowedInvestigateMessage(VillagePlayer villagePlayer) {
        return villagePlayer.isIsDeadFalse() && villagePlayer.getSkillCodeAsSkill() == CDef.Skill.??????;
    }

    private OptionalScalar<LocalDateTime> selectLatestMessageDatetime(Integer villageId, Integer latestDay,
            List<CDef.MessageType> messageTypeList, Optional<VillagePlayer> optVillagePlayer) {
        return messageBhv.selectScalar(LocalDateTime.class).max(cb -> {
            cb.specify().columnMessageDatetime();
            cb.query().setVillageId_Equal(villageId);
            cb.query().setDay_Equal(latestDay);
            if (optVillagePlayer.isPresent()) {
                Integer villagePlayerId = optVillagePlayer.get().getVillagePlayerId();
                cb.orScopeQuery(orCB -> {
                    orCB.query().setMessageTypeCode_InScope_AsMessageType(messageTypeList);
                    orCB.orScopeQueryAndPart(andCB -> {
                        andCB.query().setMessageTypeCode_Equal_?????????();
                        andCB.query().setVillagePlayerId_Equal(villagePlayerId);
                    });
                    orCB.orScopeQueryAndPart(andCB -> {
                        andCB.query().setMessageTypeCode_Equal_??????();
                        andCB.query().setVillagePlayerId_Equal(villagePlayerId);
                    });
                    orCB.orScopeQueryAndPart(andCB -> {
                        andCB.query().setMessageTypeCode_Equal_??????();
                        andCB.query().setToVillagePlayerId_Equal(villagePlayerId);
                    });
                });
            } else {
                cb.query().setMessageTypeCode_InScope_AsMessageType(messageTypeList);
            }
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
            String villageStatusMessage, String suddonlyDeathMessage, String commitStatusMessage, int latestDay, boolean isBigEars) {
        VillageMessageListResultContent content = new VillageMessageListResultContent();
        content.setMessageList(convertToMessageList(village, messageList, isBigEars));
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

    private String makeVillageStatusMessage(Village village, boolean isLatestDay, Optional<VillagePlayer> optVillagePlayer, int day) {
        if (!isLatestDay) {
            return null;
        }
        VillageSettings vSettings = village.getVillageSettingsAsOne().get();
        VillageDay maxVillageDay = selectMaxVillageDay(village);
        String startDateTime = maxVillageDay.getDaychangeDatetime().format(DateTimeFormatter.ofPattern("MM/dd HH:mm:ss"));
        if (village.isVillageStatusCode?????????()) {
            Integer minPersonNum = vSettings.getStartPersonMinNum();
            Integer maxPersonNum = vSettings.getPersonMaxNum();
            if (!optVillagePlayer.isPresent()) {
                return messageSource.getMessage("village.status.preparation",
                        new String[] { startDateTime, minPersonNum.toString(), maxPersonNum.toString() }, Locale.JAPAN);
            } else {
                return messageSource.getMessage("village.status.preparation.participate",
                        new String[] { startDateTime, minPersonNum.toString(), maxPersonNum.toString() }, Locale.JAPAN);
            }
        } else if (village.isVillageStatusCode????????????()) {
            return messageSource.getMessage("village.status.wating", new String[] { startDateTime }, Locale.JAPAN);
        } else if (village.isVillageStatusCode?????????()) {
            if (day == 1) {
                return messageSource.getMessage("village.status.progress.firstday", new String[] { startDateTime }, Locale.JAPAN);
            } else {
                return messageSource.getMessage("village.status.progress", new String[] { startDateTime }, Locale.JAPAN);
            }
        } else if (village.isVillageStatusCode???????????????()) {
            String campName = village.getWinCampCodeAsCamp().alias();
            return messageSource.getMessage("village.status.epilogue", new String[] { campName, startDateTime }, Locale.JAPAN);
        } else if (village.isVillageStatusCode??????()) {
            return messageSource.getMessage("village.status.completed", new String[] {}, Locale.JAPAN);
        } else if (village.isVillageStatusCode??????()) {
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

    private List<VillageMessageDto> convertToMessageList(Village village, List<Message> messageList, boolean isBigEars) {
        return messageList.stream().map(message -> convertToMessage(village, message, isBigEars)).collect(Collectors.toList());
    }

    private VillageMessageDto convertToMessage(Village village, Message message, boolean isBigEars) {
        VillageMessageDto messageDto = new VillageMessageDto();
        message.getVillagePlayerByVillagePlayerId().ifPresent(vp -> {
            VillagePlayer villagePlayer =
                    village.getVillagePlayers().filterBy(it -> it.getVillagePlayerId().equals(vp.getVillagePlayerId())).list.get(0);
            Chara chara = villagePlayer.getChara().get();
            messageDto.setCharacterName(message.name(villagePlayer.getRoomNumberWhen(message.getDay())));
            messageDto.setCharacterId(chara.getCharaId());
            if (message.getFaceTypeCodeAsFaceType() != null) {
                messageDto.setCharacterImageUrl(chara.getCharaImgUrlByFaceType(message.getFaceTypeCodeAsFaceType()));
            }
            messageDto.setWidth(chara.getDisplayWidth());
            messageDto.setHeight(chara.getDisplayHeight());
        });
        message.getVillagePlayerByToVillagePlayerId().ifPresent(vp -> {
            messageDto.setTargetCharacterName(message.targetName(vp.getRoomNumberWhen(message.getDay())));
        });
        if (village.isVillageStatusCode???????????????() || village.isVillageStatusCode??????() || village.isVillageStatusCode??????()) {
            messageDto.setPlayerName(message.getVillagePlayerByVillagePlayerId()
                    .map(villagePlayer -> villagePlayer.getPlayer().get().getPlayerName())
                    .orElse(null));
        }
        messageDto.setMessageContent(message.getMessageContent());
        messageDto.setMessageDatetime(message.getMessageDatetime());
        messageDto.setIsConvertDisable(message.getIsConvertDisable());
        if (!message.isMessageTypeCode?????????() || (village.isVillageStatusCode???????????????() || village.isVillageStatusCode??????())) {
            messageDto.setMessageNumber(message.getMessageNumber());
        }
        messageDto.setMessageType(message.getMessageTypeCodeAsMessageType().code());
        messageDto.setIsBigEars(shouldHideContent(message, isBigEars));
        return messageDto;
    }

    private boolean shouldHideContent(Message message, boolean isBigEars) {
        if (!isBigEars)
            return false;
        MessageType type = message.getMessageTypeCodeAsMessageType();
        return type == CDef.MessageType.??????????????? || type == CDef.MessageType.???????????? || type == CDef.MessageType.????????????;
    }

    // ===================================================================================
    //                                                                        Assist Logic
    //                                                                        ============
    private boolean isViewAllowedMessage(VillageGetAnchorMessageForm form, Village village, UserInfo userInfo) {
        Integer villageId = form.getVillageId();
        CDef.MessageType messageType = CDef.MessageType.codeOf(form.getMessageType());
        Optional<VillagePlayer> optVillagePlayer = villageService.selectVillagePlayer(villageId, userInfo, true);
        if (messageType == CDef.MessageType.???????????????) {
            return isViewAllowedWerewolfSay(village, optVillagePlayer);
        } else if (messageType == CDef.MessageType.???????????? || messageType == CDef.MessageType.??????????????? || messageType == CDef.MessageType.???????????????) {
            return true;
        } else if (messageType == CDef.MessageType.????????????) {
            return isViewAllowedMasonSay(village, optVillagePlayer);
        } else if (messageType == CDef.MessageType.????????????) {
            return isViewAllowedLoversSay(village, optVillagePlayer);
        } else if (messageType == CDef.MessageType.???????????????) {
            return isViewAllowedGraveSay(village, optVillagePlayer);
        } else if (messageType == CDef.MessageType.????????????) {
            return isViewAllowedSpectateSay(village, optVillagePlayer, 1);
        } else if (messageType == CDef.MessageType.?????????) {
            return isViewAllowedMonologueSay(village, optVillagePlayer);
        }
        return false;
    }

    private List<CDef.MessageType> makeMessageTypeList(Optional<VillagePlayer> optVillagePlayer, Village village, int day) {
        if (optVillagePlayer.isPresent() && "master".equals(optVillagePlayer.get().getPlayer().get().getPlayerName())) {
            // master?????????????????????
            return CDef.MessageType.listAll();
        }
        List<CDef.MessageType> dispAllowedMessageTypeList = new ArrayList<>(
                Arrays.asList(CDef.MessageType.?????????????????????????????????, CDef.MessageType.????????????, CDef.MessageType.???????????????, CDef.MessageType.???????????????));
        addGraveSayIfAllowed(dispAllowedMessageTypeList, village, optVillagePlayer);
        addSpectateSayIfAllowed(dispAllowedMessageTypeList, village, optVillagePlayer, day);
        addMasonSayIfAllowed(dispAllowedMessageTypeList, village, optVillagePlayer);
        addLoversSayIfAllowed(dispAllowedMessageTypeList, village, optVillagePlayer);
        addWerewolfSayIfAllowed(dispAllowedMessageTypeList, village, optVillagePlayer);
        addMonologueSayIfAllowed(dispAllowedMessageTypeList, village, optVillagePlayer);
        addSecretSayIfAllowed(dispAllowedMessageTypeList, village, optVillagePlayer);
        addSeerMessageIfAllowed(dispAllowedMessageTypeList, village, optVillagePlayer);
        addLoverMessageIfAllowed(dispAllowedMessageTypeList, village, optVillagePlayer);
        addPsychicMessageIfAllowed(dispAllowedMessageTypeList, village, optVillagePlayer);
        addCoronerMessageIfAllowed(dispAllowedMessageTypeList, village, optVillagePlayer);
        addAttackMessageIfAllowed(dispAllowedMessageTypeList, village, optVillagePlayer);
        addInvestigateMessageIfAllowed(dispAllowedMessageTypeList, village, optVillagePlayer);
        addSystemMessageIfAllowed(dispAllowedMessageTypeList, village, optVillagePlayer);
        // ?????????????????????
        addPrivateSayMessageForOwl(dispAllowedMessageTypeList, village, optVillagePlayer);
        return dispAllowedMessageTypeList;
    }

    // ????????????
    private void addGraveSayIfAllowed(List<MessageType> dispAllowedMessageTypeList, Village village,
            Optional<VillagePlayer> optVillagePlayer) {
        if (isViewAllowedGraveSay(village, optVillagePlayer)) {
            dispAllowedMessageTypeList.add(CDef.MessageType.???????????????);
        }
    }

    private boolean isViewAllowedGraveSay(Village village, Optional<VillagePlayer> optVillagePlayer) {
        // ??????????????????????????????
        if (village.isVillageStatusCode???????????????() || village.isVillageStatusCode??????() || village.isVillageStatusCode??????()) {
            return true;
        }
        // ????????????????????????????????????????????????
        if (village.getVillageSettingsAsOne().get().isIsVisibleGraveSpectateMessageTrue()) {
            return true;
        }
        // ??????????????????????????????????????????????????????????????????????????????????????????????????????
        if (!optVillagePlayer.isPresent()) {
            return false;
        }
        VillagePlayer vPlayer = optVillagePlayer.get();
        if ((vPlayer.isIsDeadTrue() && !vPlayer.isDeadReasonCode??????()) || vPlayer.isIsSpectatorTrue()) {
            return true;
        }
        return false;
    }

    // ????????????
    private void addSpectateSayIfAllowed(List<MessageType> dispAllowedMessageTypeList, Village village,
            Optional<VillagePlayer> optVillagePlayer, int day) {
        if (isViewAllowedSpectateSay(village, optVillagePlayer, day)) {
            dispAllowedMessageTypeList.add(CDef.MessageType.????????????);
        }
    }

    private boolean isViewAllowedSpectateSay(Village village, Optional<VillagePlayer> optVillagePlayer, int day) {
        // ???????????????????????????
        if (village.isVillageStatusCode?????????() || village.isVillageStatusCode????????????() || village.isVillageStatusCode???????????????()
                || village.isVillageStatusCode??????() || village.isVillageStatusCode??????()) {
            return true;
        }
        // ????????????????????????????????????????????????
        if (village.getVillageSettingsAsOne().get().isIsVisibleGraveSpectateMessageTrue()) {
            return true;
        }
        // ????????????0????????????????????????
        if (village.isVillageStatusCode?????????() && day == 0) {
            return true;
        }
        // ????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
        if (!optVillagePlayer.isPresent()) {
            return false;
        }
        VillagePlayer vPlayer = optVillagePlayer.get();
        if ((vPlayer.isIsDeadTrue() && !vPlayer.isDeadReasonCode??????()) || vPlayer.isIsSpectatorTrue()) {
            return true;
        }
        return false;
    }

    private boolean isViewAllowedMonologueSay(Village village, Optional<VillagePlayer> optVillagePlayer) {
        // ???????????????????????????
        if (village.isVillageStatusCode???????????????() || village.isVillageStatusCode??????() || village.isVillageStatusCode??????()) {
            return true;
        }
        return false;
    }

    // ????????????
    private void addMasonSayIfAllowed(List<MessageType> dispAllowedMessageTypeList, Village village,
            Optional<VillagePlayer> optVillagePlayer) {
        if (isViewAllowedMasonSay(village, optVillagePlayer)) {
            dispAllowedMessageTypeList.add(CDef.MessageType.????????????);
        }
    }

    // ????????????
    private void addLoversSayIfAllowed(List<MessageType> dispAllowedMessageTypeList, Village village,
            Optional<VillagePlayer> optVillagePlayer) {
        if (isViewAllowedLoversSay(village, optVillagePlayer)) {
            dispAllowedMessageTypeList.add(CDef.MessageType.????????????);
        }
    }

    private boolean isViewAllowedMasonSay(Village village, Optional<VillagePlayer> optVillagePlayer) {
        // ??????????????????????????????
        if (village.isVillageStatusCode???????????????() || village.isVillageStatusCode??????() || village.isVillageStatusCode??????()) {
            return true;
        }
        // ???????????????????????????????????????????????????????????????????????????
        if (!optVillagePlayer.isPresent()) {
            return false;
        }
        VillagePlayer vPlayer = optVillagePlayer.get();
        if (vPlayer.getSkillCodeAsSkill() == CDef.Skill.?????????) {
            return true;
        }
        return false;
    }

    private boolean isViewAllowedLoversSay(Village village, Optional<VillagePlayer> optVillagePlayer) {
        // ??????????????????????????????
        if (village.isVillageStatusCode???????????????() || village.isVillageStatusCode??????() || village.isVillageStatusCode??????()) {
            return true;
        }
        // ???????????????????????????????????????????????????????????????????????????????????????
        if (!optVillagePlayer.isPresent()) {
            return false;
        }
        return optVillagePlayer.get().hasLover();
    }

    // ???????????????
    private void addWerewolfSayIfAllowed(List<MessageType> dispAllowedMessageTypeList, Village village,
            Optional<VillagePlayer> optVillagePlayer) {
        if (isViewAllowedWerewolfSay(village, optVillagePlayer)) {
            dispAllowedMessageTypeList.add(CDef.MessageType.???????????????);
        }
    }

    private boolean isViewAllowedWerewolfSay(Village village, Optional<VillagePlayer> optVillagePlayer) {
        // ??????????????????????????????
        if (village.isVillageStatusCode???????????????() || village.isVillageStatusCode??????() || village.isVillageStatusCode??????()) {
            return true;
        }
        // ????????????????????????????????????????????????????????????????????????
        if (!optVillagePlayer.isPresent()) {
            return false;
        }
        VillagePlayer vPlayer = optVillagePlayer.get();
        if (vPlayer.getSkillCodeAsSkill() != null && vPlayer.getSkillCodeAsSkill().isAvailableWerewolfSay()) {
            return true;
        }
        return false;
    }

    // ????????????
    private void addSeerMessageIfAllowed(List<MessageType> dispAllowedMessageTypeList, Village village,
            Optional<VillagePlayer> optVillagePlayer) {
        // ??????????????????????????????
        if (village.isVillageStatusCode???????????????() || village.isVillageStatusCode??????() || village.isVillageStatusCode??????()) {
            dispAllowedMessageTypeList.add(CDef.MessageType.??????????????????);
            dispAllowedMessageTypeList.add(CDef.MessageType.??????????????????);
        }
    }

    // ?????????????????????
    private void addLoverMessageIfAllowed(List<MessageType> dispAllowedMessageTypeList, Village village,
            Optional<VillagePlayer> optVillagePlayer) {
        // ??????????????????????????????
        if (village.isVillageStatusCode???????????????() || village.isVillageStatusCode??????() || village.isVillageStatusCode??????()) {
            dispAllowedMessageTypeList.add(CDef.MessageType.?????????????????????);
        }
    }

    // ????????????
    private void addPsychicMessageIfAllowed(List<MessageType> dispAllowedMessageTypeList, Village village,
            Optional<VillagePlayer> optVillagePlayer) {
        // ??????????????????????????????
        if (village.isVillageStatusCode???????????????() || village.isVillageStatusCode??????() || village.isVillageStatusCode??????()) {
            dispAllowedMessageTypeList.add(CDef.MessageType.??????????????????);
            dispAllowedMessageTypeList.add(CDef.MessageType.??????????????????);
            return;
        }
        // ???????????????????????????????????????????????????????????????????????????????????????????????????
        if (!optVillagePlayer.isPresent()) {
            return;
        }
        VillagePlayer vPlayer = optVillagePlayer.get();
        CDef.Skill skill = vPlayer.getSkillCodeAsSkill();
        if (vPlayer.isIsDeadFalse() && skill != null) {
            if (skill == CDef.Skill.?????????) {
                dispAllowedMessageTypeList.add(CDef.MessageType.??????????????????);
                return;
            } else if (skill.isHasSkillPsychicAbility()) {
                dispAllowedMessageTypeList.add(CDef.MessageType.??????????????????);
                return;
            }
        }
    }

    // ????????????
    private void addCoronerMessageIfAllowed(List<MessageType> dispAllowedMessageTypeList, Village village,
            Optional<VillagePlayer> optVillagePlayer) {
        // ??????????????????????????????
        if (village.isVillageStatusCode???????????????() || village.isVillageStatusCode??????() || village.isVillageStatusCode??????()) {
            dispAllowedMessageTypeList.add(CDef.MessageType.????????????);
            return;
        }
        // ???????????????????????????????????????????????????????????????????????????????????????????????????
        if (!optVillagePlayer.isPresent()) {
            return;
        }
        VillagePlayer vPlayer = optVillagePlayer.get();
        CDef.Skill skill = vPlayer.getSkillCodeAsSkill();
        if (vPlayer.isIsDeadFalse() && skill == CDef.Skill.?????????) {
            dispAllowedMessageTypeList.add(CDef.MessageType.????????????);
            return;
        }
    }

    // ????????????
    private void addAttackMessageIfAllowed(List<MessageType> dispAllowedMessageTypeList, Village village,
            Optional<VillagePlayer> optVillagePlayer) {
        // ??????????????????????????????
        if (village.isVillageStatusCode???????????????() || village.isVillageStatusCode??????() || village.isVillageStatusCode??????()) {
            dispAllowedMessageTypeList.add(CDef.MessageType.????????????);
            return;
        }
        // ???????????????????????????????????????????????????????????????????????????????????????????????????
        if (!optVillagePlayer.isPresent()) {
            return;
        }
        VillagePlayer vPlayer = optVillagePlayer.get();
        CDef.Skill skill = vPlayer.getSkillCodeAsSkill();
        if (vPlayer.isIsDeadFalse() && skill != null && skill.isHasAttackAbility()) {
            dispAllowedMessageTypeList.add(CDef.MessageType.????????????);
            return;
        }
    }

    // ????????????
    private void addInvestigateMessageIfAllowed(List<MessageType> dispAllowedMessageTypeList, Village village,
            Optional<VillagePlayer> optVillagePlayer) {
        // ??????????????????????????????
        if (village.isVillageStatusCode???????????????() || village.isVillageStatusCode??????() || village.isVillageStatusCode??????()) {
            dispAllowedMessageTypeList.add(CDef.MessageType.??????????????????);
        }
    }

    // ????????????????????????????????????
    private void addSystemMessageIfAllowed(List<MessageType> dispAllowedMessageTypeList, Village village,
            Optional<VillagePlayer> optVillagePlayer) {
        // ??????????????????????????????
        if (village.isVillageStatusCode???????????????() || village.isVillageStatusCode??????() || village.isVillageStatusCode??????()) {
            dispAllowedMessageTypeList.add(CDef.MessageType.????????????????????????????????????);
            return;
        }
    }

    // ?????????
    private void addMonologueSayIfAllowed(List<MessageType> dispAllowedMessageTypeList, Village village,
            Optional<VillagePlayer> optVillagePlayer) {
        // ??????????????????????????????
        if (village.isVillageStatusCode???????????????() || village.isVillageStatusCode??????() || village.isVillageStatusCode??????()) {
            dispAllowedMessageTypeList.add(CDef.MessageType.?????????);
            return;
        }
        // ????????????????????????????????????????????????????????????????????????????????????????????????
    }

    // ??????
    private void addSecretSayIfAllowed(List<MessageType> dispAllowedMessageTypeList, Village village,
            Optional<VillagePlayer> optVillagePlayer) {
        // ??????????????????????????????
        if (village.isVillageStatusCode???????????????() || village.isVillageStatusCode??????() || village.isVillageStatusCode??????()) {
            dispAllowedMessageTypeList.add(CDef.MessageType.??????);
            return;
        }
        // ????????????????????????????????????????????????????????????????????????????????????????????????????????????
    }

    // ????????????
    private boolean isLatestDay(Integer villageId, int day) {
        ListResultBean<VillageDay> dayList = villageDayBhv.selectList(cb -> cb.query().setVillageId_Equal(villageId));
        return dayList.get(dayList.size() - 1).getDay().equals(day);
    }

    // ???
    private void addPrivateSayMessageForOwl(List<MessageType> dispAllowedMessageTypeList, Village village,
            Optional<VillagePlayer> optVillagePlayer) {
        if (!optVillagePlayer.isPresent() || !optVillagePlayer.get().isSkillCode???()) {
            return;
        }
        if (!dispAllowedMessageTypeList.contains(CDef.MessageType.???????????????)) {
            dispAllowedMessageTypeList.add(CDef.MessageType.???????????????);
        }
        if (!dispAllowedMessageTypeList.contains(CDef.MessageType.????????????)) {
            dispAllowedMessageTypeList.add(CDef.MessageType.????????????);
        }
        if (!dispAllowedMessageTypeList.contains(CDef.MessageType.????????????)) {
            dispAllowedMessageTypeList.add(CDef.MessageType.????????????);
        }
    }

    // ??????????????????????????????????????????????????????????????????
    private String makeSuddonlyDeathMessage(Village village, boolean isLatestDay, int day) {
        if (!isLatestDay || village.getVillageSettingsAsOne().get().isIsAvailableSuddonlyDeathFalse() || !village.isVillageStatusCode?????????()
                || day < 2) {
            return null;
        }
        Integer villageId = village.getVillageId();
        // ????????????
        ListResultBean<VillagePlayer> villagePlayerList = villagePlayerBhv.selectList(cb -> {
            cb.setupSelect_Chara();
            cb.query().setVillageId_Equal(villageId);
            cb.query().setIsDead_Equal_False();
            cb.query().setIsSpectator_Equal_False();
            cb.query().setIsGone_Equal_False();
        });
        // ???????????????
        List<Integer> voteCharaIdList = voteBhv.selectList(cb -> {
            cb.query().setVillageId_Equal(villageId);
            cb.query().setDay_Equal(day);
        }).stream().map(vote -> vote.getCharaId()).collect(Collectors.toList());
        // ????????????????????????
        List<String> noVoteCharaNameList = villagePlayerList.stream()
                .filter(vp -> !voteCharaIdList.contains(vp.getCharaId()))
                .map(vp -> vp.name())
                .collect(Collectors.toList());
        if (noVoteCharaNameList.size() == 0) {
            return null;
        }
        return String.format("??????????????????????????????????????????%s???\n\n???????????????????????????????????????????????????????????????", String.join("???", noVoteCharaNameList));
    }

    // ????????????????????????????????????
    private String makeCommitStatusMessage(Village village, boolean isLatestDay, int day) {
        if (village.getVillageSettingsAsOne().get().isIsAvailableCommitFalse() || !village.isVillageStatusCode?????????() || !isLatestDay) {
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
        return String.format("?????????????????????????????????????????????????????????????????????\n\n?????? %d/%d??? ?????????????????????????????????", commitCount, livingPersonNum);
    }
}
