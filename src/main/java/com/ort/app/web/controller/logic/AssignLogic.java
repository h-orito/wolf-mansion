package com.ort.app.web.controller.logic;

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

import com.ort.app.web.util.RoomUtil;
import com.ort.dbflute.allcommon.CDef;
import com.ort.dbflute.allcommon.CDef.Skill;
import com.ort.dbflute.exbhv.SkillBhv;
import com.ort.dbflute.exbhv.VillageBhv;
import com.ort.dbflute.exbhv.VillagePlayerBhv;
import com.ort.dbflute.exentity.Village;
import com.ort.dbflute.exentity.VillagePlayer;

@Component
public class AssignLogic {

    // ===================================================================================
    //                                                                          Definition
    //                                                                          ==========
    private static final Logger logger = LoggerFactory.getLogger(AssignLogic.class);

    private static Map<Integer, List<Integer>> roomNumberAssignMap;

    static {
        roomNumberAssignMap = RoomUtil.createRoomAssignMap();
    }

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    @Autowired
    private VillageBhv villageBhv;

    @Autowired
    private VillagePlayerBhv villagePlayerBhv;

    @Autowired
    private MessageLogic messageLogic;

    @Autowired
    private SkillBhv skillBhv;

    // ===================================================================================
    //                                                                             Execute
    //                                                                             =======
    // 役職を割り当てる
    public void assignSkill(Integer villageId, List<VillagePlayer> vPlayerList) {
        // 村人数に応じた役職人数Map（key: 役職, value: 役職人数）
        Map<CDef.Skill, Integer> skillPersonNumMap = makeSkillPersonNumMap(vPlayerList.size());
        // 更新用プレイヤーリスト
        List<VillagePlayer> updatePlayerList = new ArrayList<>();
        // 役職希望した人について、役職ごとの最大人数まで割り当てて更新用リストに追加
        addRequestToUpdatePlayerList(vPlayerList, skillPersonNumMap, updatePlayerList);
        // 希望していないもしくはまだ割り当てられていないプレイヤーを更新用リストに追加
        addNotRequestToUpdatePlayerList(vPlayerList, skillPersonNumMap, updatePlayerList);
        // update
        updatePlayerList.stream().forEach(player -> {
            logger.info("village_player_id: " + player.getVillagePlayerId() + " skill: " + player.getSkillCode());
            villagePlayerBhv.update(player);
        });
        // 役職割り当てについてメッセージ追加
        insertOrganizationMessage(villageId, skillPersonNumMap);
        insertAssignedMessage(villageId);
    }

    // 部屋を割り当てる
    public void assignRoom(Integer villageId, List<VillagePlayer> vPlayerList) {
        // 部屋サイズを決めて登録
        Village village = calculateRoomSizeAndUpdate(villageId, vPlayerList.size());
        List<Integer> roomNumList = new ArrayList<>();
        for (int i = 1; i <= village.getRoomSizeWidth() * village.getRoomSizeHeight(); i++) {
            roomNumList.add(i);
        }
        // 部屋を割り当てて登録
        assignRoomAndUpdate(vPlayerList, roomNumList);
        // デバッグ用
        printAssign(villageId, village);
        // 部屋割り当てメッセージ登録
        insertRoomAssignMessage(villageId);
    }

    // ===================================================================================
    //                                                                              Update
    //                                                                              ======
    private void insertOrganizationMessage(Integer villageId, Map<Skill, Integer> skillPersonNumMap) {
        StringJoiner joiner = new StringJoiner("、", "この館には、", "いるようだ。");
        skillBhv.selectList(cb -> {
            cb.query().addOrderBy_DispOrder_Asc();
        }).stream().forEach(skill -> {
            Skill s = skill.getSkillCodeAsSkill();
            if (skillPersonNumMap.containsKey(s) && skillPersonNumMap.get(s) > 0) {
                joiner.add(String.format("%sが%d名", s.alias(), skillPersonNumMap.get(s)));
            }
        });
        messageLogic.insertMessage(villageId, 1, CDef.MessageType.公開システムメッセージ, joiner.toString());
    }

