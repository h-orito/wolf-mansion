package com.ort.app.logic.daychange;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ort.app.datasource.VillageService;
import com.ort.app.logic.MessageLogic;
import com.ort.app.logic.message.MessageEntity;
import com.ort.dbflute.allcommon.CDef;
import com.ort.dbflute.exentity.VillagePlayer;
import com.ort.dbflute.exentity.VillagePlayers;
import com.ort.dbflute.exentity.Vote;

@Component
public class ExecuteLogic {

    @Autowired
    private MessageLogic messageLogic;
    @Autowired
    private VillageService villageService;

    // 処刑
    public void execute(DayChangeVillage dayChangeVillage) {
        int day = dayChangeVillage.day;
        if (day == 2) {
            return; // 1→2日目は処刑なし
        }

        List<Vote> voteList = dayChangeVillage.votes //
                .filterYesterday(day) //
                .filterMylsefNotIn(dayChangeVillage.deadPlayers.getList()).list;
        if (CollectionUtils.isEmpty(voteList)) {
            return; // 全員突然死した場合
        }

        // 得票数トップのプレイヤーを取得（複数いる可能性があるのでリスト）
        Map<Integer, Integer> voteNumMap = new HashMap<>();
        List<Integer> executedCharaIdList = makeExecutedCandidateList(voteList, voteNumMap);
        VillagePlayer executedPlayer = decideExecutedPlayer(dayChangeVillage.vPlayers, executedCharaIdList);

        // 処刑
        if (dayChangeVillage.isAlive(executedPlayer)) {
            villageService.dead(executedPlayer, day, CDef.DeadReason.処刑);
        }
        // 個別投票メッセージ登録
        insertEachVoteMessage(dayChangeVillage, voteList);
        // 集計メッセージ登録
        insertExecuteResultMessage(dayChangeVillage, voteNumMap, executedPlayer);

        dayChangeVillage.deadPlayers.add(executedPlayer, CDef.DeadReason.処刑);
    }

    // 得票数トップの人の中から処刑する対象を決定
    private VillagePlayer decideExecutedPlayer(VillagePlayers vPlayers, List<Integer> executedCharaIdList) {
        VillagePlayers candidatePlayers = vPlayers.filterBy(vp -> executedCharaIdList.contains(vp.getCharaId()));
        // 強運者を除いて1名以上存在する場合は強運者を除いてランダム選出
        VillagePlayers withoutLuckymanPlayers = candidatePlayers.filterBy(vp -> vp.getSkillCodeAsSkill() != CDef.Skill.強運者);
        if (withoutLuckymanPlayers.list.size() > 0) {
            return withoutLuckymanPlayers.getRandom();
        }
        // そうでない場合は全体からランダム選出
        return candidatePlayers.getRandom();
    }

    // 得票数トップのプレイヤーを取得（複数いる可能性があるのでリスト）
    private List<Integer> makeExecutedCandidateList(List<Vote> voteList, Map<Integer, Integer> voteNumMap) {
        List<Integer> executedCharaIdList = new ArrayList<>();
        int maxVoteCount = 0;
        for (Vote vote : voteList) {
            Integer targetCharaId = vote.getVoteCharaId();
            if (voteNumMap.containsKey(targetCharaId)) {
                continue;
            }
            int voteCount = (int) voteList.stream().filter(v -> v.getVoteCharaId().equals(targetCharaId)).count();
            voteNumMap.put(targetCharaId, voteCount);
            if (maxVoteCount < voteCount) {
                maxVoteCount = voteCount;
                executedCharaIdList.clear();
                executedCharaIdList.add(targetCharaId);
            } else if (maxVoteCount == voteCount) {
                executedCharaIdList.add(targetCharaId);
            }
        }
        return executedCharaIdList;
    }

