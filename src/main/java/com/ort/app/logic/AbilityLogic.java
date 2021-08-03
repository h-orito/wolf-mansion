package com.ort.app.logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.StringJoiner;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ort.app.logic.ability.AttackLogic;
import com.ort.app.logic.ability.BadgerGameLogic;
import com.ort.app.logic.ability.BombLogic;
import com.ort.app.logic.ability.CheatLogic;
import com.ort.app.logic.ability.CohabitLogic;
import com.ort.app.logic.ability.CommandLogic;
import com.ort.app.logic.ability.CourtLogic;
import com.ort.app.logic.ability.DisturbLogic;
import com.ort.app.logic.ability.DivineLogic;
import com.ort.app.logic.ability.FruitsBasketLogic;
import com.ort.app.logic.ability.GuardLogic;
import com.ort.app.logic.ability.InvestigateLogic;
import com.ort.app.logic.ability.LoneAttackLogic;
import com.ort.app.logic.ability.SeduceLogic;
import com.ort.app.logic.ability.StalkingLogic;
import com.ort.app.logic.ability.TrapLogic;
import com.ort.app.logic.ability.WallPunchLogic;
import com.ort.app.web.dto.VillageInfo;
import com.ort.app.web.model.OptionDto;
import com.ort.dbflute.allcommon.CDef;
import com.ort.dbflute.exentity.Village;
import com.ort.dbflute.exentity.VillagePlayer;
import com.ort.dbflute.exentity.VillagePlayers;

@Component
public class AbilityLogic {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    @Autowired
    private AttackLogic attackLogic;
    @Autowired
    private LoneAttackLogic loneAttackLogic;
    @Autowired
    private DivineLogic divineLogic;
    @Autowired
    private GuardLogic guardLogic;
    @Autowired
    private WallPunchLogic wallPunchLogic;
    @Autowired
    private DisturbLogic disturbLogic;
    @Autowired
    private InvestigateLogic investigateLogic;
    @Autowired
    private TrapLogic trapLogic;
    @Autowired
    private BombLogic bombLogic;
    @Autowired
    private CohabitLogic cohabitLogic;
    @Autowired
    private CommandLogic commandLogic;
    @Autowired
    private FruitsBasketLogic fruitsBasketLogic;
    @Autowired
    private CourtLogic courtLogic;
    @Autowired
    private StalkingLogic stalkingLogic;
    @Autowired
    private CheatLogic cheatLogic;
    @Autowired
    private SeduceLogic seduceLogic;
    @Autowired
    private BadgerGameLogic badgerGameLogic;

    // ===================================================================================
    //                                                                             Execute
    //                                                                             =======
    // 能力セット
    public void setAbility(Village village, VillagePlayer villagePlayer, int day, Integer charaId, Integer targetCharaId, String footstep) {
        CDef.Skill skill = villagePlayer.getSkillCodeAsSkill();
        if (skill == null) {
            return;
        }
        AbilityType type = detectAbilityType(villagePlayer.getSkillCodeAsSkill());

        switch (type) {
        case ATTACK:
            attackLogic.setAbility(village, villagePlayer, day, charaId, targetCharaId, footstep);
            break;
        case DIVINE:
            divineLogic.setAbility(village, villagePlayer, day, targetCharaId, footstep);
            break;
        case GUARD:
            guardLogic.setAbility(village, villagePlayer, day, targetCharaId, footstep);
            break;
        case WALLPUNCH:
            wallPunchLogic.setAbility(village, villagePlayer, day, targetCharaId);
            break;
        case DISTURB:
            disturbLogic.setAbility(village, villagePlayer, day, footstep);
            break;
        case INVESTIGATE:
            investigateLogic.setAbility(village, villagePlayer, day, footstep);
            break;
        case TRAP:
            trapLogic.setAbility(village, villagePlayer, day, targetCharaId);
            break;
        case BOMB:
            bombLogic.setAbility(village, villagePlayer, day, targetCharaId);
            break;
        case COHABIT:
            cohabitLogic.setAbility(village, villagePlayer, day, targetCharaId);
            break;
        case COMMAND:
            commandLogic.setAbility(village, villagePlayer, day, targetCharaId);
            break;
        case FRUITSBASKET:
            fruitsBasketLogic.setAbility(village, villagePlayer, day, targetCharaId);
            break;
        case COURT:
            courtLogic.setAbility(village, villagePlayer, day, targetCharaId);
            break;
        case STALKING:
            stalkingLogic.setAbility(village, villagePlayer, day, targetCharaId);
            break;
        case CHEAT:
            cheatLogic.setAbility(village, villagePlayer, day, targetCharaId);
            break;
        case LONEATTACK:
            loneAttackLogic.setAbility(village, villagePlayer, day, targetCharaId, footstep);
            break;
        case SEDUCE:
            seduceLogic.setAbility(village, villagePlayer, day, targetCharaId);
            break;
        case BADGERGAME:
            badgerGameLogic.setAbility(village, villagePlayer, day, targetCharaId);
            break;
        default:
            return;
        }
    }

