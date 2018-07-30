package com.ort.app.web.controller.logic;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.dbflute.cbean.result.ListResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ort.app.web.dto.VillageInfo;
import com.ort.app.web.util.SkillUtil;
import com.ort.dbflute.allcommon.CDef;
import com.ort.dbflute.exbhv.AbilityBhv;
import com.ort.dbflute.exbhv.CharaBhv;
import com.ort.dbflute.exbhv.FootstepBhv;
import com.ort.dbflute.exbhv.PlayerBhv;
import com.ort.dbflute.exbhv.VillagePlayerBhv;
import com.ort.dbflute.exentity.Ability;
import com.ort.dbflute.exentity.Chara;
import com.ort.dbflute.exentity.Footstep;
import com.ort.dbflute.exentity.Village;
import com.ort.dbflute.exentity.VillageDay;
import com.ort.dbflute.exentity.VillagePlayer;

/**
 * 村の初期表示の業務ロジック
 */
@Component
public class VillageDispLogic {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    @Autowired
    private PlayerBhv playerBhv;

    @Autowired
    private VillagePlayerBhv villagePlayerBhv;

    @Autowired
    private CharaBhv charaBhv;

    @Autowired
    private AbilityBhv abilityBhv;

    @Autowired
    private FootstepBhv footstepBhv;

    // ===================================================================================
    //                                                                             Execute
    //                                                                             =======
    // 参戦フォームを表示するか
    public boolean isDispParticipateForm(VillageInfo villageInfo) {
        // ログインしていない場合は表示しない
        if (villageInfo.user == null) {
            return false;
        }
        // 現在プロローグでない場合表示しない
        if (!villageInfo.village.isVillageStatusCode募集中() && !villageInfo.village.isVillageStatusCode開始待ち()) {
            return false;
        }
        // 決着のついていない村に参戦している場合表示しない
        int participateCount = playerBhv.selectCount(cb -> {
            cb.query().setPlayerName_Equal(villageInfo.user.getUsername());
            // 募集中、開始待ち、進行中の村に参戦している
            cb.query().existsVillagePlayer(villagePlayerCB -> {
                villagePlayerCB.query().queryVillage().setVillageStatusCode_InScope_AsVillageStatus(
                        Arrays.asList(CDef.VillageStatus.募集中, CDef.VillageStatus.進行中, CDef.VillageStatus.開始待ち));
                villagePlayerCB.query().setIsGone_Equal_False();
            });
        });
        if (participateCount > 0) {
            return false;
        }
        // 既に最大人数まで参加していたら表示しない
        int maxCharaNum = charaBhv.selectCount(cb -> cb.query().setCharaGroupId_Equal(villageInfo.settings.getCharacterGroupId()));
        int participateNum = villagePlayerBhv.selectCount(cb -> {
            cb.query().setVillageId_Equal(villageInfo.villageId);
            cb.query().setIsGone_Equal_False();
        });
        if (participateNum >= maxCharaNum) {
            return false;
        }
        // 突然死したことがある人は表示しない
        int suddonlyDeathCount = selectSuddonlyDeathCount(villageInfo.user.getUsername());
        if (suddonlyDeathCount > 0) {
            return false;
        }
        return true;
    }

    // 希望役職不可メッセージを表示するか
    public boolean isDispChangeRequestSkillNgMessage(VillageInfo villageInfo) {
        // 現在プロローグでない場合表示しない
        if (!villageInfo.village.isVillageStatusCode募集中() && !villageInfo.village.isVillageStatusCode開始待ち()) {
            return false;
        }
        // この村に参戦していない場合は表示しない
        if (!villageInfo.isParticipate() || villageInfo.isSpectator()) {
            return false;
        }
        // 役職希望有効でない場合に表示する
        if (!BooleanUtils.isTrue(villageInfo.settings.getIsPossibleSkillRequest())) {
            return true;
        }
        return false;
    }