    private void insertAssignedMessage(Integer villageId) {
        ListResultBean<VillagePlayer> playerList = villagePlayerBhv.selectList(cb -> {
            cb.setupSelect_Chara();
            cb.setupSelect_SkillByRequestSkillCode();
            cb.setupSelect_SkillBySkillCode();
            cb.query().setIsGone_Equal_False();
            cb.query().setVillageId_Equal(villageId);
        });
        String message = makeAssignedMessage(playerList);
        messageLogic.insertMessage(villageId, 1, CDef.MessageType.非公開システムメッセージ, message);
    }

    // ===================================================================================
    //                                                         Assist Logic (assign skill)
    //                                                                        ============
    // 村人数に応じた役職人数
    private Map<Skill, Integer> makeSkillPersonNumMap(int personNum) {
        Map<Skill, Integer> personNumMap = new HashMap<>();

        // 狼
        int wolfNum = personNum >= 18 ? 4 : personNum >= 13 ? 3 : 2;
        personNumMap.put(CDef.Skill.人狼, wolfNum);
        // 賢者
        int wiseNum = 1;
        personNumMap.put(CDef.Skill.賢者, wiseNum);
        // 導師
        int guruNum = 1;
        personNumMap.put(CDef.Skill.導師, guruNum);
        // 狩人
        int hunterNum = personNum >= 10 ? 1 : 0;
        personNumMap.put(CDef.Skill.狩人, hunterNum);
        // 共有
        int masonNum = personNum >= 13 ? 2 : 0;
        personNumMap.put(CDef.Skill.共鳴者, masonNum);
        // 狐
        int foxNum = personNum >= 15 ? 1 : 0;
        personNumMap.put(CDef.Skill.妖狐, foxNum);
        // 狂人
        int madmanNum = personNum >= 10 ? 1 : 0;
        personNumMap.put(CDef.Skill.狂人, madmanNum);
        // 村人
        int villagerNum = personNum - wolfNum - wiseNum - guruNum - hunterNum - masonNum - foxNum - madmanNum;
        personNumMap.put(CDef.Skill.村人, villagerNum);
        return personNumMap;
    }

