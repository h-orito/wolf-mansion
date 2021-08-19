package com.ort.app.logic.ability;

import com.ort.app.datasource.AbilityService;
import com.ort.app.datasource.FootstepService;
import com.ort.app.datasource.VillageService;
import com.ort.app.logic.FootstepLogic;
import com.ort.app.logic.MessageLogic;
import com.ort.app.logic.RoomLogic;
import com.ort.app.logic.daychange.DayChangeVillage;
import com.ort.app.logic.message.MessageEntity;
import com.ort.dbflute.allcommon.CDef;
import com.ort.dbflute.exbhv.CharaImageBhv;
import com.ort.dbflute.exentity.*;
import org.apache.commons.collections4.CollectionUtils;
import org.dbflute.optional.OptionalEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class AttackLogic {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private FootstepLogic footstepLogic;
    @Autowired
    private RoomLogic roomLogic;
    @Autowired
    private MessageLogic messageLogic;
    @Autowired
    private VillageService villageService;
    @Autowired
    private AbilityService abilityService;
    @Autowired
    private FootstepService footstepService;
    @Autowired
    private CharaImageBhv charaImageBhv;

    // ===================================================================================
    //                                                                             Execute
    //                                                                             =======
    // 襲撃
    public void attack(DayChangeVillage dayChangeVillage) {
        List<VillagePlayer> livingWolfList =
                dayChangeVillage.alivePlayers().filterBy(vp -> vp.getSkillCodeAsSkill().isHasAttackAbility()).list;
        if (CollectionUtils.isEmpty(livingWolfList)) {
            return; // 人狼がいない場合は何もしない
        }

        Optional<Ability> optAttack =
                dayChangeVillage.abilities.filterYesterday(dayChangeVillage.day).filterByType(CDef.AbilityType.襲撃).list.stream()
                        .findFirst();
        if (!optAttack.isPresent()) {
            return; // 能力セットしていない場合は何もしない
        }

        Ability attack = optAttack.get();
        VillagePlayer targetPlayer = dayChangeVillage.vPlayers.findByCharaId(attack.getTargetCharaId());

        // 襲撃メッセージ
        insertAttackMessageIfNeeded(dayChangeVillage, attack, targetPlayer);

        // 襲撃成功したら死亡
        if (!isAttackSuccess(dayChangeVillage, targetPlayer)) {
            return;
        }

        // 同棲者がいる部屋だったら移動元の同棲者も死亡
        if (isCohabitting(dayChangeVillage, targetPlayer)) {
            VillagePlayer lover = targetPlayer.getTargetCohabitor();
            villageService.dead(lover, dayChangeVillage.day, CDef.DeadReason.襲撃);
            dayChangeVillage.deadPlayers.add(lover, CDef.DeadReason.襲撃);
        }

        // 死亡処理
        villageService.dead(targetPlayer, dayChangeVillage.day, CDef.DeadReason.襲撃);
        dayChangeVillage.deadPlayers.add(targetPlayer, CDef.DeadReason.襲撃);

        // 襲撃したのが智狼だったら襲撃対象の役職を表示
        if (isAttackerWiseWolf(dayChangeVillage, attack)) {
            dayChangeVillage.deadPlayers.filterAttacked().getList().forEach(attackedPlayer -> {
                String message = String.format("%sは%sだったようだ。", targetPlayer.name(), attackedPlayer.getSkillCodeAsSkill().alias());
                messageLogic.saveIgnoreError(MessageEntity.systemBuilder(dayChangeVillage.villageId, dayChangeVillage.day) //
                        .messageType(CDef.MessageType.襲撃結果)
                        .content(message)
                        .build());
            });
        }
    }

    // デフォルトの噛み先を設定
    public void insertDefaultAttack(Village village, int newDay) {
        Integer villageId = village.getVillageId();
        // 噛む人（生存している狼で、狼が2名以上の場合は昨日噛んだ人は除外）
        VillagePlayers attackableWolfs = getAttackableWolfs(village, newDay).shuffled();
        if (attackableWolfs.list.isEmpty()) {
            return; // 全滅
        }
        VillagePlayer wolf = attackableWolfs.getRandom();
        // 噛まれる人
        Integer attackedCharaId = getSelectableTarget(village, newDay, wolf).getRandom().getCharaId();
        // 噛む人
        Integer attackCharaId = wolf.getCharaId();
        // 能力セット
        abilityService.insertAbility(villageId, newDay, attackCharaId, attackedCharaId, null, CDef.AbilityType.襲撃);
        // 時計回りの足音セット
        String footStep = footstepLogic.makeClockwiseFootStep(village, attackCharaId, attackedCharaId, village.getVillagePlayerList());
        footstepLogic.insertFootStep(villageId, newDay, attackCharaId, footStep);
        insertAbilityMessage(village, newDay, attackCharaId, attackedCharaId, footStep, true);
    }

    // 能力セット
    public void setAbility( //
                            Village village, //
                            VillagePlayer villagePlayer, //
                            int day, //
                            Integer charaId, //
                            Integer targetCharaId, //
                            String footstep//
    ) {
        if (isInvalidWolfAbility(village, villagePlayer, day, charaId, targetCharaId, footstep)) {
            return;
        }
        Integer villageId = village.getVillageId();
        abilityService.updateAbility(villageId, day, charaId, targetCharaId, CDef.AbilityType.襲撃);
        VillagePlayers wolfPlayers = village.getVillagePlayers().filterBy(vp -> vp.getSkillCodeAsSkill().isHasAttackAbility());
        footstepService.deleteWolfFootstep(villageId, day, charaId, footstep, wolfPlayers);
        footstepService.insertFootstep(villageId, day, charaId, footstep);
        insertAbilityMessage(village, day, charaId, targetCharaId, footstep, false);
    }

    // 能力行使先
    public VillagePlayers getSelectableTarget(Village village, int day, VillagePlayer villagePlayer) {
        VillagePlayers vps = null;
        if (day == 1) {
            // ダミーのみ
            Integer dummyCharaId = village.getVillageSettingsAsOne().get().getDummyCharaId();
            vps = new VillagePlayers(Arrays.asList(village.getVillagePlayers().findByCharaId(dummyCharaId)));
        } else {
            // 狼以外の生存している人
            vps = village.getVillagePlayers() //
                    .filterAlive() //
                    .filterNotSpecatate()
                    .filterBy(vp -> !vp.getSkillCodeAsSkill().isHasAttackAbility())
                    .sortedByRoomNumber();
        }
        Integer roomNumber = villagePlayer.getRoomNumber();
        Integer roomSizeWidth = village.getRoomSizeWidth();
        if (villagePlayer.getSkillCodeAsSkill() == CDef.Skill.飛狼) {
            final List<Integer> availableRoomNumberList = roomLogic.detectHishaRoomNumber(roomNumber, village);
            VillagePlayers targets = vps.filterBy(vp -> availableRoomNumberList.contains(vp.getRoomNumber()));
            if (targets.isEmpty()) {
                // 対象がいない場合は人狼と同じ選択肢になる
                return vps;
            }
            return targets;
        } else if (villagePlayer.getSkillCodeAsSkill() == CDef.Skill.角狼) {
            final List<Integer> availableRoomNumberList = roomLogic.detectKakuRoomNumber(roomNumber, village);
            VillagePlayers targets = vps.filterBy(vp -> availableRoomNumberList.contains(vp.getRoomNumber()));
            if (targets.isEmpty()) {
                // 対象がいない場合は人狼と同じ選択肢になる
                return vps;
            }
            return targets;
        } else {
            return vps;
        }
    }

    public VillagePlayers getAttackableWolfs(Village village, int day) {
        // 生存している狼リスト
        VillagePlayers aliveAttackers = village.getVillagePlayers() //
                .filterAlive() //
                .filterNotSpecatate() //
                .filterBy(vp -> vp.getSkillCodeAsSkill().isHasAttackAbility())
                .sortedByRoomNumber();
        if (village.getVillageSettingsAsOne().get().isIsAvailableSameWolfAttackTrue() || aliveAttackers.list.size() <= 1) {
            return aliveAttackers; // 連続襲撃可能
        }
        // 連続襲撃不可
        // 昨日襲撃した狼を除外して返す
        Integer yesterdayAttackerId = selectYesterdayAttackerId(village.getVillageId(), day);
        return aliveAttackers.filterBy(attacker -> !attacker.getCharaId().equals(yesterdayAttackerId));
    }

    public List<String> makeSkillHistoryList(Village village, int day) {
        // 人狼の過去日の能力
        Abilities abilities = abilityService.selectAbilities(village.getVillageId()) //
                .filterPastDay(day) //
                .filterByType(CDef.AbilityType.襲撃)
                .sortedByDay();
        List<Integer> wolfCharaIdList = village.getVillagePlayers()
                .filterNotSpecatate()
                .filterBy(vp -> vp.getSkillCodeAsSkill().isHasAttackAbility())
                .map(vp -> vp.getCharaId());
        // 襲撃の足音
        Footsteps footsteps = footstepService.selectFootsteps(village.getVillageId()).filterInCharaIdList(wolfCharaIdList);

        return abilities.list.stream().map(ability -> {
            Integer abilityDay = ability.getDay();
            String footstep =
                    footsteps.filterByDay(abilityDay).list.stream().findFirst().map(Footstep::getFootstepRoomNumbers).orElse("なし");
            VillagePlayer myself = village.getVillagePlayers().findByCharaId(ability.getCharaId());
            VillagePlayer target = village.getVillagePlayers().findByCharaId(ability.getTargetCharaId());
            return String.format("%d日目 %s が %s を襲撃する（%s）", abilityDay, myself.name(abilityDay), target.name(abilityDay), footstep);
        }).collect(Collectors.toList());
    }

    // ===================================================================================
    //                                                                        Assist Logic
    //                                                                        ============
    private void insertAttackMessageIfNeeded(DayChangeVillage dayChangeVillage, Ability attack, VillagePlayer targetPlayer) {
        if (dayChangeVillage.day == 2) {
            return;
        }
        List<VillagePlayer> livingWolfList =
                dayChangeVillage.alivePlayers().filterBy(vp -> vp.getSkillCodeAsSkill().isHasAttackAbility()).list;
        Collections.shuffle(livingWolfList);
        String attackMessage = String.format("%s！今日がお前の命日だ！", targetPlayer.getCharaName());
        VillagePlayer attackerPlayer = livingWolfList.get(0);
        boolean hasWerewolfFace = hasWerewolfFace(attackerPlayer);
        messageLogic.saveIgnoreError(new MessageEntity.Builder(dayChangeVillage.villageId, dayChangeVillage.day) //
                .messageType(CDef.MessageType.人狼の囁き)
                .content(attackMessage)
                .villagePlayer(attackerPlayer)
                .faceType(hasWerewolfFace ? CDef.FaceType.囁き : CDef.FaceType.通常)
                .isConvertDisable(true)
                .build());
    }

    private boolean hasWerewolfFace(VillagePlayer attackerPlayer) {
        OptionalEntity<CharaImage> optAttackFace = charaImageBhv.selectEntity(cb -> {
            cb.query().setCharaId_Equal(attackerPlayer.getCharaId());
            cb.query().setFaceTypeCode_Equal_囁き();
        });
        return optAttackFace.isPresent();
    }

    private boolean isAttackSuccess(DayChangeVillage dayChangeVillage, VillagePlayer targetPlayer) {
        if (!dayChangeVillage.isAlive(targetPlayer)) {
            return false; // すでに死亡している
        }
        if (isGuarded(dayChangeVillage, targetPlayer)) {
            return false; // 護衛されている
        }
        if (targetPlayer.getSkillCodeAsSkill().isNoDeadByAttack()) {
            return false; // 襲撃されても死なない役職
        }
        if (isAbsence(dayChangeVillage, targetPlayer)) {
            return false; // 同棲先に移動していて不在
        }
        return true;
    }

    private boolean isGuarded(DayChangeVillage dayChangeVillage, VillagePlayer targetPlayer) {
        return dayChangeVillage.guardedPlayers.list.stream()
                .anyMatch(guardedPlayer -> guardedPlayer.getVillagePlayerId().equals(targetPlayer.getVillagePlayerId()));
    }

    private boolean isAbsence(DayChangeVillage dayChangeVillage, VillagePlayer targetPlayer) {
        return !dayChangeVillage.abilities //
                .filterYesterday(dayChangeVillage.day) //
                .filterByType(CDef.AbilityType.同棲) //
                .filterMylsefNotIn(dayChangeVillage.deadPlayers.getList()) //
                .filterTargetNotIn(dayChangeVillage.deadPlayers.getList()) //
                .filterByChara(targetPlayer.getCharaId()).list.isEmpty();
    }

    private boolean isCohabitting(DayChangeVillage dayChangeVillage, VillagePlayer targetPlayer) {
        return !dayChangeVillage.abilities //
                .filterYesterday(dayChangeVillage.day) //
                .filterByType(CDef.AbilityType.同棲) //
                .filterMylsefNotIn(dayChangeVillage.deadPlayers.getList()) //
                .filterTargetNotIn(dayChangeVillage.deadPlayers.getList()) //
                .filterByTargetChara(targetPlayer.getCharaId()).list.isEmpty();
    }

    private boolean isAttackerWiseWolf(DayChangeVillage dayChangeVillage, Ability attack) {
        VillagePlayer attacker = dayChangeVillage.vPlayers.findByCharaId(attack.getCharaId());
        return attacker.getSkillCodeAsSkill() == CDef.Skill.智狼 && dayChangeVillage.isAlive(attacker);
    }

    private boolean isInvalidWolfAbility(//
                                         Village village, //
                                         VillagePlayer villagePlayer, //
                                         int day, //
                                         Integer charaId, //
                                         Integer targetCharaId, //
                                         String footstep//
    ) {
        if (charaId == null || targetCharaId == null || footstep == null) {
            return true;
        }
        VillagePlayer attacker = village.getVillagePlayers().findByCharaId(charaId);
        if (getSelectableTarget(village, day, attacker).list.stream().noneMatch(vp -> vp.getCharaId().equals(targetCharaId))) {
            return true; // 襲撃できない対象を選んでいる
        }
        // 襲撃者、襲撃対象、足音の整合性がとれていなかったらNG
        List<String> footstepCandidateList =
                footstepLogic.getFootstepCandidateList(village.getVillageId(), villagePlayer, day, charaId, targetCharaId);
        if (!footstepCandidateList.contains(footstep)) {
            return true;
        }

        return false;
    }

    private void insertAbilityMessage( //
                                       Village village, //
                                       int day, //
                                       Integer charaId, //
                                       Integer targetCharaId, //
                                       String footstep, //
                                       boolean isDefault //
    ) {
        VillagePlayer chara = village.getVillagePlayers().findByCharaId(charaId);
        VillagePlayer target = village.getVillagePlayers().findByCharaId(targetCharaId);
        String message = messageSource.getMessage("ability.werewolf.message",
                new String[]{chara.name(), target.name(), footstep, isDefault ? "（自動設定）" : ""}, Locale.JAPAN);
        messageLogic.insertAbilityMessage(village.getVillageId(), day, message);
    }

    public Integer selectYesterdayAttackerId(Integer villageId, int day) {
        return abilityService.selectAbilities(villageId) //
                .filterYesterday(day) //
                .filterByType(CDef.AbilityType.襲撃).list.stream() //
                .findFirst()
                .map(a -> a.getCharaId())
                .orElse(null);
    }
}