    // 希望役職変更フォームを表示するか
    public boolean isDispChangeRequestSkillForm(VillageInfo villageInfo) {
        // 現在プロローグでない場合表示しない
        if (!villageInfo.village.isVillageStatusCode募集中() && !villageInfo.village.isVillageStatusCode開始待ち()) {
            return false;
        }
        // この村に参戦していない場合は表示しない
        if (!villageInfo.isParticipate() || villageInfo.isSpectator()) {
            return false;
        }
        // 役職希望有効でない場合は表示しない
        if (!BooleanUtils.isTrue(villageInfo.settings.getIsPossibleSkillRequest())) {
            return false;
        }
        return true;
    }

    // 退村フォームを表示するか
    public boolean isDispLeaveVillageForm(VillageInfo villageInfo) {
        // 現在プロローグでない場合表示しない
        if (!villageInfo.village.isVillageStatusCode募集中() && !villageInfo.village.isVillageStatusCode開始待ち()) {
            return false;
        }
        // この村に参戦していない場合は表示しない
        if (!villageInfo.isParticipate()) {
            return false;
        }
        return true;
    }

    // コミット可能か
    public Boolean isDispCommitForm(VillageInfo villageInfo) {
        // コミットできない設定の場合は表示しない
        if (villageInfo.settings.isIsAvailableCommitFalse()) {
            return false;
        }
        // 参加していなかったり生存していない場合は表示しない
        if (!villageInfo.isParticipate() || villageInfo.isSpectator() || villageInfo.isDead()) {
            return false;
        }
        // 進行中以外は表示しない
        if (!villageInfo.village.isVillageStatusCode進行中()) {
            return false;
        }
        return true;
    }

    // 発言フォームを表示するか
    public boolean isDispSayForm(VillageInfo villageInfo) {
        // ログインしていない場合は表示しない
        if (villageInfo.user == null) {
            return false;
        }
        // この村に参戦していない場合は表示しない
        if (!villageInfo.isParticipate()) {
            return false;
        }
        // 最新日以外は表示しない
        if (!villageInfo.isLatestDay()) {
            return false;
        }
        // 突然死した場合はエピローグ以外表示しない
        if (villageInfo.optVillagePlayer.get().getDeadReasonCodeAsDeadReason() == CDef.DeadReason.突然
                && !villageInfo.village.isVillageStatusCodeエピローグ()) {
            return false;
        }
        return true;
    }

    // 最新日か
    public boolean isLatestDay(int day, ListResultBean<VillageDay> dayList) {
        return dayList.get(dayList.size() - 1).getDay().equals(day);
    }

    // 通常発言可能か
    public boolean isAvailableNormalSay(Village village, VillagePlayer villagePlayer) {
        // エピローグ以外で死亡している場合は不可
        if (BooleanUtils.isTrue(villagePlayer.getIsDead()) && !village.isVillageStatusCodeエピローグ()) {
            return false;
        }
        // 見学は不可
        if (villagePlayer.isIsSpectatorTrue()) {
            return false;
        }
        return true;
    }

    // 囁き可能か
    public boolean isAvailableWerewolfSay(Village village, VillagePlayer villagePlayer) {
        // 狼とC狂以外は不可
        CDef.Skill skill = villagePlayer.getSkillCodeAsSkill();
        if (skill != CDef.Skill.人狼 && skill != CDef.Skill.C国狂人) {
            return false;
        }
        // 死亡していたら不可
        if (BooleanUtils.isTrue(villagePlayer.getIsDead())) {
            return false;
        }
        // 進行中以外は不可
        if (!village.isVillageStatusCode進行中()) {
            return false;
        }
        return true;
    }

    // 共鳴発言可能か
    public boolean isAvailableMasonSay(Village village, VillagePlayer villagePlayer) {
        // 共鳴以外は不可
        CDef.Skill skill = villagePlayer.getSkillCodeAsSkill();
        if (skill != CDef.Skill.共鳴者) {
            return false;
        }
        // 死亡していたら不可
        if (BooleanUtils.isTrue(villagePlayer.getIsDead())) {
            return false;
        }
        // 進行中以外は不可
        if (!village.isVillageStatusCode進行中()) {
            return false;
        }
        return true;
    }

