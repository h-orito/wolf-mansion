package com.ort.app.web.controller.logic;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.StringJoiner;
import java.util.stream.Collectors;

import org.dbflute.cbean.result.ListResultBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ort.dbflute.allcommon.CDef;
import com.ort.dbflute.allcommon.CDef.Skill;
import com.ort.dbflute.allcommon.CDef.VillageStatus;
import com.ort.dbflute.exbhv.VillageBhv;
import com.ort.dbflute.exbhv.VillageDayBhv;
import com.ort.dbflute.exbhv.VillagePlayerBhv;
import com.ort.dbflute.exentity.Village;
import com.ort.dbflute.exentity.VillageDay;
import com.ort.dbflute.exentity.VillagePlayer;
import com.ort.dbflute.exentity.VillageSettings;
import com.ort.fw.util.WerewolfMansionDateUtil;

@Component
public class DayChangeLogic {

    // ===================================================================================
    //                                                                          Definition
    //                                                                          ==========
    private static final Logger logger = LoggerFactory.getLogger(DayChangeLogic.class);

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    @Autowired
    VillageBhv villageBhv;

    @Autowired
    VillageDayBhv villageDayBhv;

    @Autowired
    VillagePlayerBhv villagePlayerBhv;

    @Autowired
    MessageLogic messageLogic;

    // ===================================================================================
    //                                                                             Execute
    //                                                                             =======
    public void dayChangeIfNeeded(Integer villageId) {
        VillageDay maxVillageDay = selectMaxVillageDay(villageId);
        VillageSettings settings = maxVillageDay.getVillage().get().getVillageSettingsAsOne().get();
        LocalDateTime daychangeDatetime = maxVillageDay.getDaychangeDatetime();
        Integer intervalSeconds = settings.getDayChangeIntervalSeconds();
        LocalDateTime nextDaychangeDatetime = daychangeDatetime.plusSeconds(intervalSeconds);
        Integer day = maxVillageDay.getDay();

        // 日付更新の必要がなければ終了
        if (!shouldChangeDay(villageId, daychangeDatetime)) {
            return;
        }

        // 最低開始人数を満たしていない
        List<VillagePlayer> villagePlayerList = maxVillageDay.getVillage().get().getVillagePlayerList();
        if (villagePlayerList.size() < settings.getStartPersonMinNum()) {
            // 村日付を1日延長
            updateVillageDayTransactional(villageId, day, nextDaychangeDatetime);
            // 延長メッセージ登録
            messageLogic.insertMessage(villageId, day, CDef.MessageType.公開システムメッセージ, "まだ村人たちは揃っていないようだ。");
            return;
        }

        // 村日付を追加
        insertVillageDayTransactional(villageId, day + 1, nextDaychangeDatetime);

        // 日付切り替え処理
        if (day == 0) {
            // 1日目
            assignSkill(villageId); // 役職割り当て
            assignRoom(villageId);
            updateVillageStatus(villageId, CDef.VillageStatus.進行中); // 村ステータス更新
        } else {
            // 

        }

    }

    // ===================================================================================
    //                                                                              Select
    //                                                                              ======
    private VillageDay selectMaxVillageDay(Integer villageId) {
        VillageDay villageDay = villageDayBhv.selectEntity(cb -> {
            cb.setupSelect_Village().withVillageSettingsAsOne();
            cb.query().setVillageId_Equal(villageId);
            cb.query().addOrderBy_Day_Desc();
            cb.fetchFirst(1);
        }).get();
        villageDayBhv.load(villageDay, loader -> {
            loader.pulloutVillage().loadVillagePlayer(cb -> {});
        });
        return villageDay;
    }

    private Village selectVillage(Integer villageId) {
        Village village = villageBhv.selectEntityWithDeletedCheck(cb -> {
            cb.setupSelect_VillageSettingsAsOne();
            cb.query().setVillageId_Equal(villageId);
        });
        villageBhv.loadVillagePlayer(village, cb -> {});
        return village;
    }

    // ===================================================================================
    //                                                                              Update
    //                                                                              ======
    private void updateVillageStatus(Integer villageId, CDef.VillageStatus status) {
        Village village = new Village();
        village.setVillageStatusCodeAsVillageStatus(status);
        villageBhv.queryUpdate(village, cb -> cb.query().setVillageId_Equal(villageId));
    }