    private void insertEachVoteMessage(DayChangeVillage dayChangeVillage, List<Vote> voteList) {
        voteList.sort((v1, v2) -> {
            Integer roomNumber1 = dayChangeVillage.vPlayers.findByCharaId(v1.getCharaId()).getRoomNumber();
            Integer roomNumber2 = dayChangeVillage.vPlayers.findByCharaId(v2.getCharaId()).getRoomNumber();
            return roomNumber1 - roomNumber2;
        });

        StringJoiner joiner = new StringJoiner("\n");
        joiner.add("投票結果は以下の通り。");
        int playerMaxLength = getMaxPlayerNameLength(voteList, dayChangeVillage.vPlayers.list);
        int targetMaxLength = getMaxTargetNameLength(voteList, dayChangeVillage.vPlayers.list);
        for (Vote vote : voteList) {
            VillagePlayer player = dayChangeVillage.vPlayers.findByCharaId(vote.getCharaId());
            VillagePlayer targetPlayer = dayChangeVillage.vPlayers.findByCharaId(vote.getVoteCharaId());
            joiner.add(String.format("%s → %s", //
                    StringUtils.rightPad(player.name(), playerMaxLength, "　"), // 
                    StringUtils.rightPad(targetPlayer.name(), targetMaxLength, "　")));
        }
        messageLogic.saveIgnoreError(MessageEntity.systemBuilder(dayChangeVillage.villageId, dayChangeVillage.day) //
                .messageType(dayChangeVillage.settings.isIsOpenVoteTrue() ? CDef.MessageType.公開システムメッセージ : CDef.MessageType.非公開システムメッセージ)
                .content(joiner.toString())
                .build());
    }

    private int getMaxPlayerNameLength(List<Vote> voteList, List<VillagePlayer> vPlayerList) {
        int playerMaxLength = 0;
        for (Vote vote : voteList) {
            Integer charaId = vote.getCharaId();
            VillagePlayer player = vPlayerList.stream().filter(vp -> vp.getCharaId().equals(charaId)).findFirst().get();
            if (player.name().length() > playerMaxLength) {
                playerMaxLength = player.name().length();
            }
        }
        return playerMaxLength;
    }

    private int getMaxTargetNameLength(List<Vote> voteList, List<VillagePlayer> vPlayerList) {
        int targetMaxLength = 0;
        for (Vote vote : voteList) {
            Integer targetCharaId = vote.getVoteCharaId();
            VillagePlayer targetPlayer = vPlayerList.stream().filter(vp -> vp.getCharaId().equals(targetCharaId)).findFirst().get();
            if (targetPlayer.name().length() > targetMaxLength) {
                targetMaxLength = targetPlayer.name().length();
            }
        }
        return targetMaxLength;
    }

    private void insertExecuteResultMessage(DayChangeVillage dayChangeVillage, Map<Integer, Integer> voteNumMap,
            VillagePlayer executedPlayer) {
        List<Integer> sortedTargetCharaIdList = voteNumMap.keySet().stream().sorted((charaId1, charaId2) -> {
            Integer roomNumber1 = dayChangeVillage.vPlayers.findByCharaId(charaId1).getRoomNumber();
            Integer roomNumber2 = dayChangeVillage.vPlayers.findByCharaId(charaId2).getRoomNumber();
            return roomNumber1 - roomNumber2;
        }).collect(Collectors.toList());

        StringJoiner joiner = new StringJoiner("\n");
        for (Integer targetCharaId : sortedTargetCharaIdList) {
            Integer voteCount = voteNumMap.get(targetCharaId);
            VillagePlayer targetPlayer = dayChangeVillage.vPlayers.findByCharaId(targetCharaId);
            joiner.add(String.format("%s、%d票", targetPlayer.name(), voteCount));
        }
        joiner.add(String.format("\n%sは村人達の手により処刑された。", executedPlayer.name()));
        messageLogic.saveIgnoreError(MessageEntity.publicSystemBuilder(dayChangeVillage.villageId, dayChangeVillage.day) //
                .content(joiner.toString())
                .build());
    }
}