    private void addRequestToUpdatePlayerList(List<VillagePlayer> vPlayerList, Map<CDef.Skill, Integer> skillPersonNumMap,
            List<VillagePlayer> updatePlayerList) {
        for (Entry<CDef.Skill, Integer> entry : skillPersonNumMap.entrySet()) {
            CDef.Skill skill = entry.getKey();
            int capacity = entry.getValue();
            // この役職を希望している人
            List<VillagePlayer> requestPlayerList =
                    vPlayerList.stream().filter(player -> skill == player.getRequestSkillCodeAsSkill()).collect(Collectors.toList());
            Collections.shuffle(requestPlayerList); // シャッフル
            int count = 0;
            if (skill == CDef.Skill.村人) {
                // ダミーは強制村人
                Integer dummyVillagePlayerId =
                        vPlayerList.stream().filter(p -> p.getChara().get().isIsDummyTrue()).findFirst().get().getVillagePlayerId();
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

    private void addNotRequestToUpdatePlayerList(List<VillagePlayer> playerList, Map<CDef.Skill, Integer> skillPersonNumMap,
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

    private VillagePlayer makeUpdateVillagePlayerBean(Integer villagePlayerId, CDef.Skill skill) {
        VillagePlayer villagePlayer = new VillagePlayer();
        villagePlayer.setVillagePlayerId(villagePlayerId);
        villagePlayer.setSkillCodeAsSkill(skill);
        return villagePlayer;
    }

    private String makeAssignedMessage(ListResultBean<VillagePlayer> playerList) {
        StringJoiner joiner = new StringJoiner("\n");
        for (VillagePlayer player : playerList) {
            joiner.add(String.format("%sの役職は%sになりました。（希望役職：%s）", player.getChara().get().getCharaName(),
                    player.getSkillBySkillCode().get().getSkillName(), player.getSkillByRequestSkillCode().get().getSkillName()));
        }
        return joiner.toString();
    }

    // ===================================================================================
    //                                                          Assist Logic (assign room)
    //                                                                        ============
    private Village calculateRoomSizeAndUpdate(Integer villageId, int personNum) {
        // 8-10 4x3 11 5x3 12-13 4x4 14-17 5x4 18-20 6x4
        // 8    9    10   11   
        // _xx_ _xx_ xxx_ _xxx_
        // xxxx xxxx xxxx xxxxx
        // _xx_ _xxx _xxx _xxx_
        // 
        // 12   13   14    15    16    17   
        // _xx_ _xx_ __xx_ __xx_ _xxx_ _xxx_
        // xxxx xxxx xxxxx xxxxx xxxxx xxxxx
        // xxxx xxxx xxxxx xxxxx xxxxx xxxxx
        // _xx_ _xxx _xx__ _xxx_ _xxx_ _xxxx
        //
        // 18     19     20
        // __xxx_ _xxxx_ _xxxx_
        // xxxxxx xxxxxx xxxxxx
        // xxxxxx xxxxxx xxxxxx
        // _xxx__ _xxx__ _xxxx_

        int width = personNum >= 18 ? 6 : personNum >= 14 ? 5 : personNum >= 12 ? 4 : personNum == 11 ? 5 : 4;
        int height = personNum >= 12 ? 4 : 3;

        Village village = new Village();
        village.setRoomSizeWidth(width);
        village.setRoomSizeHeight(height);
        villageBhv.queryUpdate(village, cb -> cb.query().setVillageId_Equal(villageId));
        return village;

        //        for (int width = 3; width <= 5; width++) {
        //            for (int height = width - 1; height <= width; height++) {
        //                // 最低でも3部屋空くようにする
        //                if (width * height >= personNum + 3) {
        //                    Village village = new Village();
        //                    village.setRoomSizeWidth(width);
        //                    village.setRoomSizeHeight(height);
        //                    villageBhv.queryUpdate(village, cb -> cb.query().setVillageId_Equal(villageId));
        //                    return village;
        //                }
        //            }
        //        }
        //        throw new IllegalStateException("村人数が多すぎ？ personNum: " + personNum);
    }

    private void insertRoomAssignMessage(Integer villageId) {
        ListResultBean<VillagePlayer> playerList = villagePlayerBhv.selectList(cb -> {
            cb.setupSelect_Chara();
            cb.query().setIsGone_Equal_False();
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

    private void printAssign(Integer villageId, Village village) {
        if (logger.isInfoEnabled()) {
            ListResultBean<VillagePlayer> vpList = villagePlayerBhv.selectList(cb -> {
                cb.setupSelect_Chara();
                cb.query().setVillageId_Equal(villageId);
                cb.query().setIsGone_Equal_False();
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

    private void assignRoomAndUpdate(List<VillagePlayer> playerList, List<Integer> roomNumList) {
        // 空き部屋をランダムにする場合
        //        Collections.shuffle(roomNumList);
        //        for (int i = 0; i < playerList.size(); i++) {
        //            VillagePlayer entity = new VillagePlayer();
        //            entity.setRoomNumber(roomNumList.get(i));
        //            VillagePlayer player = playerList.get(i);
        //            villagePlayerBhv.queryUpdate(entity, cb -> cb.query().setVillagePlayerId_Equal(player.getVillagePlayerId()));
        //            logger.info("部屋割り当て villagePlayerId: {}, roomNumber:{}", player.getVillagePlayerId(), roomNumList.get(i));
        //        }
        List<Integer> roomNumberList = new ArrayList<>(roomNumberAssignMap.get(playerList.size()));
        Collections.shuffle(roomNumberList);
        for (int i = 0; i < playerList.size(); i++) {
            VillagePlayer entity = new VillagePlayer();
            entity.setRoomNumber(roomNumberList.get(i));
            VillagePlayer player = playerList.get(i);
            villagePlayerBhv.queryUpdate(entity, cb -> cb.query().setVillagePlayerId_Equal(player.getVillagePlayerId()));
            logger.info("部屋割り当て villagePlayerId: {}, roomNumber:{}", player.getVillagePlayerId(), roomNumberList.get(i));
        }
    }
}
