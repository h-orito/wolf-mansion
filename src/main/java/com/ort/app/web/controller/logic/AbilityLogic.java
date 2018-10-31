package com.ort.app.web.controller.logic;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.BooleanUtils;
import org.dbflute.cbean.result.ListResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ort.dbflute.allcommon.CDef;
import com.ort.dbflute.exbhv.AbilityBhv;
import com.ort.dbflute.exbhv.FootstepBhv;
import com.ort.dbflute.exbhv.VillageBhv;
import com.ort.dbflute.exbhv.VillagePlayerBhv;
import com.ort.dbflute.exentity.Ability;
import com.ort.dbflute.exentity.Chara;
import com.ort.dbflute.exentity.Footstep;
import com.ort.dbflute.exentity.Village;
import com.ort.dbflute.exentity.VillagePlayer;

@Component
public class AbilityLogic {

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
    private FootstepLogic footstepLogic;

    @Autowired
    private AbilityBhv abilityBhv;

    @Autowired
    private FootstepBhv footstepBhv;

    // ===================================================================================
    //                                                                             Execute
    //                                                                             =======
    // 能力セット
    public void setAbility(Integer villageId, VillagePlayer villagePlayer, int day, Integer charaId, Integer targetCharaId,
            String footstep) {
        CDef.Skill skill = villagePlayer.getSkillCodeAsSkill();
        Village village = selectVillage(villageId);
        List<VillagePlayer> villagePlayerList = selectVillagePlayerList(villageId);
        switch (skill) {
        case 人狼:
            if (isInvalidWolfAbility(village, villagePlayer, villagePlayerList, day, charaId, targetCharaId, footstep)) {
                return;
            }
            updateAbility(villageId, day, charaId, CDef.AbilityType.襲撃, targetCharaId);
            deleteSkillFootstep(villageId, day, charaId, footstep, CDef.Skill.人狼, villagePlayerList);
            insertFootstep(villageId, day, charaId, footstep);
            messageLogic.insertAbilityMessage(villageId, day, charaId, targetCharaId, villagePlayerList, footstep, false);
            break;
        case 占い師:
        case 賢者:
            if (isInvalidSeerAbility(village, villagePlayer, villagePlayerList, day, villagePlayer.getCharaId(), targetCharaId, footstep)) {
                return;
            }
            updateAbility(villageId, day, villagePlayer.getCharaId(), CDef.AbilityType.占い, targetCharaId);
            deleteFootstep(villageId, day, villagePlayer.getCharaId(), footstep);
            insertFootstep(villageId, day, villagePlayer.getCharaId(), footstep);
            messageLogic.insertAbilityMessage(villageId, day, villagePlayer.getCharaId(), targetCharaId, villagePlayerList, footstep,
                    false);
            break;
        case 狩人:
            if (isInvalidHunterAbility(village, villagePlayer, villagePlayerList, day, villagePlayer.getCharaId(), targetCharaId,
                    footstep)) {
                return;
            }
            updateAbility(villageId, day, villagePlayer.getCharaId(), CDef.AbilityType.護衛, targetCharaId);
            deleteFootstep(villageId, day, villagePlayer.getCharaId(), footstep);
            insertFootstep(villageId, day, villagePlayer.getCharaId(), footstep);
            messageLogic.insertAbilityMessage(villageId, day, villagePlayer.getCharaId(), targetCharaId, villagePlayerList, footstep,
                    false);
            break;
        case 妖狐:
        case 狂人:
        case 魔神官:
        case C国狂人:
        case 狂信者:
            if (isInvalidFoxMadmanAbility(village, footstep)) {
                return;
            }
            deleteFootstep(villageId, day, villagePlayer.getCharaId(), footstep);
            insertFootstep(villageId, day, villagePlayer.getCharaId(), footstep);
            messageLogic.insertFootstepMessage(villageId, day, villagePlayer.getCharaId(), villagePlayerList, footstep, false);
            break;
        default:
            break;
        }
    }

