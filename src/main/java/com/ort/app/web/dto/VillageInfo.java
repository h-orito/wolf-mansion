package com.ort.app.web.dto;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.dbflute.cbean.result.ListResultBean;
import org.dbflute.optional.OptionalThing;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.ort.dbflute.exentity.Ability;
import com.ort.dbflute.exentity.Footstep;
import com.ort.dbflute.exentity.Village;
import com.ort.dbflute.exentity.VillageDay;
import com.ort.dbflute.exentity.VillagePlayer;
import com.ort.dbflute.exentity.VillageSettings;
import com.ort.dbflute.exentity.Vote;
import com.ort.fw.security.UserInfo;

public class VillageInfo {

    public Integer villageId;
    public Village village;
    public List<VillagePlayer> vPlayerList;
    public VillageSettings settings;
    public UserInfo user;
    public OptionalThing<VillagePlayer> optVillagePlayer;
    public List<VillageDay> dayList;
    public int day;
    public List<Vote> voteList;
    public List<Footstep> footstepList;
    public List<Ability> abilityList;

    public VillageInfo() {
    }

    public VillageInfo(Village village, UserInfo userInfo, ListResultBean<VillageDay> dayList,
            OptionalThing<VillagePlayer> optVillagePlayer, int day, List<Vote> voteList, List<Footstep> footstepList,
            List<Ability> abilityList) {
        this.villageId = village.getVillageId();
        this.village = village;
        this.vPlayerList = village.getVillagePlayerList();
        this.settings = village.getVillageSettingsAsOne().get();
        this.user = userInfo;
        this.optVillagePlayer = optVillagePlayer;
        this.dayList = dayList;
        this.day = day;
        this.voteList = voteList;
        this.footstepList = footstepList;
        this.abilityList = abilityList;
    }

    public List<Integer> getDayList() {
        return dayList.stream().map(VillageDay::getDay).collect(Collectors.toList());
    }

    public List<VillagePlayer> getVPList(boolean rejectDead, boolean rejectSpectate, boolean rejectDummy) {
        Stream<VillagePlayer> stVPList = vPlayerList.stream();
        if (rejectDead) {
            stVPList = stVPList.filter(vp -> vp.isIsDeadFalse());
        }
        if (rejectSpectate) {
            stVPList = stVPList.filter(vp -> vp.isIsSpectatorFalse());
        }
        if (rejectDummy) {
            stVPList = stVPList.filter(vp -> vp.getCharaId().intValue() != settings.getDummyCharaId());
        }
        return stVPList.collect(Collectors.toList());
    }

    // 投票が始まっているか
    public boolean isStartedVote() {
        return dayList.size() > 3; // 2日目以降は投票が始まっている
    }

    // 足音セットが始まっているか
    public boolean isStartedFootstepSet() {
        return dayList.size() > 2; // 1日目以降は足音セットが始まっている
    }

    // この村に参加しているか
    public boolean isParticipate() {
        return optVillagePlayer.isPresent();
    }

    // 最新日か
    public boolean isLatestDay() {
        return day == this.getLatestDay();
    }

    public int getLatestDay() {
        return this.getDayList().get(this.getDayList().size() - 1).intValue();
    }

    // 管理者か
    public boolean isAdmin() {
        return user != null && user.getAuthorities().stream().anyMatch(a -> a.equals(new SimpleGrantedAuthority("ROLE_ADMIN")));
    }

    // 村建てか
    public boolean isCreator() {
        String createPlayerName = village.getCreatePlayerName();
        return user != null && user.getUsername().equals(createPlayerName);
    }

    // 死亡しているか
    public boolean isDead() {
        return optVillagePlayer.isPresent() && optVillagePlayer.get().isIsDeadTrue();
    }

    // 観戦者か
    public boolean isSpectator() {
        return optVillagePlayer.isPresent() && optVillagePlayer.get().isIsSpectatorTrue();
    }
}
