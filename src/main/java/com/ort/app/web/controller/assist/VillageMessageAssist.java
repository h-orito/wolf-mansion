package com.ort.app.web.controller.assist;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.dbflute.cbean.result.ListResultBean;
import org.dbflute.optional.OptionalEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ort.app.web.form.VillageGetMessageListForm;
import com.ort.app.web.model.VillageMessageListResultContent;
import com.ort.app.web.model.inner.VillageMessageDto;
import com.ort.dbflute.allcommon.CDef;
import com.ort.dbflute.allcommon.CDef.MessageType;
import com.ort.dbflute.exbhv.MessageBhv;
import com.ort.dbflute.exbhv.VillageBhv;
import com.ort.dbflute.exbhv.VillageDayBhv;
import com.ort.dbflute.exbhv.VillagePlayerBhv;
import com.ort.dbflute.exentity.Message;
import com.ort.dbflute.exentity.Village;
import com.ort.dbflute.exentity.VillagePlayer;
import com.ort.fw.security.UserInfo;

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

    // ===================================================================================
    //                                                                             Execute
    //                                                                             =======
    public VillageMessageListResultContent getMessageList(VillageGetMessageListForm form, UserInfo userInfo) {
        Integer villageId = form.getVillageId();
        int day = form.getDay() != null ? form.getDay() : selectLatestDay(villageId);
        OptionalEntity<VillagePlayer> optVillagePlayer = selectVillagePlayer(villageId, userInfo);
        List<CDef.MessageType> messageTypeList = makeMessageTypeList(optVillagePlayer, villageId);
        ListResultBean<Message> messageList = selectMessageList(form.getVillageId(), day, messageTypeList, optVillagePlayer);
        VillageMessageListResultContent content = mappingToMessageListContent(messageList);
        return content;
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
            cb.query().setVillageId_Equal(villageId);
        });
    }

    private OptionalEntity<VillagePlayer> selectVillagePlayer(Integer villageId, UserInfo userInfo) {
        if (userInfo == null) {
            return OptionalEntity.empty();
        }
        return villagePlayerBhv.selectEntity(cb -> {
            cb.setupSelect_SkillBySkillCode();
            cb.query().setVillageId_Equal(villageId);
            cb.query().queryPlayer().setPlayerName_Equal(userInfo.getUsername());
        });
    }

    public int selectLatestDay(Integer villageId) {
        return villageDayBhv.selectScalar(Integer.class).max(cb -> {
            cb.specify().columnDay();
            cb.query().setVillageId_Equal(villageId);
        }).get();
    }

    // ===================================================================================
    //                                                                             Mapping
    //                                                                             =======
    private VillageMessageListResultContent mappingToMessageListContent(ListResultBean<Message> messageList) {
        VillageMessageListResultContent content = new VillageMessageListResultContent();
        content.setMessageList(convertToMessageList(messageList));
        return content;
    }

    private List<VillageMessageDto> convertToMessageList(List<Message> messageList) {
        return messageList.stream().map(message -> {
            VillageMessageDto messageDto = new VillageMessageDto();
            messageDto.setCharacterName(
                    message.getVillagePlayer().map(villagePlayer -> villagePlayer.getChara().get().getCharaName()).orElse(null));
            messageDto.setCharacterImageUrl(
                    message.getVillagePlayer().map(villagePlayer -> villagePlayer.getChara().get().getCharaImgUrl()).orElse(null));
            // エピ入ってないと表示しちゃだめ
            // messageDto.setPlayerName(
            //         message.getVillagePlayer().map(villagePlayer -> villagePlayer.getPlayer().get().getPlayerName()).orElse(null));
            messageDto.setMessageContent(message.getMessageContent());
            messageDto.setMessageDatetime(message.getMessageDatetime());
            messageDto.setMessageNumber(message.getMessageNumber());
            messageDto.setMessageType(message.getMessageTypeCodeAsMessageType().code());
            return messageDto;
        }).collect(Collectors.toList());
    }

    // ===================================================================================
    //                                                                        Assist Logic
    //                                                                        ============
    private List<CDef.MessageType> makeMessageTypeList(OptionalEntity<VillagePlayer> optVillagePlayer, Integer villageId) {
        Village village = selectVillage(villageId);
        List<CDef.MessageType> dispAllowedMessageTypeList =
                new ArrayList<>(Arrays.asList(CDef.MessageType.公開システムメッセージ, CDef.MessageType.通常発言));
        addGraveSayIfAllowed(dispAllowedMessageTypeList, village, optVillagePlayer);
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
        // 終了していたら全開放
        if (village.isVillageStatusCodeエピローグ() || village.isVillageStatusCode廃村() || village.isVillageStatusCode終了()) {
            dispAllowedMessageTypeList.add(CDef.MessageType.死者の呻き);
            return;
        }
        // 終了していなかったら参加していて突然死以外で死亡している場合のみ開放
        if (!optVillagePlayer.isPresent()) {
            return;
        }
        VillagePlayer vPlayer = optVillagePlayer.get();
        if (vPlayer.isIsDeadTrue() && !vPlayer.isDeadReasonCode突然()) {
            dispAllowedMessageTypeList.add(CDef.MessageType.死者の呻き);
            return;
        }
    }

    // 共鳴発言
    private void addMasonSayIfAllowed(List<MessageType> dispAllowedMessageTypeList, Village village,
            OptionalEntity<VillagePlayer> optVillagePlayer) {
        // 終了していたら全開放
        if (village.isVillageStatusCodeエピローグ() || village.isVillageStatusCode廃村() || village.isVillageStatusCode終了()) {
            dispAllowedMessageTypeList.add(CDef.MessageType.共鳴発言);
            return;
        }
        // 終了していなかったら参加していて死亡しておらず、共鳴者だったら開放
        if (!optVillagePlayer.isPresent()) {
            return;
        }
        VillagePlayer vPlayer = optVillagePlayer.get();
        if (vPlayer.isIsDeadFalse() && vPlayer.getSkillCodeAsSkill() == CDef.Skill.共有者) {
            dispAllowedMessageTypeList.add(CDef.MessageType.共鳴発言);
            return;
        }
    }

    // 人狼の囁き
    private void addWerewolfSayIfAllowed(List<MessageType> dispAllowedMessageTypeList, Village village,
            OptionalEntity<VillagePlayer> optVillagePlayer) {
        // 終了していたら全開放
        if (village.isVillageStatusCodeエピローグ() || village.isVillageStatusCode廃村() || village.isVillageStatusCode終了()) {
            dispAllowedMessageTypeList.add(CDef.MessageType.人狼の囁き);
            return;
        }
        // 終了していなかったら参加していて死亡しておらず、人狼だったら開放
        if (!optVillagePlayer.isPresent()) {
            return;
        }
        VillagePlayer vPlayer = optVillagePlayer.get();
        if (vPlayer.isIsDeadFalse() && vPlayer.getSkillCodeAsSkill() == CDef.Skill.人狼) {
            dispAllowedMessageTypeList.add(CDef.MessageType.人狼の囁き);
            return;
        }
    }

    // 占い結果
    private void addSeerMessageIfAllowed(List<MessageType> dispAllowedMessageTypeList, Village village,
            OptionalEntity<VillagePlayer> optVillagePlayer) {
        // 終了していたら全開放
        if (village.isVillageStatusCodeエピローグ() || village.isVillageStatusCode廃村() || village.isVillageStatusCode終了()) {
            dispAllowedMessageTypeList.add(CDef.MessageType.占い結果);
            return;
        }
        // 終了していなかったら参加していて死亡しておらず、占い師だったら開放
        if (!optVillagePlayer.isPresent()) {
            return;
        }
        VillagePlayer vPlayer = optVillagePlayer.get();
        if (vPlayer.isIsDeadFalse() && vPlayer.getSkillCodeAsSkill() == CDef.Skill.占い師) {
            dispAllowedMessageTypeList.add(CDef.MessageType.占い結果);
            return;
        }
    }

    // 霊視結果
    private void addPsychicMessageIfAllowed(List<MessageType> dispAllowedMessageTypeList, Village village,
            OptionalEntity<VillagePlayer> optVillagePlayer) {
        // 終了していたら全開放
        if (village.isVillageStatusCodeエピローグ() || village.isVillageStatusCode廃村() || village.isVillageStatusCode終了()) {
            dispAllowedMessageTypeList.add(CDef.MessageType.霊視結果);
            return;
        }
        // 終了していなかったら参加していて死亡しておらず、霊能者だったら開放
        if (!optVillagePlayer.isPresent()) {
            return;
        }
        VillagePlayer vPlayer = optVillagePlayer.get();
        if (vPlayer.isIsDeadFalse() && vPlayer.getSkillCodeAsSkill() == CDef.Skill.霊能者) {
            dispAllowedMessageTypeList.add(CDef.MessageType.霊視結果);
            return;
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
            dispAllowedMessageTypeList.add(CDef.MessageType.人狼の囁き);
            return;
        }
        // 終了していなかったら自分自身のだけ表示なので、ここでは追加しない
    }

}