    // ===================================================================================
    //                                                                              Select
    //                                                                              ======
    private ListResultBean<VillagePlayer> selectVillagePlayerList(Integer villageId) {
        return villagePlayerBhv.selectList(cb -> {
            cb.setupSelect_Chara();
            cb.setupSelect_SkillBySkillCode();
            cb.query().setIsGone_Equal_False();
            cb.query().setIsSpectator_Equal_False();
            cb.query().setVillageId_Equal(villageId);
        });
    }

    private Village selectVillage(Integer villageId) {
        return villageBhv.selectEntityWithDeletedCheck(cb -> {
            cb.setupSelect_VillageSettingsAsOne();
            cb.query().setVillageId_Equal(villageId);
        });
    }

    // ===================================================================================
    //                                                                              Update
    //                                                                              ======
    private void updateAbility(Integer villageId, int day, Integer charaId, CDef.AbilityType type, Integer targetCharaId) {
        // delete insertする
        deleteAbility(villageId, day, type);
        insertAbility(villageId, day, charaId, type, targetCharaId);
    }

    private void insertAbility(Integer villageId, int day, Integer charaId, CDef.AbilityType type, Integer targetCharaId) {
        Ability ability = new Ability();
        ability.setVillageId(villageId);
        ability.setDay(day);
        ability.setCharaId(charaId);
        ability.setAbilityTypeCodeAsAbilityType(type);
        ability.setTargetCharaId(targetCharaId);
        abilityBhv.insert(ability);
    }

    private void deleteAbility(Integer villageId, int day, CDef.AbilityType type) {
        abilityBhv.queryDelete(cb -> {
            cb.query().setVillageId_Equal(villageId);
            cb.query().setDay_Equal(day);
            cb.query().setAbilityTypeCode_Equal_AsAbilityType(type);
        });
    }

    private void deleteSkillFootstep(Integer villageId, int day, Integer charaId, String footstep, CDef.Skill skill,
            List<VillagePlayer> villagePlayerList) {
        List<Integer> werewolfCharaIdList =
                villagePlayerList.stream().filter(vp -> vp.getSkillCodeAsSkill() == skill).map(vp -> vp.getCharaId()).collect(
                        Collectors.toList());
        footstepBhv.queryDelete(cb -> {
            cb.query().setVillageId_Equal(villageId);
            cb.query().setDay_Equal(day);
            cb.query().setCharaId_InScope(werewolfCharaIdList);
        });
    }

    private void deleteFootstep(Integer villageId, int day, Integer charaId, String footstep) {
        footstepBhv.queryDelete(cb -> {
            cb.query().setVillageId_Equal(villageId);
            cb.query().setDay_Equal(day);
            cb.query().setCharaId_Equal(charaId);
        });
    }

    private void insertFootstep(Integer villageId, int day, Integer charaId, String footstep) {
        Footstep entity = new Footstep();
        entity.setVillageId(villageId);
        entity.setDay(day);
        entity.setCharaId(charaId);
        entity.setFootstepRoomNumbers(footstep);
        footstepBhv.insert(entity);
    }

    // ===================================================================================
    //                                                          Assist Logic (set ability)
    //                                                                        ============
    private boolean isInvalidWolfAbility(Village village, VillagePlayer villagePlayer, List<VillagePlayer> villagePlayerList, int day,
            Integer charaId, Integer targetCharaId, String footstep) {
        if (village.getVillageSettingsAsOne().get().isIsAvailableSameWolfAttackFalse()) { // 連続襲撃不可
            // 複数人狼が生存していて昨日襲撃した人狼と今日襲撃する人狼が同じならNG
            List<Chara> attackableWolfList = getAttackableWolfList(village.getVillageId(), day, villagePlayerList);
            if (!attackableWolfList.stream().anyMatch(chara -> chara.getCharaId().equals(charaId))) {
                return true;
            }
        }
        // 襲撃先が人狼、もしくは死亡済みならNG
        if (isAttackTargetWerewolfOrDead(villagePlayerList, targetCharaId)) {
            return true;
        }
        // 1日目なのにダミーキャラ以外を襲撃しようとしていたらNG
        if (day == 1 && village.getVillageSettingsAsOne().get().getDummyCharaId().intValue() == targetCharaId.intValue()) {
            return true;
        }
        // 襲撃者、襲撃対象、足音の整合性がとれていなかったらNG
        List<String> footstepCandidateList =
                footstepLogic.getFootstepCandidateList(village.getVillageId(), villagePlayer, day, charaId, targetCharaId);
        if (!footstepCandidateList.contains(footstep)) {
            return true;
        }

        return false;
    }