    // 墓下発言可能か
    public boolean isAvailableGraveSay(Village village, VillagePlayer villagePlayer) {
        // 死亡していなかったら不可
        if (!BooleanUtils.isTrue(villagePlayer.getIsDead())) {
            return false;
        }
        // 進行中以外は不可
        if (!village.isVillageStatusCode進行中()) {
            return false;
        }
        return true;
    }

    // 見学発言可能か
    public boolean isAvailableSpectateSay(Village village, VillagePlayer villagePlayer) {
        if (BooleanUtils.isTrue(villagePlayer.isIsSpectatorFalse())) {
            return false;
        }
        return true;
    }

    // 独り言可能か
    public boolean isAvailableMonologueSay(Village village) {
        // いつでも可能
        return true;
    }

    // 役職と状況に応じて能力行使対象を抽出
    public List<Chara> makeAbilityTargetList(VillageInfo villageInfo) {
        if (!villageInfo.isParticipate() || villageInfo.isDead() || !villageInfo.isLatestDay()
                || !villageInfo.village.isVillageStatusCode進行中() || villageInfo.isSpectator()) {
            return null;
        }
        VillagePlayer vPlayer = villageInfo.optVillagePlayer.get();
        CDef.Skill skill = vPlayer.getSkillCodeAsSkill();
        if (skill == CDef.Skill.人狼) {
            if (villageInfo.day == 1) {
                // ダミーキャラ固定
                return villageInfo.vPlayerList.stream()
                        .filter(vp -> vp.getChara().get().isIsDummyTrue())
                        .map(vp -> vp.getChara().get())
                        .collect(Collectors.toList());
            } else {
                // 狼以外の生存しているプレイヤー全員
                return villageInfo.getVPList(true, true, false)
                        .stream()
                        .filter(vp -> vp.getSkillCodeAsSkill() != CDef.Skill.人狼)
                        .map(vp -> vp.getChara().get())
                        .collect(Collectors.toList());
            }
        } else if (SkillUtil.hasDivineAbility(skill)) {
            // 自分以外の生存しているプレイヤー全員
            return villageInfo.getVPList(true, true, false)
                    .stream()
                    .filter(vp -> vp.isIsDeadFalse() && vp.isIsSpectatorFalse()
                            && !vp.getVillagePlayerId().equals(vPlayer.getVillagePlayerId()))
                    .map(vp -> vp.getChara().get())
                    .collect(Collectors.toList());
        } else if (skill == CDef.Skill.狩人 && villageInfo.day > 1) {
            // 自分以外の生存しているプレイヤー全員
            List<Chara> livingCharaList = villageInfo.getVPList(true, true, false)
                    .stream()
                    .filter(vp -> vp.isIsDeadFalse() && vp.isIsSpectatorFalse()
                            && !vp.getVillagePlayerId().equals(vPlayer.getVillagePlayerId()))
                    .map(vp -> vp.getChara().get())
                    .collect(Collectors.toList());
            if (BooleanUtils.isTrue(villageInfo.settings.getIsAvailableGuardSameTarget())) {
                // 連ガ可
                return livingCharaList;
            } else { // 連ガ不可
                // 昨日の護衛先
                Integer yesterdayGuardTargetId = abilityBhv.selectEntity(cb -> {
                    cb.query().setVillageId_Equal(villageInfo.villageId);
                    cb.query().setDay_Equal(villageInfo.day - 1);
                    cb.query().setAbilityTypeCode_Equal_護衛();
                }).map(ab -> ab.getTargetCharaId()).orElse(null);
                // 生存者のうち、自分と昨日の護衛先を除いた人
                return livingCharaList.stream().filter(c -> !c.getCharaId().equals(yesterdayGuardTargetId)).collect(Collectors.toList());
            }
        }
        return null;
    }

