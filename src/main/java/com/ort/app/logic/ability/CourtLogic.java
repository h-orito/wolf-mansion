package com.ort.app.logic.ability;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import com.ort.app.datasource.AbilityService;
import com.ort.app.datasource.VillageService;
import com.ort.app.logic.MessageLogic;
import com.ort.app.logic.daychange.DayChangeLogicHelper;
import com.ort.app.logic.daychange.DayChangeVillage;
import com.ort.dbflute.allcommon.CDef;
import com.ort.dbflute.exentity.Ability;
import com.ort.dbflute.exentity.Village;
import com.ort.dbflute.exentity.VillagePlayer;
import com.ort.dbflute.exentity.VillagePlayers;

@Component
public class CourtLogic {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    @Autowired
    private DayChangeLogicHelper helper;
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private MessageLogic messageLogic;
    @Autowired
    private VillageService villageService;
    @Autowired
    private AbilityService abilityService;

    // ===================================================================================
    //                                                                             Execute
    //                                                                             =======
    // 求愛
    public void court(DayChangeVillage dayChangeVillage) {
        int day = dayChangeVillage.day;
        if (day != 2) {
            return;
        }

        List<VillagePlayer> livingCourtshipList = dayChangeVillage //
                .alivePlayers() // 死亡していない
                .filterBySkill(CDef.Skill.求愛者).list;
        if (CollectionUtils.isEmpty(livingCourtshipList)) {
            return; // 求愛者が既に死亡している場合は何もしない
        }

        livingCourtshipList.forEach(courtship -> {
            Optional<Ability> optCourt = dayChangeVillage.abilities //
                    .filterYesterday(day) //
                    .filterByType(CDef.AbilityType.求愛) //
                    .filterByChara(courtship.getCharaId()).list.stream().findFirst();
            if (!optCourt.isPresent()) {
                return; // 能力セットしていない場合は何もしない
            }
            VillagePlayer targetPlayer = dayChangeVillage.vPlayers.findByCharaId(optCourt.get().getTargetCharaId());
            // 恋絆
            villageService.insertVillagePlayerStatus(courtship, targetPlayer, CDef.VillagePlayerStatusType.後追い);
            villageService.insertVillagePlayerStatus(targetPlayer, courtship, CDef.VillagePlayerStatusType.後追い);
            // メッセージ
            String courtMessage = String.format("%sは、%sに求愛した。", courtship.name(), targetPlayer.name());
            helper.insertMessage(dayChangeVillage, CDef.MessageType.恋人メッセージ, courtMessage, courtship.getVillagePlayerId());
            String courtedMessage = String.format("あなたは、%sに求愛された。", courtship.name());
            helper.insertMessage(dayChangeVillage, CDef.MessageType.恋人メッセージ, courtedMessage, targetPlayer.getVillagePlayerId());
        });
    }

    // 日付変更時のデフォルトセット
    public void insertDefaultCourt(Village village, int newDay) {
        if (newDay != 1) {
            return;
        }
        Integer villageId = village.getVillageId();
        // 生存している求愛者
        VillagePlayers aliveCourtships = village.getVillagePlayers() //
                .filterAlive() //
                .filterBy(vp -> vp.getSkillCodeAsSkill() == CDef.Skill.求愛者);
        if (aliveCourtships.list.isEmpty()) {
            return;
        }
        aliveCourtships.list.forEach(hunter -> {
            Integer courtshipCharaId = hunter.getCharaId();
            // 求愛される人(生存者の中の誰か）
            Integer targetCharaId = getSelectableTarget(village, newDay, hunter) //
                    .getRandom()
                    .getCharaId();
            abilityService.insertAbility(villageId, newDay, courtshipCharaId, targetCharaId, null, CDef.AbilityType.求愛);
            insertAbilityMessage(village, newDay, courtshipCharaId, targetCharaId, true);
        });
    }

    // 能力セット
    public void setAbility(//
            Village village, //
            VillagePlayer villagePlayer, //
            int day, //
            Integer targetCharaId //
    ) {
        if (isInvalidAbility(village, villagePlayer, day, targetCharaId)) {
            return;
        }
        Integer villageId = village.getVillageId();
        Integer charaId = villagePlayer.getCharaId();
        abilityService.updateAbility(villageId, day, charaId, targetCharaId, CDef.AbilityType.求愛);
        insertAbilityMessage(village, day, charaId, targetCharaId, false);
    }

    public VillagePlayers getSelectableTarget(Village village, int day, VillagePlayer courtship) {
        if (day != 1) {
            return null;
        }
        // 自分以外の生存している人
        return village.getVillagePlayers() //
                .filterAlive() //
                .filterNotDummy(village.getVillageSettingsAsOne().get().getDummyCharaId())
                .filterNotSpecatate()
                .filterNot(courtship)
                .sortedByRoomNumber();
    }

    // ===================================================================================
    //                                                                        Assist Logic
    //                                                                        ============
    private boolean isInvalidAbility(Village village, VillagePlayer villagePlayer, int day, Integer targetCharaId) {
        if (targetCharaId == null) {
            return true;
        }
        if (villagePlayer.isIsDeadTrue()) {
            return true;
        }
        if (getSelectableTarget(village, day, villagePlayer).list.stream().noneMatch(vp -> vp.getCharaId().equals(targetCharaId))) {
            return true;
        }
        if (day != 1) {
            return true;
        }
        return false;
    }

    private void insertAbilityMessage( //
            Village village, //
            int day, //
            Integer charaId, //
            Integer targetCharaId, //
            boolean isDefault //
    ) {
        VillagePlayer chara = village.getVillagePlayers().findByCharaId(charaId);
        VillagePlayer target = village.getVillagePlayers().findByCharaId(targetCharaId);
        String message = messageSource.getMessage("ability.court.message",
                new String[] { chara.name(), target.name(), isDefault ? "（自動設定）" : "" }, Locale.JAPAN);
        messageLogic.insertAbilityMessage(village.getVillageId(), day, message);
    }
}
