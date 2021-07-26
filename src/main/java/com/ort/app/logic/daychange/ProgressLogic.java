package com.ort.app.logic.daychange;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.StringJoiner;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.dbflute.cbean.result.ListResultBean;
import org.dbflute.optional.OptionalEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import com.ort.app.datasource.AbilityService;
import com.ort.app.datasource.VillageService;
import com.ort.app.datasource.VoteService;
import com.ort.app.logic.MessageLogic;
import com.ort.app.logic.TwitterLogic;
import com.ort.app.logic.ability.AttackLogic;
import com.ort.app.logic.ability.AutopsyLogic;
import com.ort.app.logic.ability.BakeryLogic;
import com.ort.app.logic.ability.BombLogic;
import com.ort.app.logic.ability.CheatLogic;
import com.ort.app.logic.ability.CohabitLogic;
import com.ort.app.logic.ability.CourtLogic;
import com.ort.app.logic.ability.DivineLogic;
import com.ort.app.logic.ability.FalseChargesLogic;
import com.ort.app.logic.ability.FruitsBasketLogic;
import com.ort.app.logic.ability.GuardLogic;
import com.ort.app.logic.ability.InvestigateLogic;
import com.ort.app.logic.ability.LoneAttackLogic;
import com.ort.app.logic.ability.PsychicLogic;
import com.ort.app.logic.ability.StalkingLogic;
import com.ort.app.logic.ability.SuicideLogic;
import com.ort.app.logic.ability.TrapLogic;
import com.ort.app.logic.daychange.ability.RevivalLogic;
import com.ort.app.logic.message.MessageEntity;
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
    private LoneAttackLogic loneAttackLogic;
    @Autowired
    private MiserableLogic miserableLogic;
    @Autowired
    private SuicideLogic suicideLogic;
    @Autowired
    private AutopsyLogic autopsyLogic;
    @Autowired
    private BakeryLogic bakeryLogic;
    @Autowired
    private FruitsBasketLogic fruitsBasketLogic;
    @Autowired
    private RevivalLogic revivalLogic;
    @Autowired
    private CourtLogic courtLogic;
    @Autowired
    private StalkingLogic stalkingLogic;
    @Autowired
    private CheatLogic cheatLogic;
    @Autowired
    private FalseChargesLogic falseChargesLogic;
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
    @Autowired
    private VillageService villageService;

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

        // 誑かす
        cheatLogic.cheat(dayChangeVillage);

        // 求愛
        courtLogic.court(dayChangeVillage);

        // ストーキング
        stalkingLogic.stalking(dayChangeVillage);

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

        // 単独襲撃
        loneAttackLogic.loneAttack(dayChangeVillage);

        // 罠
        trapLogic.trap(dayChangeVillage);

        // 爆弾
        bombLogic.bomb(dayChangeVillage);

        // 無惨メッセージ
        miserableLogic.insertAttackedMessage(dayChangeVillage);

        // 検死
        autopsyLogic.autopsy(dayChangeVillage);

        // 絶対人狼の復活
        revivalLogic.revivalAbsoluteWolf(dayChangeVillage);

        // 後追い
        suicideLogic.suicide(dayChangeVillage);

        if (day == 2 && !dayChangeVillage.deadPlayers.filterAttacked().list.isEmpty()) {
            messageLogic.saveIgnoreError(MessageEntity.publicSystemBuilder(dayChangeVillage.villageId, dayChangeVillage.day) //
                    .content(messageSource.getMessage("village.start.message.day2", null, Locale.JAPAN))
                    .build());
        }

        // 勝敗判定、エピローグ処理
        Optional<CDef.Camp> optWinCamp = toEpilogueIfNeeded(villageId, day);

        if (optWinCamp.isPresent()) {
            return;
        }

        // パン屋メッセージ
        bakeryLogic.insertBakeryMessageIfNeeded(dayChangeVillage);

        // 冤罪者
        falseChargesLogic.falseCharges(dayChangeVillage);

        // 投票、能力行使のデフォルト設定
        defaultSetLogic.setDefaultVoteAndAbility(villageId, day);

        // フルーツバスケット
        fruitsBasketLogic.fruitBasket(dayChangeVillage);
    }

    // 勝敗判定、エピローグ処理
    private Optional<Camp> toEpilogueIfNeeded(Integer villageId, int day) {
        Village village = villageService.selectVillage(villageId, false, false);

        // 人狼の数
        VillagePlayers alivePlayers = village.getVillagePlayers().filterAlive();
        int werewolfCount = alivePlayers.filterBy(vp -> vp.getSkillCodeAsSkill().isWolfCount()).list.size();
        // 人間の数
        int villagerCount =
                alivePlayers.filterBy(vp -> !vp.getSkillCodeAsSkill().isWolfCount() && !vp.getSkillCodeAsSkill().isNoCount()).list.size();
        // 妖狐の数
        int foxCount =
                alivePlayers.filterBy(vp -> vp.getSkillCodeAsSkill() == CDef.Skill.妖狐 || vp.getSkillCodeAsSkill() == CDef.Skill.誑狐).list
                        .size();
        // 恋絆が付与されている人の数
        int loversCount = alivePlayers.filterBy(vp -> vp.hasLover()).list.size();

        Optional<Camp> optWinCamp = getWinCamp(werewolfCount, villagerCount, foxCount, loversCount);
        optWinCamp.ifPresent(winCamp -> epilogueVillage(village, day, winCamp, werewolfCount));
        return optWinCamp;
    }

    // 勝利陣営
    private Optional<CDef.Camp> getWinCamp(int wolfCount, int villagerCount, int foxCount, int loversCount) {
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
    private void epilogueVillage(Village village, int day, Camp winCamp, long werewolfCount) {
        // DB更新
        Integer villageId = village.getVillageId();
        helper.updateVillageEpilogue(villageId, day, winCamp);
        bombLogic.deadBomberIfNeeded(village, day); // 爆弾魔が設置していなかったら自爆
        List<VillagePlayer> vPlayerList = village.getVillagePlayers().list;
        helper.updateIsWin(vPlayerList, winCamp);
        // エピローグ遷移メッセージ登録
        String message = getEpilogueMessage(winCamp, werewolfCount);
        messageLogic.saveIgnoreError(MessageEntity.publicSystemBuilder(villageId, day) //
                .content(message)
                .build());
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
    private void insertPlayerListMessage(Integer villageId, int day, List<VillagePlayer> villagePlayerList) {
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
        messageLogic.saveIgnoreError(MessageEntity.publicSystemBuilder(villageId, day) //
                .content(joiner.toString())
                .build());
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