    // 襲撃担当の狼リストを作成
    public List<Chara> makeAttackerList(VillageInfo villageInfo) {
        if (!villageInfo.isParticipate() || villageInfo.isDead() || !villageInfo.isLatestDay()
                || !villageInfo.village.isVillageStatusCode進行中()) {
            return null;
        }
        VillagePlayer vPlayer = villageInfo.optVillagePlayer.get();
        CDef.Skill skill = vPlayer.getSkillCodeAsSkill();
        if (skill != CDef.Skill.人狼) {
            return null;
        }
        // 生存している狼リスト
        List<Chara> liveAttackerList = villageInfo.getVPList(true, true, false)
                .stream()
                .filter(vp -> vp.getSkillCodeAsSkill() == CDef.Skill.人狼)
                .map(vp -> vp.getChara().get())
                .collect(Collectors.toList());
        if (BooleanUtils.isTrue(villageInfo.settings.getIsAvailableSameWolfAttack())) {
            // 連続襲撃可能
            return liveAttackerList;
        }
        // 連続襲撃不可
        // 昨日襲撃した狼
        Integer yesterdayAttackerId = abilityBhv.selectEntity(cb -> {
            cb.query().setVillageId_Equal(villageInfo.villageId);
            cb.query().setDay_Equal(villageInfo.day - 1);
            cb.query().setAbilityTypeCode_Equal_襲撃();
        }).map(ab -> ab.getCharaId()).orElse(null);
        // 生存している狼が1名ではない場合は、昨日襲撃した狼は襲撃できない
        if (liveAttackerList.size() > 1) {
            return liveAttackerList.stream().filter(attacker -> !attacker.getCharaId().equals(yesterdayAttackerId)).collect(
                    Collectors.toList());
        } else {
            return liveAttackerList;
        }
    }

    // 投票対象リスト作成
    public List<Chara> makeVoteTargetList(VillageInfo villageInfo) {
        if (!villageInfo.isParticipate() || villageInfo.isDead() || villageInfo.isSpectator() || !villageInfo.isLatestDay()
                || !villageInfo.village.isVillageStatusCode進行中() || villageInfo.day == 1) {
            return null;
        }
        return villageInfo.getVPList(true, true, false).stream().map(vp -> vp.getChara().get()).collect(Collectors.toList());
    }

    // 能力行使履歴リスト作成
    public List<String> makeSkillHistoryList(VillageInfo villageInfo) {
        if (!villageInfo.isParticipate() || villageInfo.isDead() || !villageInfo.isLatestDay()
                || !villageInfo.village.isVillageStatusCode進行中() || villageInfo.isSpectator()) {
            return null;
        }
        VillagePlayer vPlayer = villageInfo.optVillagePlayer.get();
        CDef.Skill skill = vPlayer.getSkillCodeAsSkill();

        if (skill == CDef.Skill.人狼) {
            ListResultBean<Ability> abilityList = selectAbilityList(villageInfo, CDef.AbilityType.襲撃);
            List<Integer> wolfCharaIdList = villageInfo.getVPList(false, true, true)
                    .stream()
                    .filter(vp -> vp.isSkillCode人狼())
                    .map(VillagePlayer::getCharaId)
                    .collect(Collectors.toList());
            ListResultBean<Footstep> footstepList = selectFootstepList(villageInfo, wolfCharaIdList);
            return abilityList.stream().map(ability -> {
                int day = ability.getDay();
                Optional<Footstep> optFootstep = footstepList.stream().filter(f -> f.getDay().intValue() == day).findFirst();
                return String.format("%d日目 %sが%sを襲撃する（%s）", day, ability.getCharaByCharaId().get().getCharaName(),
                        ability.getCharaByTargetCharaId().get().getCharaName(),
                        optFootstep.map(fs -> fs.getFootstepRoomNumbers()).orElse("なし"));
            }).collect(Collectors.toList());
        } else if (SkillUtil.hasDivineAbility(skill)) {
            ListResultBean<Ability> abilityList = selectAbilityList(villageInfo, CDef.AbilityType.占い);
            ListResultBean<Footstep> footstepList = selectFootstepList(villageInfo, Arrays.asList(vPlayer.getCharaId()));
            return abilityList.stream().map(ability -> {
                int day = ability.getDay();
                Optional<Footstep> optFootstep = footstepList.stream().filter(f -> f.getDay().intValue() == day).findFirst();
                return String.format("%d日目 %sを占う（%s）", day, ability.getCharaByTargetCharaId().get().getCharaName(),
                        optFootstep.map(fs -> fs.getFootstepRoomNumbers()).orElse("なし"));
            }).collect(Collectors.toList());
        } else if (skill == CDef.Skill.狩人) {
            ListResultBean<Ability> abilityList = selectAbilityList(villageInfo, CDef.AbilityType.護衛);
            ListResultBean<Footstep> footstepList = selectFootstepList(villageInfo, Arrays.asList(vPlayer.getCharaId()));
            return abilityList.stream().map(ability -> {
                int day = ability.getDay();
                Optional<Footstep> optFootstep = footstepList.stream().filter(f -> f.getDay().intValue() == day).findFirst();
                return String.format("%d日目 %sを護衛する（%s）", day, ability.getCharaByTargetCharaId().get().getCharaName(),
                        optFootstep.map(fs -> fs.getFootstepRoomNumbers()).orElse("なし"));
            }).collect(Collectors.toList());
        } else if (SkillUtil.hasMadmanAbility(skill) || skill == CDef.Skill.妖狐) {
            ListResultBean<Footstep> footstepList = selectFootstepList(villageInfo, Arrays.asList(vPlayer.getCharaId()));
            return footstepList.stream().map(f -> {
                String footstep = f.getFootstepRoomNumbers();
                return String.format("%d日目 %s", f.getDay(),
                        StringUtils.isEmpty(footstep) || "なし".equals(footstep) ? "徘徊しない" : footstep + "を徘徊する");
            }).collect(Collectors.toList());
        }
        return null;
    }