    // 村日付のみ即コミットされるようにする。publicでないと効かないので注意。
    public void updateVillageDayTransactional(Integer villageId, int day, LocalDateTime nextDaychangeDatetime) {
        VillageDay villageDay = new VillageDay();
        villageDay.setVillageId(villageId);
        villageDay.setDay(day);
        villageDay.setDaychangeDatetime(nextDaychangeDatetime);
        villageDayBhv.update(villageDay);
    }

    @Transactional(rollbackFor = Exception.class) // 念のため検査例外でもロールバックされるようにしておく
    public void insertVillageDayTransactional(Integer villageId, int day, LocalDateTime nextDayChangeDatetime) {
        VillageDay villageDay = new VillageDay();
        villageDay.setVillageId(villageId);
        villageDay.setDay(day);
        villageDay.setDaychangeDatetime(nextDayChangeDatetime);
        villageDayBhv.insert(villageDay);
    }

    private void assignRoomAndUpdate(ListResultBean<VillagePlayer> playerList, List<Integer> roomNumList) {
        Collections.shuffle(roomNumList);
        for (int i = 0; i < playerList.size(); i++) {
            VillagePlayer entity = new VillagePlayer();
            entity.setRoomNumber(roomNumList.get(i));
            VillagePlayer player = playerList.get(i);
            villagePlayerBhv.queryUpdate(entity, cb -> cb.query().setVillagePlayerId_Equal(player.getVillagePlayerId()));
            logger.info("部屋割り当て villagePlayerId: {}, roomNumber:{}", player.getVillagePlayerId(), roomNumList.get(i));
        }
    }

    // ===================================================================================
    //                                                                        Assist Logic
    //                                                                        ============
    // 日付更新の必要があるか
    private boolean shouldChangeDay(Integer villageId, LocalDateTime nextDayChangeDatetime) {
        // 村が終了していたら必要なし
        Village village = selectVillage(villageId);
        VillageStatus villageStatus = village.getVillageStatusCodeAsVillageStatus();
        if (villageStatus == CDef.VillageStatus.廃村 || villageStatus == CDef.VillageStatus.終了) {
            return false;
        }
        // 最新の村日付の更新時間を過ぎていない
        if (WerewolfMansionDateUtil.currentLocalDateTime().isBefore(nextDayChangeDatetime)) {
            return false;
        }

        return true;
    }

    // 役職を割り当てる
    private void assignSkill(Integer villageId) {
        ListResultBean<VillagePlayer> playerList = villagePlayerBhv.selectList(cb -> {
            cb.setupSelect_Chara();
            cb.query().setVillageId_Equal(villageId);
        });
        Map<CDef.Skill, Integer> skillPersonNumMap = makeSkillPersonNumMap(playerList.size());
        // 更新用プレイヤーリスト
        List<VillagePlayer> updatePlayerList = new ArrayList<>();
        // 役職希望した人について、役職ごとの最大人数まで割り当てて更新用リストに追加
        addRequestToUpdatePlayerList(playerList, skillPersonNumMap, updatePlayerList);
        // 希望していないもしくはまだ割り当てられていないプレイヤーを更新用リストに追加
        addNotRequestToUpdatePlayerList(playerList, skillPersonNumMap, updatePlayerList);
        // update
        updatePlayerList.stream().forEach(player -> {
            logger.info("village_player_id: " + player.getVillagePlayerId() + " skill: " + player.getSkillCode());
            villagePlayerBhv.update(player);
        });
        // 役職割り当てについてメッセージ追加
        insertAssignedMessage(villageId);
    }

    // 部屋を割り当てる
    private void assignRoom(Integer villageId) {
        ListResultBean<VillagePlayer> playerList = villagePlayerBhv.selectList(cb -> {
            cb.query().setVillageId_Equal(villageId);
        });
        // 部屋サイズを決めて登録
        Village village = calculateRoomSizeAndUpdate(villageId, playerList.size());
        List<Integer> roomNumList = new ArrayList<>();
        for (int i = 1; i <= village.getRoomSizeWidth() * village.getRoomSizeHeight(); i++) {
            roomNumList.add(i);
        }
        // 部屋を割り当てて登録
        assignRoomAndUpdate(playerList, roomNumList);
        // デバッグ用
        printAssign(villageId, village);
        // 部屋割り当てメッセージ登録
        insertRoomAssignMessage(villageId);
    }

