package com.ort.app.web.controller.logic;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.StringJoiner;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.apache.commons.lang3.BooleanUtils;
import org.dbflute.cbean.result.ListResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.ort.dbflute.allcommon.CDef;
import com.ort.dbflute.allcommon.CDef.Camp;
import com.ort.dbflute.allcommon.CDef.VillageStatus;
import com.ort.dbflute.exbhv.AbilityBhv;
import com.ort.dbflute.exbhv.VillageBhv;
import com.ort.dbflute.exbhv.VillageDayBhv;
import com.ort.dbflute.exbhv.VillagePlayerBhv;
import com.ort.dbflute.exbhv.VoteBhv;
import com.ort.dbflute.exentity.Ability;
import com.ort.dbflute.exentity.Chara;
import com.ort.dbflute.exentity.Village;
import com.ort.dbflute.exentity.VillageDay;
import com.ort.dbflute.exentity.VillagePlayer;
import com.ort.dbflute.exentity.VillageSettings;
import com.ort.dbflute.exentity.Vote;
import com.ort.fw.util.WerewolfMansionDateUtil;

@Component
public class DayChangeLogic {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    @Autowired
    private VillageBhv villageBhv;

    @Autowired
    private VillageDayBhv villageDayBhv;

    @Autowired
    private VillagePlayerBhv villagePlayerBhv;

    @Autowired
    private AbilityBhv abilityBhv;

    @Autowired
    private VoteBhv voteBhv;

    @Autowired
    private AssignLogic assignLogic;

    @Autowired
    private FootstepLogic footstepLogic;

    @Autowired
    private MessageLogic messageLogic;

    @Autowired
    private MessageSource messageSource;

    // ===================================================================================
    //                                                                             Execute
    //                                                                             =======
    public void dayChangeIfNeeded(Integer villageId) {
        VillageDay maxVillageDay = selectMaxVillageDay(villageId);
        Integer day = maxVillageDay.getDay();
        LocalDateTime daychangeDatetime = maxVillageDay.getDaychangeDatetime();
        Village village = maxVillageDay.getVillage().get();
        List<VillagePlayer> vPlayerList = village.getVillagePlayerList();
        VillageSettings settings = village.getVillageSettingsAsOne().get();
        Integer intervalSeconds = settings.getDayChangeIntervalSeconds();
        LocalDateTime nextDaychangeDatetime = daychangeDatetime.plusSeconds(intervalSeconds);

        // 日付更新の必要がなければ終了
        if (!shouldChangeDay(village, daychangeDatetime)) {
            return;
        }

        // 最低開始人数を満たしていない
        if (isInsufficientVillagerNum(village, settings)) {
            updateVillageDayTransactional(villageId, day, nextDaychangeDatetime); // 村日付を1日延長
            messageLogic.insertMessage(villageId, day, CDef.MessageType.公開システムメッセージ, "まだ村人たちは揃っていないようだ。"); // 延長メッセージ登録
            return;
        }

        // 日付切り替え -----------------------------------------------------------
        int newDay = day + 1;
        insertVillageDayTransactional(villageId, newDay, nextDaychangeDatetime); // 村日付を追加

        if (day == 0) { // プロ→1日目
            assignLogic.assignSkill(villageId, vPlayerList); // 役職割り当て
            assignLogic.assignRoom(villageId, vPlayerList); // 部屋割り当て
            updateVillageStatus(villageId, CDef.VillageStatus.進行中); // 村ステータス更新
            setDefaultVoteAndAbility(villageId, newDay); // 投票、能力行使のデフォルト設定
        } else if (village.getVillageStatusCodeAsVillageStatus() == CDef.VillageStatus.エピローグ) {
            updateVillageStatus(villageId, CDef.VillageStatus.終了); // 終了
            messageLogic.insertMessage(villageId, newDay, CDef.MessageType.公開システムメッセージ, "終了しました。");
        } else {
            // 1日目以外
            dayChange(villageId, newDay, vPlayerList);
        }
    }

    // ===================================================================================
    //                                                                              Select
    //                                                                              ======
    private Village selectVillage(Integer villageId) {
        return villageBhv.selectEntityWithDeletedCheck(cb -> {
            cb.setupSelect_VillageSettingsAsOne();
            cb.query().setVillageId_Equal(villageId);
        });
    }