    // 能力行使対象
    public List<OptionDto> getSelectableTargetList(VillageInfo villageInfo) {
        Village village = villageInfo.village;
        int day = villageInfo.day;

        if (!isAbilityUsable(village, villageInfo.optVillagePlayer, day)) {
            // 能力を使用できない
            return new ArrayList<>();
        }

        VillagePlayer villagePlayer = villageInfo.optVillagePlayer.get();
        AbilityType type = detectAbilityType(villagePlayer.getSkillCodeAsSkill());
        VillagePlayers selectablePlayers = null;
        switch (type) {
        case ATTACK:
            selectablePlayers = attackLogic.getSelectableTarget(village, day);
            break;
        case DIVINE:
            selectablePlayers = divineLogic.getSelectableTarget(village, villagePlayer);
            break;
        case GUARD:
            selectablePlayers = guardLogic.getSelectableTarget(village, day, villagePlayer);
            break;
        case WALLPUNCH:
            selectablePlayers = wallPunchLogic.getSelectableTarget(village, day, villagePlayer);
            break;
        case DISTURB:
            return null;
        case INVESTIGATE:
            List<String> footstepList = investigateLogic.getSelectableTarget(village, day);
            // 対象がプレイヤーでないのでここで変換して返す
            return footstepList.stream().map(fs -> {
                OptionDto option = new OptionDto();
                option.setName(fs);
                option.setValue(fs);
                return option;
            }).collect(Collectors.toList());
        case TRAP:
            // 「なし」も選べるのでここで返す
            List<OptionDto> list = trapLogic.getSelectableTarget(village, villagePlayer, day).map(vp -> new OptionDto(vp));
            list.add(0, new OptionDto("なし", ""));
            return list;
        case BOMB:
            // 「なし」も選べるのでここで返す
            List<OptionDto> lists = bombLogic.getSelectableTarget(village, villagePlayer, day).map(vp -> new OptionDto(vp));
            lists.add(0, new OptionDto("なし", ""));
            return lists;
        case COHABIT:
            selectablePlayers = cohabitLogic.getSelectableTarget(villagePlayer);
            break;
        case COMMAND:
            // 「なし」も選べるのでここで返す
            List<OptionDto> l = commandLogic.getSelectableTarget(village, villagePlayer, day).map(vp -> new OptionDto(vp));
            l.add(0, new OptionDto("なし", ""));
            return l;
        case FRUITSBASKET:
            // 「なし」も選べるのでここで返す
            List<OptionDto> lists2 = fruitsBasketLogic.getSelectableTarget(village, villagePlayer, day).map(vp -> new OptionDto(vp));
            lists2.add(0, new OptionDto("なし", ""));
            return lists2;
        case COURT:
            selectablePlayers = courtLogic.getSelectableTarget(village, day, villagePlayer);
            break;
        case STALKING:
            selectablePlayers = stalkingLogic.getSelectableTarget(village, day, villagePlayer);
            break;
        case CHEAT:
            // 「なし」も選べるのでここで返す
            List<OptionDto> cheatList = cheatLogic.getSelectableTarget(village, day, villagePlayer).map(vp -> new OptionDto(vp));
            cheatList.add(0, new OptionDto("なし", ""));
            return cheatList;
        case LONEATTACK:
            // 「なし」も選べるのでここで返す
            List<OptionDto> list3 = loneAttackLogic.getSelectableTarget(village, villagePlayer).map(vp -> new OptionDto(vp));
            list3.add(0, new OptionDto("なし", ""));
            return list3;
        case SEDUCE:
            // 「なし」も選べるのでここで返す
            List<OptionDto> seduceList = seduceLogic.getSelectableTarget(village, day, villagePlayer).map(vp -> new OptionDto(vp));
            seduceList.add(0, new OptionDto("なし", ""));
            return seduceList;
        case BADGERGAME:
            // 「なし」も選べるのでここで返す
            List<OptionDto> badgergameList = badgerGameLogic.getSelectableTarget(village, day, villagePlayer).map(vp -> new OptionDto(vp));
            badgergameList.add(0, new OptionDto("なし", ""));
            return badgergameList;
        default:
            return null;
        }

        if (selectablePlayers == null) {
            return null;
        }
        return selectablePlayers.map(vp -> new OptionDto(vp));
    }

