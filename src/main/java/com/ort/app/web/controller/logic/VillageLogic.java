package com.ort.app.web.controller.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ort.dbflute.allcommon.CDef;
import com.ort.dbflute.exbhv.VillagePlayerBhv;
import com.ort.dbflute.exentity.VillagePlayer;

@Component
public class VillageLogic {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    @Autowired
    private VillagePlayerBhv villagePlayerBhv;

    @Autowired
    private MessageLogic messageLogic;

    // ===================================================================================
    //                                                                             Execute
    //                                                                             =======
    public void participate(Integer villageId, Integer playerId, Integer charaId, String joinMessage) {
        // 村参加
        Integer villagePlayerId = insertVillagePlayer(villageId, playerId, charaId, joinMessage);
        // 参加発言
        messageLogic.insertMessage(villageId, 0, CDef.MessageType.通常発言, joinMessage, villagePlayerId);
    }

    // ===================================================================================
    //                                                                              Update
    //                                                                              ======
    private Integer insertVillagePlayer(Integer villageId, Integer playerId, Integer charaId, String joinMessage) {
        VillagePlayer villagePlayer = new VillagePlayer();
        villagePlayer.setVillageId(villageId);
        villagePlayer.setPlayerId(playerId);
        villagePlayer.setCharaId(charaId);
        villagePlayer.setIsDead_False();
        villagePlayerBhv.insert(villagePlayer);
        return villagePlayer.getVillagePlayerId();
    }
}