    public List<Chara> makeSecretSayTargetList(VillageInfo villageInfo) {
        CDef.AllowedSecretSay allowedSecretSay = villageInfo.settings.getAllowedSecretSayCodeAsAllowedSecretSay();
        if (!villageInfo.isParticipate() || !villageInfo.isLatestDay() || !villageInfo.village.isVillageStatusCode進行中()
                || allowedSecretSay == CDef.AllowedSecretSay.なし) {
            return null;
        }
        if (allowedSecretSay == CDef.AllowedSecretSay.全員) {
            // 自分以外
            return villageInfo.vPlayerList.stream()
                    .filter(vp -> !vp.getVillagePlayerId().equals(villageInfo.optVillagePlayer.get().getVillagePlayerId()))
                    .map(vp -> vp.getChara().get())
                    .collect(Collectors.toList());
        }
        return null;
    }

    // ===================================================================================
    //                                                                              Select
    //                                                                              ======
    private ListResultBean<Ability> selectAbilityList(VillageInfo villageInfo, CDef.AbilityType abilityType) {
        return abilityBhv.selectList(cb -> {
            cb.setupSelect_CharaByCharaId();
            cb.setupSelect_CharaByTargetCharaId();
            cb.query().setVillageId_Equal(villageInfo.villageId);
            cb.query().setDay_LessThan(villageInfo.getLatestDay());
            cb.query().setAbilityTypeCode_Equal_AsAbilityType(abilityType);
            cb.query().addOrderBy_Day_Asc();
        });
    }

    private ListResultBean<Footstep> selectFootstepList(VillageInfo villageInfo, List<Integer> charaIdList) {
        ListResultBean<Footstep> footstepList = footstepBhv.selectList(cb -> {
            cb.query().setVillageId_Equal(villageInfo.villageId);
            cb.query().setDay_LessThan(villageInfo.getLatestDay());
            cb.query().setCharaId_InScope(charaIdList);
        });
        return footstepList;
    }

    private int selectSuddonlyDeathCount(String playerName) {
        return villagePlayerBhv.selectCount(cb -> {
            cb.query().queryPlayer().setPlayerName_Equal(playerName);
            cb.query().setDeadReasonCode_Equal_突然();
        });
    }
}