    // 襲撃担当の狼リストを作成
    public List<OptionDto> getSelectableAttackerList(VillageInfo villageInfo) {
        Village village = villageInfo.village;
        int day = villageInfo.day;
        if (!isAbilityUsable(village, villageInfo.optVillagePlayer, day)) {
            // 能力を使用できない
            return new ArrayList<>();
        }

        VillagePlayer vPlayer = villageInfo.optVillagePlayer.get();
        if (!vPlayer.getSkillCodeAsSkill().isHasAttackAbility()) {
            return new ArrayList<>();
        }
        return attackLogic.getAttackableWolfs(village, day).map(vp -> new OptionDto(vp));
    }

    public List<String> makeSkillHistoryList(VillageInfo villageInfo) {
        Village village = villageInfo.village;
        int day = villageInfo.day;
        if (!isAbilityUsable(village, villageInfo.optVillagePlayer, day)) {
            return new ArrayList<>();
        }
        VillagePlayer villagePlayer = villageInfo.optVillagePlayer.get();
        AbilityType type = detectAbilityType(villagePlayer.getSkillCodeAsSkill());

        switch (type) {
        case ATTACK:
            return attackLogic.makeSkillHistoryList(village, day);
        case DIVINE:
            return divineLogic.makeSkillHistoryList(village, villagePlayer, day);
        case GUARD:
            return guardLogic.makeSkillHistoryList(village, villagePlayer, day);
        case WALLPUNCH:
            return wallPunchLogic.makeSkillHistoryList(village, villagePlayer, day);
        case DISTURB:
            return disturbLogic.makeSkillHistoryList(village, villagePlayer, day);
        case INVESTIGATE:
            return investigateLogic.makeSkillHistoryList(village, villagePlayer, day);
        case TRAP:
            return trapLogic.makeSkillHistoryList(village, villagePlayer, day);
        case BOMB:
            return bombLogic.makeSkillHistoryList(village, villagePlayer, day);
        case COHABIT:
            return cohabitLogic.makeSkillHistoryList(village, villagePlayer, day);
        case COMMAND:
            return commandLogic.makeSkillHistoryList(village, villagePlayer, day);
        case LONEATTACK:
            return loneAttackLogic.makeSkillHistoryList(village, villagePlayer, day);
        default:
            return new ArrayList<>();
        }
    }

    public boolean isAbilityUsable(Village village, Optional<VillagePlayer> optVillagePlayer, int day) {
        if (!optVillagePlayer.isPresent()) {
            return false;
        }
        VillagePlayer villagePlayer = optVillagePlayer.get();
        if (villagePlayer.isIsDeadTrue() || villagePlayer.isIsSpectatorTrue()) {
            return false;
        }
        if (!village.isVillageStatusCode進行中()) {
            return false;
        }
        if (!village.getVillageDays().latestDay().getDay().equals(day)) {
            return false;
        }
        AbilityType type = detectAbilityType(villagePlayer.getSkillCodeAsSkill());
        return isAbilityUsable(type, day);
    }