    private Village calculateRoomSizeAndUpdate(Integer villageId, int personNum) {
        for (int width = 3; width <= 5; width++) {
            for (int height = width - 1; height <= width; height++) {
                // 最低でも3部屋空くようにする
                if (width * height >= personNum + 3) {
                    Village village = new Village();
                    village.setRoomSizeWidth(width);
                    village.setRoomSizeHeight(height);
                    villageBhv.queryUpdate(village, cb -> cb.query().setVillageId_Equal(villageId));
                    return village;
                }
            }
        }
        throw new IllegalStateException("村人数が多すぎ？ personNum: " + personNum);
    }

    private void insertAssignedMessage(Integer villageId) {
        ListResultBean<VillagePlayer> playerList = villagePlayerBhv.selectList(cb -> {
            cb.setupSelect_Chara();
            cb.setupSelect_SkillByRequestSkillCode();
            cb.setupSelect_SkillBySkillCode();
            cb.query().setVillageId_Equal(villageId);
        });
        String message = makeAssignedMessage(playerList);
        messageLogic.insertMessage(villageId, 1, CDef.MessageType.非公開システムメッセージ, message);
    }

    private String makeAssignedMessage(ListResultBean<VillagePlayer> playerList) {
        StringJoiner joiner = new StringJoiner("\n");
        for (VillagePlayer player : playerList) {
            joiner.add(String.format("%sの役職は%sになりました。（希望役職：%s）", player.getChara().get().getCharaName(),
                    player.getSkillBySkillCode().get().getSkillName(), player.getSkillByRequestSkillCode().get().getSkillName()));
        }
        return joiner.toString();
    }

    private void insertRoomAssignMessage(Integer villageId) {
        ListResultBean<VillagePlayer> playerList = villagePlayerBhv.selectList(cb -> {
            cb.setupSelect_Chara();
            cb.query().setVillageId_Equal(villageId);
        });
        String message = makeRoomAssignedMessage(playerList);
        messageLogic.insertMessage(villageId, 1, CDef.MessageType.公開システムメッセージ, message);
    }

    private String makeRoomAssignedMessage(ListResultBean<VillagePlayer> playerList) {
        StringJoiner joiner = new StringJoiner("\n", "人狼の痕跡を残すため、村人たちはそれぞれ別の部屋で夜を明かすことにした。\n\n", "");
        for (VillagePlayer player : playerList) {
            joiner.add(String.format("[%s] %sには、部屋番号%02dが割り当てられた。", player.getChara().get().getCharaShortName(),
                    player.getChara().get().getCharaName(), player.getRoomNumber()));
        }
        return joiner.toString();
    }

    private void addNotRequestToUpdatePlayerList(ListResultBean<VillagePlayer> playerList, Map<CDef.Skill, Integer> skillPersonNumMap,
            List<VillagePlayer> updatePlayerList) {
        List<VillagePlayer> noAssignedPlayer = playerList.stream()
                .filter(player -> updatePlayerList.stream()
                        .noneMatch(updPlayer -> updPlayer.getVillagePlayerId().equals(player.getVillagePlayerId())))
                .collect(Collectors.toList());
        Collections.shuffle(noAssignedPlayer); // シャッフル
        noAssignedPlayer.forEach(player -> {
            // 枠が余っている役職を探す
            CDef.Skill skill = findNotPopularSkill(skillPersonNumMap, updatePlayerList);
            updatePlayerList.add(makeUpdateVillagePlayerBean(player.getVillagePlayerId(), skill));
        });
    }

