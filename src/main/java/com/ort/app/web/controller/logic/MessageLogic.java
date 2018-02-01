package com.ort.app.web.controller.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ort.dbflute.allcommon.CDef;
import com.ort.dbflute.exbhv.MessageBhv;
import com.ort.dbflute.exentity.Message;
import com.ort.fw.util.WerewolfMansionDateUtil;

@Component
public class MessageLogic {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    @Autowired
    private MessageBhv messageBhv;

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
}
