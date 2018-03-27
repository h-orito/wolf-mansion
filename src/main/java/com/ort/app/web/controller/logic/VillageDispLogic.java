package com.ort.app.web.controller.logic;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.BooleanUtils;
import org.dbflute.cbean.result.ListResultBean;
import org.dbflute.optional.OptionalThing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ort.app.web.util.SkillUtil;
import com.ort.dbflute.allcommon.CDef;
import com.ort.dbflute.exbhv.AbilityBhv;
import com.ort.dbflute.exbhv.PlayerBhv;
import com.ort.dbflute.exbhv.VillagePlayerBhv;
import com.ort.dbflute.exentity.Chara;
import com.ort.dbflute.exentity.Village;
import com.ort.dbflute.exentity.VillageDay;
import com.ort.dbflute.exentity.VillagePlayer;
import com.ort.fw.security.UserInfo;

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
    private AbilityBhv abilityBhv;

    // ===================================================================================
    //                                                                             Execute
    //                                                                             =======
    // 参戦フォームを表示するか
    public boolean isDispParticipateForm(int day, UserInfo userInfo, Village village) {
        // ログインしていない場合は表示しない
        if (userInfo == null) {
            return false;
        }
        // 現在プロローグでない場合表示しない
        if (!village.isVillageStatusCode募集中() && !village.isVillageStatusCode開始待ち()) {
            return false;
        }
        // 決着のついていない村に参戦している場合表示しない
        int participateCount = playerBhv.selectCount(cb -> {
            cb.query().setPlayerName_Equal(userInfo.getUsername());
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
        Integer maxPersonNum = village.getVillageSettingsAsOne().get().getPersonMaxNum();
        int participateNum = villagePlayerBhv.selectCount(cb -> {
            cb.query().setVillageId_Equal(village.getVillageId());
            cb.query().setIsGone_Equal_False();
        });

        return participateNum < maxPersonNum;
    }

    // 希望役職変更フォームを表示するか
    public boolean isDispChangeRequestSkillForm(int day, OptionalThing<VillagePlayer> optVillagePlayer, Village village) {
        // 現在プロローグでない場合表示しない
        if (!village.isVillageStatusCode募集中() && !village.isVillageStatusCode開始待ち()) {
            return false;
        }
        // この村に参戦していない場合は表示しない
        if (!optVillagePlayer.isPresent()) {
            return false;
        }
        // 役職希望有効出ない場合は表示しない
        if (!BooleanUtils.isTrue(village.getVillageSettingsAsOne().get().getIsPossibleSkillRequest())) {
            return false;
        }
        return true;
    }

    // 退村フォームを表示するか
    public boolean isDispLeaveVillageForm(int day, OptionalThing<VillagePlayer> optVillagePlayer, Village village) {
        // 現在プロローグでない場合表示しない
        if (!village.isVillageStatusCode募集中() && !village.isVillageStatusCode開始待ち()) {
            return false;
        }
        // この村に参戦していない場合は表示しない
        if (!optVillagePlayer.isPresent()) {
            return false;
        }
        return true;
    }

    // 発言フォームを表示するか
    public boolean isDispSayForm(Integer villageId, Village village, UserInfo userInfo, OptionalThing<VillagePlayer> optVillagePlayer,
            int day, ListResultBean<VillageDay> dayList) {
        // ログインしていない場合は表示しない
        if (userInfo == null) {
            return false;
        }
        // この村に参戦していない場合は表示しない
        if (!optVillagePlayer.isPresent()) {
            return false;
        }
        // 最新日以外は表示しない
        if (!isLatestDay(day, dayList)) {
            return false;
        }
        // 突然死した場合はエピローグ以外表示しない
        if (optVillagePlayer.get().getDeadReasonCodeAsDeadReason() == CDef.DeadReason.突然 && !village.isVillageStatusCodeエピローグ()) {
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

    // 独り言可能か
    public boolean isAvailableMonologueSay(Village village) {
        // 進行中以外は不可
        if (!village.isVillageStatusCode進行中()) {
            return false;
        }
        return true;
    }

    // 役職と状況に応じて能力行使対象を抽出
    public List<Chara> makeAbilityTargetList(Village village, OptionalThing<VillagePlayer> optVillagePlayer, int day, boolean isLatestDay) {
        if (!optVillagePlayer.isPresent() || optVillagePlayer.get().isIsDeadTrue() || !isLatestDay || !village.isVillageStatusCode進行中()) {
            return null;
        }
        VillagePlayer vPlayer = optVillagePlayer.get();
        CDef.Skill skill = vPlayer.getSkillCodeAsSkill();
        if (skill == CDef.Skill.人狼) {
            if (day == 1) {
                // ダミーキャラ固定
                return village.getVillagePlayerList()
                        .stream()
                        .filter(vp -> vp.getChara().get().isIsDummyTrue())
                        .map(vp -> vp.getChara().get())
                        .collect(Collectors.toList());
            } else {
                // 狼以外の生存しているプレイヤー全員
                return village.getVillagePlayerList()
                        .stream()
                        .filter(vp -> vp.isIsDeadFalse() && vp.getSkillCodeAsSkill() != CDef.Skill.人狼)
                        .map(vp -> vp.getChara().get())
                        .collect(Collectors.toList());
            }
        } else if (SkillUtil.hasDivineAbility(skill) || (skill == CDef.Skill.狩人 && day > 1)) {
            // 自分以外の生存しているプレイヤー全員
            return village.getVillagePlayerList()
                    .stream()
                    .filter(vp -> vp.isIsDeadFalse() && !vp.getVillagePlayerId().equals(vPlayer.getVillagePlayerId()))
                    .map(vp -> vp.getChara().get())
                    .collect(Collectors.toList());
        }
        return null;
    }

    // 襲撃担当の狼リストを作成
    public List<Chara> makeAttackerList(Village village, OptionalThing<VillagePlayer> optVillagePlayer, int day, boolean isLatestDay) {
        if (!optVillagePlayer.isPresent() || optVillagePlayer.get().isIsDeadTrue() || !isLatestDay || !village.isVillageStatusCode進行中()) {
            return null;
        }
        VillagePlayer vPlayer = optVillagePlayer.get();
        CDef.Skill skill = vPlayer.getSkillCodeAsSkill();
        if (skill != CDef.Skill.人狼) {
            return null;
        }
        // 昨日襲撃した狼
        Integer yesterdayAttackerId = abilityBhv.selectEntity(cb -> {
            cb.query().setVillageId_Equal(village.getVillageId());
            cb.query().setDay_Equal(day - 1);
            cb.query().setAbilityTypeCode_Equal_襲撃();
        }).map(ab -> ab.getCharaId()).orElse(null);
        // 生存している狼リスト
        List<Chara> liveAttackerList = village.getVillagePlayerList()
                .stream()
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

    // 投票対象リスト作成
    public List<Chara> makeVoteTargetList(Village village, OptionalThing<VillagePlayer> optVillagePlayer, int day, boolean isLatestDay) {
        if (!optVillagePlayer.isPresent() || optVillagePlayer.get().isIsDeadTrue() || !isLatestDay || !village.isVillageStatusCode進行中()
                || day == 1) {
            return null;
        }
        return village.getVillagePlayerList().stream().filter(vp -> vp.isIsDeadFalse()).map(vp -> vp.getChara().get()).collect(
                Collectors.toList());
    }
}
