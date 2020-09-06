package com.ort.app.logic.daychange;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ort.dbflute.allcommon.CDef;
import com.ort.dbflute.exbhv.PlayerBhv;
import com.ort.dbflute.exentity.Player;
import com.ort.dbflute.exentity.VillagePlayer;

@Component
public class SuddenlyDeathLogic {

    @Autowired
    private DayChangeLogicHelper helper;
    @Autowired
    private PlayerBhv playerBhv;

    public void killNoVotePlayer(DayChangeVillage dayChangeVillage) {
        if (dayChangeVillage.settings.isIsAvailableSuddonlyDeathFalse() || dayChangeVillage.day < 3) {
            return;
        }
        // 前日に投票していない人が対象
        // 投票した人
        List<Integer> voteCharaIdList = dayChangeVillage.votes.filterYesterday(dayChangeVillage.day).list.stream() //
                .map(vote -> vote.getCharaId())
                .collect(Collectors.toList());
        // 投票していない人
        List<VillagePlayer> noVotePlayerList = dayChangeVillage.alivePlayers() //
                .filterNotDummy(dayChangeVillage.settings).list.stream() // ダミーでなく
                        .filter(vp -> !voteCharaIdList.contains(vp.getCharaId())) // 投票していない 
                        .collect(Collectors.toList());

        noVotePlayerList.forEach(vp -> {
            helper.updateVillagePlayerDead(dayChangeVillage.day, vp, CDef.DeadReason.突然); // 死亡処理
            updatePlayerRestrict(vp.getPlayerId()); // 入村制限
            String message = String.format("%sは突然死した。", vp.name());
            helper.insertMessage(dayChangeVillage, CDef.MessageType.公開システムメッセージ, message);
        });

        dayChangeVillage.deadPlayers.addAll(noVotePlayerList, CDef.DeadReason.突然);
    }

    private void updatePlayerRestrict(Integer playerId) {
        Player player = new Player();
        player.setPlayerId(playerId);
        player.setIsRestrictedParticipation_True();
        playerBhv.update(player);
    }
}
