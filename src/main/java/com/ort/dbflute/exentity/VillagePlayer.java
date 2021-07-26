package com.ort.dbflute.exentity;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.ort.dbflute.allcommon.CDef;
import com.ort.dbflute.allcommon.CDef.DeadReason;
import com.ort.dbflute.bsentity.BsVillagePlayer;

/**
 * The entity of village_player.
 * <p>
 * You can implement your original methods here.
 * This class remains when re-generating.
 * </p>
 * @author DBFlute(AutoGenerator)
 */
public class VillagePlayer extends BsVillagePlayer {

    /** The serial version UID for object serialization. (Default) */
    private static final long serialVersionUID = 1L;

    public boolean hasLover() {
        return getVillagePlayerStatusByVillagePlayerIdList().stream()
                .anyMatch(vpSt -> vpSt.getVillagePlayerStatusCodeAsVillagePlayerStatusType() == CDef.VillagePlayerStatusType.後追い);
    }

    public boolean isFoxPossessioning() {
        return getVillagePlayerStatusByVillagePlayerIdList().stream()
                .anyMatch(vpSt -> vpSt.getVillagePlayerStatusCodeAsVillagePlayerStatusType() == CDef.VillagePlayerStatusType.狐憑き);
    }

    public boolean isFoxPossessioned() {
        return getVillagePlayerStatusByToVillagePlayerIdList().stream()
                .anyMatch(vpSt -> vpSt.getVillagePlayerStatusCodeAsVillagePlayerStatusType() == CDef.VillagePlayerStatusType.狐憑き);
    }

    // 同棲者の相方を取得
    public VillagePlayer getTargetCohabitor() {
        List<VillagePlayer> cohabitorList = this.getTargetLovers().filterBySkill(CDef.Skill.同棲者).list;
        if (cohabitorList.isEmpty()) {
            throw new IllegalStateException("同棲者がいません");
        }
        return cohabitorList.get(0); // 同棲者同士は重複しないので1人目でok
    }

    public VillagePlayers getTargetLovers() {
        List<VillagePlayer> playerList = getVillagePlayerStatusByVillagePlayerIdList().stream()
                .filter(vpSt -> vpSt.getVillagePlayerStatusCodeAsVillagePlayerStatusType() == CDef.VillagePlayerStatusType.後追い)
                .map(vpSt -> vpSt.getVillagePlayerByToVillagePlayerId().get())
                .collect(Collectors.toList());
        return new VillagePlayers(playerList);
    }

    public String name() {
        Integer roomNumber = getRoomNumber();
        String shortName = getCharaShortName();
        String name = getCharaName();
        return makeCharaName(roomNumber, shortName, name);
    }

    public String name(int day) {
        Integer roomNumber = getRoomNumberWhen(day);
        String shortName = getCharaShortName();
        String name = getCharaName();
        return makeCharaName(roomNumber, shortName, name);
    }

    public String shortName() {
        Integer roomNumber = getRoomNumber();
        String shortName = getCharaShortName();
        return makeCharaShortName(roomNumber, shortName);
    }

    public String shortName(int day) {
        Integer roomNumber = getRoomNumberWhen(day);
        String shortName = getCharaShortName();
        return makeCharaShortName(roomNumber, shortName);
    }

    public boolean isDeadWhen(int day) {
        List<VillagePlayerDeadHistory> historyList = getVillagePlayerDeadHistoryList();
        // 最後に死んだ日
        Optional<VillagePlayerDeadHistory> optLastDeadHistory = historyList.stream()
                .filter(history -> history.isIsDeadTrue() && history.getDay() <= day)
                .max(Comparator.comparing(VillagePlayerDeadHistory::getDay));
        // 死んでいなければ生きている
        if (!optLastDeadHistory.isPresent()) {
            return false;
        }
        // 最後に死んだ日
        Integer lastDeadDay = optLastDeadHistory.get().getDay();
        DeadReason deadReason = optLastDeadHistory.get().getDeadReasonCodeAsDeadReason();
        // 最後に死んだ日以降、今日までに生き返っていれば生存、生き返っていなければ死亡
        // 死んだ日と生き返った日が同じ場合、後追なら死亡、そうでなければ生きている
        boolean existsRevive = false;
        if (deadReason == CDef.DeadReason.後追) {
            existsRevive = historyList.stream()
                    .anyMatch(history -> history.isIsDeadFalse() && history.getDay() <= day && history.getDay() > lastDeadDay);
        } else {
            existsRevive = historyList.stream()
                    .anyMatch(history -> history.isIsDeadFalse() && history.getDay() <= day && history.getDay() >= lastDeadDay);
        }
        if (existsRevive) {
            return false;
        }
        return true;
    }

    public boolean existsDeadHistory(int day, CDef.DeadReason reason) {
        return getVillagePlayerDeadHistoryList().stream().anyMatch(history -> {
            return history.getDay().equals(day) && history.isIsDeadTrue() && history.getDeadReasonCodeAsDeadReason() == reason;
        });
    }

    public boolean existsMiserableDeadHistory(int day) {
        return getVillagePlayerDeadHistoryList().stream().anyMatch(history -> {
            return history.getDay().equals(day) && history.isIsDeadTrue() && history.isDeadReasonCode_Miserable();
        });
    }

    public boolean existsReviveHistory(int day) {
        return getVillagePlayerDeadHistoryList().stream().anyMatch(history -> {
            return history.getDay().equals(day) && history.isIsDeadFalse();
        });
    }

    public boolean isAliveWhen(int day) {
        return !isDeadWhen(day);
    }

    public Integer getRoomNumberWhen(int day) {
        List<VillagePlayerRoomHistory> historyList = getVillagePlayerRoomHistoryList();
        if (historyList.isEmpty()) {
            return null;
        }
        Optional<VillagePlayerRoomHistory> optLastHistory =
                historyList.stream().filter(history -> history.getDay() <= day).max(Comparator.comparing(VillagePlayerRoomHistory::getDay));
        if (!optLastHistory.isPresent()) {
            return null;
        }
        return optLastHistory.get().getRoomNumber();
    }

    public CDef.FaceType detectDefaultFaceType(CDef.MessageType messageType) {
        return getChara().get().detectDefaultFaceType(messageType);
    }

    // ===================================================================================
    //                                                                        Assist Logic
    //                                                                        ============
    private String makeCharaName(Integer roomNumber, String shortName, String name) {
        if (roomNumber == null) {
            return String.format("[%s] %s", shortName, name);
        }
        return String.format("[%02d%s] %s", roomNumber, shortName, name);
    }

    public static String makeCharaShortName(Integer roomNumber, String shortName) {
        if (roomNumber == null) {
            return shortName;
        }
        return String.format("%02d%s", roomNumber, shortName);
    }
}
