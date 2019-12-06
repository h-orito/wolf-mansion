package com.ort.app.web.controller.logic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.dbflute.cbean.result.ListResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.ort.app.web.dto.VillageInfo;
import com.ort.app.web.model.OptionDto;
import com.ort.app.web.model.VillageFootstepDto;
import com.ort.app.web.model.VillageSituationDto;
import com.ort.app.web.model.inner.SayRestrictDto;
import com.ort.app.web.util.CharaUtil;
import com.ort.dbflute.allcommon.CDef;
import com.ort.dbflute.exbhv.AbilityBhv;
import com.ort.dbflute.exbhv.CharaBhv;
import com.ort.dbflute.exbhv.FootstepBhv;
import com.ort.dbflute.exbhv.PlayerBhv;
import com.ort.dbflute.exbhv.VillagePlayerBhv;
import com.ort.dbflute.exentity.Ability;
import com.ort.dbflute.exentity.Chara;
import com.ort.dbflute.exentity.Footstep;
import com.ort.dbflute.exentity.Message;
import com.ort.dbflute.exentity.MessageRestriction;
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
    @Autowired
    private FootstepLogic footstepLogic;
    @Autowired
    private MessageLogic messageLogic;

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
        // 突然死により入村制限がかかっている人は表示しない
        if (selectIsRestrictedParticipation(villageInfo.user.getUsername())) {
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
        // 最新日以外は表示しない
        if (!villageInfo.isLatestDay()) {
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
        if (skill == null || !skill.isAvailableWerewolfSay()) {
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
    public List<OptionDto> makeAbilityTargetList(VillageInfo villageInfo) {
        if (!villageInfo.isParticipate() || villageInfo.isDead() || !villageInfo.isLatestDay()
                || !villageInfo.village.isVillageStatusCode進行中() || villageInfo.isSpectator()) {
            return null;
        }
        VillagePlayer vPlayer = villageInfo.optVillagePlayer.get();
        CDef.Skill skill = vPlayer.getSkillCodeAsSkill();
        if (skill.isHasAttackAbility()) {
            return makeWolfAbilityTargetList(villageInfo);
        } else if (skill.isHasDivineAbility()) {
            // 自分以外の生存者全員
            return makeAbilityTargetListWithoutMyself(villageInfo, vPlayer);
        } else if (skill == CDef.Skill.狩人 && villageInfo.day > 1) {
            return makeHunterAbilityTargetList(villageInfo, vPlayer);
        } else if (skill == CDef.Skill.探偵 && villageInfo.day > 1) {
            return makeDetectiveAbilityTargetList(villageInfo);
        } else if (skill == CDef.Skill.罠師) {
            return makeTrapperAbilityTargetList(villageInfo, vPlayer);
        } else if (skill == CDef.Skill.爆弾魔) {
            // 自分以外の生存者全員
            return makeBomberAbilityTargetList(villageInfo, vPlayer);
        }
        return null;
    }

    private List<OptionDto> makeBomberAbilityTargetList(VillageInfo villageInfo, VillagePlayer myself) {
        List<Ability> abilityList = selectAbilityList(villageInfo, CDef.AbilityType.爆弾設置);
        OptionDto option = new OptionDto();
        option.setName("なし");
        option.setValue("");
        if (!abilityList.isEmpty()) {
            // 前日以前に能力行使した
            return Arrays.asList(option);
        }
        // まだ能力行使していない
        // 自分以外の生存者全員＋「なし」
        List<OptionDto> livingPlayerList = makeAbilityTargetListWithoutMyself(villageInfo, myself);
        livingPlayerList.add(0, option);
        return livingPlayerList;
    }

    private List<OptionDto> makeTrapperAbilityTargetList(VillageInfo villageInfo, VillagePlayer myself) {
        List<Ability> abilityList = selectAbilityList(villageInfo, CDef.AbilityType.罠設置);
        OptionDto option = new OptionDto();
        option.setName("なし");
        option.setValue("");
        if (!abilityList.isEmpty()) {
            // 前日以前に能力行使した
            return Arrays.asList(option);
        }
        // まだ能力行使していない
        // 生存者全員＋「なし」
        List<OptionDto> livingPlayerList = villageInfo.getVPList(true, true, false)
                .stream()
                .filter(vp -> vp.isIsDeadFalse() && vp.isIsSpectatorFalse())
                .sorted((vp1, vp2) -> vp1.getRoomNumber() - vp2.getRoomNumber())
                .map(vp -> new OptionDto(vp))
                .collect(Collectors.toList());
        livingPlayerList.add(0, option);
        return livingPlayerList;
    }

    private List<OptionDto> makeDetectiveAbilityTargetList(VillageInfo villageInfo) {
        List<String> footstepList = footstepLogic.getFootstepList(villageInfo.villageId, villageInfo.day);
        if (CollectionUtils.isEmpty(footstepList)) {
            return new ArrayList<>();
        }
        return footstepList.stream().map(fs -> {
            OptionDto option = new OptionDto();
            option.setName(fs);
            option.setValue(fs);
            return option;
        }).collect(Collectors.toList());
    }

    private List<OptionDto> makeHunterAbilityTargetList(VillageInfo villageInfo, VillagePlayer vPlayer) {
        // 自分以外の生存しているプレイヤー全員
        List<OptionDto> livingCharaList = makeAbilityTargetListWithoutMyself(villageInfo, vPlayer);
        if (BooleanUtils.isTrue(villageInfo.settings.getIsAvailableGuardSameTarget())) {
            // 連ガ可
            return livingCharaList;
        } else { // 連ガ不可
            // 昨日の護衛先
            String yesterdayGuardTargetId = abilityBhv.selectEntity(cb -> {
                cb.query().setVillageId_Equal(villageInfo.villageId);
                cb.query().setDay_Equal(villageInfo.day - 1);
                cb.query().setAbilityTypeCode_Equal_護衛();
            }).map(ab -> ab.getTargetCharaId().toString()).orElse(null);
            // 生存者のうち、自分と昨日の護衛先を除いた人
            return livingCharaList.stream().filter(c -> !c.getValue().equals(yesterdayGuardTargetId)).collect(Collectors.toList());
        }
    }

    // 自分以外の生存者全員
    private List<OptionDto> makeAbilityTargetListWithoutMyself(VillageInfo villageInfo, VillagePlayer myself) {
        return villageInfo.getVPList(true, true, false)
                .stream()
                .filter(vp -> vp.isIsDeadFalse() && vp.isIsSpectatorFalse() && !vp.getVillagePlayerId().equals(myself.getVillagePlayerId()))
                .sorted((vp1, vp2) -> vp1.getRoomNumber() - vp2.getRoomNumber())
                .map(vp -> new OptionDto(vp))
                .collect(Collectors.toList());
    }

    private List<OptionDto> makeWolfAbilityTargetList(VillageInfo villageInfo) {
        if (villageInfo.day == 1) {
            // ダミーキャラ固定
            return villageInfo.vPlayerList.stream()
                    .filter(vp -> vp.getCharaId().intValue() == villageInfo.settings.getDummyCharaId())
                    .map(vp -> new OptionDto(vp))
                    .collect(Collectors.toList());
        } else {
            // 狼以外の生存しているプレイヤー全員
            return villageInfo.getVPList(true, true, false)
                    .stream()
                    .filter(vp -> vp.getSkillCodeAsSkill() != null && !vp.getSkillCodeAsSkill().isHasAttackAbility())
                    .sorted((vp1, vp2) -> vp1.getRoomNumber() - vp2.getRoomNumber())
                    .map(vp -> new OptionDto(vp))
                    .collect(Collectors.toList());
        }
    }

    // 襲撃担当の狼リストを作成
    public List<OptionDto> makeAttackerList(VillageInfo villageInfo) {
        if (!villageInfo.isParticipate() || villageInfo.isDead() || !villageInfo.isLatestDay()
                || !villageInfo.village.isVillageStatusCode進行中() || villageInfo.isSpectator()
                || villageInfo.optVillagePlayer.get().isIsGoneTrue()) {
            return null;
        }
        VillagePlayer vPlayer = villageInfo.optVillagePlayer.get();
        CDef.Skill skill = vPlayer.getSkillCodeAsSkill();
        if (!skill.isHasAttackAbility()) {
            return null;
        }
        // 生存している狼リスト
        List<OptionDto> liveAttackerList = villageInfo.getVPList(true, true, false)
                .stream()
                .filter(vp -> vp.getSkillCodeAsSkill().isHasAttackAbility())
                .map(vp -> new OptionDto(vp))
                .collect(Collectors.toList());
        if (BooleanUtils.isTrue(villageInfo.settings.getIsAvailableSameWolfAttack())) {
            // 連続襲撃可能
            return liveAttackerList;
        }
        // 連続襲撃不可
        // 昨日襲撃した狼
        String yesterdayAttackerId = abilityBhv.selectEntity(cb -> {
            cb.query().setVillageId_Equal(villageInfo.villageId);
            cb.query().setDay_Equal(villageInfo.day - 1);
            cb.query().setAbilityTypeCode_Equal_襲撃();
        }).map(ab -> ab.getCharaId().toString()).orElse(null);
        // 生存している狼が1名ではない場合は、昨日襲撃した狼は襲撃できない
        if (liveAttackerList.size() > 1) {
            return liveAttackerList.stream().filter(attacker -> !attacker.getValue().equals(yesterdayAttackerId)).collect(
                    Collectors.toList());
        } else {
            return liveAttackerList;
        }
    }

    // 投票対象リスト作成
    public List<OptionDto> makeVoteTargetList(VillageInfo villageInfo) {
        if (!villageInfo.isParticipate() || villageInfo.isDead() || villageInfo.isSpectator() || !villageInfo.isLatestDay()
                || !villageInfo.village.isVillageStatusCode進行中() || villageInfo.day == 1) {
            return null;
        }
        return villageInfo.getVPList(true, true, false)
                .stream()
                .sorted((vp1, vp2) -> vp1.getRoomNumber() - vp2.getRoomNumber())
                .map(vp -> new OptionDto(vp))
                .collect(Collectors.toList());
    }

    // 能力行使履歴リスト作成
    public List<String> makeSkillHistoryList(VillageInfo villageInfo) {
        if (!villageInfo.isParticipate() || !villageInfo.isLatestDay() || !villageInfo.village.isVillageStatusCode進行中()
                || villageInfo.isSpectator()) {
            return null;
        }
        VillagePlayer vPlayer = villageInfo.optVillagePlayer.get();
        CDef.Skill skill = vPlayer.getSkillCodeAsSkill();

        if (skill.isHasAttackAbility()) {
            ListResultBean<Ability> abilityList = selectAbilityList(villageInfo, CDef.AbilityType.襲撃);
            List<Integer> wolfCharaIdList = villageInfo.getVPList(false, true, true)
                    .stream()
                    .filter(vp -> vp.getSkillCodeAsSkill().isHasAttackAbility())
                    .map(VillagePlayer::getCharaId)
                    .collect(Collectors.toList());
            ListResultBean<Footstep> footstepList = selectFootstepList(villageInfo, wolfCharaIdList);
            return abilityList.stream().map(ability -> {
                int day = ability.getDay();
                Optional<Footstep> optFootstep = footstepList.stream().filter(f -> f.getDay().intValue() == day).findFirst();
                VillagePlayer myself = extractVillagePlayerBy(villageInfo, ability.getCharaId());
                VillagePlayer target = extractVillagePlayerBy(villageInfo, ability.getTargetCharaId());
                return String.format("%d日目 %s が %s を襲撃する（%s）", day, CharaUtil.makeCharaName(myself), CharaUtil.makeCharaName(target),
                        optFootstep.map(fs -> fs.getFootstepRoomNumbers()).orElse("なし"));
            }).collect(Collectors.toList());
        } else if (skill.isHasDivineAbility()) {
            ListResultBean<Ability> abilityList = selectAbilityList(villageInfo, CDef.AbilityType.占い);
            ListResultBean<Footstep> footstepList = selectFootstepList(villageInfo, Arrays.asList(vPlayer.getCharaId()));
            return abilityList.stream().map(ability -> {
                int day = ability.getDay();
                Optional<Footstep> optFootstep = footstepList.stream().filter(f -> f.getDay().intValue() == day).findFirst();
                VillagePlayer target = extractVillagePlayerBy(villageInfo, ability.getTargetCharaId());
                return String.format("%d日目 %s を占う（%s）", day, CharaUtil.makeCharaName(target),
                        optFootstep.map(fs -> fs.getFootstepRoomNumbers()).orElse("なし"));
            }).collect(Collectors.toList());
        } else if (skill == CDef.Skill.狩人) {
            ListResultBean<Ability> abilityList = selectAbilityList(villageInfo, CDef.AbilityType.護衛);
            ListResultBean<Footstep> footstepList = selectFootstepList(villageInfo, Arrays.asList(vPlayer.getCharaId()));
            return abilityList.stream().map(ability -> {
                int day = ability.getDay();
                Optional<Footstep> optFootstep = footstepList.stream().filter(f -> f.getDay().intValue() == day).findFirst();
                VillagePlayer target = extractVillagePlayerBy(villageInfo, ability.getTargetCharaId());
                return String.format("%d日目 %s を護衛する（%s）", day, CharaUtil.makeCharaName(target),
                        optFootstep.map(fs -> fs.getFootstepRoomNumbers()).orElse("なし"));
            }).collect(Collectors.toList());
        } else if (skill.isHasMadmanAbility() || skill == CDef.Skill.妖狐) {
            ListResultBean<Footstep> footstepList = selectFootstepList(villageInfo, Arrays.asList(vPlayer.getCharaId()));
            return footstepList.stream().map(f -> {
                String footstep = f.getFootstepRoomNumbers();
                return String.format("%d日目 %s", f.getDay(),
                        StringUtils.isEmpty(footstep) || "なし".equals(footstep) ? "徘徊しない" : footstep + "を徘徊する");
            }).collect(Collectors.toList());
        } else if (skill == CDef.Skill.探偵) {
            ListResultBean<Ability> abilityList = selectAbilityList(villageInfo, CDef.AbilityType.捜査);
            return abilityList.stream().map(ability -> {
                int day = ability.getDay();
                String footstep = ability.getTargetFootstep();
                return String.format("%d日目 %s を調査する", day, footstep);
            }).collect(Collectors.toList());
        } else if (skill == CDef.Skill.罠師) {
            ListResultBean<Ability> abilityList = selectAbilityList(villageInfo, CDef.AbilityType.罠設置);
            return abilityList.stream().map(ability -> {
                int day = ability.getDay();
                VillagePlayer target = extractVillagePlayerBy(villageInfo, ability.getTargetCharaId());
                return String.format("%d日目 %s の部屋に罠を設置する", day, CharaUtil.makeCharaName(target));
            }).collect(Collectors.toList());
        } else if (skill == CDef.Skill.爆弾魔) {
            ListResultBean<Ability> abilityList = selectAbilityList(villageInfo, CDef.AbilityType.爆弾設置);
            return abilityList.stream().map(ability -> {
                int day = ability.getDay();
                VillagePlayer target = extractVillagePlayerBy(villageInfo, ability.getTargetCharaId());
                return String.format("%d日目 %s の部屋に爆弾を設置する", day, CharaUtil.makeCharaName(target));
            }).collect(Collectors.toList());
        }
        return null;
    }

    private VillagePlayer extractVillagePlayerBy(VillageInfo villageInfo, Integer charaId) {
        return villageInfo.getVPList(false, false, false).stream().filter(vp -> vp.getCharaId().equals(charaId)).findFirst().orElse(null);
    }

    public List<Chara> makeSecretSayTargetList(VillageInfo villageInfo) {
        // 自分以外
        return villageInfo.vPlayerList.stream()
                .filter(vp -> !vp.getVillagePlayerId().equals(villageInfo.optVillagePlayer.get().getVillagePlayerId()))
                .map(vp -> vp.getChara().get())
                .collect(Collectors.toList());
    }

    public String makeWerewolfCharaNameList(VillageInfo villageInfo) {
        if (!villageInfo.isParticipate() || villageInfo.isSpectator() || villageInfo.isDead() || !villageInfo.isLatestDay()
                || !villageInfo.village.isVillageStatusCode進行中()) {
            return null;
        }
        CDef.Skill skill = villageInfo.optVillagePlayer.get().getSkillCodeAsSkill();
        if (skill != CDef.Skill.C国狂人 && skill != CDef.Skill.狂信者 && !skill.isHasAttackAbility()) {
            return null;
        }
        return String.join("、",
                villageInfo.getVPList(false, true, true)
                        .stream()
                        .filter(vp -> vp.getSkillCodeAsSkill().isHasAttackAbility())
                        .sorted((vp1, vp2) -> vp1.getRoomNumber() - vp2.getRoomNumber())
                        .map(vp -> CharaUtil.makeCharaName(vp))
                        .collect(Collectors.toList()));
    }

    public String makeCMadmanCharaNameList(VillageInfo villageInfo) {
        if (!villageInfo.isParticipate() || villageInfo.isSpectator() || villageInfo.isDead() || !villageInfo.isLatestDay()
                || !villageInfo.village.isVillageStatusCode進行中()) {
            return null;
        }
        CDef.Skill skill = villageInfo.optVillagePlayer.get().getSkillCodeAsSkill();
        if (skill != CDef.Skill.C国狂人 && !skill.isHasAttackAbility()) {
            return null;
        }
        return String.join("、",
                villageInfo.getVPList(false, true, true)
                        .stream()
                        .filter(vp -> vp.getSkillBySkillCode().get().isSkillCodeC国狂人())
                        .sorted((vp1, vp2) -> vp1.getRoomNumber() - vp2.getRoomNumber())
                        .map(vp -> CharaUtil.makeCharaName(vp))
                        .collect(Collectors.toList()));
    }

    public List<VillageFootstepDto> makeFootstepList(VillageInfo villageInfo) {
        List<VillageFootstepDto> footstepList = new ArrayList<>();
        for (int i = 2; i < villageInfo.dayList.size(); i++) {
            int day = i;
            List<Integer> livingPlayerRoomNumList = villageInfo.vPlayerList.stream()
                    .filter(vp -> vp.isIsSpectatorFalse() && (vp.isIsDeadFalse() || day < vp.getDeadDay()))
                    .map(VillagePlayer::getRoomNumber)
                    .collect(Collectors.toList());
            String message;
            List<Footstep> dayFootstepList =
                    villageInfo.footstepList.stream().filter(f -> f.getDay() == day - 1).collect(Collectors.toList());
            if (isDispSpoilerContent(villageInfo)) {
                message = footstepLogic.makeFootstepMessageOpenSkill(livingPlayerRoomNumList, villageInfo.getVPList(false, true, true),
                        dayFootstepList);
            } else {
                message = footstepLogic.makeFootstepMessageWithoutHeader(livingPlayerRoomNumList, dayFootstepList);
            }
            VillageFootstepDto footstep = new VillageFootstepDto();
            footstep.setDay(day);
            footstep.setFootstep(message);
            footstepList.add(footstep);
        }
        return footstepList;
    }

    // ネタバレ要素を表示するか
    public boolean isDispSpoilerContent(VillageInfo villageInfo) {
        // エピっていたら表示
        if (villageInfo.village.isVillageStatusCodeエピローグ() || villageInfo.village.isVillageStatusCode終了()) {
            return true;
        }
        // 墓下役職公開で墓下か見物だったら表示
        if (villageInfo.settings.isIsOpenSkillInGraveTrue() && (villageInfo.isDead() || villageInfo.isSpectator())) {
            return true;
        }

        return false;
    }

    public List<VillageSituationDto> makeSituationList(VillageInfo villageInfo) {
        // 2dから現在の日付まで表示する
        List<VillageSituationDto> situationList = new ArrayList<>();
        List<VillagePlayer> vpList = villageInfo.getVPList(false, true, false);
        for (int i = 2; i <= villageInfo.day; i++) {
            final int day = i;
            VillageSituationDto situation = makeSituation(vpList, day);
            // エピったり墓下公開状態の場合は追加表示
            if (isDispSpoilerContent(villageInfo)) {
                setSituationDetail(villageInfo, vpList, day, situation);
            }
            situationList.add(situation);
        }
        return situationList;
    }

    public SayRestrictDto makeRestrict(VillageInfo villageInfo) {
        SayRestrictDto restrict = new SayRestrictDto();
        if (!villageInfo.isParticipate() || villageInfo.isSpectator() || villageInfo.isDead() || !villageInfo.isLatestDay()
                || !villageInfo.village.isVillageStatusCode進行中()) {
            return restrict;
        }
        VillagePlayer vPlayer = villageInfo.optVillagePlayer.get();
        if ("master".equals(vPlayer.getPlayer().get().getPlayerName())) {
            return restrict; // masterは制限なし
        }
        CDef.Skill skill = vPlayer.getSkillCodeAsSkill();
        // 制限
        List<MessageRestriction> restrictList = villageInfo.village.getMessageRestrictionList();
        restrictList.stream().filter(res -> res.getSkillCodeAsSkill() == skill).forEach(res -> {
            if (res.isMessageTypeCode通常発言()) {
                restrict.setNormalCount(res.getMessageMaxNum());
                restrict.setNormalLength(res.getMessageMaxLength());
            } else if (res.isMessageTypeCode人狼の囁き()) {
                restrict.setWhisperCount(res.getMessageMaxNum());
                restrict.setWhisperLength(res.getMessageMaxLength());
            } else if (res.isMessageTypeCode共鳴発言()) {
                restrict.setMasonCount(res.getMessageMaxNum());
                restrict.setMasonLength(res.getMessageMaxLength());
            }
        });
        // 現在の発言数から残り発言数を計算
        Integer normalCount = restrict.getNormalCount();
        Integer whisperCount = restrict.getWhisperCount();
        Integer masonCount = restrict.getMasonCount();
        if (normalCount != null || whisperCount != null || masonCount != null) {
            List<Message> messageList =
                    messageLogic.selectDayPersonMessage(villageInfo.villageId, villageInfo.day, vPlayer.getVillagePlayerId());
            if (normalCount != null) {
                int sayCount = (int) messageList.stream().filter(m -> m.isMessageTypeCode通常発言()).count();
                restrict.setNormalLeftCount(normalCount - sayCount);
            }
            if (whisperCount != null) {
                int sayCount = (int) messageList.stream().filter(m -> m.isMessageTypeCode人狼の囁き()).count();
                restrict.setWhisperLeftCount(whisperCount - sayCount);
            }
            if (masonCount != null) {
                int sayCount = (int) messageList.stream().filter(m -> m.isMessageTypeCode共鳴発言()).count();
                restrict.setMasonLeftCount(masonCount - sayCount);
            }
        }
        return restrict;
    }

    public String makeTargetPrefixMessage(VillageInfo villageInfo) {
        if (!villageInfo.isParticipate() || villageInfo.isDead() || !villageInfo.isLatestDay()
                || !villageInfo.village.isVillageStatusCode進行中() || villageInfo.isSpectator()) {
            return null;
        }
        CDef.Skill skill = villageInfo.optVillagePlayer.get().getSkillCodeAsSkill();
        if (skill.isHasAttackAbility()) {
            return "襲撃対象";
        } else if (skill == CDef.Skill.罠師) {
            return "罠を設置する部屋";
        } else if (skill == CDef.Skill.爆弾魔) {
            return "爆弾を設置する部屋";
        }
        return null;
    }

    public String makeTargetSuffixMessage(VillageInfo villageInfo) {
        if (!villageInfo.isParticipate() || villageInfo.isDead() || !villageInfo.isLatestDay()
                || !villageInfo.village.isVillageStatusCode進行中() || villageInfo.isSpectator()) {
            return null;
        }
        CDef.Skill skill = villageInfo.optVillagePlayer.get().getSkillCodeAsSkill();
        if (skill.isHasDivineAbility()) {
            return "を占う";
        } else if (skill == CDef.Skill.狩人 && villageInfo.day > 1) {
            return "を護衛する";
        }
        return null;
    }

    public Boolean isTargetingAndFootstep(VillageInfo villageInfo) {
        if (!villageInfo.isParticipate() || villageInfo.isDead() || !villageInfo.isLatestDay()
                || !villageInfo.village.isVillageStatusCode進行中() || villageInfo.isSpectator()) {
            return false;
        }
        CDef.Skill skill = villageInfo.optVillagePlayer.get().getSkillCodeAsSkill();
        return skill.isHasDivineAbility() || skill.isHasAttackAbility() || skill == CDef.Skill.狩人;
    }

    private void setSituationDetail(VillageInfo villageInfo, List<VillagePlayer> vpList, final int day, VillageSituationDto situation) {
        List<Ability> abilityList = villageInfo.abilityList.stream().filter(a -> a.getDay() == day - 1).collect(Collectors.toList());

        List<String> divineList = abilityList.stream().filter(a -> a.isAbilityTypeCode占い()).map(ability -> {
            String seerCharaName = filterAndMakeCharaName(vpList, vp -> vp.getCharaId().equals(ability.getCharaId())).get(0);
            String divinedCharaName = filterAndMakeCharaName(vpList, vp -> vp.getCharaId().equals(ability.getTargetCharaId())).get(0);
            return seerCharaName + " → " + divinedCharaName;
        }).collect(Collectors.toList());
        situation.setDivinedChara(String.join("\n", divineList));

        List<String> guardList = abilityList.stream().filter(a -> a.isAbilityTypeCode護衛()).map(ability -> {
            String hunterCharaName = filterAndMakeCharaName(vpList, vp -> vp.getCharaId().equals(ability.getCharaId())).get(0);
            String guardedCharaName = filterAndMakeCharaName(vpList, vp -> vp.getCharaId().equals(ability.getTargetCharaId())).get(0);
            return hunterCharaName + " → " + guardedCharaName;
        }).collect(Collectors.toList());
        situation.setGuardedChara(String.join("\n", guardList));

        Optional<Ability> optAttack = abilityList.stream().filter(a -> a.isAbilityTypeCode襲撃()).findFirst();
        if (optAttack.isPresent()) {
            Integer attackCharaId = optAttack.get().getCharaId();
            String attacker =
                    CharaUtil.makeCharaShortName(vpList.stream().filter(vp -> vp.getCharaId().equals(attackCharaId)).findFirst().get());
            Integer attackedCharaId = optAttack.get().getTargetCharaId();
            String attacked =
                    CharaUtil.makeCharaShortName(vpList.stream().filter(vp -> vp.getCharaId().equals(attackedCharaId)).findFirst().get());
            situation.setAttack(attacker + " → " + attacked);
        }

        List<String> investigateList = abilityList.stream().filter(a -> a.isAbilityTypeCode捜査()).map(ability -> {
            String detectiveCharaName = filterAndMakeCharaName(vpList, vp -> vp.getCharaId().equals(ability.getCharaId())).get(0);
            String targetFootstep = ability.getTargetFootstep();
            return detectiveCharaName + " → " + targetFootstep;
        }).collect(Collectors.toList());
        situation.setInvestigation(String.join("\n", investigateList));
    }

    private VillageSituationDto makeSituation(List<VillagePlayer> vpList, final int day) {
        VillageSituationDto situation = new VillageSituationDto();
        situation.setDay(day);
        List<VillagePlayer> deadPlayer =
                vpList.stream().filter(vp -> vp.getDeadDay() != null && vp.getDeadDay() == day).collect(Collectors.toList());
        List<String> suddonlyDeathPlayer = filterAndMakeCharaName(deadPlayer, vp -> vp.isDeadReasonCode突然());
        situation.setSuddonlyDeathChara(shuffleAndJoin(suddonlyDeathPlayer));
        List<String> attackedPlayer = filterAndMakeCharaName(deadPlayer, vp -> {
            return vp.isDeadReasonCode呪殺() || vp.isDeadReasonCode襲撃() || vp.isDeadReasonCode爆死() || vp.isDeadReasonCode罠死();
        });
        situation.setAttackedChara(shuffleAndJoin(attackedPlayer));
        List<String> executedPlayer = filterAndMakeCharaName(deadPlayer, vp -> vp.isDeadReasonCode処刑());
        situation.setExecutedChara(shuffleAndJoin(executedPlayer));
        return situation;
    }

    private List<String> filterAndMakeCharaName(List<VillagePlayer> vpList, Predicate<? super VillagePlayer> predicate) {
        return vpList.stream().filter(predicate).map(vp -> CharaUtil.makeCharaShortName(vp)).collect(Collectors.toList());
    }

    private String shuffleAndJoin(List<String> charaList) {
        if (CollectionUtils.isEmpty(charaList)) {
            return "なし";
        }
        Collections.shuffle(charaList);
        return String.join("、", charaList);
    }

    // ===================================================================================
    //                                                                              Select
    //                                                                              ======
    private ListResultBean<Ability> selectAbilityList(VillageInfo villageInfo, CDef.AbilityType abilityType) {
        return abilityBhv.selectList(cb -> {
            cb.setupSelect_CharaByCharaId();
            cb.setupSelect_CharaByTargetCharaId();
            cb.query().setVillageId_Equal(villageInfo.villageId);
            // 人狼系は他の人がセットしたものも見られる
            if (abilityType != CDef.AbilityType.襲撃) {
                cb.query().setCharaId_Equal(villageInfo.optVillagePlayer.get().getCharaId());
            }
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

    private boolean selectIsRestrictedParticipation(String playerName) {
        return playerBhv.selectEntity(cb -> {
            cb.query().setPlayerName_Equal(playerName);
            cb.query().setIsRestrictedParticipation_Equal_True();
        }).isPresent();
    }
}
