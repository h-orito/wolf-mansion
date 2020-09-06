package com.ort.app.logic.daychange;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.StringJoiner;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.dbflute.cbean.result.ListResultBean;
import org.dbflute.optional.OptionalEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import com.ort.app.datasource.AbilityService;
import com.ort.app.datasource.VoteService;
import com.ort.app.logic.MessageLogic;
import com.ort.app.logic.TwitterLogic;
import com.ort.app.logic.ability.AttackLogic;
import com.ort.app.logic.ability.AutopsyLogic;
import com.ort.app.logic.ability.BakeryLogic;
import com.ort.app.logic.ability.BombLogic;
import com.ort.app.logic.ability.CohabitLogic;
import com.ort.app.logic.ability.DivineLogic;
import com.ort.app.logic.ability.GuardLogic;
import com.ort.app.logic.ability.InvestigateLogic;
import com.ort.app.logic.ability.PsychicLogic;
import com.ort.app.logic.ability.SuicideLogic;
import com.ort.app.logic.ability.TrapLogic;
import com.ort.dbflute.allcommon.CDef;
import com.ort.dbflute.allcommon.CDef.Camp;
import com.ort.dbflute.exbhv.VillageBhv;
import com.ort.dbflute.exbhv.VillageDayBhv;
import com.ort.dbflute.exbhv.VillagePlayerBhv;
import com.ort.dbflute.exentity.Abilities;
import com.ort.dbflute.exentity.Village;
import com.ort.dbflute.exentity.VillageDay;
import com.ort.dbflute.exentity.VillagePlayer;
import com.ort.dbflute.exentity.VillagePlayers;
import com.ort.dbflute.exentity.VillageSettings;
import com.ort.dbflute.exentity.Votes;

@Component
public class ProgressLogic {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    @Autowired
    private DayChangeLogicHelper helper;
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private SuddenlyDeathLogic suddenlyDeathLogic;
    @Autowired
    private BombLogic bombLogic;
    @Autowired
    private TrapLogic trapLogic;
    @Autowired
    private ExecuteLogic executeLogic;
    @Autowired
    private GuardLogic guardLogic;
    @Autowired
    private DivineLogic divineLogic;
    @Autowired
    private InvestigateLogic investigateLogic;
    @Autowired
    private PsychicLogic psychicLogic;
    @Autowired
    private CohabitLogic cohabitLogic;
    @Autowired
    private AttackLogic attackLogic;
    @Autowired
    private MiserableLogic miserableLogic;
    @Autowired
    private SuicideLogic suicideLogic;
    @Autowired
    private AutopsyLogic autopsyLogic;
    @Autowired
    private BakeryLogic bakeryLogic;
    @Autowired
    private DefaultSetLogic defaultSetLogic;
    @Autowired
    private VillageBhv villageBhv;
    @Autowired
    private VillageDayBhv villageDayBhv;
    @Autowired
    private VillagePlayerBhv villagePlayerBhv;
    @Autowired
    private MessageLogic messageLogic;
    @Autowired
    private TwitterLogic twitterLogic;
    @Autowired
    private AbilityService abilityService;
    @Autowired
    private VoteService voteService;

    // ===================================================================================
    //                                                                             Execute
    //                                                                             =======
    // 初日、2日目以外の日付更新処理
    public void dayChange(Integer villageId, int day, VillagePlayers vPlayers, VillageSettings settings) {
        // 能力行使内容取得
        Abilities abilities = abilityService.selectAbilities(villageId);
        Votes votes = voteService.selectVotes(villageId);
        DayChangeVillage dayChangeVillage = new DayChangeVillage(villageId, day, vPlayers, abilities, votes, settings);

        // 突然死
        suddenlyDeathLogic.killNoVotePlayer(dayChangeVillage);

        // 罠、爆弾設置メッセージ
        trapLogic.insertTrapMessages(dayChangeVillage);
        bombLogic.insertBombMessages(dayChangeVillage);

        // 処刑
        executeLogic.execute(dayChangeVillage);

        // 護衛
        guardLogic.guard(dayChangeVillage);

        // 占い、呪殺、逆呪殺
        divineLogic.divine(dayChangeVillage);

        // 捜査
        investigateLogic.invastigate(dayChangeVillage);

        // 霊能
        psychicLogic.psychic(dayChangeVillage);

        // 同棲メッセージ
        cohabitLogic.cohabit(dayChangeVillage);

        // 襲撃
        attackLogic.attack(dayChangeVillage);

        // 罠
        trapLogic.trap(dayChangeVillage);

        // 爆弾
        bombLogic.bomb(dayChangeVillage);

        // 無惨メッセージ
        miserableLogic.insertAttackedMessage(dayChangeVillage);

        // 後追い
        suicideLogic.suicide(dayChangeVillage);

        // 検死
        autopsyLogic.autopsy(dayChangeVillage);

        if (day == 2 && !dayChangeVillage.deadPlayers.filterAttacked().list.isEmpty()) {
            helper.insertMessage(dayChangeVillage, CDef.MessageType.公開システムメッセージ,
                    messageSource.getMessage("village.start.message.day2", null, Locale.JAPAN));
        }

        // 勝敗判定、エピローグ処理
        Optional<CDef.Camp> optWinCamp = toEpilogueIfNeeded(villageId, day);

        if (optWinCamp.isPresent()) {
            return;
        }

        // パン屋メッセージ
        bakeryLogic.insertBakeryMessageIfNeeded(dayChangeVillage);

        // 投票、能力行使のデフォルト設定
        defaultSetLogic.setDefaultVoteAndAbility(villageId, day);
    }

