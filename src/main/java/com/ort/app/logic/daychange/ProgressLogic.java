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
import com.ort.app.logic.ability.CohabitLogic;
import com.ort.app.logic.ability.CourtLogic;
import com.ort.app.logic.ability.DivineLogic;
import com.ort.app.logic.ability.FruitsBasketLogic;
import com.ort.app.logic.ability.GuardLogic;
import com.ort.app.logic.ability.InvestigateLogic;
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
    // ?????????2?????????????????????????????????
    public void dayChange(Integer villageId, int day, VillagePlayers vPlayers, VillageSettings settings) {
        // ????????????????????????
        Abilities abilities = abilityService.selectAbilities(villageId);
        Votes votes = voteService.selectVotes(villageId);
        DayChangeVillage dayChangeVillage = new DayChangeVillage(villageId, day, vPlayers, abilities, votes, settings);

        // ?????????
        suddenlyDeathLogic.killNoVotePlayer(dayChangeVillage);

        // ??????
        courtLogic.court(dayChangeVillage);

        // ??????????????????
        stalkingLogic.stalking(dayChangeVillage);

        // ?????????????????????????????????
        trapLogic.insertTrapMessages(dayChangeVillage);
        bombLogic.insertBombMessages(dayChangeVillage);

        // ??????
        executeLogic.execute(dayChangeVillage);

        // ??????
        guardLogic.guard(dayChangeVillage);

        // ???????????????????????????
        divineLogic.divine(dayChangeVillage);

        // ??????
        investigateLogic.invastigate(dayChangeVillage);

        // ??????
        psychicLogic.psychic(dayChangeVillage);

        // ?????????????????????
        cohabitLogic.cohabit(dayChangeVillage);

        // ??????
        attackLogic.attack(dayChangeVillage);

        // ???
        trapLogic.trap(dayChangeVillage);

        // ??????
        bombLogic.bomb(dayChangeVillage);

        // ?????????????????????
        miserableLogic.insertAttackedMessage(dayChangeVillage);

        // ??????
        autopsyLogic.autopsy(dayChangeVillage);

        // ?????????????????????
        revivalLogic.revivalAbsoluteWolf(dayChangeVillage);

        // ?????????
        suicideLogic.suicide(dayChangeVillage);

        if (day == 2 && !dayChangeVillage.deadPlayers.filterAttacked().list.isEmpty()) {
            messageLogic.saveIgnoreError(MessageEntity.publicSystemBuilder(dayChangeVillage.villageId, dayChangeVillage.day) //
                    .content(messageSource.getMessage("village.start.message.day2", null, Locale.JAPAN))
                    .build());
        }

        // ????????????????????????????????????
        Optional<CDef.Camp> optWinCamp = toEpilogueIfNeeded(villageId, day);

        if (optWinCamp.isPresent()) {
            return;
        }

        // ????????????????????????
        bakeryLogic.insertBakeryMessageIfNeeded(dayChangeVillage);

        // ?????????????????????????????????????????????
        defaultSetLogic.setDefaultVoteAndAbility(villageId, day);

        // ???????????????????????????
        fruitsBasketLogic.fruitBasket(dayChangeVillage);
    }

    // ????????????????????????????????????
    private Optional<Camp> toEpilogueIfNeeded(Integer villageId, int day) {
        Village village = villageService.selectVillage(villageId, false, false);

        // ????????????
        VillagePlayers alivePlayers = village.getVillagePlayers().filterAlive();
        int werewolfCount = alivePlayers.filterBy(vp -> vp.getSkillCodeAsSkill().isHasAttackAbility()).list.size();
        // ????????????
        int villagerCount = alivePlayers.filterBy(vp -> !vp.getSkillCodeAsSkill().isHasAttackAbility()
                && vp.getSkillCodeAsSkill() != CDef.Skill.?????? && vp.getSkillCodeAsSkill() != CDef.Skill.???).list.size();
        // ????????????
        int foxCount = alivePlayers.filterBySkill(CDef.Skill.??????).list.size();
        // ???????????????????????????????????????
        int loversCount = alivePlayers.filterBy(vp -> vp.hasLover()).list.size();

        Optional<Camp> optWinCamp = getWinCamp(werewolfCount, villagerCount, foxCount, loversCount);
        optWinCamp.ifPresent(winCamp -> epilogueVillage(village, day, winCamp, werewolfCount));
        return optWinCamp;
    }

    // ????????????
    private Optional<CDef.Camp> getWinCamp(int wolfCount, int villagerCount, int foxCount, int loversCount) {
        if (!isSettled(wolfCount, villagerCount)) {
            return Optional.empty();
        }
        CDef.Camp winCamp = null;
        if (loversCount > 0) {
            winCamp = CDef.Camp.????????????;
        } else if (foxCount > 0) {
            winCamp = CDef.Camp.?????????;
        } else if (wolfCount > 0) {
            winCamp = CDef.Camp.????????????;
        } else {
            winCamp = CDef.Camp.????????????;
        }
        return Optional.of(winCamp);
    }

    // ????????????????????????????????????
    private boolean isSettled(long wolfCount, long villagerCount) {
        return wolfCount >= villagerCount || wolfCount <= 0;
    }

    // ?????????????????????
    private void epilogueVillage(Village village, int day, Camp winCamp, long werewolfCount) {
        // DB??????
        Integer villageId = village.getVillageId();
        helper.updateVillageEpilogue(villageId, day, winCamp);
        bombLogic.deadBomberIfNeeded(village, day); // ????????????????????????????????????????????????
        List<VillagePlayer> vPlayerList = village.getVillagePlayers().list;
        helper.updateIsWin(vPlayerList, winCamp);
        // ??????????????????????????????????????????
        String message = getEpilogueMessage(winCamp, werewolfCount);
        messageLogic.saveIgnoreError(MessageEntity.publicSystemBuilder(villageId, day) //
                .content(message)
                .build());
        // ????????????????????????????????????
        insertPlayerListMessage(villageId, day, vPlayerList);
        // ??????????????????24??????
        updateVillageDay(villageId, day);
        // tweet
        Village vil = villageBhv.selectEntityWithDeletedCheck(cb -> {
            cb.query().setVillageId_Equal(villageId);
            cb.setupSelect_VillageSettingsAsOne();
        });
        if (StringUtils.isEmpty(vil.getVillageSettingsAsOne().get().getJoinPassword())) {
            twitterLogic.tweet(String.format("%s???????????????????????????????????????", vil.getVillageDisplayName()), villageId);
        }
    }

    private void updateVillageDay(Integer villageId, int day) {
        // ????????????????????????1????????????????????????24?????????update
        OptionalEntity<VillageDay> vd = villageDayBhv.selectByPK(villageId, day - 1);
        VillageDay entity = new VillageDay();
        entity.setVillageId(villageId);
        entity.setDay(day);
        entity.setDaychangeDatetime(vd.get().getDaychangeDatetime().plusDays(1));
        villageDayBhv.update(entity);
    }

    // ????????????????????????????????????
    private void insertPlayerListMessage(Integer villageId, int day, List<VillagePlayer> villagePlayerList) {
        StringJoiner joiner = new StringJoiner("\n");
        villagePlayerList.stream().sorted((vp1, vp2) -> vp1.getRoomNumber() - vp2.getRoomNumber()).forEach(player -> {
            joiner.add(String.format("%s (%s)???%s???%s???%s????????????", // 
                    player.name(), // 
                    player.getPlayer().get().getPlayerName(), //
                    player.isIsDeadTrue() ? "??????" : "??????", //
                    player.isIsWinTrue() ? "??????" : "??????", //
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
                joiner.add(String.format("%s (%s)???????????????????????????", player.name(), player.getPlayer().get().getPlayerName()));
            });
        }
        messageLogic.saveIgnoreError(MessageEntity.publicSystemBuilder(villageId, day) //
                .content(joiner.toString())
                .build());
    }

    // ????????????????????????????????????
    private String getEpilogueMessage(Camp winCamp, long werewolfCount) {
        if (winCamp == CDef.Camp.????????????) {
            return messageSource.getMessage("village.epilogue.lovers.win.message", null, Locale.JAPAN);
        } else if (winCamp == CDef.Camp.????????? && werewolfCount <= 0) {
            return messageSource.getMessage("village.epilogue.fox-villager.win.message", null, Locale.JAPAN);
        } else if (winCamp == CDef.Camp.????????? && werewolfCount > 0) {
            return messageSource.getMessage("village.epilogue.fox-werewolf.win.message", null, Locale.JAPAN);
        } else if (winCamp == CDef.Camp.????????????) {
            return messageSource.getMessage("village.epilogue.villager.win.message", null, Locale.JAPAN);
        } else {
            return messageSource.getMessage("village.epilogue.werewolf.win.message", null, Locale.JAPAN);
        }
    }
}
