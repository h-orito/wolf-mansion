package com.ort.app.web.controller.logic;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.StringJoiner;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.dbflute.cbean.result.ListResultBean;
import org.dbflute.optional.OptionalEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import com.ort.app.web.controller.logic.daychange.DayChangeLogicHelper;
import com.ort.app.web.dto.VillageInfo;
import com.ort.app.web.exception.WerewolfMansionBusinessException;
import com.ort.app.web.util.CharaUtil;
import com.ort.app.web.util.SkillUtil;
import com.ort.dbflute.allcommon.CDef;
import com.ort.dbflute.allcommon.CDef.Camp;
import com.ort.dbflute.allcommon.CDef.Skill;
import com.ort.dbflute.allcommon.CDef.VillageStatus;
import com.ort.dbflute.exbhv.AbilityBhv;
import com.ort.dbflute.exbhv.CharaImageBhv;
import com.ort.dbflute.exbhv.PlayerBhv;
import com.ort.dbflute.exbhv.VillageBhv;
import com.ort.dbflute.exbhv.VillageDayBhv;
import com.ort.dbflute.exbhv.VillagePlayerBhv;
import com.ort.dbflute.exbhv.VoteBhv;
import com.ort.dbflute.exentity.Ability;
import com.ort.dbflute.exentity.Chara;
import com.ort.dbflute.exentity.CharaImage;
import com.ort.dbflute.exentity.Player;
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
    private PlayerBhv playerBhv;
    @Autowired
    private CharaImageBhv charaImageBhv;
    @Autowired
    private AssignLogic assignLogic;
    @Autowired
    private FootstepLogic footstepLogic;
    @Autowired
    private MessageLogic messageLogic;
    @Autowired
    private VillageParticipateLogic villageParticipateLogic;
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private TwitterLogic twitterLogic;
    @Autowired
    private DayChangeLogicHelper dayChangeLogicHelper;

    // ===================================================================================
    //                                                                             Execute
    //                                                                             =======
    public void dayChangeIfNeeded(Integer villageId) {
        VillageDay maxVillageDay = dayChangeLogicHelper.selectMaxVillageDay(villageId);
        LocalDateTime daychangeDatetime = maxVillageDay.getDaychangeDatetime();
        VillageInfo villageInfo = dayChangeLogicHelper.convertToVillageInfo(maxVillageDay);

        // プロローグで接続していない人を退村させる
        leaveVillageIfNeeded(villageInfo);

        // 日付更新の必要がなければ終了
        if (!shouldChangeDay(villageInfo, daychangeDatetime)) {
            return;
        }

        // 最低開始人数を満たしていない
        if (isInsufficientVillagerNum(villageInfo)) {
            extendOrCancelVillage(villageInfo, daychangeDatetime);
            return;
        }

        // 日付切り替え -----------------------------------------------------------
        int newDay = villageInfo.day + 1;
        Integer intervalSeconds = villageInfo.settings.getDayChangeIntervalSeconds();
        LocalDateTime nextDaychangeDatetime = daychangeDatetime.plusSeconds(intervalSeconds);
        dayChangeLogicHelper.insertVillageDayTransactional(villageId, newDay, nextDaychangeDatetime); // 村日付を追加

        if (villageInfo.day == 0) { // プロ→1日目
            startVillage(villageInfo, newDay);
        } else if (villageInfo.village.getVillageStatusCodeAsVillageStatus() == CDef.VillageStatus.エピローグ) {
            dayChangeLogicHelper.updateVillageStatus(villageId, CDef.VillageStatus.終了); // 終了
        } else {
            dayChange(villageId, newDay, villageInfo.vPlayerList, villageInfo.settings);
        }
    }

    // ===================================================================================
    //                                                                              Select
    //                                                                              ======
    private ListResultBean<Vote> selectVoteList(Integer villageId, int day, List<VillagePlayer> suddonlyDeathVPlayerList) {
        ListResultBean<Vote> voteList = voteBhv.selectList(cb -> {
            cb.query().setVillageId_Equal(villageId);
            cb.query().setDay_Equal(day);
            // 突然死した人は投票無効
            if (CollectionUtils.isNotEmpty(suddonlyDeathVPlayerList)) {
                cb.query().setCharaId_NotInScope(
                        suddonlyDeathVPlayerList.stream().map(VillagePlayer::getCharaId).collect(Collectors.toList()));
            }
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
    private void insertAbility(Integer villageId, int day, Integer charaId, Integer targetCharaId, String footstep,
            CDef.AbilityType abilityType) {
        Ability ability = new Ability();
        ability.setVillageId(villageId);
        ability.setDay(day);
        ability.setCharaId(charaId);
        ability.setTargetCharaId(targetCharaId);
        ability.setTargetFootstep(footstep);
        ability.setAbilityTypeCodeAsAbilityType(abilityType);
        abilityBhv.insert(ability);
    }

    private void insertVote(Integer villageId, int newDay, Integer charaId, Integer targetCharaId) {
        Vote vote = new Vote();
        vote.setVillageId(villageId);
        vote.setDay(newDay);
        vote.setCharaId(charaId);
        vote.setVoteCharaId(targetCharaId);
        voteBhv.insert(vote);
    }

    private void updatePlayerRestrict(Integer playerId) {
        Player player = new Player();
        player.setPlayerId(playerId);
        player.setIsRestrictedParticipation_True();
        playerBhv.update(player);
    }

    // ===================================================================================
    //                                                                        Assist Logic
    //                                                                        ============
    // 日付更新の必要があるか
    private boolean shouldChangeDay(VillageInfo vInfo, LocalDateTime nextDayChangeDatetime) {
        // 村が終了していたら必要なし
        VillageStatus villageStatus = vInfo.village.getVillageStatusCodeAsVillageStatus();
        if (villageStatus == CDef.VillageStatus.廃村 || villageStatus == CDef.VillageStatus.終了) {
            return false;
        }
        // 全員コミットしている
        if (allLivingPlayerCommited(vInfo)) {
            return true;
        }
        // 最新の村日付の更新時間を過ぎていない
        if (WerewolfMansionDateUtil.currentLocalDateTime().isBefore(nextDayChangeDatetime)) {
            return false;
        }
        return true;
    }

    public void startVillage(VillageInfo vInfo, int newDay) {
        messageLogic.insertMessageIgnoreError(vInfo.villageId, 1, CDef.MessageType.公開システムメッセージ,
                messageSource.getMessage("village.start.message.day1", null, Locale.JAPAN));
        assignLogic.assignSkill(vInfo.villageId, vInfo.vPlayerList, vInfo.settings); // 役職割り当て
        assignLogic.assignRoom(vInfo.villageId, vInfo.vPlayerList); // 部屋割り当て
        dayChangeLogicHelper.updateVillageStatus(vInfo.villageId, CDef.VillageStatus.進行中); // 村ステータス更新
        setDefaultVoteAndAbility(vInfo.villageId, newDay, vInfo.settings); // 投票、能力行使のデフォルト設定
        updateVillageSettingsIfNeeded(vInfo.villageId, vInfo.vPlayerList, vInfo.settings); // 特殊ルール変更
        insertDummyCharaMessage(vInfo.villageId, vInfo.vPlayerList, vInfo.settings); // ダミーキャラ発言
        tweetIfNeeded(vInfo.villageId, vInfo.village, vInfo.settings);
    }

    // 全員コミットしている
    private boolean allLivingPlayerCommited(VillageInfo vInfo) {
        if (vInfo.settings.isIsAvailableCommitFalse()) { // コミットなし設定
            return false;
        }
        if (!vInfo.village.isVillageStatusCode進行中()) { // 進行中じゃなかったら対象外
            return false;
        }
        // 全員コミットしているか
        int commitNum = dayChangeLogicHelper.selectCommitNum(vInfo);
        long livingPersonNum = vInfo.vPlayerList.stream()
                .filter(vp -> vp.isIsDeadFalse() && vp.isIsSpectatorFalse() && !vp.getCharaId().equals(vInfo.settings.getDummyCharaId()))
                .count();
        return commitNum == livingPersonNum;
    }

    // 投票、能力行使のデフォルト設定、生存者メッセージ登録
    private void setDefaultVoteAndAbility(Integer villageId, int newDay, VillageSettings settings) {
        // 最新の状況が必要なので取得し直す
        Village village = dayChangeLogicHelper.selectVillage(villageId);
        List<VillagePlayer> vPlayerList = village.getVillagePlayerList();
        // 噛み
        insertDefaultAttack(villageId, settings, newDay, vPlayerList, village);
        // 占い
        insertDefaultSeer(villageId, newDay, vPlayerList, village);
        // 狂人と妖狐の足音
        insertDefaultFootstep(villageId, newDay, vPlayerList);
        if (newDay == 1) {
            return; // 1日目は護衛と投票なし
        }
        // 護衛
        insertDefaultGuard(villageId, newDay, vPlayerList, village);
        // 探偵による調査
        insertDefaultInvestigate(villageId, vPlayerList, newDay);
        // 投票
        if (settings.isIsAvailableSuddonlyDeathFalse()) { // 突然死ありの場合はデフォ投票はなし
            vPlayerList.stream().filter(vp -> vp.isIsDeadFalse()).forEach(vp -> {
                insertVote(villageId, newDay, vp.getCharaId(), vp.getCharaId());
            });
        }
        // 生存者メッセージ登録
        insertLivingPlayerMessage(villageId, newDay, vPlayerList);

        // 足音メッセージ登録
        insertFootstepMessage(villageId, newDay, vPlayerList);
    }

    // デフォルトの噛み先を設定
    private void insertDefaultAttack(Integer villageId, VillageSettings settings, int newDay, List<VillagePlayer> villagePlayerList,
            Village village) {
        // 噛まれる人
        Integer attackedCharaId = null;
        if (newDay == 1) {
            attackedCharaId = village.getVillageSettingsAsOne().get().getDummyCharaId(); // ダミーキャラ固定
        } else {
            attackedCharaId = getRandomInList(villagePlayerList, vp -> vp.getSkillCodeAsSkill() != CDef.Skill.人狼).getCharaId(); // 狼以外の生存している誰か
        }
        // 噛む人（生存している狼で、狼が2名以上の場合は昨日噛んだ人は除外）
        List<Chara> attackableWolfList = getAttackableWolfList(villageId, settings, newDay, villagePlayerList);
        Integer attackCharaId = getRandomInList(attackableWolfList).getCharaId();
        // 能力セット
        insertAbility(villageId, newDay, attackCharaId, attackedCharaId, null, CDef.AbilityType.襲撃);
        // 時計回りの足音セット
        String footStep = footstepLogic.makeClockwiseFootStep(village, attackCharaId, attackedCharaId, villagePlayerList);
        footstepLogic.insertFootStep(villageId, newDay, attackCharaId, footStep);
        messageLogic.insertAbilityMessage(villageId, newDay, attackCharaId, attackedCharaId, villagePlayerList, footStep, true);
    }

    private List<Chara> getAttackableWolfList(Integer villageId, VillageSettings settings, int day, List<VillagePlayer> villagePlayerList) {
        // 生存している狼リスト
        List<Chara> liveAttackerList = villagePlayerList.stream()
                .filter(vp -> vp.isIsDeadFalse() && vp.getSkillCodeAsSkill() == CDef.Skill.人狼)
                .map(vp -> vp.getChara().get())
                .collect(Collectors.toList());
        if (settings.isIsAvailableSameWolfAttackTrue() || liveAttackerList.size() <= 1) {
            return liveAttackerList; // 連続襲撃可能
        }
        // 連続襲撃不可
        // 昨日襲撃した狼を除外して返す
        Integer yesterdayAttackerId = dayChangeLogicHelper.selectYesterdayAttackerId(villageId, day);
        return liveAttackerList.stream().filter(attacker -> !attacker.getCharaId().equals(yesterdayAttackerId)).collect(
                Collectors.toList());
    }

    private void insertDefaultGuard(Integer villageId, int newDay, List<VillagePlayer> villagePlayerList, Village village) {
        // 生存している狩人
        List<VillagePlayer> hunterList =
                villagePlayerList.stream().filter(vp -> vp.getSkillCodeAsSkill() == CDef.Skill.狩人 && vp.isIsDeadFalse()).collect(
                        Collectors.toList());
        if (CollectionUtils.isEmpty(hunterList)) {
            return;
        }
        hunterList.forEach(hunter -> {
            Integer hunterCharaId = hunter.getCharaId();
            // 護衛される人(生存者の中の誰か）
            Integer targetCharaId = getRandomInList(villagePlayerList, vp -> !vp.getCharaId().equals(hunterCharaId)).getCharaId();
            insertAbility(villageId, newDay, hunterCharaId, targetCharaId, null, CDef.AbilityType.護衛);
            // 時計回りの足音セット
            String footStep = footstepLogic.makeClockwiseFootStep(village, hunterCharaId, targetCharaId, villagePlayerList);
            footstepLogic.insertFootStep(villageId, newDay, hunterCharaId, footStep);
            messageLogic.insertAbilityMessage(villageId, newDay, hunterCharaId, targetCharaId, villagePlayerList, footStep, true);
        });
    }

    private void insertDefaultInvestigate(Integer villageId, List<VillagePlayer> villagePlayerList, int day) {
        // 生存している探偵
        List<VillagePlayer> livingDetectiveList =
                villagePlayerList.stream().filter(vp -> vp.getSkillCodeAsSkill() == CDef.Skill.探偵 && vp.isIsDeadFalse()).collect(
                        Collectors.toList());
        if (CollectionUtils.isEmpty(livingDetectiveList)) {
            return;
        }
        List<String> footstepList = footstepLogic.getFootstepList(villageId, day);
        if (CollectionUtils.isEmpty(footstepList)) {
            return; // 候補の足音なし
        }
        livingDetectiveList.forEach(detective -> {
            Integer detectiveCharaId = detective.getCharaId();
            // ランダムでセット
            Collections.shuffle(footstepList);
            insertAbility(villageId, day, detectiveCharaId, null, footstepList.get(0), CDef.AbilityType.捜査);
            messageLogic.insertAbilityMessage(villageId, day, detectiveCharaId, null, villagePlayerList, footstepList.get(0), true);
        });
    }

    private void insertDefaultSeer(Integer villageId, int newDay, List<VillagePlayer> villagePlayerList, Village village) {
        // 占う人
        List<VillagePlayer> livingSeerList =
                villagePlayerList.stream().filter(vp -> vp.getSkillCodeAsSkill().isHasDivineAbility() && vp.isIsDeadFalse()).collect(
                        Collectors.toList());
        if (CollectionUtils.isEmpty(livingSeerList)) {
            return;
        }
        livingSeerList.forEach(seer -> {
            Integer seerCharaId = seer.getCharaId();
            // 占われる人（生存者の中の誰か）
            Integer targetCharaId = getRandomInList(villagePlayerList, vp -> !vp.getCharaId().equals(seerCharaId)).getCharaId();
            insertAbility(villageId, newDay, seerCharaId, targetCharaId, null, CDef.AbilityType.占い);
            // 時計回りの足音セット
            String footStep = footstepLogic.makeClockwiseFootStep(village, seerCharaId, targetCharaId, villagePlayerList);
            footstepLogic.insertFootStep(villageId, newDay, seerCharaId, footStep);
            messageLogic.insertAbilityMessage(villageId, newDay, seerCharaId, targetCharaId, villagePlayerList, footStep, true);
        });
    }

    private void insertDefaultFootstep(Integer villageId, int newDay, List<VillagePlayer> vPlayerList) {
        // 妖狐狂人
        List<VillagePlayer> foxMadmanList = vPlayerList.stream()
                .filter(vp -> (vp.getSkillCodeAsSkill() == CDef.Skill.妖狐 || vp.getSkillCodeAsSkill().isHasMadmanAbility())
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
        vPlayerList.stream().filter(vp -> vp.isIsDeadFalse()).sorted((vp1, vp2) -> vp1.getRoomNumber() - vp2.getRoomNumber()).forEach(
                player -> {
                    joiner.add(CharaUtil.makeCharaName(player));
                });
        messageLogic.insertMessageIgnoreError(villageId, day, CDef.MessageType.公開システムメッセージ, joiner.toString());
    }

    private void insertFootstepMessage(Integer villageId, int newDay, List<VillagePlayer> vPlayerList) {
        if (newDay == 1) {
            return;
        }
        List<Integer> livingPlayerRoomNumList =
                vPlayerList.stream().filter(vp -> vp.isIsDeadFalse()).map(VillagePlayer::getRoomNumber).collect(Collectors.toList());
        String message = footstepLogic.getFootstepMessage(villageId, newDay - 1, livingPlayerRoomNumList);
        messageLogic.insertMessageIgnoreError(villageId, newDay, CDef.MessageType.公開システムメッセージ, message);
    }

    // 初日、2日目以外の日付更新処理
    private void dayChange(Integer villageId, int day, List<VillagePlayer> vPlayerList, VillageSettings settings) {
        // 突然死
        List<VillagePlayer> suddonlyDeathVPlayerList = killNoVotePlayer(villageId, day, vPlayerList, settings);

        // 能力行使内容取得
        List<Ability> abilityList = selectAbilityList(villageId, day);

        // 罠、爆弾設置メッセージ
        insertTrapMessages(villageId, day, vPlayerList, abilityList, suddonlyDeathVPlayerList);
        insertBombMessages(villageId, day, vPlayerList, abilityList, suddonlyDeathVPlayerList);

        // 処刑
        VillagePlayer executedPlayer = execute(villageId, day, vPlayerList, settings, suddonlyDeathVPlayerList);
        Integer executedPlayerId = executedPlayer == null ? null : executedPlayer.getVillagePlayerId();

        // 護衛
        List<VillagePlayer> guardedPlayerList = guard(villageId, day, vPlayerList, executedPlayerId, suddonlyDeathVPlayerList, abilityList);

        // 占い
        List<VillagePlayer> divinedPlayerList =
                divine(villageId, day, vPlayerList, executedPlayerId, suddonlyDeathVPlayerList, abilityList);

        // 呪殺
        List<VillagePlayer> divineKilledPlayerList =
                divineKill(villageId, day, vPlayerList, executedPlayerId, suddonlyDeathVPlayerList, divinedPlayerList);

        // 捜査
        invastigate(villageId, day, executedPlayerId, suddonlyDeathVPlayerList, vPlayerList, abilityList);

        // 霊能
        psychic(villageId, day, vPlayerList, executedPlayer, suddonlyDeathVPlayerList);

        // 襲撃
        Optional<VillagePlayer> optAttackedPlayer =
                attack(villageId, day, vPlayerList, executedPlayerId, suddonlyDeathVPlayerList, guardedPlayerList, abilityList);

        // 罠
        List<VillagePlayer> trappedPlayerList = trap(villageId, day, vPlayerList, abilityList, executedPlayer, divineKilledPlayerList,
                optAttackedPlayer, suddonlyDeathVPlayerList);

        // 爆弾
        List<VillagePlayer> bombedPlayerList = bomb(villageId, day, vPlayerList, abilityList, executedPlayer, divineKilledPlayerList,
                optAttackedPlayer, suddonlyDeathVPlayerList, trappedPlayerList);

        // 無惨メッセージ
        insertAttackedMessage(villageId, day, divineKilledPlayerList, optAttackedPlayer, trappedPlayerList, bombedPlayerList);

        if (day == 2 && optAttackedPlayer.isPresent()) {
            messageLogic.insertMessageIgnoreError(villageId, day, CDef.MessageType.公開システムメッセージ,
                    messageSource.getMessage("village.start.message.day2", null, Locale.JAPAN));
        }

        // 勝敗判定、エピローグ処理
        Optional<CDef.Camp> optWinCamp = endVillageIfNeeded(villageId, day);

        if (optWinCamp.isPresent()) {
            return;
        }

        // 投票、能力行使のデフォルト設定
        setDefaultVoteAndAbility(villageId, day, settings);
    }

    // 勝敗判定、エピローグ処理
    private Optional<Camp> endVillageIfNeeded(Integer villageId, int day) {
        ListResultBean<VillagePlayer> villagePlayerList = dayChangeLogicHelper.selectVillagePlayerList(villageId);

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
        dayChangeLogicHelper.updateVillageEpilogue(villageId, day, winCamp);
        dayChangeLogicHelper.deadBomberIfNeeded(villageId, day, villagePlayerList);
        ListResultBean<VillagePlayer> vPlayerList = dayChangeLogicHelper.selectVillagePlayerList(villageId);
        dayChangeLogicHelper.updateIsWin(vPlayerList, winCamp);
        // エピローグ遷移メッセージ登録
        String message = getEpilogueMessage(winCamp, werewolfCount);
        messageLogic.insertMessageIgnoreError(villageId, day, CDef.MessageType.公開システムメッセージ, message);
        // 参加者一覧メッセージ登録
        insertPlayerListMessage(villageId, day, vPlayerList);
        // エピは固定で24時間
        updateVillageDay(villageId, day);
        // tweet
        Village vil = villageBhv.selectEntityWithDeletedCheck(cb -> {
            cb.query().setVillageId_Equal(villageId);
            cb.setupSelect_VillageSettingsAsOne();
        });
        if (StringUtils.isEmpty(vil.getVillageSettingsAsOne().get().getJoinPassword())) {
            twitterLogic.tweet(String.format("%sがエピローグを迎えました。", vil.getVillageDisplayName()), villageId);
        }
    }

    private void updateVillageDay(Integer villageId, int day) {
        // 登録済みなので、1日前の更新時間＋24時間にupdate
        OptionalEntity<VillageDay> vd = villageDayBhv.selectByPK(villageId, day - 1);
        VillageDay entity = new VillageDay();
        entity.setVillageId(villageId);
        entity.setDay(day);
        entity.setDaychangeDatetime(vd.get().getDaychangeDatetime().plusDays(1));
        villageDayBhv.update(entity);
    }

    // 参加者一覧メッセージ登録
    private void insertPlayerListMessage(Integer villageId, int day, ListResultBean<VillagePlayer> villagePlayerList) {
        StringJoiner joiner = new StringJoiner("\n");
        villagePlayerList.stream().sorted((vp1, vp2) -> vp1.getRoomNumber() - vp2.getRoomNumber()).forEach(player -> {
            joiner.add(String.format("%s (%s)、%s、%s。%sだった。", // 
                    CharaUtil.makeCharaName(player), // 
                    player.getPlayer().get().getPlayerName(), //
                    player.isIsDeadTrue() ? "死亡" : "生存", //
                    player.isIsWinTrue() ? "勝利" : "敗北", //
                    player.getSkillBySkillCode().get().getSkillName()));
        });
        ListResultBean<VillagePlayer> spectatorList = villagePlayerBhv.selectList(cb -> {
            cb.setupSelect_Chara();
            cb.setupSelect_Player();
            cb.query().setVillageId_Equal(villageId);
            cb.query().setIsGone_Equal_False();
            cb.query().setIsSpectator_Equal_True();
        });
        if (CollectionUtils.isNotEmpty(spectatorList)) {
            spectatorList.stream().forEach(player -> {
                joiner.add(String.format("%s (%s)、見学参加だった。", CharaUtil.makeCharaName(player), player.getPlayer().get().getPlayerName()));
            });
        }
        messageLogic.insertMessageIgnoreError(villageId, day, CDef.MessageType.公開システムメッセージ, joiner.toString());
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
    private void insertAttackedMessage(Integer villageId, int day, List<VillagePlayer> divineKilledPlayerList,
            Optional<VillagePlayer> optAttackedPlayer, List<VillagePlayer> trappedPlayerList, List<VillagePlayer> bombedPlayerList) {
        List<VillagePlayer> victimPlayerList = new ArrayList<>();
        victimPlayerList.addAll(divineKilledPlayerList);
        victimPlayerList.addAll(trappedPlayerList);
        victimPlayerList.addAll(bombedPlayerList);
        optAttackedPlayer.ifPresent(player -> victimPlayerList.add(player));
        if (CollectionUtils.isEmpty(victimPlayerList)) {
            messageLogic.insertMessageIgnoreError(villageId, day, CDef.MessageType.公開システムメッセージ, "今日は犠牲者がいないようだ。人狼は襲撃に失敗したのだろうか。");
        } else {
            Collections.shuffle(victimPlayerList);
            StringJoiner joiner = new StringJoiner("と", "次の日の朝、", "が無惨な姿で発見された。");
            victimPlayerList.stream().forEach(player -> {
                joiner.add(CharaUtil.makeCharaName(player));
            });
            messageLogic.insertMessageIgnoreError(villageId, day, CDef.MessageType.公開システムメッセージ, joiner.toString());
        }
    }

    // 襲撃
    private Optional<VillagePlayer> attack(Integer villageId, int day, List<VillagePlayer> villagePlayerList, Integer executedPlayerId,
            List<VillagePlayer> suddonlyDeathVPlayerList, List<VillagePlayer> guardedPlayerList, List<Ability> abilityList) {

        List<VillagePlayer> livingWolfList = villagePlayerList.stream()
                .filter(vp -> vp.isIsDeadFalse() // 死亡していない
                        && suddonlyDeathVPlayerList.stream().noneMatch(sdvp -> sdvp.getVillagePlayerId().equals(vp.getVillagePlayerId())) // 突然死していない
                        && !vp.getVillagePlayerId().equals(executedPlayerId) // 処刑されていない
                        && vp.getSkillCodeAsSkill() == CDef.Skill.人狼) // 人狼である
                .collect(Collectors.toList());
        if (CollectionUtils.isEmpty(livingWolfList)) {
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
            Collections.shuffle(livingWolfList);
            String attackMessage = String.format("%s！今日がお前の命日だ！", targetPlayer.getChara().get().getCharaName());
            VillagePlayer attackerPlayer = livingWolfList.get(0);
            boolean hasWerewolfFace = hasWerewolfFace(attackerPlayer);
            messageLogic.insertMessageIgnoreError(villageId, day, CDef.MessageType.人狼の囁き, attackMessage,
                    attackerPlayer.getVillagePlayerId(), true, hasWerewolfFace ? CDef.FaceType.囁き : CDef.FaceType.通常);
        }

        // 襲撃成功したか
        if (isAttackSuccess(targetPlayer, executedPlayerId, suddonlyDeathVPlayerList, guardedPlayerList)) {
            dayChangeLogicHelper.updateVillagePlayerDead(day, targetPlayer, CDef.DeadReason.襲撃); // 死亡処理
            return Optional.ofNullable(targetPlayer);
        }
        return Optional.empty();
    }

    private boolean hasWerewolfFace(VillagePlayer attackerPlayer) {
        OptionalEntity<CharaImage> optAttackFace = charaImageBhv.selectEntity(cb -> {
            cb.query().setCharaId_Equal(attackerPlayer.getCharaId());
            cb.query().setFaceTypeCode_Equal_囁き();
        });
        return optAttackFace.isPresent();
    }

    // 襲撃成功したか
    private boolean isAttackSuccess(VillagePlayer attackedPlayer, Integer executedPlayerId, List<VillagePlayer> suddonlyDeathVPlayerList,
            List<VillagePlayer> guardedPlayerList) {
        if (attackedPlayer.getVillagePlayerId().equals(executedPlayerId)) {
            return false; // 処刑されていた
        }
        if (suddonlyDeathVPlayerList.stream().anyMatch(sdvp -> sdvp.getVillagePlayerId().equals(attackedPlayer.getVillagePlayerId()))) {
            return false; // 突然死した
        }
        if (guardedPlayerList.stream().anyMatch(vp -> vp.getVillagePlayerId().equals(attackedPlayer.getVillagePlayerId()))) {
            return false; // 護衛されていた
        }
        if (attackedPlayer.getSkillCodeAsSkill() == CDef.Skill.妖狐) {
            return false; // 噛み先が妖狐
        }
        return true;
    }

    // 罠設置メッセージ
    private void insertTrapMessages(Integer villageId, int day, List<VillagePlayer> vPlayerList, List<Ability> abilityList,
            List<VillagePlayer> suddonlyDeathVPlayerList) {
        abilityList.stream().filter(ability -> {
            return ability.isAbilityTypeCode罠設置() //
                    && suddonlyDeathVPlayerList.stream().noneMatch(vp -> vp.getCharaId().equals(ability.getCharaId()));
        }).forEach(trap -> {
            // 設置した人
            VillagePlayer trapper = vPlayerList.stream().filter(vp -> vp.getCharaId().equals(trap.getCharaId())).findFirst().get();
            // 設置された部屋の人
            VillagePlayer targetPlayer =
                    vPlayerList.stream().filter(vp -> vp.getCharaId().equals(trap.getTargetCharaId())).findFirst().get();
            String message = String.format("%sは、%sの部屋に罠を設置した。", CharaUtil.makeCharaName(trapper), CharaUtil.makeCharaName(targetPlayer));
            messageLogic.insertMessageIgnoreError(villageId, day, CDef.MessageType.非公開システムメッセージ, message);
        });
    }

    // 爆弾設置メッセージ
    private void insertBombMessages(Integer villageId, int day, List<VillagePlayer> vPlayerList, List<Ability> abilityList,
            List<VillagePlayer> suddonlyDeathVPlayerList) {
        abilityList.stream().filter(ability -> {
            return ability.isAbilityTypeCode爆弾設置() //
                    && suddonlyDeathVPlayerList.stream().noneMatch(vp -> vp.getCharaId().equals(ability.getCharaId()));
        }).forEach(bomb -> {
            // 設置した人
            VillagePlayer trapper = vPlayerList.stream().filter(vp -> vp.getCharaId().equals(bomb.getCharaId())).findFirst().get();
            // 設置された部屋の人
            VillagePlayer targetPlayer =
                    vPlayerList.stream().filter(vp -> vp.getCharaId().equals(bomb.getTargetCharaId())).findFirst().get();
            String message = String.format("%sは、%sの部屋に爆弾を設置した。", CharaUtil.makeCharaName(trapper), CharaUtil.makeCharaName(targetPlayer));
            messageLogic.insertMessageIgnoreError(villageId, day, CDef.MessageType.非公開システムメッセージ, message);
        });
    }

    private List<VillagePlayer> trap(Integer villageId, int day, List<VillagePlayer> vPlayerList, List<Ability> abilityList,
            VillagePlayer executedPlayer, List<VillagePlayer> divineKilledPlayerList, Optional<VillagePlayer> optAttackedPlayer,
            List<VillagePlayer> suddonlyDeathVPlayerList) {
        // 死亡している人
        List<VillagePlayer> deadPlayerList = new ArrayList<>();
        deadPlayerList.add(executedPlayer);
        deadPlayerList.addAll(divineKilledPlayerList);
        optAttackedPlayer.ifPresent(attackedPlayer -> deadPlayerList.add(attackedPlayer));
        // 罠
        Set<VillagePlayer> wholeTrappedPlayerSet = new HashSet<>();
        abilityList.stream().filter(ability -> {
            return ability.isAbilityTypeCode罠設置() //
                    && suddonlyDeathVPlayerList.stream().noneMatch(vp -> vp.getCharaId().equals(ability.getCharaId()));
        }).forEach(trap -> {
            // 設置された部屋
            Integer roomNumber =
                    vPlayerList.stream().filter(vp -> vp.getCharaId().equals(trap.getTargetCharaId())).findFirst().get().getRoomNumber();
            // 設置された部屋を通過した人
            List<VillagePlayer> trappedPlayerList = footstepLogic.getPassedPlayerList(villageId, day - 1, roomNumber, vPlayerList);
            wholeTrappedPlayerSet.addAll(trappedPlayerList);
        });
        // 通過した人の死亡処理
        wholeTrappedPlayerSet.stream().filter(vp -> {
            return deadPlayerList.stream().noneMatch(deadVP -> deadVP.getVillagePlayerId().equals(vp.getVillagePlayerId()));
        }).forEach(trappedPlayer -> {
            dayChangeLogicHelper.updateVillagePlayerDead(day, trappedPlayer, CDef.DeadReason.罠死); // 死亡処理
        });

        return wholeTrappedPlayerSet.stream().filter(vp -> {
            return deadPlayerList.stream().noneMatch(deadVP -> deadVP.getVillagePlayerId().equals(vp.getVillagePlayerId()));
        }).collect(Collectors.toList());
    }

    private List<VillagePlayer> bomb(Integer villageId, int day, List<VillagePlayer> vPlayerList, List<Ability> abilityList,
            VillagePlayer executedPlayer, List<VillagePlayer> divineKilledPlayerList, Optional<VillagePlayer> optAttackedPlayer,
            List<VillagePlayer> suddonlyDeathVPlayerList, List<VillagePlayer> trappedPlayerList) {
        // 死亡している人
        List<VillagePlayer> deadPlayerList = new ArrayList<>();
        deadPlayerList.add(executedPlayer);
        deadPlayerList.addAll(divineKilledPlayerList);
        deadPlayerList.addAll(trappedPlayerList);
        optAttackedPlayer.ifPresent(attackedPlayer -> deadPlayerList.add(attackedPlayer));
        // 爆弾
        Set<VillagePlayer> wholeBombedPlayerSet = new HashSet<>();
        abilityList.stream().filter(ability -> {
            return ability.isAbilityTypeCode爆弾設置() //
                    && suddonlyDeathVPlayerList.stream().noneMatch(vp -> vp.getCharaId().equals(ability.getCharaId()));
        }).forEach(trap -> {
            // 設置した人
            VillagePlayer bomber = vPlayerList.stream().filter(vp -> vp.getCharaId().equals(trap.getCharaId())).findFirst().get();
            // 設置された部屋の人
            VillagePlayer bombedRoomPlayer =
                    vPlayerList.stream().filter(vp -> vp.getCharaId().equals(trap.getTargetCharaId())).findFirst().get();
            // 設置された部屋を通過した人
            List<VillagePlayer> bombedPlayerList =
                    footstepLogic.getPassedPlayerList(villageId, day - 1, bombedRoomPlayer.getRoomNumber(), vPlayerList);
            if (bombedPlayerList.isEmpty()) {
                // 爆弾魔が死亡
                wholeBombedPlayerSet.add(bomber);
            } else {
                // 設置された部屋の人と通過した人が死亡
                wholeBombedPlayerSet.add(bombedRoomPlayer);
                wholeBombedPlayerSet.addAll(bombedPlayerList);
            }
        });
        // 死亡処理
        wholeBombedPlayerSet.stream().filter(vp -> {
            return deadPlayerList.stream().noneMatch(deadVP -> deadVP.getVillagePlayerId().equals(vp.getVillagePlayerId()));
        }).forEach(trappedPlayer -> {
            dayChangeLogicHelper.updateVillagePlayerDead(day, trappedPlayer, CDef.DeadReason.爆死); // 死亡処理
        });

        return wholeBombedPlayerSet.stream().filter(vp -> {
            return deadPlayerList.stream().noneMatch(deadVP -> deadVP.getVillagePlayerId().equals(vp.getVillagePlayerId()));
        }).collect(Collectors.toList());
    }

    // 霊視
    private void psychic(Integer villageId, int day, List<VillagePlayer> villagePlayerList, VillagePlayer executedPlayer,
            List<VillagePlayer> suddonlyDeathVPlayerList) {
        List<VillagePlayer> deadPlayerList = new ArrayList<>();
        if (executedPlayer != null) {
            deadPlayerList.add(executedPlayer);
        }
        if (CollectionUtils.isNotEmpty(suddonlyDeathVPlayerList)) {
            deadPlayerList.addAll(suddonlyDeathVPlayerList);
        }
        if (CollectionUtils.isEmpty(deadPlayerList)) {
            return;
        }
        // 霊能者
        if (villagePlayerList.stream().anyMatch(vp -> vp.isIsDeadFalse() // 死亡していない
                && (executedPlayer == null || !vp.getVillagePlayerId().equals(executedPlayer.getVillagePlayerId())) // 処刑されていない
                && suddonlyDeathVPlayerList.stream().noneMatch(sdvp -> sdvp.getVillagePlayerId().equals(vp.getVillagePlayerId())) // 突然死していない
                && vp.getSkillCodeAsSkill() == CDef.Skill.霊能者) // 霊能者である
        ) {
            StringJoiner joiner = new StringJoiner("\n");
            deadPlayerList.forEach(deadPlayer -> {
                boolean isTargetWerewolf = deadPlayer.getSkillCodeAsSkill() == CDef.Skill.人狼;
                joiner.add(String.format("%sは%sのようだ。", CharaUtil.makeCharaName(deadPlayer), isTargetWerewolf ? "人狼" : "人間"));
            });
            messageLogic.insertMessageIgnoreError(villageId, day, CDef.MessageType.白黒霊視結果, joiner.toString());
        }

        // 導師
        if (villagePlayerList.stream().anyMatch(vp -> vp.isIsDeadFalse() // 死亡していない
                && (executedPlayer == null || !vp.getVillagePlayerId().equals(executedPlayer.getVillagePlayerId())) // 処刑されていない
                && suddonlyDeathVPlayerList.stream().noneMatch(sdvp -> sdvp.getVillagePlayerId().equals(vp.getVillagePlayerId())) // 突然死していない
                && vp.getSkillCodeAsSkill().isHasSkillPsychicAbility()) // 導師または魔神官
        ) {
            StringJoiner joiner = new StringJoiner("\n");
            deadPlayerList.forEach(deadPlayer -> {
                joiner.add(String.format("%sは%sのようだ。", CharaUtil.makeCharaName(deadPlayer), deadPlayer.getSkillCodeAsSkill().alias()));
            });
            messageLogic.insertMessageIgnoreError(villageId, day, CDef.MessageType.役職霊視結果, joiner.toString());
        }
    }

    // 呪殺
    private List<VillagePlayer> divineKill(Integer villageId, int day, List<VillagePlayer> villagePlayerList, Integer executedPlayerId,
            List<VillagePlayer> suddonlyDeathVPlayerList, List<VillagePlayer> divinedPlayerList) {
        if (CollectionUtils.isEmpty(divinedPlayerList)) {
            return new ArrayList<>();
        }
        List<VillagePlayer> divineKilledPlayerList = villagePlayerList.stream()
                .filter(vp -> vp.isIsDeadFalse() // 死亡していない 
                        && !vp.getVillagePlayerId().equals(executedPlayerId) // 処刑されていない
                        && suddonlyDeathVPlayerList.stream().noneMatch(sdvp -> sdvp.getVillagePlayerId().equals(vp.getVillagePlayerId())) // 突然死していない
                        && vp.getSkillCodeAsSkill() == CDef.Skill.妖狐 // 妖狐である
                        && divinedPlayerList.stream()
                                .anyMatch(divinedPlayer -> divinedPlayer.getVillagePlayerId().equals(vp.getVillagePlayerId()))) // 占われた
                .distinct()
                .collect(Collectors.toList());
        if (CollectionUtils.isEmpty(divineKilledPlayerList)) {
            return new ArrayList<>();
        }
        divineKilledPlayerList.forEach(divineKilledPlayer -> {
            dayChangeLogicHelper.updateVillagePlayerDead(day, divineKilledPlayer, CDef.DeadReason.呪殺); // 死亡処理
        });

        return divineKilledPlayerList;
    }

    // 占い
    private List<VillagePlayer> divine(Integer villageId, int day, List<VillagePlayer> villagePlayerList, Integer executedPlayerId,
            List<VillagePlayer> suddonlyDeathVPlayerList, List<Ability> abilityList) {
        List<VillagePlayer> livingSeerList = villagePlayerList.stream()
                .filter(vp -> vp.isIsDeadFalse() // 死亡していない
                        && !vp.getVillagePlayerId().equals(executedPlayerId) // 処刑されていない
                        && suddonlyDeathVPlayerList.stream().noneMatch(sdvp -> sdvp.getVillagePlayerId().equals(vp.getVillagePlayerId())) // 突然死していない
                        && vp.getSkillCodeAsSkill().isHasDivineAbility()) // 占い能力がある 
                .collect(Collectors.toList());
        if (CollectionUtils.isEmpty(livingSeerList)) {
            return new ArrayList<>(); // 占い師が既に死亡している場合は何もしない
        }

        List<VillagePlayer> divinedPlayerList = new ArrayList<>();
        livingSeerList.forEach(seerPlayer -> {
            Optional<Ability> optSeer = abilityList.stream().filter(ability -> {
                return ability.getAbilityTypeCodeAsAbilityType() == CDef.AbilityType.占い
                        && seerPlayer.getCharaId().equals(ability.getCharaId());
            }).findFirst();
            if (!optSeer.isPresent()) {
                return; // 能力セットしていない場合は何もしない
            }
            VillagePlayer targetPlayer =
                    villagePlayerList.stream().filter(vp -> vp.getCharaId().equals(optSeer.get().getTargetCharaId())).findFirst().get();
            String message = makeDivineMessage(seerPlayer, targetPlayer);
            messageLogic.insertMessageIgnoreError(villageId, day,
                    seerPlayer.isSkillCode占い師() ? CDef.MessageType.白黒占い結果 : CDef.MessageType.役職占い結果, message,
                    seerPlayer.getVillagePlayerId(), true, null);
            divinedPlayerList.add(targetPlayer);
        });
        return divinedPlayerList;
    }

    private String makeDivineMessage(VillagePlayer seerPlayer, VillagePlayer targetPlayer) {
        String targetCharaName = CharaUtil.makeCharaName(targetPlayer);
        if (seerPlayer.isSkillCode占い師()) {
            boolean isTargetWerewolf = targetPlayer.getSkillCodeAsSkill() == CDef.Skill.人狼;
            return String.format("%sは、%sを占った。\n%sは%sのようだ。", CharaUtil.makeCharaName(seerPlayer), targetCharaName, targetCharaName,
                    isTargetWerewolf ? "人狼" : "人間");
        } else {
            return String.format("%sは、%sを占った。\n%sは%sのようだ。", CharaUtil.makeCharaName(seerPlayer), targetCharaName, targetCharaName,
                    targetPlayer.getSkillCodeAsSkill().alias());
        }
    }

    // 護衛
    private List<VillagePlayer> guard(Integer villageId, int day, List<VillagePlayer> villagePlayerList, Integer executedPlayerId,
            List<VillagePlayer> suddonlyDeathVPlayerList, List<Ability> abilityList) {
        if (day == 2) {
            return new ArrayList<>();
        }

        List<VillagePlayer> livingHunterList = villagePlayerList.stream()
                .filter(vp -> vp.isIsDeadFalse() // 死亡していない
                        && !vp.getVillagePlayerId().equals(executedPlayerId) // 処刑されていない
                        && suddonlyDeathVPlayerList.stream().noneMatch(sdvp -> sdvp.getVillagePlayerId().equals(vp.getVillagePlayerId())) // 突然死していない
                        && vp.getSkillCodeAsSkill() == CDef.Skill.狩人) //　狩人である
                .collect(Collectors.toList());
        if (CollectionUtils.isEmpty(livingHunterList)) {
            return new ArrayList<>(); // 狩人が既に死亡している場合は何もしない
        }

        List<VillagePlayer> guardedPlayerList = new ArrayList<>();
        livingHunterList.forEach(hunter -> {
            Optional<Ability> optGuard = abilityList.stream().filter(ability -> {
                return ability.getAbilityTypeCodeAsAbilityType() == CDef.AbilityType.護衛 && hunter.getCharaId().equals(ability.getCharaId());
            }).findFirst();
            if (!optGuard.isPresent()) {
                return; // 能力セットしていない場合は何もしない
            }
            VillagePlayer targetPlayer =
                    villagePlayerList.stream().filter(vp -> vp.getCharaId().equals(optGuard.get().getTargetCharaId())).findFirst().get();
            String message = String.format("%sは、%sを護衛している。", CharaUtil.makeCharaName(hunter), CharaUtil.makeCharaName(targetPlayer));
            messageLogic.insertMessageIgnoreError(villageId, day, CDef.MessageType.非公開システムメッセージ, message);
            guardedPlayerList.add(targetPlayer);
        });
        return guardedPlayerList;
    }

    // 調査
    private void invastigate(Integer villageId, int day, Integer executedPlayerId, List<VillagePlayer> suddonlyDeathVPlayerList,
            List<VillagePlayer> vPlayerList, List<Ability> abilityList) {
        if (day == 2) {
            return;
        }
        List<VillagePlayer> livingDetectiveList = vPlayerList.stream()
                .filter(vp -> vp.isIsDeadFalse() // 死亡していない
                        && !vp.getVillagePlayerId().equals(executedPlayerId) // 処刑されていない
                        && suddonlyDeathVPlayerList.stream().noneMatch(sdvp -> sdvp.getVillagePlayerId().equals(vp.getVillagePlayerId())) // 突然死していない
                        && vp.getSkillCodeAsSkill() == CDef.Skill.探偵)
                .collect(Collectors.toList());
        if (CollectionUtils.isEmpty(livingDetectiveList)) {
            return; // 探偵が既に死亡している場合は何もしない
        }
        livingDetectiveList.forEach(detective -> {
            Optional<Ability> optInvestigate = abilityList.stream().filter(ability -> {
                return ability.getAbilityTypeCodeAsAbilityType() == CDef.AbilityType.捜査
                        && detective.getCharaId().equals(ability.getCharaId());
            }).findFirst();
            if (!optInvestigate.isPresent()) {
                return; // 能力セットしていない場合は何もしない
            }
            String targetFootstep = optInvestigate.get().getTargetFootstep();
            String skillName = footstepLogic.getSkillByFootstep(villageId, day - 2, targetFootstep, vPlayerList);
            String message = String.format("%sは、昨日響いた足音%sについて調査した。\n%sの足音を響かせたのは%sのようだ。", CharaUtil.makeCharaName(detective),
                    targetFootstep, targetFootstep, skillName);
            messageLogic.insertMessageIgnoreError(villageId, day, CDef.MessageType.足音調査結果, message, detective.getVillagePlayerId(), true,
                    null);
        });
    }

    // 突然死
    private List<VillagePlayer> killNoVotePlayer(Integer villageId, int day, List<VillagePlayer> vPlayerList, VillageSettings settings) {
        if (settings.isIsAvailableSuddonlyDeathFalse() || day < 3) {
            return new ArrayList<>();
        }
        // 前日に投票していない人が対象
        // 投票した人
        List<Integer> voteCharaIdList = voteBhv.selectList(cb -> {
            cb.query().setVillageId_Equal(villageId);
            cb.query().setDay_Equal(day - 1);
        }).stream().map(vote -> vote.getCharaId()).collect(Collectors.toList());
        // 投票していない人
        List<VillagePlayer> noVotePlayerList = vPlayerList.stream().filter(vp -> {
            if (vp.getCharaId().intValue() == settings.getDummyCharaId().intValue() || vp.isIsSpectatorTrue() || vp.isIsDeadTrue()) {
                return false; // ダミーと見学と既に死亡している人は対象外
            }
            // 投票していない人
            return !voteCharaIdList.contains(vp.getCharaId());
        }).collect(Collectors.toList());

        noVotePlayerList.forEach(vp -> {
            dayChangeLogicHelper.updateVillagePlayerDead(day, vp, CDef.DeadReason.突然); // 死亡処理
            updatePlayerRestrict(vp.getPlayerId()); // 入村制限
            String message = String.format("%sは突然死した。", CharaUtil.makeCharaName(vp));
            messageLogic.insertMessageIgnoreError(villageId, day, CDef.MessageType.公開システムメッセージ, message);
        });
        return noVotePlayerList;
    }

    // 処刑
    private VillagePlayer execute(Integer villageId, int day, List<VillagePlayer> vPlayerList, VillageSettings settings,
            List<VillagePlayer> suddonlyDeathVPlayerList) {
        if (day == 2) {
            return null; // 1→2日目は処刑なし
        }
        ListResultBean<Vote> voteList = selectVoteList(villageId, day - 1, suddonlyDeathVPlayerList); // 前日の投票を使う
        if (CollectionUtils.isEmpty(voteList)) {
            return null; // 全員突然死した場合
        }
        Map<Integer, Integer> voteNumMap = new HashMap<>();
        // 得票数トップのプレイヤーを取得（複数いる可能性があるのでリスト）
        List<Integer> executedCharaIdList = makeExecutedCandidateList(voteList, voteNumMap);
        Collections.shuffle(executedCharaIdList); // 得票数が同じ場合はランダム
        VillagePlayer executedPlayer =
                vPlayerList.stream().filter(vp -> vp.getCharaId().equals(executedCharaIdList.get(0))).findFirst().get();
        // 処刑
        if (suddonlyDeathVPlayerList.stream().noneMatch(vp -> vp.getVillagePlayerId().equals(executedPlayer.getVillagePlayerId()))) {
            dayChangeLogicHelper.updateVillagePlayerDead(day, executedPlayer, CDef.DeadReason.処刑); // 死亡処理            
        }
        // 個別投票メッセージ登録
        insertEachVoteMessage(villageId, day, vPlayerList, voteList, settings);
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

    private void insertEachVoteMessage(Integer villageId, int day, List<VillagePlayer> villagePlayerList, ListResultBean<Vote> voteList,
            VillageSettings settings) {
        voteList.sort((v1, v2) -> {
            Integer roomNumber1 =
                    villagePlayerList.stream().filter(vp -> vp.getCharaId().equals(v1.getCharaId())).findFirst().get().getRoomNumber();
            Integer roomNumber2 =
                    villagePlayerList.stream().filter(vp -> vp.getCharaId().equals(v2.getCharaId())).findFirst().get().getRoomNumber();
            return roomNumber1 - roomNumber2;
        });

        StringJoiner joiner = new StringJoiner("\n");
        joiner.add("投票結果は以下の通り。");
        int playerMaxLength = getMaxPlayerNameLength(voteList, villagePlayerList);
        int targetMaxLength = getMaxTargetNameLength(voteList, villagePlayerList);
        for (Vote vote : voteList) {
            Integer charaId = vote.getCharaId();
            VillagePlayer player = villagePlayerList.stream().filter(vp -> vp.getCharaId().equals(charaId)).findFirst().get();
            Integer targetCharaId = vote.getVoteCharaId();
            VillagePlayer targetPlayer = villagePlayerList.stream().filter(vp -> vp.getCharaId().equals(targetCharaId)).findFirst().get();
            joiner.add(String.format("%s → %s", StringUtils.rightPad(CharaUtil.makeCharaName(player), playerMaxLength, "　"), // 
                    StringUtils.rightPad(CharaUtil.makeCharaName(targetPlayer), targetMaxLength, "　")));
        }
        messageLogic.insertMessageIgnoreError(villageId, day,
                settings.isIsOpenVoteTrue() ? CDef.MessageType.公開システムメッセージ : CDef.MessageType.非公開システムメッセージ, joiner.toString());
    }

    private int getMaxPlayerNameLength(List<Vote> voteList, List<VillagePlayer> vPlayerList) {
        int playerMaxLength = 0;
        for (Vote vote : voteList) {
            Integer charaId = vote.getCharaId();
            VillagePlayer player = vPlayerList.stream().filter(vp -> vp.getCharaId().equals(charaId)).findFirst().get();
            if (CharaUtil.makeCharaName(player).length() > playerMaxLength) {
                playerMaxLength = CharaUtil.makeCharaName(player).length();
            }
        }
        return playerMaxLength;
    }

    private int getMaxTargetNameLength(List<Vote> voteList, List<VillagePlayer> vPlayerList) {
        int targetMaxLength = 0;
        for (Vote vote : voteList) {
            Integer targetCharaId = vote.getVoteCharaId();
            VillagePlayer targetPlayer = vPlayerList.stream().filter(vp -> vp.getCharaId().equals(targetCharaId)).findFirst().get();
            if (CharaUtil.makeCharaName(targetPlayer).length() > targetMaxLength) {
                targetMaxLength = CharaUtil.makeCharaName(targetPlayer).length();
            }
        }
        return targetMaxLength;
    }

    private void insertExecuteResultMessage(Integer villageId, int day, List<VillagePlayer> villagePlayerList,
            Map<Integer, Integer> voteNumMap, VillagePlayer executedPlayer) {
        List<Integer> sortedTargetCharaIdList = voteNumMap.keySet().stream().sorted((charaId1, charaId2) -> {
            Integer roomNumber1 =
                    villagePlayerList.stream().filter(vp -> vp.getCharaId().equals(charaId1)).findFirst().get().getRoomNumber();
            Integer roomNumber2 =
                    villagePlayerList.stream().filter(vp -> vp.getCharaId().equals(charaId2)).findFirst().get().getRoomNumber();
            return roomNumber1 - roomNumber2;
        }).collect(Collectors.toList());

        StringJoiner joiner = new StringJoiner("\n");
        for (Integer targetCharaId : sortedTargetCharaIdList) {
            Integer voteCount = voteNumMap.get(targetCharaId);
            VillagePlayer targetPlayer = villagePlayerList.stream().filter(vp -> vp.getCharaId().equals(targetCharaId)).findFirst().get();
            joiner.add(String.format("%s、%d票", CharaUtil.makeCharaName(targetPlayer), voteCount));
        }
        joiner.add(String.format("\n%sは村人達の手により処刑された。", CharaUtil.makeCharaName(executedPlayer)));
        messageLogic.insertMessageIgnoreError(villageId, day, CDef.MessageType.公開システムメッセージ, joiner.toString());
    }

    // 参加者数が不足している
    private boolean isInsufficientVillagerNum(VillageInfo vInfo) {
        return vInfo.vPlayerList.size() < vInfo.settings.getStartPersonMinNum();
    }

    // プロローグで長時間接続していない人がいたら退村させる
    private void leaveVillageIfNeeded(VillageInfo vInfo) {
        if (vInfo.day != 0) {
            return;
        }

        // 24時間アクセスしていなかったら村を出る
        LocalDateTime yesterday = WerewolfMansionDateUtil.currentLocalDateTime().minusDays(1L);

        villagePlayerBhv.selectList(cb -> {
            cb.setupSelect_Chara();
            cb.query().setVillageId_Equal(vInfo.villageId);
            cb.orScopeQuery(orCB -> {
                orCB.query().setLastAccessDatetime_IsNull();
                orCB.query().setLastAccessDatetime_LessThan(yesterday);
            });
            cb.query().setCharaId_NotEqual(vInfo.settings.getDummyCharaId());
            cb.query().setIsGone_Equal_False();
        }).stream().forEach(vp -> {
            villageParticipateLogic.leave(vp);
        });
    }

    // 特殊ルール変更
    private void updateVillageSettingsIfNeeded(Integer villageId, List<VillagePlayer> vPlayerList, VillageSettings settings) {
        // 狼人数が3より少ない場合、同一人狼による連続襲撃可能にする
        if (settings.isIsAvailableSameWolfAttackTrue()) {
            return;
        }
        String organize = settings.getOrganize();
        String personNumOrg = Stream.of(organize.replaceAll("\r\n", "\n").split("\n"))
                .filter(org -> org.length() == vPlayerList.size())
                .findFirst()
                .get();
        Map<Skill, Integer> skillPersonNum = SkillUtil.createSkillPersonNum(personNumOrg);
        Integer wolfNum = skillPersonNum.get(CDef.Skill.人狼);
        if (wolfNum < 3) {
            dayChangeLogicHelper.updateVillageSettingsSameWolfAttackTrue(villageId);
            messageLogic.insertMessageIgnoreError(villageId, 1, CDef.MessageType.公開システムメッセージ, "人狼の人数が3名より少ないため、同一人狼による連続襲撃を「可能」に変更します。");
        }
    }

    private void extendOrCancelVillage(VillageInfo vInfo, LocalDateTime daychangeDatetime) {
        if (vInfo.vPlayerList.size() > 1) {
            // 一人でも参加していたら延長
            dayChangeLogicHelper.updateVillageDay(vInfo, daychangeDatetime); // 村日付を1日延長
            messageLogic.insertMessageIgnoreError(vInfo.villageId, vInfo.day, CDef.MessageType.公開システムメッセージ, "まだ村人たちは揃っていないようだ。"); // 延長メッセージ登録
        } else {
            // 一人も参加していなかったら廃村
            dayChangeLogicHelper.updateVillageStatus(vInfo.villageId, CDef.VillageStatus.廃村);
        }
    }

    private void insertDummyCharaMessage(Integer villageId, List<VillagePlayer> vPlayerList, VillageSettings settings) {
        VillagePlayer dummyChara = vPlayerList.stream().filter(vp -> vp.getCharaId().equals(settings.getDummyCharaId())).findFirst().get();
        String message = dummyChara.getChara().get().getDefaultFirstdayMessage();
        if (StringUtils.isEmpty(message)) {
            return;
        }
        try {
            messageLogic.insertMessage(villageId, 1, CDef.MessageType.通常発言, message, dummyChara.getVillagePlayerId(), true,
                    CDef.FaceType.通常);
        } catch (WerewolfMansionBusinessException e) {}
    }

    private void tweetIfNeeded(Integer villageId, Village village, VillageSettings settings) {
        if (StringUtils.isNotEmpty(settings.getJoinPassword())) {
            return; // 身内村は通知しない
        }
        twitterLogic.tweet(String.format("%sが開始されました。", village.getVillageDisplayName()), villageId);
    }
}