    // 勝敗判定、エピローグ処理
    private Optional<Camp> toEpilogueIfNeeded(Integer villageId, int day) {
        ListResultBean<VillagePlayer> villagePlayerList = helper.selectVillagePlayerList(villageId);

        // 人狼の数
        List<VillagePlayer> alivePlayerList = villagePlayerList.stream().filter(vp -> vp.isIsDeadFalse()).collect(Collectors.toList());
        long werewolfCount = alivePlayerList.stream().filter(vp -> vp.getSkillCodeAsSkill().isHasAttackAbility()).count();
        // 人間の数
        long villagerCount = alivePlayerList.stream()
                .filter(vp -> !vp.getSkillCodeAsSkill().isHasAttackAbility() && vp.getSkillCodeAsSkill() != CDef.Skill.妖狐)
                .count();
        // 妖狐の数
        long foxCount = alivePlayerList.stream().filter(vp -> vp.getSkillCodeAsSkill() == CDef.Skill.妖狐).count();
        // 恋人の数
        long loversCount = alivePlayerList.stream().filter(vp -> {
            CDef.Skill skill = vp.getSkillCodeAsSkill();
            return skill == CDef.Skill.恋人 || skill == CDef.Skill.同棲者;
        }).count();

        Optional<Camp> optWinCamp = getWinCamp(werewolfCount, villagerCount, foxCount, loversCount);
        optWinCamp.ifPresent(winCamp -> epilogueVillage(villageId, day, villagePlayerList, winCamp, werewolfCount));
        return optWinCamp;
    }

    // 勝利陣営
    private Optional<CDef.Camp> getWinCamp(long wolfCount, long villagerCount, long foxCount, long loversCount) {
        if (!isSettled(wolfCount, villagerCount)) {
            return Optional.empty();
        }
        CDef.Camp winCamp = null;
        if (loversCount > 0) {
            winCamp = CDef.Camp.恋人陣営;
        } else if (foxCount > 0) {
            winCamp = CDef.Camp.狐陣営;
        } else if (wolfCount > 0) {
            winCamp = CDef.Camp.人狼陣営;
        } else {
            winCamp = CDef.Camp.村人陣営;
        }
        return Optional.of(winCamp);
    }

    // 終了条件を満たしているか
    private boolean isSettled(long wolfCount, long villagerCount) {
        return wolfCount >= villagerCount || wolfCount <= 0;
    }

    // エピローグ処理
    private void epilogueVillage(Integer villageId, int day, ListResultBean<VillagePlayer> villagePlayerList, Camp winCamp,
            long werewolfCount) {
        // DB更新
        helper.updateVillageEpilogue(villageId, day, winCamp);
        helper.deadBomberIfNeeded(villageId, day, villagePlayerList);
        ListResultBean<VillagePlayer> vPlayerList = helper.selectVillagePlayerList(villageId);
        helper.updateIsWin(vPlayerList, winCamp);
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
                    player.name(), // 
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
                joiner.add(String.format("%s (%s)、見学参加だった。", player.name(), player.getPlayer().get().getPlayerName()));
            });
        }
        messageLogic.insertMessageIgnoreError(villageId, day, CDef.MessageType.公開システムメッセージ, joiner.toString());
    }

    // エピローグ遷移メッセージ
    private String getEpilogueMessage(Camp winCamp, long werewolfCount) {
        if (winCamp == CDef.Camp.恋人陣営) {
            return messageSource.getMessage("village.epilogue.lovers.win.message", null, Locale.JAPAN);
        } else if (winCamp == CDef.Camp.狐陣営 && werewolfCount <= 0) {
            return messageSource.getMessage("village.epilogue.fox-villager.win.message", null, Locale.JAPAN);
        } else if (winCamp == CDef.Camp.狐陣営 && werewolfCount > 0) {
            return messageSource.getMessage("village.epilogue.fox-werewolf.win.message", null, Locale.JAPAN);
        } else if (winCamp == CDef.Camp.村人陣営) {
            return messageSource.getMessage("village.epilogue.villager.win.message", null, Locale.JAPAN);
        } else {
            return messageSource.getMessage("village.epilogue.werewolf.win.message", null, Locale.JAPAN);
        }
    }
}