    // 襲撃先が人狼、もしくは死亡済み
    private boolean isAttackTargetWerewolfOrDead(List<VillagePlayer> villagePlayerList, Integer targetCharaId) {
        VillagePlayer target =
                villagePlayerList.stream().filter(vp -> vp.getCharaId().equals(targetCharaId)).findFirst().orElseThrow(() -> {
                    return new IllegalArgumentException("村にいないキャラ");
                });
        return target.getSkillCodeAsSkill() == CDef.Skill.人狼 || target.isIsDeadTrue();
    }

    // 襲撃可能な人狼を取得
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

    private boolean isInvalidSeerAbility(Village village, VillagePlayer villagePlayer, List<VillagePlayer> villagePlayerList, int day,
            Integer charaId, Integer targetCharaId, String footstep) {
        // 占い先が自分、もしくは死亡済みならNG
        if (isTargetMyselfOrDead(villagePlayerList, villagePlayer, targetCharaId)) {
            return true;
        }
        // 自分、占い対象、足音の整合性がとれていなかったらNG
        List<String> footstepCandidateList =
                footstepLogic.getFootstepCandidateList(village.getVillageId(), villagePlayer, day, charaId, targetCharaId);
        if (!footstepCandidateList.contains(footstep)) {
            return true;
        }

        return false;
    }

    // 対象が自分、もしくは死亡済み
    private boolean isTargetMyselfOrDead(List<VillagePlayer> villagePlayerList, VillagePlayer villagePlayer, Integer targetCharaId) {
        VillagePlayer target =
                villagePlayerList.stream().filter(vp -> vp.getCharaId().equals(targetCharaId)).findFirst().orElseThrow(() -> {
                    return new IllegalArgumentException("村にいないキャラ");
                });
        return target.getCharaId().equals(villagePlayer.getCharaId()) || target.isIsDeadTrue();
    }

    private boolean isInvalidHunterAbility(Village village, VillagePlayer villagePlayer, List<VillagePlayer> villagePlayerList, int day,
            Integer charaId, Integer targetCharaId, String footstep) {
        // 護衛先が自分、もしくは死亡済みならNG
        if (isTargetMyselfOrDead(villagePlayerList, villagePlayer, targetCharaId)) {
            return true;
        }
        // 1日目はセットできない
        if (day == 1) {
            return true;
        }
        // 自分、護衛対象、足音の整合性がとれていなかったらNG
        List<String> footstepCandidateList =
                footstepLogic.getFootstepCandidateList(village.getVillageId(), villagePlayer, day, charaId, targetCharaId);
        if (!footstepCandidateList.contains(footstep)) {
            return true;
        }
        // 連続護衛不可なのに昨日と同じ護衛対象だったらNG
        if (!BooleanUtils.isTrue(village.getVillageSettingsAsOne().get().getIsAvailableGuardSameTarget())) {
            // 昨日の護衛先
            Integer yesterdayGuardTargetId = abilityBhv.selectEntity(cb -> {
                cb.query().setVillageId_Equal(village.getVillageId());
                cb.query().setDay_Equal(day - 1);
                cb.query().setAbilityTypeCode_Equal_護衛();
            }).map(ab -> ab.getTargetCharaId()).orElse(null);
            if (targetCharaId.equals(yesterdayGuardTargetId)) {
                return true;
            }
        }
        return false;
    }

    private boolean isInvalidFoxMadmanAbility(Village village, String footstep) {
        // 足音が直線でなかったらNG
        if (!footstepLogic.isFootstepStraight(village, footstep)) {
            return true;
        }
        return false;
    }
}