    public String createWerewolfCharaNameList(VillageInfo villageInfo) {
        if (!isAbilityUsable(villageInfo.village, villageInfo.optVillagePlayer, villageInfo.day)) {
            return null;
        }

        CDef.Skill skill = villageInfo.optVillagePlayer.get().getSkillCodeAsSkill();
        if (!skill.isViewableWolfCharaName()) {
            return null;
        }
        return String.join("、", villageInfo.vPlayers //
                .filterNotDummy(villageInfo.settings.getDummyCharaId())
                .filterNotSpecatate()
                .filterBy(vp -> vp.getSkillCodeAsSkill().isHasAttackAbility())
                .sortedByRoomNumber()
                .map(VillagePlayer::name));
    }

    public String createCMadmanCharaNameList(VillageInfo villageInfo) {
        if (!isAbilityUsable(villageInfo.village, villageInfo.optVillagePlayer, villageInfo.day)) {
            return null;
        }

        CDef.Skill skill = villageInfo.optVillagePlayer.get().getSkillCodeAsSkill();
        if (!skill.isAvailableWerewolfSay()) {
            return null;
        }
        return String.join("、", villageInfo.vPlayers //
                .filterNotDummy(villageInfo.settings.getDummyCharaId())
                .filterNotSpecatate()
                .filterBySkill(CDef.Skill.C国狂人)
                .sortedByRoomNumber()
                .map(VillagePlayer::name));
    }

    public String createFoxCharaNameList(VillageInfo villageInfo) {
        if (!isAbilityUsable(villageInfo.village, villageInfo.optVillagePlayer, villageInfo.day)) {
            return null;
        }

        VillagePlayer villagePlayer = villageInfo.optVillagePlayer.get();
        if (!isFoxCamp(villagePlayer)) {
            return null;
        }

        return String.join("、", villageInfo.vPlayers //
                .filterNotDummy(villageInfo.settings.getDummyCharaId())
                .filterNotSpecatate()
                .filterBy(vp -> vp.getSkillCodeAsSkill() == CDef.Skill.妖狐 || vp.getSkillCodeAsSkill() == CDef.Skill.誑狐)
                .sortedByRoomNumber()
                .map(VillagePlayer::name));
    }

    private boolean isFoxCamp(VillagePlayer villagePlayer) {
        CDef.Skill skill = villagePlayer.getSkillCodeAsSkill();
        if (skill == CDef.Skill.背徳者 || skill == CDef.Skill.妖狐 || skill == CDef.Skill.誑狐) {
            return true;
        }
        return villagePlayer.getVillagePlayerStatusByToVillagePlayerIdList()
                .stream()
                .anyMatch(st -> st.getVillagePlayerStatusCodeAsVillagePlayerStatusType() == CDef.VillagePlayerStatusType.狐憑き);
    }

    public String createLoversCharaNameList(VillageInfo villageInfo) {
        if (!villageInfo.optVillagePlayer.isPresent()) {
            return null;
        }
        VillagePlayer villagePlayer = villageInfo.optVillagePlayer.get();
        if (villagePlayer.isIsDeadTrue() || villagePlayer.isIsSpectatorTrue() || !villagePlayer.hasLover()) {
            return null;
        }
        if (!villageInfo.village.isVillageStatusCode進行中()) {
            return null;
        }
        if (!villageInfo.village.getVillageDays().latestDay().getDay().equals(villageInfo.day)) {
            return null;
        }

        StringJoiner joiner = new StringJoiner("、", "この村で恋に落ちているのは", "です。");
        villageInfo.vPlayers //
                .filterNotDummy(villageInfo.settings.getDummyCharaId())
                .filterNotSpecatate()
                .filterBy(vp -> vp.hasLover()).list.forEach(vp -> {
                    vp.getTargetLovers().list.forEach(lover -> {
                        joiner.add(String.format("%s（%s）", vp.name(), lover.name()));
                    });
                });
        return joiner.toString();
    }

    public String makeTargetPrefixMessage(VillageInfo villageInfo) {
        if (!isAbilityUsable(villageInfo.village, villageInfo.optVillagePlayer, villageInfo.day)) {
            return null;
        }
        AbilityType type = detectAbilityType(villageInfo.optVillagePlayer.get().getSkillCodeAsSkill());
        switch (type) {
        case ATTACK:
        case LONEATTACK:
            return "襲撃対象";
        case TRAP:
            return "罠を設置する部屋";
        case BOMB:
            return "爆弾を設置する部屋";
        case FRUITSBASKET:
            return "発動させる場合自分を選択してください";
        case COURT:
            return "求愛対象";
        case STALKING:
            return "ストーキング対象";
        case CHEAT:
            return "仲間に引き入れる対象";
        case SEDUCE:
            return "誘惑する対象";
        case BADGERGAME:
            return "誘惑して脅す対象";
        default:
            return null;
        }
    }