    private VillageDay selectMaxVillageDay(Integer villageId) {
        VillageDay villageDay = villageDayBhv.selectEntity(cb -> {
            cb.setupSelect_Village().withVillageSettingsAsOne();
            cb.query().setVillageId_Equal(villageId);
            cb.query().addOrderBy_Day_Desc();
            cb.fetchFirst(1);
        }).get();
        villageDayBhv.load(villageDay, loader -> {
            loader.pulloutVillage().loadVillagePlayer(cb -> {
                cb.setupSelect_Chara();
                cb.setupSelect_Player();
                cb.setupSelect_SkillBySkillCode();
            });
        });
        return villageDay;
    }

    private List<VillagePlayer> selectVillagePlayerList(Integer villageId) {
        return villagePlayerBhv.selectList(cb -> {
            cb.setupSelect_Chara();
            cb.setupSelect_Player();
            cb.setupSelect_SkillBySkillCode();
            cb.query().setVillageId_Equal(villageId);
        });
    }

    private ListResultBean<Vote> selectVoteList(Integer villageId, int day) {
        ListResultBean<Vote> voteList = voteBhv.selectList(cb -> {
            cb.query().setVillageId_Equal(villageId);
            cb.query().setDay_Equal(day);
        });
        return voteList;
    }

    private ListResultBean<Ability> selectAbilityList(Integer villageId, int day) {
        return abilityBhv.selectList(cb -> {
            cb.query().setVillageId_Equal(villageId);
            cb.query().setDay_Equal(day - 1);
        });
    }

    // ===================================================================================
    //                                                                              Update
    //                                                                              ======
    private void updateVillageStatus(Integer villageId, CDef.VillageStatus status) {
        Village village = new Village();
        village.setVillageStatusCodeAsVillageStatus(status);
        villageBhv.queryUpdate(village, cb -> cb.query().setVillageId_Equal(villageId));
    }

    private void updateVillageDayTransactional(Integer villageId, int day, LocalDateTime nextDaychangeDatetime) {
        VillageDay villageDay = new VillageDay();
        villageDay.setVillageId(villageId);
        villageDay.setDay(day);
        villageDay.setDaychangeDatetime(nextDaychangeDatetime);
        villageDayBhv.update(villageDay);
    }

    // 村日付のみ即コミットされるようにする。publicでないと効かないので注意。
    @Transactional(rollbackFor = Exception.class) // 念のため検査例外でもロールバックされるようにしておく
    public void insertVillageDayTransactional(Integer villageId, int day, LocalDateTime nextDayChangeDatetime) {
        VillageDay villageDay = new VillageDay();
        villageDay.setVillageId(villageId);
        villageDay.setDay(day);
        villageDay.setDaychangeDatetime(nextDayChangeDatetime);
        villageDayBhv.insert(villageDay);
    }

