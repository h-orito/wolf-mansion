package com.ort.app.logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.StringJoiner;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ort.app.logic.ability.AttackLogic;
import com.ort.app.logic.ability.BombLogic;
import com.ort.app.logic.ability.CohabitLogic;
import com.ort.app.logic.ability.CommandLogic;
import com.ort.app.logic.ability.CourtLogic;
import com.ort.app.logic.ability.DisturbLogic;
import com.ort.app.logic.ability.DivineLogic;
import com.ort.app.logic.ability.FruitsBasketLogic;
import com.ort.app.logic.ability.GuardLogic;
import com.ort.app.logic.ability.InvestigateLogic;
import com.ort.app.logic.ability.StalkingLogic;
import com.ort.app.logic.ability.TrapLogic;
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
    private DivineLogic divineLogic;
    @Autowired
    private GuardLogic guardLogic;
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

    // ===================================================================================
    //                                                                             Execute
    //                                                                             =======
    // ???????????????
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
        default:
            return;
        }
    }

    // ??????????????????
    public List<OptionDto> getSelectableTargetList(VillageInfo villageInfo) {
        Village village = villageInfo.village;
        int day = villageInfo.day;

        if (!isAbilityUsable(village, villageInfo.optVillagePlayer, day)) {
            // ???????????????????????????
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
        case DISTURB:
            return null;
        case INVESTIGATE:
            List<String> footstepList = investigateLogic.getSelectableTarget(village, day);
            // ??????????????????????????????????????????????????????????????????
            return footstepList.stream().map(fs -> {
                OptionDto option = new OptionDto();
                option.setName(fs);
                option.setValue(fs);
                return option;
            }).collect(Collectors.toList());
        case TRAP:
            // ?????????????????????????????????????????????
            List<OptionDto> list = trapLogic.getSelectableTarget(village, villagePlayer, day).map(vp -> new OptionDto(vp));
            list.add(0, new OptionDto("??????", ""));
            return list;
        case BOMB:
            // ?????????????????????????????????????????????
            List<OptionDto> lists = bombLogic.getSelectableTarget(village, villagePlayer, day).map(vp -> new OptionDto(vp));
            lists.add(0, new OptionDto("??????", ""));
            return lists;
        case COHABIT:
            selectablePlayers = cohabitLogic.getSelectableTarget(villagePlayer);
            break;
        case COMMAND:
            // ?????????????????????????????????????????????
            List<OptionDto> l = commandLogic.getSelectableTarget(village, villagePlayer, day).map(vp -> new OptionDto(vp));
            l.add(0, new OptionDto("??????", ""));
            return l;
        case FRUITSBASKET:
            // ?????????????????????????????????????????????
            List<OptionDto> lists2 = fruitsBasketLogic.getSelectableTarget(village, villagePlayer, day).map(vp -> new OptionDto(vp));
            lists2.add(0, new OptionDto("??????", ""));
            return lists2;
        case COURT:
            selectablePlayers = courtLogic.getSelectableTarget(village, day, villagePlayer);
            break;
        case STALKING:
            selectablePlayers = stalkingLogic.getSelectableTarget(village, day, villagePlayer);
            break;
        default:
            return null;
        }

        if (selectablePlayers == null) {
            return null;
        }
        return selectablePlayers.map(vp -> new OptionDto(vp));
    }

    // ????????????????????????????????????
    public List<OptionDto> getSelectableAttackerList(VillageInfo villageInfo) {
        Village village = villageInfo.village;
        int day = villageInfo.day;
        if (!isAbilityUsable(village, villageInfo.optVillagePlayer, day)) {
            // ???????????????????????????
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
        if (!village.isVillageStatusCode?????????()) {
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
        return String.join("???", villageInfo.vPlayers //
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
        return String.join("???", villageInfo.vPlayers //
                .filterNotDummy(villageInfo.settings.getDummyCharaId())
                .filterNotSpecatate()
                .filterBySkill(CDef.Skill.C?????????)
                .sortedByRoomNumber()
                .map(VillagePlayer::name));
    }

    public String createFoxCharaNameList(VillageInfo villageInfo) {
        if (!isAbilityUsable(villageInfo.village, villageInfo.optVillagePlayer, villageInfo.day)) {
            return null;
        }

        CDef.Skill skill = villageInfo.optVillagePlayer.get().getSkillCodeAsSkill();
        if (skill != CDef.Skill.?????????) {
            return null;
        }

        return String.join("???", villageInfo.vPlayers //
                .filterNotDummy(villageInfo.settings.getDummyCharaId())
                .filterNotSpecatate()
                .filterBySkill(CDef.Skill.??????)
                .sortedByRoomNumber()
                .map(VillagePlayer::name));
    }

    public String createLoversCharaNameList(VillageInfo villageInfo) {
        if (!villageInfo.optVillagePlayer.isPresent()) {
            return null;
        }
        VillagePlayer villagePlayer = villageInfo.optVillagePlayer.get();
        if (villagePlayer.isIsDeadTrue() || villagePlayer.isIsSpectatorTrue() || !villagePlayer.hasLover()) {
            return null;
        }
        if (!villageInfo.village.isVillageStatusCode?????????()) {
            return null;
        }
        if (!villageInfo.village.getVillageDays().latestDay().getDay().equals(villageInfo.day)) {
            return null;
        }

        StringJoiner joiner = new StringJoiner("???", "???????????????????????????????????????", "?????????");
        villageInfo.vPlayers //
                .filterNotDummy(villageInfo.settings.getDummyCharaId())
                .filterNotSpecatate()
                .filterBy(vp -> vp.hasLover()).list.forEach(vp -> {
                    vp.getTargetLovers().list.forEach(lover -> {
                        joiner.add(String.format("%s???%s???", vp.name(), lover.name()));
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
            return "????????????";
        case TRAP:
            return "????????????????????????";
        case BOMB:
            return "???????????????????????????";
        case FRUITSBASKET:
            return "??????????????????????????????????????????????????????";
        case COURT:
            return "????????????";
        case STALKING:
            return "????????????????????????";
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
            return "?????????";
        case GUARD:
            return "???????????????";
        case COHABIT:
            return "?????????????????????";
        case COMMAND:
            return "????????????";
        default:
            return null;
        }
    }

    // ?????????????????????????????????????????????
    public Boolean isTargetingAndFootstep(VillageInfo villageInfo) {
        if (!isAbilityUsable(villageInfo.village, villageInfo.optVillagePlayer, villageInfo.day)) {
            return false;
        }
        AbilityType type = detectAbilityType(villageInfo.optVillagePlayer.get().getSkillCodeAsSkill());
        switch (type) {
        case ATTACK:
        case DIVINE:
        case GUARD:
            return true;
        default:
            return false;
        }
    }

    private enum AbilityType {
        ATTACK, DIVINE, GUARD, DISTURB, INVESTIGATE, TRAP, BOMB, COHABIT, COMMAND, FRUITSBASKET, COURT, STALKING
    }

    private AbilityType detectAbilityType(CDef.Skill skill) {
        if (skill.isHasAttackAbility()) {
            return AbilityType.ATTACK;
        } else if (skill.isHasDivineAbility()) {
            return AbilityType.DIVINE;
        } else if (skill == CDef.Skill.??????) {
            return AbilityType.GUARD;
        } else if (skill.isHasDisturbAbility()) {
            return AbilityType.DISTURB;
        } else if (skill == CDef.Skill.??????) {
            return AbilityType.INVESTIGATE;
        } else if (skill == CDef.Skill.??????) {
            return AbilityType.TRAP;
        } else if (skill == CDef.Skill.?????????) {
            return AbilityType.BOMB;
        } else if (skill == CDef.Skill.?????????) {
            return AbilityType.COHABIT;
        } else if (skill == CDef.Skill.?????????) {
            return AbilityType.COMMAND;
        } else if (skill == CDef.Skill.?????????) {
            return AbilityType.FRUITSBASKET;
        } else if (skill == CDef.Skill.?????????) {
            return AbilityType.COURT;
        } else if (skill == CDef.Skill.???????????????) {
            return AbilityType.STALKING;
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
            return true;
        case GUARD:
        case INVESTIGATE:
        case TRAP:
        case BOMB:
        case COMMAND:
        case FRUITSBASKET:
            return day > 1;
        case COURT:
        case STALKING:
            return day == 1;
        default:
            return false;
        }
    }
}