    public String makeTargetSuffixMessage(VillageInfo villageInfo) {
        if (!isAbilityUsable(villageInfo.village, villageInfo.optVillagePlayer, villageInfo.day)) {
            return null;
        }
        AbilityType type = detectAbilityType(villageInfo.optVillagePlayer.get().getSkillCodeAsSkill());
        switch (type) {
        case DIVINE:
            return "を占う";
        case GUARD:
            return "を護衛する";
        case WALLPUNCH:
            return "の部屋の壁を殴る";
        case COHABIT:
            return "の部屋で過ごす";
        case COMMAND:
            return "を指差す";
        default:
            return null;
        }
    }

    // 対象と足音両方を選択する能力か
    public Boolean isTargetingAndFootstep(VillageInfo villageInfo) {
        if (!isAbilityUsable(villageInfo.village, villageInfo.optVillagePlayer, villageInfo.day)) {
            return false;
        }
        AbilityType type = detectAbilityType(villageInfo.optVillagePlayer.get().getSkillCodeAsSkill());
        switch (type) {
        case ATTACK:
        case LONEATTACK:
        case DIVINE:
        case GUARD:
            return true;
        default:
            return false;
        }
    }

    private enum AbilityType {
        ATTACK, DIVINE, GUARD, WALLPUNCH, DISTURB, INVESTIGATE, TRAP, BOMB, COHABIT, COMMAND, FRUITSBASKET, COURT, // 
        STALKING, CHEAT, LONEATTACK, SEDUCE, BADGERGAME
    }

    private AbilityType detectAbilityType(CDef.Skill skill) {
        if (skill.isHasAttackAbility()) {
            return AbilityType.ATTACK;
        } else if (skill.isHasDivineAbility()) {
            return AbilityType.DIVINE;
        } else if (skill == CDef.Skill.狩人) {
            return AbilityType.GUARD;
        } else if (skill == CDef.Skill.壁殴り代行) {
            return AbilityType.WALLPUNCH;
        } else if (skill.isHasDisturbAbility()) {
            return AbilityType.DISTURB;
        } else if (skill == CDef.Skill.探偵) {
            return AbilityType.INVESTIGATE;
        } else if (skill == CDef.Skill.罠師) {
            return AbilityType.TRAP;
        } else if (skill == CDef.Skill.爆弾魔) {
            return AbilityType.BOMB;
        } else if (skill == CDef.Skill.同棲者) {
            return AbilityType.COHABIT;
        } else if (skill == CDef.Skill.指揮官) {
            return AbilityType.COMMAND;
        } else if (skill == CDef.Skill.果実籠) {
            return AbilityType.FRUITSBASKET;
        } else if (skill == CDef.Skill.求愛者) {
            return AbilityType.COURT;
        } else if (skill == CDef.Skill.ストーカー) {
            return AbilityType.STALKING;
        } else if (skill == CDef.Skill.誑狐) {
            return AbilityType.CHEAT;
        } else if (skill == CDef.Skill.一匹狼) {
            return AbilityType.LONEATTACK;
        } else if (skill == CDef.Skill.絡新婦) {
            return AbilityType.SEDUCE;
        } else if (skill == CDef.Skill.美人局) {
            return AbilityType.BADGERGAME;
        }
        return null;
    }

    private boolean isAbilityUsable(AbilityType type, int day) {
        if (type == null) {
            return false;
        }
        switch (type) {
        case ATTACK:
        case DIVINE:
        case DISTURB:
        case COHABIT:
        case CHEAT:
        case SEDUCE:
        case BADGERGAME:
            return true;
        case GUARD:
        case WALLPUNCH:
        case INVESTIGATE:
        case TRAP:
        case BOMB:
        case COMMAND:
        case FRUITSBASKET:
        case LONEATTACK:
            return day > 1;
        case COURT:
        case STALKING:
            return day == 1;
        default:
            return false;
        }
    }
}
