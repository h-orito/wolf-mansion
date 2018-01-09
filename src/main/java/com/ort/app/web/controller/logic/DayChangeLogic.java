package com.ort.app.web.controller.logic;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.StringJoiner;
import java.util.stream.Collectors;

import org.apache.commons.lang3.BooleanUtils;
import org.dbflute.cbean.result.ListResultBean;
import org.dbflute.optional.OptionalEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.ort.dbflute.allcommon.CDef;
import com.ort.dbflute.allcommon.CDef.Skill;
import com.ort.dbflute.allcommon.CDef.VillageStatus;
import com.ort.dbflute.exbhv.AbilityBhv;
import com.ort.dbflute.exbhv.VillageBhv;
import com.ort.dbflute.exbhv.VillageDayBhv;
import com.ort.dbflute.exbhv.VillagePlayerBhv;
import com.ort.dbflute.exbhv.VoteBhv;
import com.ort.dbflute.exentity.Ability;
import com.ort.dbflute.exentity.Village;
import com.ort.dbflute.exentity.VillageDay;
import com.ort.dbflute.exentity.VillagePlayer;
import com.ort.dbflute.exentity.VillageSettings;
import com.ort.dbflute.exentity.Vote;
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
    AbilityBhv abilityBhv;

    @Autowired
    VoteBhv voteBhv;

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
        int newDay = day + 1;
        insertVillageDayTransactional(villageId, newDay, nextDaychangeDatetime);

        // 日付切り替え処理
        if (day == 0) {
            // 1日目
            assignSkill(villageId); // 役職割り当て
            assignRoom(villageId); // 部屋割り当て
            updateVillageStatus(villageId, CDef.VillageStatus.進行中); // 村ステータス更新
            setDefaultVoteAndAbility(villageId, newDay); // 投票、能力行使のデフォルト設定
        } else {
            // 1日目以外
            dayChange(villageId, newDay);
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

    private ListResultBean<VillagePlayer> selectVillagePlayerList(Integer villageId) {
        ListResultBean<VillagePlayer> villagePlayerList = villagePlayerBhv.selectList(cb -> {
            cb.setupSelect_Chara();
            cb.setupSelect_SkillBySkillCode();
            cb.query().setVillageId_Equal(villageId);
        });
        return villagePlayerList;
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

    // デフォルトの噛み先を設定
    private void insertDefaultAttack(Integer villageId, int newDay, ListResultBean<VillagePlayer> villagePlayerList) {
        // 噛まれる人
        Integer attackedCharaId = null;
        if (newDay == 1) {
            // ダミーキャラ固定
            attackedCharaId = villagePlayerList.stream()
                    .filter(vp -> BooleanUtils.isTrue(vp.getChara().get().getIsDummy()))
                    .findFirst()
                    .get()
                    .getCharaId();
        } else {
            // 狼以外の生存している誰か
            attackedCharaId = villagePlayerList.stream()
                    .filter(vp -> vp.getSkillCodeAsSkill() != CDef.Skill.人狼 && vp.isIsDeadFalse())
                    .findFirst()
                    .get()
                    .getCharaId();
        }
        // 噛む人
        // 生存している狼の誰か
        Integer attackCharaId = villagePlayerList.stream()
                .filter(vp -> vp.getSkillCodeAsSkill() == CDef.Skill.人狼 && vp.isIsDeadFalse())
                .findFirst()
                .get()
                .getCharaId();
        insertAbility(villageId, newDay, attackedCharaId, attackCharaId, CDef.AbilityType.襲撃);
    }

    private void insertAbility(Integer villageId, int newDay, Integer charaId, Integer targetCharaId, CDef.AbilityType abilityType) {
        Ability attack = new Ability();
        attack.setVillageId(villageId);
        attack.setDay(newDay);
        attack.setCharaId(targetCharaId);
        attack.setTargetCharaId(charaId);
        attack.setAbilityTypeCodeAsAbilityType(abilityType);
        abilityBhv.insert(attack);
    }

    private void insertVote(Integer villageId, int newDay, Integer charaId, Integer targetCharaId) {
        Vote vote = new Vote();
        vote.setVillageId(villageId);
        vote.setDay(newDay);
        vote.setCharaId(charaId);
        vote.setVoteCharaId(targetCharaId);
        voteBhv.insert(vote);
    }

    private void insertDefaultGuard(Integer villageId, int newDay, ListResultBean<VillagePlayer> villagePlayerList) {
        // 護衛する人
        Optional<VillagePlayer> optHunter =
                villagePlayerList.stream().filter(vp -> vp.getSkillCodeAsSkill() == CDef.Skill.狩人 && vp.isIsDeadFalse()).findFirst();
        if (!optHunter.isPresent()) {
            return;
        }
        Integer hunterCharaId = optHunter.get().getCharaId();
        // 護衛される人
        Integer targetCharaId = villagePlayerList.stream()
                .filter(vp -> !vp.getCharaId().equals(hunterCharaId) && vp.isIsDeadFalse() && vp.getSkillCodeAsSkill() != CDef.Skill.狩人)
                .findFirst()
                .get()
                .getCharaId();
        insertAbility(villageId, newDay, hunterCharaId, targetCharaId, CDef.AbilityType.護衛);
    }

    private void insertDefaultSeer(Integer villageId, int newDay, ListResultBean<VillagePlayer> villagePlayerList) {
        // 占う人
        Optional<VillagePlayer> optSeer =
                villagePlayerList.stream().filter(vp -> vp.getSkillCodeAsSkill() == CDef.Skill.占い師 && vp.isIsDeadFalse()).findFirst();
        if (!optSeer.isPresent()) {
            return;
        }
        Integer seerCharaId = optSeer.get().getCharaId();
        // 占われる人
        Integer targetCharaId = villagePlayerList.stream()
                .filter(vp -> !vp.getCharaId().equals(seerCharaId) && vp.isIsDeadFalse())
                .findFirst()
                .get()
                .getCharaId();
        insertAbility(villageId, newDay, seerCharaId, targetCharaId, CDef.AbilityType.占い);
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

    // 投票、能力行使のデフォルト設定
    private void setDefaultVoteAndAbility(Integer villageId, int newDay) {
        ListResultBean<VillagePlayer> villagePlayerList = selectVillagePlayerList(villageId);
        // 噛み
        insertDefaultAttack(villageId, newDay, villagePlayerList);
        // 占い
        insertDefaultSeer(villageId, newDay, villagePlayerList);
        // 護衛
        insertDefaultGuard(villageId, newDay, villagePlayerList);
        // 投票
        villagePlayerList.stream().filter(vp -> vp.isIsDeadFalse()).forEach(vp -> {
            insertVote(villageId, newDay, vp.getCharaId(), vp.getCharaId());
        });
    }

    // 初日以外の日付更新処理
    private void dayChange(Integer villageId, int day) {
        ListResultBean<VillagePlayer> villagePlayerList = selectVillagePlayerList(villageId);
        // 突然死
        // TODO h-orito あとで実装する (2018/01/09)

        // TODO h-orito 2日目の処理 (2018/01/09)
        // 処刑
        VillagePlayer executedPlayer = execute(villageId, day, villagePlayerList);

        // 護衛
        Optional<VillagePlayer> optGuardedPlayer = guard(villageId, day, villagePlayerList, executedPlayer);

        // 占い
        Optional<VillagePlayer> optDivinedPlayer = divine(villageId, day, villagePlayerList, executedPlayer);

        // 呪殺
        Optional<VillagePlayer> optDivineKilledPlayer = divineKill(villageId, day, villagePlayerList, executedPlayer, optDivinedPlayer);

        // 霊能
        psychic(villageId, day, villagePlayerList, executedPlayer);

        // 襲撃
        Optional<VillagePlayer> optAttackedPlayer = attack(villageId, day, villagePlayerList, executedPlayer, optGuardedPlayer);

        // 無残メッセージ
        insertAttackedMessage(villageId, day, optDivineKilledPlayer, optAttackedPlayer);

        // 勝敗判定

        // 投票、能力行使のデフォルト設定
        setDefaultVoteAndAbility(villageId, day);
    }

    // 無残メッセージ
    private void insertAttackedMessage(Integer villageId, int day, Optional<VillagePlayer> optDivineKilledPlayer,
            Optional<VillagePlayer> optAttackedPlayer) {
        List<VillagePlayer> attackedPlayerList = new ArrayList<>();
        optDivineKilledPlayer.ifPresent(player -> attackedPlayerList.add(player));
        optAttackedPlayer.ifPresent(player -> attackedPlayerList.add(player));
        if (CollectionUtils.isEmpty(attackedPlayerList)) {
            messageLogic.insertMessage(villageId, day, CDef.MessageType.公開システムメッセージ, "今日は犠牲者がいないようだ。人狼は襲撃に失敗したのだろうか。");
        } else {
            Collections.shuffle(attackedPlayerList);
            StringJoiner joiner = new StringJoiner("と", "次の日の朝、", "が無残な姿で発見された。");
            attackedPlayerList.stream().forEach(player -> {
                joiner.add(player.getChara().get().getCharaName());
            });
            messageLogic.insertMessage(villageId, day, CDef.MessageType.公開システムメッセージ, joiner.toString());
        }
    }

    // 襲撃
    private Optional<VillagePlayer> attack(Integer villageId, int day, ListResultBean<VillagePlayer> villagePlayerList,
            VillagePlayer executedPlayer, Optional<VillagePlayer> optGuardedPlayer) {
        Optional<VillagePlayer> optLivingWerewolf = villagePlayerList.stream()
                .filter(vp -> vp.isIsDeadFalse() && !vp.getVillagePlayerId().equals(executedPlayer.getVillagePlayerId())
                        && vp.getSkillCodeAsSkill() == CDef.Skill.人狼)
                .findAny();
        if (!optLivingWerewolf.isPresent()) {
            return Optional.empty(); // 人狼がいない場合は何もしない
        }
        OptionalEntity<Ability> optAttack = abilityBhv.selectEntity(cb -> {
            cb.query().setVillageId_Equal(villageId);
            cb.query().setDay_Equal(day - 1);
            cb.query().setAbilityTypeCode_Equal_襲撃();
        });
        if (!optAttack.isPresent()) {
            return Optional.empty(); // 能力セットしていない場合は何もしない
        }
        VillagePlayer targetPlayer =
                villagePlayerList.stream().filter(vp -> vp.getCharaId().equals(optAttack.get().getTargetCharaId())).findFirst().get();
        // 襲撃メッセージ
        String attackMessage = String.format("%s！今日がお前の命日だ！", targetPlayer.getChara().get().getCharaName());
        messageLogic.insertMessage(villageId, day, CDef.MessageType.人狼の囁き, attackMessage, optLivingWerewolf.get().getVillagePlayerId());

        // 襲撃成功したか
        boolean isAttackSuccess = isAttackSuccess(targetPlayer, executedPlayer.getVillagePlayerId(), optGuardedPlayer);
        if (isAttackSuccess) {
            return Optional.ofNullable(targetPlayer);
        }
        return Optional.empty();
    }

    // 襲撃成功したか
    private boolean isAttackSuccess(VillagePlayer attackedPlayer, Integer executedPlayerId, Optional<VillagePlayer> optGuardedPlayer) {
        if (attackedPlayer.getVillagePlayerId().equals(executedPlayerId)) {
            return false; // 処刑されていた
        }
        if (optGuardedPlayer.isPresent() && optGuardedPlayer.get().getVillagePlayerId().equals(attackedPlayer.getVillagePlayerId())) {
            return false; // 護衛されていた
        }
        if (attackedPlayer.getSkillCodeAsSkill() == CDef.Skill.妖狐) {
            return false; // 噛み先が妖狐
        }
        return true;
    }

    // 霊視
    private void psychic(Integer villageId, int day, ListResultBean<VillagePlayer> villagePlayerList, VillagePlayer executedPlayer) {
        Optional<VillagePlayer> optLivingPsychic = villagePlayerList.stream()
                .filter(vp -> vp.isIsDeadFalse() && !vp.getVillagePlayerId().equals(executedPlayer.getVillagePlayerId())
                        && vp.getSkillCodeAsSkill() == CDef.Skill.霊能者)
                .findFirst();
        if (!optLivingPsychic.isPresent()) {
            return; // 霊能者が既に死亡している場合は何もしない
        }
        boolean isTargetWerewolf = executedPlayer.getSkillCodeAsSkill() == CDef.Skill.人狼;
        String message = String.format("処刑された霊が語りかける...\n%sは%sのようだ。", executedPlayer.getChara().get().getCharaName(),
                isTargetWerewolf ? "人狼" : "人間");
        messageLogic.insertMessage(villageId, day, CDef.MessageType.霊視結果, message);
    }

    // 呪殺
    private Optional<VillagePlayer> divineKill(Integer villageId, int day, ListResultBean<VillagePlayer> villagePlayerList,
            VillagePlayer executedPlayer, Optional<VillagePlayer> optDivinedPlayer) {
        if (!optDivinedPlayer.isPresent()) {
            return Optional.empty();
        }
        Optional<VillagePlayer> optDivineKilledPlayer = villagePlayerList.stream()
                .filter(vp -> vp.isIsDeadFalse() && !vp.getVillagePlayerId().equals(executedPlayer.getVillagePlayerId())
                        && vp.getSkillCodeAsSkill() == CDef.Skill.妖狐
                        && vp.getVillagePlayerId().equals(optDivinedPlayer.get().getVillagePlayerId()))
                .findFirst();
        if (!optDivineKilledPlayer.isPresent()) {
            return Optional.empty();
        }
        // 死亡処理
        VillagePlayer vPlayer = new VillagePlayer();
        vPlayer.setDeadReasonCode_呪殺();
        vPlayer.setIsDead_True();
        villagePlayerBhv.queryUpdate(vPlayer, cb -> cb.query().setVillagePlayerId_Equal(optDivineKilledPlayer.get().getVillagePlayerId()));

        return optDivineKilledPlayer;
    }

    // 占い
    private Optional<VillagePlayer> divine(Integer villageId, int day, ListResultBean<VillagePlayer> villagePlayerList,
            VillagePlayer executedPlayer) {
        Optional<VillagePlayer> optLivingSeer = villagePlayerList.stream()
                .filter(vp -> vp.isIsDeadFalse() && !vp.getVillagePlayerId().equals(executedPlayer.getVillagePlayerId())
                        && vp.getSkillCodeAsSkill() == CDef.Skill.占い師)
                .findFirst();
        if (!optLivingSeer.isPresent()) {
            return Optional.empty(); // 占い師が既に死亡している場合は何もしない
        }
        OptionalEntity<Ability> optSeer = abilityBhv.selectEntity(cb -> {
            cb.query().setVillageId_Equal(villageId);
            cb.query().setDay_Equal(day - 1);
            cb.query().setAbilityTypeCode_Equal_占い();
        });
        if (!optSeer.isPresent()) {
            return Optional.empty(); // 能力セットしていない場合は何もしない
        }
        VillagePlayer targetPlayer =
                villagePlayerList.stream().filter(vp -> vp.getCharaId().equals(optSeer.get().getTargetCharaId())).findFirst().get();
        boolean isTargetWerewolf = targetPlayer.getSkillCodeAsSkill() == CDef.Skill.人狼;
        String targetCharaName = targetPlayer.getChara().get().getCharaName();
        String message = String.format("%sは、%sを占った。\n%sは%sのようだ。", optLivingSeer.get().getChara().get().getCharaName(), targetCharaName,
                targetCharaName, isTargetWerewolf ? "人狼" : "人間");
        messageLogic.insertMessage(villageId, day, CDef.MessageType.占い結果, message);
        return Optional.ofNullable(targetPlayer);
    }

    // 護衛
    private Optional<VillagePlayer> guard(Integer villageId, int day, ListResultBean<VillagePlayer> villagePlayerList,
            VillagePlayer executedPlayer) {
        Optional<VillagePlayer> optLivingHunter = villagePlayerList.stream()
                .filter(vp -> vp.isIsDeadFalse() && !vp.getVillagePlayerId().equals(executedPlayer.getVillagePlayerId())
                        && vp.getSkillCodeAsSkill() == CDef.Skill.狩人)
                .findFirst();
        if (!optLivingHunter.isPresent()) {
            return Optional.empty(); // 狩人が既に死亡している場合は何もしない
        }
        OptionalEntity<Ability> optGuard = abilityBhv.selectEntity(cb -> {
            cb.query().setVillageId_Equal(villageId);
            cb.query().setDay_Equal(day - 1);
            cb.query().setAbilityTypeCode_Equal_護衛();
        });
        if (!optGuard.isPresent()) {
            return Optional.empty(); // 能力セットしていない場合は何もしない
        }
        VillagePlayer targetPlayer =
                villagePlayerList.stream().filter(vp -> vp.getCharaId().equals(optGuard.get().getTargetCharaId())).findFirst().get();
        String message = String.format("%sは、%sを護衛している。", optLivingHunter.get().getChara().get().getCharaName(),
                targetPlayer.getChara().get().getCharaName());
        messageLogic.insertMessage(villageId, day, CDef.MessageType.非公開システムメッセージ, message);
        return Optional.ofNullable(targetPlayer);
    }

    // 処刑
    private VillagePlayer execute(Integer villageId, int day, ListResultBean<VillagePlayer> villagePlayerList) {
        ListResultBean<Vote> voteList = voteBhv.selectList(cb -> {
            cb.query().setVillageId_Equal(villageId);
            cb.query().setDay_Equal(day - 1); // 前日の投票を使う
        });
        Map<Integer, Integer> voteNumMap = new HashMap<>();
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
        Collections.sort(executedCharaIdList); // 得票数が同じ場合はランダム
        VillagePlayer executedPlayer =
                villagePlayerList.stream().filter(vp -> vp.getCharaId().equals(executedCharaIdList.get(0))).findFirst().get();

        // 処刑
        VillagePlayer vPlayer = new VillagePlayer();
        vPlayer.setDeadReasonCode_処刑();
        vPlayer.setIsDead_True();
        villagePlayerBhv.queryUpdate(vPlayer, cb -> cb.query().setVillagePlayerId_Equal(executedPlayer.getVillagePlayerId()));

        // 個別投票メッセージ登録
        insertEachVoteMessage(villageId, day, villagePlayerList, voteList);
        // 集計メッセージ登録
        insertExecuteResultMessage(villageId, day, villagePlayerList, voteNumMap, executedPlayer);

        return executedPlayer;
    }

    private void insertEachVoteMessage(Integer villageId, int day, ListResultBean<VillagePlayer> villagePlayerList,
            ListResultBean<Vote> voteList) {
        StringJoiner joiner = new StringJoiner("\n");
        for (Vote vote : voteList) {
            Integer charaId = vote.getCharaId();
            VillagePlayer player = villagePlayerList.stream().filter(vp -> vp.getCharaId().equals(charaId)).findFirst().get();
            Integer targetCharaId = vote.getVoteCharaId();
            VillagePlayer targetPlayer = villagePlayerList.stream().filter(vp -> vp.getCharaId().equals(targetCharaId)).findFirst().get();
            joiner.add(String.format("%sは、%sに投票した。", player.getChara().get().getCharaName(), targetPlayer.getChara().get().getCharaName()));
        }
        messageLogic.insertMessage(villageId, day, CDef.MessageType.非公開システムメッセージ, joiner.toString());
    }

    private void insertExecuteResultMessage(Integer villageId, int day, ListResultBean<VillagePlayer> villagePlayerList,
            Map<Integer, Integer> voteNumMap, VillagePlayer executedPlayer) {
        StringJoiner joiner = new StringJoiner("\n");
        for (Entry<Integer, Integer> entry : voteNumMap.entrySet()) {
            Integer targetCharaId = entry.getKey();
            Integer voteCount = entry.getValue();
            VillagePlayer targetPlayer = villagePlayerList.stream().filter(vp -> vp.getCharaId().equals(targetCharaId)).findFirst().get();
            joiner.add(String.format("%s、%d票", targetPlayer.getChara().get().getCharaName(), voteCount));
        }
        joiner.add(String.format("\n%sは村人達の手により処刑された。", executedPlayer.getChara().get().getCharaName()));
        messageLogic.insertMessage(villageId, day, CDef.MessageType.公開システムメッセージ, joiner.toString());
    }
}