    private void insertAbility(Integer villageId, int newDay, Integer charaId, Integer targetCharaId, CDef.AbilityType abilityType) {
        Ability attack = new Ability();
        attack.setVillageId(villageId);
        attack.setDay(newDay);
        attack.setCharaId(charaId);
        attack.setTargetCharaId(targetCharaId);
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

    private void updateVillagePlayerDead(int day, VillagePlayer targetPlayer, CDef.DeadReason deadReason) {
        VillagePlayer vPlayer = new VillagePlayer();
        vPlayer.setDeadReasonCodeAsDeadReason(deadReason);
        vPlayer.setIsDead_True();
        vPlayer.setDeadDay(day);
        villagePlayerBhv.queryUpdate(vPlayer, cb -> cb.query().setVillagePlayerId_Equal(targetPlayer.getVillagePlayerId()));
    }

    // ===================================================================================
    //                                                                        Assist Logic
    //                                                                        ============
    // 日付更新の必要があるか
    private boolean shouldChangeDay(Village village, LocalDateTime nextDayChangeDatetime) {
        // 村が終了していたら必要なし
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

    // 投票、能力行使のデフォルト設定、生存者メッセージ登録
    private void setDefaultVoteAndAbility(Integer villageId, int newDay) {
        Village village = selectVillage(villageId);
        List<VillagePlayer> vPlayerList = selectVillagePlayerList(villageId);
        // 噛み
        insertDefaultAttack(villageId, newDay, vPlayerList, village);
        // 占い
        insertDefaultSeer(villageId, newDay, vPlayerList, village);
        // 狂人と妖狐の足音
        insertDefaultFootstep(villageId, newDay, vPlayerList);
        if (newDay == 1) {
            return; // 1日目は護衛と投票なし
        }
        // 護衛
        insertDefaultGuard(villageId, newDay, vPlayerList);
        // 投票
        vPlayerList.stream().filter(vp -> vp.isIsDeadFalse()).forEach(vp -> {
            insertVote(villageId, newDay, vp.getCharaId(), vp.getCharaId());
        });
        // 生存者メッセージ登録
        insertLivingPlayerMessage(villageId, newDay, vPlayerList);
    }

    // デフォルトの噛み先を設定
    private void insertDefaultAttack(Integer villageId, int newDay, List<VillagePlayer> villagePlayerList, Village village) {
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
            attackedCharaId = getRandomInList(villagePlayerList, vp -> vp.getSkillCodeAsSkill() != CDef.Skill.人狼).getCharaId();
        }
        // 噛む人（生存している狼で、狼が2名以上の場合は昨日噛んだ人は除外）
        List<Chara> attackableWolfList = getAttackableWolfList(villageId, newDay, villagePlayerList);
        Integer attackCharaId = getRandomInList(attackableWolfList).getCharaId();
        // 能力セット
        insertAbility(villageId, newDay, attackCharaId, attackedCharaId, CDef.AbilityType.襲撃);
        // 時計回りの足音セット
        String footStep = footstepLogic.makeClockwiseFootStep(village, attackCharaId, attackedCharaId, villagePlayerList);
        footstepLogic.insertFootStep(villageId, newDay, attackCharaId, footStep);
        messageLogic.insertAbilityMessage(villageId, newDay, attackCharaId, attackedCharaId, villagePlayerList, footStep, true);
    }

    private List<Chara> getAttackableWolfList(Integer villageId, int day, List<VillagePlayer> villagePlayerList) {
        // 昨日襲撃した狼
        Integer yesterdayAttackerId = abilityBhv.selectEntity(cb -> {
            cb.query().setVillageId_Equal(villageId);
            cb.query().setDay_Equal(day - 1);
            cb.query().setAbilityTypeCode_Equal_襲撃();
        }).map(ab -> ab.getCharaId()).orElse(null);
        // 生存している狼リスト
        List<Chara> liveAttackerList = villagePlayerList.stream()
                .filter(vp -> vp.isIsDeadFalse() && vp.getSkillCodeAsSkill() == CDef.Skill.人狼)
                .map(vp -> vp.getChara().get())
                .collect(Collectors.toList());
        // 生存している狼が1名ではない場合は、昨日襲撃した狼は襲撃できない
        if (liveAttackerList.size() > 1) {
            return liveAttackerList.stream().filter(attacker -> !attacker.getCharaId().equals(yesterdayAttackerId)).collect(
                    Collectors.toList());
        } else {
            return liveAttackerList;
        }
    }

    private void insertDefaultGuard(Integer villageId, int newDay, List<VillagePlayer> villagePlayerList) {
        // 護衛する人
        Optional<VillagePlayer> optHunter =
                villagePlayerList.stream().filter(vp -> vp.getSkillCodeAsSkill() == CDef.Skill.狩人 && vp.isIsDeadFalse()).findFirst();
        if (!optHunter.isPresent()) {
            return;
        }
        Integer hunterCharaId = optHunter.get().getCharaId();
        // 護衛される人(生存者の中の誰か）
        Integer targetCharaId = getRandomInList(villagePlayerList, vp -> !vp.getCharaId().equals(hunterCharaId)).getCharaId();
        insertAbility(villageId, newDay, hunterCharaId, targetCharaId, CDef.AbilityType.護衛);
        messageLogic.insertAbilityMessage(villageId, newDay, hunterCharaId, targetCharaId, villagePlayerList, null, true);
    }

    private void insertDefaultSeer(Integer villageId, int newDay, List<VillagePlayer> villagePlayerList, Village village) {
        // 占う人
        Optional<VillagePlayer> optSeer =
                villagePlayerList.stream().filter(vp -> vp.getSkillCodeAsSkill() == CDef.Skill.占い師 && vp.isIsDeadFalse()).findFirst();
        if (!optSeer.isPresent()) {
            return;
        }
        Integer seerCharaId = optSeer.get().getCharaId();
        // 占われる人（生存者の中の誰か）
        Integer targetCharaId = getRandomInList(villagePlayerList, vp -> !vp.getCharaId().equals(seerCharaId)).getCharaId();
        insertAbility(villageId, newDay, seerCharaId, targetCharaId, CDef.AbilityType.占い);
        // 時計回りの足音セット
        String footStep = footstepLogic.makeClockwiseFootStep(village, seerCharaId, targetCharaId, villagePlayerList);
        footstepLogic.insertFootStep(villageId, newDay, seerCharaId, footStep);
        messageLogic.insertAbilityMessage(villageId, newDay, seerCharaId, targetCharaId, villagePlayerList, footStep, true);
    }

    private void insertDefaultFootstep(Integer villageId, int newDay, List<VillagePlayer> vPlayerList) {
        // 妖狐狂人
        List<VillagePlayer> foxMadmanList = vPlayerList.stream()
                .filter(vp -> (vp.getSkillCodeAsSkill() == CDef.Skill.妖狐 || vp.getSkillCodeAsSkill() == CDef.Skill.狂人)
                        && vp.isIsDeadFalse())
                .collect(Collectors.toList());
        for (VillagePlayer vp : foxMadmanList) {
            Integer charaId = vp.getCharaId();
            // 足音なしセット
            footstepLogic.insertFootStep(villageId, newDay, charaId, "なし");
            messageLogic.insertFootstepMessage(villageId, newDay, charaId, vPlayerList, "なし", true);
        }
    }

    // リストを引数の条件で絞りつつ、生存者の中からランダムで1名取得する
    private VillagePlayer getRandomInList(List<VillagePlayer> villagePlayerList, Predicate<? super VillagePlayer> predicate) {
        List<VillagePlayer> candidateList =
                villagePlayerList.stream().filter(predicate).filter(vp -> vp.isIsDeadFalse()).collect(Collectors.toList());
        Collections.shuffle(candidateList);
        return candidateList.get(0);
    }

    private Chara getRandomInList(List<Chara> charaList) {
        Collections.shuffle(charaList);
        return charaList.get(0);
    }

    // 生存者メッセージ登録
    private void insertLivingPlayerMessage(Integer villageId, int day, List<VillagePlayer> vPlayerList) {
        long livePersonNum = vPlayerList.stream().filter(vp -> vp.isIsDeadFalse()).count();
        StringJoiner joiner = new StringJoiner("、", "現在の生存者は、以下の" + livePersonNum + "名。\n", "");
        vPlayerList.stream().filter(vp -> vp.isIsDeadFalse()).forEach(player -> {
            joiner.add(player.getChara().get().getCharaName());
        });
        messageLogic.insertMessage(villageId, day, CDef.MessageType.公開システムメッセージ, joiner.toString());
    }

    // 初日、2日目以外の日付更新処理
    private void dayChange(Integer villageId, int day, List<VillagePlayer> vPlayerList) {
        // 突然死
        // TODO h-orito あとで実装する (2018/01/09)

        // 処刑
        VillagePlayer executedPlayer = execute(villageId, day, vPlayerList);
        Integer executedPlayerId = executedPlayer == null ? null : executedPlayer.getVillagePlayerId();

        // 能力行使内容取得
        List<Ability> abilityList = selectAbilityList(villageId, day);

        // 護衛
        Optional<VillagePlayer> optGuardedPlayer = guard(villageId, day, vPlayerList, executedPlayerId, abilityList);

        // 占い
        Optional<VillagePlayer> optDivinedPlayer = divine(villageId, day, vPlayerList, executedPlayerId, abilityList);

        // 呪殺
        Optional<VillagePlayer> optDivineKilledPlayer = divineKill(villageId, day, vPlayerList, executedPlayerId, optDivinedPlayer);

        // 霊能
        psychic(villageId, day, vPlayerList, executedPlayer);

        // 襲撃
        Optional<VillagePlayer> optAttackedPlayer = attack(villageId, day, vPlayerList, executedPlayerId, optGuardedPlayer, abilityList);

        // 無惨メッセージ
        insertAttackedMessage(villageId, day, optDivineKilledPlayer, optAttackedPlayer);

        if (day == 2) {
            messageLogic.insertMessage(villageId, day, CDef.MessageType.公開システムメッセージ,
                    messageSource.getMessage("village.start.message", null, Locale.JAPAN));
        }

        // 勝敗判定、エピローグ処理
        Optional<CDef.Camp> optWinCamp = endVillageIfNeeded(villageId, day);

        if (optWinCamp.isPresent()) {
            return;
        }

        // 投票、能力行使のデフォルト設定
        setDefaultVoteAndAbility(villageId, day);
    }

    // 勝敗判定、エピローグ処理
    private Optional<Camp> endVillageIfNeeded(Integer villageId, int day) {
        ListResultBean<VillagePlayer> villagePlayerList = villagePlayerBhv.selectList(cb -> {
            cb.setupSelect_Player();
            cb.setupSelect_Chara();
            cb.setupSelect_SkillBySkillCode();
            cb.query().setVillageId_Equal(villageId);
        });

        // 人狼の数
        long werewolfCount =
                villagePlayerList.stream().filter(vp -> vp.isIsDeadFalse() && vp.getSkillCodeAsSkill() == CDef.Skill.人狼).count();
        // 人間の数
        long villagerCount = villagePlayerList.stream()
                .filter(vp -> vp.isIsDeadFalse() && vp.getSkillCodeAsSkill() != CDef.Skill.人狼 && vp.getSkillCodeAsSkill() != CDef.Skill.妖狐)
                .count();
        // 妖狐の数
        long foxCount = villagePlayerList.stream().filter(vp -> vp.isIsDeadFalse() && vp.getSkillCodeAsSkill() == CDef.Skill.妖狐).count();

        // 終了判定、処理
        if (werewolfCount >= villagerCount) {
            if (foxCount > 0) {
                epilogueVillage(villageId, day, villagePlayerList, CDef.Camp.狐陣営, werewolfCount);
                return Optional.of(CDef.Camp.狐陣営);
            } else {
                epilogueVillage(villageId, day, villagePlayerList, CDef.Camp.人狼陣営, werewolfCount);
                return Optional.of(CDef.Camp.人狼陣営);
            }
        } else if (werewolfCount <= 0) {
            if (foxCount > 0) {
                epilogueVillage(villageId, day, villagePlayerList, CDef.Camp.狐陣営, werewolfCount);
                return Optional.of(CDef.Camp.狐陣営);
            } else {
                epilogueVillage(villageId, day, villagePlayerList, CDef.Camp.村人陣営, werewolfCount);
                return Optional.of(CDef.Camp.村人陣営);
            }
        } else {
            return Optional.empty();
        }
    }

    // エピローグ処理
    private void epilogueVillage(Integer villageId, int day, ListResultBean<VillagePlayer> villagePlayerList, Camp winCamp,
            long werewolfCount) {
        // DB更新
        Village village = new Village();
        village.setVillageStatusCode_エピローグ();
        village.setEpilogueDay(day);
        village.setWinCampCodeAsCamp(winCamp);
        villageBhv.queryUpdate(village, cb -> cb.query().setVillageId_Equal(villageId));
        // エピローグ遷移メッセージ登録
        String message = getEpilogueMessage(winCamp, werewolfCount);
        messageLogic.insertMessage(villageId, day, CDef.MessageType.公開システムメッセージ, message);
        // 参加者一覧メッセージ登録
        insertPlayerListMessage(villageId, day, villagePlayerList);
    }

    // 参加者一覧メッセージ登録
    private void insertPlayerListMessage(Integer villageId, int day, ListResultBean<VillagePlayer> villagePlayerList) {
        StringJoiner joiner = new StringJoiner("\n");
        villagePlayerList.stream().forEach(player -> {
            joiner.add(String.format("%s (%s)、%s。%sだった。", player.getChara().get().getCharaName(), player.getPlayer().get().getPlayerName(),
                    player.isIsDeadTrue() ? "死亡" : "生存", player.getSkillBySkillCode().get().getSkillName()));
        });
        messageLogic.insertMessage(villageId, day, CDef.MessageType.公開システムメッセージ, joiner.toString());
    }

    // エピローグ遷移メッセージ
    private String getEpilogueMessage(Camp winCamp, long werewolfCount) {
        if (winCamp == CDef.Camp.狐陣営 && werewolfCount <= 0) {
            return messageSource.getMessage("village.epilogue.fox-villager.win.message", null, Locale.JAPAN);
        } else if (winCamp == CDef.Camp.狐陣営 && werewolfCount > 0) {
            return messageSource.getMessage("village.epilogue.fox-werewolf.win.message", null, Locale.JAPAN);
        } else if (winCamp == CDef.Camp.村人陣営) {
            return messageSource.getMessage("village.epilogue.villager.win.message", null, Locale.JAPAN);
        } else {
            return messageSource.getMessage("village.epilogue.werewolf.win.message", null, Locale.JAPAN);
        }
    }

    // 無惨メッセージ
    private void insertAttackedMessage(Integer villageId, int day, Optional<VillagePlayer> optDivineKilledPlayer,
            Optional<VillagePlayer> optAttackedPlayer) {
        List<VillagePlayer> attackedPlayerList = new ArrayList<>();
        optDivineKilledPlayer.ifPresent(player -> attackedPlayerList.add(player));
        optAttackedPlayer.ifPresent(player -> attackedPlayerList.add(player));
        if (CollectionUtils.isEmpty(attackedPlayerList)) {
            messageLogic.insertMessage(villageId, day, CDef.MessageType.公開システムメッセージ, "今日は犠牲者がいないようだ。人狼は襲撃に失敗したのだろうか。");
        } else {
            Collections.shuffle(attackedPlayerList);
            StringJoiner joiner = new StringJoiner("と", "次の日の朝、", "が無惨な姿で発見された。");
            attackedPlayerList.stream().forEach(player -> {
                joiner.add(player.getChara().get().getCharaName());
            });
            messageLogic.insertMessage(villageId, day, CDef.MessageType.公開システムメッセージ, joiner.toString());
        }
    }

    // 襲撃
    private Optional<VillagePlayer> attack(Integer villageId, int day, List<VillagePlayer> villagePlayerList, Integer executedPlayerId,
            Optional<VillagePlayer> optGuardedPlayer, List<Ability> abilityList) {
        Optional<VillagePlayer> optLivingWerewolf = villagePlayerList.stream()
                .filter(vp -> vp.isIsDeadFalse() && !vp.getVillagePlayerId().equals(executedPlayerId)
                        && vp.getSkillCodeAsSkill() == CDef.Skill.人狼)
                .findAny();
        if (!optLivingWerewolf.isPresent()) {
            return Optional.empty(); // 人狼がいない場合は何もしない
        }
        Optional<Ability> optAttack =
                abilityList.stream().filter(ability -> ability.getAbilityTypeCodeAsAbilityType() == CDef.AbilityType.襲撃).findFirst();
        if (!optAttack.isPresent()) {
            return Optional.empty(); // 能力セットしていない場合は何もしない
        }
        VillagePlayer targetPlayer =
                villagePlayerList.stream().filter(vp -> vp.getCharaId().equals(optAttack.get().getTargetCharaId())).findFirst().get();
        // 襲撃メッセージ
        if (day != 2) {
            String attackMessage = String.format("%s！今日がお前の命日だ！", targetPlayer.getChara().get().getCharaName());
            messageLogic.insertMessage(villageId, day, CDef.MessageType.人狼の囁き, attackMessage, optLivingWerewolf.get().getVillagePlayerId());
        }

        // 襲撃成功したか
        if (isAttackSuccess(targetPlayer, executedPlayerId, optGuardedPlayer)) {
            updateVillagePlayerDead(day, targetPlayer, CDef.DeadReason.襲撃); // 死亡処理
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
    private void psychic(Integer villageId, int day, List<VillagePlayer> villagePlayerList, VillagePlayer executedPlayer) {
        if (executedPlayer == null) {
            return;
        }
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
    private Optional<VillagePlayer> divineKill(Integer villageId, int day, List<VillagePlayer> villagePlayerList, Integer executedPlayerId,
            Optional<VillagePlayer> optDivinedPlayer) {
        if (!optDivinedPlayer.isPresent()) {
            return Optional.empty();
        }
        Optional<VillagePlayer> optDivineKilledPlayer = villagePlayerList.stream()
                .filter(vp -> vp.isIsDeadFalse() && !vp.getVillagePlayerId().equals(executedPlayerId)
                        && vp.getSkillCodeAsSkill() == CDef.Skill.妖狐
                        && vp.getVillagePlayerId().equals(optDivinedPlayer.get().getVillagePlayerId()))
                .findFirst();
        if (!optDivineKilledPlayer.isPresent()) {
            return Optional.empty();
        }
        updateVillagePlayerDead(day, optDivineKilledPlayer.get(), CDef.DeadReason.呪殺); // 死亡処理

        return optDivineKilledPlayer;
    }

    // 占い
    private Optional<VillagePlayer> divine(Integer villageId, int day, List<VillagePlayer> villagePlayerList, Integer executedPlayerId,
            List<Ability> abilityList) {
        Optional<VillagePlayer> optLivingSeer = villagePlayerList.stream()
                .filter(vp -> vp.isIsDeadFalse() && !vp.getVillagePlayerId().equals(executedPlayerId)
                        && vp.getSkillCodeAsSkill() == CDef.Skill.占い師)
                .findFirst();
        if (!optLivingSeer.isPresent()) {
            return Optional.empty(); // 占い師が既に死亡している場合は何もしない
        }
        Optional<Ability> optSeer =
                abilityList.stream().filter(ability -> ability.getAbilityTypeCodeAsAbilityType() == CDef.AbilityType.占い).findFirst();
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
    private Optional<VillagePlayer> guard(Integer villageId, int day, List<VillagePlayer> villagePlayerList, Integer executedPlayerId,
            List<Ability> abilityList) {
        if (day == 2) {
            return Optional.empty();
        }
        Optional<VillagePlayer> optLivingHunter = villagePlayerList.stream()
                .filter(vp -> vp.isIsDeadFalse() && !vp.getVillagePlayerId().equals(executedPlayerId)
                        && vp.getSkillCodeAsSkill() == CDef.Skill.狩人)
                .findFirst();
        if (!optLivingHunter.isPresent()) {
            return Optional.empty(); // 狩人が既に死亡している場合は何もしない
        }
        Optional<Ability> optGuard =
                abilityList.stream().filter(ability -> ability.getAbilityTypeCodeAsAbilityType() == CDef.AbilityType.護衛).findFirst();
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
    private VillagePlayer execute(Integer villageId, int day, List<VillagePlayer> vPlayerList) {
        if (day == 2) {
            return null; // 1→2日目は処刑なし
        }
        ListResultBean<Vote> voteList = selectVoteList(villageId, day - 1); // 前日の投票を使う
        Map<Integer, Integer> voteNumMap = new HashMap<>();
        // 得票数トップのプレイヤーを取得（複数いる可能性があるのでリスト）
        List<Integer> executedCharaIdList = makeExecutedCandidateList(voteList, voteNumMap);
        Collections.shuffle(executedCharaIdList); // 得票数が同じ場合はランダム
        VillagePlayer executedPlayer =
                vPlayerList.stream().filter(vp -> vp.getCharaId().equals(executedCharaIdList.get(0))).findFirst().get();
        // 処刑
        updateVillagePlayerDead(day, executedPlayer, CDef.DeadReason.処刑); // 死亡処理
        // 個別投票メッセージ登録
        insertEachVoteMessage(villageId, day, vPlayerList, voteList);
        // 集計メッセージ登録
        insertExecuteResultMessage(villageId, day, vPlayerList, voteNumMap, executedPlayer);

        return executedPlayer;
    }

    // 得票数トップのプレイヤーを取得（複数いる可能性があるのでリスト）
    private List<Integer> makeExecutedCandidateList(ListResultBean<Vote> voteList, Map<Integer, Integer> voteNumMap) {
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

    private void insertEachVoteMessage(Integer villageId, int day, List<VillagePlayer> villagePlayerList, ListResultBean<Vote> voteList) {
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

    private void insertExecuteResultMessage(Integer villageId, int day, List<VillagePlayer> villagePlayerList,
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

    // 参加者数が不足している
    private boolean isInsufficientVillagerNum(Village village, VillageSettings settings) {
        return village.getVillagePlayerList().size() < settings.getStartPersonMinNum();
    }
}
