package com.ort.app.web.controller.logic;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import com.ort.dbflute.allcommon.CDef;
import com.ort.dbflute.allcommon.CDef.Skill;
import com.ort.dbflute.exbhv.MessageBhv;
import com.ort.dbflute.exentity.Message;
import com.ort.dbflute.exentity.VillagePlayer;
import com.ort.fw.util.WerewolfMansionDateUtil;

@Component
public class MessageLogic {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    @Autowired
    private MessageBhv messageBhv;

    @Autowired
    private MessageSource messageSource;

    // ===================================================================================
    //                                                                             Execute
    //                                                                             =======
    public void insertMessage(Integer villageId, int day, CDef.MessageType messageType, String content, Integer villagePlayerId,
            Integer playerId) {
        Message message = new Message();
        message.setVillageId(villageId);
        message.setDay(day);
        message.setVillagePlayerId(villagePlayerId);
        message.setPlayerId(playerId);
        message.setMessageTypeCodeAsMessageType(messageType);
        message.setMessageNumber(selectNextMessageNumber(villageId, messageType));
        message.setMessageContent(content);
        message.setMessageDatetime(WerewolfMansionDateUtil.currentLocalDateTime());
        messageBhv.insert(message);
    }

    public void insertMessage(Integer villageId, int day, CDef.MessageType messageType, String content, Integer villagePlayerId) {
        insertMessage(villageId, day, messageType, content, villagePlayerId, null);
    }

    public void insertMessage(Integer villageId, int day, CDef.MessageType messageType, String content) {
        insertMessage(villageId, day, messageType, content, null, null);
    }

    /**
     * 表ロックを取りつつ次の番号を返す
     * @param villageId
     * @param messageType
     * @return
     */
    public int selectNextMessageNumber(Integer villageId, CDef.MessageType messageType) {
        Integer maxNessageNumber = messageBhv.selectScalar(Integer.class).max(cb -> {
            cb.specify().columnMessageNumber();
            cb.query().setVillageId_Equal(villageId);
            cb.query().setMessageTypeCode_Equal_AsMessageType(messageType);
            cb.lockForUpdate();
        }).orElse(0);
        return maxNessageNumber + 1;
    }

    // 能力セットメッセージ登録
    public void insertAbilityMessage(Integer villageId, int day, Integer charaId, Integer targetCharaId,
            List<VillagePlayer> villagePlayerList, String footstep, boolean isDefault) {
        String message = makeAbilitySetMessage(charaId, targetCharaId, villagePlayerList, footstep, isDefault);
        insertMessage(villageId, day, CDef.MessageType.非公開システムメッセージ, message);
    }

    // 足音セットメッセージ登録
    public void insertFootstepMessage(Integer villageId, int day, Integer charaId, List<VillagePlayer> villagePlayerList, String footstep,
            boolean isDefault) {
        String message = makeFootstepSetMessage(charaId, villagePlayerList, footstep, isDefault);
        insertMessage(villageId, day, CDef.MessageType.非公開システムメッセージ, message);
    }

    // ===================================================================================
    //                                                                        Assist Logic
    //                                                                        ============
    private String makeAbilitySetMessage(Integer charaId, Integer targetCharaId, List<VillagePlayer> villagePlayerList, String footstep,
            boolean isDefault) {
        // 自分
        VillagePlayer myself = villagePlayerList.stream().filter(vp -> vp.getCharaId().equals(charaId)).findFirst().get();
        // 自分の役職
        Skill skill = myself.getSkillCodeAsSkill();
        // キャラ名
        String charaName = myself.getChara().get().getCharaName();
        // 対象キャラ名
        String targetCharaName = villagePlayerList.stream()
                .filter(vp -> vp.getCharaId().equals(targetCharaId))
                .findFirst()
                .get()
                .getChara()
                .get()
                .getCharaName();
        if (skill == CDef.Skill.人狼) {
            return messageSource.getMessage("ability.werewolf.message",
                    new String[] { charaName, targetCharaName, footstep, isDefault ? "（自動設定）" : "" }, Locale.JAPAN);
        } else if (skill == CDef.Skill.占い師) {
            return messageSource.getMessage("ability.seer.message",
                    new String[] { charaName, targetCharaName, footstep, isDefault ? "（自動設定）" : "" }, Locale.JAPAN);
        } else if (skill == CDef.Skill.狩人) {
            return messageSource.getMessage("ability.hunter.message",
                    new String[] { charaName, targetCharaName, isDefault ? "（自動設定）" : "" }, Locale.JAPAN);
        }
        return null;
    }

    private String makeFootstepSetMessage(Integer charaId, List<VillagePlayer> villagePlayerList, String footstep, boolean isDefault) {
        // キャラ名
        String charaName =
                villagePlayerList.stream().filter(vp -> vp.getCharaId().equals(charaId)).findFirst().get().getChara().get().getCharaName();
        return messageSource.getMessage("ability.foxmadman.message", new String[] { charaName, footstep, isDefault ? "（自動設定）" : "" },
                Locale.JAPAN);
    }
}
