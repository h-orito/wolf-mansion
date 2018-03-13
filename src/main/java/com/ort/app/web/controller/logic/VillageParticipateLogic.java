package com.ort.app.web.controller.logic;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import com.ort.dbflute.allcommon.CDef;
import com.ort.dbflute.exbhv.CharaBhv;
import com.ort.dbflute.exbhv.VillagePlayerBhv;
import com.ort.dbflute.exentity.VillagePlayer;

@Component
public class VillageParticipateLogic {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    @Autowired
    private VillagePlayerBhv villagePlayerBhv;

    @Autowired
    private MessageLogic messageLogic;

    @Autowired
    private CharaBhv charaBhv;

    @Autowired
    private MessageSource messageSource;

    // ===================================================================================
    //                                                                             Execute
    //                                                                             =======
    public void participate(Integer villageId, Integer playerId, Integer charaId, CDef.Skill requestSkill, String joinMessage) {
        // 村参加
        Integer villagePlayerId = insertVillagePlayer(villageId, playerId, charaId, requestSkill, joinMessage);
        // 参加メッセージ
        int participateNum = villagePlayerBhv.selectCount(cb -> cb.query().setVillageId_Equal(villageId));
        String charaName = charaBhv.selectEntityWithDeletedCheck(cb -> cb.query().setCharaId_Equal(charaId)).getCharaName();
        messageLogic.insertMessage(villageId, 0, CDef.MessageType.公開システムメッセージ, String.format("%d人目、%s。", participateNum, charaName));
        // 参加発言
        messageLogic.insertMessage(villageId, 0, CDef.MessageType.通常発言, joinMessage, villagePlayerId);
        // 希望役職メッセージ
        String message = messageSource.getMessage("requestskill.message", new String[] { charaName, requestSkill.alias() }, Locale.JAPAN);
        messageLogic.insertMessage(villageId, 0, CDef.MessageType.非公開システムメッセージ, message);
    }

    public void leave(VillagePlayer villagePlayer) {
        // プレイヤー削除
        villagePlayerBhv.queryDelete(cb -> {
            cb.query().setVillagePlayerId_Equal(villagePlayer.getVillagePlayerId());
        });
        // 退村発言
        String charaName =
                charaBhv.selectEntityWithDeletedCheck(cb -> cb.query().setCharaId_Equal(villagePlayer.getCharaId())).getCharaName();
        messageLogic.insertMessage(villagePlayer.getVillageId(), 0, CDef.MessageType.公開システムメッセージ, String.format("%sは村を去った。", charaName));
    }

    // ===================================================================================
    //                                                                              Update
    //                                                                              ======
    private Integer insertVillagePlayer(Integer villageId, Integer playerId, Integer charaId, CDef.Skill requestSkill, String joinMessage) {
        VillagePlayer villagePlayer = new VillagePlayer();
        villagePlayer.setVillageId(villageId);
        villagePlayer.setPlayerId(playerId);
        villagePlayer.setCharaId(charaId);
        villagePlayer.setIsDead_False();
        villagePlayer.setRequestSkillCodeAsSkill(requestSkill);
        villagePlayerBhv.insert(villagePlayer);
        return villagePlayer.getVillagePlayerId();
    }
}