    private void addRequestToUpdatePlayerList(ListResultBean<VillagePlayer> playerList, Map<CDef.Skill, Integer> skillPersonNumMap,
            List<VillagePlayer> updatePlayerList) {
        for (Entry<CDef.Skill, Integer> entry : skillPersonNumMap.entrySet()) {
            CDef.Skill skill = entry.getKey();
            int capacity = entry.getValue();
            // この役職を希望している人
            List<VillagePlayer> requestPlayerList =
                    playerList.stream().filter(player -> skill == player.getRequestSkillCodeAsSkill()).collect(Collectors.toList());
            Collections.shuffle(requestPlayerList); // シャッフル
            int count = 0;
            if (skill == CDef.Skill.村人) {
                // ダミーは強制村人
                Integer dummyVillagePlayerId =
                        playerList.stream().filter(p -> p.getChara().get().isIsDummyTrue()).findFirst().get().getVillagePlayerId();
                updatePlayerList.add(makeUpdateVillagePlayerBean(dummyVillagePlayerId, CDef.Skill.村人));
                count++;
            }
            for (VillagePlayer requestedPlayer : requestPlayerList) {
                if (count >= capacity || requestedPlayer.getPlayerId().equals(1)) {
                    break;
                }
                updatePlayerList.add(makeUpdateVillagePlayerBean(requestedPlayer.getVillagePlayerId(), skill));
                count++;
            }
        }
    }

    private VillagePlayer makeUpdateVillagePlayerBean(Integer villagePlayerId, CDef.Skill skill) {
        VillagePlayer villagePlayer = new VillagePlayer();
        villagePlayer.setVillagePlayerId(villagePlayerId);
        villagePlayer.setSkillCodeAsSkill(skill);
        return villagePlayer;
    }

    private Skill findNotPopularSkill(Map<Skill, Integer> skillPersonNumMap, List<VillagePlayer> updatePlayerList) {
        for (Entry<CDef.Skill, Integer> entry : skillPersonNumMap.entrySet()) {
            CDef.Skill skill = entry.getKey();
            int capacity = entry.getValue();
            long assignedPlayerNum = updatePlayerList.stream().filter(player -> player.getSkillCodeAsSkill() == skill).count();
            if (assignedPlayerNum < capacity) {
                return skill;
            }
        }
        return null;
    }

    // 村人数に応じた役職人数
    private Map<Skill, Integer> makeSkillPersonNumMap(int personNum) {
        Map<Skill, Integer> personNumMap = new HashMap<>();

        // 狼
        int wolfNum = personNum >= 13 ? 3 : personNum >= 6 ? 2 : 1;
        personNumMap.put(CDef.Skill.人狼, wolfNum);
        // 占
        int seerNum = 1;
        personNumMap.put(CDef.Skill.占い師, seerNum);
        // 霊
        int mediumNum = 1;
        personNumMap.put(CDef.Skill.霊能者, mediumNum);
        // 狩人
        int hunterNum = personNum >= 11 ? 1 : 0;
        personNumMap.put(CDef.Skill.狩人, hunterNum);
        // 狂人
        int madmanNum = personNum >= 11 ? 1 : 0;
        personNumMap.put(CDef.Skill.狂人, madmanNum);
        // 共有
        int masonNum = personNum >= 16 ? 2 : 0;
        personNumMap.put(CDef.Skill.共有者, masonNum);
        // 狐
        int foxNum = personNum >= 17 ? 1 : 0;
        personNumMap.put(CDef.Skill.妖狐, foxNum);
        // 村人
        int villagerNum = personNum - wolfNum - seerNum - mediumNum - hunterNum - madmanNum - masonNum - foxNum;
        personNumMap.put(CDef.Skill.村人, villagerNum);

        return personNumMap;
    }

    private void printAssign(Integer villageId, Village village) {
        if (logger.isInfoEnabled()) {
            ListResultBean<VillagePlayer> vpList = villagePlayerBhv.selectList(cb -> {
                cb.setupSelect_Chara();
                cb.query().setVillageId_Equal(villageId);
                cb.query().addOrderBy_RoomNumber_Asc();
            });
            for (int i = 0; i < village.getRoomSizeHeight(); i++) {
                StringJoiner joiner = new StringJoiner(" ");

                for (int j = 0; j < village.getRoomSizeWidth(); j++) {
                    int num = village.getRoomSizeWidth() * i + j + 1;
                    String playerName = vpList.stream()
                            .filter(vplayer -> vplayer.getRoomNumber().equals(num))
                            .findFirst()
                            .map(vplayer -> vplayer.getChara().get().getCharaShortName())
                            .orElse("＿");
                    joiner.add(playerName);
                }
                logger.info(joiner.toString());
            }
        }
    }

}
